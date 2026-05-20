package vitacare.model;

import vitacare.cobertura.TipoCobertura;
import vitacare.exceptions.CoberturaInvalidaException;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Beneficiario {

    private static final double MENSALIDADE_ATE_18  = 180.00;
    private static final double MENSALIDADE_19_A_59 = 380.00;
    private static final double MENSALIDADE_60_MAIS = 560.00;

    private final String cpf;
    private String nome;
    private final LocalDate dataNascimento;

    private final List<AcionamentoCobertura> historicoCoberturas = new ArrayList<>();

    protected Beneficiario(String cpf, String nome, LocalDate dataNascimento) {
        this.cpf = cpf;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
    }

    protected double mensalidadeBase() {
        int idade = calcularIdade();
        if (idade <= 18) return MENSALIDADE_ATE_18;
        if (idade <= 59) return MENSALIDADE_19_A_59;
        return MENSALIDADE_60_MAIS;
    }

    public abstract double calcularMensalidade();

    public AcionamentoCobertura acionarCobertura(TipoCobertura tipo) {
        try {
            if (!tipo.isPermitidaParaDependente() && this instanceof Dependente) {
                throw new CoberturaInvalidaException(
                        "Cobertura de " + tipo.getDescricao() + " não está disponível para dependentes.");
            }
            AcionamentoCobertura acionamento = new AcionamentoCobertura(tipo, nome, cpf);
            historicoCoberturas.add(acionamento);
            return acionamento;
        } catch (CoberturaInvalidaException e) {
            System.out.println("\n  ✘ " + e.getMessage());
            return null;
        }
    }

    public void carregarAcionamento(AcionamentoCobertura acionamento) {
        historicoCoberturas.add(acionamento);
    }

    public List<AcionamentoCobertura> getHistoricoCoberturas() {
        return Collections.unmodifiableList(historicoCoberturas);
    }

    public int calcularIdade() {
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }

    public String getCpf() { return cpf; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public LocalDate getDataNascimento() { return dataNascimento; }
}
