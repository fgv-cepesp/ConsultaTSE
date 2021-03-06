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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.fgv.model.Candidato;
import br.fgv.model.Cargo;
import br.fgv.model.Partido;
import br.fgv.util.ColumnField;

import com.google.common.base.Joiner;

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

	private static final Map<AgregacaoRegional, List<ColumnField>> _CAMPOS_DISPONIVEIS_REGIONAL = new HashMap<AgregacaoRegional, List<ColumnField>>();
	private static final Map<AgregacaoRegional, List<ColumnField>> _CAMPOS_FIXOS_REGIONAL = new HashMap<AgregacaoRegional, List<ColumnField>>();

	private static final Map<AgregacaoPolitica, List<ColumnField>> _CAMPOS_DISPONIVEIS_POLITICO = new HashMap<AgregacaoPolitica, List<ColumnField>>();
	private static final Map<AgregacaoPolitica, List<ColumnField>> _CAMPOS_FIXOS_POLITICO = new HashMap<AgregacaoPolitica, List<ColumnField>>();
	
	private static final List<Cargo> cargos = new ArrayList<Cargo>();
	
	private final DaoFactory daoFactory;

	static {

		/**
		 * Campos Regionais Disponiveis
		 */
        _CAMPOS_DISPONIVEIS_REGIONAL.put(AgregacaoRegional.MACRO_REGIAO, Tabela.TB_DIM_MACROREGIAO.getColunas(DISPONIVEL));
        _CAMPOS_DISPONIVEIS_REGIONAL.put(AgregacaoRegional.UF, Tabela.TB_DIM_ESTADOS.getColunas(DISPONIVEL));
        _CAMPOS_DISPONIVEIS_REGIONAL.put(AgregacaoRegional.UF_ZONA, Tabela.TB_DIM_ESTADOS.getColunas(DISPONIVEL));
        _CAMPOS_DISPONIVEIS_REGIONAL.put(AgregacaoRegional.MESO_REGIAO, Tabela.TB_DIM_MESOREGIAO.getColunas(DISPONIVEL));
        _CAMPOS_DISPONIVEIS_REGIONAL.put(AgregacaoRegional.MICRO_REGIAO, Tabela.TB_DIM_MICROREGIAO.getColunas(DISPONIVEL));
        _CAMPOS_DISPONIVEIS_REGIONAL.put(AgregacaoRegional.MUNICIPIO, Tabela.TB_DIM_MUNICIPIO.getColunas(DISPONIVEL));


		/**
		 * Campos Regionais Fixos
		 */
        _CAMPOS_FIXOS_REGIONAL.put(AgregacaoRegional.MACRO_REGIAO, Tabela.TB_DIM_MACROREGIAO.getColunas(FIXO));
        _CAMPOS_FIXOS_REGIONAL.put(AgregacaoRegional.UF, Tabela.TB_DIM_ESTADOS.getColunas(FIXO));
        _CAMPOS_FIXOS_REGIONAL.put(AgregacaoRegional.UF_ZONA, Tabela.TB_DIM_ESTADOS.getColunas(FIXO));
        _CAMPOS_FIXOS_REGIONAL.put(AgregacaoRegional.MESO_REGIAO, Tabela.TB_DIM_MESOREGIAO.getColunas(FIXO));
        _CAMPOS_FIXOS_REGIONAL.put(AgregacaoRegional.MICRO_REGIAO, Tabela.TB_DIM_MICROREGIAO.getColunas(FIXO));
        _CAMPOS_FIXOS_REGIONAL.put(AgregacaoRegional.MUNICIPIO, Tabela.TB_DIM_MUNICIPIO.getColunas(FIXO));

		/**
		 * Campos Politicos Disponiveis
		 */
        _CAMPOS_DISPONIVEIS_POLITICO.put(AgregacaoPolitica.PARTIDO, Tabela.TB_DIM_PARTIDOS.getColunas(DISPONIVEL));
        _CAMPOS_DISPONIVEIS_POLITICO.put(AgregacaoPolitica.CANDIDATO, Tabela.TB_DIM_CANDIDATOS.getColunas(DISPONIVEL));
        _CAMPOS_DISPONIVEIS_POLITICO.put(AgregacaoPolitica.COLIGACAO, Tabela.TB_DIM_LEGENDAS.getColunas(DISPONIVEL));

		/**
		 * Campos Politicos Disponiveis
		 */
        _CAMPOS_FIXOS_POLITICO.put(AgregacaoPolitica.PARTIDO, incluiVotos(AgregacaoPolitica.PARTIDO, Tabela.TB_DIM_PARTIDOS.getColunas(FIXO)));
        _CAMPOS_FIXOS_POLITICO.put(AgregacaoPolitica.CANDIDATO, incluiVotos(AgregacaoPolitica.CANDIDATO, Tabela.TB_DIM_CANDIDATOS.getColunas(FIXO)));
        _CAMPOS_FIXOS_POLITICO.put(AgregacaoPolitica.COLIGACAO, incluiVotos(AgregacaoPolitica.COLIGACAO, Tabela.TB_DIM_LEGENDAS.getColunas(FIXO)));
		
		cargos.add(new Cargo(1, "Presidente"));
		//cargos.add(new Cargo(2,	"Vice-Presidente"));
		cargos.add(new Cargo(3,	"Governador"));
		//cargos.add(new Cargo(4,	"Vice-Governador"));
		//cargos.add(new Cargo(9,	"1º Suplente Senador"));
		//cargos.add(new Cargo(10, "2º Suplente Senador"));
		cargos.add(new Cargo(5,	"Senador"));
		cargos.add(new Cargo(6,	"Deputado Federal"));
		//cargos.add(new Cargo(7, "Deputado Estadual(Inclui DF)"));
		cargos.add(new Cargo(7,	"Deputado Estadual"));
		cargos.add(new Cargo(8,	"Deputado Distrital"));
		cargos.add(new Cargo(11, "Prefeito"));
		//cargos.add(new Cargo(12, "Vice-Prefeito"));
		cargos.add(new Cargo(13, "Vereador"));
	}
	
	private static final Map<AgregacaoRegional, List<ColumnField>> CAMPOS_DISPONIVEIS_REGIONAL = Collections.unmodifiableMap(_CAMPOS_DISPONIVEIS_REGIONAL);
	private static final Map<AgregacaoRegional, List<ColumnField>> CAMPOS_FIXOS_REGIONAL = Collections.unmodifiableMap(_CAMPOS_FIXOS_REGIONAL);

	private static final Map<AgregacaoPolitica, List<ColumnField>> CAMPOS_DISPONIVEIS_POLITICO = Collections.unmodifiableMap(_CAMPOS_DISPONIVEIS_POLITICO);
	private static final Map<AgregacaoPolitica, List<ColumnField>> CAMPOS_FIXOS_POLITICO = Collections.unmodifiableMap(_CAMPOS_FIXOS_POLITICO);

	private static final List<Cargo> CARGOS = Collections.unmodifiableList(cargos);
	
	public BusinessImpl(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	private static List<ColumnField> incluiVotos(AgregacaoPolitica agregacao, List<ColumnField> colunas) {
		ColumnField votoNominal = new ColumnField("votos", "Resultado");
		votoNominal.setName("nominal");
		votoNominal.setDescription("Voto Nominal");
		colunas.add(votoNominal);

		if(AgregacaoPolitica.PARTIDO.equals(agregacao) || AgregacaoPolitica.CANDIDATO.equals(agregacao)) {
			ColumnField votoLegenda = new ColumnField("votos", "Resultado");
			votoLegenda.setName("legenda");
			votoLegenda.setDescription("Voto Legenda");
			colunas.add(votoLegenda);
		}

		if(AgregacaoPolitica.PARTIDO.equals(agregacao)) {
			ColumnField votoTotal = new ColumnField("votos", "Resultado");
			votoTotal.setName("total");
			votoTotal.setDescription("Voto Total");
			colunas.add(votoTotal);
		}

		return Collections.unmodifiableList(colunas);
	}

	public List<Integer> getAnosDisponiveis() {
		return daoFactory.getResultadosDAO().getAnosDisponiveisList();
	}

	public List<Integer> getTurnosDisponiveis() {
		List<Integer> turnos = new ArrayList<Integer>();
		turnos.add(1);
		turnos.add(2);

		return turnos;
	}

	public List<Integer> getAnosParaCargo(String cargo) {
		return daoFactory.getResultadosDAO().getAnosParaCargoList(cargo);
	}

	public List<Cargo> getCargos() {
		return CARGOS;
	}

	public CollumnFieldsCollection getCamposDisponiveis(String nivelAgregacaoRegional, String nivelAgregacaoPolitica) {
		AgregacaoRegional regional = AgregacaoRegional.findByNivel(nivelAgregacaoRegional);
		AgregacaoPolitica politica = AgregacaoPolitica.findByNivel(nivelAgregacaoPolitica);

		LOGGER.info("regional: " + regional);
		LOGGER.info("politica: " + politica);

		List<ColumnField> optionalFieds = this.getCamposDisponiveis(regional, politica);
		List<ColumnField> fixedFields = this.getCamposFixos(regional, politica);

		return new CollumnFieldsCollection(optionalFieds, fixedFields);
	}

	public List<ColumnField> getCamposDisponiveis(AgregacaoRegional nivelAgregacaoRegional, AgregacaoPolitica nivelAgregacaoPolitica) {
		List<ColumnField> columnFields = new ArrayList<ColumnField>();

		if(nivelAgregacaoRegional != null) columnFields.addAll(CAMPOS_DISPONIVEIS_REGIONAL.get(nivelAgregacaoRegional));
		if(nivelAgregacaoPolitica != null) columnFields.addAll(CAMPOS_DISPONIVEIS_POLITICO.get(nivelAgregacaoPolitica));

		return columnFields;
	}

	public List<ColumnField> getCamposFixos(AgregacaoRegional nivelAgregacaoRegional, AgregacaoPolitica nivelAgregacaoPolitica) {
		List<ColumnField> columns = new ArrayList<ColumnField>();

		if(nivelAgregacaoRegional != null) columns.addAll(CAMPOS_FIXOS_REGIONAL.get(nivelAgregacaoRegional));
		if(nivelAgregacaoPolitica != null) columns.addAll(CAMPOS_FIXOS_POLITICO.get(nivelAgregacaoPolitica));

		return columns;
	}

	public String getCargoByID(String codCargo) {
		return daoFactory.getResultadosDAO().getCargoByID(codCargo);
	}

	public InputStream getLinkResult(ArgumentosBusca args) throws CepespDataException {
		return daoFactory.getResultadosDAO().doWorkResult(args);
	}

	public String getSugestaoNomeArquivo(String anosEscolhidos, AgregacaoRegional nivelAgregacaoRegional, AgregacaoPolitica nivelAgregacaoPolitica, String codCargo) {

		String cargo = preprocessarParaNome(getCargoByID(codCargo));
		String agregacaoRegional = preprocessarParaNome(nivelAgregacaoRegional.getNomeDescritivo());
		String agregacaoPolitica = preprocessarParaNome(nivelAgregacaoPolitica.getNomeDescritivo());

		String nameFile = anosEscolhidos + "_" + cargo + "_" + agregacaoRegional + "_" + agregacaoPolitica + ".csv";

		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("Nome sugerido: " + nameFile);
		}

		return nameFile;
	}

	public String getSugestaoNomeArquivo(String anosEscolhidos, String agregacaoRegional, String agregacaoPolitica, String codCargo) {
		return getSugestaoNomeArquivo(anosEscolhidos, AgregacaoRegional.findByNivel(agregacaoRegional), AgregacaoPolitica.findByNivel(agregacaoPolitica), codCargo);
	}

	public String getSugestaoNomeArquivo(List<String> anosEscolhidos, AgregacaoRegional nivelAgregacaoRegional, AgregacaoPolitica nivelAgregacaoPolitica, String codCargo) {
		return getSugestaoNomeArquivo(Joiner.on('-').join(anosEscolhidos), nivelAgregacaoRegional, nivelAgregacaoPolitica, codCargo);
	}

	private String preprocessarParaNome(String str) {
		str = str.replace("/", "");
		str = str.replace("-", "");
		str = str.replace(" ", "");
		str = Normalizer.normalize(str, Normalizer.Form.NFD);
		str = str.replaceAll("[^\\p{ASCII}]", "");

		return str;
	}

	public List<Partido> getPartidos(String ano) {
		return Collections.unmodifiableList(daoFactory.getResultadosDAO()
				.getPartidosPorAnoList(new String[]{ano}));
	}

	public List<Partido> getPartidos(String[] anosList) {
		return Collections.unmodifiableList(daoFactory.getResultadosDAO()
				.getPartidosPorAnoList(anosList));
	}


	public List<Par> getFiltroRegional(String q, String nivelRegional) {
		Integer nivel = Integer.valueOf(nivelRegional);
		List<Par> ret = new ArrayList<Par>();
		if (nivel == 1) {
			ret = daoFactory.getResultadosDAO().getMacroRegiaoList(q);
        } else if (nivel == 2 || nivel == 3 ) {
            ret = daoFactory.getResultadosDAO().getEstadosList(q);
		} else if (nivel == 4) {
			ret = daoFactory.getResultadosDAO().getMesoRegiaoList(q);
		} else if (nivel == 5) {
			ret = daoFactory.getResultadosDAO().getMicroRegiaoList(q);
		} else if (nivel == 6) {
			ret = daoFactory.getResultadosDAO().getMunicipioList(q);
		}
		return Collections.unmodifiableList(ret);
	}

	public List<Candidato> getCandidatos(String query, String ano) {
		return Collections.unmodifiableList(daoFactory.getResultadosDAO()
				.getCandidatosPorAnoList(query, ano));
	}

	public List<Candidato> getCandidatos(String query, String[] anos, String cargo) {
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
		List<Integer> anos = dao.getAnosDisponiveisList();
		List<Date> cargas = dao.getDataCarga();

		String[][] m = new String[anos.size() + 1][cargas.size() + 1];

		m[0][0] = "Eleição &darr; Data TSE &#2198;  Data da Carga &rarr;";
		// preencher anos

		for(int c = cargas.size(), i = 1; c > 0; c--, i++){
			m[0][i] = cargas.get(c-1).toString();
		}

		for(int l = 1; l <= anos.size(); l++){
			m[l][0] = anos.get(l-1).toString();
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
	
	private static Set<Integer> _majoritarios = new HashSet<Integer>();
	
	static{
		_majoritarios.add(1); // Presidente
		_majoritarios.add(3); // Governador
		_majoritarios.add(5); // Senador
		_majoritarios.add(11); // Prefeito
	}
	private static final Set<Integer> MAJORITARIO = Collections.unmodifiableSet(_majoritarios);
	
	public static boolean isCargoMajoritario(int cargo) {
		return MAJORITARIO.contains(cargo);
	}

}
