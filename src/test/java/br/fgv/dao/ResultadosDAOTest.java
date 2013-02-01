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

import static br.fgv.model.Tabela.CO_DIM_PARTIDOS_SIGLA;
import static br.fgv.model.Tabela.CO_FACT_VOTOS_MUN_CANDIDATO_SK;
import static br.fgv.model.Tabela.CO_FACT_VOTOS_MUN_COD_MUN;
import static br.fgv.model.Tabela.CO_FACT_VOTOS_MUN_MACRO;
import static br.fgv.model.Tabela.CO_FACT_VOTOS_MUN_MESO;
import static br.fgv.model.Tabela.CO_FACT_VOTOS_MUN_MICRO;
import static br.fgv.model.Tabela.CO_FACT_VOTOS_MUN_PARTIDO;
import static br.fgv.model.Tabela.CO_FACT_VOTOS_MUN_UF;
import static br.fgv.model.Tabela.TB_DIM_PARTIDOS;
import static br.fgv.util.QueryBuilder.IN;
import static br.fgv.util.QueryBuilder.SELECT_;
import static br.fgv.util.QueryBuilder._AND_;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.hibernate.Session;
import org.junit.BeforeClass;
import org.junit.Test;

import br.fgv.business.AgregacaoPolitica;
import br.fgv.business.AgregacaoRegional;
import br.fgv.util.ArgumentosBusca;
import br.fgv.util.Par;
import br.fgv.util.QueryBuilder;

public class ResultadosDAOTest {

