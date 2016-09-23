class ConsultaTSE.QueryView extends ConsultaTSE.View

  constructor: (container) ->
    super(container)
    this.query = new ConsultaTSE.Query()
    this.initializeComponents()

  initializeComponents: ->
    this.startBtn = this.findComponent('startBtn')
    this.resetBtn = this.findComponent('resetBtn')
    this.requiredFieldsFilter = new ConsultaTSE.RequiredFieldsFilterView(this.findComponent('requiredFieldsFilter'), this.query)
    this.electionsFilter = new ConsultaTSE.ElectionsFilterView(this.findComponent('electionsFilter'), this.query)
    this.columnsFilter = new ConsultaTSE.ColumnsFilterView(this.findComponent('columnsFilter'), this.query)
    this.optionalFilter = new ConsultaTSE.OptionalFiltersView(this.findComponent('optionalFilters'), this.query)
    this.asideItem = $('.aside-item')
    this.form = this.findElement('form')
    this.downloadModal = this.findElement('#downloadModal')

    this.requiredFieldsFilter.setElectionsFilter(this.electionsFilter)
    this.requiredFieldsFilter.setColumnsFilter(this.columnsFilter)
    this.requiredFieldsFilter.setOptionalFilter(this.optionalFilter)
    this.electionsFilter.setOptionalFilter(this.optionalFilter)

    this.startBtn.click (e) => this.onStartBtnClick(e)
    this.downloadModal.modal(show: false, backdrop: 'static', keyboard: false)

    self = this
    this.asideItem.click (e) -> self.onAsideItemClick($(this), e)
    this.form.submit (e) => this.onFormSubmit(e)
    this.resetBtn.click (e) => this.onResetBtnClick(e)

  onResetBtnClick: (e) ->
    e.preventDefault()
    this.query.reset()
    this.requiredFieldsFilter.reset()
    this.electionsFilter.disable()
    this.columnsFilter.disable()
    this.optionalFilter.disable()

  trackInputChange: (inputName, inputValue) ->
    _gaq.push(['_trackEvent', 'ConsultaTSE.Consulta', inputName, inputValue]);

  onFormSubmit: (event) ->
    event.preventDefault()
    if this.query.isValid()
      this.setDownloadingState()
      this.downloadResult()
    else
      alert("Preencha todos os campos obrigatórios antes de continuar")

  downloadResult: ->
    $.fileDownload(this.form.prop('action'), {
      httpMethod: this.form.prop('method'), data: this.query.getFormData(), dataType: 'text/csv'
      successCallback: (url) => this.onFormSubmitDone()
      failCallback: (htmlStr, url) => this.onFormSubmitError(htmlStr)
    })

  onFormSubmitDone:  ->
    this.reset()
    elapsedTime = new Date().getTime() - this.startTime.getTime()
    this.trackInputChange('Query Performed', {data: this.query.getFormData(), elapsedTime: elapsedTime})

  onFormSubmitError: (htmlStr) ->
    this.reset()
    _gaq.push(['_trackPageview', '/consultaResultados/resultados-ERRO.csv']);
    alert('Ocorreu uma exceção ao realizar a consulta. Confira os logs')
    console.error(htmlStr)

  reset: ->
    this.downloadModal.modal('hide')

  setDownloadingState: ->
    this.downloadModal.modal('show')
    this.startTime = new Date()

  onAsideItemClick: (sender, event) ->
    event.preventDefault()
    target = $(sender.attr('href'))
    $('html, body').animate({scrollTop: target.offset().top - 100}, 300)

  onStartBtnClick: (e) ->
    e.preventDefault()
    this.requiredFieldsFilter.focus()