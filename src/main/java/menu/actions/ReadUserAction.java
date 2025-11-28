package menu.actions;

import entity.User;
import menu.MenuAction;
import service.UserService;
import org.apache.log4j.Logger;

import java.util.Scanner;

/**
 * Действие для чтения информации о пользователе по ID.
 * Проверяет корректность ввода и логирует операции.
 */
public class ReadUserAction implements MenuAction {

    private static final Logger logger = Logger.getLogger(ReadUserAction.class);
    private final UserService service = new UserService();

    @Override
    public void execute() {
        Scanner sc = new Scanner(System.in);
        String idInput = "";  // объявляем заранее, чтобы использовать в catch

        try {
            System.out.print("Enter user ID to read: ");
            idInput = sc.nextLine().trim();

            Long id;
            try {
                id = Long.parseLong(idInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid ID format.");
                logger.warn("Invalid user ID input for reading: " + idInput);
                return;
            }

            User user = service.getUser(id);
            if (user != null) {
                System.out.println("User details:");
                System.out.println("ID: " + user.getId());
                System.out.println("Name: " + user.getName());
                System.out.println("Email: " + user.getEmail());
                System.out.println("Age: " + user.getAge());
                System.out.println("Created at: " + user.getCreatedAt());
                logger.info("Read user successfully: " + user);
            } else {
                System.out.println("User not found.");
                logger.info("User not found for ID: " + id);
            }

        } catch (Exception e) {
            System.out.println("An unexpected error occurred while reading the user.");
            logger.error("Exception during reading user with ID: " + idInput, e);
        }
    }
}