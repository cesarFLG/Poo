package vitacare.cobertura;

public class Exame implements TipoCobertura {
    public String getDescricao() { return "Exame"; }
    public boolean isPermitidaParaDependente() { return true; }
}
