package vitacare.cobertura;

public class Consulta implements TipoCobertura {

    @Override
    public String getDescricao() {
        return "Consulta";
    }

    @Override
    public boolean isPermitidaParaDependente() {
        return true;
    }
}
