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

import static br.fgv.model.Coluna.Disponibilidade.DISPONIVEL;
import static br.fgv.model.Coluna.Disponibilidade.FIXO;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;

import br.com.caelum.vraptor.ioc.Component;
import br.fgv.CepespDataException;
import br.fgv.dao.DaoFactory;
import br.fgv.dao.ResultadosDAO;
import br.fgv.model.Tabela;
import br.fgv.util.ArgumentosBusca;
import br.fgv.util.Par;


@Component
public class BusinessImpl {

	private static final Logger LOGGER = Logger.getLogger(BusinessImpl.class);

	private static final Map<AgregacaoRegional, List<Par>> CAMPOS_DISPONIVEIS_REGIONAL;
	private static final Map<AgregacaoRegional, List<Par>> CAMPOS_FIXOS_REGIONAL;

	private static final Map<AgregacaoPolitica, List<Par>> CAMPOS_DISPONIVEIS_POLITICO;
	private static final Map<AgregacaoPolitica, List<Par>> CAMPOS_FIXOS_POLITICO;

	private final DaoFactory daoFactory;

	static {

		/**
		 * Campos Regionais Disponiveis
		 */
		Map<AgregacaoRegional, List<Par>> hashCamposDisponiveisRegional = new HashMap<AgregacaoRegional, List<Par>>();

		hashCamposDisponiveisRegional.put(AgregacaoRegional.MACRO_REGIAO,
				Tabela.TB_DIM_MACROREGIAO.getColunas(DISPONIVEL));

		hashCamposDisponiveisRegional.put(AgregacaoRegional.UF,
				Tabela.TB_DIM_ESTADOS.getColunas(DISPONIVEL));

        hashCamposDisponiveisRegional.put(AgregacaoRegional.UF_ZONA,
        		Tabela.TB_DIM_ESTADOS.getColunas(DISPONIVEL));

		hashCamposDisponiveisRegional.put(AgregacaoRegional.MESO_REGIAO,
				Tabela.TB_DIM_MESOREGIAO.getColunas(DISPONIVEL));

		hashCamposDisponiveisRegional.put(AgregacaoRegional.MICRO_REGIAO,
				Tabela.TB_DIM_MICROREGIAO.getColunas(DISPONIVEL));

		hashCamposDisponiveisRegional.put(AgregacaoRegional.MUNICIPIO,
				Tabela.TB_DIM_MUNICIPIO.getColunas(DISPONIVEL));

		CAMPOS_DISPONIVEIS_REGIONAL = Collections.unmodifiableMap(hashCamposDisponiveisRegional);


		/**
		 * Campos Regionais Fixos
		 */
		Map<AgregacaoRegional, List<Par>> hashCamposFixosRegional = new HashMap<AgregacaoRegional, List<Par>>();

		hashCamposFixosRegional.put(AgregacaoRegional.MACRO_REGIAO,
				Tabela.TB_DIM_MACROREGIAO.getColunas(FIXO));

		hashCamposFixosRegional.put(AgregacaoRegional.UF,
				Tabela.TB_DIM_ESTADOS.getColunas(FIXO));

		List<Par> fixosUfZona = new ArrayList<Par>(Tabela.TB_DIM_ESTADOS.getColunas(FIXO));
		fixosUfZona.add(new Par("zona.blah", "Estado:Zona"));
		hashCamposFixosRegional.put(AgregacaoRegional.UF_ZONA,
				fixosUfZona);

		hashCamposFixosRegional.put(AgregacaoRegional.MESO_REGIAO,
				Tabela.TB_DIM_MESOREGIAO.getColunas(FIXO));

		hashCamposFixosRegional.put(AgregacaoRegional.MICRO_REGIAO,
				Tabela.TB_DIM_MICROREGIAO.getColunas(FIXO));

		hashCamposFixosRegional.put(AgregacaoRegional.MUNICIPIO,
				Tabela.TB_DIM_MUNICIPIO.getColunas(FIXO));

		CAMPOS_FIXOS_REGIONAL = Collections.unmodifiableMap(hashCamposFixosRegional);


		/**
		 * Campos Politicos Disponiveis
		 */
		Map<AgregacaoPolitica, List<Par>> hashCamposDisponiveisPolitico = new HashMap<AgregacaoPolitica, List<Par>>();

		hashCamposDisponiveisPolitico.put(AgregacaoPolitica.PARTIDO,
				Tabela.TB_DIM_PARTIDOS.getColunas(DISPONIVEL));

		hashCamposDisponiveisPolitico.put(AgregacaoPolitica.CANDIDATO,
				Tabela.TB_DIM_CANDIDATOS.getColunas(DISPONIVEL));

		hashCamposDisponiveisPolitico.put(AgregacaoPolitica.COLIGACAO,
				Tabela.TB_DIM_COLIGACOES.getColunas(DISPONIVEL));

		CAMPOS_DISPONIVEIS_POLITICO = Collections.unmodifiableMap(hashCamposDisponiveisPolitico);

		/**
		 * Campos Politicos Disponiveis
		 */
		Map<AgregacaoPolitica, List<Par>> hashCamposFixosPolitico = new HashMap<AgregacaoPolitica, List<Par>>();

		hashCamposFixosPolitico.put(AgregacaoPolitica.PARTIDO,
				incluiVotos(AgregacaoPolitica.PARTIDO, Tabela.TB_DIM_PARTIDOS.getColunas(FIXO)));

		hashCamposFixosPolitico.put(AgregacaoPolitica.CANDIDATO,
				incluiVotos(AgregacaoPolitica.CANDIDATO, Tabela.TB_DIM_CANDIDATOS.getColunas(FIXO)));

		hashCamposFixosPolitico.put(AgregacaoPolitica.COLIGACAO,
				incluiVotos(AgregacaoPolitica.COLIGACAO, Tabela.TB_DIM_COLIGACOES.getColunas(FIXO)));

		CAMPOS_FIXOS_POLITICO = Collections.unmodifiableMap(hashCamposFixosPolitico);
	}

