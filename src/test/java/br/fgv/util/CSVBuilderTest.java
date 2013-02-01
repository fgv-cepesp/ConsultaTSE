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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;

import br.fgv.CepespDataException;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class CSVBuilderTest {
	
	private static final Logger LOGGER = Logger.getLogger(CSVBuilderTest.class);

	@Test
	public void testCreateTemp() throws Exception {
		CSVBuilder b = CSVBuilder.createTemp();
		
		b.elemento("a");
		b.elemento("b", "c", "d");
		
		b.linha();
		
		b.elemento("1a");
		b.elemento("1b", "1c", "1d");
		
		b.linha();
		
		b.elemento("2a","2b", "2c", "2d");
		
		
		File f = b.finalisa();
		assertNotNull(f);
		
		List<String> linhas = readLines(f);
		assertEquals(3, linhas.size());
		assertEquals(3, b.getNumLinhas());
		assertEquals(4, b.getNumColunas());
		
		assertEquals("\"a\",\"b\",\"c\",\"d\"", linhas.get(0));
		assertEquals("\"1a\",\"1b\",\"1c\",\"1d\"", linhas.get(1));
		assertEquals("\"2a\",\"2b\",\"2c\",\"2d\"", linhas.get(2));
		
		if(!f.delete()) {
			LOGGER.error("Não pude deletar arquivo " + f);
		}
	}
	

	@Test
	public void testCreateTempComNull() throws Exception {
		CSVBuilder b = CSVBuilder.createTemp();
		
		b.elemento("a");
		b.elemento("b", null, "d");
		
		b.linha();
		
		b.elemento((String)null);
		b.elemento("1b", "1c", null);
		
		b.linha();
		
		b.elemento("2a",null, "2c", "2d");
		
		
		File f = b.finalisa();
		assertNotNull(f);
		
		List<String> linhas = readLines(f);
		assertEquals(3, linhas.size());
		
		assertEquals("\"a\",\"b\",\"null\",\"d\"", linhas.get(0));
		assertEquals("\"null\",\"1b\",\"1c\",\"null\"", linhas.get(1));
		assertEquals("\"2a\",\"null\",\"2c\",\"2d\"", linhas.get(2));
		
		if(!f.delete()) {
			LOGGER.error("Não pude deletar arquivo " + f);
		}
	}
	

	@Test(expected=CepespDataException.class)
	public void testCreateTempFail() throws CepespDataException, IOException {
		CSVBuilder b = CSVBuilder.createTemp();
		
		b.elemento("a");
		b.elemento("b", "c", "d");
		
		b.linha();
		
		b.elemento("1a");
		b.elemento("1b", "1c");
		
		File f = b.finalisa();
		assertNotNull(f);
		
		if(!f.delete()) {
			LOGGER.error("Não pude deletar arquivo " + f);
		}
		
		fail("Falhou ao nao enviar exception");
	}
	
	@Test(expected=CepespDataException.class)
	public void testCreateTempFail2() throws CepespDataException, IOException {
		CSVBuilder b = CSVBuilder.createTemp();
		
		b.elemento("a");
		b.elemento("b", "c", "d");
		
		b.linha();
		
		b.elemento("1a");
		b.elemento("1b", "1c");
		
		b.linha();
		
		File f = b.finalisa();
		assertNotNull(f);
		
		if(!f.delete()) {
			LOGGER.error("Não pude deletar arquivo " + f);
		}
		
		fail("Falhou ao nao enviar exception");
	}
	
	private List<String> readLines(File f) throws IOException {
		return Files.readLines(f, Charsets.UTF_8);
	}

}
