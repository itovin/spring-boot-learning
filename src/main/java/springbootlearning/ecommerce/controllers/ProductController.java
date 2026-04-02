package springbootlearning.ecommerce.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springbootlearning.ecommerce.dtos.ProductDto;
import springbootlearning.ecommerce.entities.Product;
import springbootlearning.ecommerce.exceptions.CategoryNotFoundException;
import springbootlearning.ecommerce.mappers.ProductMapper;
import springbootlearning.ecommerce.services.CategoryService;
import springbootlearning.ecommerce.services.ProductService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductMapper productMapper;
    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<ProductDto>> showAllProducts(@RequestParam(required = false, name = "categoryId") Byte categoryId){
        List<ProductDto> productDtoList = null;
        List<Product> productList = null;
        if(categoryId == null)
            productList = productService.getAll();
        else if(!categoryService.isCategoryExists(categoryId))
            throw new CategoryNotFoundException("No such category");
        else{
            productList = productService.getAllByCategoryId(categoryId);
        }
       productDtoList = productList.stream()
                .map(productMapper::productToProductDto)
                .toList();
        return ResponseEntity.ok(productDtoList);
    }
}
