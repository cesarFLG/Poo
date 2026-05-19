package vitacare.model;

import java.time.LocalDate;


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

    @Override
    public double calcularMensalidade() {
        return mensalidadeBase() * (1 - DESCONTO_APOSENTADO);
    }


    public String getNumeroBoletimINSS() {
        return numeroBeneficioINSS;
    }

    @Override
    protected String getTipoDescricao() {
        return "Aposentado (desconto 20%)";
    }
}
