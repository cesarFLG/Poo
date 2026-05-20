package vitacare.cobertura;

public class Internacao implements TipoCobertura {

    @Override
    public String getDescricao() {
        return "Internação";
    }

    @Override
    public boolean isPermitidaParaDependente() {
        return false;
    }
}
