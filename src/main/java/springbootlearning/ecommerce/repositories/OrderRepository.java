package springbootlearning.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springbootlearning.ecommerce.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
