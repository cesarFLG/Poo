package org.example;


public class Main {
    public static void main(String[] args) {
        Filme filme = new Filme("O Senhor dos Aneis", 178, "4K");
        Musica musica = new Musica("Imagine", 3, "John Lennon");


        System.out.println("Duracao do Filme: " + conversorTempo.formatoMinutos(filme.getDuracaoEmMinutos()));


        processarPlayer(filme);
        processarPlayer(musica);
    }

    public static void processarPlayer(reproduzivel item) {
        item.darPlay();
    }
}