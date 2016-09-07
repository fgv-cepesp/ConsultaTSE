class ConsultaTSE.QueryView extends ConsultaTSE.View

  constructor: (container) ->
    super(container)
    this.query = new ConsultaTSE.Query()
    this.initializeComponents()

  initializeComponents: ->
    this.startBtn = this.findComponent('startBtn')
    this.requiredFieldsFilter = new ConsultaTSE.RequiredFieldsFilterView(this.findComponent('requiredFieldsFilter'), this.query)
    this.electionsFilter = new ConsultaTSE.ElectionsFilterView(this.findComponent('electionsFilter'), this.query)
    this.columnsFilter = new ConsultaTSE.ColumnsFilterView(this.findComponent('columnsFilter'), this.query)
    this.optionalFilter = new ConsultaTSE.OptionalFiltersView(this.findComponent('optionalFilters'), this.query)
    this.resultStep = new ConsultaTSE.ResultStepView(this.findComponent('resultStep'), this.query)
    this.asideItem = $('.aside-item')

    this.requiredFieldsFilter.setElectionsFilter(this.electionsFilter)
    this.requiredFieldsFilter.setColumnsFilter(this.columnsFilter)

    this.electionsFilter.setOptionalFilter(this.optionalFilter)

    this.startBtn.click (e) => this.onStartBtnClick(e)

    self = this
    this.asideItem.click (e) -> self.onAsideItemClick($(this), e)

  onAsideItemClick: (sender, event) ->
    event.preventDefault()
    target = $(sender.attr('href'))
    $('html, body').animate({scrollTop: target.offset().top - 100}, 300)

  onStartBtnClick: (e) ->
    e.preventDefault()
    this.requiredFieldsFilter.focus()