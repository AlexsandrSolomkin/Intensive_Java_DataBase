package menu.actions;

import entity.User;
import menu.MenuAction;
import service.UserService;

import java.util.List;

public class ListUsersAction implements MenuAction {
    private final UserService service = new UserService();

    @Override
    public void execute() {
        try {
            List<User> users = service.getAllUsers();
            if (users.isEmpty()) {
                System.out.println("No users found in the database.");
                return;
            }
            System.out.println("\n--- All Users ---");
            for (User user : users) {
                System.out.println("ID: " + user.getId() +
                        ", Name: " + user.getName() +
                        ", Email: " + user.getEmail() +
                        ", Age: " + user.getAge() +
                        ", Created at: " + user.getCreatedAt());
            }
        } catch (Exception e) {
            System.out.println("Error fetching users: " + e.getMessage());
        }
    }
}