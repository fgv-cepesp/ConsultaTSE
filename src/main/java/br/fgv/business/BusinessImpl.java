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

import java.io.File;
import java.io.InputStream;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import br.com.caelum.vraptor.ioc.Component;
import br.fgv.CepespDataException;
import br.fgv.dao.DaoFactory;
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
		fixosUfZona.add(new Par("zona.blah", "Estados:Zona"));
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
		
		CAMPOS_DISPONIVEIS_POLITICO = Collections.unmodifiableMap(hashCamposDisponiveisPolitico);
		
		/**
		 * Campos Politicos Disponiveis
		 */
		Map<AgregacaoPolitica, List<Par>> hashCamposFixosPolitico = new HashMap<AgregacaoPolitica, List<Par>>();

		hashCamposFixosPolitico.put(AgregacaoPolitica.PARTIDO,
				Tabela.TB_DIM_PARTIDOS.getColunas(FIXO));

		hashCamposFixosPolitico.put(AgregacaoPolitica.CANDIDATO,
				Tabela.TB_DIM_CANDIDATOS.getColunas(FIXO));
		
		CAMPOS_FIXOS_POLITICO = Collections.unmodifiableMap(hashCamposFixosPolitico);
	}

	public BusinessImpl(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
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

}
