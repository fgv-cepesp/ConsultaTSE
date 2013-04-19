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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.apache.log4j.Logger;

import br.fgv.CepespDataException;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;

public class CSVBuilder {

	private static final Logger LOGGER = Logger.getLogger(CSVBuilder.class);
	private static final Joiner JOINER = Joiner.on("\",\"").useForNull("null");

	private Writer out;
	private File file;

	private int linhas = 0;

	private int colunasTotal = -1;
	private int colunas = 0;

	private CSVBuilder(File aFile) {
		try {
			this.file = aFile;
			this.file.deleteOnExit();
			out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(aFile), Charsets.UTF_8));

		} catch (FileNotFoundException e) {
			// não deve acontecer porque o arquivo foi criado pelo sistema
			LOGGER.error("Não pude criar BufferedInputStream para " + file, e);
		}
	}

	public static final CSVBuilder createTemp() throws IOException {
		return new CSVBuilder(File.createTempFile("cepesp-data", ".csv"));
	}

	public CSVBuilder elemento(String... elementos) throws IOException {
		if (colunas == 0) {
			out.append('"');
		} else {
			out.append("\",\"");
		}
		JOINER.appendTo(out, elementos);
		colunas += elementos.length;
		return this;
	}

	public CSVBuilder linha() throws IOException, CepespDataException {
		if (colunas != 0) { // ignora se for zero...
			// verifica coluna
			if (colunasTotal < 0) {
				colunasTotal = colunas;
			} else if (colunasTotal != colunas) {
				throw new CepespDataException(
						"Arquivo CSV com coluna invalida. "
								+ "Linha deveria ter " + colunasTotal
								+ " colunas, mas tem " + colunas);
			}

			linhas++;

			if (colunas > 0) {
				out.append("\"\r\n"); // mais compativel com Windows
			}

			colunas = 0;
		}
		return this;
	}

	public File finalisa() throws IOException, CepespDataException {
		// verifica coluna
		if (colunasTotal < 0) {
			colunasTotal = colunas;
		} else if (colunasTotal != colunas && colunas != 0) {
			throw new CepespDataException("Arquivo CSV com coluna invalida. "
					+ "Linha deveria ter " + colunasTotal
					+ " colunas, mas tem " + colunas);
		}

		linhas++;

		if (colunas > 0) {
			out.append("\"\n");
		}

		colunas = 0;
		out.close();

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("CSV criado com " + getNumColunas() + " colunas e "
					+ getNumLinhas() + " linhas. Arquivo tmp: " + file);
		}
		return file;
	}

	public int getNumLinhas() {
		return linhas;
	}

	public int getNumColunas() {
		return colunasTotal;
	}
}
