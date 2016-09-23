(function() {
  window.ConsultaTSE = window.ConsultaTSE || {};

  ConsultaTSE.Components = ConsultaTSE.Components || {};

  ConsultaTSE.Components.iCheck = {
    Load: function(selector, increaseArea) {
      if (selector == null) {
        selector = $('.icheck');
      }
      if (increaseArea == null) {
        increaseArea = 20;
      }
      return selector.iCheck({
        checkboxClass: 'icheckbox_flat-blue',
        radioClass: 'iradio_flat-blue',
        increaseArea: increaseArea + "%"
      });
    }
  };

  ConsultaTSE.InitializeServices = function(config) {
    if ((config.mixpanel != null) && config.mixpanel.project_token !== "" && (typeof mixpanel !== "undefined" && mixpanel !== null)) {
      ConsultaTSE.Mixpanel = mixpanel;
      ConsultaTSE.Mixpanel.init(config.mixpanel.project_token);
      if (ConsultaTSE.AuthUser != null) {
        ConsultaTSE.Mixpanel.identify(ConsultaTSE.AuthUser.getId());
      }
    } else {
      console.warn('Could not initialize Mixpanel Service! Some unexpected errors may appear.');
    }
    if (config.endpoints != null) {
      ConsultaTSE.EndPoints = {};
      ConsultaTSE.EndPoints.GetYearsForJob = function(data) {
        return jQuery.get(config.endpoints.yearsForJob, data);
      };
      ConsultaTSE.EndPoints.GetCollumnsFilters = function(data) {
        return jQuery.get(config.endpoints.collumnsFilters, data);
      };
      ConsultaTSE.EndPoints.GetPartidosAnos = function(data) {
        return jQuery.get(config.endpoints.partidosAnos, data);
      };
      ConsultaTSE.EndPoints.GetNivelRegional = function(data) {
        return jQuery.get(config.endpoints.nivelRegional, data);
      };
      ConsultaTSE.EndPoints.CandidatosAnosCargo = config.endpoints.candidatosAnosCargo;
      ConsultaTSE.EndPoints.FiltroRegionalQuery = config.endpoints.filtroRegionalQuery;
    }
    return ConsultaTSE.Components.iCheck.Load();
  };

}).call(this);

//# sourceMappingURL=app.js.map
