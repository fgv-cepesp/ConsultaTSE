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

public class TSEDadosAuxiliaresTest {


	@Test
	public void testGetNivelAgregacaoPolitica() {
		List<Par> l = TSEDadosAuxiliares.getNivelAgregacaoPolitica();
		assertEquals(3, l.size());
		Par p = l.get(1);
		assertTrue(p.getChave().matches("^\\d+$"));
		assertTrue(p.getValor().matches("^.+$"));
	}

	@Test
	public void testGetNivelAgregacaoRegional() {
		List<Par> l = TSEDadosAuxiliares.getNivelAgregacaoRegional();
		assertEquals(7, l.size());
		Par p = l.get(1);
		assertTrue(p.getChave().matches("^\\d+$"));
		assertTrue(p.getValor().matches("^.+$"));
	}
}
