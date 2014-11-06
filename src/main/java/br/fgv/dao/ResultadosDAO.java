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
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import br.fgv.CepespDataException;
import br.fgv.business.AgregacaoPolitica;
import br.fgv.business.AgregacaoRegional;
import br.fgv.model.Coluna;
import br.fgv.model.Tabela;
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

	@SuppressWarnings("unchecked")
	private List<Integer> getAnosDisponiveis() {
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



	public List<Par> getAnosDisponiveisList() {
		List<Par> pares = new ArrayList<Par>();
		List<Integer> list = null;

		list = getAnosDisponiveis();

		for (Integer item : list) {
			String i = String.valueOf(item);
			pares.add(new Par(i, i));
		}

		return pares;
	}

	public List<Par> getCargosList() {
		List<Integer> anos = getAnosDisponiveis();

		List<String> queries = new ArrayList<String>();

		for (Integer ano : anos) {
			queries.add(getQueryCargosPorAnoList(Integer.toString(ano)));
		}

		String completeQuery = QueryBuilder.unionDistinct(queries)
				+ _ORDER_BY_ + CO_SIS_ANO_CARGO_COD_CARGO;

		List<Par> pares = new ArrayList<Par>();
		List<Object[]> list = null;

		try {

			Query query = getSession().createSQLQuery(completeQuery);

			list = (List<Object[]>) query.list();

			for (Object[] item : list) {
				String chave = String.valueOf(item[0]);
				String valor = String.valueOf(item[1]);

				pares.add(new Par(chave, valor));
			}
		} catch (RuntimeException e) {
			LOGGER.error("Falhou ao tentar obter cargos disponiveis.", e);
		}

		return pares;
	}

	@SuppressWarnings("unchecked")
	public List<Par> getCargosPorAnoList(String ano) {
		List<Par> pares = new ArrayList<Par>();
		List<Object[]> list = null;

		try {
			String queryString = getQueryCargosPorAnoList(ano);

			Query query = getSession().createSQLQuery(queryString);

			list = (List<Object[]>) query.list();

			for (Object[] item : list) {
				String chave = String.valueOf(item[0]);
				String valor = String.valueOf(item[1]);

				pares.add(new Par(chave, valor));
			}

		} catch (RuntimeException e) {
			LOGGER.error("Falhou ao tentar obter cargos disponiveis por ano.", e);
		}

		return pares;
	}

	public String getQueryCargosPorAnoList(String ano) {
		QueryBuilder qb = new QueryBuilder();

		qb.select_().colunas(CO_SIS_ANO_CARGO_COD_CARGO, CO_DIM_CARGO_DS)
			._from_().declareRef(TB_SIS_ANO_CARGO, s).comma_().declareRef(TB_DIM_CARGO, a)
			._where_().ref(CO_SIS_ANO_CARGO_COD_CARGO, s)._eq_().ref(CO_DIM_CARGO_CD, a)
				._and_().ref(CO_SIS_ANO_CARGO_ANO, s)._eq_().valor(ano);

		return qb.toString();
	}

	public List<Par> getAnosParaCargoList(String cargo) {
		List<Par> pares = new ArrayList<Par>();
		List<Integer> list = null;

		try {
			QueryBuilder qb = new QueryBuilder();

			qb.select_()._distinct_().colunas(CO_SIS_ANO_CARGO_ANO)
				._from_().tabela(TB_SIS_ANO_CARGO)
				._where_().coluna(CO_SIS_ANO_CARGO_COD_CARGO)._eq_().valor(cargo)
				._order_by_().coluna(CO_SIS_ANO_CARGO_ANO);


			Query query = getSession().createSQLQuery(qb.toString());

			list = (List<Integer>) query.list();

			for (Integer item : list) {
				String chave = String.valueOf(item);
				String valor = String.valueOf(item);

				pares.add(new Par(chave, valor));
			}

		} catch (RuntimeException e) {
			LOGGER.error("Falhou ao tentar obter cargos disponiveis por ano.", e);
		}

		return pares;
	}

	QueryBuilder appendFiltroPolitico(QueryBuilder query, AgregacaoPolitica agregacaoPolitica,
			String[] filtroPolitico) {

		// nesses casos o nivel é fora do intervalo...
		if (agregacaoPolitica == null || filtroPolitico == null
				|| filtroPolitico.length == 0) {
			// do nothing
		} else {
			query._and_().valor(agregacaoPolitica.getNome()).in(filtroPolitico);
		}

		return query;
	}

	QueryBuilder appendFiltroRegional(QueryBuilder query, AgregacaoRegional agregacaoRegional, String[] filtroRegional) {

		if(agregacaoRegional == null || filtroRegional == null || filtroRegional.length == 0){
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
		qb._where_().eq(CO_FACT_VOTOS_MUN_COD_CARGO, args.getFiltroCargo());

			appendFiltroRegional(qb, args.getNivelFiltroRegional(), args.getFiltroRegional());
			appendFiltroPolitico(qb, AgregacaoPolitica.PARTIDO, args.getFiltroPartido());
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

		for (String campo : camposEscolhidos) {
			String tabela = campo.substring(0, campo.indexOf("."));
			if(Tabela.byName(tabela) != null)
				tabelasARelacionar.add(tabela);
		}


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
		for (String c : camposEscolhidos) {
			if(c.startsWith("zona.")) {
				camposFiltrados.add("zona");
			} else if(c.startsWith("votos.")) {
				// nao inclui
			} else {
				camposFiltrados.add(c);
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

		String[] anos = args.getAnoEleicao();

		List<String> queries = new ArrayList<String>();

		for (String ano : anos) {
		    String queryFato = getStringQueryFato(args, ano);
			String queryTotal = getStringQueryDim(queryFato, ano,
					args.getCamposEscolhidos(), args.getNivelAgrecacaoPolitica());
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
		String[] filtroCandidato = args.getFiltroCandidato();
		if(filtroCandidato != null && filtroCandidato.length > 0) {
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
			if(ret == null || ret.isEmpty()) {
				LOGGER.error("Nao pude obter cargo para id: " + codCargo);
			}

		} catch (RuntimeException e) {
			LOGGER.error("Exception ao tentar obter cargo para id: " + codCargo, e);
		}

		return ret;
	}

	public List<Par> getPartidosPorAnoList(String ano) {
		String[] anos = {ano};
		return getPartidosPorAnoList(anos);
	}

	@SuppressWarnings("unchecked")
	public List<Par> getPartidosPorAnoList(String[] anosList) {
		List<Par> pares = new ArrayList<Par>();
		List<Object[]> list = null;

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

			list = (List<Object[]>) query.list();

			for (Object[] item : list) {
				String chave = String.valueOf(item[0]);
				String valor = String.valueOf(item[1] + " (" + item[0] + ")");

				pares.add(new Par(chave, valor));
			}

		} catch (RuntimeException e) {
			LOGGER.error("Exception ao tentar obter partidos por: " + Arrays.toString(anosList), e);
		}

		return pares;
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

	public List<Par> getCandidatosPorAnoList(String filtro, String ano) {
		String[] anos = {ano};
		return getCandidatosPorAnoList(filtro, anos, null);
	}

	public List<Par> getCandidatosPorAnoList(String filtro, String[] anos, String codCargo) {

		List<String> partialQueries = new ArrayList<String>(anos.length);

		for (String ano : anos) {
			QueryBuilder qb = new QueryBuilder();

			qb.select_()._distinct_().colunas(CO_DIM_CANDIDATOS_TITULO, CO_DIM_CANDIDATOS_NOME)
				._from_().tabela(TB_DIM_CANDIDATOS)
				._where_().coluna(CO_DIM_CANDIDATOS_NOME).like(filtro);
			if(codCargo != null && codCargo.length() > 0) {
				qb._and_().coluna(CO_DIM_CANDIDATOS_CARGO_COD)._eq_().valor(codCargo);
			}
			partialQueries.add(qb.toString(ano));
		}


		String queryStr = QueryBuilder.unionDistinct(partialQueries);


		Query query = getSession().createSQLQuery(queryStr);
		return getParChaveList(query);
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
