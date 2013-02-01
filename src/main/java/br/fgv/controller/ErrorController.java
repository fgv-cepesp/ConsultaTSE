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
package br.fgv.controller;

import org.apache.commons.mail.EmailException;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.fgv.dao.DaoFactory;
import br.fgv.util.EmailSender;

@Resource
public class ErrorController {

    private final Result result;

    public ErrorController(Result result, DaoFactory daoFactory) {
        this.result = result;
    }

    @Post
    @Path("/sendError")
    public void sendError(String message) {
        String user = "anonimo";
        try {
            EmailSender.enviarEmail(message, "[" + user + "] Falha no sistema.");
        } catch (EmailException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        result.redirectTo(IndexController.class).index();
    }

}