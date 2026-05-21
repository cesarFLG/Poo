package VitaCare;

public class Consulta implements TipoCobertura {

    public String getDescricao() {
        return "Consulta";
    }

    public boolean permitidoDependente() {
        return true;
    }

}
