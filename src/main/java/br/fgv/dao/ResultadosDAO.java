/**
 * Copyright (c) 20012-2013 "FGV - CEPESP" [http://cepesp.fgv.br]
 *
 * This file is part of CEPESP-DATA.
 *
 * CEPESP-DATA is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * CEPESP-DATA is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with CEPESP-DATA. If not, see <http://www.gnu.org/licenses/>.
 */
package br.fgv.dao;

import static br.fgv.model.Tabela.CO_DIM_CANDIDATOS_CARGO_COD;
import static br.fgv.model.Tabela.CO_DIM_CANDIDATOS_NOME;
import static br.fgv.model.Tabela.CO_DIM_CANDIDATOS_TITULO;
import static br.fgv.model.Tabela.CO_DIM_CARGO_CD;
import static br.fgv.model.Tabela.CO_DIM_CARGO_DS;
import static br.fgv.model.Tabela.CO_DIM_ESTADOS_ID;
import static br.fgv.model.Tabela.CO_DIM_ESTADOS_NOME;
import static br.fgv.model.Tabela.CO_DIM_MACROREGIAO_COD;
import static br.fgv.model.Tabela.CO_DIM_MACROREGIAO_NOME;
import static br.fgv.model.Tabela.CO_DIM_MESOREGIAO_ID;
import static br.fgv.model.Tabela.CO_DIM_MESOREGIAO_NOME;
import static br.fgv.model.Tabela.CO_DIM_MICROREGIAO_ID;
import static br.fgv.model.Tabela.CO_DIM_MICROREGIAO_NOME;
import static br.fgv.model.Tabela.CO_DIM_MUNICIPIO_COD;
import static br.fgv.model.Tabela.CO_DIM_MUNICIPIO_NOME;
import static br.fgv.model.Tabela.CO_DIM_MUNICIPIO_SIGLA_UF;
import static br.fgv.model.Tabela.CO_DIM_PARTIDOS_ANO;
import static br.fgv.model.Tabela.CO_DIM_PARTIDOS_COD;
import static br.fgv.model.Tabela.CO_DIM_PARTIDOS_SIGLA;
import static br.fgv.model.Tabela.CO_FACT_VOTOS_MUN_COD_CARGO;
import static br.fgv.model.Tabela.CO_FACT_VOTOS_MUN_QNT_VOTOS;
import static br.fgv.model.Tabela.CO_FACT_VOTOS_MUN_TIPO_VOTAVEL;
import static br.fgv.model.Tabela.CO_FACT_VOTOS_MUN_TURNO;
import static br.fgv.model.Tabela.CO_SIS_ANOS_ANO;
import static br.fgv.model.Tabela.CO_SIS_ANO_CARGO_ANO;
import static br.fgv.model.Tabela.CO_SIS_ANO_CARGO_COD_CARGO;
import static br.fgv.model.Tabela.REF_FACT;
import static br.fgv.model.Tabela.TB_DIM_CANDIDATOS;
import static br.fgv.model.Tabela.TB_DIM_CARGO;
import static br.fgv.model.Tabela.TB_DIM_ESTADOS;
import static br.fgv.model.Tabela.TB_DIM_MACROREGIAO;
import static br.fgv.model.Tabela.TB_DIM_MESOREGIAO;
import static br.fgv.model.Tabela.TB_DIM_MICROREGIAO;
import static br.fgv.model.Tabela.TB_DIM_MUNICIPIO;
import static br.fgv.model.Tabela.TB_DIM_PARTIDOS;
import static br.fgv.model.Tabela.TB_FACT_VOTOS_MUN;
import static br.fgv.model.Tabela.TB_SIS_ANOS;
import static br.fgv.model.Tabela.TB_SIS_ANO_CARGO;
import static br.fgv.model.Tabela.VOTO_LEGENDA;
import static br.fgv.model.Tabela.VOTO_LEGENDA_COD;
import static br.fgv.model.Tabela.VOTO_NOMINAL;
import static br.fgv.model.Tabela.VOTO_NOMINAL_COD;
import static br.fgv.model.Tabela.VOTO_TOTAL;
import static br.fgv.util.QueryBuilder.EQ;
import static br.fgv.util.QueryBuilder.IFF;
import static br.fgv.util.QueryBuilder._ORDER_BY_;
import static br.fgv.util.QueryBuilder.a;
import static br.fgv.util.QueryBuilder.p;
import static br.fgv.util.QueryBuilder.s;

