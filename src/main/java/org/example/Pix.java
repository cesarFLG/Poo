package org.example;

public class Pix implements MeioPagamento {
    @Override
    public void pagar(double valor) {
        System.out.println("Pagando "+ valor + " com Pix");
    }

    @Override
    public String tipoPagamento() {
        return "Pagamento Com Pix";
    }
}
