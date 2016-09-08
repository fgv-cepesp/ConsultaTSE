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
    ConsultaTSE.EndPoints.YearsForJob = config.endpoints.yearsForJob
    ConsultaTSE.EndPoints.CollumnsFilters = config.endpoints.collumnsFilters
    ConsultaTSE.EndPoints.PartidosAnos = config.endpoints.partidosAnos

  ConsultaTSE.Components.iCheck.Load()