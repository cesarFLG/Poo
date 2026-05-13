package org.example;

public class Musica extends Midia implements reproduzivel {

    private String artista;

    public Musica(String titulo, int duracao, String artista) {
        super(titulo, duracao);
        this.artista = artista;
    }

    @Override
    public void darPlay() {
        System.out.println("Tocando musica: " + titulo + " - Artista: " + artista);
    }

    @Override
    public double calcularCusto() {
        return 2.00;
    }
}