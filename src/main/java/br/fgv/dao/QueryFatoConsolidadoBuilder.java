package br.fgv.dao;

import br.fgv.business.AgregacaoRegional;
import br.fgv.util.ArgumentosBusca;
import br.fgv.util.QueryBuilder;

public class QueryFatoConsolidadoBuilder {

    private final ArgumentosBusca args;
    private final String ano;
    private final String queryFato;
    private final String queryConsolidado;
    private final QueryBuilder queryBuilder = new QueryBuilder();


    public QueryFatoConsolidadoBuilder(ArgumentosBusca args, String ano, String queryFato, String queryConsolidado) {
        this.args = args;
        this.ano = ano;
        this.queryFato = queryFato;
        this.queryConsolidado = queryConsolidado;
    }

    public String build() {
        String reg = args.getNivelRegional().getCamposAgregar();
        if (args.getNivelRegional().equals(AgregacaoRegional.MUNICIPIO)) reg = "cod"; //Instead of mun_cod

        queryBuilder
                .select_().comma("mun.*", "c.eleitores_aptos", "c.votos_validos", "c.votos_totais_consolidado", "c.votos_brancos", "c.votos_nulos")
                ._from_().par(queryFato)._as_().valor("mun")
                ._left_join_().par(queryConsolidado)._as_().valor("c")
                ._on_()
                    .eq("c." + reg, "mun." + reg)
                    ._and_()
                    .eq("c.cargo", args.getFiltroCargo());

        return queryBuilder.toString(ano);
    }
}
