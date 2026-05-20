package vitacare.model;

import vitacare.cobertura.TipoCobertura;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AcionamentoCobertura {

    static final DateTimeFormatter FORMATADOR = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final LocalDate data;
    private final TipoCobertura tipo;
    private final String nomeBeneficiario;
    private final String cpfBeneficiario;

    public AcionamentoCobertura(TipoCobertura tipo, String nomeBeneficiario, String cpfBeneficiario) {
        this.data = LocalDate.now();
        this.tipo = tipo;
        this.nomeBeneficiario = nomeBeneficiario;
        this.cpfBeneficiario = cpfBeneficiario;
    }

    public AcionamentoCobertura(TipoCobertura tipo, String nomeBeneficiario, String cpfBeneficiario, LocalDate data) {
        this.data = data;
        this.tipo = tipo;
        this.nomeBeneficiario = nomeBeneficiario;
        this.cpfBeneficiario = cpfBeneficiario;
    }

    public LocalDate getData() { return data; }
    public TipoCobertura getTipo() { return tipo; }
    public String getNomeBeneficiario() { return nomeBeneficiario; }
    public String getCpfBeneficiario() { return cpfBeneficiario; }

    public String toString() {
        return String.format("[%s] %-12s → %s (%s)",
                data.format(FORMATADOR), tipo.getDescricao(), nomeBeneficiario, cpfBeneficiario);
    }
}
