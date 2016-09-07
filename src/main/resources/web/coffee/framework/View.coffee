class ConsultaTSE.View

  constructor : (@container = jQuery('<div></div>')) ->

  findComponent : (component) ->
    this.container.find("[data-component='#{component}']")

  findInput : (inputName) ->
    this.container.find("[name='#{inputName}']")

  findElement : (query) ->
    this.container.find(query)

  getData : (key) ->
    this.container.data(key)