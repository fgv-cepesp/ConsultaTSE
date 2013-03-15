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
		
		
<script type="text/javascript">
jQuery.fn.log = function (msg) {
  console.log("%s: %o", msg, this);
  return this;
};

//************************************************************************
//
//  Inicia multiselect
//
//************************************************************************

function initMultiselect(sel) {
	sel.multiselect({
	      buttonClass: 'btn',
	      buttonWidth: 'auto',
	      buttonText: function(options) {
	        if (options.length == 0) {
	          return 'Nada selecionado <b class="caret"></b>';
	        }
	        else if (options.length > 3) {
	          return options.length + ' itens selecionados  <b class="caret"></b>';
	        }
	        else {
	          var selected = '';
	          options.each(function() {
	            selected += $(this).text() + ', ';
	          });
	          return selected.substr(0, selected.length -2) + ' <b class="caret"></b>';
	        }
	      }
	    });
	
		
	$('.multiselect-all').on('click', function(e) {
		e.preventDefault();
		var s = $(this).parent().children('.multiselect');
		
		$('option', s).each(function(element) {
			$(this).prop('selected', 'selected');
		});
		s.multiselect('refresh');
	});
	
	$('.multiselect-clean').on('click', function(e) {
		e.preventDefault();
		var s = $(this).parent().children('.multiselect');
		$('option', s).each(function(element) {
			$(this).removeAttr('selected');
		});
		s.multiselect('refresh');
	});
}

//************************************************************************
//
//  Auxiliares em geral
//
//************************************************************************

function validateAndGetSelection(select)
{
	var value = select.children('option:selected').val();
	if(value == "") {
		// é invalido
		select.closest('.control-group').addClass('error');
	} else {
		select.closest('.control-group').removeClass('error');
	}
	
	return value;
}

function refreshScroll() {
	
	$('[data-spy="scroll"]').each(function () {
		  $(this).scrollspy('refresh');
	});
	
}

function commaJoin(strArr) {
	
	return strArr.join(', ');
}

function btnResetAndDisable(btn) {
	btn.button('reset');
	setTimeout(function() { btn.attr('disabled', 'disabled').addClass('disabled'); }, 200);
}

function getSelectionText(select)
{
	var value = select.children('option:selected').text();
	return value;
}

function selectedAsMultipleParameter(select, paramName)
{
	var value = select.val();
	paramName = paramName + '=';
	return paramName + value.join('&' + paramName);
}

//************************************************************************
//
//  Auxiliares dos Anos
//
//************************************************************************

function limparAnos() {
	$('#anosDisponiveisUneditable').text('');
	$('#anosDisponiveisUneditable').hide();
	
	$('#anosContinuar').button('reset');
	$('#anosEditar').button('reset');
	
	$('#anosDisponiveisForm').hide();
	$('#anosDisponiveisInfo').show();
}

function popularAnos(codCargo) {
	$('#anosDisponiveisPlaceholder').empty();
	$('#anosDisponiveisForm').hide();
	
	// criando elementos usando o template
	var clone = $('#anosTemplate').clone();
	clone.show();
	clone.removeAttr('id');
	
	var l = clone.children('.multiselect');
	l.attr('id', 'anosDisponiveis');
	l.attr('name', 'anosEscolhidos[]');
	
	
	$('#anosDisponiveisPlaceholder').append(clone);
	
	//id="anosDisponiveis" name="anosEscolhidos[]" 
	
	
	//var l = $('#anosDisponiveis');
	//l.children('option').remove().multiselect('destroy');
	//l.multiselect('destroy');
	
	$.getJSON('<c:url value="/consulta/anos"/>',
            { cargo: codCargo },
            function(data) {
                $.each(data.list, function(indice, par) {
                	l.append( $("<option/>").text(par.valor).val(par.chave) );
                });
                initMultiselect(l);
            }
        );
	
	setTimeout(function() { 
			$('#anosDisponiveisInfo').hide();
			$('#anosDisponiveisForm').show();
			$('#anosDisponiveisPlaceholder').show(); 
		}, 900);
}

