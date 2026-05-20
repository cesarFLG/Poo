package vitacare.cobertura;

public class Exame implements TipoCobertura {

    @Override
    public String getDescricao() {
        return "Exame";
    }

    @Override
    public boolean isPermitidaParaDependente() {
        return true;
    }
}
