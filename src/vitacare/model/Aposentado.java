package vitacare.model;

import java.time.LocalDate;

/**
 * Aposentado é um Titular que comprovou aposentadoria.
 * Recebe desconto fixo de 20% na mensalidade base (Regra 3).
 * Mantém acesso à cobertura de internação (Regra 8).
 */
public class Aposentado extends Titular {

    private static final double DESCONTO_APOSENTADO = 0.20;

    private final String numeroBeneficioINSS;

    public Aposentado(String cpf, String nome, LocalDate dataNascimento, String numeroBeneficioINSS) {
        super(cpf, nome, dataNascimento);
        if (numeroBeneficioINSS == null || numeroBeneficioINSS.isBlank()) {
            throw new IllegalArgumentException("Número do benefício INSS é obrigatório para Aposentado.");
        }
        this.numeroBeneficioINSS = numeroBeneficioINSS;
    }

    // ── Mensalidade ────────────────────────────────────────────────────────────

    /**
     * Desconto de 20% sobre a mensalidade base (Regra 3).
     */
    @Override
    public double calcularMensalidade() {
        return mensalidadeBase() * (1 - DESCONTO_APOSENTADO);
    }

    // ── Getters ────────────────────────────────────────────────────────────────

    public String getNumeroBoletimINSS() {
        return numeroBeneficioINSS;
    }

    @Override
    protected String getTipoDescricao() {
        return "Aposentado (desconto 20%)";
    }
}
