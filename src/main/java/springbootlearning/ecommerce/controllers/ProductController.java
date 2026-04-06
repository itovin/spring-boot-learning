package springbootlearning.ecommerce.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import springbootlearning.ecommerce.dtos.AddProductDto;
import springbootlearning.ecommerce.dtos.ProductDto;
import springbootlearning.ecommerce.entities.Product;
import springbootlearning.ecommerce.exceptions.CategoryNotFoundException;
import springbootlearning.ecommerce.mappers.ProductMapper;
import springbootlearning.ecommerce.services.CategoryService;
import springbootlearning.ecommerce.services.ProductService;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDto>> showAllProducts(@RequestParam(required = false, name = "categoryId") Byte categoryId){
        return ResponseEntity.ok(productService.getProductDtoList(categoryId));
    }

    @PostMapping
    public ResponseEntity<ProductDto> addProduct(@Valid @RequestBody AddProductDto addProductDto, UriComponentsBuilder uriBuilder){
        return new ResponseEntity<>(productService.addProduct(addProductDto), HttpStatus.CREATED);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable(name = "productId") Long productId){
        return ResponseEntity.ok(productService.getProductDto(productId));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Map<String, String>> deleteProduct(@PathVariable(name = "productId") Long productId){
        return ResponseEntity.ok(
                Map.of("deleted", productService.deleteProduct(productId))
        );
    }

}
