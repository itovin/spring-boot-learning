package springbootlearning.ecommerce.menu;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import springbootlearning.ecommerce.entities.Address;
import springbootlearning.ecommerce.entities.User;
import springbootlearning.ecommerce.entities.services.AddressService;
import springbootlearning.ecommerce.entities.services.UserService;
import springbootlearning.ecommerce.exceptions.EmailAddressAlreadyRegisteredException;
import springbootlearning.ecommerce.exceptions.UserDoesNotExistException;
import springbootlearning.ecommerce.exceptions.UsernameAlreadyRegisteredException;

import java.util.Scanner;

@AllArgsConstructor
@Component
public class Menu {
    private final Scanner scanner = new Scanner(System.in);
    private final UserService userService;
    private final AddressService addressService;

    public void mainMenu(){

        while(true) {
            System.out.println("1. Register user\n2. Add address");
            String choice = scanner.nextLine();
            switch(choice){
                case "1" -> registerUser();
                case "2" -> addAddressToExistingUser();
            }
        }
    }

    public User createUser() throws UsernameAlreadyRegisteredException, EmailAddressAlreadyRegisteredException {
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
        return userService.createUser(firstName, lastName, email, username, password);
    }

    public Address createAddress(){
        System.out.print("Enter address1: ");
        String address1 = scanner.nextLine();
        System.out.print("Enter address2: ");
        String address2 = scanner.nextLine();
        System.out.print("Enter City: ");
        String city = scanner.nextLine();
        System.out.print("Enter Province: ");
        String province = scanner.nextLine();
        System.out.print("Enter Zip Code: ");
        String zipCode = scanner.nextLine();
        return userService.createAddress(address1, address2, city, province, zipCode);
    }

    public User getUser() throws UserDoesNotExistException {
        System.out.print("Enter the username or email: ");
        String usernameOrEmail = scanner.nextLine();
        return userService.getUser(usernameOrEmail);
    }

    @Transactional
    public void addAddressToExistingUser(){

        String choice;
        try {
            User user = getUser();
            do{
                Address address = createAddress();
                userService.addUserToAddress(user, address);
                addressService.save(address);
                System.out.println("Address added to the user " + user.getUsername() + " successfully!");
                choice = askAddingAddress();
            }while(choice.equals("1"));

        } catch (UserDoesNotExistException e) {
            System.out.println(e.getMessage());
        }

    }
    public String askAddingAddress(){
        System.out.print("1. Add address\nAnyKey. Exit adding address");
        return scanner.nextLine();
    }
    public void registerUser(){
        try {
            User user = createUser();
            while(true) {
                if (askAddingAddress().equals("1")) {
                    Address address = createAddress();
                    userService.addAddressToUser(user, address);
                }else break;
            }
            userService.save(user);
            System.out.println("User " + user.getUsername() + " saved!");

        } catch (UsernameAlreadyRegisteredException | EmailAddressAlreadyRegisteredException e) {
            System.out.println(e.getMessage());
        }
    }
}
