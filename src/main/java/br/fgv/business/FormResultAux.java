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

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import com.google.common.base.Objects;

import br.fgv.util.Par;

public class FormResultAux implements Serializable {

	private static final long serialVersionUID = -8961026753295064058L;
	private final Par[] camposOpcionais;
	private final Par[] camposFixos;

	public FormResultAux(List<Par> camposOpcionais, List<Par> camposFixos) {
		this.camposOpcionais = camposOpcionais.toArray(new Par[camposOpcionais.size()]);
		this.camposFixos = camposFixos.toArray(new Par[camposFixos.size()]);
	}

	public List<Par> getCamposOpcionaisXX() {
		return Arrays.asList(camposOpcionais);
	}

	public List<Par> getCamposFixosXX() {
		return Arrays.asList(camposFixos);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Opcionais: " + Arrays.toString(camposOpcionais))
				.append("\n")
				.append("Fixos: " + Arrays.toString(camposFixos));

		return sb.toString();
	}

	public boolean equals(Object obj) {

		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final FormResultAux other = (FormResultAux) obj;

		return Objects.equal(this.camposFixos, other.camposFixos)
				&& Objects.equal(this.camposOpcionais, other.camposOpcionais);

	};

	@Override
	public int hashCode() {
		return Objects.hashCode(this.camposFixos, this.camposFixos);
	}

}
