package springbootlearning.ecommerce.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import springbootlearning.ecommerce.entities.RefreshToken;
import springbootlearning.ecommerce.entities.User;
import springbootlearning.ecommerce.exceptions.InvalidTokenException;
import springbootlearning.ecommerce.repositories.RefreshTokenRepository;
import springbootlearning.ecommerce.repositories.UserRepository;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    public String save(Long userId, Instant expiresAt){
        User userRef = userRepository.getReferenceById(userId);
        RefreshToken refreshToken = new RefreshToken(userRef, expiresAt);
        refreshTokenRepository.save(refreshToken);
        return String.valueOf(refreshToken.getId());
    }

    public boolean isJtiValid(UUID jti){
        refreshTokenRepository.getRefreshToken(jti).orElseThrow(() -> new InvalidTokenException("Invalid token"));
        return true;
    }

    @Transactional
    public int updateRefreshTokenStatus(UUID jti){
        return refreshTokenRepository.updateRefreshTokenStatus(jti);
    }
}
