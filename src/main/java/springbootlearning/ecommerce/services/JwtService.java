package springbootlearning.ecommerce.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;
import springbootlearning.ecommerce.config.JwtConfig;
import springbootlearning.ecommerce.entities.Role;

import javax.crypto.SecretKey;
import java.util.Date;

@AllArgsConstructor
@Service
public class JwtService {

    private final JwtConfig jwtConfig;

    public String generateAccessToken(String usernameOrEmail, UserDetails userDetails){
        return generateToken(usernameOrEmail, userDetails, jwtConfig.getAccessTokenExpiration(), jwtConfig.getAccessTokenSecretKey());
    }

    public String generateRefreshToken(String usernameOrEmail, UserDetails userDetails){
        return generateToken(usernameOrEmail, userDetails, jwtConfig.getRefreshTokenExpiration(), jwtConfig.getRefreshTokenSecretKey());
    }

    private String generateToken(String usernameOrEmail, UserDetails userDetails, int tokenExpiration, SecretKey secretKey) {

        String roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("USER");
        return Jwts.builder()
                .subject(usernameOrEmail)
                .claim("role", roles)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * tokenExpiration))
                .signWith(secretKey)
                .compact();
    }

    public boolean validateToken(String token, SecretKey secretKey){
        try {
            return getPayload(token, secretKey).getExpiration().after(new Date());
        }catch(JwtException e){
            return false;
        }

    }

    public Claims getPayload(String token, SecretKey secretKey){

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getUsernameOrEmailFromToken(String token, SecretKey secretKey){
        return getPayload(token, secretKey).getSubject();
    }

    public Role getRoleFromToken(String token, SecretKey secretKey){
        return Role.valueOf(getPayload(token, secretKey).get("role", String.class));
    }
}
