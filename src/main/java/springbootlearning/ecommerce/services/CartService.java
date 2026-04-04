package springbootlearning.ecommerce.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import springbootlearning.ecommerce.dtos.AddCartItemDto;
import springbootlearning.ecommerce.dtos.CartDto;
import springbootlearning.ecommerce.dtos.CartItemDto;
import springbootlearning.ecommerce.dtos.DeleteCartItemDto;
import springbootlearning.ecommerce.entities.Cart;
import springbootlearning.ecommerce.entities.Product;
import springbootlearning.ecommerce.entities.User;
import springbootlearning.ecommerce.exceptions.ItemDoesNotExistInCart;
import springbootlearning.ecommerce.mappers.CartMapper;
import springbootlearning.ecommerce.repositories.CartRepository;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Service
public class CartService {
    private final CartRepository cartRepository;
    private final UserService userService;
    private final ProductService productService;
    private final CartMapper cartMapper;

    public void save(Cart cart){
        cartRepository.save(cart);
    }

    public List<Cart> getAllCartItemsOfUser(Long id){
        return cartRepository.findByUserId(id);
    }

    public Cart getCartItemOfUser(User user, Product product){
        return cartRepository.findCartItemOfUser(user, product).orElse(null);
    }

    @Transactional
    public int deleteCartItemFromCart(Long userId, Long productId){
        return cartRepository.deleteItemFromCart(userId, productId);
    }

    public CartDto getCartDto(Long id){

        CartDto cartDto = new CartDto();

        List<CartItemDto> cartItemDtoList = getAllCartItemsOfUser(id).stream()
                .map(cartMapper::cartItemToCartItemDto)
                .toList();
        BigDecimal totalGrandPrice = cartItemDtoList.stream()
                .map(CartItemDto::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        cartDto.setCartItemDtoList(cartItemDtoList);
        cartDto.setTotalGrandPrice(totalGrandPrice);

        return cartDto;
    }

    public Cart addCartItemDtoToCart(AddCartItemDto addCartItemDto){
        User user = userService.getUserById(addCartItemDto.getUserId());
        Product product = productService.getProduct(addCartItemDto.getProductId());
        Cart cart = getCartItemOfUser(user, product);
        if(cart == null)
            return new Cart(user, product, addCartItemDto.getQuantity());
        cart.setQuantity(cart.getQuantity() + addCartItemDto.getQuantity());
        return cart;
    }

    public CartItemDto saveCartItem(AddCartItemDto addCartItemDto){
        Cart cart = addCartItemDtoToCart(addCartItemDto);
        save(cart);
        return cartMapper.cartItemToCartItemDto(cart);
    }


    public CartItemDto cartItemToCartItemDto(Cart cartItem){
        return cartMapper.cartItemToCartItemDto(cartItem);
    }

    public String deleteCartItem(DeleteCartItemDto deleteCartItemDto){
        int rowDeleted = deleteCartItemFromCart(deleteCartItemDto.getUserId(), deleteCartItemDto.getProductId());
        if(rowDeleted == 0)
            throw new ItemDoesNotExistInCart("Item is not in the cart.");
        return productService.getProduct(deleteCartItemDto.getProductId()).getName();
    }
}
