package VitaCare;


import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        System.out.println("Primeiro Teste : cadastrar titular e dependentes");
        Titular ana = new Titular("111.111.111-11", "Ana Paula", LocalDate.of(1985, 6, 20));
        Dependente carlos = new Dependente("222.222.222-22", "Carlos", LocalDate.of(1983, 3, 10), ana, new Conjuge());
        Dependente lucas = new Dependente("333.333.333-33", "Lucas", LocalDate.of(2008, 9, 5), ana, new Filho());

        ana.getDependentes().add(carlos);
        ana.getDependentes().add(lucas);
        ana.exibirResumo();

        System.out.println("Segundo Teste : aposentado com desconto");

        Aposentado roberto = new Aposentado("444.444.444-44", "Roberto", LocalDate.of(1958, 4, 15), true);
        System.out.println("Mensalidade Roberto: R$ " + roberto.getMensalidade());
        roberto.exibirResumo();

        System.out.println("\n--- Teste 3: Dependente com idade invalida ---");

        Titular joao = new Titular("555.555.555-55", "Joao", LocalDate.of(1975, 1, 1));
        Dependente filho_velho = new Dependente("666.666.666-66", "Pedro", LocalDate.of(1998, 5, 5), joao, new Filho());
        joao.adicionarDependente(filho_velho);

        System.out.println("\n--- Teste 4: Limite de dependentes ---");

        Titular maria = new Titular("777.777.777-77", "Maria", LocalDate.of(1980, 3, 3));
        Dependente d1 = new Dependente("001.000.000-01", "Dep 1", LocalDate.of(2005, 1, 1), maria, new Filho());
        Dependente d2 = new Dependente("002.000.000-02", "Dep 2", LocalDate.of(2007, 1, 1), maria, new Filho());
        Dependente d3 = new Dependente("003.000.000-03", "Dep 3", LocalDate.of(2009, 1, 1), maria, new Filho());
        Dependente d4 = new Dependente("004.000.000-04", "Dep 4", LocalDate.of(2011, 1, 1), maria, new Filho());
        maria.adicionarDependente(d1);
        maria.adicionarDependente(d2);
        maria.adicionarDependente(d3);
        maria.adicionarDependente(d4);

        System.out.println("\n--- Teste 5: Coberturas ---");

        ana.acionarCobertura(new Consulta());
        ana.acionarCobertura(new Internacao());
        carlos.acionarCobertura(new Exame());
        carlos.acionarCobertura(new Internacao());

        System.out.println("\nHistorico da Ana:");
        for (AcionarCobertura a : ana.getHistorico()) {
            System.out.println(a);
        }
    }
}