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

		 <h1>Informações sobre os dados</h1>

		 <p>Os dados do CEPESPDATA são obtidos do <a href="http://www.tse.jus.br/hotSites/pesquisas-eleitorais/resultados.html">Repositório de Dados Eleitorais do TSE</a>.</p>

		<h2>Carga de Candidatos</h2>

		<c:set var="i" value="0" scope="page" />
		<c:set var="j" value="0" scope="page" />

		<table class="table table-hover">

		 <c:forEach var="linha" items="${mcand}">
		 	<c:if test="${i == 0}">
	          <thead>
			</c:if>
			<c:if test="${i != 0}">
	          <tbody>
			</c:if>
			    <tr>
	        <c:forEach var="col" items="${linha}">

		        <c:if test="${i == 0}">
		          <th>
				</c:if>
				<c:if test="${i != 0}">
		          <td>
				</c:if>
				${col}
				<c:if test="${i == 0}">
		          </th>
				</c:if>
				<c:if test="${i != 0}">
		          </td>
				</c:if>

	        	<c:set var="j" value="${j + 1}" scope="page"/>
	      	</c:forEach>

	      	<c:if test="${i == 0}">
	          </thead>
			</c:if>
			<c:if test="${i != 0}">
	          </tbody>
			</c:if>

	      	<c:set var="i" value="${i + 1}" scope="page"/>
	      </c:forEach>

	      </table>



	    <h2>Carga de Votos</h2>

		<c:set var="i" value="0" scope="page" />
		<c:set var="j" value="0" scope="page" />

		<table class="table table-hover">

		 <c:forEach var="linha" items="${mvoto}">
		 	<c:if test="${i == 0}">
	          <thead>
			</c:if>
			<c:if test="${i != 0}">
	          <tbody>
			</c:if>
			    <tr>
	        <c:forEach var="col" items="${linha}">

		        <c:if test="${i == 0}">
		          <th>
				</c:if>
				<c:if test="${i != 0}">
		          <td>
				</c:if>
				${col}
				<c:if test="${i == 0}">
		          </th>
				</c:if>
				<c:if test="${i != 0}">
		          </td>
				</c:if>

	        	<c:set var="j" value="${j + 1}" scope="page"/>
	      	</c:forEach>

	      	<c:if test="${i == 0}">
	          </thead>
			</c:if>
			<c:if test="${i != 0}">
	          </tbody>
			</c:if>

	      	<c:set var="i" value="${i + 1}" scope="page"/>
	      </c:forEach>

	      </table>

	</div>
  </div>
      <!-- Page End -->
