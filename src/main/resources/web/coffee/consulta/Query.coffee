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

  setJob: (jobId) -> this.job = new ConsultaTSE.Query.JOBS[jobId](jobId)
  getJob: -> this.job || new ConsultaTSE.UndefinedJob(0)

  setRegionalAggregation: (a) -> this.regionalAggregation = a
  getRegionalAggregation: -> this.regionalAggregation || null

  setPoliticalAggregationLevel: (l) -> this.policalAggregationLevel = l
  getPoliticalAggregationLevel: -> this.policalAggregationLevel || 0

  setTurn: (turn) -> this.turn = turn
  getTurn: -> this.turn || 0