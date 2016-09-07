class ConsultaTSE.ElectionsFilterView extends ConsultaTSE.FilterView

  getAnalyticsName: -> 'ConsultaTSE.Eleiçoes'

  initializeComponents: ->
    super()
    this.anosComponent = this.findComponent('anosComponent')
    this.anosDisponiveisAlert = this.findComponent('anosDisponiveisAlert')

    this.reset()

  setOptionalFilter: (filter) -> this.optionalFilter = filter

  reset: ->
    this.anosComponent.html('')
    this.anosDisponiveisAlert.hide()

  disable: ->
    super()
    this.reset()

  enable: ->
    super()
    this.update()

  update: ->
    job = this.query.getJob()
    if job isnt null
      request = jQuery.get(ConsultaTSE.EndPoints.YearsForJob, {cargo: job.getId()})
      request.done (data) => this.onGetJobYears(data)

  setYears: (years) ->
    this.years = years
    ConsultaTSE.Components.iCheck.Load(years, 50)
    this.years.on 'ifChanged', (event) => this.onYearSelected(jQuery(event.currentTarget))

  onYearSelected: (selected) ->
    this.trackInputChange('AnoSelecionado', selected.val())
    this.checkValidity()

  getYears: -> this.years || []

  onGetJobYears: (data) ->
    this.reset()
    for year in data.list
      group = this.buildYearItem(year)
      this.anosComponent.append(group)

    this.setYears(this.anosComponent.find('input'))

  getSelectedYears: -> this.anosComponent.find('input:checked')

  hasTooMuchSelectedYears: -> this.getSelectedYears().length > 3

  checkValidity: ->
    super()
    if this.hasTooMuchSelectedYears()
      this.anosDisponiveisAlert.show('slow')
    else
      this.anosDisponiveisAlert.hide('slow')

  isValid: -> this.getSelectedYears().length > 0

  buildYearItem: (year) -> jQuery """
      <label class='control-label control-year'>
        <input type='checkbox' name='anosEscolhidos[]' class='icheck' value='#{year}'/>
        #{year}
      </label>
    """
