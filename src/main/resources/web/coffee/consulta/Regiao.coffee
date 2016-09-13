class ConsultaTSE.Regiao

  getCod: -> this.cod || 0
  setCod: (cod) -> this.cod = cod

  getNome: -> this.nome || ""
  setNome: (nome) -> this.nome = nome

  getNomeIdentificador: -> this.getNome() + " (" + this.getCod() + ")"

  @FromPar: (par) ->
    regiao = new ConsultaTSE.Regiao()
    regiao.setCod(parseInt(par.chave))
    regiao.setNome(par.valor)

    return regiao

  @IdentifierNameToCod: (identifier) ->
    matches = /\(([^)]+)\)/.exec(identifier)
    if matches then matches[1] else 0