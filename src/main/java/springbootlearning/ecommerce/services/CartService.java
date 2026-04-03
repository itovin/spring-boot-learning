package springbootlearning.ecommerce.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import springbootlearning.ecommerce.dtos.CartItemDto;
import springbootlearning.ecommerce.entities.Cart;
import springbootlearning.ecommerce.entities.Product;
import springbootlearning.ecommerce.entities.User;
import springbootlearning.ecommerce.repositories.CartRepository;

import java.util.List;

@AllArgsConstructor
@Service
public class CartService {
    private final CartRepository cartRepository;
    private final UserService userService;
    private final ProductService productService;

    public void save(Cart cart){
        cartRepository.save(cart);
    }

    public List<Cart> getAllItems(Long id){
        return cartRepository.findByUserId(id);
    }

    public Cart getCartItemOfUser(User user, Product product){
        return cartRepository.findCartItemOfUser(user, product).orElse(null);
    }
}
