package vitacare.model;

import java.time.LocalDate;

public class Aposentado extends Titular {

    private static final double DESCONTO = 0.20;

    private final String numeroBeneficioINSS;

    public Aposentado(String cpf, String nome, LocalDate dataNascimento, String numeroBeneficioINSS) {
        super(cpf, nome, dataNascimento);
        this.numeroBeneficioINSS = numeroBeneficioINSS;
    }

    @Override
    public double calcularMensalidade() {
        return mensalidadeBase() * (1 - DESCONTO);
    }

    public String getNumeroBeneficioINSS() {
        return numeroBeneficioINSS;
    }

    @Override
    protected String getTipoDescricao() {
        return "Aposentado (desconto 20%)";
    }
}
