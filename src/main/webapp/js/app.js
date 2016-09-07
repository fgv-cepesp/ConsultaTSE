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
      ConsultaTSE.EndPoints.YearsForJob = config.endpoints.yearsForJob;
      ConsultaTSE.EndPoints.CollumnsFilters = config.endpoints.collumnsFilters;
    }
    return ConsultaTSE.Components.iCheck.Load();
  };

}).call(this);

//# sourceMappingURL=app.js.map
