package vitacare.model;

import vitacare.enums.TipoCobertura;
import vitacare.exceptions.CoberturaInvalidaException;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Beneficiario {


    private static final double MENSALIDADE_ATE_18   = 180.00;
    private static final double MENSALIDADE_19_A_59  = 380.00;
    private static final double MENSALIDADE_60_MAIS  = 560.00;

    private final String cpf;
    private String nome;
    private final LocalDate dataNascimento;

    private final List<AcionamentoCobertura> historicoCoberturas = new ArrayList<>();

    protected Beneficiario(String cpf, String nome, LocalDate dataNascimento) {
        if (cpf == null || cpf.isBlank()) {
            throw new IllegalArgumentException("CPF não pode ser nulo ou vazio.");
        }
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome não pode ser nulo ou vazio.");
        }
        if (dataNascimento == null || dataNascimento.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Data de nascimento inválida.");
        }
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
        validarCobertura(tipo);
        AcionamentoCobertura acionamento = new AcionamentoCobertura(tipo, this.nome);
        historicoCoberturas.add(acionamento);
        return acionamento;
    }

    /**
     * Ponto de extensão: subclasses lançam {@link CoberturaInvalidaException}
     * para tipos de cobertura não permitidos.
     */
    protected void validarCobertura(TipoCobertura tipo) {
        // Por padrão, todos os tipos são permitidos; Dependente restringe INTERNACAO.
    }

    public List<AcionamentoCobertura> getHistoricoCoberturas() {
        return Collections.unmodifiableList(historicoCoberturas);
    }

    // ── Utilitários ────────────────────────────────────────────────────────────

    public int calcularIdade() {
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }

    // ── Getters ────────────────────────────────────────────────────────────────

    public String getCpf() {
        return cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome não pode ser vazio.");
        }
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    @Override
    public String toString() {
        return String.format("%s | CPF: %s | Idade: %d anos | Mensalidade: R$ %.2f",
                nome, cpf, calcularIdade(), calcularMensalidade());
    }
}
