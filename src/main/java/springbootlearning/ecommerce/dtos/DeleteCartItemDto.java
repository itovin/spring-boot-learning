package springbootlearning.ecommerce.dtos;

import lombok.Getter;

@Getter
public class DeleteCartItemDto {
    private Long userId;
    private Long productId;
}
