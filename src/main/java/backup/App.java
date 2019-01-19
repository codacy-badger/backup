package backup;

public class App {

    static Configurator configurator = new FileConfigurator();

    public static void main(String[] args) {
        try {
            configurator.updateConfiguration();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Stopping now");
        }
    }
}
