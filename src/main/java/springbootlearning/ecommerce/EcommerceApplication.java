package springbootlearning.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import springbootlearning.ecommerce.entities.services.UserService;
import springbootlearning.ecommerce.menu.Menu;

@SpringBootApplication
public class EcommerceApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(EcommerceApplication.class, args);
        Menu menu = context.getBean(Menu.class);
        menu.mainMenu();
	}

}
