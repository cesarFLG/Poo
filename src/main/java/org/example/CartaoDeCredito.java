package org.example;

public class CartaoDeCredito implements MeioPagamento {
    @Override
    public void pagar(double valor) {
        System.out.println("Pagando "+ valor + " com Cartao de Credito");

    }

    @Override
    public String tipoPagamento() {
        return "Pagamento com Cartão de Credito";
    }
}
