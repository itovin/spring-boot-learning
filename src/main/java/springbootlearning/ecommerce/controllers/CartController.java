package springbootlearning.ecommerce.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springbootlearning.ecommerce.dtos.*;
import springbootlearning.ecommerce.entities.Cart;
import springbootlearning.ecommerce.exceptions.ItemDoesNotExistInCart;
import springbootlearning.ecommerce.mappers.CartMapper;
import springbootlearning.ecommerce.services.CartService;
import springbootlearning.ecommerce.services.ProductService;

import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;


    @PostMapping
    public ResponseEntity<CartItemDto> addToCart(@RequestBody AddCartItemDto addCartItemDto){
        return ResponseEntity.ok(cartService.saveCartItem(addCartItemDto));
    }

    @GetMapping
    public ResponseEntity<CartDto> showCart(@RequestParam(name = "userId") Long id){
        return ResponseEntity.ok(cartService.getCartDto(id));
    }

    @DeleteMapping
    public ResponseEntity<Map<String, String>> deleteFromCart(@RequestBody DeleteCartItemDto deleteCartItemDto){
        String name = cartService.deleteCartItem(deleteCartItemDto);
        return ResponseEntity.ok(
                Map.of("Deleted", name)
        );
    }
}
