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

import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.caelum.vraptor.ioc.Component;

@Component
public class DaoFactoryImpl implements DaoFactory {

	private Session session;
	private Transaction transaction;

	public DaoFactoryImpl(Session session) {
		this.session = session;
	}

	public void beginTransaction() {
		this.transaction = session.beginTransaction();
	}

	public void commit() {
		this.transaction.commit();
		this.transaction = null;
	}

	public boolean hasTransaction() {
		return this.transaction != null;
	}

	public void rollback() {
		this.transaction.rollback();
		this.transaction = null;
	}

	public void close() {
		this.session.close();
	}

	public ResultadosDAO getResultadosDAO() {
		return new ResultadosDAO(this.session);
	}
}
