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
/*
 * Essa classe foi criada apenas para "teste" de conexão com o Servidor de Banco de Dados.
 * Fica apenas para "apreciação".
 */

package br.fgv.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class UtilHibernate {

	public static void main(String[] args) {

		System.out.println("Conectando ao BD...");
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("default");

		EntityManager em = emf.createEntityManager();
		

		System.out.println("Fechando conexão...");
		em.close();
		emf.close();

	}
}
