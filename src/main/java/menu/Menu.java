package menu;

import menu.actions.CreateUserAction;
import menu.actions.DeleteUserAction;
import menu.actions.ReadUserAction;
import menu.actions.UpdateUserAction;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private final List<MenuItem> items = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);

    public Menu() {
        items.add(new MenuItem("Create User", new CreateUserAction()));
        items.add(new MenuItem("Read User", new ReadUserAction()));
        items.add(new MenuItem("Update User", new UpdateUserAction()));
        items.add(new MenuItem("Delete User", new DeleteUserAction()));
        items.add(new MenuItem("Exit", () -> System.exit(0)));
    }

    public void start() {
        while (true) {
            System.out.println("\n--- User Service Menu ---");
            for (int i = 0; i < items.size(); i++) {
                System.out.println((i + 1) + ". " + items.get(i).getName());
            }
            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(scanner.nextLine());
            if (choice > 0 && choice <= items.size()) {
                items.get(choice - 1).getAction().execute();
            } else {
                System.out.println("Invalid option.");
            }
        }
    }

    public Scanner getScanner() { return scanner; }
}