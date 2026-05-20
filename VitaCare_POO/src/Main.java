import vitacare.model.Aposentado;
import vitacare.model.Dependente;
import vitacare.model.Titular;
import vitacare.cobertura.Consulta;
import vitacare.cobertura.Exame;
import vitacare.cobertura.Internacao;
import vitacare.vinculo.Conjuge;
import vitacare.vinculo.Filho;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

        separador("CENÁRIO 1 — Titular com dependentes e resumo de contrato");
        Titular ana = new Titular("111.111.111-11", "Ana Paula Souza", LocalDate.of(1985, 6, 20));
        Dependente carlos = new Dependente("222.222.222-22", "Carlos Souza", LocalDate.of(1983, 3, 10), ana, new Conjuge());
        Dependente lucas = new Dependente("333.333.333-33", "Lucas Souza", LocalDate.of(2008, 9, 5), ana, new Filho());
        Dependente beatriz = new Dependente("444.444.444-44", "Beatriz Souza", LocalDate.of(2012, 12, 1), ana, new Filho());
        ana.adicionarDependente(carlos);
        ana.adicionarDependente(lucas);
        ana.adicionarDependente(beatriz);
        System.out.println(ana.emitirResumoContrato());

        separador("CENÁRIO 2 — Aposentado com desconto de 20%");
        Aposentado roberto = new Aposentado("555.555.555-55", "Roberto Lima", LocalDate.of(1958, 4, 15), "INSS-2024-98765");
        System.out.printf("Mensalidade base (60+) : R$ 560,00%n");
        System.out.printf("Mensalidade com desconto: R$ %.2f%n%n", roberto.calcularMensalidade());
        System.out.println(roberto.emitirResumoContrato());

        separador("CENÁRIO 3 — Faixas etárias");
        Titular menor = new Titular("601.000.000-01", "Sofia Mendes", LocalDate.of(2010, 1, 1));
        Titular adulto = new Titular("602.000.000-02", "Pedro Alves", LocalDate.of(1990, 1, 1));
        Titular idoso = new Titular("603.000.000-03", "Dona Maria", LocalDate.of(1960, 1, 1));
        Dependente depMenor = new Dependente("604.000.000-04", "Joãozinho", LocalDate.of(2012, 5, 10), adulto, new Filho());
        System.out.printf("Titular até 18 anos  (%d anos): R$ %.2f%n", menor.calcularIdade(), menor.calcularMensalidade());
        System.out.printf("Titular 19-59 anos   (%d anos): R$ %.2f%n", adulto.calcularIdade(), adulto.calcularMensalidade());
        System.out.printf("Titular 60+ anos     (%d anos): R$ %.2f%n", idoso.calcularIdade(), idoso.calcularMensalidade());
        System.out.printf("Dependente filho     (%d anos): R$ %.2f%n%n", depMenor.calcularIdade(), depMenor.calcularMensalidade());

        separador("CENÁRIO 4 — Limite de 3 dependentes");
        Titular marcia = new Titular("700.000.000-00", "Márcia Ferreira", LocalDate.of(1980, 3, 3));
        for (int i = 1; i <= 4; i++) {
            Dependente dep = new Dependente(i + "00.000.000-0" + i, "Dependente " + i, LocalDate.of(2005, 1, 1), marcia, new Filho());
            marcia.adicionarDependente(dep);
        }
        System.out.println();

        separador("CENÁRIO 5 — Filho maior de 24 anos");
        Titular fernando = new Titular("800.000.000-00", "Fernando Costa", LocalDate.of(1975, 7, 7));
        Dependente rafael = new Dependente("801.000.000-01", "Rafael Costa", LocalDate.of(1998, 5, 5), fernando, new Filho());
        fernando.adicionarDependente(rafael);
        System.out.println();

        separador("CENÁRIO 6 — Internação negada para dependente");
        Titular juliana = new Titular("900.000.000-00", "Juliana Ramos", LocalDate.of(1978, 11, 22));
        Dependente andre = new Dependente("901.000.000-01", "André Ramos", LocalDate.of(1976, 8, 14), juliana, new Conjuge());
        juliana.adicionarDependente(andre);
        System.out.println("Titular: " + juliana.acionarCobertura(new Internacao()));
        andre.acionarCobertura(new Internacao());
        System.out.println();

        separador("CENÁRIO 7 — Coberturas válidas e histórico");
        Titular camila = new Titular("050.000.000-50", "Camila Duarte", LocalDate.of(1992, 2, 14));
        Dependente gabriel = new Dependente("051.000.000-51", "Gabriel Duarte", LocalDate.of(2015, 7, 20), camila, new Filho());
        camila.adicionarDependente(gabriel);
        System.out.println(camila.acionarCobertura(new Consulta()));
        System.out.println(camila.acionarCobertura(new Internacao()));
        System.out.println(gabriel.acionarCobertura(new Consulta()));
        System.out.println(gabriel.acionarCobertura(new Exame()));
        System.out.println("\nHistórico " + camila.getNome() + ":");
        camila.getHistoricoCoberturas().forEach(a -> System.out.println("  " + a));
        System.out.println("\nHistórico " + gabriel.getNome() + ":");
        gabriel.getHistoricoCoberturas().forEach(a -> System.out.println("  " + a));
        System.out.println();

        separador("CENÁRIO 8 — Resumo contrato Aposentado com dependente");
        Aposentado claudio = new Aposentado("099.099.099-99", "Cláudio Pereira", LocalDate.of(1955, 8, 30), "INSS-2020-11111");
        Dependente tereza = new Dependente("099.100.000-00", "Tereza Pereira", LocalDate.of(1958, 1, 20), claudio, new Conjuge());
        claudio.adicionarDependente(tereza);
        System.out.println(claudio.emitirResumoContrato());
    }

    private static void separador(String titulo) {
        System.out.println("═".repeat(60));
        System.out.println("  " + titulo);
        System.out.println("═".repeat(60));
    }
}
