package org.example;

public abstract class Midia {
    protected String titulo;
    protected int duracaoEmMinutos;

    public Midia(String titulo, int duracaoEmMinutos) {
        this.titulo = titulo;
        this.duracaoEmMinutos = duracaoEmMinutos;
    }


    public abstract double calcularCusto();


    public String getTitulo() {
        return titulo;
    }

    protected void exibirDetalhes() {
    }
}