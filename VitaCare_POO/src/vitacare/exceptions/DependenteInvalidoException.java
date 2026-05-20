package vitacare.excetion;

public class DependenteInvalidoException extends RuntimeException {
    public DependenteInvalidoException(String mensagem) {
        super(mensagem);
    }
}
