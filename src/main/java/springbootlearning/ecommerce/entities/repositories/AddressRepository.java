package springbootlearning.ecommerce.entities.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import springbootlearning.ecommerce.entities.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
