class ConsultaTSE.Candidato

  getNome: -> this.nome || ""
  setNome: (nome) -> this.nome = nome

  getTitulo: -> this.titulo || ""
  setTitulo: (titulo) -> this.titulo = titulo

  getNomeIdentificador: -> this.getNome() + " (" + this.getTitulo() + ")"

  @FromRemote: (remote) ->
    candidato = new ConsultaTSE.Candidato
    candidato.setNome(remote.nome)
    candidato.setTitulo(remote.titulo)

    return candidato

  @IdentifierNameToTitulo: (identifier) ->
    matches = /\(([^)]+)\)/.exec(identifier)
    if matches then matches[1] else 0