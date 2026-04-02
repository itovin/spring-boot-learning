package springbootlearning.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
//import springbootlearning.ecommerce.exceptions.CategoryAlreadyExistException;
//import springbootlearning.ecommerce.exceptions.CategoryNotFoundException;
//import springbootlearning.ecommerce.exceptions.InvalidInputException;
//import springbootlearning.ecommerce.menu.LoginRegistrationMenu;
//import springbootlearning.ecommerce.menu.MainMenu;
//import springbootlearning.ecommerce.menu.ProductAndCategoryMenu;

@SpringBootApplication
public class EcommerceApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(EcommerceApplication.class, args);
//        MainMenu  mainMenu = context.getBean((MainMenu.class));
//        ProductAndCategoryMenu productAndCategoryMenu = context.getBean(ProductAndCategoryMenu.class);
//        mainMenu.mainMenu();
    }

}
