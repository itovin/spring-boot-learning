package springbootlearning.ecommerce.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springbootlearning.ecommerce.dtos.AddCartItemDto;
import springbootlearning.ecommerce.dtos.CartDto;
import springbootlearning.ecommerce.dtos.CartItemDto;
import springbootlearning.ecommerce.dtos.ProductDto;
import springbootlearning.ecommerce.entities.Cart;
import springbootlearning.ecommerce.mappers.CartMapper;
import springbootlearning.ecommerce.mappers.ProductMapper;
import springbootlearning.ecommerce.services.CartService;
import springbootlearning.ecommerce.services.ProductService;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartMapper cartMapper;
    private final CartService cartService;


    @PostMapping
    public ResponseEntity<CartItemDto> addToCart(@RequestBody AddCartItemDto addCartItemDto){

        Cart cartItem = cartMapper.cartItemDtoToCart(addCartItemDto);
        cartService.save(cartItem);
        CartItemDto cartItemDto = cartMapper.cartItemToCartItemDto(cartItem);
        return ResponseEntity.ok(cartItemDto);
    }

    @GetMapping
    public ResponseEntity<CartDto> showCart(@RequestParam(name = "userId") Long id){
        CartDto cartDto = new CartDto();

        List<CartItemDto> cartItemDtoList = cartService.getAllItems(id).stream()
                .map(cartMapper::cartItemToCartItemDto)
                .toList();
        BigDecimal totalGrandPrice = cartItemDtoList.stream()
                .map(cartItemDto -> cartItemDto.getTotalPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        cartDto.setCartItemDtoList(cartItemDtoList);
        cartDto.setTotalGrandPrice(totalGrandPrice);

        return ResponseEntity.ok(cartDto);
    }
}