function validarAnosAlerta()
{
	$('#anosDisponiveisError').hide();
	var anos = $('#anosDisponiveis').val();
	
	if (anos != null && anos.length > 1) {
		var alert = $('#anosDisponiveisAlert');
		var form = $('#anosDisponiveisForm');
		
		$('#anosDisponiveisAlertNao').on('click', function (e) {
			e.preventDefault();
			
			// fechar este alerta e sai
			alert.hide();
			form.show();
			
			return;
		});
		
		$('#anosDisponiveisAlertSim').on('click', function (e) {
			e.preventDefault();
			
			// fecha este alerta e valida o resto
			alert.hide();
			form.show();
			
			validarAnosSemAlerta();
		});
		
		alert.bind('closed', function () {
			alert.hide();
			$('#anosDisponiveisForm').show();
		});
		
		form.hide();
		alert.show();
		
	} else {
		validarAnosSemAlerta();
	}
}

function validarAnosSemAlerta()
{
	var btnCont = $('#anosContinuar');
	if(btnCont.hasClass('disabled')) {
		return;
	}
	
	var btnEdit = $('#anosEditar');
	
	var select = $('#anosDisponiveis'); 
	
	var anos = select.val();
	
	var valid = true;
	// loading enquanto validamos
	btnCont.button('loading');
	
	if(anos == null || anos.length == 0) {
		valid = false;
		$('#anosDisponiveisError').alert();
		$('#anosDisponiveisError').show();
	} else {
		$('#anosDisponiveisError').hide();
	}
	
	if(valid) {
		btnResetAndDisable(btnCont);
		
		// bloquear edição
		$('#anosDisponiveisPlaceholder').hide();
		$('#anosDisponiveisUneditable').text( commaJoin(anos) ).show();
		
		// configurar botoes
		
		btnEdit.removeAttr('disabled', 'disabled').removeClass('disabled');
		
		// Abrir filtros opcionais...
		$('#filtrosOpcionaisInfo').hide();
		$('#filtrosOpcionaisForm').show();
		
		$('#consultaInfo').hide();
		$('#consultaForm').show();
		
		
		$.scrollTo($('#colunas'), 800);
	} else {
		btnCont.button('reset');
		btnEdit.addClass('disabled');
	}
	
}

function popularColunas(nivelRegional, nivelPolitica) {

	
    $.getJSON('<c:url value="/consulta/camposDisponiveis"/>',
        { nivelAgregacaoRegional: nivelRegional ,
          nivelAgregacaoPolitica: nivelPolitica },
        function(data) {
             popularColunasFixas(data.formResultAux.camposFixos);
             popularColunasOpcionais(data.formResultAux.camposOpcionais);
        }
    );
	
	$('#colunasInfo').hide();
	$('#colunasForm').show();
}

//************************************************************************
//
//  Auxiliares das Colunas
//
//************************************************************************

function limparColunas() {
	$('#colunasForm').hide();
	$('#colunasInfo').show();
}

function popularColunasFixas(campos) {
	$('#colunasFixasContainer').empty();
	
	var l = $('#camposFixos');
    l.children('option').remove();
	
	$.each(campos, function(indice, par) {
        
        // verifica se já aninhamento da coluna
        var chaveParts = par.chave.split('.');
        var grupo = chaveParts[0];
        //var elemento = indiceParts[1];
        
        var valorParts = par.valor.split(':');
        var nomeGrupo = valorParts[0];
        var nomeCol = valorParts[1];
        
        var dl;
        
        if($('#' + grupo + 'Fixed').length == 0) {
        	// nao existe, tem que criar!
        	
        	// criando elementos usando o template
    		var clone = $('#colunasDefTemplate').clone();
    		clone.show();
    		clone.removeAttr('id');
    		dl = clone.children('dl');
    		
    		clone.appendTo($('#colunasFixasContainer'));
    		
    		dl.attr('id', grupo + 'Fixed');
    		$("<dt/>").text(nomeGrupo).appendTo(dl);
    		
    		// fake
    		$("<option/>").attr('selected', 'selected' ).text(par.valor).val(par.chave).appendTo(l);
        } else {
        	dl = $('#' + grupo + 'Fixed');
        }
        
        $("<dd/>").text(nomeCol).appendTo(dl);
   });
}

