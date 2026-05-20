package vitacare.vinculo;

public class Filho implements TipoVinculo {

    private static final int IDADE_MAXIMA = 24;

    @Override
    public String getDescricao() {
        return "Filho(a)";
    }

    @Override
    public boolean idadeValida(int idade) {
        return idade <= IDADE_MAXIMA;
    }
}
