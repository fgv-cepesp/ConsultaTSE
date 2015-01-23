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

import br.fgv.business.AgregacaoPolitica;
import br.fgv.business.AgregacaoRegional;
import br.fgv.util.Par;

public abstract class TSEDadosAuxiliares {

	private static final List<Par> NIVEL_AGREGACAO_REGIONAL;
	private static final List<Par> NIVEL_AGREGACAO_POLITICA;

	static {
		List<Par> l = new ArrayList<Par>();
		l.add(new Par("", "--Selecionar---"));
		l.add(new Par(Integer.toString(AgregacaoRegional.MACRO_REGIAO.getNivel()), AgregacaoRegional.MACRO_REGIAO.getNomeDescritivo()));
		l.add(new Par(Integer.toString(AgregacaoRegional.UF.getNivel()), AgregacaoRegional.UF.getNomeDescritivo()));
		l.add(new Par(Integer.toString(AgregacaoRegional.MESO_REGIAO.getNivel()), AgregacaoRegional.MESO_REGIAO.getNomeDescritivo()));
		l.add(new Par(Integer.toString(AgregacaoRegional.MICRO_REGIAO.getNivel()), AgregacaoRegional.MICRO_REGIAO.getNomeDescritivo()));
		l.add(new Par(Integer.toString(AgregacaoRegional.UF_ZONA.getNivel()), AgregacaoRegional.UF_ZONA.getNomeDescritivo()));
		l.add(new Par(Integer.toString(AgregacaoRegional.MUNICIPIO.getNivel()), AgregacaoRegional.MUNICIPIO.getNomeDescritivo()));

        NIVEL_AGREGACAO_REGIONAL = Collections.unmodifiableList(l);

		///////////////////////////////
        l = new ArrayList<Par>();
		l.add(new Par("", "--Selecionar---"));
		l.add(new Par(Integer.toString(AgregacaoPolitica.PARTIDO.getNivel()), AgregacaoPolitica.PARTIDO.getNomeDescritivo()));
		l.add(new Par(Integer.toString(AgregacaoPolitica.CANDIDATO.getNivel()), AgregacaoPolitica.CANDIDATO.getNomeDescritivo()));
		l.add(new Par(Integer.toString(AgregacaoPolitica.COLIGACAO.getNivel()), AgregacaoPolitica.COLIGACAO.getNomeDescritivo()));

		NIVEL_AGREGACAO_POLITICA = Collections.unmodifiableList(l);

	}

	public static List<Par> getNivelAgregacaoRegional() {

		return NIVEL_AGREGACAO_REGIONAL;
	}

	public static List<Par> getNivelAgregacaoPolitica() {
		return NIVEL_AGREGACAO_POLITICA;
	}

}
