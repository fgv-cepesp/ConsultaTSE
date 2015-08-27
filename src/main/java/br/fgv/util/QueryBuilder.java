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


import java.util.List;

import br.fgv.model.Coluna;
import br.fgv.model.Tabela;

import com.google.common.base.Joiner;

public class QueryBuilder {
	
	private final StringBuilder query = new StringBuilder();
	
	private static final Joiner COMMA_JOINER = Joiner.on(", ").skipNulls();
	
	public static final String a = "a";
	public static final String p = "p";
	public static final String s = "s";
	
	public static final String SELECT_ = "SELECT ";
	public static final String _STAR_ = " * ";
	public static final String _FROM_ = " FROM ";
	public static final String _DISTINCT_ = " DISTINCT ";
	public static final String _WHERE_ = " WHERE ";
	public static final String _AND_ = " AND ";
	public static final String COMMA_ = ", ";
	public static final String _AS_ = " as ";
	public static final String _ON_ = " on ";
	
	public static final String _GROUP_BY_ = " group by ";
	public static final String _ORDER_BY_ = " order by ";
	public static final String _LEFT_JOIN_ = " left join ";
	
	public static final String _UNION_DISTINCT_ = " UNION DISTINCT ";
	public static final String _UNION_ALL_ = " UNION ALL ";
	
	private static final Joiner UNION_DISTINCT_JOINER = Joiner.on(") " + _UNION_DISTINCT_ + " (").skipNulls();
	
	private static final Joiner UNION_ALL_JOINER = Joiner.on(") " + _UNION_ALL_ + " (").skipNulls();
	
	
	public QueryBuilder select_() {
		query.append(SELECT_);
		return this;
	}
	
	public QueryBuilder _distinct_() {
		query.append(_DISTINCT_);
		return this;
	}

	public QueryBuilder _star_() {
		query.append(_STAR_);
		return this;
	}
	
	public QueryBuilder _from_() {
		query.append(_FROM_);
		return this;
	}
	
	public QueryBuilder _where_() {
		query.append(_WHERE_);
		return this;
	}
	
	public QueryBuilder _and_() {
		query.append(_AND_);
		return this;
	}
	
	public QueryBuilder comma_() {
		query.append(COMMA_);
		return this;
	}
	
	public QueryBuilder _as_() {
		query.append(_AS_);
		return this;
	}
	
	public QueryBuilder _on_() {
		query.append(_ON_);
		return this;
	}
	
	public QueryBuilder _group_by_() {
		query.append(_GROUP_BY_);
		return this;
	}
	
	public QueryBuilder _order_by_() {
		query.append(_ORDER_BY_);
		return this;
	}
	
	public QueryBuilder _left_join_() {
		query.append(_LEFT_JOIN_);
		return this;
	}
	
	public QueryBuilder _ano_eleicao_() {
		query.append(Tabela.HOLDER_ANO_ELEICAO);
		return this;
	}
	
	public QueryBuilder _eq_() {
		query.append(" = ");
		return this;
	}
	
	public QueryBuilder a() {
		query.append(a);
		return this;
	}
	
	public QueryBuilder p() {
		query.append(p);
		return this;
	}
	
	public QueryBuilder s() {
		query.append(s);
		return this;
	}
	
	public QueryBuilder tabela(Tabela t) {
		query.append(t.getNome());
		return this;
	}
	
	public QueryBuilder coluna(Coluna c) {
		query.append(c.getNome());
		return this;
	}
	
	public QueryBuilder valor(String c) {
		query.append(c);
		return this;
	}
	
	public static String COMMA(Object... values) {
		return COMMA_JOINER.join(values);
	}
	
	public QueryBuilder comma(Object... values) {
		query.append( COMMA_JOINER.join(values) );
		return this;
	}
	
	public QueryBuilder comma(String... values) {
		return this.comma((Object[])values);
	}
	
