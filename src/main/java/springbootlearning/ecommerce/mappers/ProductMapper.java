package springbootlearning.ecommerce.mappers;

import org.springframework.stereotype.Component;
import springbootlearning.ecommerce.dtos.ProductDto;
import springbootlearning.ecommerce.entities.Product;

@Component
public class ProductMapper {
    public ProductDto productToProductDto(Product product){
        if(product == null)
            return null;
        return new ProductDto(product.getId(), product.getName(), product.getDescription(), product.getPrice());
    }
}
