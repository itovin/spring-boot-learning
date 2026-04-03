package springbootlearning.ecommerce.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CartDto {
    private List<CartItemDto> cartItemDtoList = new ArrayList<>();
    private BigDecimal totalGrandPrice = BigDecimal.ZERO;
}
