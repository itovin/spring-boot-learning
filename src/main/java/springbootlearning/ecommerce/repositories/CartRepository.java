package springbootlearning.ecommerce.repositories;

import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import springbootlearning.ecommerce.entities.Cart;
import springbootlearning.ecommerce.entities.Product;
import springbootlearning.ecommerce.entities.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @EntityGraph(attributePaths = "product")
    List<Cart> findByUserId(Long id);

    @Query("select c from Cart c where user = :user and product = :product ")
    Optional<Cart> findCartItemOfUser(@Param("user")User user, @Param("product")Product product);
}
