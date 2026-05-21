package VitaCare;

import java.time.LocalDate;

public class AcionarCobertura {
    private String nomeBeneficiario;
    private TipoCobertura tipo;
    private LocalDate data;

    public  AcionarCobertura(String nomeBeneficiario, TipoCobertura tipo) {
        this.nomeBeneficiario = nomeBeneficiario;
        this.tipo = tipo;
        this.data = LocalDate.now();
    }

    public String toString(){return data + " - " + tipo.getDescricao() + " - " + nomeBeneficiario;}
}
