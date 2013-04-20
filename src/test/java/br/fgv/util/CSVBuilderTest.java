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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;

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
		
		b.finalisa();
		
		List<String> linhas = readLines(b);
		assertEquals(3, linhas.size());
		assertEquals(3, b.getNumLinhas());
		assertEquals(4, b.getNumColunas());
		
		assertEquals("\"a\",\"b\",\"c\",\"d\"", linhas.get(0));
		assertEquals("\"1a\",\"1b\",\"1c\",\"1d\"", linhas.get(1));
		assertEquals("\"2a\",\"2b\",\"2c\",\"2d\"", linhas.get(2));
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
		
		b.finalisa();
		
		List<String> linhas = readLines(b);
		assertEquals(3, linhas.size());
		
		assertEquals("\"a\",\"b\",\"null\",\"d\"", linhas.get(0));
		assertEquals("\"null\",\"1b\",\"1c\",\"null\"", linhas.get(1));
		assertEquals("\"2a\",\"null\",\"2c\",\"2d\"", linhas.get(2));
		
	}
	
	private List<String> readLines(CSVBuilder csv) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(csv.getAsInputStream()));
		String line = null;
		List<String> lines = new ArrayList<String>();
		StringBuilder responseData = new StringBuilder();
		while((line = in.readLine()) != null) {
		    lines.add(line);
		}
		
		return lines;
	}

}
