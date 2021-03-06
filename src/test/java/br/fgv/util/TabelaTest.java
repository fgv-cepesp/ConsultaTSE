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
package br.fgv.util;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.hibernate.Session;
import org.junit.BeforeClass;
import org.junit.Test;

import br.fgv.CepespDataException;
import br.fgv.dao.FactorySessionMySqlDB;
import br.fgv.dao.ResultSetWork;
import br.fgv.model.Coluna;
import br.fgv.model.Tabela;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class TabelaTest {

	private static Session session;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		session = FactorySessionMySqlDB.create();
	}

	@Test
	public void testTabelaFatoNomesColunas() {
		test(Tabela.TB_FACT_VOTOS_MUN);
		test(Tabela.TB_DIM_PARTIDOS);
		test(Tabela.TB_DIM_CANDIDATOS);
		test(Tabela.TB_DIM_CARGO);
		test(Tabela.TB_DIM_ESTADOS);
		test(Tabela.TB_DIM_MACROREGIAO);
		test(Tabela.TB_DIM_MESOREGIAO);
		test(Tabela.TB_DIM_MICROREGIAO);
		test(Tabela.TB_DIM_MUNICIPIO);
//		test(Tabela.TB_DIM_VOTAVEIS);
		test(Tabela.TB_SIS_ANO_CARGO);
		test(Tabela.TB_SIS_ANOS);
		test(Tabela.TB_DIM_LEGENDAS);

	}

	@Test
	public void testRelacionamento() {
		assertEquals(
				"dim_partidos.cod_Partido = r.partido AND dim_partidos.ano = '#ANO_ELEICAO#'",
				Tabela.TB_DIM_PARTIDOS.getRelacao());
		assertEquals(
				"aux_candidatos_#ANO_ELEICAO#.surrogatekey = r.candidato_sk",
				Tabela.TB_DIM_CANDIDATOS.getRelacao());
		assertEquals(
				"aux_macroregiao.cod_Macro = r.macro",
				Tabela.TB_DIM_MACROREGIAO.getRelacao());
		assertEquals(
				"aux_mesoregiao", "aux_mesoregiao.id = r.meso",
				Tabela.TB_DIM_MESOREGIAO.getRelacao());
		assertEquals(
				"aux_microregiao.id = r.micro",
				Tabela.TB_DIM_MICROREGIAO.getRelacao());
		assertEquals(
				"aux_estados.id = r.uf",
				Tabela.TB_DIM_ESTADOS.getRelacao());
		assertEquals(
				"aux_municipio.cod = r.cargo_cod",
				Tabela.TB_DIM_MUNICIPIO.getRelacao());
	}

	@Test
	public void testCSVHelp() throws CepespDataException, IOException {
		File csv = Tabela.getHelpCSV();

		assertNotNull(csv);

		String content = Files.toString(csv, Charsets.UTF_8);

		assertTrue(content.length() > 0);
	}

	private void test(Tabela tabela) {
		SortedSet<String> colunasCodigo = new TreeSet<String>();
		for (Coluna tc : tabela.getColunas()) {
			colunasCodigo.add(tc.getNome());
		}

		SortedSet<String> colunasDB = new TreeSet<String>(getNomeColunasDB(tabela.getNome().replaceAll(Tabela.HOLDER_ANO_ELEICAO, "2010")));

		colunasDB.remove("turno_c"); // renaned...
		assertEquals( colunasCodigo, colunasDB);
	}

	private Set<String> getNomeColunasDB(String tabela) {
		String query = "select * from " + tabela + " LIMIT 0,10;";

		ResultSetWork ra = new ResultSetWork();
		ra.setQuery(query);
		session.doWork(ra);
		return new HashSet<String>(Arrays.asList(ra.getColumnsName()));
	}

}
