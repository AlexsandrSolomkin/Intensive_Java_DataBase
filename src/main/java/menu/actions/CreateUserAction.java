package menu.actions;

import entity.User;
import menu.MenuAction;
import service.UserService;

import java.util.Scanner;

public class CreateUserAction implements MenuAction {
    private final UserService service = new UserService();

    @Override
    public void execute() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter name: ");
        String name = sc.nextLine();
        System.out.print("Enter email: ");
        String email = sc.nextLine();
        System.out.print("Enter age: ");
        int age = Integer.parseInt(sc.nextLine());

        User user = new User.Builder()
                .name(name)
                .email(email)
                .age(age)
                .build();

        service.createUser(user);
        System.out.println("User created with ID: " + user.getId());
    }
}
