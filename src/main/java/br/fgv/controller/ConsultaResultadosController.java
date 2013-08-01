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
package br.fgv.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.interceptor.download.Download;
import br.com.caelum.vraptor.interceptor.download.FileDownload;
import br.com.caelum.vraptor.interceptor.download.InputStreamDownload;
import br.com.caelum.vraptor.view.Results;
import br.fgv.CepespDataException;
import br.fgv.business.AgregacaoPolitica;
import br.fgv.business.AgregacaoRegional;
import br.fgv.business.BusinessImpl;
import br.fgv.business.FormResultAux;
import br.fgv.model.TSEDadosAuxiliares;
import br.fgv.model.Tabela;
import br.fgv.util.ArgumentosBusca;

import com.google.common.base.Joiner;
import com.google.common.io.Closeables;

@Resource
public class ConsultaResultadosController {
	
	private static final Logger LOGGER = Logger.getLogger(ConsultaResultadosController.class);

	private final Result result;
	private final BusinessImpl business;
	private final HttpServletResponse response;

	public ConsultaResultadosController(Result result, BusinessImpl business, HttpServletResponse response) {
		this.result = result;
		this.business = business;
		this.response = response;
	}

	@Get
	@Path(priority = 1, value = "/consultaResultados")
	public void inicial() {
		result.include("nivelAgregacaoRegionalList", TSEDadosAuxiliares
				.getNivelAgregacaoRegional());
		result.include("nivelAgregacaoPoliticaList", TSEDadosAuxiliares
				.getNivelAgregacaoPolitica());
		result.include("filtroCargoList", business.getCargosDisponiveis());
	}

	@Get
	@Path(priority = 1, value = "/ajuda")
	public void ajuda() {
		result.include("ajudaTabela", Tabela.getHelp());
	}
	
	@Get
	@Path(priority = 1, value = "/ajudaCsv")
	public Download ajudaCsv() throws CepespDataException {

		
		File retFile = Tabela.getHelpCSV();

		return new FileDownload(retFile, "text/csv", "ajuda_cepesp-data.csv", true);
		
	}

	@Get
	@Path("/consulta/camposDisponiveis")
	public void camposDisponiveisList(String nivelAgregacaoRegional,
			String nivelAgregacaoPolitica) {
		
		final FormResultAux f = business.getCamposDisponiveis(nivelAgregacaoRegional,
				nivelAgregacaoPolitica);
		
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("Nivel regional: " + nivelAgregacaoRegional +
					" Nivel politica: " + nivelAgregacaoPolitica +
					" Campos disponíveis:\n" + f);
		}
		
