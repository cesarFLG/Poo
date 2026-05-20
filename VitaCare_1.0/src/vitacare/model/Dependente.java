package vitacare.model;

import vitacare.vinculo.TipoVinculo;

import java.time.LocalDate;

public class Dependente extends Beneficiario {

    private static final double PERCENTUAL_MENSALIDADE = 0.70;

    private final Titular titular;
    private final TipoVinculo tipoVinculo;

    public Dependente(String cpf, String nome, LocalDate dataNascimento,
                      Titular titular, TipoVinculo tipoVinculo) {
        super(cpf, nome, dataNascimento);
        this.titular = titular;
        this.tipoVinculo = tipoVinculo;
    }

    public double calcularMensalidade() {
        return mensalidadeBase() * PERCENTUAL_MENSALIDADE;
    }

    public Titular getTitular() { return titular; }
    public TipoVinculo getTipoVinculo() { return tipoVinculo; }
}
