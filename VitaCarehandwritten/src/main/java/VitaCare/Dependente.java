package VitaCare;
import java.time.LocalDate;

public class Dependente extends Beneficiario {

    private Titular titular;
    private TipoVinculo tipoVinculo;

    public Dependente(String cpf, String nome, LocalDate dataNascimento, Titular titular, TipoVinculo tipoVinculo) {
        super(cpf, nome, LocalDate.parse(String.valueOf(dataNascimento)));
        this.titular = titular;
        this.tipoVinculo = tipoVinculo;
    }

    @Override
    public double getMensalidade() {
        return super.getMensalidade() * 0.7;
    }
    public Titular getTitular() {
        return titular;
    }
    public TipoVinculo getTipoVinculo() {
        return  tipoVinculo;
    }

}

