package springbootlearning.ecommerce.menu;

import org.springframework.stereotype.Component;
import springbootlearning.ecommerce.entities.User;
import springbootlearning.ecommerce.entities.services.UserService;
import springbootlearning.ecommerce.exceptions.EmailAddressAlreadyRegisteredException;
import springbootlearning.ecommerce.exceptions.UsernameAlreadyRegisteredException;

import java.util.Scanner;

@Component
public class Menu {
    private final Scanner scanner = new Scanner(System.in);
    private final UserService userService;

    public Menu(UserService userService){
        this.userService = userService;
    }
    public void mainMenu(){

        while(true) {
            System.out.println("1. Register user");
            int choice = Integer.parseInt((scanner.nextLine()));
            if (choice == 1) {
                System.out.print("Enter your first name: ");
                String firstName = scanner.nextLine();
                System.out.print("Enter your last name: ");
                String lastName = scanner.nextLine();
                System.out.print("Enter your email: ");
                String email = scanner.nextLine();
                System.out.print("Enter your username: ");
                String username = scanner.nextLine();
                System.out.print("Enter your password: ");
                String password = scanner.nextLine();
                registerUser(firstName, lastName, email, username, password);
            }else
                break;
        }
    }

    public void registerUser(String firstName, String lastName, String email,  String username, String password){
        try {
            userService.registerUser(new User(firstName, lastName, email, username, password));
        } catch (EmailAddressAlreadyRegisteredException | UsernameAlreadyRegisteredException e) {
            System.out.println(e.getMessage());
        }
    }
}
