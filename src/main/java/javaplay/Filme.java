package javaplay;

public class Filme extends Midia implements Reproduzivel, Baixavel {

    private String qualidade;

    public Filme(String titulo, int duracao, String qualidade) {
        super(titulo, duracao);
        this.qualidade = qualidade;

    }
    @Override
    public void darPlay(){
        System.out.println("Reproduzir Filme :"+ getTitulo() + "em "+ getQualidade());
    }

    @Override
    public void realizarDownload() {
        System.out.println("Baixando filme :"+ getTitulo() + "em "+ getQualidade());
    }

    @Override

    public double calcularCusto() {
        double custo = 10.00;
        if ("4K".equalsIgnoreCase(qualidade)) {
            custo += 5.00;

        }
        return custo;
    }
    @Override
    public void exibirDetalhes(){
        super.exibirDetalhes();
        System.out.println("Qualidade: " +qualidade);
        System.out.println("Custo: "+calcularCusto());
    }
    public String getQualidade() {
        return qualidade;
    }

    public int getDuracaoEmMinutos(){
        return super.getDuracaoEmMinutos();
    }



}