		result.use(Results.json()).from(f)
			.include("camposOpcionais","camposFixos")
			.serialize();
		
	}

	@Get
	@Path("/consulta/cargos")
	public void cargosPorAno(String ano) {
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("Cargos para o ano.: " + ano);
		}
		result.use(Results.json()).from(business.getCargosPorAno(ano))
				.serialize();
	}
	
	@Get
	@Path("/consulta/anos")
	public void anosParaCargo(String cargo) {
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("Anos para o cargo: " + cargo);
		}
		result.use(Results.json()).from(business.getAnosParaCargo(cargo))
				.serialize();
	}

	@Get
	@Path("/consulta/partidos")
	public void partidosPorAno(String ano) {
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("Partidos para o ano.: " + ano);
		}
		result.use(Results.json()).from(business.getPartidos(ano))
				.serialize();
	}

	@Get
	@Path("/consulta/partidosAnos")
	public void partidosPorAno(String[] anosList) {
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("Partidos para o ano.: " + Arrays.toString(anosList));
		}
		result.use(Results.json()).from(business.getPartidos(anosList))
				.serialize();
	}

	@Get
	@Path("/consulta/candidatos")
	public void candidatosPorAno(String q, String ano) {
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("Candidatos para o ano. Ano: " + ano + "; Filtro: " + q);
		}
		result.use(Results.json()).from(business.getCandidatos(q, ano))
				.serialize();
	}

	@Get
	@Path("/consulta/candidatosAnosCargo")
	public void candidatosPorAno(String q, String[] anosList, String cargo) {
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("Candidatos para o ano. Anos: " + Arrays.toString(anosList) + "; Filtro: " + q + "; Cargo: " + cargo);
		}
		result.use(Results.json()).from(business.getCandidatos(q, anosList, cargo))
				.serialize();
	}

	@Get
	@Path("/consulta/filtroRegionalQuery")
	public void filtroRegionalQuery(String q, String nivelRegional) {
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("Filtro Query................: " + q);
			LOGGER.debug("Filtro para o nivel Regional: " + nivelRegional);
		}
		result.use(Results.json()).from(
				business.getFiltroRegional(q, nivelRegional)).serialize();
	}

	@Post
	@Path("/resultados.csv")
	public Download resultadosCSVEntrada(List<String> anosEscolhidos, String filtroCargo,
			String nivelAgregacaoRegional, String nivelAgregacaoPolitica,
			List<String> camposEscolhidos, List<String> camposFixos,
			String nivelFiltroRegional, String as_values_regional,
			String as_values_candidatos, String as_values_partidos)
			throws CepespDataException {
		
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("Controller preparando para delegar criacao do CSV");
		}
		
		List<String> fp = trataLista(as_values_partidos);
		List<String> fr = trataLista(as_values_regional);
		List<String> fc = trataLista(as_values_candidatos);
		

		return resultadosCSV(anosEscolhidos, filtroCargo, nivelAgregacaoRegional,
				nivelAgregacaoPolitica, camposEscolhidos, camposFixos,
				nivelFiltroRegional, fr, fp, fc);
	}

	private List<String> trataLista(String lista) {
	    lista = lista == null ? "": lista;
		String[] tmp = lista.split(",");
		List<String> ret = new ArrayList<String>();

		for (int i = 0; i < tmp.length; i++) {
			if (tmp[i].trim() != "") {
				ret.add(tmp[i]);
			}
		}

		return ret;
	}

	// @Post
	// @Path("/resultados.csv")
	public Download resultadosCSV(List<String> anosEscolhidos, String filtroCargo,
			String nivelAgregacaoRegional, String nivelAgregacaoPolitica,
			List<String> camposEscolhidos, List<String> camposFixos,
			String nivelFiltroRegional, List<String> filtroRegional,
			List<String> filtroPartido, List<String> filtroCandidato) throws CepespDataException {
		
		long start = System.currentTimeMillis();
		if(LOGGER.isDebugEnabled() && camposEscolhidos != null) {
			LOGGER.debug(">>> campos fixos " + Arrays.toString(camposFixos.toArray()));
			LOGGER.debug(">>> campos escolhidos " + Arrays.toString(camposEscolhidos.toArray()));
		}
		
		
		camposFixos = camposFixos == null ? Collections.<String>emptyList()
				: camposFixos;
		camposEscolhidos = camposEscolhidos == null ? Collections.<String>emptyList()
				: camposEscolhidos;
		filtroRegional = filtroRegional == null ? Collections.<String>emptyList()
				: filtroRegional;
		filtroPartido = filtroPartido == null ? Collections.<String>emptyList()
				: filtroPartido;
		filtroCandidato = filtroCandidato == null ? Collections.<String>emptyList()
				: filtroCandidato;
		
		List<String> c = new ArrayList<String>();
		c.addAll(camposFixos);
		c.addAll(camposEscolhidos);
		String[] campos = c.toArray(new String[c.size()]);
		
		ArgumentosBusca args = new ArgumentosBusca();
		Collections.sort(anosEscolhidos);
		args.setAnoEleicao(anosEscolhidos.toArray(new String[anosEscolhidos.size()]));
		args.setFiltroCargo(filtroCargo);
		args.setNivelAgrecacaoPolitica(AgregacaoPolitica.fromInt(nivelAgregacaoPolitica));
		args.setNivelRegional(AgregacaoRegional.fromInt(nivelAgregacaoRegional));
		args.setCamposEscolhidos(campos);
		
		
		args.setNivelFiltroRegional(AgregacaoRegional.fromInt(nivelFiltroRegional));
		

		String[] fr = filtroRegional.toArray(new String[filtroRegional.size()]);
		String[] fp = filtroPartido.toArray(new String[filtroPartido.size()]);
		String[] fc = filtroCandidato
				.toArray(new String[filtroCandidato.size()]);

		args.setFiltroRegional(fr);
		args.setFiltroPartido(fp);
		args.setFiltroCandidato(fc);
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info("Argumentos da busca: " + args.toString());
		}
		
		
		String nameFile = business.getSugestaoNomeArquivo(Joiner.on("-").join(anosEscolhidos),
				nivelAgregacaoRegional, nivelAgregacaoPolitica, filtroCargo);

		if(LOGGER.isInfoEnabled()) {
			LOGGER.info("Comecando consulta propriamente, tempo total (s): " + (System.currentTimeMillis() - start)/1000.0);
			
		}
		Download d = new CloseableInputStreamDownload(business.getLinkResult(args), "text/csv", nameFile, true, 0);
		Cookie cookie = new Cookie("fileDownload", "true");
		cookie.setPath("/");
        response.addCookie(cookie);
        
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info("<<< Download preparado. Tempo total (s): " + (System.currentTimeMillis() - start)/1000.0);
		}
		
		return d;
	}
	
	private static class CloseableInputStreamDownload extends InputStreamDownload{
		
		private InputStream stream;
		
		public CloseableInputStreamDownload(InputStream input, String contentType, String fileName, boolean doDownload, long size) {
			super(input, contentType, fileName, doDownload, size);
			this.stream = input;
		}
		
		@Override
		public void write(HttpServletResponse response) throws IOException {
			try {
				super.write(response);
			} catch(IOException e) {
				throw new IOException("Exception durante download. Vamos tentar fechar CSVBuilder." , e); 
			} finally {
				Closeables.closeQuietly(stream);
			}
		}
	}

}
