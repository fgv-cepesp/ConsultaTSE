class ConsultaTSE.SlideViewAnimation

    animateTransition: (from, to) ->
        holder = from.parent()
        holder.css({overflow: 'hidden'})
        from.addClass('slideOutLeft animated')
        setTimeout((() ->
            from.remove()
            holder.append(to.addClass('animated slideInRight'))
        ), 1300)
