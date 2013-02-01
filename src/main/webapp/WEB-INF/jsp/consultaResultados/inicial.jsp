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


<link type="text/css" rel="stylesheet" href="css/common.css"/>
<link type="text/css" rel="stylesheet" href="css/form.css"/>
<link type="text/css" rel="stylesheet" href="css/ui.multiselect.css"/>
<link type="text/css" rel="stylesheet" href="css/autoSuggest.css"/>
<link type="text/css" rel="stylesheet" href="css/jquery.tooltip.css"/>

<script type="text/javascript" src="js/plugins/localisation/jquery.localisation-min.js"></script>
<script type="text/javascript" src="js/plugins/scrollTo/jquery.scrollTo-min.js"></script>
<script type="text/javascript" src="js/ui.multiselect.js"></script>
<script type="text/javascript" src="js/jquery.autoSuggest.w.js"></script>
<script type="text/javascript" src="js/jquery.tooltip.min.js"></script>
<script type="text/javascript" src="js/spin.js"></script>
<script type="text/javascript" src="js/jquery.spin.js"></script>
<script src="<c:url value='/js/jquery.fileDownload.js' />"         type="text/javascript" ></script>


<script type="text/javascript">
jQuery.fn.log = function (msg) {
  console.log("%s: %o", msg, this);
  return this;
};

