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

import static br.fgv.model.Coluna.Disponibilidade.DISPONIVEL;
import static br.fgv.model.Coluna.Disponibilidade.FIXO;
import static br.fgv.util.QueryBuilder.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.fgv.model.Coluna.Disponibilidade;
import br.fgv.util.Par;

public class Tabela {
	
	public static final String HOLDER_ANO_ELEICAO = "#ANO_ELEICAO#";

	/* 
	 * Tabela fact (votos) 
	 */
	public static final Tabela TB_FACT_VOTOS_MUN;

	public static final Coluna CO_FACT_VOTOS_MUN_ANO;
	public static final Coluna CO_FACT_VOTOS_MUN_TURNO;
	public static final Coluna CO_FACT_VOTOS_MUN_COD_MUN;
	public static final Coluna CO_FACT_VOTOS_MUN_ZONA;
	public static final Coluna CO_FACT_VOTOS_MUN_COD_CARGO;
	public static final Coluna CO_FACT_VOTOS_MUN_NR_VOTAVEL;
	public static final Coluna CO_FACT_VOTOS_MUN_TIPO_VOTAVEL;
	public static final Coluna CO_FACT_VOTOS_MUN_QNT_VOTOS;
	public static final Coluna CO_FACT_VOTOS_MUN_MACRO;
	public static final Coluna CO_FACT_VOTOS_MUN_MESO;
	public static final Coluna CO_FACT_VOTOS_MUN_MICRO;
	public static final Coluna CO_FACT_VOTOS_MUN_UF;
	public static final Coluna CO_FACT_VOTOS_MUN_PARTIDO;
	public static final Coluna CO_FACT_VOTOS_MUN_CANDIDATO_SK;

	/* Tabelas "Dim" */
	public static final Tabela TB_DIM_PARTIDOS;
	public static final Coluna CO_DIM_PARTIDOS_ANO;
	public static final Coluna CO_DIM_PARTIDOS_COD;
	public static final Coluna CO_DIM_PARTIDOS_SIGLA;
	public static final Coluna CO_DIM_PARTIDOS_NOME;

	
	public static final Tabela TB_DIM_CANDIDATOS;
	public static final Coluna CO_DIM_CANDIDATOS_CARGO_COD;
	public static final Coluna CO_DIM_CANDIDATOS_DATA_NASC;
	public static final Coluna CO_DIM_CANDIDATOS_SURROGATEKEY;
	public static final Coluna CO_DIM_CANDIDATOS_NOME;
	public static final Coluna CO_DIM_CANDIDATOS_TITULO;
	public static final Coluna CO_DIM_CANDIDATOS_NR_VOTAVEL;
	public static final Coluna CO_DIM_CANDIDATOS_UF;
	public static final Coluna CO_DIM_CANDIDATOS_SG_UE;
	public static final Coluna CO_DIM_CANDIDATOS_SIT_CANDIDATURA_COD;
	public static final Coluna CO_DIM_CANDIDATOS_PARTIDO_COD;
	public static final Coluna CO_DIM_CANDIDATOS_PARTIDO_SIG;
	public static final Coluna CO_DIM_CANDIDATOS_LEGENDA_COMPOSICAO;
	public static final Coluna CO_DIM_CANDIDATOS_LEGENDA_DES;
	public static final Coluna CO_DIM_CANDIDATOS_OCUPACAO_COD;
	public static final Coluna CO_DIM_CANDIDATOS_SEXO_COD;
	public static final Coluna CO_DIM_CANDIDATOS_GRAU_INSTRUCAO_COD;
	public static final Coluna CO_DIM_CANDIDATOS_EST_CIVIL_COD;
	public static final Coluna CO_DIM_CANDIDATOS_NACIONALIDADE_COD;
	public static final Coluna CO_DIM_CANDIDATOS_NASC_UF;
	public static final Coluna CO_DIM_CANDIDATOS_NASC_COD_MUN;
	public static final Coluna CO_DIM_CANDIDATOS_RESULTADO_COD;
	
