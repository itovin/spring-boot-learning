package springbootlearning.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import springbootlearning.ecommerce.entities.RefreshToken;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {

    Optional<RefreshToken> findById(UUID id);

    @Query("select r from RefreshToken r where r.id = :id and r.isRevoked = false")
    Optional<RefreshToken> getRefreshToken(@Param("id")UUID jti);

    @Modifying
    @Query("update RefreshToken set isRevoked = true where id = :jti and isRevoked = false")
    int updateRefreshTokenStatus(@Param("jti") UUID jti);
}
