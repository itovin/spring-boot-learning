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
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDto>> showAllProducts(@RequestParam(required = false, name = "categoryId") Byte categoryId){
        return ResponseEntity.ok(productService.getProductDtoList(categoryId));
    }
}
