package VitaCare;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public abstract class Beneficiario {
    private String nome;
    private  String cpf;
    private final LocalDate dataNascimento;
    private List<AcionarCobertura> historico = new ArrayList<>();

    public Beneficiario(String nome, String cpf, LocalDate dataNascimento) {
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
    }

    public int getIdade() {return Period.between(this.dataNascimento, LocalDate.now()).getYears();}

    public double getMensalidadeBase(){
        if(getIdade() <= 18) return (int) 180.00;
        if(getIdade() <= 59) return (int) 380.00;
        return (int) 560.00;
    }

    public double getMensalidade() {
        return getMensalidadeBase();
    }

    public void acionarCobertura(TipoCobertura tipo) {
        if (!tipo.permitidoDependente() && this instanceof Dependente) {
            System.out.println("Erro: Dependente nao pode acionar " + tipo.getDescricao());
            return;
        }
        AcionarCobertura acionamento = new AcionarCobertura(nome, tipo);
        historico.add(acionamento);
        System.out.println("Cobertura registrada: " + acionamento);
    }

    public List<AcionarCobertura> getHistorico() {
        return historico;
    }

    public String getCpf() {
        return cpf;
    }
    public LocalDate getDataNascimento() {
        return dataNascimento;
    }
    public String getNome() {
        return nome;
    }
}
