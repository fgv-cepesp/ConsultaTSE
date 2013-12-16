package br.fgv.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

import br.fgv.business.BusinessImpl;

public class ValidacaoVagasTest {

	private Session session = FactorySessionMySqlDB.create();
	private BusinessImpl businessImpl = new BusinessImpl(
			new DaoFactoryImpl(session));

	public String createQuery(String ano, String cargo, String ue, String res)
			throws IOException {

		BufferedReader in = new BufferedReader(new InputStreamReader(this
				.getClass().getResourceAsStream("query.sql")));
		String line = null;

		StringBuilder query = new StringBuilder();
		while ((line = in.readLine()) != null) {
			query.append(line).append("\n");
		}
		String str = query.toString();

		str = str.replace("${ano}", ano);
		str = str.replace("${cargo}", cargo);
		str = str.replace("${ue}", ue);
		str = str.replace("${res}", res);

		return str;
	}

	private List<String> buscarUEs(String ano) {
		String queryStr = "select SIGLA_UE from aux_vagas "
+ "where ANO_ELEICAO = " + ano + " "
+ "GROUP BY SIGLA_UE";

		SQLQuery query = session.createSQLQuery(queryStr);
		return (List<String>) query.list();
	}

	private List<String> buscarCargos(String ano, String ue) {
		String queryStr = "select CODIGO_CARGO from aux_vagas "
+ "where ANO_ELEICAO = " + ano + " "
+ "AND SIGLA_UE = " + ue;

		SQLQuery query = session.createSQLQuery(queryStr);
		return (List<String>) query.list();
	}

	private int vagasEsperadas(String ano, String ue, String cargo) {
		String queryStr = "select QTDE_VAGAS from aux_vagas "
+ "where ANO_ELEICAO = " + ano + " "
+ "AND SIGLA_UE = " + ue + " "
+ "AND CODIGO_CARGO = " + cargo;

		SQLQuery query = session.createSQLQuery(queryStr);
		return Integer.parseInt((String) query.uniqueResult());
	}

	private int vagasPreenchidas(String ano, String ue, String cargo) throws IOException {
		String q = createQuery(ano, cargo, ue, "resultado_cod = 1 OR resultado_cod = 3");

		SQLQuery query = session.createSQLQuery(q);
		return ((BigInteger)query.uniqueResult()).intValue();
	}

	public void test(String[] args) throws IOException {
		String[] anos = { "2000", "2002", "2004", "2006", "2008", "2010",
				"2012" };

		// encontrar as UEs em cada ano
		for (String ano : anos) {
			List<String> ues = buscarUEs(ano);
			for (String ue : ues) {
				// encontrar os cargos para cada UE
				List<String> cargos = buscarCargos(ano, ue);
				for (String cargo : cargos) {
					int esperadas = vagasEsperadas(ano, ue, cargo);
					int preenchidas = vagasPreenchidas(ano, ue, cargo);

					if(esperadas != preenchidas) {
						System.out.println("Ano: " + ano + ", UE: " + ue + ", Cargo: " + cargo + " -> " + esperadas + " != " + preenchidas);
					}
				}
			}
		}

	}

	public static void main(String[] args) throws IOException {
		ValidacaoVagasTest v = new ValidacaoVagasTest();
		v.test(null);
	}

}
