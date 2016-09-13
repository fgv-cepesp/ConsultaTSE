(function() {
  var extend = function(child, parent) { for (var key in parent) { if (hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; },
    hasProp = {}.hasOwnProperty;

  ConsultaTSE.Job = (function() {
    function Job(id) {
      this.id = id;
    }

    Job.prototype.getId = function() {
      return this.id;
    };

    Job.prototype.isValid = function() {
      return true;
    };

    return Job;

  })();

  ConsultaTSE.UndefinedJob = (function(superClass) {
    extend(UndefinedJob, superClass);

    function UndefinedJob() {
      return UndefinedJob.__super__.constructor.apply(this, arguments);
    }

    UndefinedJob.prototype.toggleTurnInput = function(input) {
      return input.hide();
    };

    UndefinedJob.prototype.getName = function() {
      return '-';
    };

    UndefinedJob.prototype.isValid = function() {
      return false;
    };

    return UndefinedJob;

  })(ConsultaTSE.Job);

  ConsultaTSE.PresidenteJob = (function(superClass) {
    extend(PresidenteJob, superClass);

    function PresidenteJob() {
      return PresidenteJob.__super__.constructor.apply(this, arguments);
    }

    PresidenteJob.prototype.toggleTurnInput = function(input) {
      return input.show();
    };

    PresidenteJob.prototype.getName = function() {
      return 'Presidente';
    };

    PresidenteJob.prototype.isValid = function(input) {
      return input.val() !== "";
    };

    return PresidenteJob;

  })(ConsultaTSE.Job);

  ConsultaTSE.VicePresidenteJob = (function(superClass) {
    extend(VicePresidenteJob, superClass);

    function VicePresidenteJob() {
      return VicePresidenteJob.__super__.constructor.apply(this, arguments);
    }

    VicePresidenteJob.prototype.toggleTurnInput = function(input) {
      return input.show();
    };

    VicePresidenteJob.prototype.getName = function() {
      return 'Vice-Presidente';
    };

    VicePresidenteJob.prototype.isValid = function(input) {
      return input.val() !== "";
    };

    return VicePresidenteJob;

  })(ConsultaTSE.Job);

  ConsultaTSE.GovernadorJob = (function(superClass) {
    extend(GovernadorJob, superClass);

    function GovernadorJob() {
      return GovernadorJob.__super__.constructor.apply(this, arguments);
    }

    GovernadorJob.prototype.toggleTurnInput = function(input) {
      return input.show();
    };

    GovernadorJob.prototype.getName = function() {
      return 'Governador';
    };

    GovernadorJob.prototype.isValid = function(input) {
      return input.val() !== "";
    };

    return GovernadorJob;

  })(ConsultaTSE.Job);

  ConsultaTSE.ViceGovernadorJob = (function(superClass) {
    extend(ViceGovernadorJob, superClass);

    function ViceGovernadorJob() {
      return ViceGovernadorJob.__super__.constructor.apply(this, arguments);
    }

    ViceGovernadorJob.prototype.toggleTurnInput = function(input) {
      return input.show();
    };

    ViceGovernadorJob.prototype.getName = function() {
      return 'Vice-Governador';
    };

    ViceGovernadorJob.prototype.isValid = function(input) {
      return input.val() !== "";
    };

    return ViceGovernadorJob;

  })(ConsultaTSE.Job);

  ConsultaTSE.SenadorJob = (function(superClass) {
    extend(SenadorJob, superClass);

    function SenadorJob() {
      return SenadorJob.__super__.constructor.apply(this, arguments);
    }

    SenadorJob.prototype.toggleTurnInput = function(input) {
      return input.hide();
    };

    SenadorJob.prototype.getName = function() {
      return 'Senador';
    };

    return SenadorJob;

  })(ConsultaTSE.Job);

  ConsultaTSE.DeputadoFederalJob = (function(superClass) {
    extend(DeputadoFederalJob, superClass);

    function DeputadoFederalJob() {
      return DeputadoFederalJob.__super__.constructor.apply(this, arguments);
    }

    DeputadoFederalJob.prototype.toggleTurnInput = function(input) {
      return input.hide();
    };

    DeputadoFederalJob.prototype.getName = function() {
      return 'Deputado Federal';
    };

    return DeputadoFederalJob;

  })(ConsultaTSE.Job);

  ConsultaTSE.DeputadoEstadualJob = (function(superClass) {
    extend(DeputadoEstadualJob, superClass);

    function DeputadoEstadualJob() {
      return DeputadoEstadualJob.__super__.constructor.apply(this, arguments);
    }

    DeputadoEstadualJob.prototype.toggleTurnInput = function(input) {
      return input.hide();
    };

    DeputadoEstadualJob.prototype.getName = function() {
      return 'Deputado Estadual';
    };

    return DeputadoEstadualJob;

  })(ConsultaTSE.Job);

  ConsultaTSE.DeputadoDistritalJob = (function(superClass) {
    extend(DeputadoDistritalJob, superClass);

    function DeputadoDistritalJob() {
      return DeputadoDistritalJob.__super__.constructor.apply(this, arguments);
    }

    DeputadoDistritalJob.prototype.toggleTurnInput = function(input) {
      return input.show();
    };

    DeputadoDistritalJob.prototype.getName = function() {
      return 'Deputado Distrital';
    };

    return DeputadoDistritalJob;

  })(ConsultaTSE.Job);

  ConsultaTSE.PrimeiroSuplenteSenadorJob = (function(superClass) {
    extend(PrimeiroSuplenteSenadorJob, superClass);

    function PrimeiroSuplenteSenadorJob() {
      return PrimeiroSuplenteSenadorJob.__super__.constructor.apply(this, arguments);
    }

    PrimeiroSuplenteSenadorJob.prototype.toggleTurnInput = function(input) {
      return input.hide();
    };

    PrimeiroSuplenteSenadorJob.prototype.getName = function() {
      return 'Primeiro Suplente Senador';
    };

    return PrimeiroSuplenteSenadorJob;

  })(ConsultaTSE.Job);

  ConsultaTSE.SuplenteSenadorJob = (function(superClass) {
    extend(SuplenteSenadorJob, superClass);

    function SuplenteSenadorJob() {
      return SuplenteSenadorJob.__super__.constructor.apply(this, arguments);
    }

    SuplenteSenadorJob.prototype.toggleTurnInput = function(input) {
      return input.hide();
    };

    SuplenteSenadorJob.prototype.getName = function() {
      return 'Suplente Senador';
    };

    return SuplenteSenadorJob;

  })(ConsultaTSE.Job);

  ConsultaTSE.PrefeitoJob = (function(superClass) {
    extend(PrefeitoJob, superClass);

    function PrefeitoJob() {
      return PrefeitoJob.__super__.constructor.apply(this, arguments);
    }

    PrefeitoJob.prototype.toggleTurnInput = function(input) {
      return input.show();
    };

    PrefeitoJob.prototype.isValid = function(input) {
      return input.val() !== "";
    };

    PrefeitoJob.prototype.getName = function() {
      return 'Prefeito';
    };

    return PrefeitoJob;

  })(ConsultaTSE.Job);

  ConsultaTSE.VicePrefeitoJob = (function(superClass) {
    extend(VicePrefeitoJob, superClass);

    function VicePrefeitoJob() {
      return VicePrefeitoJob.__super__.constructor.apply(this, arguments);
    }

    VicePrefeitoJob.prototype.toggleTurnInput = function(input) {
      return input.show();
    };

    VicePrefeitoJob.prototype.isValid = function(input) {
      return input.val() !== "";
    };

    VicePrefeitoJob.prototype.getName = function() {
      return 'Vice-Prefeito';
    };

    return VicePrefeitoJob;

  })(ConsultaTSE.Job);

  ConsultaTSE.VereadorJob = (function(superClass) {
    extend(VereadorJob, superClass);

    function VereadorJob() {
      return VereadorJob.__super__.constructor.apply(this, arguments);
    }

    VereadorJob.prototype.toggleTurnInput = function(input) {
      return input.hide();
    };

    VereadorJob.prototype.getName = function() {
      return 'Verador';
    };

    return VereadorJob;

  })(ConsultaTSE.Job);

}).call(this);

(function() {
  ConsultaTSE.Query = (function() {
    Query.JOBS = {
      0: ConsultaTSE.UndefinedJob,
      1: ConsultaTSE.PresidenteJob,
      2: ConsultaTSE.VicePresidenteJob,
      3: ConsultaTSE.GovernadorJob,
      4: ConsultaTSE.ViceGovernadorJob,
      5: ConsultaTSE.SenadorJob,
      6: ConsultaTSE.DeputadoFederalJob,
      7: ConsultaTSE.DeputadoEstadualJob,
      8: ConsultaTSE.DeputadoDistritalJob,
      9: ConsultaTSE.PrimeiroSuplenteSenadorJob,
      10: ConsultaTSE.SuplenteSenadorJob,
      11: ConsultaTSE.PrefeitoJob,
      12: ConsultaTSE.VicePrefeitoJob,
      13: ConsultaTSE.VereadorJob
    };

    function Query() {
      this.years = [];
      this.optionalFields = [];
      this.partidos = [];
      this.regioes = [];
    }

    Query.prototype.setJob = function(jobId) {
      return this.job = new ConsultaTSE.Query.JOBS[jobId](jobId);
    };

    Query.prototype.getJob = function() {
      return this.job || new ConsultaTSE.UndefinedJob(0);
    };

    Query.prototype.setRegionalAggregation = function(a) {
      return this.regionalAggregation = a;
    };

    Query.prototype.getRegionalAggregation = function() {
      return this.regionalAggregation || 0;
    };

    Query.prototype.setPoliticalAggregationLevel = function(l) {
      return this.policalAggregationLevel = l;
    };

    Query.prototype.getPoliticalAggregationLevel = function() {
      return this.policalAggregationLevel || 0;
    };

    Query.prototype.getFiltroRegional = function() {
      return this.filtroRegional || 0;
    };

    Query.prototype.setFiltroRegional = function(regiao) {
      return this.filtroRegional = regiao;
    };

    Query.prototype.setTurn = function(turn) {
      return this.turn = turn;
    };

    Query.prototype.getTurn = function() {
      return this.turn || 0;
    };

    Query.prototype.clearYears = function() {
      return this.years = [];
    };

    Query.prototype.addYear = function(year) {
      return this.years.push(year);
    };

    Query.prototype.removeYear = function(year) {
      return this.years = this.years.filter(function(y) {
        return y !== year;
      });
    };

    Query.prototype.getYears = function() {
      return this.years;
    };

    Query.prototype.clearOptionalFields = function() {
      return this.optionalFields = [];
    };

    Query.prototype.addOptionalField = function(field) {
      return this.optionalFields.push(field);
    };

    Query.prototype.removeOptionalField = function(field) {
      return this.optionalFields = this.optionalFields.filter(function(f) {
        return f !== field;
      });
    };

    Query.prototype.getOptionalFields = function() {
      return this.optionalFields;
    };

    Query.prototype.getPartidos = function() {
      return this.partidos;
    };

    Query.prototype.addPartido = function(cod) {
      return this.partidos.push(cod);
    };

    Query.prototype.clearPartidos = function() {
      return this.partidos = [];
    };

    Query.prototype.removePartido = function(cod) {
      return this.partidos = this.partidos.filter(function(p) {
        return p !== cod;
      });
    };

    Query.prototype.getCandidatos = function() {
      return this.candidatos;
    };

    Query.prototype.addCandidato = function(cod) {
      return this.candidatos.push(cod);
    };

    Query.prototype.clearCandidatos = function() {
      return this.candidatos = [];
    };

    Query.prototype.removeCandidato = function(cod) {
      return this.candidatos = this.candidatos.filter(function(p) {
        return p !== cod;
      });
    };

    Query.prototype.getRegioes = function() {
      return this.regioes;
    };

    Query.prototype.addRegiao = function(cod) {
      return this.regioes.push(cod);
    };

    Query.prototype.clearRegioes = function() {
      return this.regioes = [];
    };

    Query.prototype.removeRegiao = function(cod) {
      return this.regioes = this.regioes.filter(function(r) {
        return r !== cod;
      });
    };

    Query.prototype.isValid = function() {
      return this.getJob().getId() !== 0 && this.getRegionalAggregation() !== 0 && this.getPoliticalAggregationLevel() !== 0;
    };

    Query.prototype.getFormData = function() {
      var data;
      data = {
        anos: this.getYears(),
        cargo: this.getJob().getId(),
        agregacaoRegional: this.getRegionalAggregation(),
        agregacaoPolitica: this.getPoliticalAggregationLevel(),
        camposOpcionais: this.getOptionalFields(),
        partidos: this.getPartidos(),
        candidatos: this.getCandidatos(),
        filtroRegional: this.getFiltroRegional(),
        regioes: this.getRegioes()
      };
      if (this.getTurn() !== 0) {
        data.turno = this.getTurn();
      }
      return data;
    };

    return Query;

  })();

}).call(this);

(function() {
  var extend = function(child, parent) { for (var key in parent) { if (hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; },
    hasProp = {}.hasOwnProperty;

  ConsultaTSE.QueryView = (function(superClass) {
    extend(QueryView, superClass);

    function QueryView(container) {
      QueryView.__super__.constructor.call(this, container);
      this.query = new ConsultaTSE.Query();
      this.initializeComponents();
    }

    QueryView.prototype.initializeComponents = function() {
      var self;
      this.startBtn = this.findComponent('startBtn');
      this.requiredFieldsFilter = new ConsultaTSE.RequiredFieldsFilterView(this.findComponent('requiredFieldsFilter'), this.query);
      this.electionsFilter = new ConsultaTSE.ElectionsFilterView(this.findComponent('electionsFilter'), this.query);
      this.columnsFilter = new ConsultaTSE.ColumnsFilterView(this.findComponent('columnsFilter'), this.query);
      this.optionalFilter = new ConsultaTSE.OptionalFiltersView(this.findComponent('optionalFilters'), this.query);
      this.asideItem = $('.aside-item');
      this.form = this.findElement('form');
      this.downloadModal = this.findElement('#downloadModal');
      this.requiredFieldsFilter.setElectionsFilter(this.electionsFilter);
      this.requiredFieldsFilter.setColumnsFilter(this.columnsFilter);
      this.requiredFieldsFilter.setOptionalFilter(this.optionalFilter);
      this.electionsFilter.setOptionalFilter(this.optionalFilter);
      this.startBtn.click((function(_this) {
        return function(e) {
          return _this.onStartBtnClick(e);
        };
      })(this));
      this.downloadModal.modal({
        show: false,
        backdrop: 'static',
        keyboard: false
      });
      self = this;
      this.asideItem.click(function(e) {
        return self.onAsideItemClick($(this), e);
      });
      return this.form.submit((function(_this) {
        return function(e) {
          return _this.onFormSubmit(e);
        };
      })(this));
    };

    QueryView.prototype.trackInputChange = function(inputName, inputValue) {
      return _gaq.push(['_trackEvent', 'ConsultaTSE.Consulta', inputName, inputValue]);
    };

    QueryView.prototype.onFormSubmit = function(event) {
      event.preventDefault();
      if (this.query.isValid()) {
        this.setDownloadingState();
        return this.downloadResult();
      } else {
        return alert("Preencha todos os campos obrigatórios antes de continuar");
      }
    };

    QueryView.prototype.downloadResult = function() {
      console.log('Iniciando consulta...');
      return $.fileDownload(this.form.prop('action'), {
        httpMethod: this.form.prop('method'),
        data: this.query.getFormData(),
        dataType: 'text/csv',
        successCallback: (function(_this) {
          return function(url) {
            return _this.onFormSubmitDone();
          };
        })(this),
        failCallback: (function(_this) {
          return function(htmlStr, url) {
            return _this.onFormSubmitError(htmlStr);
          };
        })(this)
      });
    };

    QueryView.prototype.onFormSubmitDone = function() {
      var elapsedTime;
      this.reset();
      elapsedTime = new Date().getTime() - this.startTime.getTime();
      return this.trackInputChange('Query Performed', {
        data: this.query.getFormData(),
        elapsedTime: elapsedTime
      });
    };

    QueryView.prototype.onFormSubmitError = function(htmlStr) {
      this.reset();
      _gaq.push(['_trackPageview', '/consultaResultados/resultados-ERRO.csv']);
      alert('Ocorreu uma exceção ao realizar a consulta. Confira os logs');
      return console.error(htmlStr);
    };

    QueryView.prototype.reset = function() {
      return this.downloadModal.modal('hide');
    };

    QueryView.prototype.setDownloadingState = function() {
      console.log(this.query.getFormData());
      this.downloadModal.modal('show');
      return this.startTime = new Date();
    };

    QueryView.prototype.onAsideItemClick = function(sender, event) {
      var target;
      event.preventDefault();
      target = $(sender.attr('href'));
      return $('html, body').animate({
        scrollTop: target.offset().top - 100
      }, 300);
    };

    QueryView.prototype.onStartBtnClick = function(e) {
      e.preventDefault();
      return this.requiredFieldsFilter.focus();
    };

    return QueryView;

  })(ConsultaTSE.View);

}).call(this);

(function() {
  var extend = function(child, parent) { for (var key in parent) { if (hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; },
    hasProp = {}.hasOwnProperty;

  ConsultaTSE.FilterView = (function(superClass) {
    extend(FilterView, superClass);

    function FilterView(container, query) {
      FilterView.__super__.constructor.call(this, container);
      this.query = query;
    }

    FilterView.prototype.initializeComponents = function() {
      this.document = $('html, body');
      this.getUnavailableMessage();
      return this.getContent().hide();
    };

    FilterView.prototype.getUnavailableMessage = function() {
      if (this.unavailable == null) {
        this.unavailable = this.findComponent('unavailable');
      }
      return this.unavailable;
    };

    FilterView.prototype.getContent = function() {
      if (this.content == null) {
        this.content = this.findComponent('content');
      }
      return this.content;
    };

    FilterView.prototype.enable = function() {
      this.getUnavailableMessage().hide('slow');
      return this.getContent().slideDown();
    };

    FilterView.prototype.disable = function() {
      this.getUnavailableMessage().show('slow');
      return this.getContent().slideUp();
    };

    FilterView.prototype.onContinueBtnClick = function() {
      if (this.nextStep != null) {
        return this.nextStep.focus();
      }
    };

    FilterView.prototype.trackInputChange = function(inputName, inputValue) {
      return _gaq.push(['_trackEvent', this.getAnalyticsName(), inputName, inputValue]);
    };

    FilterView.prototype.focus = function() {
      return this.document.animate({
        scrollTop: this.container.offset().top - 100
      }, 300);
    };

    FilterView.prototype.checkValidity = function() {
      if (this.isValid()) {
        return this.container.addClass('status-valid');
      } else {
        return this.container.removeClass('status-valid');
      }
    };

    return FilterView;

  })(ConsultaTSE.View);

}).call(this);

(function() {
  ConsultaTSE.ColumnField = (function() {
    function ColumnField() {}

    ColumnField.prototype.getTableName = function() {
      return this.table_name || "";
    };

    ColumnField.prototype.setTableName = function(tableName) {
      return this.table_name = tableName;
    };

    ColumnField.prototype.getTableDescription = function() {
      return this.table_description || "";
    };

    ColumnField.prototype.setTableDescription = function(description) {
      return this.table_description = description;
    };

    ColumnField.prototype.getName = function() {
      return this.name || "";
    };

    ColumnField.prototype.setName = function(name) {
      return this.name = name;
    };

    ColumnField.prototype.getDescription = function() {
      return this.description || "";
    };

    ColumnField.prototype.setDescription = function(description) {
      return this.description = description;
    };

    ColumnField.prototype.getKey = function() {
      return (this.getTableName()) + "." + (this.getName());
    };

    ColumnField.fromRemote = function(remote) {
      var columnField;
      columnField = new ConsultaTSE.ColumnField();
      columnField.setName(remote.name);
      columnField.setDescription(remote.description);
      columnField.setTableName(remote.tableName);
      columnField.setTableDescription(remote.tableDescription);
      return columnField;
    };

    return ColumnField;

  })();

}).call(this);

(function() {
  ConsultaTSE.Partido = (function() {
    function Partido() {}

    Partido.prototype.setNome = function(nome) {
      return this.nome = nome;
    };

    Partido.prototype.getNome = function() {
      return this.nome || "";
    };

    Partido.prototype.setAno = function(ano) {
      return this.ano = ano;
    };

    Partido.prototype.getAno = function() {
      return this.ano || 0;
    };

    Partido.prototype.setCod = function(cod) {
      return this.cod = cod;
    };

    Partido.prototype.getCod = function() {
      return this.cod || 0;
    };

    Partido.prototype.setSigla = function(sigla) {
      return this.sigla = sigla;
    };

    Partido.prototype.getSigla = function() {
      return this.sigla || "";
    };

    Partido.prototype.getSiglaIdentificador = function() {
      return this.getSigla() + " (" + this.getCod() + ")";
    };

    Partido.FromRemote = function(remote) {
      var partido;
      partido = new ConsultaTSE.Partido();
      partido.setCod(remote.cod);
      partido.setAno(remote.ano);
      partido.setSigla(remote.sigla);
      partido.setNome(remote.nome);
      return partido;
    };

    Partido.IdentifierNameToCod = function(identifier) {
      var matches;
      matches = /\(([^)]+)\)/.exec(identifier);
      if (matches) {
        return matches[1];
      } else {
        return 0;
      }
    };

    return Partido;

  })();

}).call(this);

(function() {
  ConsultaTSE.Candidato = (function() {
    function Candidato() {}

    Candidato.prototype.getNome = function() {
      return this.nome || "";
    };

    Candidato.prototype.setNome = function(nome) {
      return this.nome = nome;
    };

    Candidato.prototype.getTitulo = function() {
      return this.titulo || "";
    };

    Candidato.prototype.setTitulo = function(titulo) {
      return this.titulo = titulo;
    };

    Candidato.prototype.getNomeIdentificador = function() {
      return this.getNome() + " (" + this.getTitulo() + ")";
    };

    Candidato.FromRemote = function(remote) {
      var candidato;
      candidato = new ConsultaTSE.Candidato;
      candidato.setNome(remote.nome);
      candidato.setTitulo(remote.titulo);
      return candidato;
    };

    Candidato.IdentifierNameToTitulo = function(identifier) {
      var matches;
      matches = /\(([^)]+)\)/.exec(identifier);
      if (matches) {
        return matches[1];
      } else {
        return 0;
      }
    };

    return Candidato;

  })();

}).call(this);

(function() {
  ConsultaTSE.Regiao = (function() {
    function Regiao() {}

    Regiao.prototype.getCod = function() {
      return this.cod || 0;
    };

    Regiao.prototype.setCod = function(cod) {
      return this.cod = cod;
    };

    Regiao.prototype.getNome = function() {
      return this.nome || "";
    };

    Regiao.prototype.setNome = function(nome) {
      return this.nome = nome;
    };

    Regiao.prototype.getNomeIdentificador = function() {
      return this.getNome() + " (" + this.getCod() + ")";
    };

    Regiao.FromPar = function(par) {
      var regiao;
      regiao = new ConsultaTSE.Regiao();
      regiao.setCod(parseInt(par.chave));
      regiao.setNome(par.valor);
      return regiao;
    };

    Regiao.IdentifierNameToCod = function(identifier) {
      var matches;
      matches = /\(([^)]+)\)/.exec(identifier);
      if (matches) {
        return matches[1];
      } else {
        return 0;
      }
    };

    return Regiao;

  })();

}).call(this);

(function() {
  var extend = function(child, parent) { for (var key in parent) { if (hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; },
    hasProp = {}.hasOwnProperty;

  ConsultaTSE.RequiredFieldsFilterView = (function(superClass) {
    extend(RequiredFieldsFilterView, superClass);

    RequiredFieldsFilterView.prototype.getAnalyticsName = function() {
      return 'ConsultaTSE.FiltrosObrigatorios';
    };

    function RequiredFieldsFilterView(container, query) {
      RequiredFieldsFilterView.__super__.constructor.call(this, container, query);
      this.initializeComponents();
    }

    RequiredFieldsFilterView.prototype.initializeComponents = function() {
      RequiredFieldsFilterView.__super__.initializeComponents.call(this);
      this.job = this.findInput('filtroCargo');
      this.regionalAggregation = this.findInput('nivelAgregacaoRegional');
      this.politicalAggregationLevel = this.findInput('nivelAgregacaoPolitica');
      this.turn = this.findInput('filtroTurno');
      this.turnContainer = this.findComponent('turnContainer');
      this.job.change((function(_this) {
        return function() {
          return _this.onJobChange(_this.job.find('option:selected'));
        };
      })(this));
      this.regionalAggregation.change((function(_this) {
        return function() {
          return _this.onRegionalAggregationChange(_this.regionalAggregation.find('option:selected'));
        };
      })(this));
      this.politicalAggregationLevel.change((function(_this) {
        return function() {
          return _this.onPoliticalAggregationLevel(_this.politicalAggregationLevel.find('option:selected'));
        };
      })(this));
      this.turn.change((function(_this) {
        return function() {
          return _this.onTurnChange(_this.turnContainer.is(':visible') ? _this.turn.find('option:selected') : null);
        };
      })(this));
      return this.updateTurnInput();
    };

    RequiredFieldsFilterView.prototype.setElectionsFilter = function(filter) {
      return this.electionsFilter = filter;
    };

    RequiredFieldsFilterView.prototype.setColumnsFilter = function(filter) {
      return this.collumnsFilter = filter;
    };

    RequiredFieldsFilterView.prototype.setOptionalFilter = function(filter) {
      return this.optionalFilter = filter;
    };

    RequiredFieldsFilterView.prototype.updateTurnInput = function() {
      if (this.query.getJob() === null) {
        return this.turnContainer.hide();
      } else {
        return this.query.getJob().toggleTurnInput(this.turnContainer);
      }
    };

    RequiredFieldsFilterView.prototype.onJobChange = function(selected) {
      this.trackInputChange('Cargo', selected.text());
      this.query.setJob(parseInt(selected.val()));
      this.updateTurnInput();
      this.checkValidity();
      this.query.setTurn(0);
      if (selected.val() !== "0") {
        return this.electionsFilter.enable();
      } else {
        return this.electionsFilter.disable();
      }
    };

    RequiredFieldsFilterView.prototype.onRegionalAggregationChange = function(selected) {
      if (selected !== null && selected.val() !== "") {
        this.trackInputChange('AgregacaoRegional', selected.text());
        this.query.setRegionalAggregation(parseInt(selected.val()));
        this.checkCollumnFieldsValidity();
      } else {
        this.query.setRegionalAggregation(0);
      }
      return this.checkValidity();
    };

    RequiredFieldsFilterView.prototype.onPoliticalAggregationLevel = function(selected) {
      if (selected.val() !== "") {
        this.trackInputChange('AgregacaoPolitica', selected.text());
        this.query.setPoliticalAggregationLevel(parseInt(selected.val()));
        this.checkValidity();
        return this.checkCollumnFieldsValidity();
      } else {
        return this.query.setPoliticalAggregationLevel(0);
      }
    };

    RequiredFieldsFilterView.prototype.onTurnChange = function(selected) {
      if (selected.val() !== "") {
        this.trackInputChange('Turno', selected.text());
        this.query.setTurn(parseInt(selected.val()));
        return this.checkValidity();
      } else {
        return this.query.setTurn(0);
      }
    };

    RequiredFieldsFilterView.prototype.checkCollumnFieldsValidity = function() {
      if (this.query.getPoliticalAggregationLevel() !== 0 && this.query.getRegionalAggregation() !== 0) {
        this.collumnsFilter.enable();
        if (this.optionalFilter != null) {
          return this.optionalFilter.update();
        }
      } else {
        return this.collumnsFilter.disable();
      }
    };

    RequiredFieldsFilterView.prototype.isValid = function() {
      var job, politicalAggregationLevel, regionalAggregation;
      job = this.query.getJob();
      regionalAggregation = this.query.getRegionalAggregation();
      politicalAggregationLevel = this.query.getPoliticalAggregationLevel();
      return job.isValid(this.turn) && regionalAggregation !== 0 && politicalAggregationLevel !== 0;
    };

    return RequiredFieldsFilterView;

  })(ConsultaTSE.FilterView);

}).call(this);

(function() {
  var extend = function(child, parent) { for (var key in parent) { if (hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; },
    hasProp = {}.hasOwnProperty;

  ConsultaTSE.ElectionsFilterView = (function(superClass) {
    extend(ElectionsFilterView, superClass);

    ElectionsFilterView.prototype.getAnalyticsName = function() {
      return 'ConsultaTSE.Eleiçoes';
    };

    function ElectionsFilterView(container, query) {
      ElectionsFilterView.__super__.constructor.call(this, container, query);
      this.initializeComponents();
    }

    ElectionsFilterView.prototype.initializeComponents = function() {
      ElectionsFilterView.__super__.initializeComponents.call(this);
      this.anosComponent = this.findComponent('anosComponent');
      this.anosDisponiveisAlert = this.findComponent('anosDisponiveisAlert');
      return this.reset();
    };

    ElectionsFilterView.prototype.setOptionalFilter = function(filter) {
      return this.optionalFilter = filter;
    };

    ElectionsFilterView.prototype.reset = function() {
      this.anosComponent.html('');
      this.query.clearYears();
      return this.anosDisponiveisAlert.hide();
    };

    ElectionsFilterView.prototype.disable = function() {
      ElectionsFilterView.__super__.disable.call(this);
      return this.reset();
    };

    ElectionsFilterView.prototype.enable = function() {
      ElectionsFilterView.__super__.enable.call(this);
      return this.update();
    };

    ElectionsFilterView.prototype.update = function() {
      var job, request;
      job = this.query.getJob();
      if (job !== null) {
        request = ConsultaTSE.EndPoints.GetYearsForJob({
          cargo: job.getId()
        });
        return request.done((function(_this) {
          return function(data) {
            return _this.onGetJobYears(data);
          };
        })(this));
      }
    };

    ElectionsFilterView.prototype.setYearsInputs = function(years) {
      ConsultaTSE.Components.iCheck.Load(years, 50);
      return years.on('ifChanged', (function(_this) {
        return function(event) {
          return _this.onYearChange(jQuery(event.currentTarget));
        };
      })(this));
    };

    ElectionsFilterView.prototype.onYearChange = function(selected) {
      var year;
      year = parseInt(selected.val());
      if (selected.prop('checked')) {
        this.trackInputChange('AnoSelecionado', year);
        this.query.addYear(year);
      } else {
        this.trackInputChange('AnoDeselecionado', year);
        this.query.removeYear(year);
      }
      return this.checkValidity();
    };

    ElectionsFilterView.prototype.onGetJobYears = function(data) {
      var group, i, len, ref, year;
      this.reset();
      ref = data.list;
      for (i = 0, len = ref.length; i < len; i++) {
        year = ref[i];
        group = this.buildYearItem(year);
        this.anosComponent.append(group);
      }
      return this.setYearsInputs(this.anosComponent.find('input'));
    };

    ElectionsFilterView.prototype.getSelectedYears = function() {
      return this.anosComponent.find('input:checked');
    };

    ElectionsFilterView.prototype.hasTooMuchSelectedYears = function() {
      return this.getSelectedYears().length > 3;
    };

    ElectionsFilterView.prototype.checkValidity = function() {
      ElectionsFilterView.__super__.checkValidity.call(this);
      if (this.hasTooMuchSelectedYears()) {
        this.anosDisponiveisAlert.show('slow');
      } else {
        this.anosDisponiveisAlert.hide('slow');
      }
      if (this.optionalFilter != null) {
        return this.optionalFilter.update();
      }
    };

    ElectionsFilterView.prototype.isValid = function() {
      return this.getSelectedYears().length > 0;
    };

    ElectionsFilterView.prototype.buildYearItem = function(year) {
      return jQuery("<label class='control-label control-year'>\n  <input type='checkbox' name='anosEscolhidos[]' class='icheck' value='" + year + "'/>\n  " + year + "\n</label>");
    };

    return ElectionsFilterView;

  })(ConsultaTSE.FilterView);

}).call(this);

(function() {
  var extend = function(child, parent) { for (var key in parent) { if (hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; },
    hasProp = {}.hasOwnProperty;

  ConsultaTSE.ColumnsFilterView = (function(superClass) {
    extend(ColumnsFilterView, superClass);

    ColumnsFilterView.prototype.getAnalyticsName = function() {
      return 'ConsultaTSE.ColunasFixasEOpcionais';
    };

    function ColumnsFilterView(container, query) {
      ColumnsFilterView.__super__.constructor.call(this, container, query);
      this.initializeComponents();
    }

    ColumnsFilterView.prototype.initializeComponents = function() {
      ColumnsFilterView.__super__.initializeComponents.call(this);
      this.optionalColumnsComponent = this.findComponent('optionalColumnsComponent');
      this.fixedColumnsComponent = this.findComponent('fixedColumnsComponent');
      return this.optionalFieldsInputs = [];
    };

    ColumnsFilterView.prototype.reset = function() {
      this.optionalColumnsComponent.html('');
      this.fixedColumnsComponent.html('');
      this.query.clearOptionalFields();
      return this.content.hide();
    };

    ColumnsFilterView.prototype.enable = function() {
      ColumnsFilterView.__super__.enable.call(this);
      return this.update();
    };

    ColumnsFilterView.prototype.update = function() {
      var politicalAggregationLevel, regionalAggregation, request;
      regionalAggregation = this.query.getRegionalAggregation();
      politicalAggregationLevel = this.query.getPoliticalAggregationLevel();
      if (regionalAggregation !== 0 && politicalAggregationLevel !== 0) {
        request = ConsultaTSE.EndPoints.GetCollumnsFilters({
          nivelAgregacaoRegional: regionalAggregation,
          nivelAgregacaoPolitica: politicalAggregationLevel
        });
        return request.done((function(_this) {
          return function(data) {
            return _this.onGetCollumnsFilters(data);
          };
        })(this));
      }
    };

    ColumnsFilterView.prototype.onGetCollumnsFilters = function(data) {
      var columnField, columnFieldData, fixedFields, i, j, len, len1, optionalFields;
      this.reset();
      fixedFields = data.collumnFieldsCollection.fixedFields || [];
      optionalFields = data.collumnFieldsCollection.optionalFields || [];
      for (i = 0, len = fixedFields.length; i < len; i++) {
        columnFieldData = fixedFields[i];
        columnField = ConsultaTSE.ColumnField.fromRemote(columnFieldData);
        this.addToFixedFields(columnField, true);
      }
      for (j = 0, len1 = optionalFields.length; j < len1; j++) {
        columnFieldData = optionalFields[j];
        columnField = ConsultaTSE.ColumnField.fromRemote(columnFieldData);
        this.addToOptionalFields(columnField, false);
      }
      return this.content.slideDown();
    };

    ColumnsFilterView.prototype.isValid = function() {
      return this.query.getOptionalFields().length > 0;
    };

    ColumnsFilterView.prototype.getCategoryFor = function(columnField, fixed) {
      var category, component;
      component = fixed ? this.fixedColumnsComponent : this.optionalColumnsComponent;
      category = component.find("ul[data-category='" + (columnField.getTableName()) + "']");
      if ((category.length != null) && category.length === 0) {
        category = this.buildCategory(columnField);
        component.append(category);
      }
      return category;
    };

    ColumnsFilterView.prototype.addToFixedFields = function(columnField, fixed) {
      var category, view;
      view = this.buildFixedCollumnItem(columnField);
      category = this.getCategoryFor(columnField, fixed);
      return category.append(view);
    };

    ColumnsFilterView.prototype.addToOptionalFields = function(columnField, fixed) {
      var category, view;
      view = this.buildOptionalCollumnItem(columnField);
      view.find('input').on('ifChanged', (function(_this) {
        return function(event) {
          return _this.onOptionalFieldChange(jQuery(event.currentTarget));
        };
      })(this));
      category = this.getCategoryFor(columnField, fixed);
      category.append(view);
      this.query.addOptionalField(columnField.getKey());
      return ConsultaTSE.Components.iCheck.Load(view.find('.icheck'));
    };

    ColumnsFilterView.prototype.onOptionalFieldChange = function(option) {
      var field;
      field = option.val();
      if (option.prop('checked')) {
        this.trackInputChange("Coluna Opcional (Selecionado)", field);
        this.query.addOptionalField(field);
      } else {
        this.trackInputChange("Coluna Opcional (Deselecionado)", field);
        this.query.removeOptionalField(field);
      }
      return this.checkValidity();
    };

    ColumnsFilterView.prototype.buildCategory = function(columnField) {
      return jQuery("<ul data-category='" + (columnField.getTableName()) + "'>\n  <li class='header'>" + (columnField.getTableDescription()) + "</li>\n</ul>");
    };

    ColumnsFilterView.prototype.buildOptionalCollumnItem = function(columnField) {
      return jQuery("<label class='control-label control-column'>\n  <input type='checkbox' name='camposOpcionais[]' class='icheck' value='" + (columnField.getKey()) + "' checked/>\n  " + (columnField.getDescription()) + "\n</label>");
    };

    ColumnsFilterView.prototype.buildFixedCollumnItem = function(columnField) {
      return jQuery("<li>" + (columnField.getDescription()) + "</li>");
    };

    return ColumnsFilterView;

  })(ConsultaTSE.FilterView);

}).call(this);

(function() {
  var extend = function(child, parent) { for (var key in parent) { if (hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; },
    hasProp = {}.hasOwnProperty;

  ConsultaTSE.OptionalFiltersView = (function(superClass) {
    extend(OptionalFiltersView, superClass);

    OptionalFiltersView.prototype.getAnalyticsName = function() {
      return 'ConsultaTSE.FiltrosOpcionais';
    };

    function OptionalFiltersView(container, query) {
      OptionalFiltersView.__super__.constructor.call(this, container, query);
      this.partidosSuggestions = new Bloodhound({
        local: [],
        datumTokenizer: Bloodhound.tokenizers.whitespace,
        queryTokenizer: Bloodhound.tokenizers.whitespace
      });
      this.candidatosSuggestions = new Bloodhound({
        remote: {
          url: ConsultaTSE.EndPoints.CandidatosAnosCargo,
          prepare: this.getPrepareCandidatosFactory(),
          transform: this.getTransformCandidatosFactory()
        },
        datumTokenizer: Bloodhound.tokenizers.whitespace,
        queryTokenizer: Bloodhound.tokenizers.whitespace
      });
      this.regioesSuggestions = new Bloodhound({
        remote: {
          url: ConsultaTSE.EndPoints.FiltroRegionalQuery,
          prepare: this.getPrepareRegioesFactory(),
          transform: this.getTransformRegioesFactory()
        },
        datumTokenizer: Bloodhound.tokenizers.whitespace,
        queryTokenizer: Bloodhound.tokenizers.whitespace
      });
      this.initializeComponents();
    }

    OptionalFiltersView.prototype.initializeComponents = function() {
      OptionalFiltersView.__super__.initializeComponents.call(this);
      this.partidos = this.findInput('partidos');
      this.candidatos = this.findInput('candidatos');
      this.nivelRegional = this.findInput('filtroNivelRegional');
      this.regioes = this.findInput('regioes');
      this.partidosInfoMessage = this.findComponent('partidosInfoMessage');
      this.regioesComponent = this.findComponent('regioesComponent');
      this.partidosSuggestions.initialize();
      this.partidos.tokenfield({
        typeahead: [
          null, {
            source: this.partidosSuggestions.ttAdapter()
          }
        ]
      });
      this.partidos.on('tokenfield:createdtoken', (function(_this) {
        return function(e) {
          return _this.onAddPartido(e);
        };
      })(this));
      this.partidos.on('tokenfield:removetoken', (function(_this) {
        return function(e) {
          return _this.onRemovePartido(e);
        };
      })(this));
      this.candidatosSuggestions.initialize();
      this.candidatos.tokenfield({
        typeahead: [
          null, {
            source: this.candidatosSuggestions.ttAdapter()
          }
        ]
      });
      this.candidatos.on('tokenfield:createdtoken', (function(_this) {
        return function(e) {
          return _this.onAddCandidato(e);
        };
      })(this));
      this.candidatos.on('tokenfield:removetoken', (function(_this) {
        return function(e) {
          return _this.onRemoveCandidato(e);
        };
      })(this));
      this.regioesSuggestions.initialize();
      this.regioes.tokenfield({
        typeahead: [
          null, {
            source: this.regioesSuggestions.ttAdapter()
          }
        ]
      });
      this.regioes.on('tokenfield:createdtoken', (function(_this) {
        return function(e) {
          return _this.onAddRegiao(e);
        };
      })(this));
      this.regioes.on('tokenfield:removetoken', (function(_this) {
        return function(e) {
          return _this.onRemoveRegiao(e);
        };
      })(this));
      this.nivelRegional.change((function(_this) {
        return function() {
          return _this.onNivelRegionalChange(_this.nivelRegional.find('option:selected'));
        };
      })(this));
      return this.regioesComponent.hide();
    };

    OptionalFiltersView.prototype.onRemovePartido = function(event) {
      this.query.removePartido(ConsultaTSE.Partido.IdentifierNameToCod(event.attrs.value));
      return null;
    };

    OptionalFiltersView.prototype.onRemoveCandidato = function(event) {
      var titulo;
      titulo = ConsultaTSE.Candidato.IdentifierNameToTitulo(event.attrs.label);
      if (titulo !== 0) {
        this.query.removeCandidato(titulo);
      }
      return null;
    };

    OptionalFiltersView.prototype.onRemoveRegiao = function(event) {
      var cod;
      cod = ConsultaTSE.Regiao.IdentifierNameToCod(event.attrs.label);
      if (cod !== 0) {
        this.query.removeRegiao(cod);
      }
      return null;
    };

    OptionalFiltersView.prototype.getElectionsYears = function() {
      return this.query.getYears();
    };

    OptionalFiltersView.prototype.onAddPartido = function(event) {
      this.query.addPartido(ConsultaTSE.Partido.IdentifierNameToCod(event.attrs.value));
      return null;
    };

    OptionalFiltersView.prototype.onAddCandidato = function(event) {
      var titulo;
      titulo = ConsultaTSE.Candidato.IdentifierNameToTitulo(event.attrs.label);
      if (titulo !== 0) {
        this.query.addCandidato(titulo);
      }
      return null;
    };

    OptionalFiltersView.prototype.onAddRegiao = function(event) {
      var cod;
      cod = ConsultaTSE.Regiao.IdentifierNameToCod(event.attrs.label);
      if (cod !== 0) {
        this.query.addRegiao(cod);
      }
      return null;
    };

    OptionalFiltersView.prototype.onNivelRegionalChange = function(selected) {
      if (selected.val() !== "") {
        this.regioesComponent.show('slow');
        this.query.setFiltroRegional(parseInt(selected.val()));
        return this.regioesSuggestions.initialize(true);
      } else {
        this.regioes.tokenfield('setTokens', []);
        return this.regioesComponent.hide('slow');
      }
    };

    OptionalFiltersView.prototype.getPrepareCandidatosFactory = function() {
      return (function(_this) {
        return function(query, settings) {
          var jobId, years;
          years = _this.getElectionsYears();
          jobId = _this.query.getJob().getId();
          settings.data = {
            q: query,
            anosList: years,
            cargo: jobId
          };
          return settings;
        };
      })(this);
    };

    OptionalFiltersView.prototype.getTransformCandidatosFactory = function() {
      return (function(_this) {
        return function(response) {
          var candidato, candidatos, data, i, len, ref;
          candidatos = [];
          ref = response.list;
          for (i = 0, len = ref.length; i < len; i++) {
            data = ref[i];
            candidato = ConsultaTSE.Candidato.FromRemote(data);
            candidatos.push(candidato.getNomeIdentificador());
          }
          return candidatos;
        };
      })(this);
    };

    OptionalFiltersView.prototype.getPrepareRegioesFactory = function() {
      return (function(_this) {
        return function(query, settings) {
          var nivelRegional;
          nivelRegional = _this.nivelRegional.find('option:selected').val();
          settings.data = {
            q: query,
            nivelRegional: nivelRegional
          };
          return settings;
        };
      })(this);
    };

    OptionalFiltersView.prototype.getTransformRegioesFactory = function() {
      return (function(_this) {
        return function(response) {
          var data, i, len, ref, regiao, regioes;
          regioes = [];
          ref = response.list;
          for (i = 0, len = ref.length; i < len; i++) {
            data = ref[i];
            regiao = ConsultaTSE.Regiao.FromPar(data);
            regioes.push(regiao.getNomeIdentificador());
          }
          return regioes;
        };
      })(this);
    };

    OptionalFiltersView.prototype.setPartidosCount = function(count) {
      var years;
      years = this.getElectionsYears().join(", ");
      if (count === 0) {
        return this.partidosInfoMessage.html("Nenhum partido encontrado para os anos " + years);
      } else {
        return this.partidosInfoMessage.html(count + " partidos encontrados para os anos " + years + ".");
      }
    };

    OptionalFiltersView.prototype.loadPartidos = function() {
      return ConsultaTSE.EndPoints.GetPartidosAnos({
        anosList: this.getElectionsYears()
      }).done((function(_this) {
        return function(data) {
          return _this.loadPartidosSuggestions(data.list);
        };
      })(this));
    };

    OptionalFiltersView.prototype.loadPartidosSuggestions = function(partidosList) {
      var data, i, len, partido;
      this.partidosSuggestions.clear();
      this.setPartidosCount(partidosList.length);
      for (i = 0, len = partidosList.length; i < len; i++) {
        data = partidosList[i];
        partido = ConsultaTSE.Partido.FromRemote(data);
        this.partidosSuggestions.local.push(partido.getSiglaIdentificador());
      }
      return this.partidosSuggestions.initialize(true);
    };

    OptionalFiltersView.prototype.loadCandidatos = function() {
      return this.candidatosSuggestions.initialize(true);
    };

    OptionalFiltersView.prototype.loadRegioes = function() {
      var el, i, len, option, ref, results;
      ref = this.nivelRegional.find('option');
      results = [];
      for (i = 0, len = ref.length; i < len; i++) {
        option = ref[i];
        el = jQuery(option);
        if (parseInt(el.val()) > this.query.getRegionalAggregation()) {
          results.push(el.hide());
        } else {
          results.push(el.show());
        }
      }
      return results;
    };

    OptionalFiltersView.prototype.reset = function() {
      this.partidos.tokenfield('setTokens', []);
      this.query.clearPartidos();
      this.candidatos.tokenfield('setTokens', []);
      return this.query.clearCandidatos();
    };

    OptionalFiltersView.prototype.update = function() {
      this.reset();
      if (this.getElectionsYears().length > 0) {
        this.loadPartidos();
        this.loadCandidatos();
        this.loadRegioes();
        return this.enable();
      } else {
        return this.disable();
      }
    };

    return OptionalFiltersView;

  })(ConsultaTSE.FilterView);

}).call(this);

//# sourceMappingURL=consulta.js.map
