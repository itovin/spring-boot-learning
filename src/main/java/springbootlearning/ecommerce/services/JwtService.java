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
import springbootlearning.ecommerce.entities.User;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Service
public class JwtService {

    private final JwtConfig jwtConfig;

    public String generateAccessToken(String usernameOrEmail, UserDetails userDetails){
        return generateToken(usernameOrEmail, userDetails, jwtConfig.getAccessTokenExpiration());
    }

    public String generateRefreshToken(String usernameOrEmail, UserDetails userDetails){
        return generateToken(usernameOrEmail, userDetails, jwtConfig.getRefreshTokenExpiration());
    }

    private String generateToken(String usernameOrEmail, UserDetails userDetails, int tokenExpiration) {
        String roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("USER");
        return Jwts.builder()
                .subject(usernameOrEmail)
                .claim("role", roles)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * tokenExpiration))
                .signWith(jwtConfig.getSecretKey())
                .compact();
    }

    public boolean validateToken(@RequestHeader("Authorization") String token){

        try {
            return getPayload(token).getExpiration().after(new Date());
        }catch(JwtException e){
            return false;
        }

    }

    public Claims getPayload(String token){

        return Jwts.parser()
                .verifyWith(jwtConfig.getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getUsernameOrEmailFromToken(String token){
        return getPayload(token).getSubject();
    }

    public Role getRoleFromToken(String token){
        return Role.valueOf(getPayload(token).get("role", String.class));
    }
}
