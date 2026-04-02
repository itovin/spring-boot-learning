package springbootlearning.ecommerce.dtos;

import java.math.BigDecimal;

public class CartItemDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private int quantity;
}
