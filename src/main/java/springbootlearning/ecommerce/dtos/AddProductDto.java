package springbootlearning.ecommerce.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class AddProductDto {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @PositiveOrZero
    private BigDecimal price;
    private String status = "Inactive";
    private Byte categoryId;

}
