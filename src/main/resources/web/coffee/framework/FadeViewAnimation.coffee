class ConsultaTSE.FadeViewAnimation
  
  constructor : (@delay = 1300) ->

  animateTransition : (from, to) ->
    from.addClass 'fadeOut animated'
    .delay this.delay
    .html to
    .removeClass 'fadeOut'
    .addClass 'fadeIn animated'
    