package menu.actions;

import entity.User;
import menu.MenuAction;
import service.UserService;
import org.apache.log4j.Logger;

import java.util.Scanner;

/**
 * Действие для создания нового пользователя.
 * Проверяет корректность ввода и логирует все операции.
 */
public class CreateUserAction implements MenuAction {

    private static final Logger logger = Logger.getLogger(CreateUserAction.class);
    private final UserService service = new UserService();

    @Override
    public void execute() {
        Scanner sc = new Scanner(System.in);

        try {
            // Ввод имени
            System.out.print("Enter name: ");
            String name = sc.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("Name cannot be empty.");
                logger.warn("Attempted to create user with empty name");
                return;
            }

            // Ввод email
            System.out.print("Enter email: ");
            String email = sc.nextLine().trim();
            if (email.isEmpty()) {
                System.out.println("Email cannot be empty.");
                logger.warn("Attempted to create user with empty email");
                return;
            }

            // Ввод возраста
            System.out.print("Enter age: ");
            String ageInput = sc.nextLine().trim();
            int age;
            try {
                age = Integer.parseInt(ageInput);
                if (age < 0) {
                    System.out.println("Age cannot be negative.");
                    logger.warn("Attempted to create user with negative age: " + age);
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid age format.");
                logger.warn("Invalid age input: " + ageInput);
                return;
            }

            // Создание объекта User через Builder
            User user = new User.Builder()
                    .name(name)
                    .email(email)
                    .age(age)
                    .build();

            // Сохранение пользователя в базе
            service.createUser(user);
            System.out.println("User created with ID: " + user.getId());
            logger.info("User created successfully: " + user);

        } catch (Exception e) {
            System.out.println("An unexpected error occurred while creating the user.");
            logger.error("Exception during user creation", e);
        }
    }
}