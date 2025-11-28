import menu.Menu;
import org.apache.log4j.Logger;

/**
 * Точка входа в приложение.
 * Запускает консольное меню.
 */
public class Main {

    private static final Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            logger.info("Application started.");
            Menu menu = new Menu();
            menu.start();
        } catch (Exception e) {
            System.out.println("An unexpected error occurred in the application.");
            logger.error("Fatal exception in Main", e);
        }
    }
}
