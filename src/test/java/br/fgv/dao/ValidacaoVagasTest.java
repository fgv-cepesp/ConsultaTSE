package br.fgv.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

import br.fgv.business.BusinessImpl;

public class ValidacaoVagasTest {

	private Session session = FactorySessionMySqlDB.create();
	private BusinessImpl businessImpl = new BusinessImpl(
			new DaoFactoryImpl(session));

	public String createQuery(String ano, String cargo, String ue, String res)
			throws IOException {

		BufferedReader in;

		if(
				ano.equals("2000") ||
				ano.equals("2004") ||
				ano.equals("2008") ||
				ano.equals("2012")
			) {
			in = new BufferedReader(new InputStreamReader(this
					.getClass().getResourceAsStream("query.sql")));
		} else {
			in = new BufferedReader(new InputStreamReader(this
					.getClass().getResourceAsStream("query_uf.sql")));
		}


		String line = null;

		StringBuilder query = new StringBuilder();
		while ((line = in.readLine()) != null) {
			query.append(line).append("\n");
		}
		String str = query.toString();

		str = str.replace("${ano}", ano);
//		str = str.replace("${cargo}", cargo);
//		str = str.replace("${ue}", ue);
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
			+ "AND SIGLA_UE = '" + ue + "'";
		SQLQuery query = session.createSQLQuery(queryStr);

		Set<String> list = new HashSet<String>((List<String>) query.list());

		return new ArrayList<String>(list);
	}

	private int vagasEsperadas(String ano, String ue, String cargo) {
		String queryStr = "select QTDE_VAGAS from aux_vagas "
			+ "where ANO_ELEICAO = " + ano + " "
			+ "AND SIGLA_UE = '" + ue + "' "
			+ "AND CODIGO_CARGO = " + cargo;

		SQLQuery query = session.createSQLQuery(queryStr);
		List<String> result = (List<String>) query.list();

		return Integer.parseInt((String) result.get(0));
	}

	private Map<String, Map<Integer, Integer>> vagasPreenchidas(String ano) throws IOException, SQLException {

		String res = "resultado_cod = 1 OR resultado_cod = 3 OR (resultado_cod = 7 AND turno = 2)";
		if("2012".equals(ano)) {
			res += " OR resultado_cod = 4";
		}
		String q = createQuery(ano, null, null, res);

		Map<String, Map<Integer, Integer>> table = new HashMap<String, Map<Integer,Integer>>();

		ResultSetWork ra = new ResultSetWork();

		ra.setQuery(q);
		session.doWork(ra);

		final ResultSet rs = ra.getResultSet();
		while (rs.next()) {
			if(rs.getString(2) != null) {
				String ue = eliminaZeros(rs.getString(1));
				Integer cargo = Integer.decode(rs.getString(2));
				Integer vagas = Integer.decode(rs.getString(3));

				// 11: prefeito
				// 13: vereador

				if(!table.containsKey(ue)) {
					table.put(ue, new HashMap<Integer, Integer>());
				}
				table.get(ue).put(cargo, vagas);
			}
		}

		return table;
	}

	public void test(String[] args) throws IOException, SQLException {
		PrintStream out = new PrintStream("target/ValidacaoVagasTest.txt");

		String[] anos = {
//				"2000",
				"2002",
//				"2004",
				"2006",
//				"2008",
				"2010",
//				"2012"
				};


		// encontrar as UEs em cada ano
		for (String ano : anos) {
			System.out.println("Verificando " + ano);
			// ue -> cargo -> vagas preenchidas
			Map<String, Map<Integer, Integer>> table = vagasPreenchidas(ano);
			List<String> ues = buscarUEs(ano);
			for (String ue : ues) {
				// encontrar os cargos para cada UE
				List<String> cargos = buscarCargos(ano, ue);

				Map<Integer, Integer> tabelaUE = table.get(eliminaZeros(ue));

				if(tabelaUE == null) {
					out.println("UE faltando - Ano: " + ano + ", UE: " + ue);
				} else {
					for (String cargo : cargos) {
						int esperadas = vagasEsperadas(ano, ue, cargo);

						Integer preenchidas =  tabelaUE.get(new Integer(cargo));

						if(preenchidas == null) {
							out.println("Cargo faltando - Ano: " + ano + ", UE: " + ue + ", Cargo: " + cargo);
						} else if(esperadas != preenchidas) {
							out.println("Erro - Ano: " + ano + ", UE: " + ue + ", Cargo: " + cargo + " -> " + esperadas + " != " + preenchidas);
						}/* else {
							System.out.println("  OK - Ano: " + ano + ", UE: " + ue + ", Cargo: " + cargo + " -> " + esperadas + " == " + preenchidas);
						}*/
					}
				}
			}
		}

		out.close();

	}

	private String eliminaZeros(String s) {
		return s.replaceFirst("^0+(?!$)", "");
	}

	public static void main(String[] args) throws IOException, SQLException {
		ValidacaoVagasTest v = new ValidacaoVagasTest();
		v.test(null);
	}

}
