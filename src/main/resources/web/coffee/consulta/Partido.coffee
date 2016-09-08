class ConsultaTSE.Partido

  setNome: (nome) -> this.nome = nome
  getNome: -> this.nome || ""

  setAno: (ano) -> this.ano = ano
  getAno: -> this.ano || 0

  setCod: (cod) -> this.cod = cod
  getCod: -> this.cod || 0

  setSigla: (sigla) -> this.sigla = sigla
  getSigla: -> this.sigla || ""

  @FromRemote: (remote) ->
    partido = new ConsultaTSE.Partido()
    partido.setCod(remote.cod)
    partido.setAno(remote.ano)
    partido.setSigla(remote.sigla)
    partido.setNome(remote.nome)

    return partido