	public static final Tabela TB_DIM_CARGO;
	public static final Coluna CO_DIM_CARGO_DS;
	public static final Coluna CO_DIM_CARGO_CD;

	public static final Tabela TB_DIM_MACROREGIAO;
	public static final Coluna CO_DIM_MACROREGIAO_NOME;
	public static final Coluna CO_DIM_MACROREGIAO_COD;
	
	public static final Tabela TB_DIM_ESTADOS;
	public static final Coluna CO_DIM_ESTADOS_ID;
	public static final Coluna CO_DIM_ESTADOS_NOME;
	public static final Coluna CO_DIM_ESTADOS_IBGE;
	public static final Coluna CO_DIM_ESTADOS_SIGLA;
	
	public static final Tabela TB_DIM_MESOREGIAO;
	public static final Coluna CO_DIM_MESOREGIAO_COD;
	public static final Coluna CO_DIM_MESOREGIAO_ID;
	public static final Coluna CO_DIM_MESOREGIAO_SIGLA_UF;
	public static final Coluna CO_DIM_MESOREGIAO_NOME;

	public static final Tabela TB_DIM_MICROREGIAO;
	public static final Coluna CO_DIM_MICROREGIAO_COD;
	public static final Coluna CO_DIM_MICROREGIAO_ID;
	public static final Coluna CO_DIM_MICROREGIAO_SIGLA_UF;
	public static final Coluna CO_DIM_MICROREGIAO_NOME;

	public static final Tabela TB_DIM_MUNICIPIO;
	public static final Coluna CO_DIM_MUNICIPIO_SIGLA_UF;
	public static final Coluna CO_DIM_MUNICIPIO_IBGE;
	public static final Coluna CO_DIM_MUNICIPIO_UF;
	public static final Coluna CO_DIM_MUNICIPIO_MESO;
	public static final Coluna CO_DIM_MUNICIPIO_SG_UE;
	public static final Coluna CO_DIM_MUNICIPIO_MICRO_DES;
	public static final Coluna CO_DIM_MUNICIPIO_MACRO_DES;
	public static final Coluna CO_DIM_MUNICIPIO_MESO_DES;
	public static final Coluna CO_DIM_MUNICIPIO_TP_UE;
	public static final Coluna CO_DIM_MUNICIPIO_MACRO;
	public static final Coluna CO_DIM_MUNICIPIO_MICRO;
	public static final Coluna CO_DIM_MUNICIPIO_COD;
	public static final Coluna CO_DIM_MUNICIPIO_NOME;
	
	public static final Tabela TB_DIM_VOTAVEIS;
	public static final Coluna CO_DIM_VOTAVEIS_NR_VOTAVEL;
	public static final Coluna CO_DIM_VOTAVEIS_TITULO;
	public static final Coluna CO_DIM_VOTAVEIS_SURROGATEKEY;
	public static final Coluna CO_DIM_VOTAVEIS_NOME_CANDIDATO;
	
	public static final Tabela TB_SIS_ANO_CARGO;
	public static final Coluna CO_SIS_ANO_CARGO_ANO;
	public static final Coluna CO_SIS_ANO_CARGO_COD_CARGO;

	public static final Tabela TB_SIS_ANOS;
	public static final Coluna CO_SIS_ANOS_ANO;


	/*outras constantes*/
	public static final int VOTO_NOMINAL_COD = 1;
	public static final int VOTO_LEGENDA_COD = 4;

	public static final String VOTO_NOMINAL = "voto_nominal";
	public static final String VOTO_LEGENDA = "voto_legenda";
	public static final String VOTO_TOTAL = "voto_total";
	
	public static final String REF_FACT = "r";
	
	private static final Map<String, Tabela> tabelas;
	private static Map<String, Tabela> __tabelas = new HashMap<String, Tabela>();
	
	private final String nome;
	private final String nomeDescritivo;
	private final Set<Coluna> colunas;
	private final String relacao;

