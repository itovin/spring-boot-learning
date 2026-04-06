package springbootlearning.ecommerce.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springbootlearning.ecommerce.dtos.*;
import springbootlearning.ecommerce.entities.Cart;
import springbootlearning.ecommerce.exceptions.ItemDoesNotExistInCart;
import springbootlearning.ecommerce.mappers.CartMapper;
import springbootlearning.ecommerce.results.AddToCartResult;
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
        AddToCartResult result = cartService.addToCart(addCartItemDto);
        if(result.getStatus() == AddToCartResult.AddToCartStatus.UPDATED)
            return ResponseEntity.ok(result.getCartItemDto());
        return new ResponseEntity<>(result.getCartItemDto(), HttpStatus.CREATED);
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
