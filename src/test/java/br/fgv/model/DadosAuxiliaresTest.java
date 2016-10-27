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
import java.util.Map;

import org.junit.Test;

import br.fgv.util.Par;

public class DadosAuxiliaresTest {


	@Test
	public void testGetMeses() {
		Map<Integer, String> l = DadosAuxiliares.getMeses();
		assertEquals(12, l.size());
		String janeiro = l.get(1);
		assertEquals(janeiro, "Janeiro");
	}

	@Test
	public void testGetEscolaridade() {
		Map<Integer, String> l = DadosAuxiliares.getEscolaridade();
		assertEquals(10, l.size());
		String primeiroCompleto = l.get(1);
		assertEquals("Primeiro Incompleto", primeiroCompleto);
	}


	@Test
	public void testGetCargoPolitico() {
		Map<String, String> l = DadosAuxiliares.getCargoPolitico();
		assertEquals(5, l.size());
		String veread = l.get("veread");
		assertEquals("Vereador(a)", veread);
	}


	@Test
	public void testGetEstados() {
		Map<String, String> l = DadosAuxiliares.getEstados();
		assertEquals(27, l.size());
		String ac = l.get("AC");
		assertEquals("Acre", ac);
	}

}
