package br.fgv.model;

public class Cargo {

    private int id;
    private String nomeCargo;

    public Cargo() {}
    public Cargo(int id, String nomeCargo) {
        this.id = id;
        this.nomeCargo = nomeCargo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeCargo() {
        return nomeCargo;
    }

    public void setNomeCargo(String nomeCargo) {
        this.nomeCargo = nomeCargo;
    }
}
