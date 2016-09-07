class ConsultaTSE.ColumnsFilterView extends ConsultaTSE.FilterView

  initializeComponents: ->
    super()
    this.optionalColumnsComponent = this.findComponent('optionalColumnsComponent')
    this.fixedColumnsComponent = this.findComponent('fixedColumnsComponent')

  reset: ->
    this.optionalColumnsComponent.html('')
    this.fixedColumnsComponent.html('')
    this.content.hide()

  enable: ->
    super()
    this.update()

  update: ->
    regionalAggregation = this.query.getRegionalAggregation()
    politicalAggregationLevel = this.query.getPoliticalAggregationLevel()
    if regionalAggregation isnt 0 and politicalAggregationLevel isnt 0
      request = jQuery.get(ConsultaTSE.EndPoints.CollumnsFilters, {
        nivelAgregacaoRegional: regionalAggregation,
        nivelAgregacaoPolitica: politicalAggregationLevel
      })
      request.done (data) => this.onGetCollumnsFilters(data)

  onGetCollumnsFilters: (data) ->
    this.reset()
    for keypair in data.formResultAux.camposFixos
      group = this.buildCollumnItem('camposEscolhidos', keypair.chave, keypair.valor)
      this.fixedColumnsComponent.append(group)
      ConsultaTSE.Components.iCheck.Load(group.find('.icheck'))

    for keypair in data.formResultAux.camposOpcionais
      group = this.buildCollumnItem('camposFixos', keypair.chave, keypair.valor)
      this.optionalColumnsComponent.append(group)
      ConsultaTSE.Components.iCheck.Load(group.find('.icheck'))

    this.content.slideDown()

  isValid: -> true

  buildCollumnItem: (input, value, message) -> jQuery """
      <label class='control-label control-column'>
        <input type='checkbox' name='#{input}[]' class='icheck' value='#{value}' checked/>
        #{message}
      </label>
    """