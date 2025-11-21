package menu.actions;

import entity.User;
import menu.MenuAction;
import service.UserService;

import java.util.Scanner;

public class ReadUserAction implements MenuAction {
    private final UserService service = new UserService();

    @Override
    public void execute() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter user ID to read: ");
        Long id = Long.parseLong(sc.nextLine());

        User user = service.getUser(id);
        if (user != null) {
            System.out.println("User details:");
            System.out.println("ID: " + user.getId());
            System.out.println("Name: " + user.getName());
            System.out.println("Email: " + user.getEmail());
            System.out.println("Age: " + user.getAge());
            System.out.println("Created at: " + user.getCreatedAt());
        } else {
            System.out.println("User not found.");
        }
    }
}
