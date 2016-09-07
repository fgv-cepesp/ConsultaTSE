class ConsultaTSE.AsyncView extends ConsultaTSE.View

  constructor : (container, address, data) ->
    super(container)
    this.loadView(address, data)
    this.transitionAnimation = new ConsultaTSE.FadeViewAnimation()
    this.loadingView = new ConsultaTSE.LoadingView()
    this.loadElement(this.loadingView.container)

  onLoad : () ->

  loadView : (address, data) ->
    $.get(address, data).done (html) =>
      this.loadElement(jQuery(html))
      this.onLoad()

  loadElement : (element) ->
    this.transitionAnimation.animateTransition(this.container, element)