window.ConsultaTSE = window.ConsultaTSE || {}

ConsultaTSE.Components = ConsultaTSE.Components || {}
ConsultaTSE.Components.iCheck = Load: (selector = $('.icheck'), increaseArea = 20) ->
  selector.iCheck(
    checkboxClass: 'icheckbox_flat-blue'
    radioClass: 'iradio_flat-blue'
    increaseArea: "#{increaseArea}%"
  )

ConsultaTSE.InitializeServices = (config) ->

  # Mixpanel Service
  if config.mixpanel? and config.mixpanel.project_token isnt "" and mixpanel?
    ConsultaTSE.Mixpanel = mixpanel
    ConsultaTSE.Mixpanel.init(config.mixpanel.project_token)

    if ConsultaTSE.AuthUser?
      ConsultaTSE.Mixpanel.identify(ConsultaTSE.AuthUser.getId())
  else
    console.warn('Could not initialize Mixpanel Service! Some unexpected errors may appear.')

  if config.endpoints?
    ConsultaTSE.EndPoints = {}
    ConsultaTSE.EndPoints.GetYearsForJob = (data) -> jQuery.get(config.endpoints.yearsForJob, data)
    ConsultaTSE.EndPoints.GetCollumnsFilters = (data) -> jQuery.get(config.endpoints.collumnsFilters, data)
    ConsultaTSE.EndPoints.GetPartidosAnos = (data) -> jQuery.get(config.endpoints.partidosAnos, data)
    ConsultaTSE.EndPoints.GetNivelRegional = (data) -> jQuery.get(config.endpoints.nivelRegional, data)
    ConsultaTSE.EndPoints.CandidatosAnosCargo = config.endpoints.candidatosAnosCargo
    ConsultaTSE.EndPoints.FiltroRegionalQuery = config.endpoints.filtroRegionalQuery

  ConsultaTSE.Components.iCheck.Load()