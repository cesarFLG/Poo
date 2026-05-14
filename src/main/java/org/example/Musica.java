package org.example;

public class Musica extends Midia implements Reproduzivel {

    private String artista;

    public Musica(String titulo, int duracao, String artista) {
        super(titulo, duracao);
        this.artista = artista;
    }

    @Override
    public void darPlay() {
        System.out.println("Tocando musica: " + gettitulo() + " - Artista: " + artista);

    }

    @Override
    public double calcularCusto() {
        return 2.00;
    }
    public int getDuracaoEmMinutos() {return super.getDuracaoEmMinutos();}
}