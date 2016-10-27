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

import br.fgv.model.Tabela;

public enum AgregacaoRegional {
	
	MACRO_REGIAO(Tabela.CO_FACT_VOTOS_MUN_MACRO.getNome(), "Macro-Região", 1), 
	UF(Tabela.CO_FACT_VOTOS_MUN_UF.getNome(),"UF", 2), 
	UF_ZONA(Tabela.CO_FACT_VOTOS_MUN_UF.getNome() + ", " + Tabela.CO_FACT_VOTOS_MUN_ZONA,"Zona Eleitoral", 3), 
	MESO_REGIAO(Tabela.CO_FACT_VOTOS_MUN_MESO.getNome(), "Meso-Região", 4), 
	MICRO_REGIAO(Tabela.CO_FACT_VOTOS_MUN_MICRO.getNome(), "Micro-Região", 5), 
	MUNICIPIO(Tabela.CO_FACT_VOTOS_MUN_COD_MUN.getNome(), "Município", 6);

	private final String nomeDescritivo;
	private final int nivel;
	private final String camposAgregar;

	private AgregacaoRegional(String camposAgregar, String nomeDescritivo, int nivel) {
		this.camposAgregar = camposAgregar;
		this.nomeDescritivo = nomeDescritivo;
		this.nivel = nivel;
	}

	public static AgregacaoRegional findByNivel(String nivel) {
		return findByNivel(Integer.parseInt(nivel));
	}


	public static AgregacaoRegional findByNivel(Integer nivel) {
		for (AgregacaoRegional e : AgregacaoRegional.values()) {
			if(e.getNivel() == nivel) {
				return e;
			}
		}
		return null;
	}

	public boolean equalsName(String otherName) {
		return (otherName == null) ? false : nomeDescritivo.equals(otherName);
	}

	public String toString() {
		return nomeDescritivo;
	}

	public String getCamposAgregar() {
		return camposAgregar;
	}

	public String getNomeDescritivo() {
		return nomeDescritivo;
	}

	public int getNivel() {
		return nivel;
	}

}
