package org.example;

public class ProcessadorPagamento {

    private CartaoCredito cartao;


    public ProcessadorPagamento(CartaoCredito cartao) {
        this.cartao = cartao;
    }

    public void executarVenda(double valor) {
        if (valor <= cartao.getLimiteDisponivel()) {

            cartao.alterarLimite(cartao.getLimiteDisponivel() - valor);
            System.out.println("Venda de R$ " + valor + " aprovada para: " + cartao.getTitular());
            System.out.println("Limite restante: R$ " + cartao.getLimiteDisponivel());
        } else {
            System.out.println("Transação Negada: Saldo insuficiente para R$ " + valor);
        }
    }

    public void estornarVenda(double valor) {
        cartao.alterarLimite(cartao.getLimiteDisponivel() + valor);
        System.out.println("Estorno de R$ " + valor + " realizado com sucesso.");
        System.out.println("Limite restante: R$ " + cartao.getLimiteDisponivel());
    }
}
