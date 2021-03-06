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

import static br.fgv.model.Tabela.*;
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
import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import br.fgv.model.Candidato;
import br.fgv.model.Partido;
import org.hibernate.Session;
import org.junit.BeforeClass;
import org.junit.Test;

import br.fgv.CepespDataException;
import br.fgv.business.AgregacaoPolitica;
import br.fgv.business.AgregacaoRegional;
import br.fgv.util.ArgumentosBusca;
import br.fgv.util.CSVBuilder;
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
		List<Integer> l = dao.getAnosDisponiveisList();
		assertTrue(l.size() > 0);
	}

	@Test
	public void testGetStringQueryDimPartido() {
		String[] empty = new String[0];
		String[] ab = {TB_DIM_PARTIDOS.getNome() + "." + CO_DIM_PARTIDOS_SIGLA};

		assertEquals("SELECT xxAnoEleicao AS \"anoEleicao\", turno AS \"turno\", voto_nominal, voto_legenda, voto_total FROM ( xxQueryFato ) as r",dao.getStringQueryDim(new ArgumentosBusca(), "xxQueryFato", "xxAnoEleicao", empty, AgregacaoPolitica.PARTIDO));
		assertEquals("SELECT xxAnoEleicao AS \"anoEleicao\", turno AS \"turno\", dim_partidos.sigla_Partido, voto_nominal, voto_legenda, voto_total " +
				"FROM ( xxQueryFato ) as r " +
				"left join dim_partidos on dim_partidos.cod_Partido = r.partido AND dim_partidos.ano = 'xxAnoEleicao'", dao.getStringQueryDim(new ArgumentosBusca(), "xxQueryFato", "xxAnoEleicao", ab, AgregacaoPolitica.PARTIDO));
	}

	@Test
	public void testGetStringQueryDimCandidato() {
		String[] empty = new String[0];
		String[] ab = {TB_DIM_PARTIDOS.getNome() + "." + CO_DIM_PARTIDOS_SIGLA};

		assertEquals("SELECT xxAnoEleicao AS \"anoEleicao\", turno AS \"turno\", voto_nominal, voto_total FROM ( xxQueryFato ) as r",dao.getStringQueryDim(new ArgumentosBusca(), "xxQueryFato", "xxAnoEleicao", empty, AgregacaoPolitica.CANDIDATO));
		assertEquals("SELECT xxAnoEleicao AS \"anoEleicao\", turno AS \"turno\", dim_partidos.sigla_Partido, voto_nominal, voto_total " +
				"FROM ( xxQueryFato ) as r " +
				"left join dim_partidos on dim_partidos.cod_Partido = r.partido AND dim_partidos.ano = 'xxAnoEleicao'", dao.getStringQueryDim(new ArgumentosBusca(), "xxQueryFato", "xxAnoEleicao", ab, AgregacaoPolitica.CANDIDATO));
		
	}
	
	@Test
	public void testGetStringQueryDimCandidatoDesc() {
		
		String[] ocup = {TB_DIM_CANDIDATOS.getNome() + "." + CO_DIM_CANDIDATOS_OCUPACAO_COD};
		
		assertEquals("SELECT xxAnoEleicao AS \"anoEleicao\", turno AS \"turno\", aux_candidatos_xxAnoEleicao.ocupacao_cod, voto_nominal, voto_total " +
				"FROM ( xxQueryFato ) as r " +
				"left join aux_candidatos_xxAnoEleicao on aux_candidatos_xxAnoEleicao.surrogatekey = r.candidato_sk", dao.getStringQueryDim(new ArgumentosBusca(), "xxQueryFato", "xxAnoEleicao", ocup, AgregacaoPolitica.CANDIDATO));
	}

	@Test
	public void testGetCargoByID() {
		assertEquals("Presidente",dao.getCargoByID("1"));
	}

	@Test
	public void testGetPartidosPorAnoList() {
		List<Partido> l = dao.getPartidosPorAnoList("2010");
		assertEquals(30,l.size());
		Partido p = l.get(0);
		assertNotEquals(p.getCod(), 0);
		assertTrue(p.getSigla().matches("^\\w+ \\(\\d+\\)$"));
	}

	@Test
	public void testGetMacroRegiaoList() {
		List<Par> l = dao.getMacroRegiaoList("%");
		assertTrue(l.size() < 10);
		assertTrue(l.size() >= 5);
		Par p = l.get(0);
		assertTrue(p.getChave().matches("^\\d+$"));
		assertTrue(p.getValor().matches("^\\w+$"));
	}

	@Test
	public void testGetEstadosList() {
		List<Par> l = dao.getEstadosList("%");
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
		assertTrue(l.size() < 6000);
		Par p = l.get(0);
		assertTrue(p.getChave().matches("^\\d+$"));
		assertTrue(p.getValor().matches("^.+$"));
	}

	@Test
	public void testCandidatosPorAnoList() {
		List<Candidato> l = dao.getCandidatosPorAnoList("A", "2010");
		assertTrue(l.size() > 20000);
		assertTrue(l.size() < 22000);
		Candidato p = l.get(0);
		assertNotNull(p);

		p = l.get(500);
		assertTrue(p.getTitulo().matches("^\\d+$"));
		assertTrue(p.getNome().matches("^.+$"));
		assertTrue(p.getNomeIdentificador().contains(p.getTitulo()));
	}

	@Test
	public void testCandidatosPorAnoListCargo() {
		String[] anos = {"2002", "2006"};
		List<Candidato> l = dao.getCandidatosPorAnoList("LUI", anos, "1");
		assertTrue(l.size() == 1);
	}

	@Test
	public void testApplicarFiltro() {
		ArgumentosBusca args = new ArgumentosBusca();

		assertEquals("abc", dao.aplicarFiltros("abc", args));

		String[] filtroCandidato = {"c1", "c2"};
		args.setCandidados(filtroCandidato);
		assertEquals("SELECT  *  FROM (abc) T WHERE T.titulo in (c1, c2) ", dao.aplicarFiltros("abc", args));
	}

	@Test
	public void testGetAnos() {
		assertEquals(5, dao.getAnosParaCargoList("1").size());
	}

	@Test
	public void testCompleto() throws CepespDataException, IOException {
		ArgumentosBusca ab = new ArgumentosBusca();
		String[] anos = {"2010"};
		ab.setAnoEleicao(anos);

		ab.setFiltroCargo("7");
		ab.setNivelAgrecacaoPolitica(AgregacaoPolitica.PARTIDO);
		ab.setNivelRegional(AgregacaoRegional.UF);
		ab.setTurno(1);

		String[] partidos = {"43"};
		ab.setPartidos(partidos);

		String[] campos = {"aux_estados.ibge"};
		ab.setCamposEscolhidos(campos);

		InputStream is = dao.doWorkResult(ab);
		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		String line = null;
		List<String> lines = new ArrayList<String>();
		while((line = in.readLine()) != null) {
		    lines.add(line);
		}

		is.close();
		assertEquals(27, lines.size());
	}

	@Test
	public void testCompletoCancelado() throws CepespDataException, IOException {
		ArgumentosBusca ab = new ArgumentosBusca();
		String[] anos = {"2010"};
		ab.setAnoEleicao(anos);

		ab.setFiltroCargo("7");
		ab.setNivelAgrecacaoPolitica(AgregacaoPolitica.PARTIDO);
		ab.setNivelRegional(AgregacaoRegional.UF);

		String[] partidos = {"43"};
		ab.setPartidos(partidos);

		String[] campos = {"aux_estados.ibge"};
		ab.setCamposEscolhidos(campos);

		InputStream is = dao.doWorkResult(ab);
		((CSVBuilder)is).stop();
		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		String line = null;
		List<String> lines = new ArrayList<String>();
		while((line = in.readLine()) != null) {
		    lines.add(line);
		}

		is.close();
		assertNotEquals(28, lines.size());
	}

	@Test
	public void testDatasCarga() throws CepespDataException, IOException {
		List<Date> datas = dao.getDataCarga();
		assertTrue(datas.size() > 0);

	}


	@Test
	public void testDatasCand() throws CepespDataException, IOException {
		Date data = dao.getDataCand(dao.getDataCarga().get(0).toString(), 2010);
		assertNotNull(data);

	}


	@Test
	public void testDatasVoto() throws CepespDataException, IOException {
		Date data = dao.getDataVoto(dao.getDataCarga().get(0).toString(), 2010);
		assertNotNull(data);

	}
}
