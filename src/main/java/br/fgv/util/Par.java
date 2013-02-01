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

import java.io.Serializable;

import com.google.common.base.Objects;

public class Par implements Serializable {

	private static final long serialVersionUID = -4571959244266063114L;
	private final String chave;
	private final String valor;

	public Par(String chave, String valor) {
		this.chave = chave;
		this.valor = valor;
	}

	public String getChave() {
		return chave;
	}

	public String getValor() {
		return valor;
	}

	@Override
	public String toString() {
		return "(" + chave + ":" + valor + ")";
	}

	public boolean equals(Object obj) {

		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Par other = (Par) obj;

		return Objects.equal(this.chave, other.chave)
				&& Objects.equal(this.valor, other.valor);

	};

	@Override
	public int hashCode() {
		return Objects.hashCode(this.chave, this.valor);
	}
}
