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

import br.fgv.util.ColumnField;
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
import br.fgv.business.CollumnFieldsCollection;
import br.fgv.model.TSEDadosAuxiliares;
import br.fgv.model.Tabela;
import br.fgv.util.ArgumentosBusca;

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
		result.include("nivelAgregacaoRegionalList", TSEDadosAuxiliares.getNivelAgregacaoRegional());
		result.include("nivelAgregacaoPoliticaList", TSEDadosAuxiliares.getNivelAgregacaoPolitica());
		result.include("filtroCargoList", business.getCargos());
		result.include("filtroTurnoList", business.getTurnosDisponiveis());
	}

	@Get
	@Path(priority = 1, value = "/ajuda")
	public void ajuda() {
		result.include("ajudaTabela", Tabela.getHelp());
	}

	@Get
	@Path(priority = 1, value = "/etl")
	public void etl() {
		result.include("mcand", business.getDadosCargaCand());
		result.include("mvoto", business.getDadosCargaVoto());
	}

	@Get
	@Path(priority = 1, value = "/ajudaCsv")
	public Download ajudaCsv() throws CepespDataException {
		File retFile = Tabela.getHelpCSV();
		return new FileDownload(retFile, "text/csv", "ajuda_cepesp-data.csv", true);

	}

	@Get
	@Path("/consulta/camposDisponiveis")
	public void camposDisponiveisList(String nivelAgregacaoRegional, String nivelAgregacaoPolitica) {
		final CollumnFieldsCollection f = business.getCamposDisponiveis(nivelAgregacaoRegional, nivelAgregacaoPolitica);

		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("Nivel regional: " + nivelAgregacaoRegional +
					" Nivel politica: " + nivelAgregacaoPolitica +
					" Campos disponíveis:\n" + f);
		}

		result.use(Results.json()).from(f)
			.include("optionalFields", "fixedFields")
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
		if(LOGGER.isDebugEnabled()) LOGGER.debug("Anos para o cargo: " + cargo);
		result.use(Results.json())
				.from(business.getAnosParaCargo(cargo))
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
	@Path("/consulta/candidatosAnosCargo")
	public void candidatosPorAno(String q, String[] anosList, String cargo) {
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("Candidatos para o ano. Anos: " + Arrays.toString(anosList) + "; Cargo: " + cargo);
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
	public Download resultadosCSV(List<String> anos, String cargo, String turno,
			String agregacaoRegional, String agregacaoPolitica,
			List<String> camposOpcionais, String filtroRegional, List<String> regioes,
		  	List<String> partidos, List<String> candidatos, List<String> consolidados)
			throws CepespDataException {

		long start = System.currentTimeMillis();
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug(">>> partidos " + partidos);
			LOGGER.debug(">>> candidatos " + candidatos);
			LOGGER.debug(">>> campos opcionais " + camposOpcionais);
		}

		if (partidos == null) partidos = new ArrayList<String>();
		if (regioes == null) regioes = new ArrayList<String>();
		if (candidatos == null) candidatos = new ArrayList<String>();
		if (camposOpcionais == null) camposOpcionais = new ArrayList<String>();
		if (consolidados == null) consolidados = new ArrayList<String>();
		if (filtroRegional == null) filtroRegional = "0";

		Collections.sort(anos);
		AgregacaoPolitica nivelAgregacaoPolitica = AgregacaoPolitica.findByNivel(Integer.parseInt(agregacaoPolitica));
		AgregacaoRegional nivelAgregacaoRegional = AgregacaoRegional.findByNivel(Integer.parseInt(agregacaoRegional));
		AgregacaoRegional nivelFiltroRegional = AgregacaoRegional.findByNivel(Integer.parseInt(filtroRegional));
		List<ColumnField> camposFixos = business.getCamposFixos(nivelAgregacaoRegional, nivelAgregacaoPolitica);

		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug(">>> campos fixos " + camposFixos);
		}

		ArgumentosBusca args = new ArgumentosBusca();
		args.setFiltroCargo(cargo);
		args.setNivelAgrecacaoPolitica(nivelAgregacaoPolitica);
		args.setNivelRegional(nivelAgregacaoRegional);
		args.setFiltroNivelRegional(nivelFiltroRegional);

		for (String ano : anos) args.addAnoEleicao(ano);
		for (ColumnField campo : camposFixos) args.addCampoEscolhido(campo.getKey());
		for (String campo : camposOpcionais) args.addCampoEscolhido(campo);
		for (String codCandidato : candidatos) args.addCanditado(codCandidato);
		for (String codPartido : partidos) args.addPartido(codPartido);
		for (String codRegiao : regioes) args.addRegiao(codRegiao);
		for (String consolidado : consolidados) args.addConsolidado(consolidado);

		int fcint = Integer.parseInt(cargo);
		if (!(turno == null || turno == "")) {
			int t = Integer.parseInt(turno);
			if (fcint == 1 || fcint == 3 || fcint == 11) {
				// keep
			} else {
				t = 1;
			}
			args.setTurno(t);
		}

		if(LOGGER.isInfoEnabled()) {
			LOGGER.info("Argumentos da busca: " + args.toString());
		}

		String nameFile = business.getSugestaoNomeArquivo(anos, nivelAgregacaoRegional, nivelAgregacaoPolitica, cargo);

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

	private static class CloseableInputStreamDownload extends InputStreamDownload {
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
