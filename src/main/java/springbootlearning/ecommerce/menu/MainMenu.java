package springbootlearning.ecommerce.menu;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@AllArgsConstructor
@Component
public class MainMenu {
    private final Scanner scanner = new Scanner(System.in);
    private final LoginRegistrationMenu accountMenu;
    private final ProductAndCategoryMenu productsMenu;

    public void mainMenu(){

        while(true) {
            System.out.println("1. Register user" +
                    "\n2. Add address" +
                    "\n3. Add product" +
                    "\n4. Add category" +
                    "\n5. View Products");
            String choice = scanner.nextLine();
            switch(choice){
                case "1" -> accountMenu.registerUser();
                case "2" -> accountMenu.addAddressToExistingUser();
                case "3" -> productsMenu.addProduct();
                case "4" -> productsMenu.addCategory();
                case "5" -> productsMenu.viewProducts();
            }
        }
    }
}
