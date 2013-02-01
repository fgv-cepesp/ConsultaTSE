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
package br.fgv.common;

import static junit.framework.Assert.assertTrue;

import com.thoughtworks.selenium.Selenium;


public class GivenContexts {
	private final Selenium browser;
	
	public GivenContexts(Selenium browser) {
		this.browser = browser;
	}
	
	public void thereIsAnAnonymousUser() {
		
	}
	
	public GivenContexts and() {
		return this;
	}

	public GivenContexts theUserDoesntExist(String string) {
		// TODO Verificação que o usuário ainda não existe, como não guarda usuário por enquanto não faz nada
		return this;
	}

	public GivenContexts iAmOnTheRootPage() {
		browser.open("/");
		return this;
	}

	public GivenContexts thereisAnUserNamed(String string) {
		// TODO usuários ainda não sao salvos no bd, mas posteriormente este metodo iria criar ele.
		return this;
	}

	public void iAmLoggedAs(String username) {
		browser.open("/");
		browser.click("link=Efetuar login");
		browser.type("username", username);
		browser.click("login");
	}

	public void iAmNotLoggedIn() {
		assertTrue(browser.isTextPresent("login"));		
	}


}
