package br.fgv.model;

import java.io.Serializable;

public class Candidato implements Serializable {
    private String titulo;
    private String nome;
    private int partido_cod;
    private int cargo_cod;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getPartido_cod() {
        return partido_cod;
    }

    public void setPartido_cod(int partido_cod) {
        this.partido_cod = partido_cod;
    }

    public int getCargo_cod() {
        return cargo_cod;
    }

    public void setCargo_cod(int cargo_cod) {
        this.cargo_cod = cargo_cod;
    }

    public String getNomeIdentificador() {
        return this.getNome() + " (" + this.getTitulo() + ")";
    }
}
