package org.example;

public class ProcessadorPagamento {
    private CartaoCredito cartao;


    public ProcessadorPagamento(CartaoCredito cartao) {
        this.cartao = cartao;
    }

    public void executarVenda(double valor) {
        if (valor <= cartao.getLimiteDisponivel()) {
            double novoLimite = cartao.getLimiteDisponivel() - valor;
            cartao.setLimiteDisponivel(novoLimite);
            System.out.println("Venda de R$ " + valor + " aprovada para: " + cartao.getTitular());
        } else {
            System.out.println("Transação Negada: Saldo insuficiente para o valor de R$ " + valor);
        }
    }

    public void estornarVenda(double valor) {
        double novoLimite = cartao.getLimiteDisponivel() + valor;
        cartao.setLimiteDisponivel(novoLimite);
        System.out.println("Estorno de R$ " + valor + " realizado com sucesso.");
    }
}