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
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class FactorySessionHSqlDB {
	Session session;
	private static SessionFactory factory;
	
	public FactorySessionHSqlDB() {
		if(factory==null){
			Configuration cfg = new Configuration();
			cfg.configure().setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect")
			.setProperty("hibernate.connection.url", "jdbc:hsqldb:mem:CGU")
			.setProperty("hibernate.connection.driver_class", "org.hsqldb.jdbcDriver")
			.setProperty("hibernate.connection.username", "sa")
			.setProperty("hibernate.connection.password", "")
			.setProperty("hibernate.show_sql", "false");
			
			session = cfg.buildSessionFactory().openSession();
		}
	}
	
	public Session getSession() {		
		return session;
	}

}
