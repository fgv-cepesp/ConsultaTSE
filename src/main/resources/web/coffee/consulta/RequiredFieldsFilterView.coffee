class ConsultaTSE.RequiredFieldsFilterView extends ConsultaTSE.FilterView

  getAnalyticsName: -> 'ConsultaTSE.FiltrosObrigatorios'

  constructor: (container, query) ->
    super(container, query)
    this.initializeComponents()

  initializeComponents: ->
    super()
    this.job = this.findInput('filtroCargo')
    this.regionalAggregation = this.findInput('nivelAgregacaoRegional')
    this.politicalAggregationLevel = this.findInput('nivelAgregacaoPolitica')
    this.turn = this.findInput('filtroTurno')
    this.turnContainer = this.findComponent('turnContainer')

    this.job.change () => this.onJobChange(this.job.find('option:selected'))
    this.regionalAggregation.change () => this.onRegionalAggregationChange(this.regionalAggregation.find('option:selected'))
    this.politicalAggregationLevel.change () => this.onPoliticalAggregationLevel(this.politicalAggregationLevel.find('option:selected'))
    this.turn.change () => this.onTurnChange(if this.turnContainer.is(':visible') then this.turn.find('option:selected') else null)

    this.updateTurnInput()

  setElectionsFilter: (filter) -> this.electionsFilter = filter
  setColumnsFilter: (filter) -> this.collumnsFilter = filter
  setOptionalFilter: (filter) -> this.optionalFilter = filter

  reset: ->
    this.turn.val(1)
    this.job.val(0)
    this.regionalAggregation.val(0)
    this.politicalAggregationLevel.val(0)

  updateTurnInput: ->
    if this.query.getJob() is null
      this.turnContainer.hide('slow')
    else
      if this.query.getPoliticalAggregationLevel() is 3 or not this.query.getJob().showTurnInput()
        this.turnContainer.hide('slow')
      else
        this.turnContainer.show('slow')


  onJobChange: (selected) ->
    this.trackInputChange('Cargo', selected.text())
    this.query.setJob(parseInt(selected.val()))
    this.updateTurnInput()
    this.checkValidity()
    this.query.setTurn(0)

    if selected.val() isnt "0"
      this.electionsFilter.enable()
    else
      this.electionsFilter.disable()

  onRegionalAggregationChange: (selected) ->
    if selected isnt null and selected.val() isnt ""
      this.trackInputChange('AgregacaoRegional', selected.text())
      this.query.setRegionalAggregation(parseInt(selected.val()))
      this.checkCollumnFieldsValidity()
    else
      this.query.setRegionalAggregation(0)

    this.checkValidity()

  onPoliticalAggregationLevel: (selected) ->
    if selected.val() isnt ""
      this.trackInputChange('AgregacaoPolitica', selected.text())
      this.query.setPoliticalAggregationLevel(parseInt(selected.val()))
      this.checkValidity()
      this.updateTurnInput()
      this.checkCollumnFieldsValidity()
    else
      this.query.setPoliticalAggregationLevel(0)

  onTurnChange: (selected) ->
    if selected.val() isnt ""
      this.trackInputChange('Turno', selected.text())
      this.query.setTurn(parseInt(selected.val()))
      this.checkValidity()
    else
      this.query.setTurn(0)

  checkCollumnFieldsValidity: ->
    if this.query.getPoliticalAggregationLevel() isnt 0 and this.query.getRegionalAggregation() isnt 0
      this.collumnsFilter.enable()
      if this.optionalFilter? then this.optionalFilter.update()
    else
      this.collumnsFilter.disable()

  isValid: ->
    regionalAggregation = this.query.getRegionalAggregation()
    politicalAggregationLevel = this.query.getPoliticalAggregationLevel()
    return regionalAggregation isnt 0 and politicalAggregationLevel isnt 0
