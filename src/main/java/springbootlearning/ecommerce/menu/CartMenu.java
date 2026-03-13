package springbootlearning.ecommerce.menu;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import springbootlearning.ecommerce.entities.Cart;
import springbootlearning.ecommerce.entities.Product;
import springbootlearning.ecommerce.entities.User;
import springbootlearning.ecommerce.exceptions.ProductNotFoundException;
import springbootlearning.ecommerce.exceptions.UserDoesNotExistException;
import springbootlearning.ecommerce.services.CartService;
import springbootlearning.ecommerce.services.ProductService;
import springbootlearning.ecommerce.services.UserService;

import java.util.Scanner;

@AllArgsConstructor
@Component
public class CartMenu {
    private final Scanner scanner = new Scanner(System.in);
    private final ProductAndCategoryMenu productAndCategoryMenu;
    private final ProductService productService;
    private final CartService cartService;
    private final UserService userService;
//    @PersistenceContext
//    private EntityManager entityManager;

    public void viewProducts(){
        productAndCategoryMenu.viewProducts();
    }

    public void mainMenu(){
        System.out.print("Enter username or email: ");
        try {
            User user = userService.getUser(scanner.nextLine());
            boolean continueAdding = true;
            while(continueAdding) {
                System.out.println("1. Add product" +
                        "\nAny key. Exit");
                String choice = scanner.nextLine();
                switch(choice){
                    case "1" -> addToCart(user);
                    default -> continueAdding = false;
                }
            }
        } catch (UserDoesNotExistException e) {
            throw new RuntimeException(e);
        }

    }

    public void addToCart(User user){
        try {
//            User user = entityManager.getReference(User.class, userId);
            System.out.println("Enter product name or id to add.");
            String productInfo = scanner.nextLine();
            Product product = null;
            if (productInfo.matches("\\d+"))
                product = productService.getProduct(Long.valueOf(productInfo));
            else
                product = productService.getProduct(productInfo);
            System.out.print("Quantity: ");
            int quantity = Integer.parseInt(scanner.nextLine());
            cartService.save(new Cart(user, product, quantity));
            System.out.println("Successfully added " + product.getName() + " | Qty: " + quantity);
        } catch (ProductNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void viewCart(){
        System.out.print("Enter username or email: ");
        try {
            User user = userService.getUser(scanner.nextLine());
            cartService.getAllItems(user.getId()).forEach(System.out::println);
        } catch (UserDoesNotExistException e) {
            System.out.println(e.getMessage());
        }

    }
}
