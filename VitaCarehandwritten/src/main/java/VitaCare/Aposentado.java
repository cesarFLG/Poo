package VitaCare;

import java.time.LocalDate;

public class Aposentado extends Titular {

    private boolean aposentado;

    public Aposentado(String nome, String cpf, LocalDate dataNascimento, Boolean aposentado) {
        super(nome, cpf, dataNascimento);
        this.aposentado = true;
    }

    @Override
    public double getMensalidade() {
        return super.getMensalidade() * 0.8;
    }

    public boolean getaposentado() {
        return aposentado;
    }
}
