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
package br.fgv.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import br.fgv.util.Par;

public class DadosAuxiliaresTest {


	@Test
	public void testGetMeses() {
		List<Par> l = DadosAuxiliares.getMeses();
		assertEquals(13, l.size());
		Par p = l.get(1);
		assertTrue(p.getChave().matches("^\\d+$"));
		assertTrue(p.getValor().matches("^.+$"));
	}


	@Test
	public void testGetEscolaridade() {
		List<Par> l = DadosAuxiliares.getEscolaridade();
		assertEquals(11, l.size());
		Par p = l.get(1);
		assertTrue(p.getChave().matches("^\\d+$"));
		assertTrue(p.getValor().matches("^.+$"));
	}


	@Test
	public void testGetCargoPolitico() {
		List<Par> l = DadosAuxiliares.getCargoPolitico();
		assertEquals(6, l.size());
		Par p = l.get(1);
		assertTrue(p.getChave().matches("^.+$"));
		assertTrue(p.getValor().matches("^.+$"));
	}


	@Test
	public void testGetEstados() {
		List<Par> l = DadosAuxiliares.getEstados();
		assertEquals(28, l.size());
		Par p = l.get(1);
		assertTrue(p.getChave().matches("^\\w\\w$"));
		assertTrue(p.getValor().matches("^.+$"));
	}

}
