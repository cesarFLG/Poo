import vitacare.enums.TipoCobertura;
import vitacare.exceptions.CoberturaInvalidaException;
import vitacare.exceptions.DependenteInvalidoException;
import vitacare.model.*;

import java.time.LocalDate;

/**
 * Classe principal que valida todos os cenários das regras de negócio da VitaCare.
 */
public class Main {

    public static void main(String[] args) {

        separador("CENÁRIO 1 — Titular comum com dependentes");
        cenario1_TitularComDependentes();

        separador("CENÁRIO 2 — Aposentado com desconto de 20%");
        cenario2_Aposentado();

        separador("CENÁRIO 3 — Faixas etárias e cálculo de mensalidade");
        cenario3_FaixasEtarias();

        separador("CENÁRIO 4 — Limite de 3 dependentes (deve lançar exceção)");
        cenario4_LimiteDependentes();

        separador("CENÁRIO 5 — Filho maior de 24 anos (deve lançar exceção)");
        cenario5_FilhoMaiorDeIdade();

        separador("CENÁRIO 6 — Cobertura de internação para Dependente (deve lançar exceção)");
        cenario6_InternacaoDependente();

        separador("CENÁRIO 7 — Registro de coberturas válidas");
        cenario7_CoberturaValida();

        separador("CENÁRIO 8 — Resumo de contrato do Aposentado");
        cenario8_ResumoAposentado();
    }

    // ──────────────────────────────────────────────────────────────────────────

    private static void cenario1_TitularComDependentes() {
        Titular titular = new Titular("111.111.111-11", "Ana Paula Souza",
                LocalDate.of(1985, 6, 20)); // 39 anos → R$ 380,00

        Dependente conjuge = new Dependente("222.222.222-22", "Carlos Souza",
                LocalDate.of(1983, 3, 10), titular, TipoVinculo.CONJUGE); // 42 anos → 70% de R$380

        Dependente filho1 = new Dependente("333.333.333-33", "Lucas Souza",
                LocalDate.of(2008, 9, 5), titular, TipoVinculo.FILHO);   // 16 anos → 70% de R$180

        Dependente filho2 = new Dependente("444.444.444-44", "Beatriz Souza",
                LocalDate.of(2012, 12, 1), titular, TipoVinculo.FILHO);  // 12 anos → 70% de R$180

        titular.adicionarDependente(conjuge);
        titular.adicionarDependente(filho1);
        titular.adicionarDependente(filho2);

        System.out.println(titular.emitirResumoContrato());
    }

    private static void cenario2_Aposentado() {
        Aposentado aposentado = new Aposentado("555.555.555-55", "Roberto Lima",
                LocalDate.of(1958, 4, 15), "INSS-2024-98765"); // 66 anos → R$560 × 80% = R$448

        System.out.printf("Beneficiário : %s%n", aposentado.getNome());
        System.out.printf("Mensalidade base (60+) : R$ 560,00%n");
        System.out.printf("Desconto aposentado (20%%) : - R$ 112,00%n");
        System.out.printf("Mensalidade final : R$ %.2f%n%n", aposentado.calcularMensalidade());
        System.out.println(aposentado.emitirResumoContrato());
    }

    private static void cenario3_FaixasEtarias() {
        // Criamos beneficiários de cada faixa apenas para demonstrar os valores
        Titular menor = new Titular("601.000.000-01", "Sofia Mendes",
                LocalDate.of(2010, 1, 1));  // 15 anos

        Titular adulto = new Titular("602.000.000-02", "Pedro Alves",
                LocalDate.of(1990, 1, 1));  // 35 anos

        Titular idoso = new Titular("603.000.000-03", "Dona Maria",
                LocalDate.of(1960, 1, 1));  // 65 anos

        Dependente depMenor = new Dependente("604.000.000-04", "Joãozinho",
                LocalDate.of(2012, 5, 10), adulto, TipoVinculo.FILHO); // 13 anos

        System.out.printf("Titular até 18 anos  (%2d anos): R$ %.2f%n",
                menor.calcularIdade(), menor.calcularMensalidade());
        System.out.printf("Titular 19–59 anos   (%2d anos): R$ %.2f%n",
                adulto.calcularIdade(), adulto.calcularMensalidade());
        System.out.printf("Titular 60+ anos     (%2d anos): R$ %.2f%n",
                idoso.calcularIdade(), idoso.calcularMensalidade());
        System.out.printf("Dependente filho     (%2d anos): R$ %.2f  (70%% de R$180,00)%n%n",
                depMenor.calcularIdade(), depMenor.calcularMensalidade());
    }