function popularColunasOpcionaisFake() {
	var l = $('#camposEscolhidos');
    l.children('option').remove();
    
    // pegar os campos
	$.each($('.multiselectOpcionais'), function() {
		if($(this).val() != null) {
			$.each($(this).val(), function(index, value){
				$("<option/>").attr('selected', 'selected' ).text(value).val(value).appendTo(l);
			});
		}
	});
    
}

function popularColunasOpcionais(campos) {
	var container = $('#colunasOpcionaisContainer');
	container.empty();

	$.each(campos, function(indice, par) {
		
        // verifica se já aninhamento da coluna
        var chaveParts = par.chave.split('.');
        var grupo = chaveParts[0];
        //var elemento = indiceParts[1];
        
        var valorParts = par.valor.split(':');
        var nomeGrupo = valorParts[0];
        var nomeCol = valorParts[1].trim();
        grupo = grupo.replace("#", "").replace("#", "");
        var id = '#' + grupo + 'Opcionais'; 
        
        var l;
		
        if($(id).length == 0) {
        	$("<h4/>").text(nomeGrupo).appendTo(container);
        	
        	// criando elementos usando o template
        	var clone = $('#anosTemplate').clone();
        	clone.show();
        	clone.removeAttr('id');
        	
        	l = clone.children('.multiselect');
         	l.attr('id', grupo + 'Opcionais');
        	l.addClass('multiselectOpcionais');
        	
        	clone.appendTo(container);
        } else {
        	l = $(id);
        }
        
        l.append( $("<option />").text(nomeCol).val(par.chave) );
        
	});
	$.each($('.multiselectOpcionais'), function() {
		initMultiselect($(this));
	});
	
}

//************************************************************************
//
//  Auxiliares dos Filtros Opcionais
//
//************************************************************************

function limparFiltrosOpcionais() {
	$("#filtrosOpcionaisForm").children('option').remove();
	$('#filtroPartidoHolder').empty();
	$('#filtroCandidatoHolder').empty();
	
	configuraAutoComplete();
	

	$('#filtrosOpcionaisForm').hide();
	$('#filtrosOpcionaisInfo').show();
}

function popularFiltrosOpcionais(nivelRegional) {
	configuraAutoComplete();
	popularSelectFiltroRegional(nivelRegional);
}

function popularSelectFiltroRegional(nivelRegional) {

    var filtroRegional = $("#nivelFiltroRegional");
    filtroRegional.children('option').remove();
    $('select[name="nivelAgregacaoRegional"] option').each(function(i) {
        var child = $(this).clone();
        if(child.val() <= nivelRegional){
            child.appendTo( filtroRegional );
        }
    });

}

function limparSelectFiltroRegional() {
	var filtroRegional = $("#nivelFiltroRegional");
    filtroRegional.children('option').remove();	
}

function configuraAutoComplete() {
	
	// colocar inputs
	$('#filtroPartidoHolder').empty();
	$('#filtroCandidatoHolder').empty();
	
	$('#filtroPartidoHolder').append( '<input type="text" id="filtroPartidoNovo" name="filtroPartido"></input>' );
	$('#filtroCandidatoHolder').append( '<input type="text" id="filtroCandidato" name="filtroCandidato"></input>' );
	
	
    $('#filtroPartidoNovo').autoSuggest('<c:url value="/consulta/partidosAnos"/>',
            {
                startText: "Se desejar, digite aqui a sigla de um ou mais partidos.",
                emptyText: "Não existem resultados",
                extraParamsDynamic: function(string){  return "&" + selectedAsMultipleParameter($('#anosDisponiveis'), 'anosList');},
                selectedItemProp: "valor",
                searchObjProps:   "valor",
                selectedValuesProp: "chave",
                asHtmlID: "partidos",
                retrieveComplete: function(data){ return data.list; },
            }
        );
    
    $('#filtroCandidato').autoSuggest('<c:url value="/consulta/candidatosAnosCargo"/>',
            {
                startText: "Se desejar, digite aqui o nome de um ou mais candidatos.",
                emptyText: "Não existem resultados",
                extraParamsDynamic: function(string){  
                	return "&" + selectedAsMultipleParameter($('#anosDisponiveis'), 'anosList')
                			+ "&cargo=" + $('#filtroCargo').val();},
                selectedItemProp: "valor",
                searchObjProps:   "valor",
                selectedValuesProp: "chave",
                asHtmlID: "candidatos",
                retrieveComplete: function(data){ return data.list; },
            }
        );
}


