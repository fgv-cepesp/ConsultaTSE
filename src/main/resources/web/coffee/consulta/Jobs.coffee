class ConsultaTSE.Job
  constructor: (@id) ->
  getId: -> this.id
  isValid: -> true

class ConsultaTSE.UndefinedJob extends ConsultaTSE.Job
  toggleTurnInput: (input) -> input.hide()
  getName: -> '-'
  isValid: -> false

class ConsultaTSE.PresidenteJob extends ConsultaTSE.Job
  toggleTurnInput: (input) -> input.show()
  getName: -> 'Presidente'
  isValid: (input) -> input.val() isnt ""

class ConsultaTSE.VicePresidenteJob extends ConsultaTSE.Job
  toggleTurnInput: (input) -> input.show()
  getName: -> 'Vice-Presidente'
  isValid: (input) -> input.val() isnt ""

class ConsultaTSE.GovernadorJob extends ConsultaTSE.Job
  toggleTurnInput: (input) -> input.show()
  getName: -> 'Governador'
  isValid: (input) -> input.val() isnt ""

class ConsultaTSE.ViceGovernadorJob extends ConsultaTSE.Job
  toggleTurnInput: (input) -> input.show()
  getName: -> 'Vice-Governador'
  isValid: (input) -> input.val() isnt ""

class ConsultaTSE.SenadorJob extends ConsultaTSE.Job
  toggleTurnInput: (input) -> input.hide()
  getName: -> 'Senador'

class ConsultaTSE.DeputadoFederalJob extends ConsultaTSE.Job
  toggleTurnInput: (input) -> input.hide()
  getName: -> 'Deputado Federal'

class ConsultaTSE.DeputadoEstadualJob extends ConsultaTSE.Job
  toggleTurnInput: (input) -> input.hide()
  getName: -> 'Deputado Estadual'

class ConsultaTSE.DeputadoDistritalJob extends ConsultaTSE.Job
  toggleTurnInput: (input) -> input.show()
  getName: -> 'Deputado Distrital'

class ConsultaTSE.PrimeiroSuplenteSenadorJob extends ConsultaTSE.Job
  toggleTurnInput: (input) -> input.hide()
  getName: -> 'Primeiro Suplente Senador'

class ConsultaTSE.SuplenteSenadorJob extends ConsultaTSE.Job
  toggleTurnInput: (input) -> input.hide()
  getName: -> 'Suplente Senador'

class ConsultaTSE.PrefeitoJob extends ConsultaTSE.Job
  toggleTurnInput: (input) -> input.show()
  isValid: (input) -> input.val() isnt ""
  getName: -> 'Prefeito'

class ConsultaTSE.VicePrefeitoJob extends ConsultaTSE.Job
  toggleTurnInput: (input) -> input.show()
  isValid: (input) -> input.val() isnt ""
  getName: -> 'Vice-Prefeito'

class ConsultaTSE.VereadorJob extends ConsultaTSE.Job
  toggleTurnInput: (input) -> input.hide()
  getName: -> 'Verador'
