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
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import br.fgv.business.AgregacaoPolitica;
import br.fgv.business.AgregacaoRegional;
import br.fgv.util.Par;

public abstract class TSEDadosAuxiliares {

	private static final List<AgregacaoRegional> NIVEL_AGREGACAO_REGIONAL = new ArrayList<AgregacaoRegional>();
	private static final List<AgregacaoPolitica> NIVEL_AGREGACAO_POLITICA = new ArrayList<AgregacaoPolitica>();

	static {
        NIVEL_AGREGACAO_REGIONAL.add(AgregacaoRegional.MACRO_REGIAO);
        NIVEL_AGREGACAO_REGIONAL.add(AgregacaoRegional.UF);
        NIVEL_AGREGACAO_REGIONAL.add(AgregacaoRegional.MESO_REGIAO);
        NIVEL_AGREGACAO_REGIONAL.add(AgregacaoRegional.MICRO_REGIAO);
        NIVEL_AGREGACAO_REGIONAL.add(AgregacaoRegional.UF_ZONA);
        NIVEL_AGREGACAO_REGIONAL.add(AgregacaoRegional.MUNICIPIO);

		///////////////////////////////
        NIVEL_AGREGACAO_POLITICA.add(AgregacaoPolitica.PARTIDO);
        NIVEL_AGREGACAO_POLITICA.add(AgregacaoPolitica.CANDIDATO);
	}

	public static List<AgregacaoRegional> getNivelAgregacaoRegional() {
		return Collections.unmodifiableList(NIVEL_AGREGACAO_REGIONAL);
	}

	public static List<AgregacaoPolitica> getNivelAgregacaoPolitica() {
		return Collections.unmodifiableList(NIVEL_AGREGACAO_POLITICA);
	}

}
