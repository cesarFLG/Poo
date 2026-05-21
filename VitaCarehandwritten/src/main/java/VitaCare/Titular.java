package VitaCare;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Titular extends Beneficiario {
    private List<Dependente> dependentes = new ArrayList<>();

    public Titular(String nome, String cpf, LocalDate dataNascimento) {
        super(nome, cpf, LocalDate.parse(String.valueOf(dataNascimento)));
    }

    @Override
    public double getMensalidade() {
        return super.getMensalidade();
    }

    public void adicionarDependente(Dependente dependente) {
        if (dependentes.size() >=3) {
            System.out.println("Erro! Titular ja tem 3 dependentes");
        return;
        }if(!dependente.getTipoVinculo().idadeValida(dependente.getIdade())){
            System.out.println("Erro! " + dependente.getNome() + " nao pode ser dependente, idade invalida");
            return;

        }dependentes.add(dependente);
        System.out.println(dependente.getNome() + " adicionado como dependente de " + getNome());
    }
    public List<Dependente> getDependentes() {
        return dependentes;
    }

    public void exibirResumo() {
        System.out.println("=== Resumo do Contrato ===");
        System.out.println("Titular: " + getNome() + " | CPF: " + getCpf());
        System.out.println("Mensalidade titular: R$ " + getMensalidade());

        for (Dependente d : dependentes) {
            System.out.println("Dependente: " + d.getNome() + " | Vinculo: " + d.getTipoVinculo().getDescricao() + " | Mensalidade: R$ " + d.getMensalidade());
        }

        double total = getMensalidade();
        for (Dependente d : dependentes) {
            total += d.getMensalidade();
        }
        System.out.println("Total mensal: R$ " + total);
        System.out.println("=========================");
    }
}
