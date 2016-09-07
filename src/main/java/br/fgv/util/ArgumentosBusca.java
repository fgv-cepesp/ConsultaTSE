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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import br.fgv.business.AgregacaoPolitica;
import br.fgv.business.AgregacaoRegional;

public class ArgumentosBusca {

	private List<String> anosEleicoes;
	private String filtroCargo;
	private AgregacaoRegional nivelRegional;
	private AgregacaoPolitica nivelAgrecacaoPolitica;
	private List<String> camposEscolhidos;
	private List<String> partidos;
	private List<String> candidados;
	private List<String> regioes;
	private int turno;
	private AgregacaoRegional filtroNivelRegional;

	public ArgumentosBusca() {
		this.anosEleicoes = new ArrayList<String>();
		this.camposEscolhidos = new ArrayList<String>();
		this.partidos = new ArrayList<String>();
		this.candidados = new ArrayList<String>();
		this.regioes = new ArrayList<String>();
	}

	public void addAnoEleicao(String ano) {
		this.anosEleicoes.add(ano);
	}

	public void addCampoEscolhido(String campo) {
		this.camposEscolhidos.add(campo);
	}

	public void addPartido(String codPartido) {
		this.partidos.add(codPartido);
	}

	public void addCanditado(String codCandidato) {
		this.partidos.add(codCandidato);
	}

	public void addRegiao(String codRegiao) {
		this.regioes.add(codRegiao);
	}

	public List<String> getAnosEleicoes() {
		return Collections.unmodifiableList(anosEleicoes);
	}

	public List<String> getCamposEscolhidos() {
		return Collections.unmodifiableList(camposEscolhidos);
	}

	public List<String> getPartidos() {
		return Collections.unmodifiableList(partidos);
	}

	public List<String> getCandidados() {
		return Collections.unmodifiableList(candidados);
	}

	public List<String> getRegioes() {
		return Collections.unmodifiableList(regioes);
	}

	public String getFiltroCargo() {
		return filtroCargo;
	}
	public void setFiltroCargo(String filtroCargo) {
		this.filtroCargo = filtroCargo;
	}

	public AgregacaoRegional getNivelRegional() {
		return nivelRegional;
	}
	public void setNivelRegional(AgregacaoRegional nivelRegional) {
		this.nivelRegional = nivelRegional;
	}
	public void setNivelRegional(Integer nivelRegional) {
		this.nivelRegional = AgregacaoRegional.findByNivel(nivelRegional);
	}

	public AgregacaoPolitica getNivelAgrecacaoPolitica() {
		return nivelAgrecacaoPolitica;
	}
	public void setNivelAgrecacaoPolitica(Integer nivelAgrecacaoPolitica) {
		this.nivelAgrecacaoPolitica = AgregacaoPolitica.findByNivel(nivelAgrecacaoPolitica);
	}
	public void setNivelAgrecacaoPolitica(AgregacaoPolitica nivelAgrecacaoPolitica) {
		this.nivelAgrecacaoPolitica = nivelAgrecacaoPolitica;
	}

	public int getTurno() {
		return turno;
	}

	public void setTurno(int turno) {
		this.turno = turno;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ArgumentosBusca[\n")
			.append( "  Filtros obrigatórios:")
			.append( "\n    - cargo: " ).append( filtroCargo )
			.append( "\n    - nivelRegional: " ).append( nivelRegional )
			.append( "\n    - turno: " ).append( turno )
			.append( "\n    - nivelAgrecacaoPolitica: " ).append( nivelAgrecacaoPolitica )
			.append( "\n Eleicoes: ")
			.append( "\n    - anos: " ).append( anosEleicoes )
			.append( "\n Colunas: ")
			.append( "\n    - camposEscolhidos: " ).append( camposEscolhidos )
			.append( "\n Filtros: ")
			.append( "\n    - partido: " ).append( partidos )
			.append( "\n    - candidato: " ).append( candidados )
			.append( "\n    - nivelRegional: " ).append( filtroNivelRegional.getNomeDescritivo() )
			.append( "\n    - regiao: " ).append( regioes )
			.append("]\n");

		return sb.toString();
	}

	public void setAnoEleicao(String[] anos) {
		for (String ano: anos)
			this.addAnoEleicao(ano);
	}

	public void setCandidados(String[] candidatos) {
		for (String candidato : candidatos)
			this.addCanditado(candidato);
	}

	public void setPartidos(String[] partidos) {
		for (String partido : partidos)
			this.addPartido(partido);
	}

	public void setRegioes(String[] regioes) {
		for (String regiao : regioes)
			this.addRegiao(regiao);
	}

    public void setFiltroNivelRegional(AgregacaoRegional filtroNivelRegional) {
        this.filtroNivelRegional = filtroNivelRegional;
    }
	public void setFiltroNivelRegional(Integer filtroNivelRegional) {
		this.filtroNivelRegional = AgregacaoRegional.findByNivel(filtroNivelRegional);
	}

	public void setCamposEscolhidos(String[] campos) {
		for (String campo : campos)
			this.addCampoEscolhido(campo);
	}
}
