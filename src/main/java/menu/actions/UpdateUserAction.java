package menu.actions;

import entity.User;
import menu.MenuAction;
import service.UserService;

import java.util.Scanner;

public class UpdateUserAction implements MenuAction {
    private final UserService service = new UserService();

    @Override
    public void execute() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter user ID to update: ");
        Long id = Long.parseLong(sc.nextLine());

        User user = service.getUser(id);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        System.out.print("Enter new name (" + user.getName() + "): ");
        String name = sc.nextLine();
        if (!name.isBlank()) user.setName(name);

        System.out.print("Enter new email (" + user.getEmail() + "): ");
        String email = sc.nextLine();
        if (!email.isBlank()) user.setEmail(email);

        System.out.print("Enter new age (" + user.getAge() + "): ");
        String ageStr = sc.nextLine();
        if (!ageStr.isBlank()) user.setAge(Integer.parseInt(ageStr));

        service.updateUser(user);
        System.out.println("User updated.");
    }
}
