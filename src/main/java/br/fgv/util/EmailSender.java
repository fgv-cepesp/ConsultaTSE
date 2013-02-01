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

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class EmailSender {
	
	public static void enviarEmail(String body) throws EmailException{
		enviarEmail(body, "Sem assunto.");
	}
	
	public static void enviarEmail(String body, String subject) throws EmailException{
		
		StringBuffer sb = new StringBuffer("");
		sb.append(body);
		
		Email email = new SimpleEmail();
		email.setHostName("smtp.gmail.com");
		
		email.setDebug(true);
		email.setSSLOnConnect(true);

		email.addTo("wesley.seidel@gmail.com");
		
		email.setAuthentication("wseidel.fgv", "batavinhofgv");
		
		email.setFrom("wseidel.fgv@gmail.com");
		email.setSubject(subject);
		email.setMsg(sb.toString());
		
		email.send();
	}
	
	
	
	

	public static void main(String[] args) {
		try {
			enviarEmail("Mais um teste !", "Falha no sistema.");
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