$(function(){

    $.localise('ui-multiselect', {language: 'pt', path: 'js/locale/'});
    $('#camposDisponiveis').multiselect();
    $('#butQuery').attr('disabled', 'disabled');
    $('#butQuery').attr("title","Para fazer o download, escolha o Ano, Cargo e Agregacoes desejadas").tooltip();
    $('#grupoFiltroRegional').hide();

    //.attr('title', 'Informe o ano da Eleicao pretendida e o nivel de agrupamento dos votos')
     $('#helpAgregacao').tooltip({
    	opacity: 1,
        showURL: false,
        //showBody: " - ",
        bodyHandler: function() { 
                return "<h3>Ano e agrega&ccedil;&otilde;es</h3>" + 
                "Informe o ano da Elei&ccedil;&atilde;o pretendida, o cargo  e a forma na qual a quantidade de votos devem ser agrupadas.";
        }
     });

     $('#helpCampos').tooltip({
        opacity: 1,
        showURL: false,
        bodyHandler: function() { 
                return "<ul><li><b>Fixos:</b> Campos que identificam a unicidade de cada informa&ccedil;&atilde;o</li>" +
                "<li><b>Opcionais</b> Campos extras, podem ser escolhidos de acordo com a necessidade de sua pesquisa. A disponibilidade varia de acordo com o tipo de agrega&ccedil;&atilde;o escolhida</li></ul>"; 
        }
     });

     $('#helpFiltros').tooltip({
        showURL: false,
        bodyHandler: function() { 
                return "<h3>Filtros</h3>"+
                "<ul><li><b>Partidos:</b> Aqui é possível filtrar um ou mais partidos;</li>" +
                "<li><b>Candidatos</b> Aqui é possível filtrar um ou mais candidatos;</li>" + 
                "<li><b>Regional:</b> Aqui é possível filtrar uma ou mais unidade de agregação regional.</li></ul>" +
                "<br>Para não haver filtro dos dados, basta deixar os campos em branco.";
        }
    });

     
     $('#helpAgregacaoRegional').click(function() {
    	 $("#dialog-helpAgregacaoRegional").dialog({ 
        	 modal: true,
        	 draggable: true,
        	 width: 700,
        	 height: 450,
        	 buttons: {
                 "Fechar": function() { $(this).dialog("close"); }
             }
    	 });
     });

    
    var updateQueryButton = function(){
        var nivelRegional = $('select[name="nivelAgregacaoRegional"]').children('option:selected').val();
        var nivelPolitico = $('select[name="nivelAgregacaoPolitica"]').children('option:selected').val();
        var ano = $('#anoEleicao').children('option:selected').val();
        var cargo = $('#filtroCargoList').children('option:selected').val();
        
       if(nivelRegional * nivelPolitico * ano * cargo > 0){
    	   $('#pButQuery').attr("title","Clique para baixar o arquivo.").tooltip();
           $('#butQuery').removeAttr("disabled");
       } else{
           $('#pButQuery').attr("title","Para fazer o download, escolha o Ano, Cargo e Agregacoes desejadas.").tooltip();
    	   $('#butQuery').attr('disabled', 'disabled');
       };
    };

    
    $('#butQuery').click(function() {
    	try {

    		var preparingFileModal = $("#preparing-file-modal");    		 
            preparingFileModal.dialog({ modal: true });
	
	        $.fileDownload('<c:url value="/resultados.csv"/>', {
				httpMethod: "POST",
				data: $('#form').serialize()
	//			preparingMessageHtml: "Estamos gerando seu arquivo, por favor aguarde...",
	//			failMessageHtml: "Houve algum problema ao gerar seu arquivo, por favor tente novamente.",
	//			successCallback: function (url) {
	//	            alert("xxx"); 
	//              $preparingFileModal.dialog('close');
	//	        },
	        });
//	        preparingFileModal.dialog('close');
	        return false;
    	}
        catch (err) {
            alert("Houve algum erro na geracao do arquivo.");
            return;
        }
    });

      

    var updateForm = function( formResult ){
	        var l = $('#camposDisponiveis').multiselect( 'destroy' );
	        l.children('option').remove();
	        $.each(formResult.camposOpcionais, function(indice, par) {
	             $("<option/>").text(par.valor).val(par.chave).appendTo(l);
	        });
	        l.multiselect();
	
	        var l = $('#camposFixos');
	        var fakeList = $('#camposFixosFake');
	        fakeList.children('li').remove();
	        l.children('option').remove();
	
	        $.each(formResult.camposFixos, 
	            function(indice, par) {
	                $("<option/>").attr('selected', 'selected' ).text(par.valor).val(par.chave).appendTo(l);
	                $("<li/>").text(par.valor).appendTo(fakeList);
	            }
	        );
	    };


    $('select[name^="nivelAgregacao"]').change(function (){
    	
    	$("#spinCampos").spin();
    	
        var nivelRegional = $('select[name="nivelAgregacaoRegional"]').children('option:selected').val();
        var nivelPolitico = $('select[name="nivelAgregacaoPolitica"]').children('option:selected').val();

        $.getJSON('<c:url value="/consulta/camposDisponiveis"/>',
            { nivelAgregacaoRegional: nivelRegional ,
              nivelAgregacaoPolitica: nivelPolitico },
            function(data) {
                     updateForm(data.formResultAux);
            }
        );

        var filtroRegional = $("#nivelFiltroRegional");
        filtroRegional.children('option').remove();
        $('select[name="nivelAgregacaoRegional"] option').each(function(i) {
            var child = $(this).clone();
            if(child.val() <= nivelRegional){
                child.appendTo( filtroRegional );
            }
        });
        
        updateQueryButton();
        
        $("#spinCampos").spin(false);
    });

    $('#anoEleicao').change(function (){
    	// inicia spin dos campo cargos
    	
    	$("#spinAnoAgre").spin("large");
    	
        var anoSelecionado = $(this).children('option:selected').val();
        var l = $('#filtroCargoList');
        l.children('option').remove();

        $.getJSON('<c:url value="/consulta/cargos"/>',
            { ano: anoSelecionado },
            function(data) {
                $.each(data.list, function(indice, par) {
                    l.append( $("<option/>").text(par.valor).val(par.chave) );
                });
            }
        );
        updateQueryButton();
        $("#spinAnoAgre").spin(false);
    });

    
    $('#filtroCargoList').change(function (){
        updateQueryButton();
    });


    var applyFiltroRegional = function( descricaoFiltro ){
	    $('#filtroRegional').autoSuggest('<c:url value="/consulta/filtroRegionalQuery"/>',
	        {
	            startText: "Escolha " + descricaoFiltro,
	            emptyText: "Não existem resultados",
	            extraParamsDynamic: function(string){  return "&nivelRegional=" + $("#nivelFiltroRegional").val();},
	            selectedItemProp: "valor",
	            searchObjProps: "valor",
	            selectedValuesProp: "chave",
	            asHtmlID: "regional",
	            retrieveComplete: function(data){ /*alert(data.list[0].chave); */ return data.list; },
	        }
	    );
    };

    
    $('#nivelFiltroRegional').change(function () {
    	
        var nivelFiltroRegional = $(this).children('option:selected').val();
        if(nivelFiltroRegional > 0){
            $('#grupoFiltroRegional').children().remove( );
            $('#grupoFiltroRegional').append( '<input type="text" id="filtroRegional"></input>' );
            applyFiltroRegional( $(this).children('option:selected').text() );
            $('#grupoFiltroRegional').fadeIn();
        } else {
            $('#grupoFiltroRegional').hide();
        };
        
    });

    
    $('#filtroPartidoNovo').autoSuggest('<c:url value="/consulta/partidos"/>',
        {
            startText: "Escolha o partido para filtro",
            emptyText: "Não existem resultados",
            extraParamsDynamic: function(string){  return "&ano=" + $('#anoEleicao').children('option:selected').val();},
            selectedItemProp: "valor",
            searchObjProps:   "valor",
            selectedValuesProp: "chave",
            asHtmlID: "partidos",
            retrieveComplete: function(data){ return data.list; },
        }
    );

    $('#filtroCandidato').autoSuggest('<c:url value="/consulta/candidatos"/>',
        {
            startText: "Escolha os candidatos para filtro",
            emptyText: "Não existem resultados",
            extraParamsDynamic: function(string){  return "&ano=" + $('#anoEleicao').children('option:selected').val();},
            selectedItemProp: "valor",
            searchObjProps:   "valor",
            selectedValuesProp: "chave",
            asHtmlID: "candidatos",
            retrieveComplete: function(data){ return data.list; },
        }
    );


       // http://harvesthq.github.com/chosen/
       // http://www.senamion.com/blog/jmultiselect2side.html
       // http://quasipartikel.at/2009/05/10/jqueryui-multiselect/
       // http://quasipartikel.at/multiselect_next/
       // http://labs.abeautifulsite.net/jquery-selectBox/
//    <h1>Consulta Resultados TSE</h1>
//    <p>Consulta de resultados de eleições.</p>
        

});
</script>

