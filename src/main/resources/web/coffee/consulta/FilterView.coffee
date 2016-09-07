class ConsultaTSE.FilterView extends ConsultaTSE.View

  constructor: (container, query) ->
    super(container)
    this.query = query
    this.initializeComponents()

  initializeComponents: ->
    this.document = $('html, body')
    this.unavailable = this.findComponent('unavailable')
    this.content = this.findComponent('content')

    this.content.hide()

  enable: ->
    if this.unavailable? then this.unavailable.hide('slow')
    this.content.slideDown()

  disable: ->
    if this.unavailable? then this.unavailable.show('slow')
    this.content.slideUp()

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