import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import br.fgv.model.*;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import br.fgv.CepespDataException;
import br.fgv.business.AgregacaoPolitica;
import br.fgv.business.AgregacaoRegional;
import br.fgv.util.ArgumentosBusca;
import br.fgv.util.CSVBuilder;
import br.fgv.util.Par;
import br.fgv.util.QueryBuilder;

public class ResultadosDAO {

	private static final Logger LOGGER = Logger.getLogger(ResultadosDAO.class);

	private final Session session;

	public ResultadosDAO(Session session) {
		this.session = session;
	}

	private Session getSession() {
		return session;
	}

	public List<Integer> getAnosDisponiveisList() {
		List<Integer> list = null;

		try {
			QueryBuilder qb = new QueryBuilder();

			qb.select_().coluna(CO_SIS_ANOS_ANO)
				._from_().tabela(TB_SIS_ANOS)
				._order_by_().coluna(CO_SIS_ANOS_ANO);

			Query query = getSession().createSQLQuery(qb.toString());

			list = (List<Integer>) query.list();

		} catch (RuntimeException e) {
			LOGGER.error("Falhou ao tentar obter anos disponiveis.", e);
		}

		return list;
	}

	@Deprecated
	public Map<String, String> getCargosList() {
		List<Integer> anos = getAnosDisponiveisList();
		List<String> queries = new ArrayList<String>();

		for (Integer ano : anos)
			queries.add(getQueryCargosPorAnoList(Integer.toString(ano)));

		String completeQuery = QueryBuilder.unionDistinct(queries) + _ORDER_BY_ + CO_SIS_ANO_CARGO_COD_CARGO;

		System.out.println(">> QUERY COMPLETA");
		System.out.println(completeQuery);
		System.out.println("<< QUERY COMPLETA");

		Map<String, String> pares = new HashMap<String, String>();

		try {
			Query query = getSession().createSQLQuery(completeQuery);
			List<Object[]> list = (List<Object[]>) query.list();

			for (Object[] item : list)
				pares.put(String.valueOf(item[0]), String.valueOf(item[1]));

		} catch (RuntimeException e) {
			LOGGER.error("Falhou ao tentar obter cargos disponiveis.", e);
		}

		return pares;
	}

	public List<Cargo> getCargos() {
		List<Cargo> cargos = new ArrayList<Cargo>();
		cargos.add(new Cargo(1, "Presidente"));
		//cargos.add(new Cargo(2,	"Vice-Presidente"));
		cargos.add(new Cargo(3,	"Governador"));
		//cargos.add(new Cargo(4,	"Vice-Governador"));
		//cargos.add(new Cargo(9,	"1º Suplente Senador"));
		//cargos.add(new Cargo(10, "2º Suplente Senador"));
		cargos.add(new Cargo(5,	"Senador"));
		cargos.add(new Cargo(6,	"Deputado Federal"));
		//cargos.add(new Cargo(7, "Deputado Estadual(Inclui DF)"));
		cargos.add(new Cargo(7,	"Deputado Estadual"));
		cargos.add(new Cargo(8,	"Deputado Distrital"));
		cargos.add(new Cargo(11, "Prefeito"));
		//cargos.add(new Cargo(12, "Vice-Prefeito"));
		cargos.add(new Cargo(13, "Vereador"));

		return cargos;
	}

