package vitacare.sistema;

import vitacare.cobertura.Consulta;
import vitacare.cobertura.Exame;
import vitacare.cobertura.Internacao;
import vitacare.cobertura.TipoCobertura;
import vitacare.model.*;
import vitacare.vinculo.Conjuge;
import vitacare.vinculo.Filho;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Repositorio {

    private static final String ARQUIVO = "vitacare.txt";
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final List<Titular> titulares = new ArrayList<>();

    public void adicionarTitular(Titular titular) {
        titulares.add(titular);
    }

    public List<Titular> getTitulares() {
        return Collections.unmodifiableList(titulares);
    }

    public Optional<Titular> buscarTitularPorCpf(String cpf) {
        return titulares.stream()
                .filter(t -> t.getCpf().equals(cpf))
                .findFirst();
    }

    public Optional<Beneficiario> buscarBeneficiarioPorCpf(String cpf) {
        for (Titular t : titulares) {
            if (t.getCpf().equals(cpf)) return Optional.of(t);
            Optional<Dependente> dep = t.getDependentes().stream()
                    .filter(d -> d.getCpf().equals(cpf))
                    .findFirst();
            if (dep.isPresent()) return Optional.of(dep.get());
        }
        return Optional.empty();
    }

    public List<Beneficiario> listarTodos() {
        List<Beneficiario> todos = new ArrayList<>();
        for (Titular t : titulares) {
            todos.add(t);
            todos.addAll(t.getDependentes());
        }
        return todos;
    }

    public List<AcionamentoCobertura> listarTodosAcionamentos() {
        List<AcionamentoCobertura> todos = new ArrayList<>();
        for (Beneficiario b : listarTodos()) {
            todos.addAll(b.getHistoricoCoberturas());
        }
        todos.sort((a, b) -> b.getData().compareTo(a.getData()));
        return todos;
    }

    public void salvar() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARQUIVO))) {
            for (Titular t : titulares) {
                if (t instanceof Aposentado ap) {
                    pw.printf("APOSENTADO;%s;%s;%s;%s%n",
                            ap.getCpf(), ap.getNome(),
                            ap.getDataNascimento().format(FMT),
                            ap.getNumeroBeneficioINSS());
                } else {
                    pw.printf("TITULAR;%s;%s;%s%n",
                            t.getCpf(), t.getNome(),
                            t.getDataNascimento().format(FMT));
                }
            }
            for (Titular t : titulares) {
                for (Dependente d : t.getDependentes()) {
                    String vinculo = d.getTipoVinculo() instanceof Conjuge ? "CONJUGE" : "FILHO";
                    pw.printf("DEPENDENTE;%s;%s;%s;%s;%s%n",
                            d.getCpf(), d.getNome(),
                            d.getDataNascimento().format(FMT),
                            t.getCpf(), vinculo);
                }
            }
            for (Beneficiario b : listarTodos()) {
                for (AcionamentoCobertura ac : b.getHistoricoCoberturas()) {
                    pw.printf("COBERTURA;%s;%s;%s%n",
                            ac.getCpfBeneficiario(),
                            ac.getTipo().getDescricao(),
                            ac.getData().format(FMT));
                }
            }
            System.out.println("\n  ✔ Dados salvos em " + ARQUIVO);
        } catch (IOException e) {
            System.out.println("\n  ✘ Erro ao salvar: " + e.getMessage());
        }
    }

    public void carregar() {
        File arquivo = new File(ARQUIVO);
        if (!arquivo.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] p = linha.split(";");
                switch (p[0]) {
                    case "TITULAR" -> titulares.add(
                            new Titular(p[1], p[2], LocalDate.parse(p[3], FMT)));
                    case "APOSENTADO" -> titulares.add(
                            new Aposentado(p[1], p[2], LocalDate.parse(p[3], FMT), p[4]));
                    case "DEPENDENTE" -> buscarTitularPorCpf(p[4]).ifPresent(t -> {
                        var vinculo = p[5].equals("CONJUGE") ? new Conjuge() : new Filho();
                        Dependente dep = new Dependente(p[1], p[2], LocalDate.parse(p[3], FMT), t, vinculo);
                        t.adicionarDependente(dep);
                    });
                    case "COBERTURA" -> buscarBeneficiarioPorCpf(p[1]).ifPresent(b -> {
                        TipoCobertura tipo = switch (p[2]) {
                            case "Exame"      -> new Exame();
                            case "Internação" -> new Internacao();
                            default           -> new Consulta();
                        };
                        b.carregarAcionamento(new AcionamentoCobertura(
                                tipo, b.getNome(), b.getCpf(), LocalDate.parse(p[3], FMT)));
                    });
                }
            }
            System.out.println("  ✔ Dados carregados de " + ARQUIVO);
        } catch (IOException e) {
            System.out.println("  ✘ Erro ao carregar: " + e.getMessage());
        }
    }
}
