package springbootlearning.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springbootlearning.ecommerce.entities.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
