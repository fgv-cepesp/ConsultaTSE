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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AjudaTabela {

	private final Tabela tabela;
	private final List<ItemAjuda> itens;
	
	public AjudaTabela(Tabela aTabela) {
		tabela = aTabela;
		List<ItemAjuda> i = new ArrayList<AjudaTabela.ItemAjuda>();
		for (Coluna c : tabela.getColunas()) {
			if(c.isFixo() || c.isDisponivel()) {
				i.add(new ItemAjuda(tabela.getNome(), c));
			}
		}
		itens = Collections.unmodifiableList(i);
	}
	
	public String getNome() {
		return tabela.getNomeDescritivo();
	}
	
	public List<ItemAjuda> getItens() {
		return itens;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("# " + getNome() + "\n");
		
		for (ItemAjuda i : itens) {
			sb.append(" * ").append(i.getNome()).append(": ").append(i.getDescricao()).append("\n");
		}
		sb.append("\n");
		return sb.toString();
	}
	
	public static class ItemAjuda {
		
		private final String tab;
		private final Coluna col;

		public ItemAjuda(String tab, Coluna coluna) {
			this.col = coluna;
			this.tab = tab.replace("_" + Tabela.HOLDER_ANO_ELEICAO, "");
		}

		public String getDescricao() {
			return col.getNomeDescritivo();
		}

		public String getDetalhes() {
			return AjudaMessages.getString(tab + '.' + col.getNome());
		}
		
		public String getNome() {
			return col.getNome();
		}
		
	}
	
	public static void main(String[] args) {
		AjudaTabela ia = new AjudaTabela(Tabela.TB_DIM_CANDIDATOS);
		
		System.out.println(ia);
	}
	
}
