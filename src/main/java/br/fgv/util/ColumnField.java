package br.fgv.util;

import br.fgv.model.Tabela;

import java.io.Serializable;

public class ColumnField implements Serializable {

    private String name;
    private String description;
    private String tableName;
    private String tableDescription;

    public ColumnField() {}
    public ColumnField(Tabela table) {
        this.tableName = table.getNome();
        this.tableDescription = table.getNomeDescritivo();
    }
    public ColumnField(String tableName, String tableDescription) {
        this.tableName = tableName;
        this.tableDescription = tableDescription;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getTableName() {
        return tableName;
    }
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableDescription() {
        return tableDescription;
    }
    public void setTableDescription(String tableDescription) {
        this.tableDescription = tableDescription;
    }

    public String getKey() {
        return this.getTableName() + "." + this.getName();
    }
}
