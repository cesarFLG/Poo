package vitacare.model;

public enum TipoVinculo {
    CONJUGE("Cônjuge"),
    FILHO("Filho(a)");

    private final String descricao;

    TipoVinculo(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
