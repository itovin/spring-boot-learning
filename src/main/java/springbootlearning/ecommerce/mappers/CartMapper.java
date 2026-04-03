package springbootlearning.ecommerce.mappers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import springbootlearning.ecommerce.dtos.AddCartItemDto;
import springbootlearning.ecommerce.dtos.CartItemDto;
import springbootlearning.ecommerce.entities.Cart;
import springbootlearning.ecommerce.entities.Product;
import springbootlearning.ecommerce.entities.User;
import springbootlearning.ecommerce.services.CartService;
import springbootlearning.ecommerce.services.ProductService;
import springbootlearning.ecommerce.services.UserService;

import java.math.BigDecimal;

@AllArgsConstructor
@Component
public class CartMapper {
    private final UserService userService;
    private final ProductService productService;
    private final CartService cartService;

    public Cart cartItemDtoToCart(AddCartItemDto addCartItemDto){
        User user = userService.getUserById(addCartItemDto.getUserId());
        Product product = productService.getProduct(addCartItemDto.getProductId());
        Cart cart = cartService.getCartItemOfUser(user, product);
        if(cart == null)
            return new Cart(user, product, addCartItemDto.getQuantity());
        cart.setQuantity(cart.getQuantity() + addCartItemDto.getQuantity());
        return cart;
    }

    public CartItemDto cartItemToCartItemDto(Cart cartItem){
        String productName = cartItem.getProduct().getName();;
        String productDescription = cartItem.getProduct().getDescription();
        int quantity = cartItem.getQuantity();;
        BigDecimal price = cartItem.getProduct().getPrice();
        BigDecimal totalPrice = price.multiply(BigDecimal.valueOf(quantity));
        return new CartItemDto(productName, productDescription, quantity, price, totalPrice);
    }
}
