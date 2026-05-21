package VitaCare;

public class Internacao implements TipoCobertura {

    public String getDescricao(){
        return "Internação";
    }

    public boolean permitidoDependente(){
        return false;
    }
}