	@Deprecated
	public Map<String, String> getCargosPorAnoList(String ano) {
		Map<String, String> pares = new HashMap<String, String>();

		try {
			String queryString = getQueryCargosPorAnoList(ano);
			Query query = getSession().createSQLQuery(queryString);
			List<Object[]> list = (List<Object[]>) query.list();

			for (Object[] item : list)
				pares.put(String.valueOf(item[0]), String.valueOf(item[1]));

		} catch (RuntimeException e) {
			LOGGER.error("Falhou ao tentar obter cargos disponiveis por ano.", e);
		}

		return pares;
	}

	@Deprecated
	public String getQueryCargosPorAnoList(String ano) {
		QueryBuilder qb = new QueryBuilder();

		qb.select_().colunas(CO_SIS_ANO_CARGO_COD_CARGO, CO_DIM_CARGO_DS)
			._from_().declareRef(TB_SIS_ANO_CARGO, s).comma_().declareRef(TB_DIM_CARGO, a)
			._where_().ref(CO_SIS_ANO_CARGO_COD_CARGO, s)._eq_().ref(CO_DIM_CARGO_CD, a)
				._and_().ref(CO_SIS_ANO_CARGO_ANO, s)._eq_().valor(ano);

		return qb.toString();
	}

	public List<Integer> getAnosParaCargoList(String cargo) {
		try {
			QueryBuilder qb = new QueryBuilder();

			qb.select_()._distinct_().colunas(CO_SIS_ANO_CARGO_ANO)
				._from_().tabela(TB_SIS_ANO_CARGO)
				._where_().coluna(CO_SIS_ANO_CARGO_COD_CARGO)._eq_().valor(cargo)
				._order_by_().coluna(CO_SIS_ANO_CARGO_ANO);

			Query query = getSession().createSQLQuery(qb.toString());

			return (List<Integer>) query.list();
		} catch (RuntimeException e) {
			LOGGER.error("Falhou ao tentar obter cargos disponiveis por ano.", e);
			throw e;
		}
	}

	QueryBuilder appendFiltroPolitico(QueryBuilder query, AgregacaoPolitica agregacaoPolitica, String[] filtroPolitico) {
		// nesses casos o nivel é fora do intervalo...
		if (agregacaoPolitica == null || filtroPolitico.length == 0) {
			// do nothing
		} else {
			query._and_().valor(agregacaoPolitica.getNome()).in(filtroPolitico);
		}

		return query;
	}

	QueryBuilder appendFiltroRegional(QueryBuilder query, AgregacaoRegional agregacaoRegional, String[] filtroRegional) {
		if(agregacaoRegional == null || filtroRegional.length == 0){
			// do nothing
		} else {
			query._and_().valor(agregacaoRegional.getCamposAgregar()).in(filtroRegional);
		}

		return query;
	}


	String getStringQueryFato(ArgumentosBusca args, String ano) {

		String reg = args.getNivelRegional().getCamposAgregar();
		String pol = args.getNivelAgrecacaoPolitica().getNome();

		QueryBuilder qb = new QueryBuilder();

		// SELECT
		qb.select_().comma(CO_FACT_VOTOS_MUN_TURNO, reg, pol);
		qb.comma_().sum(IFF( EQ(CO_FACT_VOTOS_MUN_TIPO_VOTAVEL, VOTO_NOMINAL_COD), CO_FACT_VOTOS_MUN_QNT_VOTOS, 0))._as_().valor(VOTO_NOMINAL);
		qb.comma_().sum(CO_FACT_VOTOS_MUN_QNT_VOTOS)._as_().valor(VOTO_TOTAL);

		if(AgregacaoPolitica.PARTIDO.equals(args.getNivelAgrecacaoPolitica()) || AgregacaoPolitica.COLIGACAO.equals(args.getNivelAgrecacaoPolitica())) {
			  qb.comma_().sum(IFF( EQ(CO_FACT_VOTOS_MUN_TIPO_VOTAVEL, VOTO_LEGENDA_COD), CO_FACT_VOTOS_MUN_QNT_VOTOS, 0))._as_().valor(VOTO_LEGENDA);
		}

		qb._from_().tabela(TB_FACT_VOTOS_MUN);

		// WHERE
		qb._where_().eq(CO_FACT_VOTOS_MUN_COD_CARGO, args.getFiltroCargo())
			._and_().eq(CO_FACT_VOTOS_MUN_TURNO, args.getTurno());

			List<String> regioes = args.getRegioes();
			List<String> partidos = args.getPartidos();
			appendFiltroRegional(qb, args.getNivelRegional(), regioes.toArray(new String[regioes.size()]));
			appendFiltroPolitico(qb, AgregacaoPolitica.PARTIDO, partidos.toArray(new String[partidos.size()]));
			// filtro candidato agora é feito na tabela resultado. Veja metodo aplicarFiltros

		// GROUP BY
		qb._group_by_().comma(reg, pol, CO_FACT_VOTOS_MUN_TURNO)._order_by_().comma(reg, pol, CO_FACT_VOTOS_MUN_TURNO);

		return qb.toString(ano);
	}