function limparConsulta() {
	$('#consultaForm').hide();
	$('#consultaInfo').show();
	
}

$(function(){
	
    //************************************************************************
    //
    //  Binds dos filtros obrigatorios
    //
    //************************************************************************
	
    $('#filtrosObrigatoriosContinuar').on('click', function (e) {
    	
    	e.preventDefault();
    	
    	var btnCont = $('#filtrosObrigatoriosContinuar');
    	if(btnCont.hasClass('disabled')) {
    		return;
    	}
    	
    	var btnEdit = $('#filtrosObrigatoriosEditar');
    	
    	var selCargo = $('select[name="filtroCargo"]');
    	var selRegional = $('select[name="nivelAgregacaoRegional"]');
    	var selPolitico = $('select[name="nivelAgregacaoPolitica"]');
    	
    	var valid = true;
    	// loading enquanto validamos
    	btnCont.button('loading');
    	
    	// validando selects
    	var cargo = validateAndGetSelection(selCargo);
    	if(cargo == "") {
    		valid = false;
    	}
    	
    	var nivelRegional = validateAndGetSelection(selRegional);
    	if(nivelRegional == "") {
    		valid = false;
    	}
    	
    	var nivelPolitico = validateAndGetSelection(selPolitico);
    	if(nivelPolitico == "") {
    		valid = false;
    	}
    	
    	
    	if(valid) {
    		btnResetAndDisable(btnCont);
    		
    		// bloquear edição
    		selCargo.hide();
    		$('#filtroCargoText').text( getSelectionText(selCargo) ).show();
    		
    		selRegional.hide();
    		$('#nivelAgregacaoRegionalText').text( getSelectionText(selRegional) ).show();
    		
    		selPolitico.hide();
    		$('#nivelAgregacaoPoliticaText').text( getSelectionText(selPolitico) ).show();
    		
    		// configurar botoes
			
			btnEdit.removeAttr('disabled', 'disabled').removeClass('disabled');
			
			// iniciar proximo passo
			popularAnos(cargo);
			popularColunas(nivelRegional, nivelPolitico);
			popularFiltrosOpcionais(nivelRegional);
    		
    		$.scrollTo($('#eleicoes'), 800);
    	} else {
    		btnCont.button('reset');
    		btnEdit.addClass('disabled');
    	}
    	
    	refreshScroll();
    });
    
    $('#filtrosObrigatoriosEditar').on('click', function (e) {
		e.preventDefault();
    	
		var btnEdit = $('#filtrosObrigatoriosEditar');
		var btnCont = $('#filtrosObrigatoriosContinuar');
		btnCont.removeAttr('disabled', 'disabled').removeClass('disabled');
		
    	if(btnEdit.hasClass('disabled')) {
    		return;
    	}
    	
    	// apagar outros filtros!!
    	    limparAnos();
    		limparColunas();
    		limparSelectFiltroRegional();
    		limparConsulta();
    		limparFiltrosOpcionais();
    	
    	
    	var selCargo = $('select[name="filtroCargo"]');
    	var selRegional = $('select[name="nivelAgregacaoRegional"]');
    	var selPolitico = $('select[name="nivelAgregacaoPolitica"]');
    	
    	selCargo.show();
		$('#filtroCargoText').hide();
		
		selRegional.show();
		$('#nivelAgregacaoRegionalText').hide();
		
		selPolitico.show();
		$('#nivelAgregacaoPoliticaText').hide();
		
		btnEdit.addClass('disabled');
		
		refreshScroll();
    });
    
    //************************************************************************
    //
    //  Binds dos anos
    //
    //************************************************************************
    
    $('#anosContinuar').on('click', function (e) {
    	e.preventDefault();
    	validarAnosAlerta();
    	
    	refreshScroll();
    });
    
    $('#anosEditar').on('click', function (e) {
		e.preventDefault();
    	
		var btnEdit = $('#anosEditar');
		var btnCont = $('#anosContinuar');
		btnCont.removeAttr('disabled', 'disabled').removeClass('disabled');
		
    	if(btnEdit.hasClass('disabled')) {
    		return;
    	}
    	
    	// apagar outros filtros!!
		limparFiltrosOpcionais();
    	limparConsulta();
    	
    	$('#anosDisponiveisUneditable').hide();
    	$('#anosDisponiveisPlaceholder').show();
    	
    	btnEdit.addClass('disabled');
    	
    	refreshScroll();
    });
    
    //************************************************************************
    //
    //  Binds das colunas
    //
    //************************************************************************
    
    $('#colunasContinuar').on('click', function (e) {
    	e.preventDefault();
    	
    	$.scrollTo($('#filtrosOpcionais'), 800);
    	
    	refreshScroll();
    });
    
    //************************************************************************
    //
    //  Binds dos filtros opcionais
    //
    //************************************************************************
    
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
        
        refreshScroll();
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
	    
	    refreshScroll();
    };
    
    
    $('#filtrosOpcionaisContinuar').on('click', function (e) {
    	e.preventDefault();
    	$.scrollTo($('#consulta'), 800);
    	
    	refreshScroll();
    });
    
    //************************************************************************
    //
    //  Binds da consulta
    //
    //************************************************************************
    
    
    $('#butQuery').click(function(e) {
    	e.preventDefault();
    	
    	$('#butQuery').button('loading');
    	
    	setTimeout(function() { $('#butQuery').button('reset'); }, 60000);
    	
    	try {
    		popularColunasOpcionaisFake();
    		
	        $.fileDownload('<c:url value="/resultados.csv"/>', {
				httpMethod: "POST",
				data: $('#formConsulta').serialize(),
				dataType: 'text/csv',
			    successCallback: function (url) {
			    	$('#butQuery').button('complete');
			    },
			    failCallback: function (html, url) {
			        alert('O download falhou.');
			        $('#butQuery').button('complete');
			    }
	        });
	        return false;
    	}
        catch (err) {
            alert("Houve algum erro na geracao do arquivo.");
            return;
        }
    });
    refreshScroll();
    setTimeout(function() { $('body').scrollspy(); }, 2000);
    
});
</script>
  <div class="row-fluid">
    <!-- Docs nav
    ================================================== -->
      <div class="well sidebar-nav" data-spy="affix" id="sidebar">
        <ul class="nav nav-list scroll-sidebar">
          <li class="active"><a href="#inicio"><i class="icon-chevron-right"></i> Início</a></li>
          <li><a href="#filtrosObrigatorios"><i class="icon-chevron-right"></i> Filtros obrigatórios</a></li>
          <li><a href="#eleicoes"><i class="icon-chevron-right"></i> Eleições</a></li>
          <li><a href="#colunas"><i class="icon-chevron-right"></i> Colunas fixas e opcionais</a></li>
          <li><a href="#filtrosOpcionais"><i class="icon-chevron-right"></i> Filtros opcionais</a></li>
          <li><a href="#consulta"><i class="icon-chevron-right"></i> Resultado</a></li>
        </ul>
      </div>

	 <div class="offset3 span8">
		  <section id="inicio">
		      <div class="hero-unit">
		        <h2>Resultados de eleições por cargo</h2>
		        <p>Este formulário permite efetuar consultas às bases de dados relativas as eleições especificando um cargo eletivo. As consultas podem ser
		       também agregadas por partido e nível regional.</p>
		        <p><a class="btn btn-primary btn-large" href="#filtrosObrigatorios">Começar &raquo;</a></p>
		      </div>
	      </section>
      
      
      	<form class="form-horizontal" id="formConsulta">
      	<!-- Filtros Obrigatorios
        ================================================== -->
      	<section id="filtrosObrigatorios">
      		<div class="page-header">
      			<h1>Filtros obrigatórios</h1>
      		</div>
      		<p>Neste modo de consulta, você deve primeiro escolher um cargo 
      		e os níveis de agregação regional e política.</p>
      		
      		<div id="controlFiltrosObrigatorios">
      		  <div class="control-group">
			    <label class="control-label" for="filtroCargo">Cargo</label>
			    <div class="controls">
			      <select id="filtroCargo" name="filtroCargo" required title="Cargo é um campo obrigatório.">
	                    <c:forEach items="${filtroCargoList}" var="cargo" varStatus="s">
	                        <option value="${cargo.chave}">${cargo.valor}</option>
	                    </c:forEach>
                	</select>
                	<span class="input-large uneditable-input" id="filtroCargoText" style="display: none;"></span>
			    </div>
			  </div>
			  <div class="control-group">
			    <label class="control-label" for="nivelAgregacaoRegional">Agregação regional <a href="#myModal" role="button" data-toggle="modal"><i class="icon-question-sign"></i></a></label>
			    <div class="controls">
			        <select name="nivelAgregacaoRegional" required>
	                    <c:forEach items="${nivelAgregacaoRegionalList}" var="nar" varStatus="s">
	                        <option value="${nar.chave}">${nar.valor}</option>
	                    </c:forEach>
                	</select>
                	<span class="input-large uneditable-input" id="nivelAgregacaoRegionalText" style="display: none;"></span>
			    </div>
			  </div>
			  <div class="control-group">
			    <label class="control-label" for="nivelAgregacaoPolitica">Agregação política</label>
			    <div class="controls">
			      <select name="nivelAgregacaoPolitica" required>
	                    <c:forEach items="${nivelAgregacaoPoliticaList}" var="nap" varStatus="s">
	                        <option value="${nap.chave}">${nap.valor}</option>
	                    </c:forEach>
                	</select>
                	<span class="input-large uneditable-input" id="nivelAgregacaoPoliticaText" style="display: none;"></span>
			    </div>
			  </div>
			</div>
      		<div class="control-group">
      			<div class="controls">
			  		<button class="btn btn-primary" id="filtrosObrigatoriosContinuar" data-loading-text="Validando...">Continuar</button>
			  		<button class="btn btn-danger disabled" id="filtrosObrigatoriosEditar" title="Este botão limpa os próximos!">Editar</button>
			  	</div>
			</div>
      	
      	</section>
      	<!-- Eleições
        ================================================== -->
      	<section id="eleicoes">
      		<div class="page-header">
      			<h1>Eleições</h1>
      		</div>
			<p>Abaixo estão listadas as eleições disponíveis para o cargo
				escolhido. Para continuar escolha ao menos um ano. Note que escolher
				vários anos pode tornar a consulta bastante demorada.</p>

			<div id="anosDisponiveisInfo">
				<div class="alert alert-info">
  					<strong>Nota:</strong> Esta parte do formulário estará disponível quando preencher os <a href="#filtrosObrigatorios">filtros obrigatórios</a>.
				</div>
			</div>
			
			<div id="anosDisponiveisAlert" class="alert alert-block alert-warning fade in" style="display: none;">
            	<h4 class="alert-heading">Você escolheu muitos anos!</h4>
	            <p>A consulta pode levar mais do que o esperado. Deseja continuar assim mesmo?<p>
              	<button class="btn btn-warning" id="anosDisponiveisAlertSim">Sim, quero continuar</button> <button class="btn" id="anosDisponiveisAlertNao">Não, vou alterar minha escolha</button>
             </div>
			
			<div id="anosDisponiveisError" class="alert alert-block alert-error fade in" style="display: none;">
				<button type="button" class="close" data-dismiss="alert">×</button>
            	<h4 class="alert-heading">Você deve escolher ao menos um ano!</h4>
	            <p>Para efetuar a consulta você deve selecionar ao menos um ano.<p>
            </div>

			<div id="anosDisponiveisForm" style="display: none;">
	      		<div class="control-group" >
	      				<span class="input-xlarge uneditable-input" id="anosDisponiveisUneditable" style="display: none;"></span>
				  		<div id="anosDisponiveisPlaceholder"></div>
				</div>
	      		<div class="control-group">
				  		<button class="btn btn-primary" id="anosContinuar" data-loading-text="Validando...">Continuar</button>
				  		<button class="btn btn-danger disabled" id="anosEditar" title="Este botão limpa os próximos!">Editar</button>
				</div>		
			</div>
			
			<!-- Multiselect template -->
	   		<div class="btn-group multiselect-group" id="anosTemplate" style="display: none;">
				<select multiple="multiple" class="multiselect" required title="Este campo é obrigatório.">
				</select>
				<button class="btn multiselect-all">Selecionar todos</button>
				<button class="btn multiselect-clean">Limpar seleção</button>
			</div>
					    
	</section>
      
      	<!-- Colunas
        ================================================== -->
      	<section id="colunas">
      		<div class="page-header">
      			<h1>Colunas fixas e opcionais</h1>
      		</div>

			<p>Dependendo da agregação ecolhida a consulta
				pode trazer diferentes colunas. Algumas colunas são fixas, 
				quando identificam a unicidade de cada informação, e outras
				são opcionais e podem ser escolhidos de acordo com a necessidade
				de sua pesquisa. Aqui você pode escolher as colunas opicionais desejadas.</p>
	
			<div id="colunasInfo">
				<div class="alert alert-info">
	 				<strong>Nota:</strong> Esta parte do formulário estará disponível quando preencher os <a href="#filtrosObrigatorios">filtros obrigatórios</a>.
				</div>
			</div>
			
			<div id="colunasForm" style="display: none;">
				        <h3>Colunas Opcionais</h3>
				        <div class="control-group" id="colunasOpcionaisContainer"></div>
				        	<select id="camposEscolhidos" name="camposEscolhidos[]"  class="multiselect" multiple="multiple" style="display: none;"></select>
				        
				        <h3>Colunas Fixas</h3>
				        	<div class="row-fluid  show-grid" id="colunasFixasContainer"></div>
							<select id="camposFixos" name="camposFixos[]" multiple="multiple" style="display: none;"></select>
	      		<div class="control-group">
				  	<button class="btn btn-primary" id="colunasContinuar">Continuar</button>
				</div>
			</div>
	      	
	      	<!-- templates -->
	      	<div class="span3" id="colunasDefTemplate" style="display: none; ">
				<dl>
				</dl>
			</div>   
      
		</section>
		
		<!-- Filtros opcionais
        ================================================== -->
      	<section id="filtrosOpcionais">
      		<div class="page-header">
      			<h1>Filtros opcionais</h1>
      		</div>

			<p>
				Aqui você pode selecionar filtros opcionais, como partido político,
				candidato e região. No campo partidos, além das siglas, é possível
				buscar por <strong>Branco</strong>, <strong>Nulo</strong> e <strong>Anulado</strong>.
			</p>

			<div id="filtrosOpcionaisInfo">
				<div class="alert alert-info">
	 				<strong>Nota:</strong> Esta parte do formulário estará disponível quando escolher as <a href="#eleicoes">eleições</a>.
				</div>
			</div>
	      
	      	<div id="filtrosOpcionaisForm" style="display: none;">
	      		<h3>Partido político</h3>
	      			<div class="control-group" id="filtroPartidoHolder">
	            	</div>
	      		<h3>Candidato</h3>
	      			<div class="control-group" id="filtroCandidatoHolder">
	      			</div>
	      		<h3>Região</h3>
	      			<h4>Tipo de região</h4>
	      			<div class="control-group">
	      				<select name="nivelFiltroRegional" id="nivelFiltroRegional" class="formLayout"></select>
	      			</div>
	      			<div class="control-group">
	      				<div id="grupoFiltroRegional"></div>
	      			</div>
	      		
	      		<div class="control-group">
				  	<button class="btn btn-primary" id="filtrosOpcionaisContinuar">Continuar</button>
				</div>
			</div>
      
		</section>
		
		<!-- Efetuar consulta
        ================================================== -->
      	<section id=consulta>
      		<div class="page-header">
      			<h1>Resultado</h1>
      		</div>

			<p>
				O botão abaixo efetuará a consulta. Isto pode demorar de acordo com
				os filtros selecionados. O arquivo
				<strong>.csv</strong> pode ser aberto com editores de planilhas
				eletrônicas, como MS Excel ou OpenOffice Calc.
			</p>

			<div id="consultaInfo">
				<div class="alert alert-info">
	 				<strong>Nota:</strong> Alguns campos obrigatórios ainda não foram selecionados.
				</div>
			</div>
	      
	      	<div id="consultaForm" style="display: none;">
	      		<p>
				  <button class="btn btn-large btn-primary" type="submit" id="butQuery" data-loading-text="Consultando...">Efetuar consulta</button>
				</p>
			</div>
			
			
      
		</section>
		
		<!-- Modal -->
