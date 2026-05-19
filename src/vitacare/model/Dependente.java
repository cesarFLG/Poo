package vitacare.model;

import vitacare.enums.TipoCobertura;
import vitacare.exceptions.CoberturaInvalidaException;

import java.time.LocalDate;


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


    @Override
    public double calcularMensalidade() {
        return mensalidadeBase() * PERCENTUAL_MENSALIDADE;
    }

    @Override
    protected void validarCobertura(TipoCobertura tipo) {
        if (tipo == TipoCobertura.INTERNACAO) {
            throw new CoberturaInvalidaException(
                    "Cobertura de internação não está disponível para dependentes. "
                    + "Beneficiário: " + getNome());
        }
    }


    public Titular getTitular() {
        return titular;
    }

    public TipoVinculo getTipoVinculo() {
        return tipoVinculo;
    }
}
