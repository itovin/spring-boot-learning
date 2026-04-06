package springbootlearning.ecommerce.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import springbootlearning.ecommerce.entities.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @EntityGraph(attributePaths = "category")
    List<Product> findAll();

    @EntityGraph(attributePaths = "category")
    @Query("select p from Product p where p.category.id = :id")
    List<Product> findAllWithCategoryId(@Param("id") Integer id);

    Optional<Product> findById(Long id);
    Optional<Product> findByName(String name);

    @Modifying
    @Query("delete from Product where id = :productId")
    int deleteProduct(@Param("productId") Long productId);
}
