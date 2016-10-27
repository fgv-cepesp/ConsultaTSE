class ConsultaTSE.Query

  #ID Based List
  @JOBS:
    0: ConsultaTSE.UndefinedJob
    1: ConsultaTSE.PresidenteJob
    2: ConsultaTSE.VicePresidenteJob
    3: ConsultaTSE.GovernadorJob
    4: ConsultaTSE.ViceGovernadorJob
    5: ConsultaTSE.SenadorJob
    6: ConsultaTSE.DeputadoFederalJob
    7: ConsultaTSE.DeputadoEstadualJob
    8: ConsultaTSE.DeputadoDistritalJob
    9: ConsultaTSE.PrimeiroSuplenteSenadorJob
    10: ConsultaTSE.SuplenteSenadorJob
    11:	ConsultaTSE.PrefeitoJob
    12:	ConsultaTSE.VicePrefeitoJob
    13: ConsultaTSE.VereadorJob

  constructor : () ->
    this.years = []
    this.optionalFields = []
    this.partidos = []
    this.regioes = []

  setJob: (jobId) -> this.job = new ConsultaTSE.Query.JOBS[jobId](jobId)
  getJob: -> this.job || new ConsultaTSE.UndefinedJob(0)

  setRegionalAggregation: (a) -> this.regionalAggregation = a
  getRegionalAggregation: -> this.regionalAggregation || 0

  setPoliticalAggregationLevel: (l) -> this.policalAggregationLevel = l
  getPoliticalAggregationLevel: -> this.policalAggregationLevel || 0

  getFiltroRegional: -> this.filtroRegional || 0
  setFiltroRegional: (regiao) -> this.filtroRegional = regiao

  setTurn: (turn) -> this.turn = turn
  getTurn: -> this.turn || 0

  clearYears: () -> this.years = []
  addYear: (year) -> this.years.push(year)
  removeYear: (year) -> this.years = this.years.filter (y) -> y isnt year
  getYears: -> this.years

  clearOptionalFields: () -> this.optionalFields = []
  addOptionalField: (field) -> this.optionalFields.push(field)
  removeOptionalField: (field) -> this.optionalFields = this.optionalFields.filter (f) -> f isnt field
  getOptionalFields: -> this.optionalFields

  getPartidos: -> this.partidos
  addPartido: (cod) -> this.partidos.push(cod)
  clearPartidos: -> this.partidos = []
  removePartido: (cod) -> this.partidos = this.partidos.filter (p) -> p isnt cod

  getCandidatos: -> this.candidatos
  addCandidato: (cod) -> this.candidatos.push(cod)
  clearCandidatos: -> this.candidatos = []
  removeCandidato: (cod) -> this.candidatos = this.candidatos.filter (p) -> p isnt cod

  getRegioes: -> this.regioes
  addRegiao: (cod) -> this.regioes.push(cod)
  clearRegioes: -> this.regioes = []
  removeRegiao: (cod) -> this.regioes = this.regioes.filter (r) -> r isnt cod

  isValid: -> this.getJob().getId() isnt 0 and this.getRegionalAggregation() isnt 0 and this.getPoliticalAggregationLevel() isnt 0

  getFormData: ->
    data =
      anos: this.getYears()
      cargo: this.getJob().getId()
      agregacaoRegional: this.getRegionalAggregation()
      agregacaoPolitica: this.getPoliticalAggregationLevel()
      camposOpcionais: this.getOptionalFields()
      partidos: this.getPartidos()
      candidatos: this.getCandidatos()
      filtroRegional: this.getFiltroRegional()
      regioes: this.getRegioes()

    if this.getTurn() isnt 0 then data.turno = this.getTurn()

    return data


  reset: ->
    this.clearYears()
    this.clearOptionalFields()
    this.clearPartidos()
    this.clearRegioes()
    this.setJob(0)
    this.setRegionalAggregation(0)
    this.setPoliticalAggregationLevel(0)
    this.setTurn(1)
    this.setFiltroRegional(0)
