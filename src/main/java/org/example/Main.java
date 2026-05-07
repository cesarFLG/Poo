public class Main {
    public static void main(String[] args) {

        CartaoCredito meuCartao = new CartaoCredito("1234-5678", "cesar", 5000.0, "123");


        ProcessadorPagamento payShield = new ProcessadorPagamento(meuCartao);

    }
}