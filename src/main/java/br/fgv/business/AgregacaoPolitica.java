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

import static br.fgv.model.Tabela.VOTO_LEGENDA;
import static br.fgv.model.Tabela.VOTO_NOMINAL;
import static br.fgv.model.Tabela.VOTO_TOTAL;
import br.fgv.model.Tabela;

public enum AgregacaoPolitica {

	PARTIDO(Tabela.CO_FACT_VOTOS_MUN_PARTIDO.getNome(), "Partido", 1, VOTO_NOMINAL, VOTO_LEGENDA, VOTO_TOTAL),
	CANDIDATO(Tabela.CO_FACT_VOTOS_MUN_CANDIDATO_SK.getNome(), "Candidato", 2, VOTO_NOMINAL, VOTO_TOTAL),
	COLIGACAO(Tabela.CO_FACT_VOTOS_MUN_LEGENDA_SK.getNome(), "Coligação", 3, VOTO_NOMINAL, VOTO_LEGENDA, VOTO_TOTAL);


	private final String nomeDescritivo;
	private final int nivel;
	private final String nome;
	private String[] colunas;


	private AgregacaoPolitica(String nome, String nomeDescritivo, int nivel, String ... colunas) {
		this.nome = nome;
		this.nomeDescritivo = nomeDescritivo;
		this.nivel = nivel;
		this.colunas = colunas;
	}

	public static AgregacaoPolitica findByNivel(String nivel) {
		return findByNivel(Integer.parseInt(nivel));
	}

	public static AgregacaoPolitica findByNivel(int nivel) {
		for (AgregacaoPolitica e : AgregacaoPolitica.values()) {
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

	public String getNome() {
		return nome;
	}

	public String getNomeDescritivo() {
		return nomeDescritivo;
	}

	public int getNivel() {
		return nivel;
	}

	public String[] getColunas() {
		return this.colunas;
	}
}