	String getStringQueryDim(String queryFato, String anoEleicao,
			String[] camposEscolhidos, AgregacaoPolitica agregacaoPolitica) {

		SortedSet<String> tabelasARelacionar = new TreeSet<String>(new Comparator<String>() {
						public int compare(String o1, String o2) {
							Tabela t1 = Tabela.byName(o1);
							Tabela t2 = Tabela.byName(o2);
							return t1.compareTo(t2);
						}
					});
		



		SortedSet<String> camposFiltrados = new TreeSet<String>(new Comparator<String>() {
			public int compare(String campo1, String campo2) {
				campo1 = zonaWorkaround(campo1);
				campo2 = zonaWorkaround(campo2);

				String nomeTab1 = campo1.substring(0, campo1.indexOf('.'));
				String nomeCol1 = campo1.substring(campo1.indexOf('.') + 1);

				String nomeTab2 = campo2.substring(0, campo2.indexOf('.'));
				String nomeCol2 = campo2.substring(campo2.indexOf('.') + 1);

				Tabela t1 = Tabela.byName(nomeTab1);
				Tabela t2 = Tabela.byName(nomeTab2);

				if(nomeTab1.equals(nomeTab2)) {
					// compara coluna
					Coluna c1 = t1.getColuna(nomeCol1);
					Coluna c2 = t2.getColuna(nomeCol2);
					return c1.compareTo(c2);
				}

				return t1.compareTo(t2);
			}

			private String zonaWorkaround(String campo) {
				if(campo.startsWith("zona"))
					return TB_DIM_MUNICIPIO.getNome() + "." + CO_DIM_MUNICIPIO_NOME.getNome();
				return campo;
			}
		});
		
		Map<String, String> descricoes = new HashMap<String, String>();

		for (String campo : camposEscolhidos) {
			String tabela = campo.substring(0, campo.indexOf("."));
			String coluna = campo.substring(campo.indexOf(".") + 1);
			
			Tabela t = Tabela.byName(tabela); 
			if(t != null) {
				tabelasARelacionar.add(tabela);
				
				Coluna c = t.getColuna(coluna);
				if(c != null && c.hasDescricao()) {
					tabelasARelacionar.add(c.getTabelaDescricao());
					descricoes.put(campo, c.getDescricaoCol());
				}
			}
			
			if(campo.startsWith("zona.")) {
				camposFiltrados.add("zona");
			} else if(campo.startsWith("votos.")) {
				// nao inclui
			} else {
				camposFiltrados.add(campo);
			}
			
		}
		

		QueryBuilder qb = new QueryBuilder();
		qb.select_().valor(anoEleicao + " AS \"anoEleicao\", ").
			valor(CO_FACT_VOTOS_MUN_TURNO + " AS \"turno\", ").commaWithTrailing(camposFiltrados.toArray()).comma(agregacaoPolitica.getColunas())
			._from_().par(queryFato)._as_().valor(REF_FACT);

		for (String nomeTabela : tabelasARelacionar) {
			Tabela tabela = Tabela.byName(nomeTabela);
	        if(tabela != null && tabela.getRelacao() != null && !nomeTabela.equals("zona")) {
	        	qb._left_join_().tabela(tabela)._on_().valor(replace(tabela.getRelacao(), anoEleicao));
	        }
		}
		
		for (String k : descricoes.keySet()) {
			qb.replaceAll(k, k + ", " + descricoes.get(k));
		}

		String ret = qb.toString(anoEleicao);
		
		

		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("Query DIM:\t" + ret);
		}

