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

import com.thoughtworks.selenium.Selenium;

public class WhenActions {
	private final Selenium browser;
	
	public WhenActions(Selenium browser) {
		this.browser = browser;
	}
	
	public void iSearchForWord(String word) {
		browser.type("word", word);
		browser.click("go");
	}

	public WhenActions iAmAtDictionaryEntrySearchPage() {
		browser.open("/dictionaryEntrySearch");
		return this;
	}
	
	public WhenActions and() {
		return this;
	}

	public void iSignUpAs(String username) {
		browser.click("link=Efetuar login");
		browser.type("user.name", username);
		browser.click("login");
	}

	public void iLogout() {
		browser.click("link=(logout)");
		
	}

	public void iSignUpAs(String username, String pass) {
		browser.click("link=login");
		browser.waitForPageToLoad("500");
		browser.type("name=usuario.login", username);
		browser.type("name=usuario.password", pass);
		browser.click("entrar");		
		browser.waitForPageToLoad("500");
	}

	
}
