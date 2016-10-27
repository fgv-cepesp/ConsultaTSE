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

import java.util.*;

import br.fgv.util.Par;

public abstract class DadosAuxiliares {

	private static final Map<String, String> ESTADOS = new HashMap<String, String>();
	private static final Map<Integer, String> ESCOLARIDADE = new HashMap<Integer, String>();
	private static final Map<String, String> CARGO_POLITICO = new HashMap<String, String>();
	private static final Map<Integer, String> MESES = new HashMap<Integer, String>();

	static {

		ESTADOS.put("AC", "Acre");
		ESTADOS.put("AL", "Alagoas");
		ESTADOS.put("AP", "Amapá");
		ESTADOS.put("AM", "Amazonas");
		ESTADOS.put("BA", "Bahia");
		ESTADOS.put("CE", "Ceará");
		ESTADOS.put("DF", "Distrito Federal");
		ESTADOS.put("ES", "Espírito Santo");
		ESTADOS.put("GO", "Goiás");
		ESTADOS.put("MA", "Maranhão");
		ESTADOS.put("MT", "Mato Grosso");
		ESTADOS.put("MS", "Mato Grosso do Sul");
		ESTADOS.put("MG", "Minas Gerais");
		ESTADOS.put("PA", "Pará");
		ESTADOS.put("PB", "Paraíba");
		ESTADOS.put("PR", "Paraná");
		ESTADOS.put("PE", "Pernambuco");
		ESTADOS.put("PI", "Piauí");
		ESTADOS.put("RJ", "Rio de Janeiro");
		ESTADOS.put("RN", "Rio Grande do Norte");
		ESTADOS.put("RS", "Rio Grande do Sul");
		ESTADOS.put("RO", "Rondônia");
		ESTADOS.put("RR", "Roraima");
		ESTADOS.put("SC", "Santa Catarina");
		ESTADOS.put("SP", "São Paulo");
		ESTADOS.put("SE", "Sergipe");
		ESTADOS.put("TO", "Tocantins");


		//////////////////////////////////////////
		ESCOLARIDADE.put(1, "Primeiro Incompleto");
		ESCOLARIDADE.put(2, "Primeiro Completo");
		ESCOLARIDADE.put(3, "Segundo Incompleto");
		ESCOLARIDADE.put(4, "Segundo Completo");
		ESCOLARIDADE.put(5, "Superior Incompleto");
		ESCOLARIDADE.put(6, "Superior Completo");
		ESCOLARIDADE.put(7, "Mestrado Incompleto");
		ESCOLARIDADE.put(8, "Mestrado Completo");
		ESCOLARIDADE.put(9, "Doutorado Incompleto");
		ESCOLARIDADE.put(10, "Doutorado Completo");

		//////////////////////////////////////////
		CARGO_POLITICO.put("nenhum", "Não ocupava cargo político");
		CARGO_POLITICO.put("veread", "Vereador(a)");
		CARGO_POLITICO.put("depest", "Deputado(a) Estadual");
		CARGO_POLITICO.put("depfed", "Deputado(a) Federal");
		CARGO_POLITICO.put("senado", "Senador(a)");

		//////////////////////////////////////////
		MESES.put(1, "Janeiro");
		MESES.put(2, "Fevereiro");
		MESES.put(3, "Março");
		MESES.put(4, "Abril");
		MESES.put(5, "Maio");
		MESES.put(6, "Junho");
		MESES.put(7, "Julho");
		MESES.put(8, "Agosto");
		MESES.put(9, "Setembro");
		MESES.put(10, "Outubro");
		MESES.put(11, "Novembro");
		MESES.put(12, "Dezembro");
	}

	public static Map<String, String> getEstados() {
		return Collections.unmodifiableMap(ESTADOS);
	}
	public static Map<Integer, String> getEscolaridade() {
		return Collections.unmodifiableMap(ESCOLARIDADE);
	}
	public static Map<String, String> getCargoPolitico() {
		return Collections.unmodifiableMap(CARGO_POLITICO);
	}
	public static Map<Integer, String> getMeses() {
		return Collections.unmodifiableMap(MESES);
	}
}
