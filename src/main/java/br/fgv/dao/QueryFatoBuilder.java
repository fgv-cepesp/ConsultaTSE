package br.fgv.dao;

import br.fgv.business.AgregacaoPolitica;
import br.fgv.business.AgregacaoRegional;
import br.fgv.business.BusinessImpl;
import br.fgv.util.ArgumentosBusca;
import br.fgv.util.QueryBuilder;

import java.util.List;

import static br.fgv.model.Tabela.*;
import static br.fgv.model.Tabela.CO_FACT_VOTOS_MUN_TURNO;
import static br.fgv.util.QueryBuilder.EQ;
import static br.fgv.util.QueryBuilder.IFF;

public class QueryFatoBuilder {

    private ArgumentosBusca args;
    private String ano;
    private QueryBuilder qb = new QueryBuilder();

    public QueryFatoBuilder(ArgumentosBusca args, String ano) {
        this.args = args;
        this.ano = ano;
    }

    public String build()
    {
        String reg = args.getNivelRegional().getCamposAgregar();
        String pol = args.getNivelAgrecacaoPolitica().getNome();

        // SELECT
        qb.select_().comma(CO_FACT_VOTOS_MUN_TURNO, reg, pol);
        qb.comma_().sum(IFF( EQ(CO_FACT_VOTOS_MUN_TIPO_VOTAVEL, VOTO_NOMINAL_COD), CO_FACT_VOTOS_MUN_QNT_VOTOS, 0))._as_().valor(VOTO_NOMINAL);
        qb.comma_().sum(CO_FACT_VOTOS_MUN_QNT_VOTOS)._as_().valor(VOTO_TOTAL);

        if(AgregacaoPolitica.PARTIDO.equals(args.getNivelAgrecacaoPolitica()) || AgregacaoPolitica.COLIGACAO.equals(args.getNivelAgrecacaoPolitica())) {
            qb.comma_().sum(IFF( EQ(CO_FACT_VOTOS_MUN_TIPO_VOTAVEL, VOTO_LEGENDA_COD), CO_FACT_VOTOS_MUN_QNT_VOTOS, 0))._as_().valor(VOTO_LEGENDA);
        }

        qb._from_().tabela(TB_FACT_VOTOS_MUN);

        // WHERE
        qb._where_().eq(CO_FACT_VOTOS_MUN_COD_CARGO, args.getFiltroCargo())
                ._and_().eq(CO_FACT_VOTOS_MUN_TURNO, args.getTurno());


        if (args.hasFiltroNivelRegional()) {
            List<String> regioes = args.getRegioes();
            qb._and_().valor(args.getFiltroNivelRegional().getCamposAgregar()).in(regioes.toArray(new String[regioes.size()]));
        }

        if (args.hasPartidos()) {
            List<String> partidos = args.getPartidos();
            qb._and_().valor(AgregacaoPolitica.PARTIDO.getNome()).in(partidos.toArray(new String[partidos.size()]));
        }

        // GROUP BY
        qb._group_by_().comma(reg, pol, CO_FACT_VOTOS_MUN_TURNO)._order_by_().comma(reg, pol, CO_FACT_VOTOS_MUN_TURNO);

        return qb.toString(ano);
    }

}
