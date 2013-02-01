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

import br.fgv.util.Par;

public abstract class DadosAuxiliares {

	private static final List<Par> ESTADOS;
	private static final List<Par> ESCOLARIDADE;
	private static final List<Par> CARGO_POLITICO;
	private static final List<Par> MESES;

	static {

		List<Par> l = new ArrayList<Par>();

		l.add(new Par("  ", "--Selecionar---"));
		l.add(new Par("AC", "Acre"));
		l.add(new Par("AL", "Alagoas"));
		l.add(new Par("AP", "Amapá"));
		l.add(new Par("AM", "Amazonas"));
		l.add(new Par("BA", "Bahia"));
		l.add(new Par("CE", "Ceará"));
		l.add(new Par("DF", "Distrito Federal"));
		l.add(new Par("ES", "Espírito Santo"));
		l.add(new Par("GO", "Goiás"));
		l.add(new Par("MA", "Maranhão"));
		l.add(new Par("MT", "Mato Grosso"));
		l.add(new Par("MS", "Mato Grosso do Sul"));
		l.add(new Par("MG", "Minas Gerais"));
		l.add(new Par("PA", "Pará"));
		l.add(new Par("PB", "Paraíba"));
		l.add(new Par("PR", "Paraná"));
		l.add(new Par("PE", "Pernambuco"));
		l.add(new Par("PI", "Piauí"));
		l.add(new Par("RJ", "Rio de Janeiro"));
		l.add(new Par("RN", "Rio Grande do Norte"));
		l.add(new Par("RS", "Rio Grande do Sul"));
		l.add(new Par("RO", "Rondônia"));
		l.add(new Par("RR", "Roraima"));
		l.add(new Par("SC", "Santa Catarina"));
		l.add(new Par("SP", "São Paulo"));
		l.add(new Par("SE", "Sergipe"));
		l.add(new Par("TO", "Tocantins"));

		ESTADOS = Collections.unmodifiableList(l);

		// ////////////////////////////////////////
		l = new ArrayList<Par>();
		l.add(new Par("0", "--Selecionar---"));
		l.add(new Par("1", "Primeiro Incompleto"));
		l.add(new Par("2", "Primeiro Completo"));
		l.add(new Par("3", "Segundo Incompleto"));
		l.add(new Par("4", "Segundo Completo"));
		l.add(new Par("5", "Superior Incompleto"));
		l.add(new Par("6", "Superior Completo"));
		l.add(new Par("7", "Mestrado Incompleto"));
		l.add(new Par("8", "Mestrado Completo"));
		l.add(new Par("9", "Doutorado Incompleto"));
		l.add(new Par("10", "Doutorado Completo"));

		ESCOLARIDADE = Collections.unmodifiableList(l);

		// ////////////////////////////////////////
		l = new ArrayList<Par>();
		l.add(new Par("0", "--Selecionar---"));
		l.add(new Par("nenhum", "Não ocupava cargo político"));
		l.add(new Par("veread", "Vereador(a)"));
		l.add(new Par("depest", "Deputado(a) Estadual"));
		l.add(new Par("depfed", "Deputado(a) Federal"));
		l.add(new Par("senado", "Senador(a)"));

		CARGO_POLITICO = Collections.unmodifiableList(l);

		// ////////////////////////////////////////
		l = new ArrayList<Par>();
		l.add(new Par("0", "--Selecionar---"));
		l.add(new Par("1", "Janeiro"));
		l.add(new Par("2", "Fevereiro"));
		l.add(new Par("3", "Março"));
		l.add(new Par("4", "Abril"));
		l.add(new Par("5", "Maio"));
		l.add(new Par("6", "Junho"));
		l.add(new Par("7", "Julho"));
		l.add(new Par("8", "Agosto"));
		l.add(new Par("9", "Setembro"));
		l.add(new Par("10", "Outubro"));
		l.add(new Par("11", "Novembro"));
		l.add(new Par("12", "Dezembro"));

		MESES = Collections.unmodifiableList(l);

	}

	public static List<Par> getEstados() {
		return ESTADOS;
	}

	public static List<Par> getEscolaridade() {
		return ESCOLARIDADE;
	}

	public static List<Par> getCargoPolitico() {
		return CARGO_POLITICO;
	}

	public static List<Par> getMeses() {
		return MESES;
	}
}
