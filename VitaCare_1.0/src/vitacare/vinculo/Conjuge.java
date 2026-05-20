package vitacare.vinculo;

public class Conjuge implements TipoVinculo {
    public String getDescricao() { return "Cônjuge"; }
    public boolean idadeValida(int idade) { return true; }
}
