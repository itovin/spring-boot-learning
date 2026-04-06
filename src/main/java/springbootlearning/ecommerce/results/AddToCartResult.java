package springbootlearning.ecommerce.results;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import springbootlearning.ecommerce.dtos.CartItemDto;
import springbootlearning.ecommerce.dtos.ProductDto;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class AddToCartResult {
    CartItemDto cartItemDto;
    AddToCartStatus status = AddToCartStatus.ADDED;


    public enum AddToCartStatus{
        UPDATED,
        ADDED
    }
}
