class ConsultaTSE.Job
  constructor: (@id) ->
  getId: -> this.id
  isValid: -> true

class ConsultaTSE.UndefinedJob extends ConsultaTSE.Job
  toggleTurnInput: (input) -> input.hide()
  isValid: -> false

class ConsultaTSE.PresidenteJob extends ConsultaTSE.Job
  toggleTurnInput: (input) -> input.show()
  isValid: (input) -> input.val() isnt ""

class ConsultaTSE.VicePresidenteJob extends ConsultaTSE.Job
  toggleTurnInput: (input) -> input.show()
  isValid: (input) -> input.val() isnt ""

class ConsultaTSE.GovernadorJob extends ConsultaTSE.Job
  toggleTurnInput: (input) -> input.show()
  isValid: (input) -> input.val() isnt ""

class ConsultaTSE.ViceGovernadorJob extends ConsultaTSE.Job
  toggleTurnInput: (input) -> input.show()
  isValid: (input) -> input.val() isnt ""

class ConsultaTSE.SenadorJob extends ConsultaTSE.Job
  toggleTurnInput: (input) -> input.hide()

class ConsultaTSE.DeputadoFederalJob extends ConsultaTSE.Job
  toggleTurnInput: (input) -> input.hide()

class ConsultaTSE.DeputadoEstadualJob extends ConsultaTSE.Job
  toggleTurnInput: (input) -> input.hide()

class ConsultaTSE.DeputadoDistritalJob extends ConsultaTSE.Job
  toggleTurnInput: (input) -> input.show()

class ConsultaTSE.PrimeiroSuplenteSenadorJob extends ConsultaTSE.Job
  toggleTurnInput: (input) -> input.hide()

class ConsultaTSE.SuplenteSenadorJob extends ConsultaTSE.Job
  toggleTurnInput: (input) -> input.hide()

class ConsultaTSE.PrefeitoJob extends ConsultaTSE.Job
  toggleTurnInput: (input) -> input.show()
  isValid: (input) -> input.val() isnt ""

class ConsultaTSE.VicePrefeitoJob extends ConsultaTSE.Job
  toggleTurnInput: (input) -> input.show()
  isValid: (input) -> input.val() isnt ""

class ConsultaTSE.VereadorJob extends ConsultaTSE.Job
  toggleTurnInput: (input) -> input.hide()
