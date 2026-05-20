package vitacare.vinculo;

public class Conjuge implements TipoVinculo {

    @Override
    public String getDescricao() {
        return "Cônjuge";
    }

    @Override
    public boolean idadeValida(int idade) {
        return true;
    }
}
