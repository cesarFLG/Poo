package org.example;

public class Main {
    public static void main(String[] args) {


        CartaoCredito meuCartao = new CartaoCredito("1234-5678", "Cesar", 5000.0, "123");


        ProcessadorPagamento payShield = new ProcessadorPagamento(meuCartao);


        payShield.executarVenda(2000.0);

        payShield.executarVenda(4000.0);
    }
}