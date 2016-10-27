class ConsultaTSE.ElectionsFilterView extends ConsultaTSE.FilterView

  getAnalyticsName: -> 'ConsultaTSE.Eleiçoes'

  constructor: (container, query) ->
    super(container, query)
    this.initializeComponents()

  initializeComponents: ->
    super()
    this.anosComponent = this.findComponent('anosComponent')
    this.anosDisponiveisAlert = this.findComponent('anosDisponiveisAlert')

    this.reset()

  setOptionalFilter: (filter) ->
    this.optionalFilter = filter

  reset: ->
    this.anosComponent.html('')
    this.query.clearYears()
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
      request = ConsultaTSE.EndPoints.GetYearsForJob(cargo: job.getId())
      request.done (data) => this.onGetJobYears(data)

  setYearsInputs: (years) ->
    ConsultaTSE.Components.iCheck.Load(years, 50)
    years.on 'ifChanged', (event) => this.onYearChange(jQuery(event.currentTarget))

  onYearChange: (selected) ->
    year = parseInt(selected.val())

    if selected.prop('checked')
      this.trackInputChange('AnoSelecionado', year)
      this.query.addYear(year)
    else
      this.trackInputChange('AnoDeselecionado', year)
      this.query.removeYear(year)

    this.checkValidity()

  onGetJobYears: (data) ->
    this.reset()
    for year in data.list
      group = this.buildYearItem(year)
      this.anosComponent.append(group)

    this.setYearsInputs(this.anosComponent.find('input'))

  getSelectedYears: -> this.anosComponent.find('input:checked')

  hasTooMuchSelectedYears: -> this.getSelectedYears().length > 3

  checkValidity: ->
    super()
    if this.hasTooMuchSelectedYears()
      this.anosDisponiveisAlert.show('slow')
    else
      this.anosDisponiveisAlert.hide('slow')

    if this.optionalFilter?
      this.optionalFilter.update()

  isValid: -> this.getSelectedYears().length > 0

  buildYearItem: (year) -> jQuery """
      <label class='control-label control-year'>
        <input type='checkbox' name='anosEscolhidos[]' class='icheck' value='#{year}'/>
        #{year}
      </label>
    """
