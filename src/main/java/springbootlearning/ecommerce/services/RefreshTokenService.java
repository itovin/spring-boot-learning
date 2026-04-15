package springbootlearning.ecommerce.services;

import lombok.AllArgsConstructor;
import springbootlearning.ecommerce.entities.RefreshToken;
import springbootlearning.ecommerce.repositories.RefreshTokenRepository;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    public Optional<RefreshToken> getRefreshToken(UUID id){
        return refreshTokenRepository.findById(id);
    }
}
