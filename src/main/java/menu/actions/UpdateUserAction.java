package menu.actions;

import entity.User;
import menu.MenuAction;
import service.UserService;
import org.apache.log4j.Logger;

import java.util.Scanner;

/**
 * Действие для обновления существующего пользователя.
 * Проверяет корректность ввода и ведет лог всех операций.
 */
public class UpdateUserAction implements MenuAction {

    private static final Logger logger = Logger.getLogger(UpdateUserAction.class);
    private final UserService service = new UserService();

    @Override
    public void execute() {
        Scanner sc = new Scanner(System.in);
        String idInput = "";

        try {
            // Ввод ID пользователя
            System.out.print("Enter user ID to update: ");
            idInput = sc.nextLine().trim();

            Long id;
            try {
                id = Long.parseLong(idInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid ID format.");
                logger.warn("Invalid user ID input for update: " + idInput);
                return;
            }

            // Получение пользователя
            User user = service.getUser(id);
            if (user == null) {
                System.out.println("User not found.");
                logger.info("User not found for update with ID: " + id);
                return;
            }

            // Ввод нового имени
            System.out.print("Enter new name (" + user.getName() + "): ");
            String name = sc.nextLine().trim();
            if (!name.isBlank()) {
                user.setName(name);
            } else {
                System.out.println("Name cannot be empty, keeping previous value.");
                logger.info("User name not changed, kept previous: " + user.getName());
            }

            // Ввод нового email
            System.out.print("Enter new email (" + user.getEmail() + "): ");
            String email = sc.nextLine().trim();
            if (!email.isBlank()) {
                user.setEmail(email);
            } else {
                System.out.println("Email cannot be empty, keeping previous value.");
                logger.info("User email not changed, kept previous: " + user.getEmail());
            }

            // Ввод нового возраста
            System.out.print("Enter new age (" + user.getAge() + "): ");
            String ageInput = sc.nextLine().trim();
            if (!ageInput.isBlank()) {
                try {
                    int age = Integer.parseInt(ageInput);
                    if (age < 0) {
                        System.out.println("Age cannot be negative, keeping previous value.");
                        logger.warn("Attempted to update user with negative age: " + age);
                    } else {
                        user.setAge(age);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid age format, keeping previous value.");
                    logger.warn("Invalid age input for update: " + ageInput);
                }
            }

            // Сохранение изменений
            service.updateUser(user);
            System.out.println("User updated successfully.");
            logger.info("User updated: " + user);

        } catch (Exception e) {
            System.out.println("An unexpected error occurred while updating the user.");
            logger.error("Exception during updating user with ID: " + idInput, e);
        }
    }
}