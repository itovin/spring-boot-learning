package springbootlearning.ecommerce.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import springbootlearning.ecommerce.entities.Product;
import springbootlearning.ecommerce.repositories.ProductRepository;

import java.util.List;

@AllArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> getAll(){
        return productRepository.findAll();
    }

    public List<Product> getAllByCategoryId(Long id){
        return productRepository.findAllByCategoryId(id);
    }
    public void save(Product product){
        productRepository.save(product);
    }
}
