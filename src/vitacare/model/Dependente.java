package vitacare.model;

import vitacare.enums.TipoCobertura;
import vitacare.exceptions.CoberturaInvalidaException;

import java.time.LocalDate;

/**
 * Dependente de um Titular (cônjuge ou filho até 24 anos).
 * Não existe sem um Titular associado (Regra 2).
 * Mensalidade: 70% da mensalidade base pela faixa etária própria (Regra 5).
 * Não tem acesso à cobertura de internação (Regra 8).
 */
public class Dependente extends Beneficiario {

    private static final double PERCENTUAL_MENSALIDADE = 0.70;

    private final Titular titular;
    private final TipoVinculo tipoVinculo;

    public Dependente(String cpf, String nome, LocalDate dataNascimento,
                      Titular titular, TipoVinculo tipoVinculo) {
        super(cpf, nome, dataNascimento);
        if (titular == null) {
            throw new IllegalArgumentException("Dependente deve estar vinculado a um Titular.");
        }
        if (tipoVinculo == null) {
            throw new IllegalArgumentException("Tipo de vínculo é obrigatório.");
        }
        this.titular = titular;
        this.tipoVinculo = tipoVinculo;
    }

    // ── Mensalidade ────────────────────────────────────────────────────────────

    @Override
    public double calcularMensalidade() {
        return mensalidadeBase() * PERCENTUAL_MENSALIDADE;
    }

    // ── Cobertura ──────────────────────────────────────────────────────────────

    /**
     * Impede que Dependente acione internação (Regra 8).
     */
    @Override
    protected void validarCobertura(TipoCobertura tipo) {
        if (tipo == TipoCobertura.INTERNACAO) {
            throw new CoberturaInvalidaException(
                    "Cobertura de internação não está disponível para dependentes. "
                    + "Beneficiário: " + getNome());
        }
    }

    // ── Getters ────────────────────────────────────────────────────────────────

    public Titular getTitular() {
        return titular;
    }

    public TipoVinculo getTipoVinculo() {
        return tipoVinculo;
    }
}
