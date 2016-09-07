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
<%@ taglib tableName="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib tableName="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link type="text/css" rel="stylesheet" href="css/autoSuggest.css" />

<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="bootstrap/js/bootstrap.js"></script>

<div class="jumbotron">
	<h1 class="text-center">Bem-vindo ao CEPESPDATA</h1>
	<p>O Centro de Política e Economia do Setor Público (<a href="http://cepesp.fgv.br" target="_blank">CEPESP</a>) da
		Fundação Getulio Vargas (<a href="http://wwww.fgv.br" target="_blank">FGV</a>) disponibiliza uma série
		de bases de dados pesquisáveis. Veja abaixo as bases atuais.</p>
</div>

<div class="row">
	<div class="col-md-4">
		<h2>Eleições por cargo</h2>
		<p>Este formulário permite efetuar consultas às bases de dados relativas as eleições especificando um cargo eletivo. As consultas podem ser
		       também agregadas por partido e nível regional.</p>
		<p>
			<a class="btn btn-primary" href="<c:url value="/consultaResultados"/>">Consultar &raquo;</a>
		</p>
	</div>
	<!-- <div class="span4">
		<h2>Eleições por candidato</h2>
		<p>Este formulário permite efetuar consultas às bases de dados relativas as eleições especificando um ou mais candidatos.
		É possível acompanhar seu desempenho ao longo dos anos.</p>
		<p>
			<a class="btn" href="#">Em breve!</a>
		</p>
	</div> -->
</div>

<!-- Page End -->