<div id="myModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    <h3 id="myModalLabel">Divisão Regional</h3>
  </div>
  <div class="modal-body">


Com exce&ccedil;&atilde;o das Unidades da Federa&ccedil;&atilde;o (tamb&eacute;m conhecidas como "Estados") e os munic&iacute;pios que s&atilde;o classifica&ccedil;&otilde;es administrativas definidas pelos respectivos legislativos e homologadas pelo TSE, o &oacute;rg&atilde;o respons&aacute;vel pela divis&atilde;o regional do Brasil &eacute; o Instituto Brasileiro de Geografia e Estat&iacute;stica (IBGE). 
(<a href="http://www.ibge.gov.br/home/geociencias/geografia/default_div_int.shtm" target="blank" >mais...</a>) 
<br/>O IBGE define atualmente 3 categorias:
<ul>
<li>
<b>Macroregi&atilde;o:</b> Divide o pa&iacute;s em grandes blocos em fun&ccedil;&atilde;o de sua posi&ccedil;&atilde;o geogr&aacute;fica - Sul, Sudeste, Centro-Oeste, Norte e Nordeste. Essa classifica&ccedil;&atilde;o existe desde 1970 e substitui classifica&ccedil;&otilde;es anteriores (1913 e 1945). Consiste em um agrupamento de UFs.
</li>
<li>
<b>Microregi&atilde;o:</b> Um agrupamento de munic&iacute;pios lim&iacute;trofes. Para fins estat&iacute;sticos e com base em similaridades econômicas e sociais, o IBGE divide os diversos estados da federa&ccedil;&atilde;o brasileira em microrregi&otilde;es.
</li>
<li>
<b>Mesoregi&atilde;o:</b> A Divis&atilde;o Regional do Brasil em mesorregi&otilde;es, partindo de determina&ccedil;&otilde;es mais amplas a n&iacute;vel conjuntural, buscou identificar &aacute;reas individualizadas em cada uma das Unidades Federadas, tomadas como universo de an&aacute;lise e definiu as mesorregi&otilde;es com base nas seguintes dimens&otilde;es: o processo social como determinante, o quadro natural como condicionante e a rede de comunica&ccedil;&atilde;o e de lugares como elemento da articula&ccedil;&atilde;o espacial. Um exemplo t&iacute;pico de mesoregi&atilde;o s&atilde;o as regi&otilde;es metropolitanas. A mesoregi&atilde;o &eacute; um agrupamento de microregi&otilde;es.
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
