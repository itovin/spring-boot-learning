package springbootlearning.ecommerce.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@Setter
@Getter
public class CartItemDto {
    private Long productId;
    private String productName;
    private String productDescription;
    private int quantity = 1;
    private BigDecimal price = BigDecimal.ZERO;
    private BigDecimal totalPrice = BigDecimal.ZERO;
}