</head>
<body>

<div id="wrapper">
<div id="header">
<p class="left">
<img src="<c:url value="/images/logo.png"/>"></img>
<h1>Consulta Resultados TSE</h1>
Consulta de resultados de eleições.
</p>
<p class="right"><img src="<c:url value="/images/logoFapesp.jpg"/>"></p>
<br class="clear" />


</div>
<c:if test="${not empty errors}">
    <div id="error">
        <ul>
        <c:forEach items="${errors }" var="error">
            <li>${error.category } - ${error.message }</li>
        </c:forEach>
        </ul>
    </div>
</c:if>
<c:if test="${not empty notice}">
    <div id="notice">
        <p>${notice }</p>
    </div>
</c:if>

    <div id="content">
        <form id="form" action="<c:url value="/resultados.csv"/>" method="post">
			
            <h2>Ano e agregações <img id="helpAgregacao" src="<c:url value="/images/help_icon.gif"/>"></img></h2>
            <div id="spinAnoAgre" class="formLayout">
            <label for="anoEleicao">Ano da Eleição:</label>
                <select id="anoEleicao" name="anoEleicao">
                    <c:forEach items="${anoEleicaoList}" var="ano" varStatus="s">
                        <option value="${ano.chave}">${ano.valor}</option>
                    </c:forEach>
                </select><br>
                <label for="filtroCargoList">Cargo:</label> 
                <select id="filtroCargoList" name="filtroCargo"></select><br>
                <label for="nivelAgregacaoRegional">Nível de Agregação Regional:</label>
                <select name="nivelAgregacaoRegional">
                    <c:forEach items="${nivelAgregacaoRegionalList}" var="nar" varStatus="s">
                        <option value="${nar.chave}">${nar.valor}</option>
                    </c:forEach>
                </select>
                (<a href="#" id="helpAgregacaoRegional">saiba mais...</a>)<br>
                <label for="nivelAgregacaoPolitica">Nível de Agregação Política:</label>
                <select name="nivelAgregacaoPolitica">
                    <c:forEach items="${nivelAgregacaoPoliticaList}" var="nap" varStatus="s">
                        <option value="${nap.chave}">${nap.valor}</option>
                    </c:forEach>
                </select>
			</div>
            <h2>Campos <img id="helpCampos" src="<c:url value="/images/help_icon.gif"/>"></img></h2>
            <div id="spinCampos">
	            <h3>Fixos</h3>
	            <p>
	                <ul id="camposFixosFake"></ul>
	                <select id="camposFixos" name="camposFixos[]" multiple="multiple" style="display: none;"></select>
	            </p>
	
	            <h3>Opcionais</h3>
	            <p>
	                <select id="camposDisponiveis" name="camposEscolhidos[]"  class="multiselect" multiple="multiple">
	                </select>
	            </p>
			</div>
	            <h2>Filtros  <img id="helpFiltros" src="<c:url value="/images/help_icon.gif"/>"></img></h2>
	            <div>
	                <ul id="filters"></ul>
	                <p>
	                    <label for="filtroPartido">Partidos:</label>
	                    <input type="text" id="filtroPartidoNovo" name="filtroPartido"></input>
	                </p>
	                <p>
	                    <label for="filtroCandidato">Candidatos:</label>
	                    <input type="text" id="filtroCandidato" name="filtroCandidato"></input>
	                </p>
	
	                <p>
	                    <label for="nivelFiltroRegional">Filtro Regional:</label>
	                    <select name="nivelFiltroRegional" id="nivelFiltroRegional" class="formLayout"></select>
	                
	                </p>
	                <p id="grupoFiltroRegional"></p>
	            </div>
			


            <br />
            <hr />

        </form>
        </div>
        <p id="pButQuery" class="right">
            <button id="butQuery">Executar Consulta</button>
        </p>
		<br/>

    </div>


    <div id="footer">
        <p class="left"></p>
        <p class="right">.</p>
        <br class="clear" />
    </div>
    
	<div id="preparing-file-modal" title="Gerando Arquivo..." style="display: none;">
	   Gerando arquivo, por favor aguarde...	 
	<!--Throw what you'd like for a progress indicator below-->
	<div class="ui-progressbar-value ui-corner-left ui-corner-right" style="width: 100%; height:22px; margin-top: 20px;"></div>
	</div>	 
	<div id="error-modal" title="Erro" style="display: none;">
	    Houve algum problema ao gerar seu arquivo, por favor tente novamente.
	</div>

    <div id="dialog-helpAgregacaoRegional" title="Sobre agrega&ccedil;&atilde;o Regional" style="display: none;">
