package org.example;

public class CartaoCredito {

    private String numeroCartao;
    private String titular;
    private double limiteDisponivel;
    private String cvv;

    public CartaoCredito(String numeroCartao, String titular, double limiteInicial, String cvv) {
        this.numeroCartao = numeroCartao;
        this.titular = titular;
        this.limiteDisponivel = limiteInicial;
        this.cvv = cvv;
    }
    
    public void alterarLimite(double valor) {
        if (valor >= 0) {
            this.limiteDisponivel = valor;
        } else {
            System.out.println("Valor inválido. O limite deve ser positivo.");
        }
    }

    public String getNumeroCartao() { return numeroCartao; }
    public String getTitular() { return titular; }
    public double getLimiteDisponivel() { return limiteDisponivel; }
}