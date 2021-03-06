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
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

	<link type="text/css" rel="stylesheet" href="css/autoSuggest.css"/>

    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <script src="bootstrap/js/bootstrap.js"></script>
	<script src="js/jquery.scrollTo-min.js"></script>
	<script type="text/javascript" src="js/bootstrap-multiselect.js"></script>
	<script type="text/javascript" src="js/jquery.autoSuggest.w.js"></script>
	<script src="<c:url value='/js/jquery.fileDownload.js' />" type="text/javascript" ></script>


	<script>
	printDivCSS = new String ('<link href="bootstrap/css/bootstrap-responsive.css" rel="stylesheet">')
	function printDiv(divId) {
		_gaq.push(['_trackPageview', '/ajuda/print']);
		var content = $('#' + divId).clone();
		content.children('#print_btn').remove();
	    window.frames["print_frame"].document.body.innerHTML=printDivCSS + content.html();
	    window.frames["print_frame"].window.focus();
	    window.frames["print_frame"].window.print();
	}
	</script>
		
  <div class="row-fluid">

	<iframe name=print_frame width=0 height=0 frameborder=0 src=about:blank></iframe>
	
	 <div id="dicionario">
		 
		 <h1>Dicionário das colunas</h1>
		 
		 
		 <div id='print_btn' class="alert alert-info">
  			<a href="#" class="btn btn-mini btn-primary" onclick="printDiv('dicionario')">Imprimir</a>
  			<a href="<c:url value='/ajudaCsv' />" class="btn btn-mini btn-primary" onclick="_gaq.push(['_trackPageview', '/ajuda/ajuda_cepesp-data.csv'])">Exportar .CSV</a>
		</div>
		 
		 
		 <c:forEach var="ajuda" items="${ajudaTabela}">
	          <h2>${ajuda.nome}</h2>
	          
			<table class="table table-striped">
              <thead>
                <tr>
                  <th>Coluna no CSV</th>
                  <th>Nome no formulário</th>
                  <th>Descrição</th>
                </tr>
              </thead>
              <tbody>
                
	          		<c:forEach var="item" items="${ajuda.itens}">
	          			<tr>
		                  <td>${item.nome}</td>
		                  <td>${item.descricao}</td>
		                  <td>${item.detalhes}</td>
		                </tr>
	          		</c:forEach>
	          
	          
              </tbody>
            </table>
	          
	      </c:forEach>
      
	</div>	
  </div> 
      <!-- Page End -->
