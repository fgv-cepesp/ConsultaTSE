class ConsultaTSE.ColumnsFilterView extends ConsultaTSE.FilterView

  getAnalyticsName: -> 'ConsultaTSE.ColunasFixasEOpcionais'

  constructor: (container, query) ->
    super(container, query)
    this.initializeComponents()

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
        nivelAgregacaoRegional: regionalAggregation, nivelAgregacaoPolitica: politicalAggregationLevel
      })
      request.done (data) => this.onGetCollumnsFilters(data)

  onGetCollumnsFilters: (data) ->
    this.reset()
    fixedFields = data.collumnFieldsCollection.fixedFields || []
    optionalFields = data.collumnFieldsCollection.optionalFields || []
    for columnFieldData in fixedFields
      columnField = ConsultaTSE.ColumnField.fromRemote(columnFieldData)
      this.addToFixedFields(columnField, true)

    for columnFieldData in optionalFields
      columnField = ConsultaTSE.ColumnField.fromRemote(columnFieldData)
      this.addToOptionalFields(columnField, false)

    this.content.slideDown()

  isValid: -> true

  getCategoryFor: (columnField, fixed) ->
    component = if fixed then this.fixedColumnsComponent else this.optionalColumnsComponent
    category = component.find("ul[data-category='#{columnField.getTableName()}']")
    if category.length? and category.length is 0
      category = this.buildCategory(columnField)
      component.append(category)

    return category

  addToFixedFields: (columnField, fixed) ->
    view = this.buildFixedCollumnItem(columnField)
    category = this.getCategoryFor(columnField, fixed)
    category.append(view)

  addToOptionalFields: (columnField, fixed) ->
    view = this.buildOptionalCollumnItem(columnField)
    view.find('input').on 'ifChanged', (event) => this.onOptionalFieldChange(jQuery(event.currentTarget))
    category = this.getCategoryFor(columnField, fixed)
    category.append(view)
    ConsultaTSE.Components.iCheck.Load(view.find('.icheck'))

  onOptionalFieldChange: (option) ->
    checked = if option.is('checked') then 'Selecionado' : 'Deselecionado'
    this.trackInputChange("Coluna Opcional (#{checked})", option.val())
    this.checkValidity()

  buildCategory: (columnField) -> jQuery """
    <ul data-category='#{columnField.getTableName()}'>
      <li class='header'>#{columnField.getTableDescription()}</li>
    </ul>
  """

  buildOptionalCollumnItem: (columnField) -> jQuery """
      <label class='control-label control-column'>
        <input type='checkbox' name='camposOpcionais[]' class='icheck' value='#{columnField.getKey()}' checked/>
        #{columnField.getDescription()}
      </label>
    """

  buildFixedCollumnItem: (columnField) -> jQuery """
      <li>#{columnField.getDescription()}</li>
    """