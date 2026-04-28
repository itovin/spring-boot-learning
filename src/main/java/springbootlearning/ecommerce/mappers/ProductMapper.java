package springbootlearning.ecommerce.mappers;

import org.springframework.stereotype.Component;
import springbootlearning.ecommerce.dtos.AddProductDto;
import springbootlearning.ecommerce.dtos.CartItemDto;
import springbootlearning.ecommerce.dtos.ProductDto;
import springbootlearning.ecommerce.entities.*;

import java.math.BigDecimal;

@Component
public class ProductMapper {
    public ProductDto productToProductDto(Product product){
        if(product == null)
            return null;
        return new ProductDto(product.getId(), product.getName(), product.getDescription(), product.getPrice());
    }

    public Product addProductDtoToProduct(AddProductDto addProductDto){
        return new Product(addProductDto.getName(), addProductDto.getPrice(), addProductDto.getDescription(), addProductDto.getStatus());
    }


    public OrderItem cartItemToOrderItem(Order order, Cart cartItem){
        Product product = cartItem.getProduct();
        String name = product.getName();
        String description = product.getDescription();
        BigDecimal price = product.getPrice();
        int quantity = cartItem.getQuantity();
        return new OrderItem(
                order,
                product,
                name,
                description,
                price,
                quantity
        );
    }

}