    private static void cenario4_LimiteDependentes() {
        Titular titular = new Titular("700.000.000-00", "Márcia Ferreira",
                LocalDate.of(1980, 3, 3));

        for (int i = 1; i <= 4; i++) {
            Dependente dep = new Dependente(
                    String.format("%03d.000.000-0%d", i, i),
                    "Dependente " + i,
                    LocalDate.of(2005, 1, 1),
                    titular, TipoVinculo.FILHO);
            try {
                titular.adicionarDependente(dep);
                System.out.printf("✔ Dependente %d adicionado com sucesso.%n", i);
            } catch (DependenteInvalidoException e) {
                System.out.printf("✘ Dependente %d recusado: %s%n", i, e.getMessage());
            }
        }
        System.out.println();
    }

    private static void cenario5_FilhoMaiorDeIdade() {
        Titular titular = new Titular("800.000.000-00", "Fernando Costa",
                LocalDate.of(1975, 7, 7));

        Dependente filhoAdulto = new Dependente("801.000.000-01", "Rafael Costa",
                LocalDate.of(1998, 5, 5), // 26 anos
                titular, TipoVinculo.FILHO);

        try {
            titular.adicionarDependente(filhoAdulto);
            System.out.println("✔ Dependente adicionado.");
        } catch (DependenteInvalidoException e) {
            System.out.printf("✘ Inclusão recusada: %s%n%n", e.getMessage());
        }
    }

    private static void cenario6_InternacaoDependente() {
        Titular titular = new Titular("900.000.000-00", "Juliana Ramos",
                LocalDate.of(1978, 11, 22));
        Dependente conjuge = new Dependente("901.000.000-01", "André Ramos",
                LocalDate.of(1976, 8, 14), titular, TipoVinculo.CONJUGE);
        titular.adicionarDependente(conjuge);

        // Titular pode acionar internação
        try {
            var ev = titular.acionarCobertura(TipoCobertura.INTERNACAO);
            System.out.println("✔ Titular: " + ev);
        } catch (CoberturaInvalidaException e) {
            System.out.println("✘ " + e.getMessage());
        }

        // Dependente NÃO pode acionar internação
        try {
            var ev = conjuge.acionarCobertura(TipoCobertura.INTERNACAO);
            System.out.println("✔ Dependente: " + ev);
        } catch (CoberturaInvalidaException e) {
            System.out.printf("✘ Internação negada para dependente: %s%n%n", e.getMessage());
        }
    }

    private static void cenario7_CoberturaValida() {
        Titular titular = new Titular("050.000.000-50", "Camila Duarte",
                LocalDate.of(1992, 2, 14));
        Dependente filho = new Dependente("051.000.000-51", "Gabriel Duarte",
                LocalDate.of(2015, 7, 20), titular, TipoVinculo.FILHO);
        titular.adicionarDependente(filho);

        // Coberturas permitidas
        System.out.println(titular.acionarCobertura(TipoCobertura.CONSULTA));
        System.out.println(titular.acionarCobertura(TipoCobertura.INTERNACAO));
        System.out.println(filho.acionarCobertura(TipoCobertura.CONSULTA));
        System.out.println(filho.acionarCobertura(TipoCobertura.EXAME));

        System.out.println("\nHistórico do titular (" + titular.getNome() + "):");
        titular.getHistoricoCoberturas().forEach(a -> System.out.println("  " + a));
        System.out.println("\nHistórico do dependente (" + filho.getNome() + "):");
        filho.getHistoricoCoberturas().forEach(a -> System.out.println("  " + a));
        System.out.println();
    }

    private static void cenario8_ResumoAposentado() {
        Aposentado apo = new Aposentado("099.099.099-99", "Cláudio Pereira",
                LocalDate.of(1955, 8, 30), "INSS-2020-11111"); // 69 anos → R$560 × 80% = R$448

        Dependente esposa = new Dependente("099.100.000-00", "Tereza Pereira",
                LocalDate.of(1958, 1, 20), apo, TipoVinculo.CONJUGE); // 66 anos → 70% de R$560

        apo.adicionarDependente(esposa);
        System.out.println(apo.emitirResumoContrato());
    }


    private static void separador(String titulo) {
        System.out.println("═".repeat(60));
        System.out.println("  " + titulo);
        System.out.println("═".repeat(60));
    }
}
