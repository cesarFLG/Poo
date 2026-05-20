import vitacare.sistema.Repositorio;
import vitacare.sistema.VitaCareApp;

public class Main {
    public static void main(String[] args) {
        Repositorio repositorio = new Repositorio();
        new VitaCareApp(repositorio).iniciar();
    }
}
