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

import static br.fgv.model.Tabela.CO_DIM_CANDIDATOS_NOME;
import static br.fgv.model.Tabela.CO_DIM_CANDIDATOS_SURROGATEKEY;
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
import static br.fgv.util.QueryBuilder.a;
import static br.fgv.util.QueryBuilder.p;
import static br.fgv.util.QueryBuilder.s;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import br.fgv.CepespDataException;
import br.fgv.business.AgregacaoPolitica;
import br.fgv.business.AgregacaoRegional;
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
	public List<Par> getAnosDisponiveisList() {
		List<Par> pares = new ArrayList<Par>();
		List<Integer> list = null;

		try {
			QueryBuilder qb = new QueryBuilder();
			
			qb.select_().coluna(CO_SIS_ANOS_ANO)
				._from_().tabela(TB_SIS_ANOS)
				._order_by_().coluna(CO_SIS_ANOS_ANO);

			Query query = getSession().createSQLQuery(qb.toString());

			list = (List<Integer>) query.list();

			for (Integer item : list) {
				String i = String.valueOf(item);
				pares.add(new Par(i, i));
			}

		} catch (RuntimeException e) {
			LOGGER.error("Falhou ao tentar obter anos disponiveis.", e);
		}

		return pares;
	}

	@SuppressWarnings("unchecked")
	public List<Par> getCargosPorAnoList(String ano) {
		List<Par> pares = new ArrayList<Par>();
		List<Object[]> list = null;

		try {
			QueryBuilder qb = new QueryBuilder();
			
			qb.select_().colunas(CO_SIS_ANO_CARGO_COD_CARGO, CO_DIM_CARGO_DS)
				._from_().declareRef(TB_SIS_ANO_CARGO, s).comma_().declareRef(TB_DIM_CARGO, a)
				._where_().ref(CO_SIS_ANO_CARGO_COD_CARGO, s)._eq_().ref(CO_DIM_CARGO_CD, a)
					._and_().ref(CO_SIS_ANO_CARGO_ANO, s)._eq_().valor(ano);
			
			Query query = getSession().createSQLQuery(qb.toString());

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
			query._and_().valor(agregacaoRegional.getNome()).in(filtroRegional);
		}
		
		return query;
	}
	
	
	String getStringQueryFato(ArgumentosBusca args) {
		
		String reg = args.getNivelRegional().getNome();
		String pol = args.getNivelAgrecacaoPolitica().getNome();
		
		QueryBuilder qb = new QueryBuilder();

		// SELECT
		qb.select_().comma(reg, pol);
		qb.comma_().sum(IFF( EQ(CO_FACT_VOTOS_MUN_TIPO_VOTAVEL, VOTO_NOMINAL_COD), CO_FACT_VOTOS_MUN_QNT_VOTOS, 0))._as_().valor(VOTO_NOMINAL);
		if(AgregacaoPolitica.PARTIDO.equals(args.getNivelAgrecacaoPolitica())) {
			  qb.comma_().sum(IFF( EQ(CO_FACT_VOTOS_MUN_TIPO_VOTAVEL, VOTO_LEGENDA_COD), CO_FACT_VOTOS_MUN_QNT_VOTOS, 0))._as_().valor(VOTO_LEGENDA)
				.comma_().sum(CO_FACT_VOTOS_MUN_QNT_VOTOS)._as_().valor(VOTO_TOTAL);
		}
		qb._from_().tabela(TB_FACT_VOTOS_MUN);

		// WHERE
		qb._where_().eq(CO_FACT_VOTOS_MUN_COD_CARGO, args.getFiltroCargo());
		
			appendFiltroRegional(qb, args.getNivelRegional(), args.getFiltroRegional());
			appendFiltroPolitico(qb, AgregacaoPolitica.PARTIDO, args.getFiltroPartido());
			appendFiltroPolitico(qb, AgregacaoPolitica.CANDIDATO, args.getFiltroCandidato());
		
		// GROUP BY
		qb._group_by_().comma(reg, pol)._order_by_().comma(reg, pol);

		return qb.toString(args.getAnoEleicao());
	}

	String getStringQueryDim(String queryFato, String anoEleicao,
			String[] camposEscolhidos, AgregacaoPolitica agregacaoPolitica) {
		
		Set<String> tabelasARelacionar = new HashSet<String>(camposEscolhidos.length);
		for (String campo : camposEscolhidos) {
			tabelasARelacionar.add(campo.substring(0, campo.indexOf(".")));
		}
		
		QueryBuilder qb = new QueryBuilder();
		qb.select_().commaWithTrailing((Object[])camposEscolhidos).comma(agregacaoPolitica.getColunas())
			._from_().par(queryFato)._as_().valor(REF_FACT);
		
		for (String nomeTabela : tabelasARelacionar) {
			Tabela tabela = Tabela.byName(nomeTabela);
	        if(tabela != null && tabela.getRelacao() != null) {
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

	    String queryFato = getStringQueryFato(args);
	    
	    
		String queryTotal = getStringQueryDim(queryFato, args.getAnoEleicao(),
				args.getCamposEscolhidos(), args.getNivelAgrecacaoPolitica());

		ResultSetWork ra = new ResultSetWork();

		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("Query fato:\t" + queryFato);
			LOGGER.debug("Query total:\t" + queryTotal);
		}

		ra.setQuery(queryTotal);
		session.doWork(ra);
		
		return ra;
	}

	public File doWorkResult(ArgumentosBusca args) throws CepespDataException {
		
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug(">>> doWorkResult");
		}
		
		File tmpFile = null;
		
		ResultSetWork ra = queryFato(args);

		ResultSet rs = ra.getResultSet();
		
		try {
			int colCount = rs.getMetaData().getColumnCount();
			String ano = args.getAnoEleicao();
			
			CSVBuilder csv = CSVBuilder.createTemp();
			
			csv.elemento("anoEleicao")
				.elemento(ra.getColumnsName())
				.linha();
			
			while (rs.next()) {
				csv.elemento(ano);
				for (int i = 0; i < colCount; i++) {
					csv.elemento(rs.getString(i + 1));
				}
				
				csv.linha();
			}
			
			ra.close();
			tmpFile = csv.finalisa();
			
		} catch (IOException e) {
			LOGGER.error("IOException ao montar output.", e);
		} catch (SQLException e) {
			LOGGER.error("SQLException ao montar output.", e);
		} catch (RuntimeException e) {
			LOGGER.error("RuntimeException ao montar output.", e);
		}
		
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("<<< doWorkResult");
		}
		return tmpFile;
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
	
	@SuppressWarnings("unchecked")
	public List<Par> getPartidosPorAnoList(String ano) {
		List<Par> pares = new ArrayList<Par>();
		List<Object[]> list = null;

		try {
			QueryBuilder qb = new QueryBuilder();
			qb.select_().colunas(CO_DIM_PARTIDOS_COD, CO_DIM_PARTIDOS_SIGLA)
				._from_().declareRef(TB_DIM_PARTIDOS, p)
				._where_().ref(CO_DIM_PARTIDOS_ANO, p)._eq_().valor(ano);
			
			Query query = getSession().createSQLQuery(qb.toString());

			list = (List<Object[]>) query.list();

			for (Object[] item : list) {
				String chave = String.valueOf(item[0]);
				String valor = String.valueOf(item[1] );

				pares.add(new Par(chave, valor));
			}

		} catch (RuntimeException e) {
			LOGGER.error("Exception ao tentar obter partidos por: " + ano, e);
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

	public List<Par> getCandidatosPorAnoList(String filtro, String ano) {
		// XXX: nao usa ano!
		QueryBuilder qb = new QueryBuilder();
		
		qb.select_().colunas(CO_DIM_CANDIDATOS_SURROGATEKEY, CO_DIM_CANDIDATOS_NOME)
			._from_().tabela(TB_DIM_CANDIDATOS)
			._where_().coluna(CO_DIM_CANDIDATOS_NOME).like(filtro);
		
		
		Query query = getSession().createSQLQuery(qb.toString(ano));
		return getParList(query);
	}



}
