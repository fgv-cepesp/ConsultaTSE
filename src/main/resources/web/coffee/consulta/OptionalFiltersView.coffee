class ConsultaTSE.OptionalFiltersView extends ConsultaTSE.FilterView

  getAnalyticsName: -> 'ConsultaTSE.FiltrosOpcionais'

  constructor: (container, query) ->
    super(container, query)
    this.cache = {}
    this.partidosSuggestionsEngine = new Bloodhound(
      local: this.cache.partidos,
      datumTokenizer: Bloodhound.tokenizers.whitespace,
      queryTokenizer: Bloodhound.tokenizers.whitespace
    )
    this.initializeComponents()

  initializeComponents: ->
    super()
    this.partidos = this.findInput('partidos')
    this.candidatos = this.findInput('candidatos')
    this.nivelRegiao = this.findInput('filtroNivelRegional')

    this.partidosSuggestionsEngine.initialize()
    this.partidos.tokenfield(typeahead: [null, { source: this.partidosSuggestionsEngine.ttAdapter() }])
    this.candidatos.tokenfield()

  setElectionsFilter: (filter) -> this.electionsFilter = filter

  getElectionsYears: ->
    this.electionsFilter.getElectionsYears()

  loadPartidos: ->
    $.get(ConsultaTSE.EndPoints.PartidosAnos, {anosList: this.getElectionsYears()}).done (data) =>
      this.loadPartidosSuggestions(data.list)

  loadCandidatos: ->

  loadPartidosSuggestions: (partidosList) ->
    this.partidosSuggestionsEngine.clear()
    this.cache.partidos = Array()
    for partidoData in partidosList
      partido = ConsultaTSE.Partido.FromRemote(partidoData)
      this.cache.partidos.push(partido.getSigla())

    this.partidosSuggestionsEngine.local = this.cache.partidos
    this.partidosSuggestionsEngine.initialize(true)

  reset: ->
    this.partidos.tokenfield('setTokens', [])
    this.candidatos.tokenfield('setTokens', [])

  update: ->
    this.reset()
    if this.getElectionsYears().length > 0
      this.loadPartidos()
      this.loadCandidatos()
      this.enable()
    else
      this.disable()
