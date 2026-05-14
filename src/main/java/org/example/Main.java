package org.example;


public class Main {
    public static void main(String[] args) {
        Filme filme = new Filme("O Senhor dos Aneis", 178, "4K");
        Musica musica = new Musica("Imagine", 3, "John Lennon");


        System.out.println("Duracao do Filme: " + conversorTempo.formatoMinutos(filme.getDuracaoEmMinutos()));
        System.out.println("Duracao da musica: "+ conversorTempo.formatoMinutos(musica.duracaoEmMinutos));

        System.out.println("Preco do Filme: "+filme.calcularCusto()+ " Reais");
        System.out.println("Preco da musica: "+musica.calcularCusto()+ " Reais");

        processarPlayer(filme);
        processarPlayer(musica);
    }

    public static void processarPlayer(reproduzivel item) {
        item.darPlay();
    }
}