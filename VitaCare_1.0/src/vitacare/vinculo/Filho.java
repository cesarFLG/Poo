package vitacare.vinculo;

public class Filho implements TipoVinculo {
    private static final int IDADE_MAXIMA = 24;
    public String getDescricao() { return "Filho(a)"; }
    public boolean idadeValida(int idade) { return idade <= IDADE_MAXIMA; }
}
