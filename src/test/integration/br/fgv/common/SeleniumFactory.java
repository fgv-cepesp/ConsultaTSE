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
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

public class SeleniumFactory {
	private final Selenium selenium;
	
	public SeleniumFactory() {
		this.selenium = new DefaultSelenium("localhost", 4444, "*firefox", "http://localhost:8080");
		this.selenium.start();
		this.selenium.windowMaximize();
		this.selenium.setSpeed("400");
	}
	
	public Selenium getBrowser() {
		return selenium;
	}
	
	public void close() {
		selenium.stop();
	}
}