		return ret;
	}

	private String replace(String relacao, String anoEleicao) {
		return relacao.replace(Tabela.HOLDER_ANO_ELEICAO, anoEleicao);
	}

	public ResultSetWork queryFato(ArgumentosBusca args) {
		List<String> queries = new ArrayList<String>();
		List<String> listCamposEscolhidos = args.getCamposEscolhidos();
		String[] camposEscolhidos = listCamposEscolhidos.toArray(new String[listCamposEscolhidos.size()]);

		for (String ano : args.getAnosEleicoes()) {
		    String queryFato = getStringQueryFato(args, ano);
			String queryTotal = getStringQueryDim(queryFato, ano, camposEscolhidos, args.getNivelAgrecacaoPolitica());
			String queryFinal = aplicarFiltros(queryTotal, args);

			queries.add(queryFinal);

			if(LOGGER.isDebugEnabled()) {
				LOGGER.debug("Query para ano:\t" + ano);
				LOGGER.debug("Query fato:\t" + queryFato);
				LOGGER.debug("Query total:\t" + queryTotal);
				LOGGER.debug("Query total:\t" + queryFinal);
			}
		}

		String union = QueryBuilder.unionAll(queries);

		ResultSetWork ra = new ResultSetWork();

		ra.setQuery(union);
		session.doWork(ra);

		return ra;
	}

	String aplicarFiltros(String queryTotal, ArgumentosBusca args) {
		// por agora, apenas filtro de candidato... os outros podem ser feito
		// direto na fato.
		QueryBuilder qb = new QueryBuilder();
		List<String> filtroCandidato = args.getCandidados();
		if(filtroCandidato.size() > 0) {
			qb.select_()._star_()._from_().declareRef(queryTotal, "T")
				._where_().ref(CO_DIM_CANDIDATOS_TITULO, "T").in(filtroCandidato);
		} else {
			return queryTotal;
		}

		return qb.toString();
	}

	public InputStream doWorkResult(ArgumentosBusca args) throws CepespDataException {

		long start = System.currentTimeMillis();
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug(">>> doWorkResult");
		}

		final ResultSetWork ra = queryFato(args);
		final ResultSet rs = ra.getResultSet();

		if(LOGGER.isInfoEnabled()) {
			LOGGER.info("Consulta concluida. Tempo (s): " + (System.currentTimeMillis() - start)/1000.0);
		}

		InputStream is = null;
		try {
			final CSVBuilder csv = CSVBuilder.getInstance();
			csv.setSource(ra, rs);
			csv.start();

	        is = csv.getAsInputStream();

		} catch (IOException e) {
			LOGGER.error("IOException ao montar output.", e);
		} catch (RuntimeException e) {
			LOGGER.error("RuntimeException ao montar output.", e);
		}

		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("<<< doWorkResult");
		}

		return is;
	}

	public String getCargoByID(String codCargo) {
		String ret = null;

		try {
			QueryBuilder qb = new QueryBuilder();
			qb.select_().coluna(CO_DIM_CARGO_DS)
				._from_().declareRef(TB_DIM_CARGO, a)
				._where_().ref(CO_DIM_CARGO_CD, a)._eq_().valor(codCargo);

			Query query = getSession().createSQLQuery(qb.toString());
			ret = (String) query.uniqueResult();

		} catch (RuntimeException e) {
			LOGGER.error("Exception ao tentar obter cargo para id: " + codCargo, e);
			throw e;
		}

		return ret;
	}

	public List<Partido> getPartidosPorAnoList(String s) {
		return getPartidosPorAnoList(new String[] {s});
	}

	public List<Partido> getPartidosPorAnoList(String[] anosList) {
		List<Partido> partidos = new ArrayList<Partido>();
		List<String> partialQueries = new ArrayList<String>(anosList.length);

		for (String ano : anosList) {
			QueryBuilder qb = new QueryBuilder();
			qb.select_().colunas(CO_DIM_PARTIDOS_COD, CO_DIM_PARTIDOS_SIGLA)
				._from_().declareRef(TB_DIM_PARTIDOS, p)
				._where_().ref(CO_DIM_PARTIDOS_ANO, p)._eq_().valor(ano);

			partialQueries.add(qb.toString());
		}

		String queryStr = QueryBuilder.unionDistinct(partialQueries)
				+ _ORDER_BY_ + CO_DIM_PARTIDOS_COD;

		try {

			Query query = getSession().createSQLQuery(queryStr);
			List<Object[]> list = (List<Object[]>) query.list();

			for (Object[] item : list) {
				Partido p = new Partido();
				p.setCod((Integer) item[0]);
				p.setSigla(String.valueOf(item[1]));

				partidos.add(p);
			}

		} catch (RuntimeException e) {
			LOGGER.error("Exception ao tentar obter partidos por: " + Arrays.toString(anosList), e);
		}

		return partidos;
	}


	@SuppressWarnings("unchecked")
	private List<Par> getParList(Query query) {
		List<Par> pares = new ArrayList<Par>();
		List<Object[]> list = null;

		try {
			list = (List<Object[]>) query.list();

			for (Object[] item : list) {
				String chave = String.valueOf(item[0]);
				String valor = String.valueOf(item[1] );

				pares.add(new Par(chave, valor));
			}

		} catch (RuntimeException e) {
			LOGGER.error("Exception ao tentar executar query!", e);
		}

		return pares;
	}

	@SuppressWarnings("unchecked")
	private List<Par> getParChaveList(Query query) {
		List<Par> pares = new ArrayList<Par>();
		List<Object[]> list = null;

		try {
			list = (List<Object[]>) query.list();

			for (Object[] item : list) {
				String chave = String.valueOf(item[0]);
				String valor = String.valueOf(item[1]);

				pares.add(new Par(chave, valor + " (" + chave + ")"));
			}

		} catch (RuntimeException e) {
			LOGGER.error("Exception ao tentar executar query!", e);
		}

		return pares;
	}


	public List<Par> getMacroRegiaoList() {
		QueryBuilder qb = new QueryBuilder();

		qb.select_().colunas(CO_DIM_MACROREGIAO_COD, CO_DIM_MACROREGIAO_NOME)
			._from_().tabela(TB_DIM_MACROREGIAO);

		Query query = getSession().createSQLQuery(qb.toString());

		return getParList(query);
	}

	public List<Par> getEstadosList() {
		QueryBuilder qb = new QueryBuilder();

		qb.select_().colunas(CO_DIM_ESTADOS_ID, CO_DIM_ESTADOS_NOME)
			._from_().tabela(TB_DIM_ESTADOS);

		Query query = getSession().createSQLQuery(qb.toString());
		return getParList(query);
	}

	public List<Par> getMesoRegiaoList(String filtro) {
		QueryBuilder qb = new QueryBuilder();

		qb.select_().colunas(CO_DIM_MESOREGIAO_ID, CO_DIM_MESOREGIAO_NOME)
			._from_().tabela(TB_DIM_MESOREGIAO)
			._where_().coluna(CO_DIM_MESOREGIAO_NOME).like(filtro);


		Query query = getSession().createSQLQuery(qb.toString());
		return getParList(query);
	}

	public List<Par> getMicroRegiaoList(String filtro) {
		QueryBuilder qb = new QueryBuilder();

		qb.select_().colunas(CO_DIM_MICROREGIAO_ID, CO_DIM_MICROREGIAO_NOME)
			._from_().tabela(TB_DIM_MICROREGIAO)
			._where_().coluna(CO_DIM_MICROREGIAO_NOME).like(filtro);

		Query query = getSession().createSQLQuery(qb.toString());
		return getParList(query);
	}

	public List<Par> getMunicipioList(String filtro) {
		QueryBuilder qb = new QueryBuilder();

		qb.select_().coluna(CO_DIM_MUNICIPIO_COD).comma_()
			.concat(CO_DIM_MUNICIPIO_NOME, "'-'", CO_DIM_MUNICIPIO_SIGLA_UF)._as_().valor("des")
			._from_().tabela(TB_DIM_MUNICIPIO)
			._where_().coluna(CO_DIM_MUNICIPIO_NOME).like(filtro);

		Query query = getSession().createSQLQuery(qb.toString());
		return getParList(query);
	}

