class ConsultaTSE.ColumnField

  getTableName: -> this.table_name || ""
  setTableName: (tableName) -> this.table_name = tableName

  getTableDescription: -> this.table_description || ""
  setTableDescription: (description) -> this.table_description = description

  getName: -> this.name || ""
  setName: (name) -> this.name = name

  getDescription: -> this.description || ""
  setDescription: (description) -> this.description = description

  getKey: -> "#{this.getTableName()}.#{this.getName()}"

  @fromRemote: (remote) ->
    columnField = new ConsultaTSE.ColumnField()
    columnField.setName(remote.name)
    columnField.setDescription(remote.description)
    columnField.setTableName(remote.tableName)
    columnField.setTableDescription(remote.tableDescription)

    return columnField