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

    return VereadorJob;

  })(ConsultaTSE.Job);

}).call(this);

(function() {
  ConsultaTSE.Query = (function() {
    function Query() {}

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
      return this.regionalAggregation || null;
    };

    Query.prototype.setPoliticalAggregationLevel = function(l) {
      return this.policalAggregationLevel = l;
    };

    Query.prototype.getPoliticalAggregationLevel = function() {
      return this.policalAggregationLevel || 0;
    };

    Query.prototype.setTurn = function(turn) {
      return this.turn = turn;
    };

    Query.prototype.getTurn = function() {
      return this.turn || 0;
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
      this.resultStep = new ConsultaTSE.ResultStepView(this.findComponent('resultStep'), this.query);
      this.asideItem = $('.aside-item');
      this.requiredFieldsFilter.setElectionsFilter(this.electionsFilter);
      this.requiredFieldsFilter.setColumnsFilter(this.columnsFilter);
      this.electionsFilter.setOptionalFilter(this.optionalFilter);
      this.startBtn.click((function(_this) {
        return function(e) {
          return _this.onStartBtnClick(e);
        };
      })(this));
      self = this;
      return this.asideItem.click(function(e) {
        return self.onAsideItemClick($(this), e);
      });
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
      this.initializeComponents();
    }

    FilterView.prototype.initializeComponents = function() {
      this.document = $('html, body');
      this.unavailable = this.findComponent('unavailable');
      this.content = this.findComponent('content');
      return this.content.hide();
    };

    FilterView.prototype.enable = function() {
      if (this.unavailable != null) {
        this.unavailable.hide('slow');
      }
      return this.content.slideDown();
    };

    FilterView.prototype.disable = function() {
      if (this.unavailable != null) {
        this.unavailable.show('slow');
      }
      return this.content.slideUp();
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
  var extend = function(child, parent) { for (var key in parent) { if (hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; },
    hasProp = {}.hasOwnProperty;

  ConsultaTSE.RequiredFieldsFilterView = (function(superClass) {
    extend(RequiredFieldsFilterView, superClass);

    function RequiredFieldsFilterView() {
      return RequiredFieldsFilterView.__super__.constructor.apply(this, arguments);
    }

    RequiredFieldsFilterView.prototype.getAnalyticsName = function() {
      return 'ConsultaTSE.FiltrosObrigatorios';
    };

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
          return _this.onTurnChange(_this.turn.find('option:selected'));
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
      if (selected.val() !== "0") {
        return this.electionsFilter.enable();
      } else {
        return this.electionsFilter.disable();
      }
    };

    RequiredFieldsFilterView.prototype.onRegionalAggregationChange = function(selected) {
      if (selected.val() !== "") {
        this.trackInputChange('AgregacaoRegional', selected.text());
        this.query.setRegionalAggregation(parseInt(selected.val()));
        this.checkValidity();
        return this.checkCollumnFieldsValidity();
      } else {
        return this.query.setTurn(0);
      }
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
        return this.collumnsFilter.enable();
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

    function ElectionsFilterView() {
      return ElectionsFilterView.__super__.constructor.apply(this, arguments);
    }

    ElectionsFilterView.prototype.getAnalyticsName = function() {
      return 'ConsultaTSE.Eleiçoes';
    };

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
        request = jQuery.get(ConsultaTSE.EndPoints.YearsForJob, {
          cargo: job.getId()
        });
        return request.done((function(_this) {
          return function(data) {
            return _this.onGetJobYears(data);
          };
        })(this));
      }
    };

    ElectionsFilterView.prototype.setYears = function(years) {
      this.years = years;
      ConsultaTSE.Components.iCheck.Load(years, 50);
      return this.years.on('ifChanged', (function(_this) {
        return function(event) {
          return _this.onYearSelected(jQuery(event.currentTarget));
        };
      })(this));
    };

    ElectionsFilterView.prototype.onYearSelected = function(selected) {
      this.trackInputChange('AnoSelecionado', selected.val());
      return this.checkValidity();
    };

    ElectionsFilterView.prototype.getYears = function() {
      return this.years || [];
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
      return this.setYears(this.anosComponent.find('input'));
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
        return this.anosDisponiveisAlert.show('slow');
      } else {
        return this.anosDisponiveisAlert.hide('slow');
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

    function ColumnsFilterView() {
      return ColumnsFilterView.__super__.constructor.apply(this, arguments);
    }

    ColumnsFilterView.prototype.initializeComponents = function() {
      ColumnsFilterView.__super__.initializeComponents.call(this);
      this.optionalColumnsComponent = this.findComponent('optionalColumnsComponent');
      return this.fixedColumnsComponent = this.findComponent('fixedColumnsComponent');
    };

    ColumnsFilterView.prototype.reset = function() {
      this.optionalColumnsComponent.html('');
      this.fixedColumnsComponent.html('');
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
        request = jQuery.get(ConsultaTSE.EndPoints.CollumnsFilters, {
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
      var group, i, j, keypair, len, len1, ref, ref1;
      this.reset();
      ref = data.formResultAux.camposFixos;
      for (i = 0, len = ref.length; i < len; i++) {
        keypair = ref[i];
        group = this.buildCollumnItem('camposEscolhidos', keypair.chave, keypair.valor);
        this.fixedColumnsComponent.append(group);
        ConsultaTSE.Components.iCheck.Load(group.find('.icheck'));
      }
      ref1 = data.formResultAux.camposOpcionais;
      for (j = 0, len1 = ref1.length; j < len1; j++) {
        keypair = ref1[j];
        group = this.buildCollumnItem('camposFixos', keypair.chave, keypair.valor);
        this.optionalColumnsComponent.append(group);
        ConsultaTSE.Components.iCheck.Load(group.find('.icheck'));
      }
      return this.content.slideDown();
    };

    ColumnsFilterView.prototype.isValid = function() {
      return true;
    };

    ColumnsFilterView.prototype.buildCollumnItem = function(input, value, message) {
      return jQuery("<label class='control-label control-column'>\n  <input type='checkbox' name='" + input + "[]' class='icheck' value='" + value + "' checked/>\n  " + message + "\n</label>");
    };

    return ColumnsFilterView;

  })(ConsultaTSE.FilterView);

}).call(this);

(function() {
  var extend = function(child, parent) { for (var key in parent) { if (hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; },
    hasProp = {}.hasOwnProperty;

  ConsultaTSE.OptionalFiltersView = (function(superClass) {
    extend(OptionalFiltersView, superClass);

    function OptionalFiltersView() {
      return OptionalFiltersView.__super__.constructor.apply(this, arguments);
    }

    OptionalFiltersView.prototype.initializeComponents = function() {
      return OptionalFiltersView.__super__.initializeComponents.call(this);
    };

    return OptionalFiltersView;

  })(ConsultaTSE.FilterView);

}).call(this);

(function() {
  var extend = function(child, parent) { for (var key in parent) { if (hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; },
    hasProp = {}.hasOwnProperty;

  ConsultaTSE.ResultStepView = (function(superClass) {
    extend(ResultStepView, superClass);

    function ResultStepView() {
      return ResultStepView.__super__.constructor.apply(this, arguments);
    }

    ResultStepView.prototype.initializeComponents = function() {};

    return ResultStepView;

  })(ConsultaTSE.FilterView);

}).call(this);

//# sourceMappingURL=consulta.js.map
