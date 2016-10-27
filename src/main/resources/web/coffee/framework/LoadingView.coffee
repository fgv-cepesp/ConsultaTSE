class ConsultaTSE.LoadingView extends ConsultaTSE.View
  
  constructor : () ->
    super()
    this.container.html(jQuery(this.getViewHtml()))

  getViewHtml : ->
    """
        <div class='row'>
            <div class='col-md-2 col-md-offset-5'>
                <div style="color: #47c6de" class="la-ball-fall">
                    <div></div>
                    <div></div>
                    <div></div>
                 </div>
            </div>
        </div>
    """
  