//	public List<Par> getCandidatosPorAnoList(String filtro, String ano) {
//
//		QueryBuilder qb = new QueryBuilder();
//
//		qb.select_().colunas(CO_DIM_CANDIDATOS_SURROGATEKEY, CO_DIM_CANDIDATOS_NOME)
//			._from_().tabela(TB_DIM_CANDIDATOS)
//			._where_().coluna(CO_DIM_CANDIDATOS_NOME).like(filtro);
//
//
//		Query query = getSession().createSQLQuery(qb.toString(ano));
//		return getParList(query);
//	}

	public List<Candidato> getCandidatosPorAnoList(String filtro, String ano) {
		String[] anos = {ano};
		return getCandidatosPorAnoList(filtro, anos, null);
	}


	public List<Candidato> getCandidatosPorAnoList(String filtro, String[] anos, String codCargo)
	{
		List<Candidato> candidatos = new ArrayList<Candidato>();
		List<String> partialQueries = new ArrayList<String>(anos.length);

		for (String ano : anos) {
			QueryBuilder qb = new QueryBuilder();

			qb.select_()._distinct_().colunas(CO_DIM_CANDIDATOS_TITULO, CO_DIM_CANDIDATOS_NOME)
				._from_().tabela(TB_DIM_CANDIDATOS)
				._where_().coluna(CO_DIM_CANDIDATOS_NOME).like(filtro)
                ._and_().coluna(CO_DIM_CANDIDATOS_CARGO_COD)._eq_().valor(codCargo);

			partialQueries.add(qb.toString(ano));
		}


		String queryStr = QueryBuilder.unionDistinct(partialQueries);

		Query query = getSession().createSQLQuery(queryStr);
		List<Object[]> datas = (List<Object[]>) query.list();

		for (Object[] row : datas) {
			Candidato c = new Candidato();
			c.setTitulo(String.valueOf(row[0]));
			c.setNome(String.valueOf(row[1]));
			candidatos.add(c);
		}

		return candidatos;
	}

	public List<Date> getDataCarga() {
		StringBuilder select = new StringBuilder();
		select.append("SELECT distinct data_etl FROM info_cepespdata.datas");

		Query query = getSession().createSQLQuery(select.toString());

		@SuppressWarnings("unchecked")
		List<Date> datas = (List<Date>)query.list();

		return datas;
	}

	public Date getDataCand(String carga, int ano) {
		StringBuilder select = new StringBuilder();
		select.append("select data_tse_cand from info_cepespdata.datas where data_etl = '" + carga + "' and ano_eleicao = " + ano);

		Query query = getSession().createSQLQuery(select.toString());

		List<Date> datas = (List<Date>)query.list();

		return datas.get(0);
	}

	public Date getDataVoto(String carga, int ano) {
		StringBuilder select = new StringBuilder();
		select.append("select data_tse_voto from info_cepespdata.datas where data_etl = '" + carga + "' and ano_eleicao = " + ano);

		Query query = getSession().createSQLQuery(select.toString());

		List<Date> datas = (List<Date>)query.list();

		return datas.get(0);
	}


}