	public BusinessImpl(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	private static List<Par> incluiVotos(AgregacaoPolitica agregacao,
			List<Par> colunas) {
		List<Par> comVotos = new ArrayList<Par>(colunas);
		comVotos.add(new Par("votos.blah", "Resultado:Voto nominal"));
		if(AgregacaoPolitica.PARTIDO.equals(agregacao)) {
			comVotos.add(new Par("votos.bleh", "Resultado:Voto legenda"));
			comVotos.add(new Par("votos.bluh", "Resultado:Voto total"));
		}

		return Collections.unmodifiableList(comVotos);
	}

	public List<Par> getAnosDisponiveis() {
		List<Par> l = new ArrayList<Par>();
		l.add(new Par("", "--Selecionar---"));
		l.addAll(daoFactory.getResultadosDAO().getAnosDisponiveisList());
		return Collections.unmodifiableList(l);
	}

	public List<Par> getCargosPorAno(String ano) {
		List<Par> l = new ArrayList<Par>();
		l.add(new Par("", "--Selecionar---"));
		l.addAll(daoFactory.getResultadosDAO().getCargosPorAnoList(ano));
		return Collections.unmodifiableList(l);
	}

	public Object getAnosParaCargo(String cargo) {
		List<Par> l = new ArrayList<Par>();
//		l.add(new Par("", "--Selecionar---"));
		l.addAll(daoFactory.getResultadosDAO().getAnosParaCargoList(cargo));
		return Collections.unmodifiableList(l);
	}

	public List<Par> getCargosDisponiveis() {
		List<Par> l = new ArrayList<Par>();
		l.add(new Par("", "--Selecionar---"));
		l.addAll(daoFactory.getResultadosDAO().getCargosList());
		return Collections.unmodifiableList(l);
	}

	public FormResultAux getCamposDisponiveis(String nivelAgregacaoRegional,
			String nivelAgregacaoPolitica) {

		AgregacaoRegional regional = AgregacaoRegional.fromInt(nivelAgregacaoRegional);
		AgregacaoPolitica politica = AgregacaoPolitica.fromInt(nivelAgregacaoPolitica);

		FormResultAux f = new FormResultAux(
				this.getCamposDisponiveis(
						regional,
						politica),
				this.getCamposFixos(
						regional,
						politica));

		return f;
	}

	public List<Par> getCamposDisponiveis(AgregacaoRegional nivelAgregacaoRegional,
			AgregacaoPolitica nivelAgregacaoPolitica) {

		List<Par> l = new ArrayList<Par>();

		if(nivelAgregacaoRegional != null)
			l.addAll(CAMPOS_DISPONIVEIS_REGIONAL.get(nivelAgregacaoRegional));

		if(nivelAgregacaoPolitica != null)
			l.addAll(CAMPOS_DISPONIVEIS_POLITICO.get(nivelAgregacaoPolitica));

		return Collections.unmodifiableList(l);
	}

	public List<Par> getCamposFixos(AgregacaoRegional nivelAgregacaoRegional,
			AgregacaoPolitica nivelAgregacaoPolitica) {
		List<Par> l = new ArrayList<Par>();

		if(nivelAgregacaoRegional != null)
			l.addAll(CAMPOS_FIXOS_REGIONAL.get(nivelAgregacaoRegional));

		if(nivelAgregacaoPolitica != null)
			l.addAll(CAMPOS_FIXOS_POLITICO.get(nivelAgregacaoPolitica));

		return Collections.unmodifiableList(l);
	}

	public String getCargoByID(String codCargo) {
		return daoFactory.getResultadosDAO().getCargoByID(codCargo);
	}

	public InputStream getLinkResult(ArgumentosBusca args)
			throws CepespDataException {

		return daoFactory.getResultadosDAO().doWorkResult(args);

	}

	public String getSugestaoNomeArquivo(String anoEleicao,
			String nivelAgregacaoRegional, String nivelAgregacaoPolitica,
			String filtroCargo) {

		String cargo = preprocessarParaNome(getCargoByID(filtroCargo));
		String agregacaoRegional = preprocessarParaNome(
				AgregacaoRegional.fromInt(nivelAgregacaoRegional).getNomeDescritivo());

		String agregacaoPolitica = preprocessarParaNome(
				AgregacaoPolitica.fromInt(nivelAgregacaoPolitica).getNomeDescritivo());

		String nameFile = anoEleicao + "_" + cargo + "_" + agregacaoRegional
				+ "_" + agregacaoPolitica + ".csv";

		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("Nome sugerido: " + nameFile);
		}

		return nameFile;
	}

