package vitacare.cobertura;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AcionamentoCobertura {

    private static final DateTimeFormatter FORMATADOR = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final LocalDate data;
    private final TipoCobertura tipo;
    private final String nomeBeneficiario;

    public AcionamentoCobertura(TipoCobertura tipo, String nomeBeneficiario) {
        this.data = LocalDate.now();
        this.tipo = tipo;
        this.nomeBeneficiario = nomeBeneficiario;
    }

    public LocalDate getData() {
        return data;
    }

    public TipoCobertura getTipo() {
        return tipo;
    }

    public String getNomeBeneficiario() {
        return nomeBeneficiario;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s acionado por: %s",
                data.format(FORMATADOR), tipo.getDescricao(), nomeBeneficiario);
    }
}
