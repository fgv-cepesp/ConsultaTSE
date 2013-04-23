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

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.Writer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import br.fgv.CepespDataException;
import br.fgv.dao.ResultSetWork;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.io.Closeables;

public class CSVBuilder extends InputStream implements Runnable {
	
	private long start = System.currentTimeMillis();
	
	private int BUFFER = 2048;

	private static final Logger LOGGER = Logger.getLogger(CSVBuilder.class);
	private static final Joiner JOINER = Joiner.on("\",\"").useForNull("null");

	// 
	private Writer out;
	private PipedInputStream pis = new MonitoredPipedInputStream(BUFFER);
	
	private volatile int linhas = 0;
	private volatile int linhasAnterior = 0;
	
	private int colunasTotal = -1;
	private int colunas = 0;
	
	// alto popular
	private ResultSetWork ra;
	private ResultSet rs;
	private volatile boolean continuarPopulando = true;

	private boolean isDbOpen = true;
	
	private CSVBuilder() throws IOException {
			OutputStream os = new MonitoredPipedOutputStream(this.pis);

			out = new OutputStreamWriter(
					new BufferedOutputStream(os), Charsets.UTF_8);

	}
	
	// auto popular com thread!!
	public void setSource(ResultSetWork ra, ResultSet rs) {
		this.ra = ra;
		this.rs = rs;
	}
	
	// auto popular (usar thread)
	public void run() {
		try {
			LOGGER.info("Iniciando criacao do CSV...");
			int colCount = rs.getMetaData().getColumnCount();
			elemento(ra.getColumnsName()).linha();

			while (rs.next()) {
				if(!continuarPopulando) {
					LOGGER.info("Parando escrita do CSV...");
					break;
				}
				
				for (int i = 0; i < colCount; i++) {
					elemento(rs.getString(i + 1));
				}
				linha();
			}
			LOGGER.info("Concluiu popular CSV. Tentando finalizar...");
			
			finaliza();
		} catch (Exception e) {
			throw new RuntimeException(
					"Falhou ao criar arquivo csv.", e);
		}
	}
	
	private Thread t;
	
	public void start() {
		
		// start the thread
		t = new Thread(this, "CSVBuilder");
		t.start();
		
		// we will need to setup a timeout to the CSV writer..
		Timer timer = new Timer();
		
		timer.scheduleAtFixedRate(new TimerTask() {
			  @Override
			  public void run() {
			    if(linhas == linhasAnterior) {
			    	// it is frozen! we should stop the writting...
			    	LOGGER.warn("Escrita do CSV parada a 2 minutos! Cancelando processo...");
			    	this.cancel();
			    	stop();
			    } else {
					LOGGER.info("Passaram-se 2 minutos e foram escritas "
							+ (linhas - linhasAnterior)
							+ " linhas neste periodo.");
					linhasAnterior = linhas;
			    }
			  }
			}, 2*60*1000, 2*60*1000);
	}
	
	public void stop() {
		if(t != null) {
			continuarPopulando = false;
			LOGGER.info("Pediu parada de escrita do CSV. Aguardando thread finalizar.");
			try {
				t.join(50000);
				LOGGER.info("Thread de escrita terminada, ou timeout.");
			} catch (InterruptedException e) {
				// oops
			} catch (Exception e) {
				// TODO: handle exception
			}
			t = null;
		}
	}

	public static final CSVBuilder getInstance() throws IOException {
		return new CSVBuilder();
	}

	public InputStream getAsInputStream() {
		return this;
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
	
	private volatile boolean isFinalizadoPendente = true;
	private final Object finalizaFlag = new Object();

	public void finaliza() throws IOException, CepespDataException {
		synchronized (finalizaFlag) {
			if(isFinalizadoPendente) {
				isFinalizadoPendente = false;
				
				// fecha recursos de leitura
				this.closeDBResources();
				
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

				Closeables.closeQuietly(this.out);
				
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("CSV criado com " + getNumColunas() + " colunas e "
							+ getNumLinhas() + " linhas. Tempo (s): " + (System.currentTimeMillis() - start)/1000.0);
				}
			}
		}
	}

	public int getNumLinhas() {
		return linhas;
	}

	public int getNumColunas() {
		return colunasTotal;
	}
	
	private final Object dbFlag = new Object();
	
	public void closeDBResources() {
		synchronized (dbFlag) {
			LOGGER.info("Fechando conexao DB");
			if(this.isDbOpen && this.rs != null) {
				this.isDbOpen = false;
				try {
					this.rs.close();
				} catch (SQLException e) {
					LOGGER.warn("Falhou fechar DB da criacao de CSV.", e);
				}
				this.ra.close();
				
				this.rs = null;
				this.ra = null;
			}	
		}
	}

	@Override
	public int read() throws IOException {
		// read the pis
		return this.pis.read();
	}
	
	private volatile boolean isClosePendente = true;
	private final Object closeFlag = new Object();
	
	@Override
	public void close() throws IOException {
		synchronized (closeFlag) {
			if(isClosePendente) {

				LOGGER.info("Closing CSVBuilder...");
				
				Closeables.closeQuietly(this.pis);
				
				stop();
				
				LOGGER.info("Comecando a fechar recursos ainda abertos.");
				Closeables.closeQuietly(this.out);
				
				this.closeDBResources();
				
				super.close();
				
				LOGGER.info("Close concluido!");
				this.isClosePendente = false;
			}
		}
	}
	
	@Override
	protected void finalize() throws Throwable {
		LOGGER.info("Executando finalize...");
		this.close();
		super.finalize();
	}
	
	private class MonitoredPipedOutputStream extends PipedOutputStream {
		
		public MonitoredPipedOutputStream(PipedInputStream in) throws IOException {
			super(in);
		}
		
		@Override
		public void close() throws IOException {
			LOGGER.info("Closing MonitoredPipedOutputStream");
			super.close();
		}
	}
	
	private class MonitoredPipedInputStream extends PipedInputStream {
		
		public MonitoredPipedInputStream(int size) throws IOException {
			super(size);
		}
		
		@Override
		public void close() throws IOException {
			LOGGER.info("Closing MonitoredPipedInputStream");
			super.close();
		}
	}
}
