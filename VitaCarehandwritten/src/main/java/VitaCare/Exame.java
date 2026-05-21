package VitaCare;

public class Exame implements TipoCobertura {

    public String getDescricao(){
        return "Exame";
    }

    public boolean permitidoDependente() {
        return true;
    }
}
