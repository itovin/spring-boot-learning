package springbootlearning.ecommerce.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import springbootlearning.ecommerce.entities.Cart;
import springbootlearning.ecommerce.repositories.CartRepository;

import java.util.List;

@AllArgsConstructor
@Service
public class CartService {
    private final CartRepository cartRepository;

    public void save(Cart cart){
        cartRepository.save(cart);
    }

    public List<Cart> getAllItems(Long id){
        return cartRepository.findByUserId(id);
    }
}
