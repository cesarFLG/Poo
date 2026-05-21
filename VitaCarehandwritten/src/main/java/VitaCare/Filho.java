package VitaCare;

public class Filho implements TipoVinculo {

    public String getDescricao() {
        return "Filho";
    }

    public boolean idadeValida (int idade) {
        return idade <= 21;
    }
}
