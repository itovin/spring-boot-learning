package springbootlearning.ecommerce.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Date;

@Service
public class JwtService {
    @Value("${spring.jwt.secret}")
    private String secret;
    public String generateToken(String usernameOrEmail){
        final int TOKEN_EXPIRATION = 86400;

        return Jwts.builder()
                .subject(usernameOrEmail)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * TOKEN_EXPIRATION))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
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
                .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getUsernameOrEmail(String token){
        return getPayload(token).getSubject();
    }
}
