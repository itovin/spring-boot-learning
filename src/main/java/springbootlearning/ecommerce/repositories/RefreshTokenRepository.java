package springbootlearning.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import springbootlearning.ecommerce.entities.RefreshToken;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {

    Optional<RefreshToken> findById(UUID id);
}
