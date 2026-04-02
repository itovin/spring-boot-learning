package springbootlearning.ecommerce.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import springbootlearning.ecommerce.entities.Product;
import springbootlearning.ecommerce.exceptions.ProductNotFoundException;
import springbootlearning.ecommerce.repositories.ProductRepository;

import java.util.List;

@AllArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> getAll(){
        return productRepository.findAll();
    }

    public List<Product> getAllByCategoryId(Byte id){
        return productRepository.findAllWithCategoryId(Integer.valueOf(id));
    }
    public void save(Product product){
        productRepository.save(product);
    }

    public Product getProduct(Long id) throws ProductNotFoundException {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }
    public Product getProduct(String name) throws ProductNotFoundException {
        return productRepository.findByName(name).orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }

    public boolean isProductExist(String name){
        return productRepository.findByName(name).isPresent();
    }
}