	private String preprocessarParaNome(String str) {
		str = str.replace("/", "");
		str = str.replace("-", "");
		str = str.replace(" ", "");
		str = Normalizer.normalize(str, Normalizer.Form.NFD);
		str = str.replaceAll("[^\\p{ASCII}]", "");

		return str;
	}

	public List<Par> getPartidos(String string) {
		return Collections.unmodifiableList(daoFactory.getResultadosDAO()
				.getPartidosPorAnoList(string));
	}

	public List<Par> getPartidos(String[] anosList) {
		return Collections.unmodifiableList(daoFactory.getResultadosDAO()
				.getPartidosPorAnoList(anosList));
	}


	public Object getFiltroRegional(String q, String nivelRegional) {
		Integer nivel = Integer.valueOf(nivelRegional);
		List<Par> ret = new ArrayList<Par>();
		if (nivel == 1) {
			ret = daoFactory.getResultadosDAO().getMacroRegiaoList();
        } else if (nivel == 2 || nivel == 3 ) {
            ret = daoFactory.getResultadosDAO().getEstadosList();
		} else if (nivel == 4) {
			ret = daoFactory.getResultadosDAO().getMesoRegiaoList(q);
		} else if (nivel == 5) {
			ret = daoFactory.getResultadosDAO().getMicroRegiaoList(q);
		} else if (nivel == 6) {
			ret = daoFactory.getResultadosDAO().getMunicipioList(q);
		}
		return Collections.unmodifiableList(ret);
	}

	public List<Par> getCandidatos(String query, String ano) {
		return Collections.unmodifiableList(daoFactory.getResultadosDAO()
				.getCandidatosPorAnoList(query, ano));
	}

	public List<Par> getCandidatos(String query, String[] anos, String cargo) {
		return Collections.unmodifiableList(daoFactory.getResultadosDAO()
				.getCandidatosPorAnoList(query, anos, cargo));
	}


	public String[][] getDadosCargaCand() {
		return getDadosCarga("cand");
	}

	public String[][] getDadosCargaVoto() {
		return getDadosCarga("voto");
	}

	private String[][] getDadosCarga(final String tipo) {
		ResultadosDAO dao = daoFactory.getResultadosDAO();
		List<Par> anos = dao.getAnosDisponiveisList();
		List<Date> cargas = dao.getDataCarga();

		String[][] m = new String[anos.size() + 1][cargas.size() + 1];

		m[0][0] = "Eleição &darr; Data TSE &#2198;  Data da Carga &rarr;";
		// preencher anos

		for(int c = cargas.size(), i = 1; c > 0; c--, i++){
			m[0][i] = cargas.get(c-1).toString();
		}

		for(int l = 1; l <= anos.size(); l++){
			m[l][0] = anos.get(l-1).getChave();
		}

		for(int i = 1; i < m.length; i++) {
			for(int j = 1; j < m[0].length; j++) {
				int ano = Integer.parseInt(m[i][0]);
				String carga = m[0][j];


				Date data = null;
				if(tipo.equals("cand")) {
					data = dao.getDataCand(carga, ano);
				} else {
					data = dao.getDataVoto(carga, ano);
				}

				m[i][j] = "" + data;
			}
		}

		return m;
	}

	private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

}
