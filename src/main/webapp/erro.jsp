<%--

    Copyright (c) 20012-2013 "FGV - CEPESP" [http://cepesp.fgv.br]

    This file is part of CEPESP-DATA.

    CEPESP-DATA is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    CEPESP-DATA is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with CEPESP-DATA. If not, see <http://www.gnu.org/licenses/>.

--%>
<%@ page isErrorPage="true"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<h1>UM ERRO ACONTECEU</h1>
1) Esta PAGINA DE ERRO FOI CHAMADA PELO CONTAINER POIS UMA
<br>
<b><%=exception.getClass().getCanonicalName()%> </b>
foi gerada.
<br>
2) A mensagem de erro gerada foi:
<br>
<b><%=exception.getMessage()%></b>
<br>
3) A Causa foi gerada foi:
<br>
<b><%=exception.getCause().getMessage()%></b>
<br>
4) Pilha:
<br>

<%   
    java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();  
    java.io.PrintStream errOut = new java.io.PrintStream(baos);  
    exception.printStackTrace(errOut);   

%>

<form action="<c:url value="/sendError"/>"  method="post" >
	<p>Comunique este erro ao Administrador clicando em Enviar.</p>
    <input type="submit" value="Enviar" />

    <textarea name="message" rows="2" cols="30" readonly="readonly" >
Informacoes sobre o erro:

1) Classe: 
<%=exception.getClass().getCanonicalName()%>

2) Mensagem: 
<%=exception.getMessage()%>

3) Causa:
<%=exception.getCause().getMessage()%>

4) Pilha:
  
===========================================
<%= baos.toString()%>
===========================================
    </textarea>

</form>