	/*
	 * Aqui criamos as tabelas e suas colunas ...
	 */
	static {
		/* Colunas da tabela FACT VOTOS */

		CO_FACT_VOTOS_MUN_ANO = new Coluna("ano");
		CO_FACT_VOTOS_MUN_TURNO = new Coluna("turno");
		CO_FACT_VOTOS_MUN_COD_MUN = new Coluna("cod_mun", "código do município");
		CO_FACT_VOTOS_MUN_ZONA = new Coluna("zona");
		CO_FACT_VOTOS_MUN_COD_CARGO = new Coluna("cod_cargo", "código do cargo");
		CO_FACT_VOTOS_MUN_NR_VOTAVEL = new Coluna("nr_votavel", "número votável");
		CO_FACT_VOTOS_MUN_TIPO_VOTAVEL = new Coluna("tipo_votavel", "tipo votável");
		CO_FACT_VOTOS_MUN_QNT_VOTOS = new Coluna("qnt_votos", "quantidade de votos");
		CO_FACT_VOTOS_MUN_MACRO = new Coluna("macro", "macro-região");
		CO_FACT_VOTOS_MUN_MESO = new Coluna("meso", "meso-região");
		CO_FACT_VOTOS_MUN_MICRO = new Coluna("micro", "micro-região");
		CO_FACT_VOTOS_MUN_UF = new Coluna("uf");
		CO_FACT_VOTOS_MUN_PARTIDO = new Coluna("partido");
		CO_FACT_VOTOS_MUN_CANDIDATO_SK = new Coluna("candidato_sk");
		
		Set<Coluna> c = new HashSet<Coluna>();
		c.add(CO_FACT_VOTOS_MUN_ANO);
		c.add(CO_FACT_VOTOS_MUN_TURNO);
		c.add(CO_FACT_VOTOS_MUN_COD_MUN);
		c.add(CO_FACT_VOTOS_MUN_ZONA);
		c.add(CO_FACT_VOTOS_MUN_COD_CARGO);
		c.add(CO_FACT_VOTOS_MUN_NR_VOTAVEL);
		c.add(CO_FACT_VOTOS_MUN_TIPO_VOTAVEL);
		c.add(CO_FACT_VOTOS_MUN_QNT_VOTOS);
		c.add(CO_FACT_VOTOS_MUN_MACRO);
		c.add(CO_FACT_VOTOS_MUN_MESO);
		c.add(CO_FACT_VOTOS_MUN_MICRO);
		c.add(CO_FACT_VOTOS_MUN_UF);
		c.add(CO_FACT_VOTOS_MUN_PARTIDO);
		c.add(CO_FACT_VOTOS_MUN_CANDIDATO_SK);

		TB_FACT_VOTOS_MUN = new Tabela("voto_mun_" + HOLDER_ANO_ELEICAO, c);

		/* Colunas da tabela DIM PARTIDOS */
		CO_DIM_PARTIDOS_ANO = new Coluna("ano");
		CO_DIM_PARTIDOS_COD = new Coluna("cod_Partido", "Código", FIXO);
		CO_DIM_PARTIDOS_SIGLA = new Coluna("sigla_Partido", "Sigla", DISPONIVEL);
		CO_DIM_PARTIDOS_NOME = new Coluna("nome_Partido", "Nome", DISPONIVEL);

		c = new HashSet<Coluna>();
		c.add(CO_DIM_PARTIDOS_ANO);
		c.add(CO_DIM_PARTIDOS_COD);
		c.add(CO_DIM_PARTIDOS_SIGLA);
		c.add(CO_DIM_PARTIDOS_NOME);

		final String dim_partidos = "dim_partidos";
		TB_DIM_PARTIDOS = new Tabela(dim_partidos, "partido", c,
				EQ(TB_CO(dim_partidos, CO_DIM_PARTIDOS_COD), REF(CO_FACT_VOTOS_MUN_PARTIDO, REF_FACT))
				+ _AND_ + EQ(TB_CO(dim_partidos, CO_DIM_PARTIDOS_ANO), SQuote(HOLDER_ANO_ELEICAO)));

		/* Colunas da tabela DIM CANDIDATOS */
		CO_DIM_CANDIDATOS_SURROGATEKEY = new Coluna("surrogatekey");
		CO_DIM_CANDIDATOS_NOME = new Coluna("nome_Candidato", "Nome", DISPONIVEL);
		CO_DIM_CANDIDATOS_TITULO = new Coluna("titulo", "Título", DISPONIVEL);
		CO_DIM_CANDIDATOS_NR_VOTAVEL = new Coluna("nr_votavel", "Número", FIXO);
		CO_DIM_CANDIDATOS_UF = new Coluna("uf", "UF", DISPONIVEL);
		CO_DIM_CANDIDATOS_SG_UE = new Coluna("SG_UE", "SG UE", DISPONIVEL);
		CO_DIM_CANDIDATOS_CARGO_COD = new Coluna("cargo_cod", "Código Cargo", DISPONIVEL);
		CO_DIM_CANDIDATOS_SIT_CANDIDATURA_COD = new Coluna("sit_candidatura_cod", "Código Situação Candidatura", DISPONIVEL);
		CO_DIM_CANDIDATOS_PARTIDO_COD = new Coluna("partido_cod", "Código Partido", DISPONIVEL);
		CO_DIM_CANDIDATOS_PARTIDO_SIG = new Coluna("partido_sig", "Sigla Partido", DISPONIVEL);
		CO_DIM_CANDIDATOS_LEGENDA_COMPOSICAO = new Coluna("legenda_composicao", "Legenda Composisão", DISPONIVEL);
		CO_DIM_CANDIDATOS_LEGENDA_DES = new Coluna("legenda_des", "Legenda Des", DISPONIVEL);
		CO_DIM_CANDIDATOS_OCUPACAO_COD = new Coluna("ocupacao_cod", "Código Ocupação", DISPONIVEL);
		CO_DIM_CANDIDATOS_DATA_NASC = new Coluna("data_nasc","Data Nascimento", DISPONIVEL);
		CO_DIM_CANDIDATOS_SEXO_COD = new Coluna("sexo_cod", "Código Sexo", DISPONIVEL);
		CO_DIM_CANDIDATOS_GRAU_INSTRUCAO_COD = new Coluna("grau_instrucao_cod", "Código Instrução");
		CO_DIM_CANDIDATOS_EST_CIVIL_COD = new Coluna("est_civil_cod", "Código Estado Civil", DISPONIVEL);
		CO_DIM_CANDIDATOS_NACIONALIDADE_COD = new Coluna("nacionalidade_cod", "Código Nacionalidade", DISPONIVEL);
		CO_DIM_CANDIDATOS_NASC_UF = new Coluna("nasc_uf", "UF de Nascimento", DISPONIVEL);
		CO_DIM_CANDIDATOS_NASC_COD_MUN = new Coluna("nasc_cod_mun", "Código Município de Nascimento", DISPONIVEL);
		CO_DIM_CANDIDATOS_RESULTADO_COD = new Coluna("resultado_cod", "Código Resultado", DISPONIVEL);
		
		c = new HashSet<Coluna>();
		c.add(CO_DIM_CANDIDATOS_SURROGATEKEY);
		c.add(CO_DIM_CANDIDATOS_NOME);
		c.add(CO_DIM_CANDIDATOS_TITULO);
		c.add(CO_DIM_CANDIDATOS_NR_VOTAVEL);
		c.add(CO_DIM_CANDIDATOS_UF);
		c.add(CO_DIM_CANDIDATOS_SG_UE);
		c.add(CO_DIM_CANDIDATOS_CARGO_COD);
		c.add(CO_DIM_CANDIDATOS_SIT_CANDIDATURA_COD);
		c.add(CO_DIM_CANDIDATOS_PARTIDO_COD);
		c.add(CO_DIM_CANDIDATOS_PARTIDO_SIG);
		c.add(CO_DIM_CANDIDATOS_LEGENDA_COMPOSICAO);
		c.add(CO_DIM_CANDIDATOS_LEGENDA_DES);
		c.add(CO_DIM_CANDIDATOS_OCUPACAO_COD);
		c.add(CO_DIM_CANDIDATOS_DATA_NASC);
		c.add(CO_DIM_CANDIDATOS_SEXO_COD);
		c.add(CO_DIM_CANDIDATOS_GRAU_INSTRUCAO_COD);
		c.add(CO_DIM_CANDIDATOS_EST_CIVIL_COD);
		c.add(CO_DIM_CANDIDATOS_NACIONALIDADE_COD);
		c.add(CO_DIM_CANDIDATOS_NASC_UF);
		c.add(CO_DIM_CANDIDATOS_NASC_COD_MUN);
		c.add(CO_DIM_CANDIDATOS_RESULTADO_COD);

		final String dim_candidatos = "aux_candidatos_" + HOLDER_ANO_ELEICAO;
		TB_DIM_CANDIDATOS = new Tabela(dim_candidatos, "candidatos", c,
				EQ(TB_CO(dim_candidatos, CO_DIM_CANDIDATOS_SURROGATEKEY), REF(CO_FACT_VOTOS_MUN_CANDIDATO_SK, REF_FACT)));

		/* Colunas da tabela DIM CARGO */
		CO_DIM_CARGO_DS = new Coluna("DS_CARGO");
		CO_DIM_CARGO_CD = new Coluna("CD_CARGO");

		c = new HashSet<Coluna>();
		c.add(CO_DIM_CARGO_DS);
		c.add(CO_DIM_CARGO_CD);

		TB_DIM_CARGO = new Tabela("aux_cargo", c);

		/* Colunas da tabela DIM MACROREGIAO */
		CO_DIM_MACROREGIAO_NOME = new Coluna("nome_Macro", "Nome", DISPONIVEL);
		CO_DIM_MACROREGIAO_COD = new Coluna("cod_Macro", "Código", FIXO);

		c = new HashSet<Coluna>();
		c.add(CO_DIM_MACROREGIAO_NOME);
		c.add(CO_DIM_MACROREGIAO_COD);
		
		final String dim_macroregiao = "aux_macroregiao";
		TB_DIM_MACROREGIAO = new Tabela(dim_macroregiao,"macro-região", c,
				EQ(TB_CO(dim_macroregiao, CO_DIM_MACROREGIAO_COD), REF(CO_FACT_VOTOS_MUN_MACRO, REF_FACT)));

		/* Colunas da tabela DIM ESTADOS */
		CO_DIM_ESTADOS_ID = new Coluna("id");
		CO_DIM_ESTADOS_NOME = new Coluna("nome_Estado", "Nome", DISPONIVEL);
		CO_DIM_ESTADOS_IBGE = new Coluna("ibge", "Código IBGE", FIXO);
		CO_DIM_ESTADOS_SIGLA = new Coluna("sigla_Estado", "Sigla UF", DISPONIVEL);
		
		c = new HashSet<Coluna>();
		c.add(CO_DIM_ESTADOS_ID);
		c.add(CO_DIM_ESTADOS_NOME);
		c.add(CO_DIM_ESTADOS_IBGE);
		c.add(CO_DIM_ESTADOS_SIGLA);

		// TODO: aqui falta um campo fixo: zona para UF_ZONA
		final String dim_estados = "aux_estados";
		TB_DIM_ESTADOS = new Tabela(dim_estados, "estado", c,
				EQ(TB_CO(dim_estados, CO_DIM_ESTADOS_ID), REF(CO_FACT_VOTOS_MUN_UF, REF_FACT)));

		/* Colunas da tabela DIM MESOREGIAO */
		CO_DIM_MESOREGIAO_COD = new Coluna("cod_Meso", "Código", FIXO);
		CO_DIM_MESOREGIAO_ID = new Coluna("id");
		CO_DIM_MESOREGIAO_SIGLA_UF = new Coluna("sigla_UF", "Sigla UF", FIXO);
		CO_DIM_MESOREGIAO_NOME = new Coluna("nome_Meso", "Nome", DISPONIVEL);

		c = new HashSet<Coluna>();
		c.add(CO_DIM_MESOREGIAO_COD);
		c.add(CO_DIM_MESOREGIAO_ID);
		c.add(CO_DIM_MESOREGIAO_SIGLA_UF);
		c.add(CO_DIM_MESOREGIAO_NOME);
		
		final String dim_mesoregiao = "aux_mesoregiao";
		TB_DIM_MESOREGIAO = new Tabela(dim_mesoregiao,"meso-região", c,
				EQ(TB_CO(dim_mesoregiao, CO_DIM_MESOREGIAO_ID), REF(CO_FACT_VOTOS_MUN_MESO, REF_FACT)));

		/* Colunas da tabela DIM MICROREGIAO */
		CO_DIM_MICROREGIAO_COD = new Coluna("cod_Micro", "Código", FIXO);
		CO_DIM_MICROREGIAO_ID = new Coluna("id");
		CO_DIM_MICROREGIAO_SIGLA_UF = new Coluna("sigla_UF", "Sigla UF", FIXO);
		CO_DIM_MICROREGIAO_NOME = new Coluna("nome_Micro", "Nome", DISPONIVEL);

		c = new HashSet<Coluna>();
		c.add(CO_DIM_MICROREGIAO_COD);
		c.add(CO_DIM_MICROREGIAO_ID);
		c.add(CO_DIM_MICROREGIAO_SIGLA_UF);
		c.add(CO_DIM_MICROREGIAO_NOME);

		final String dim_microregiao = "aux_microregiao";
		TB_DIM_MICROREGIAO = new Tabela(dim_microregiao,"micro-região", c,
				EQ(TB_CO(dim_microregiao, CO_DIM_MICROREGIAO_ID), REF(CO_FACT_VOTOS_MUN_MICRO, REF_FACT)));

		/* Colunas da tabela DIM MUNICIPIO */
				
		CO_DIM_MUNICIPIO_SIGLA_UF = new Coluna("sigla_UF", "Sigla UF", FIXO);
		CO_DIM_MUNICIPIO_IBGE = new Coluna("ibge", "Código IBGE", FIXO);
		CO_DIM_MUNICIPIO_UF = new Coluna("uf");
		CO_DIM_MUNICIPIO_MESO = new Coluna("meso");
		CO_DIM_MUNICIPIO_SG_UE = new Coluna("SG_UE");
		CO_DIM_MUNICIPIO_MICRO_DES = new Coluna("micro_des", "Micro-região", DISPONIVEL);
		CO_DIM_MUNICIPIO_MACRO_DES = new Coluna("macro_des", "Macro-região", DISPONIVEL);
		CO_DIM_MUNICIPIO_MESO_DES = new Coluna("meso_des", "Meso-região", DISPONIVEL);
		CO_DIM_MUNICIPIO_TP_UE = new Coluna("TP_UE");
		CO_DIM_MUNICIPIO_MACRO = new Coluna("macro");
		CO_DIM_MUNICIPIO_MICRO = new Coluna("micro");
		CO_DIM_MUNICIPIO_COD = new Coluna("cod","Código TSE", DISPONIVEL);
		CO_DIM_MUNICIPIO_NOME = new Coluna("nome_Municipio","Nome", DISPONIVEL);
		
		c = new HashSet<Coluna>();
		c.add(CO_DIM_MUNICIPIO_SIGLA_UF);
		c.add(CO_DIM_MUNICIPIO_IBGE);
		c.add(CO_DIM_MUNICIPIO_UF);
		c.add(CO_DIM_MUNICIPIO_MESO);
		c.add(CO_DIM_MUNICIPIO_SG_UE);
		c.add(CO_DIM_MUNICIPIO_MICRO_DES);
		c.add(CO_DIM_MUNICIPIO_MACRO_DES);
		c.add(CO_DIM_MUNICIPIO_MESO_DES);
		c.add(CO_DIM_MUNICIPIO_TP_UE);
		c.add(CO_DIM_MUNICIPIO_MACRO);
		c.add(CO_DIM_MUNICIPIO_MICRO);
		c.add(CO_DIM_MUNICIPIO_COD);
		c.add(CO_DIM_MUNICIPIO_NOME);

		final String dim_municipio = "aux_municipio";
		TB_DIM_MUNICIPIO = new Tabela(dim_municipio, "município", c,
				EQ(TB_CO(dim_municipio, CO_DIM_MUNICIPIO_COD), REF(CO_FACT_VOTOS_MUN_COD_MUN, REF_FACT)));

		/* Colunas da tabela DIM VOTAVEIS */
		CO_DIM_VOTAVEIS_NR_VOTAVEL = new Coluna("nr_votavel");
		CO_DIM_VOTAVEIS_TITULO = new Coluna("titulo");
		CO_DIM_VOTAVEIS_SURROGATEKEY = new Coluna("surrogatekey");
		CO_DIM_VOTAVEIS_NOME_CANDIDATO = new Coluna("nome_Candidato");

		c = new HashSet<Coluna>();
		c.add(CO_DIM_VOTAVEIS_NR_VOTAVEL);
		c.add(CO_DIM_VOTAVEIS_TITULO);
		c.add(CO_DIM_VOTAVEIS_SURROGATEKEY);
		c.add(CO_DIM_VOTAVEIS_NOME_CANDIDATO);

		TB_DIM_VOTAVEIS = new Tabela("aux_votaveis", c);

		/* Colunas da tabela SIS ANO CARGO */
		CO_SIS_ANO_CARGO_ANO = new Coluna("ano");
		CO_SIS_ANO_CARGO_COD_CARGO = new Coluna("cod_cargo");
		
		c = new HashSet<Coluna>();
		c.add(CO_SIS_ANO_CARGO_ANO);
		c.add(CO_SIS_ANO_CARGO_COD_CARGO);

		TB_SIS_ANO_CARGO = new Tabela("sis_ano_cargo", c);

		/* Colunas da tabela SIS ANO CARGO */
		CO_SIS_ANOS_ANO = new Coluna("ano");

		c = new HashSet<Coluna>();
		c.add(CO_SIS_ANOS_ANO);

		TB_SIS_ANOS = new Tabela("sis_anos", c);
		
		// Fecho o mapa de tabelas
		tabelas = Collections.unmodifiableMap(__tabelas);
		__tabelas = null;
	}

