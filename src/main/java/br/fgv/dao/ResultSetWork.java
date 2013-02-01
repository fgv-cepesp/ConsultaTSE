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
package br.fgv.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.hibernate.jdbc.Work;

/*
 * http://www.informit.com/guides/content.aspx?g=java&seqNum=575
 * http://docs.jboss.org/hibernate/orm/3.5/reference/pt-BR/html/best-practices.html
 */
public class ResultSetWork implements Work {

	private String query;
	private ResultSet rs = null;
	private PreparedStatement ps = null;

	public void execute(Connection connection) throws SQLException {
		try {
			ps = connection.prepareStatement(query);
			rs = ps.executeQuery();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public String[] getColumnsName() {
		ResultSetMetaData metaData;
		String[] ret = null;
		try {
			metaData = rs.getMetaData();
			int colCount = metaData.getColumnCount();
			ret = new String[colCount];
			for (int i = 0; i < colCount; i++) {
				ret[i] = metaData.getColumnName(i + 1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}

	public ResultSet getResultSet() {
		return rs;
	}

	public void close() {
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
			}
		}
		if (ps != null) {
			try {
				ps.close();
			} catch (Exception e) {
			}
		}

	}

	public void setQuery(String query) {
		this.query = query;
	}

}
