class ConsultaTSE.FilterView extends ConsultaTSE.View

  constructor: (container, query) ->
    super(container)
    this.query = query

  initializeComponents: ->
    this.document = $('html, body')
    this.getUnavailableMessage()
    this.getContent().hide()

  getUnavailableMessage: ->
    if not this.unavailable? then this.unavailable = this.findComponent('unavailable')
    return this.unavailable

  getContent: ->
    if not this.content? then this.content = this.findComponent('content')
    return this.content

  enable: ->
    this.getUnavailableMessage().hide('slow')
    this.getContent().slideDown()

  disable: ->
    this.getUnavailableMessage().show('slow')
    this.getContent().slideUp()

  onContinueBtnClick: ->
    if this.nextStep?
      this.nextStep.focus()

  trackInputChange: (inputName, inputValue) ->
    _gaq.push(['_trackEvent', this.getAnalyticsName(), inputName, inputValue]);

  focus: ->
    this.document.animate(scrollTop: this.container.offset().top - 100, 300)

  checkValidity: ->
    if this.isValid()
      this.container.addClass('status-valid')
    else
      this.container.removeClass('status-valid')