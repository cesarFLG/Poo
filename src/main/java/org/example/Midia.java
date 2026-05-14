package org.example;

public abstract class Midia {
    private String titulo;
    private final int duracaoEmMinutos;

    public Midia(String titulo, int duracaoEmMinutos) {
        this.titulo = titulo;
        this.duracaoEmMinutos = duracaoEmMinutos;
    }


    public abstract double calcularCusto();


    public String gettitulo() {
        return titulo;
    }

    protected void exibirDetalhes() {
    }

    protected int getDuracaoEmMinutos() {
        return duracaoEmMinutos;
    }
}