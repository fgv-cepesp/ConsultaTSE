package br.fgv.dao;

import br.fgv.business.AgregacaoRegional;
import br.fgv.util.ArgumentosBusca;
import br.fgv.util.QueryBuilder;
import com.sun.org.apache.bcel.internal.generic.RETURN;


public class QueryConsolidadoBuilder {

    private final ArgumentosBusca args;
    private final String ano;
    private final QueryBuilder qb = new QueryBuilder();

    public QueryConsolidadoBuilder(ArgumentosBusca args, String ano) {
        this.args = args;
        this.ano = ano;
    }

    public String build() {
        String reg = args.getNivelRegional().getCamposAgregar();

        if (args.getNivelRegional().equals(AgregacaoRegional.MUNICIPIO))
            reg = "cod"; //Instead of mun_cod


        qb.select_();

        if (args.getNivelRegional().equals(AgregacaoRegional.UF_ZONA))
            qb.comma("m.uf", "mc.zona", "mc.cargo");
        else
            qb.comma("m." + reg, "mc.cargo");

        qb
            .comma_().sum("mc.qtd_aptos")._as_().valor("eleitores_aptos")
            .comma_().sum("(mc.qt_votos_legenda + mc.qt_votos_nominais)")._as_().valor("votos_validos")
            .comma_().sum("(((mc.qt_votos_legenda + mc.qt_votos_nominais) + mc.qt_votos_brancos) + mc.qt_votos_nulos)")._as_().valor("votos_totais_consolidado")
            .comma_().sum("mc.qt_votos_brancos")._as_().valor("votos_brancos")
            .comma_().sum("mc.qt_votos_nulos")._as_().valor("votos_nulos")

            ._from_().comma("munzona_detalhe_consolidado as mc", "aux_municipio as m")
                ._where_().eq("m.cod", "mc.codigo_municipio")
                    ._and_().eq("mc.ano", ano);

        if (args.getNivelRegional().equals(AgregacaoRegional.UF_ZONA))
            qb._group_by_().comma("m.uf", "mc.zona", "mc.cargo");
        else
            qb._group_by_().comma("m." + reg, "mc.cargo");

        return qb.toString(ano);
    }
}
