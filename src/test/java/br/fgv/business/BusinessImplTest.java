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
package br.fgv.business;

import static org.junit.Assert.*;

import java.util.List;

import br.fgv.util.ColumnField;
import org.hibernate.Session;
import org.junit.Test;

import br.fgv.dao.DaoFactoryImpl;
import br.fgv.dao.FactorySessionMySqlDB;
import br.fgv.util.Par;

public class BusinessImplTest {

	private BusinessImpl businessImpl;

	public BusinessImplTest() {
		Session session = FactorySessionMySqlDB.create();
		this.businessImpl = new BusinessImpl(new DaoFactoryImpl(session));
	}

	@Test
	public void testCamposDisponiveis() {
		FormResultAux t = this.businessImpl.getCamposDisponiveis("1", "1");
		assertNotNull(t);

		assertEquals(5, t.getFixedFields().size());
	}

	@Test
	public void testCamposDisponiveisVazio() {
		List<ColumnField> l = this.businessImpl.getCamposDisponiveis((AgregacaoRegional)null, null);
		assertNotNull(l);
		assertEquals(0, l.size());
	}

	@Test
	public void testCamposFixosVazio() {
		List<ColumnField> l = this.businessImpl.getCamposFixos(null, null);
		assertNotNull(l);
		assertEquals(0, l.size());
	}

	@Test
	public void testCamposDisponiveisPolitica() {
		List<ColumnField> l = this.businessImpl.getCamposDisponiveis(null,
				AgregacaoPolitica.PARTIDO);
		assertEquals(2, l.size());

		l = this.businessImpl.getCamposDisponiveis(null,
				AgregacaoPolitica.CANDIDATO);
		assertEquals(16, l.size());
	}

	@Test
	public void testCamposFixosPolitica() {
		List<ColumnField> l = this.businessImpl.getCamposFixos(null,
				AgregacaoPolitica.PARTIDO);
		assertEquals(4, l.size());

		l = this.businessImpl.getCamposFixos(null,
				AgregacaoPolitica.CANDIDATO);
		assertEquals(4, l.size());
	}

	@Test
	public void testCamposDisponiveisRegional() {
		List<ColumnField> l = this.businessImpl.getCamposDisponiveis(
				AgregacaoRegional.MACRO_REGIAO, null);
		assertEquals(1, l.size());

		l = this.businessImpl.getCamposDisponiveis(
				AgregacaoRegional.UF, null);
		assertEquals(2, l.size());

		l = this.businessImpl.getCamposDisponiveis(
				AgregacaoRegional.UF_ZONA, null);
		assertEquals(2, l.size()); // XXX: deveria ser 3?

		l = this.businessImpl.getCamposDisponiveis(
				AgregacaoRegional.MESO_REGIAO, null);
		assertEquals(1, l.size());

		l = this.businessImpl.getCamposDisponiveis(
				AgregacaoRegional.MICRO_REGIAO, null);
		assertEquals(1, l.size());

		l = this.businessImpl.getCamposDisponiveis(
				AgregacaoRegional.MUNICIPIO, null);
		assertEquals(5, l.size());
	}

	@Test
	public void testCamposFixosRegional() {
		List<ColumnField> l = this.businessImpl.getCamposFixos(
				AgregacaoRegional.MACRO_REGIAO, null);
		assertEquals(1, l.size());

		assertEquals("aux_macroregiao.cod_Macro", l.get(0).getKey());
		assertEquals("Macro-região: Código", l.get(0).getKey());

		l = this.businessImpl.getCamposFixos(
				AgregacaoRegional.UF, null);
		assertEquals(1, l.size());

		l = this.businessImpl.getCamposFixos(
				AgregacaoRegional.UF_ZONA, null);
		assertEquals(2, l.size());

		l = this.businessImpl.getCamposFixos(
				AgregacaoRegional.MESO_REGIAO, null);
		assertEquals(2, l.size());

		l = this.businessImpl.getCamposFixos(
				AgregacaoRegional.MICRO_REGIAO, null);
		assertEquals(2, l.size());

		l = this.businessImpl.getCamposFixos(
				AgregacaoRegional.MUNICIPIO, null);
		assertEquals(2, l.size());
	}

	@Test
	public void testCamposDisponiveisCombinado() {
		List<ColumnField> l = this.businessImpl.getCamposDisponiveis(
				AgregacaoRegional.MACRO_REGIAO, AgregacaoPolitica.CANDIDATO);
		assertEquals(17, l.size());
	}

	@Test
	public void testCamposFixosCombinado() {
		List<ColumnField> l = this.businessImpl.getCamposFixos(
				AgregacaoRegional.MACRO_REGIAO, AgregacaoPolitica.CANDIDATO);
		assertEquals(5, l.size());
	}

	@Test
	public void testNomeArquivo01() {
		assertEquals("2099_Presidente_MacroRegiao_Partido.csv", businessImpl.getSugestaoNomeArquivo("2099", "1", "1", "1"));
		assertEquals("2099_DeputadoFederal_MacroRegiao_Partido.csv", businessImpl.getSugestaoNomeArquivo("2099", "1", "1", "6"));
		assertEquals("2099_DeputadoFederal_MesoRegiao_Partido.csv", businessImpl.getSugestaoNomeArquivo("2099", "4", "1", "6"));

	}

	@Test
	public void testMatrizETL() {
		String[][] m = businessImpl.getDadosCargaCand();

		assertNotNull(m);
	}
}
