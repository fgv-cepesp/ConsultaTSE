class ConsultaTSE.Job
  constructor: (@id) ->
  getId: -> this.id
  isValid: -> true

class ConsultaTSE.UndefinedJob extends ConsultaTSE.Job
  showTurnInput: -> false
  getName: -> '-'
  isValid: -> false

class ConsultaTSE.PresidenteJob extends ConsultaTSE.Job
  showTurnInput: -> true
  getName: -> 'Presidente'
  isValid: (input) -> input.val() isnt ""

class ConsultaTSE.VicePresidenteJob extends ConsultaTSE.Job
  showTurnInput: -> true
  getName: -> 'Vice-Presidente'
  isValid: (input) -> input.val() isnt ""

class ConsultaTSE.GovernadorJob extends ConsultaTSE.Job
  showTurnInput: -> true
  getName: -> 'Governador'
  isValid: (input) -> input.val() isnt ""

class ConsultaTSE.ViceGovernadorJob extends ConsultaTSE.Job
  showTurnInput: -> true
  getName: -> 'Vice-Governador'
  isValid: (input) -> input.val() isnt ""

class ConsultaTSE.SenadorJob extends ConsultaTSE.Job
  showTurnInput: -> false
  getName: -> 'Senador'

class ConsultaTSE.DeputadoFederalJob extends ConsultaTSE.Job
  showTurnInput: -> false
  getName: -> 'Deputado Federal'

class ConsultaTSE.DeputadoEstadualJob extends ConsultaTSE.Job
  showTurnInput: -> false
  getName: -> 'Deputado Estadual'

class ConsultaTSE.DeputadoDistritalJob extends ConsultaTSE.Job
  showTurnInput: -> true
  getName: -> 'Deputado Distrital'

class ConsultaTSE.PrimeiroSuplenteSenadorJob extends ConsultaTSE.Job
  showTurnInput: -> false
  getName: -> 'Primeiro Suplente Senador'

class ConsultaTSE.SuplenteSenadorJob extends ConsultaTSE.Job
  showTurnInput: -> false
  getName: -> 'Suplente Senador'

class ConsultaTSE.PrefeitoJob extends ConsultaTSE.Job
  showTurnInput: -> true
  isValid: (input) -> input.val() isnt ""
  getName: -> 'Prefeito'

class ConsultaTSE.VicePrefeitoJob extends ConsultaTSE.Job
  showTurnInput: -> true
  isValid: (input) -> input.val() isnt ""
  getName: -> 'Vice-Prefeito'

class ConsultaTSE.VereadorJob extends ConsultaTSE.Job
  showTurnInput: -> false
  getName: -> 'Verador'
