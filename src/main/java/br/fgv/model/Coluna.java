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
package br.fgv.model;

public class Coluna implements Comparable<Coluna>{

	private final String nome;
	private final String nomeDescritivo;
	private final Disponibilidade disponivel;
	private int order;
	
	private final String tabelaDescricao;

	public Coluna(String nome, String nomeDescritivo) {
		this(nome, nomeDescritivo, Disponibilidade.DESCONHECIDA);
	}

	public Coluna(String nome) {
		this(nome, nome);
	}

	public Coluna(String nome, String nomeDescritivo, Disponibilidade disponivel) {
		this.nome = nome;
		this.nomeDescritivo = nomeDescritivo;
		this.disponivel = disponivel;
		this.tabelaDescricao = null;
	}

	public Coluna(String nome, String nomeDescritivo, Disponibilidade disponivel, String tabelaDescricao) {
		this.nome = nome;
		this.nomeDescritivo = nomeDescritivo;
		this.disponivel = disponivel;
		this.tabelaDescricao = tabelaDescricao;
	}

	public Coluna(String nome, Disponibilidade disponivel) {
		this(nome, nome, disponivel);
	}

	public String getNome() {
		return this.nome;
	}

	public String getNomeDescritivo() {
		return nomeDescritivo;
	}

	public boolean isDisponivel() {
		return Disponibilidade.DISPONIVEL.equals(disponivel);
	}

	public boolean isFixo() {
		return Disponibilidade.FIXO.equals(disponivel);
	}
	
	public Disponibilidade getDisponibilidade() {
		return disponivel;
	}

	public static enum Disponibilidade {
		DESCONHECIDA,
		FIXO,
		DISPONIVEL,
		OCULTA
	}
	
	@Override
	public String toString() {
		return getNome();
	}

	public void setOrder(int i) {
		this.order = i;
	}

	public int compareTo(Coluna other) {
		if (this.order < other.order) {
            return -1;
        }
        if (this.order > other.order) {
            return 1;
        }
        return 0;
	}
	
	public boolean hasDescricao() {
		if(this.tabelaDescricao == null) {
			return false;
		}
		return true;
	}
	
	public String getTabelaDescricao() {
		return tabelaDescricao;
	}

	public String getDescricaoCol() {
		return getTabelaDescricao() +  ".value as " + getNome().replace("_cod", "_des");
	}
}
