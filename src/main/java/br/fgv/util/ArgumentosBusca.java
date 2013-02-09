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

import java.util.Arrays;

import br.fgv.business.AgregacaoPolitica;
import br.fgv.business.AgregacaoRegional;

public class ArgumentosBusca {

	private String[] anos;
	private String filtroCargo;
	private AgregacaoRegional nivelRegional;
	private AgregacaoPolitica nivelAgrecacaoPolitica;
	private String[] camposEscolhidos;
	private String[] filtroPartido;
	private String[] filtroCandidato;
	private String[] filtroRegional;

	public String[] getFiltroRegional() {
		return filtroRegional;
	}

	public void setFiltroRegional(String[] filtroRegional) {
		this.filtroRegional = filtroRegional;
	}

	public String[] getAnoEleicao() {
		return anos;
	}

	public void setAnoEleicao(String[] anos) {
		this.anos = anos;
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

	public AgregacaoPolitica getNivelAgrecacaoPolitica() {
		return nivelAgrecacaoPolitica;
	}

	public void setNivelAgrecacaoPolitica(AgregacaoPolitica nivelAgrecacaoPolitica) {
		this.nivelAgrecacaoPolitica = nivelAgrecacaoPolitica;
	}

	public String[] getCamposEscolhidos() {
		return camposEscolhidos;
	}

	public void setCamposEscolhidos(String[] camposEscolhidos) {
		this.camposEscolhidos = camposEscolhidos;
	}

	public String[] getFiltroPartido() {
		return filtroPartido;
	}

	public void setFiltroPartido(String[] filtroPartido) {
		this.filtroPartido = filtroPartido;
	}

	public String[] getFiltroCandidato() {
		return filtroCandidato;
	}

	public void setFiltroCandidato(String[] filtroCandidato) {
		this.filtroCandidato = filtroCandidato;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ArgumentosBusca[")
			.append( "\nanoEleicao: " ).append( Arrays.toString(anos) )
			.append( "\nfiltroCargo: " ).append( filtroCargo )
			.append( "\nnivelRegional: " ).append( nivelRegional )
			.append( "\nnivelAgrecacaoPolitica: " ).append( nivelAgrecacaoPolitica )
			.append( "\ncamposEscolhidos: " ).append( Arrays.toString(camposEscolhidos) )
			.append( "\nfiltroPartido: " ).append( Arrays.toString(filtroPartido) )
			.append( "\nfiltroCandidato: " ).append( Arrays.toString(filtroCandidato) )
			.append( "\nfiltroRegional: " ).append( Arrays.toString(filtroRegional) )
			.append("]\n");
		
		return sb.toString();
	}

}
