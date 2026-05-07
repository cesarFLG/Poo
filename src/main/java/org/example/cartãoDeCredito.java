package org.example;

public class cartaoDeCredito {

    private String numeroCartao;
    private String nomeTitular;
    private double limiteDisponivel;
    private String CVV;


    public cartaoDeCredito(String numeroCartao, String nomeTitular, double limiteInicial, String CVV) {
        this.numeroCartao = numeroCartao;
        this.nomeTitular = nomeTitular;
        this.limiteDisponivel = limiteInicial;
        this.CVV = CVV;
    }

    public void alterarLimite(double valor) {
        if (valor >= 0) {
            this.limiteDisponivel = valor;
        } else {
            System.out.println("Valor inválido. O limite deve ser um número positivo.");
        }
    }

    // Getters e Setters
    public String getNomeTitular() {
        return nomeTitular;
    }

    public double getLimiteDisponivel() {
        return limiteDisponivel;
    }

    public void setLimiteDisponivel(double novoLimite) {
        this.limiteDisponivel = novoLimite;
    }

    // Getters adicionais para os outros atributos (boa prática)
    public String getNumeroCartao() {
        return numeroCartao;
    }

    public String getCVV() {
        return CVV;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public void setNomeTitular(String nomeTitular) {
        this.nomeTitular = nomeTitular;
    }

    public void setCVV(String CVV) {
        this.CVV = CVV;
    }

}