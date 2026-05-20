package vitacare.cobertura;

public class Consulta implements TipoCobertura {
    public String getDescricao() { return "Consulta"; }
    public boolean isPermitidaParaDependente() { return true; }
}