	private static Session session;
	private static ResultadosDAO dao;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		session = FactorySessionMySqlDB.create();
		dao = new DaoFactoryImpl(session).getResultadosDAO();
	}
	
	@Test
	public void testGetAnosDisponiveisList() {
		List<Par> l = dao.getAnosDisponiveisList();
		assertTrue(l.size() > 0);
	}
	
	@Test
	public void testCargosDisponiveisList() {
		List<Par> l = dao.getCargosPorAnoList("2010");
		assertEquals(6, l.size());
		
		l = dao.getCargosPorAnoList("2008");
		assertEquals(4, l.size());
	}
	
	@Test
	public void testGetStringFiltroPolitico() {
		
		assertEquals("", dao.appendFiltroPolitico(new QueryBuilder(), AgregacaoPolitica.CANDIDATO, new String[0]).toString());
		
		String[] filtroPolitico = {"aaa", "bbb"};
		
		assertEquals("", dao.appendFiltroPolitico(new QueryBuilder(), null, filtroPolitico).toString());
		assertEquals(_AND_ + CO_FACT_VOTOS_MUN_PARTIDO + IN(filtroPolitico), dao.appendFiltroPolitico(new QueryBuilder(), AgregacaoPolitica.PARTIDO, filtroPolitico).toString());
		assertEquals(_AND_ + CO_FACT_VOTOS_MUN_CANDIDATO_SK + IN(filtroPolitico), dao.appendFiltroPolitico(new QueryBuilder(), AgregacaoPolitica.CANDIDATO, filtroPolitico).toString());
	}
	
	@Test
	public void testAppendFiltroRegional() {
		
		assertEquals("", dao.appendFiltroRegional(new QueryBuilder(), AgregacaoRegional.MACRO_REGIAO, new String[0]).toString());
		
		String[] filtroRegional = {"aaa", "bbb"};
		assertEquals("", dao.appendFiltroRegional(new QueryBuilder(), null, filtroRegional).toString());
		assertEquals(_AND_ + CO_FACT_VOTOS_MUN_MACRO.getNome() + IN(filtroRegional), dao.appendFiltroRegional(new QueryBuilder(), AgregacaoRegional.MACRO_REGIAO, filtroRegional).toString());
		assertEquals(_AND_ + CO_FACT_VOTOS_MUN_UF.getNome() + IN(filtroRegional), dao.appendFiltroRegional(new QueryBuilder(), AgregacaoRegional.UF, filtroRegional).toString());
		assertEquals(_AND_ + CO_FACT_VOTOS_MUN_UF.getNome() + IN(filtroRegional), dao.appendFiltroRegional(new QueryBuilder(), AgregacaoRegional.UF_ZONA, filtroRegional).toString());
		assertEquals(_AND_ + CO_FACT_VOTOS_MUN_MESO.getNome() + IN(filtroRegional), dao.appendFiltroRegional(new QueryBuilder(), AgregacaoRegional.MESO_REGIAO, filtroRegional).toString());
		assertEquals(_AND_ + CO_FACT_VOTOS_MUN_MICRO.getNome() + IN(filtroRegional), dao.appendFiltroRegional(new QueryBuilder(), AgregacaoRegional.MICRO_REGIAO, filtroRegional).toString());
		assertEquals(_AND_ + CO_FACT_VOTOS_MUN_COD_MUN.getNome() + IN(filtroRegional), dao.appendFiltroRegional(new QueryBuilder(), AgregacaoRegional.MUNICIPIO, filtroRegional).toString());
		
	}
	
	@Test
	public void testGetStringQueryFato() {
		String[] empty = new String[0];
		
		String[] ab = {"aaa", "bbb"};
		String[] cd = {"ccc", "ddd"};
		String[] ef = {"eee", "fff"};
		
		ArgumentosBusca args = new ArgumentosBusca();
		args.setAnoEleicao("2010");
		args.setFiltroCargo("xxFiltroCargo");
		args.setNivelAgrecacaoPolitica(AgregacaoPolitica.fromInt("1"));
		args.setNivelRegional(AgregacaoRegional.fromInt("1"));
		
		args.setFiltroCandidato(empty);
		args.setFiltroPartido(empty);
		args.setFiltroRegional(empty);
		
		
		assertEquals(SELECT_ + "macro, partido, " +
				"sum(if( tipo_votavel = 1, qnt_votos, 0)) as voto_nominal, " +
				"sum(if( tipo_votavel = 4, qnt_votos, 0)) as voto_legenda, " +
				"sum(qnt_votos) as voto_total " +
				"FROM voto_mun_2010 " +
				"WHERE cod_cargo = xxFiltroCargo group by macro, partido order by macro, partido", 
				dao.getStringQueryFato(args));
		
		args.setNivelRegional(AgregacaoRegional.fromInt("2"));

		assertEquals(SELECT_ + "uf, partido, " +
				"sum(if( tipo_votavel = 1, qnt_votos, 0)) as voto_nominal, " +
				"sum(if( tipo_votavel = 4, qnt_votos, 0)) as voto_legenda, " +
				"sum(qnt_votos) as voto_total " +
				"FROM voto_mun_2010 " +
				"WHERE cod_cargo = xxFiltroCargo group by uf, partido order by uf, partido", 
				dao.getStringQueryFato(args));
		
		args.setNivelAgrecacaoPolitica(AgregacaoPolitica.CANDIDATO);
		
		assertEquals(SELECT_ + "uf, candidato_sk, " +
				"sum(if( tipo_votavel = 1, qnt_votos, 0)) as voto_nominal " +
				"FROM voto_mun_2010 " +
				"WHERE cod_cargo = xxFiltroCargo group by uf, candidato_sk order by uf, candidato_sk", 
				dao.getStringQueryFato(args));
		
		assertEquals(SELECT_ + "uf, candidato_sk, " +
				"sum(if( tipo_votavel = 1, qnt_votos, 0)) as voto_nominal " +
				"FROM voto_mun_2010 " +
				"WHERE cod_cargo = xxFiltroCargo group by uf, candidato_sk order by uf, candidato_sk", 
				dao.getStringQueryFato(args));
		
		args.setFiltroCandidato(ef);
		args.setFiltroPartido(cd);
		args.setFiltroRegional(ab);
		
		assertEquals(SELECT_ + "uf, candidato_sk, " +
				"sum(if( tipo_votavel = 1, qnt_votos, 0)) as voto_nominal " +
				"FROM voto_mun_2010 " +
				"WHERE cod_cargo = xxFiltroCargo " +
				"AND uf in (aaa, bbb)  AND partido in (ccc, ddd)  AND candidato_sk in (eee, fff)  " +
				"group by uf, candidato_sk order by uf, candidato_sk", 
				dao.getStringQueryFato(args));
	}
	
	@Test
	public void testGetStringQueryDimPartido() {
		String[] empty = new String[0];
		String[] ab = {TB_DIM_PARTIDOS.getNome() + "." + CO_DIM_PARTIDOS_SIGLA};
		
		assertEquals("SELECT voto_nominal, voto_legenda, voto_total FROM ( xxQueryFato ) as r",dao.getStringQueryDim("xxQueryFato", "xxAnoElecicao", empty, AgregacaoPolitica.PARTIDO));
		assertEquals("SELECT dim_partidos.sigla_Partido, voto_nominal, voto_legenda, voto_total " +
				"FROM ( xxQueryFato ) as r " +
				"left join dim_partidos on dim_partidos.cod_Partido = r.partido AND dim_partidos.ano = 'xxAnoEleicao'", dao.getStringQueryDim("xxQueryFato", "xxAnoEleicao", ab, AgregacaoPolitica.PARTIDO));		
	}
	
	@Test
	public void testGetStringQueryDimCandidato() {
		String[] empty = new String[0];
		String[] ab = {TB_DIM_PARTIDOS.getNome() + "." + CO_DIM_PARTIDOS_SIGLA};
		
		assertEquals("SELECT voto_nominal FROM ( xxQueryFato ) as r",dao.getStringQueryDim("xxQueryFato", "xxAnoElecicao", empty, AgregacaoPolitica.CANDIDATO));
		assertEquals("SELECT dim_partidos.sigla_Partido, voto_nominal " +
				"FROM ( xxQueryFato ) as r " +
				"left join dim_partidos on dim_partidos.cod_Partido = r.partido AND dim_partidos.ano = 'xxAnoEleicao'", dao.getStringQueryDim("xxQueryFato", "xxAnoEleicao", ab, AgregacaoPolitica.CANDIDATO));		
	}
	
	@Test
	public void testGetCargoByID() {
		assertEquals("Presidente",dao.getCargoByID("1"));
	}
	
	@Test
	public void testGetPartidosPorAnoList() {
		List<Par> l = dao.getPartidosPorAnoList("2010");
		assertEquals(30,l.size());
		Par p = l.get(0);
		assertTrue(p.getChave().matches("^\\d+$"));
		assertTrue(p.getValor().matches("^\\w+$"));
	}
	
	@Test
	public void testGetMacroRegiaoList() {
		List<Par> l = dao.getMacroRegiaoList();
		assertTrue(l.size() < 10);
		assertTrue(l.size() >= 5);
		Par p = l.get(0);
		assertTrue(p.getChave().matches("^\\d+$"));
		assertTrue(p.getValor().matches("^\\w+$"));
	}
	
	@Test
	public void testGetEstadosList() {
		List<Par> l = dao.getEstadosList();
		assertTrue(l.size() > 20);
		assertTrue(l.size() < 30);
		Par p = l.get(0);
		assertTrue(p.getChave().matches("^\\d+$"));
		assertTrue(p.getValor().matches("^\\w+$"));
	}
	
	@Test
	public void testGetMesoRegiaoList() {
		List<Par> l = dao.getMesoRegiaoList("A");
		assertTrue(l.size() > 150);
		assertTrue(l.size() < 200);
		Par p = l.get(0);
		assertTrue(p.getChave().matches("^\\d+$"));
		assertTrue(p.getValor().matches("^.+$"));
	}
	
	@Test
	public void testGetMicroRegiaoList() {
		List<Par> l = dao.getMicroRegiaoList("A");
		assertTrue(l.size() > 500);
		assertTrue(l.size() < 600);
		Par p = l.get(0);
		assertTrue(p.getChave().matches("^\\d+$"));
		assertTrue(p.getValor().matches("^.+$"));
	}
	
	@Test
	public void testGetMunicipioList() {
		List<Par> l = dao.getMunicipioList("A");
		assertTrue(l.size() > 4500);
		assertTrue(l.size() < 5000);
		Par p = l.get(0);
		assertTrue(p.getChave().matches("^\\d+$"));
		assertTrue(p.getValor().matches("^.+$"));
	}
	
	@Test
	public void testCandidatosPorAnoList() {
		List<Par> l = dao.getCandidatosPorAnoList("A", "2010");
		assertTrue(l.size() > 22000);
		assertTrue(l.size() < 25000);
		Par p = l.get(0);
		assertTrue(p.getChave().matches("^\\d+$"));
		assertTrue(p.getValor().matches("^.+$"));
	}
}
