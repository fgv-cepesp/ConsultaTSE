class ConsultaTSE.OptionalFiltersView extends ConsultaTSE.FilterView

  getAnalyticsName: -> 'ConsultaTSE.FiltrosOpcionais'

  constructor: (container, query) ->
    super(container, query)
    this.partidosSuggestions = new Bloodhound(local: [], datumTokenizer: Bloodhound.tokenizers.whitespace, queryTokenizer: Bloodhound.tokenizers.whitespace)
    this.candidatosSuggestions = new Bloodhound(
      remote:
        url: ConsultaTSE.EndPoints.CandidatosAnosCargo
        prepare: this.getPrepareCandidatosFactory()
        transform: this.getTransformCandidatosFactory()
      datumTokenizer: Bloodhound.tokenizers.whitespace
      queryTokenizer: Bloodhound.tokenizers.whitespace
    )

    this.regioesSuggestions = new Bloodhound(
      remote:
        url: ConsultaTSE.EndPoints.FiltroRegionalQuery
        prepare: this.getPrepareRegioesFactory()
        transform: this.getTransformRegioesFactory()
      datumTokenizer: Bloodhound.tokenizers.whitespace
      queryTokenizer: Bloodhound.tokenizers.whitespace
    )
    this.initializeComponents()

  initializeComponents: ->
    super()
    this.partidos = this.findInput('partidos')
    this.candidatos = this.findInput('candidatos')
    this.nivelRegional = this.findInput('filtroNivelRegional')
    this.regioes = this.findInput('regioes')
    this.partidosInfoMessage = this.findComponent('partidosInfoMessage')
    this.regioesComponent = this.findComponent('regioesComponent')

    this.partidosSuggestions.initialize()
    this.partidos.tokenfield(typeahead: [null, { source: this.partidosSuggestions.ttAdapter() }])
    this.partidos.on 'tokenfield:createdtoken', (e) => this.onAddPartido(e)
    this.partidos.on 'tokenfield:removetoken', (e) => this.onRemovePartido(e)

    this.candidatosSuggestions.initialize()
    this.candidatos.tokenfield(typeahead: [null, { source: this.candidatosSuggestions.ttAdapter() }])
    this.candidatos.on 'tokenfield:createdtoken', (e) => this.onAddCandidato(e)
    this.candidatos.on 'tokenfield:removetoken', (e) => this.onRemoveCandidato(e)

    this.regioesSuggestions.initialize()
    this.regioes.tokenfield(typeahead: [null, { source: this.regioesSuggestions.ttAdapter() }])
    this.regioes.on 'tokenfield:createdtoken', (e) => this.onAddRegiao(e)
    this.regioes.on 'tokenfield:removetoken', (e) => this.onRemoveRegiao(e)

    this.nivelRegional.change => this.onNivelRegionalChange(this.nivelRegional.find('option:selected'))
    this.regioesComponent.hide()

  onRemovePartido: (event) ->
    this.query.removePartido(ConsultaTSE.Partido.IdentifierNameToCod(event.attrs.value))
    return null

  onRemoveCandidato: (event) ->
    titulo = ConsultaTSE.Candidato.IdentifierNameToTitulo(event.attrs.label)
    if titulo isnt 0 then this.query.removeCandidato(titulo)
    return null

  onRemoveRegiao: (event) ->
    cod = ConsultaTSE.Regiao.IdentifierNameToCod(event.attrs.label)
    if cod isnt 0 then this.query.removeRegiao(cod)
    return null

  getElectionsYears: -> this.query.getYears()

  onAddPartido: (event) ->
    this.query.addPartido(ConsultaTSE.Partido.IdentifierNameToCod(event.attrs.value))
    return null

  onAddCandidato: (event) ->
    titulo = ConsultaTSE.Candidato.IdentifierNameToTitulo(event.attrs.label)
    if titulo isnt 0 then this.query.addCandidato(titulo)
    return null

  onAddRegiao: (event) ->
    cod = ConsultaTSE.Regiao.IdentifierNameToCod(event.attrs.label)
    if cod isnt 0 then this.query.addRegiao(cod)
    return null

  onNivelRegionalChange: (selected) ->
    if selected.val() isnt ""
      this.regioesComponent.show('slow')
      this.query.setFiltroRegional(parseInt(selected.val()))
      this.regioesSuggestions.initialize(true)
    else
      this.regioes.tokenfield('setTokens', [])
      this.regioesComponent.hide('slow')

  getPrepareCandidatosFactory: -> (query, settings) =>
    years = this.getElectionsYears()
    jobId = this.query.getJob().getId()
    settings.data = q: query, anosList: years, cargo: jobId
    return settings

  getTransformCandidatosFactory: -> (response) =>
    candidatos = []
    for data in response.list
      candidato = ConsultaTSE.Candidato.FromRemote(data)
      candidatos.push(candidato.getNomeIdentificador())
    return candidatos

  getPrepareRegioesFactory: -> (query, settings) =>
    nivelRegional = this.nivelRegional.find('option:selected').val()
    settings.data = q: query, nivelRegional: nivelRegional
    return settings

  getTransformRegioesFactory: -> (response) =>
    regioes = []
    for data in response.list
      regiao = ConsultaTSE.Regiao.FromPar(data)
      regioes.push(regiao.getNomeIdentificador())
    return regioes

  setPartidosCount: (count) ->
    years = this.getElectionsYears().join(", ")
    if count is 0
      this.partidosInfoMessage.html "Nenhum partido encontrado para os anos #{years}"
    else
      this.partidosInfoMessage.html "#{count} partidos encontrados para os anos #{years}."

  loadPartidos: ->
    ConsultaTSE.EndPoints.GetPartidosAnos(anosList: this.getElectionsYears()).done (data) =>
      this.loadPartidosSuggestions(data.list)

  loadPartidosSuggestions: (partidosList) ->
    this.partidosSuggestions.clear()
    this.setPartidosCount(partidosList.length)
    for data in partidosList
      partido = ConsultaTSE.Partido.FromRemote(data)
      this.partidosSuggestions.local.push(partido.getSiglaIdentificador())
    this.partidosSuggestions.initialize(true)

  loadCandidatos: ->
    this.candidatosSuggestions.initialize(true)

  loadRegioes: ->
    for option in this.nivelRegional.find('option')
      el = jQuery(option)
      if parseInt(el.val()) > this.query.getRegionalAggregation()
        el.hide()
      else
        el.show()

  reset: ->
    this.partidos.tokenfield('setTokens', [])
    this.query.clearPartidos()
    this.candidatos.tokenfield('setTokens', [])
    this.query.clearCandidatos()

  update: ->
    this.reset()
    if this.getElectionsYears().length > 0
      this.loadPartidos()
      this.loadCandidatos()
      this.loadRegioes()
      this.enable()
    else
      this.disable()
