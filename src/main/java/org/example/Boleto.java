package org.example;

public class Boleto implements MeioPagamento {


    @Override
    public void pagar(double valor) {
        System.out.println("Pagando "+ valor + " com Boleto");
    }

    @Override
    public String tipoPagamento() {
        return "Pagamento Com Boleto";
    }
}