	public Tabela(String nome, Set<Coluna> colunas) {
		this(nome, nome, colunas, null);
	}

	public Tabela(String nome, Set<Coluna> colunas, String relacao) {
		this(nome, nome, colunas, relacao);
	}

	public Tabela(String nome, String nomeDescritivo, Set<Coluna> colunas) {
		this(nome, nomeDescritivo, colunas, null);
	}
	
	public Tabela(String nome, String nomeDescritivo, Set<Coluna> colunas, String relacao) {
		this.nome = nome;
		this.nomeDescritivo = nomeDescritivo;
		this.colunas = Collections.unmodifiableSet(colunas);
		this.relacao = relacao;
		
		__tabelas.put(nome, this);
	}

	public String getNome() {
		return nome;
	}

	public String getNomeDescritivo() {
		return nomeDescritivo;
	}

	public String getRelacao() {
		return relacao;
	}

	public Set<Coluna> getColunas() {
		return this.colunas;
	}
	
	@Override
	public String toString() {
		return getNome();
	}
	
	public List<Par> getColunas(Disponibilidade disponibilidade) {
		List<Par> l = new ArrayList<Par>();
		String prefixo = getNome() + ".";
		String prefixoDescritivo = getNomeDescritivo() + ": ";
		for (Coluna c : getColunas()) {
			if(disponibilidade.equals(c.getDisponibilidade()))
				l.add(new Par(prefixo + c.getNome(), prefixoDescritivo
					+ c.getNomeDescritivo()));
		}
		
		return Collections.unmodifiableList(l);
	}
	
	public static Tabela byName(String nome) {
		return tabelas.get(nome);
	}
}
