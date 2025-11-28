package menu.actions;

import entity.User;
import menu.MenuAction;
import service.UserService;
import org.apache.log4j.Logger;

import java.util.Scanner;

/**
 * Действие для удаления пользователя по ID.
 * Проверяет корректность ввода и ведет лог операций.
 */
public class DeleteUserAction implements MenuAction {

    private static final Logger logger = Logger.getLogger(DeleteUserAction.class);
    private final UserService service = new UserService();

    @Override
    public void execute() {
        Scanner sc = new Scanner(System.in);
        String idInput = "";

        try {
            System.out.print("Enter user ID to delete: ");
            idInput = sc.nextLine().trim();

            Long id;
            try {
                id = Long.parseLong(idInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid ID format.");
                logger.warn("Invalid user ID input for deletion: " + idInput);
                return;
            }

            // Получение пользователя
            User user = service.getUser(id);
            if (user == null) {
                System.out.println("User not found.");
                logger.info("User not found for deletion with ID: " + id);
                return;
            }

            // Удаление пользователя
            service.deleteUser(user);
            System.out.println("User deleted successfully.");
            logger.info("User deleted: " + user);

        } catch (Exception e) {
            System.out.println("An unexpected error occurred while deleting the user.");
            logger.error("Exception during deleting user with ID: " + idInput, e);
        }
    }
}