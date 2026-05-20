package vitacare.sistema;

import vitacare.cobertura.Consulta;
import vitacare.cobertura.Exame;
import vitacare.cobertura.Internacao;
import vitacare.cobertura.TipoCobertura;
import vitacare.model.*;
import vitacare.vinculo.Conjuge;
import vitacare.vinculo.Filho;
import vitacare.vinculo.TipoVinculo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class VitaCareApp {

    private static final DateTimeFormatter FMT_EXIB = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter FMT_ENTRADA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final Repositorio repositorio;
    private final Scanner scanner;

    public VitaCareApp(Repositorio repositorio) {
        this.repositorio = repositorio;
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        limpar();
        System.out.println(cabecalho());
        repositorio.carregar();

        boolean rodando = true;
        while (rodando) {
            exibirMenuPrincipal();
            String opcao = scanner.nextLine().trim();
            switch (opcao) {
                case "1" -> menuCadastros();
                case "2" -> menuConsultas();
                case "3" -> repositorio.salvar();
                case "0" -> {
                    repositorio.salvar();
                    System.out.println("\n  Até logo!\n");
                    rodando = false;
                }
                default -> System.out.println("\n  Opção inválida.");
            }
        }
    }

    private void exibirMenuPrincipal() {
        System.out.println("""

  ╔════════════════════════════════╗
  ║        MENU PRINCIPAL          ║
  ╠════════════════════════════════╣
  ║  1. Cadastros                  ║
  ║  2. Consultas                  ║
  ║  3. Salvar dados               ║
  ║  0. Sair                       ║
  ╚════════════════════════════════╝
  Escolha: """);
    }

    private void menuCadastros() {
        boolean voltar = false;
        while (!voltar) {
            System.out.println("""

  ╔════════════════════════════════╗
  ║          CADASTROS             ║
  ╠════════════════════════════════╣
  ║  1. Cadastrar Titular          ║
  ║  2. Cadastrar Aposentado       ║
  ║  3. Cadastrar Dependente       ║
  ║  4. Registrar cobertura        ║
  ║  0. Voltar                     ║
  ╚════════════════════════════════╝
  Escolha: """);
            switch (scanner.nextLine().trim()) {
                case "1" -> cadastrarTitular();
                case "2" -> cadastrarAposentado();
                case "3" -> cadastrarDependente();
                case "4" -> registrarCobertura();
                case "0" -> voltar = true;
                default  -> System.out.println("\n  Opção inválida.");
            }
        }
    }

    private void menuConsultas() {
        boolean voltar = false;
        while (!voltar) {
            System.out.println("""

  ╔════════════════════════════════╗
  ║          CONSULTAS             ║
  ╠════════════════════════════════╣
  ║  1. Listar beneficiários       ║
  ║  2. Resumo de contrato         ║
  ║  3. Coberturas de um titular   ║
  ║  4. Histórico geral            ║
  ║  0. Voltar                     ║
  ╚════════════════════════════════╝
  Escolha: """);
            switch (scanner.nextLine().trim()) {
                case "1" -> listarBeneficiarios();
                case "2" -> exibirResumoContrato();
                case "3" -> consultarCoberturasDoTitular();
                case "4" -> exibirHistoricoGeral();
                case "0" -> voltar = true;
                default  -> System.out.println("\n  Opção inválida.");
            }
        }
    }

    private void cadastrarTitular() {
        System.out.println("\n  — CADASTRAR TITULAR —");
        String cpf  = lerCampo("  CPF");
        if (cpfJaExiste(cpf)) return;
        String nome = lerCampo("  Nome");
        LocalDate nasc = lerData("  Data de nascimento (dd/mm/aaaa)");
        if (nasc == null) return;

        repositorio.adicionarTitular(new Titular(cpf, nome, nasc));
        System.out.println("\n  ✔ Titular " + nome + " cadastrado.");
    }

    private void cadastrarAposentado() {
        System.out.println("\n  — CADASTRAR APOSENTADO —");
        String cpf   = lerCampo("  CPF");
        if (cpfJaExiste(cpf)) return;
        String nome  = lerCampo("  Nome");
        LocalDate nasc = lerData("  Data de nascimento (dd/mm/aaaa)");
        if (nasc == null) return;
        String inss  = lerCampo("  Número do benefício INSS");

        repositorio.adicionarTitular(new Aposentado(cpf, nome, nasc, inss));
        System.out.println("\n  ✔ Aposentado " + nome + " cadastrado.");
    }

    private void cadastrarDependente() {
        System.out.println("\n  — CADASTRAR DEPENDENTE —");

        if (repositorio.getTitulares().isEmpty()) {
            System.out.println("\n  Nenhum titular cadastrado.");
            return;
        }

        System.out.println("\n  Titulares disponíveis:");
        repositorio.getTitulares().forEach(t ->
                System.out.printf("    %-30s CPF: %s%n", t.getNome(), t.getCpf()));

        String cpfTitular = lerCampo("\n  CPF do titular");
        Optional<Titular> optTitular = repositorio.buscarTitularPorCpf(cpfTitular);
        if (optTitular.isEmpty()) {
            System.out.println("\n  ✘ Titular não encontrado.");
            return;
        }
        Titular titular = optTitular.get();

        String cpf  = lerCampo("  CPF do dependente");
        if (cpfJaExiste(cpf)) return;
        String nome = lerCampo("  Nome");
        LocalDate nasc = lerData("  Data de nascimento (dd/mm/aaaa)");
        if (nasc == null) return;

        TipoVinculo vinculo = lerVinculo();
        if (vinculo == null) return;

        Dependente dep = new Dependente(cpf, nome, nasc, titular, vinculo);
        titular.adicionarDependente(dep);

        if (titular.getDependentes().contains(dep)) {
            System.out.println("\n  ✔ Dependente " + nome + " vinculado a " + titular.getNome() + ".");
        }
    }

    private void registrarCobertura() {
        System.out.println("\n  — REGISTRAR COBERTURA —");
        String cpf = lerCampo("  CPF do beneficiário");
        Optional<Beneficiario> opt = repositorio.buscarBeneficiarioPorCpf(cpf);
        if (opt.isEmpty()) {
            System.out.println("\n  ✘ Beneficiário não encontrado.");
            return;
        }
        Beneficiario b = opt.get();

        System.out.println("""

    Tipo de cobertura:
      1. Consulta
      2. Exame
      3. Internação
  Escolha: """);
        TipoCobertura tipo = switch (scanner.nextLine().trim()) {
            case "2" -> new Exame();
            case "3" -> new Internacao();
            default  -> new Consulta();
        };

        AcionamentoCobertura ac = b.acionarCobertura(tipo);
        if (ac != null) {
            System.out.println("\n  ✔ " + ac);
        }
    }

    private void listarBeneficiarios() {
        List<Beneficiario> todos = repositorio.listarTodos();
        if (todos.isEmpty()) {
            System.out.println("\n  Nenhum beneficiário cadastrado.");
            return;
        }
        System.out.println("\n  — BENEFICIÁRIOS CADASTRADOS —\n");
        for (Titular t : repositorio.getTitulares()) {
            String tipo = t instanceof Aposentado ? "[Aposentado]" : "[Titular]  ";
            System.out.printf("  %s %-30s CPF: %-15s Idade: %d  R$ %.2f/mês%n",
                    tipo, t.getNome(), t.getCpf(), t.calcularIdade(), t.calcularMensalidade());
            for (Dependente d : t.getDependentes()) {
                System.out.printf("    └ [Dependente/%s] %-24s CPF: %-15s Idade: %d  R$ %.2f/mês%n",
                        d.getTipoVinculo().getDescricao(), d.getNome(),
                        d.getCpf(), d.calcularIdade(), d.calcularMensalidade());
            }
        }
    }

    private void exibirResumoContrato() {
        System.out.println("\n  — RESUMO DE CONTRATO —");
        String cpf = lerCampo("  CPF do titular");
        repositorio.buscarTitularPorCpf(cpf).ifPresentOrElse(
                t -> System.out.println("\n" + t.emitirResumoContrato()),
                () -> System.out.println("\n  ✘ Titular não encontrado.")
        );
    }

    private void consultarCoberturasDoTitular() {
        System.out.println("\n  — COBERTURAS POR TITULAR —");
        String cpf = lerCampo("  CPF do titular");
        Optional<Titular> opt = repositorio.buscarTitularPorCpf(cpf);
        if (opt.isEmpty()) {
            System.out.println("\n  ✘ Titular não encontrado.");
            return;
        }
        Titular t = opt.get();
        System.out.println("\n  Titular: " + t.getNome());

        List<Beneficiario> grupo = new java.util.ArrayList<>();
        grupo.add(t);
        grupo.addAll(t.getDependentes());

        boolean encontrou = false;
        for (Beneficiario b : grupo) {
            List<AcionamentoCobertura> acs = b.getHistoricoCoberturas();
            if (!acs.isEmpty()) {
                System.out.println("\n  " + b.getNome() + ":");
                acs.forEach(ac -> System.out.println("    " + ac));
                encontrou = true;
            }
        }
        if (!encontrou) System.out.println("\n  Nenhuma cobertura registrada para este grupo.");
    }

    private void exibirHistoricoGeral() {
        List<AcionamentoCobertura> todos = repositorio.listarTodosAcionamentos();
        if (todos.isEmpty()) {
            System.out.println("\n  Nenhuma cobertura registrada.");
            return;
        }
        System.out.println("\n  — HISTÓRICO GERAL DE COBERTURAS —\n");
        todos.forEach(ac -> System.out.println("  " + ac));
    }

    private String lerCampo(String label) {
        System.out.print(label + ": ");
        return scanner.nextLine().trim();
    }

    private LocalDate lerData(String label) {
        System.out.print(label + ": ");
        try {
            return LocalDate.parse(scanner.nextLine().trim(), FMT_ENTRADA);
        } catch (DateTimeParseException e) {
            System.out.println("\n  ✘ Data inválida. Use o formato dd/mm/aaaa.");
            return null;
        }
    }

    private TipoVinculo lerVinculo() {
        System.out.println("""

    Vínculo:
      1. Cônjuge
      2. Filho(a)
  Escolha: """);
        return switch (scanner.nextLine().trim()) {
            case "1" -> new Conjuge();
            case "2" -> new Filho();
            default  -> { System.out.println("\n  ✘ Vínculo inválido."); yield null; }
        };
    }

    private boolean cpfJaExiste(String cpf) {
        if (repositorio.buscarBeneficiarioPorCpf(cpf).isPresent()) {
            System.out.println("\n  ✘ CPF " + cpf + " já cadastrado.");
            return true;
        }
        return false;
    }

    private String cabecalho() {
        return """
╔══════════════════════════════════════════╗
║        VitaCare 1.0 — Sistema de         ║
║          Planos de Saúde                 ║
╚══════════════════════════════════════════╝
""";
    }

    private void limpar() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
