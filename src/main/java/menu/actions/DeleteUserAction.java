package menu.actions;

import entity.User;
import menu.MenuAction;
import service.UserService;

import java.util.Scanner;

public class DeleteUserAction implements MenuAction {
    private final UserService service = new UserService();

    @Override
    public void execute() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter user ID to delete: ");
        Long id = Long.parseLong(sc.nextLine());

        User user = service.getUser(id);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        service.deleteUser(user);
        System.out.println("User deleted.");
    }
}
