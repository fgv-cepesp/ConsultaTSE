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
<ul id="menu" class="menu">

	<c:if test="${!usuarioLogado.logged}">
			<li><a href="<c:url value="/login"/>">login</a></li>
	</c:if>
	<c:if test="${usuarioLogado.logged}">


		<li><a href="<c:url value="/"/>">Home</a></li>
		
		<c:if test="${usuarioLogado.usuario.perfil eq 2}">
				<li><span>Cadastros</span>
					<ul>
						<li><a href="<c:url value="/secretario"/>">Secretários</a></li>
					</ul>
				</li>
		</c:if>

		<c:if test="${usuarioLogado.usuario.perfil eq 1}">
				<li><span>Cadastros</span>
					<ul>
						<li><a href="<c:url value="/secretario"/>">Secretários</a></li>
					</ul>
				</li>
				<li><span>Downloads</span>
					<ul>
						<li><a href="<c:url value="/secretarios.csv"/>">Secretários</a></li>
						<li><a href="<c:url value="/secretarios-cargos-periodos.csv"/>">Secretários/CargoPeriodo</a></li>
					</ul>
				</li>
		</c:if>
		<li><a href="http://cepesp.fgv.br/pt-br/node/373" target="_blanck" >FAQ</a></li>
		
	</c:if>
    <li><a href="<c:url value="/consultaResultados"/>">Consulta</a></li>
	
</ul>
			