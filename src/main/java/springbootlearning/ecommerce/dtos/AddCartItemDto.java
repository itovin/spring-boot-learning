package springbootlearning.ecommerce.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddCartItemDto {
    private Long userId;
    private Long productId;
    private int quantity;
}