Com exce&ccedil;&atilde;o das Unidades da Federa&ccedil;&atilde;o (tamb&eacute;m conhecidas como "Estados") e os munic&iacute;pios que s&atilde;o classifica&ccedil;&otilde;es administrativas definidas pelos respectivos legislativos e homologadas pelo TSE, o &oacute;rg&atilde;o respons&aacute;vel pela divis&atilde;o regional do Brasil &eacute; o Instituto Brasileiro de Geografia e Estat&iacute;stica (IBGE). 
(<a href="http://www.ibge.gov.br/home/geociencias/geografia/default_div_int.shtm" target="blank" >mais...</a>) 
<br/>O IBGE define atualmente 3 categorias:
<ul>
<li>
<b>Macroregi&atilde;o:</b> Divide o pa&iacute;s em grandes blocos em fun&ccedil;&atilde;o de sua posi&ccedil;&atilde;o geogr&aacute;fica - Sul, Sudeste, Centro-Oeste, Norte e Nordeste. Essa classifica&ccedil;&atilde;o existe desde 1970 e substitui classifica&ccedil;&otilde;es anteriores (1913 e 1945). Consiste em um agrupamento de UFs;
</li>
<li>
<b>Microregi&atilde;o:</b> Um agrupamento de munic&iacute;pios lim&iacute;trofes. Para fins estat&iacute;sticos e com base em similaridades econômicas e sociais, o IBGE divide os diversos estados da federa&ccedil;&atilde;o brasileira em microrregi&otilde;es.
</li>
<li>
<b>Mesoregi&atilde;o:</b> A Divis&atilde;o Regional do Brasil em mesorregi&otilde;es, partindo de determina&ccedil;&otilde;es mais amplas a n&iacute;vel conjuntural, buscou identificar &aacute;reas individualizadas em cada uma das Unidades Federadas, tomadas como universo de an&aacute;lise e definiu as mesorregi&otilde;es com base nas seguintes dimens&otilde;es: o processo social como determinante, o quadro natural como condicionante e a rede de comunica&ccedil;&atilde;o e de lugares como elemento da articula&ccedil;&atilde;o espacial. Um exemplo t&iacute;pico de mesoregi&atilde;o s&atilde;o as regi&otilde;es metropolitanas. A mesoregi&atilde;o &eacute; um agrupamento de microregi&otilde;es.
</li>
</ul>
    </div>

    
</div>