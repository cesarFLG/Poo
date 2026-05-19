package vitacare.enums;

public enum TipoCobertura {
    CONSULTA("Consulta"),
    EXAME("Exame"),
    INTERNACAO("Internação");

    private final String descricao;

    TipoCobertura(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
