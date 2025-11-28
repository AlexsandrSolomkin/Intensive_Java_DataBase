package menu;

import menu.actions.*;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Консольное меню для работы с пользователями.
 * Все действия логируются, ошибки ввода обрабатываются.
 */
public class Menu {

    private static final Logger logger = Logger.getLogger(Menu.class);

    private final List<MenuItem> items = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);

    public Menu() {
        items.add(new MenuItem("Create User", new CreateUserAction()));
        items.add(new MenuItem("Read User", new ReadUserAction()));
        items.add(new MenuItem("List All Users", new ListUsersAction()));
        items.add(new MenuItem("Update User", new UpdateUserAction()));
        items.add(new MenuItem("Delete User", new DeleteUserAction()));
        items.add(new MenuItem("Exit", () -> {
            logger.info("Application exited by user.");
            System.out.println("Exiting...");
            System.exit(0);
        }));
    }

    public void start() {
        while (true) {
            try {
                System.out.println("\n--- User Service Menu ---");
                for (int i = 0; i < items.size(); i++) {
                    System.out.println((i + 1) + ". " + items.get(i).getName());
                }

                System.out.print("Choose an option: ");
                String input = scanner.nextLine().trim();

                int choice;
                try {
                    choice = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number.");
                    logger.warn("Invalid menu choice input: " + input);
                    continue;
                }

                if (choice > 0 && choice <= items.size()) {
                    items.get(choice - 1).getAction().execute();
                } else {
                    System.out.println("Invalid option number.");
                    logger.warn("Menu choice out of range: " + choice);
                }

            } catch (Exception e) {
                System.out.println("An unexpected error occurred in the menu.");
                logger.error("Exception in menu loop", e);
            }
        }
    }

    public Scanner getScanner() {
        return scanner;
    }
}