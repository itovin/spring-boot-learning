package springbootlearning.ecommerce.securities;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import springbootlearning.ecommerce.config.JwtConfig;
import springbootlearning.ecommerce.entities.Role;
import springbootlearning.ecommerce.exceptions.InvalidTokenException;
import springbootlearning.ecommerce.services.RefreshTokenService;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

@AllArgsConstructor
@Service
public class JwtService {

    private final JwtConfig jwtConfig;
    private final RefreshTokenService refreshTokenService;

    public String generateAccessToken(String usernameOrEmail, CustomUserDetails userDetails){
        return generateToken(usernameOrEmail, userDetails, jwtConfig.getAccessTokenExpiration(), jwtConfig.getAccessTokenSecretKey(), false);
    }

    public String generateRefreshToken(String usernameOrEmail, CustomUserDetails userDetails){
        return generateToken(usernameOrEmail, userDetails, jwtConfig.getRefreshTokenExpiration(), jwtConfig.getRefreshTokenSecretKey(), true);
    }

    private String generateToken(String usernameOrEmail, CustomUserDetails userDetails, int tokenExpiration, SecretKey secretKey, boolean isRefreshToken) {
        Instant expiresAt = Instant.now().plusSeconds(tokenExpiration);
        Long userId = userDetails.getId();
        String roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("USER");
        JwtBuilder jwts =  Jwts.builder()
                .subject(usernameOrEmail)
                .claim("role", roles)
                .issuedAt(new Date())
                .expiration(Date.from(expiresAt))
                .signWith(secretKey);

        if(isRefreshToken){
            String jti = refreshTokenService.save(userId, expiresAt);
            jwts.claim("jti", jti);
        }

        return jwts.compact();

    }

    public Claims validateTokenAndGetClaim(String token, SecretKey secretKey){

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }

}
