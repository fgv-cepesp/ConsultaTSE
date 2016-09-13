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
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link type="text/css" rel="stylesheet" href="css/autoSuggest.css"/>

<div class="row">

    <section class="col-md-12" id="consultaWrapper">


        <section id="inicio">
            <div class="jumbotron">
                <h2>Resultados de eleições por cargo</h2>
                <p>Este formulário permite efetuar consultas às bases de dados relativas as eleições especificando um
                    cargo eletivo. As consultas podem ser
                    também agregadas por partido e nível regional.</p>
                <small><a href="<c:url value='/etl'/>"/>Notas sobre os dados</a></small>
                <p><a class="btn btn-primary btn-large" data-component="startBtn"
                      href="#filtrosObrigatorios">Começar &raquo;</a></p>
            </div>
        </section>

        <form action="<c:url value="/resultados.csv"/>" method="post" enctype="multipart/form-data" novalidate>

            <!-- Filtros Obrigatorios -->
            <section id="filtrosObrigatorios" class="step" data-component="requiredFieldsFilter">

                <h1>Filtros obrigatórios</h1>
                <p>Neste modo de consulta, você deve primeiro escolher um cargo e os níveis de agregação
                    regional e
                    política.</p>

                <div class="form-group">
                    <label class="control-label" for="filtroCargo">Cargo</label>
                    <select name="filtroCargo" class="form-control" required
                            title="Cargo é um campo obrigatório.">
                        <option value="0">Selecione um Cargo</option>
                        <c:forEach items="${filtroCargoList}" var="cargo" varStatus="s">
                            <option value="${cargo.getKey()}">${cargo.getValue()}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label class="control-label" for="nivelAgregacaoRegional">
                        Agregação Regional
                        <a href="#myModal" role="button" data-toggle="modal">
                            <i class="icon-question-sign"></i>
                        </a>
                    </label>

                    <select name="nivelAgregacaoRegional" class="form-control">
                        <option value="">Selecione uma Agregação Regional</option>
                        <c:forEach items="${nivelAgregacaoRegionalList}" var="nar" varStatus="s">
                            <option value="${nar.getNivel()}">${nar.getNomeDescritivo()}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label class="control-label" for="nivelAgregacaoPolitica">Agregação Política</label>
                    <select name="nivelAgregacaoPolitica" class="form-control" required>
                        <option value="">Selecione uma Agregação Política</option>
                        <c:forEach items="${nivelAgregacaoPoliticaList}" var="nap" varStatus="s">
                            <option value="${nap.getNivel()}">${nap.getNomeDescritivo()}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group" data-component="turnContainer">
                    <label class="control-label" for="filtroTurno">Turno</label>
                    <select name="filtroTurno" class="form-control" required>
                        <option value="">Selecione um Turno</option>
                        <c:forEach items="${filtroTurnoList}" var="turno" varStatus="s">
                            <option value="${turno}">${turno}</option>
                        </c:forEach>
                    </select>
                </div>

            </section>

            <!-- Eleições -->
            <section id="eleicoes" class="step" data-component="electionsFilter">

                <h1>Eleições</h1>

                <div class="alert alert-info fade in" data-component="unavailable">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    Antes, Selecione um <b>Cargo</b>.
                </div>

                <div data-component="content">
                    <p>Abaixo estão listadas as eleições disponíveis para o cargo
                        escolhido. Para continuar escolha ao menos um ano. Note que escolher
                        vários anos pode tornar a consulta bastante demorada.</p>

                    <div class="alert alert-block alert-warning fade in" data-component="anosDisponiveisAlert">
                        <h4 class="alert-heading">Você escolheu muitos anos!</h4>
                        <p>A consulta pode levar mais do que o esperado.</p>
                    </div>

                    <div class="form-group" data-component="anosComponent">
                        <!-- Content dynamically inflated -->
                    </div>
                </div>

            </section>

            <!-- Colunas -->
            <section id="colunas" class="step" data-component="columnsFilter">
                <h1>Colunas fixas e opcionais</h1>

                <div class="alert alert-info" data-component="unavailable">
                    Antes, selecione uma <b>Agregação Regional</b> e uma <b>Agregação Política</b>
                </div>

                <div data-component="content">
                    <p>Dependendo da agregação ecolhida a consulta
                        pode trazer diferentes colunas. Algumas colunas são fixas,
                        quando identificam a unicidade de cada informação, e outras
                        são opcionais e podem ser escolhidos de acordo com a necessidade
                        de sua pesquisa. Aqui você pode escolher as colunas opcionais desejadas.
                        <a href="<c:url value='/ajuda' />" target="_blank">Veja aqui as descrições das opções</a>.</p>

                    <div class="row">
                        <div class="col-md-6">
                            <h3>Colunas Opcionais</h3>

                            <div class="form-group" data-component="optionalColumnsComponent">
                                <!-- Content dynamically inflated -->
                            </div>
                        </div>

                        <div class="col-md-6">
                            <h3>Colunas Fixas</h3>
                            <div class="form-group" data-component="fixedColumnsComponent">
                                <!-- Content dynamically inflated -->
                            </div>
                        </div>
                    </div>
                </div>
            </section>

            <!-- Filtros opcionais  -->
            <section id="filtrosOpcionais" class="step" data-component="optionalFilters">
                <h1>Filtros opcionais</h1>

                <div data-component="unavailable">
                    <div class="alert alert-info">
                        Antes, complete o passo <a href="#eleicoes" class="aside-item">Eleiçoes</a>.
                    </div>
                </div>

                <div data-component="content">
                    <p>
                        Aqui você pode selecionar filtros opcionais, como partido político,
                        candidato e região. No campo partidos, além das siglas, é possível
                        buscar por <strong>Branco</strong>, <strong>Nulo</strong> e <strong>Anulado</strong>.
                    </p>

                    <div class="form-group">
                        <label for="partidos">Partido político</label>
                        <input type="text" name="partidos" class="form-control"/>
                        <div class="help-block" data-component="partidosInfoMessage"></div>
                    </div>

                    <div class="form-group">
                        <label for="candidados">Candidato</label>
                        <input type="text" name="candidatos" class="form-control" style="float: left; margin-bottom: 20px"/>
                    </div>

                    <h3>Região</h3>
                    <h4>Tipo de Região</h4>
                    <div class="form-group">
                        <select name="filtroNivelRegional" class="form-control">
                            <option value="">Selecione uma Região</option>
                            <c:forEach items="${nivelAgregacaoRegionalList}" var="nar" varStatus="s">
                                <option value="${nar.getNivel()}">${nar.getNomeDescritivo()}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="form-group" data-component="regioesComponent">
                        <input type="text" name="regioes" class="form-control"/>
                    </div>
                </div>

            </section>


            <button class="btn btn-large btn-primary" type="submit" data-component="submit">Efetuar consulta</button>
            <a class="btn" href="<c:url value='/ajuda' />" target="_blank">Descrições das Variáveis</a>

            <div class="modal fade" id="downloadModal" tabindex="-1" role="dialog" aria-labelledby="downloadModalLabel">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title" id="downloadModalLabel">Efetuando consulta...</h4>
                        </div>
                        <div class="modal-body">
                            <div class="help-block">Estamos efetuando a sua consulta, aguarde um momento.</div>
                            <div class="progress">
                                <div class="progress-bar progress-bar-striped active" role="progressbar" aria-valuenow="200" aria-valuemin="0" aria-valuemax="100" style="width: 100%"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Modal -->
            <div id="myModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
                 aria-hidden="true">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h3 id="myModalLabel">Divisão Regional</h3>
                </div>
                <div class="modal-body">
                    Com exce&ccedil;&atilde;o das Unidades da Federa&ccedil;&atilde;o (tamb&eacute;m conhecidas como
                    "Estados")
                    e os munic&iacute;pios que s&atilde;o classifica&ccedil;&otilde;es administrativas definidas pelos
                    respectivos legislativos e homologadas pelo TSE, o &oacute;rg&atilde;o respons&aacute;vel pela divis&atilde;o
                    regional do Brasil &eacute; o Instituto Brasileiro de Geografia e Estat&iacute;stica (IBGE).
                    (<a href="http://www.ibge.gov.br/home/geociencias/geografia/default_div_int.shtm" target="blank">mais...</a>)
                    <br/>O IBGE define atualmente 3 categorias:
                    <ul>
                        <li>
                            <b>Macroregi&atilde;o:</b> Divide o pa&iacute;s em grandes blocos em fun&ccedil;&atilde;o de
                            sua
                            posi&ccedil;&atilde;o geogr&aacute;fica - Sul, Sudeste, Centro-Oeste, Norte e Nordeste. Essa
                            classifica&ccedil;&atilde;o existe desde 1970 e substitui classifica&ccedil;&otilde;es
                            anteriores
                            (1913 e 1945). Consiste em um agrupamento de UFs.
                        </li>
                        <li>
                            <b>Microregi&atilde;o:</b> Um agrupamento de munic&iacute;pios lim&iacute;trofes. Para fins
                            estat&iacute;sticos
                            e com base em similaridades econômicas e sociais, o IBGE divide os diversos estados da
                            federa&ccedil;&atilde;o
                            brasileira em microrregi&otilde;es.
                        </li>
                        <li>
                            <b>Mesoregi&atilde;o:</b> A Divis&atilde;o Regional do Brasil em mesorregi&otilde;es,
                            partindo de
                            determina&ccedil;&otilde;es mais amplas a n&iacute;vel conjuntural, buscou
                            identificar &aacute;reas
                            individualizadas em cada uma das Unidades Federadas, tomadas como universo de an&aacute;lise
                            e
                            definiu as mesorregi&otilde;es com base nas seguintes dimens&otilde;es: o processo social
                            como
                            determinante, o quadro natural como condicionante e a rede de comunica&ccedil;&atilde;o e de
                            lugares
                            como elemento da articula&ccedil;&atilde;o espacial. Um exemplo t&iacute;pico de mesoregi&atilde;o
                            s&atilde;o
                            as regi&otilde;es metropolitanas. A mesoregi&atilde;o &eacute; um agrupamento de microregi&otilde;es.
                        </li>
                    </ul>
                </div>
                <div class="modal-footer">
                    <button class="btn" data-dismiss="modal" aria-hidden="true">Fechar</button>
                </div>
            </div>
        </form>

</div>
</div>
<!-- Page End -->