	public QueryBuilder concat(Object... values) {
		query.append("concat( ").append( COMMA_JOINER.join(values) ).append(")");
		return this;
	}
	
	public QueryBuilder colunas(Object... values) {
		return comma(values);
	}
	
	public static String DECLARE_REF(Tabela table, String ref) {
		return table.toString() + " " + ref;
	}
	
	public QueryBuilder declareRef(Tabela table, String ref) {
		query.append(table.getNome()).append(" ").append(ref);
		return this;
	}
	
	public QueryBuilder declareRef(String table, String ref) {
		query.append("(").append(table).append(") ").append(ref);
		return this;
	}

	public static String REF(Coluna col, String ref) {
		return ref + "." + col;
	}
	
	public QueryBuilder ref(Coluna col, String ref) {
		query.append(ref).append(".").append(col.getNome());
		return this;
	}
	
	public static String TB_CO(Object tb, Object co) {
		return tb + "." + co;
	}
	
	public QueryBuilder tbCo(Object tb, Object co) {
		query.append(tb).append(".").append(co);
		return this;
	}
	
	public static String EQ(Object a, Object b) {
		return a + " = " + b;
	}
	
	public static String SQuote(String val) {
		return "'" + val + "'";
	}
	
	public QueryBuilder eq(Object a, Object b) {
		query.append(a).append(" = ").append(b);
		return this;
	}
	
	public static String IN(String... values) {
		return " in (" + COMMA_JOINER.join(values) + ") ";
	}
	
	public QueryBuilder in(String... values) {
		query.append(" in (").append(COMMA_JOINER.join(values)).append(") ");
		return this;
	}
	
	public static String SUM(Object... values) {
		return "sum(" + COMMA_JOINER.join(values) + ")";
	}
	
	public QueryBuilder sum(Object... values) {
		query.append("sum(").append(COMMA_JOINER.join(values)).append(")");
		return this;
	}
	
	public static String IFF(Object condicao, Object seTrue, Object seFalse) {
		return "if( " + COMMA(condicao, seTrue, seFalse) + ")";
	}
	
	public QueryBuilder iff(Object condicao, Object seTrue, Object seFalse) {
		query.append("if(").append(COMMA(condicao, seTrue, seFalse)).append(") ");
		return this;
	}
	
	public static String PAR(String value) {
		return "( " + value + " )";
	}
	
	public QueryBuilder par(String value) {
		query.append("( ").append(value).append(" )");
		return this;
	}
	
	public static String LIKE(String value) {
		return " like '%" + value + "%' ";
	}
	
	public QueryBuilder like(String value) {
		query.append(" like '%").append(value).append("%' ");
		return this;
	}
	
	public static String COMMA_WITH_TRAILING(Object... values) {
		if(values == null || values.length == 0) {
			return "";
		}
		return COMMA_JOINER.join(values) + COMMA_;
	}
	
	public QueryBuilder commaWithTrailing(Object... values) {
		if(values == null || values.length == 0) {
			return this;
		}
		query.append( COMMA_JOINER.join(values) ).append( COMMA_ );
		return this;
	}
	
	public String toString(String ano) {
		return query.toString().replaceAll(Tabela.HOLDER_ANO_ELEICAO, ano);
	}
	
	@Override
	public String toString() {
		return query.toString();
	}
	
	public static String unionDistinct(List<String> queries) {
		
		return "(" + UNION_DISTINCT_JOINER.join(queries) + ")";
	}
	
	public static String unionAll(List<String> queries) {
		
		return "(" + UNION_ALL_JOINER.join(queries) + ")";
	}

	public void replaceAll(String from, String to) {
		replaceAll(query, from, to);
		
	}
	
	public static void replaceAll(StringBuilder builder, String from, String to)
	{
	    int index = builder.indexOf(from);
	    while (index != -1)
	    {
	        builder.replace(index, index + from.length(), to);
	        index += to.length(); // Move to the end of the replacement
	        index = builder.indexOf(from, index);
	    }
	}
}
