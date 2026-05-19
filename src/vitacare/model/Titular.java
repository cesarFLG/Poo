package vitacare.model;

import vitacare.exceptions.DependenteInvalidoException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Titular extends Beneficiario {

    private static final int LIMITE_DEPENDENTES = 3;
    private static final int IDADE_MAXIMA_FILHO  = 24;
    private static final DateTimeFormatter FORMATADOR = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final List<Dependente> dependentes = new ArrayList<>();

    public Titular(String cpf, String nome, LocalDate dataNascimento) {
        super(cpf, nome, dataNascimento);
    }


    @Override
    public double calcularMensalidade() {
        return mensalidadeBase();
    }


    public void adicionarDependente(Dependente dependente) {
        if (dependentes.size() >= LIMITE_DEPENDENTES) {
            throw new DependenteInvalidoException(
                    "Limite de " + LIMITE_DEPENDENTES + " dependentes já atingido para o titular " + getNome() + ".");
        }
        if (dependente.getTipoVinculo() == TipoVinculo.FILHO
                && dependente.calcularIdade() > IDADE_MAXIMA_FILHO) {
            throw new DependenteInvalidoException(
                    "Filho(a) " + dependente.getNome() + " tem " + dependente.calcularIdade()
                    + " anos e excede a idade máxima permitida de " + IDADE_MAXIMA_FILHO + " anos.");
        }
        dependentes.add(dependente);
    }

    public void removerDependente(Dependente dependente) {
        dependentes.remove(dependente);
    }

    public List<Dependente> getDependentes() {
        return Collections.unmodifiableList(dependentes);
    }


    public String emitirResumoContrato() {
        StringBuilder sb = new StringBuilder();
        sb.append("╔══════════════════════════════════════════════════════╗\n");
        sb.append("║            RESUMO DE CONTRATO — VitaCare             ║\n");
        sb.append("╠══════════════════════════════════════════════════════╣\n");
        sb.append(String.format("║  TITULAR : %-40s║\n", getNome()));
        sb.append(String.format("║  CPF     : %-40s║\n", getCpf()));
        sb.append(String.format("║  Idade   : %-40s║\n", calcularIdade() + " anos"));
        sb.append(String.format("║  Tipo    : %-40s║\n", getTipoDescricao()));
        sb.append(String.format("║  Mensalidade titular : R$ %-26s║\n",
                String.format("%.2f", calcularMensalidade())));
        sb.append("╠══════════════════════════════════════════════════════╣\n");

        double totalDependentes = 0;
        if (dependentes.isEmpty()) {
            sb.append("║  Sem dependentes cadastrados.                        ║\n");
        } else {
            sb.append(String.format("║  %-51s║\n", "DEPENDENTES (" + dependentes.size() + ")"));
            for (int i = 0; i < dependentes.size(); i++) {
                Dependente d = dependentes.get(i);
                sb.append(String.format("║  %d. %-48s║\n", i + 1, d.getNome()));
                sb.append(String.format("║     Vínculo : %-37s║\n", d.getTipoVinculo().getDescricao()));
                sb.append(String.format("║     Idade   : %-37s║\n", d.calcularIdade() + " anos"));
                sb.append(String.format("║     Mensalidade : R$ %-31s║\n",
                        String.format("%.2f", d.calcularMensalidade())));
                totalDependentes += d.calcularMensalidade();
            }
        }

        double totalFamiliar = calcularMensalidade() + totalDependentes;
        sb.append("╠══════════════════════════════════════════════════════╣\n");
        sb.append(String.format("║  TOTAL MENSAL DO GRUPO FAMILIAR: R$ %-16s║\n",
                String.format("%.2f", totalFamiliar)));
        sb.append("╚══════════════════════════════════════════════════════╝\n");
        return sb.toString();
    }

    protected String getTipoDescricao() {
        return "Titular";
    }
}
