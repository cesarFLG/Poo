package org.example;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<MeioPagamento> pagamentos = new ArrayList<>();


        pagamentos.add(new CartaoDeCredito());
        pagamentos.add(new Pix());
        pagamentos.add(new Boleto());


        for (MeioPagamento p : pagamentos) {
            p.pagar(100.0);
        }
    }
}