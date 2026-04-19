package springbootlearning.ecommerce.securities;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import springbootlearning.ecommerce.config.JwtConfig;
import springbootlearning.ecommerce.dtos.LoginDto;
import springbootlearning.ecommerce.entities.Role;
import springbootlearning.ecommerce.entities.User;
import springbootlearning.ecommerce.exceptions.InvalidTokenException;
import springbootlearning.ecommerce.exceptions.UserNotFoundException;
import springbootlearning.ecommerce.results.AuthResult;
import springbootlearning.ecommerce.services.RefreshTokenService;
import springbootlearning.ecommerce.services.UserService;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class AuthControllerService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final JwtConfig jwtConfig;
    private final UserService userService;
    private RefreshTokenService refreshTokenService;

    public AuthResult login(LoginDto loginDto){
        Authentication authentication = getAuthentication(loginDto);
        CustomUserDetails userDetails = getUserDetails(authentication);
        String usernameOrEmail = loginDto.getUsernameOrEmail();
        String accessToken = jwtService.generateAccessToken(usernameOrEmail, userDetails);
        String refreshToken = jwtService.generateRefreshToken(usernameOrEmail, userDetails);
        return new AuthResult(accessToken, refreshToken);
    }

    public String refresh(String refreshToken){
        Claims claims;
        try {
            claims = jwtService.validateTokenAndGetClaim(refreshToken, jwtConfig.getRefreshTokenSecretKey());
        }catch (Exception e){
            throw new InvalidTokenException("Invalid token");
        }

        String jti = claims.get("jti", String.class);
        if(!refreshTokenService.isJtiValid(UUID.fromString(jti)))
            throw new InvalidTokenException("Invalid token");

        String usernameOrEmail = claims.getSubject();
        User user = userService.getUser(usernameOrEmail).orElseThrow(() -> new UserNotFoundException("User not found"));
        Long id = user.getId();
        String password = user.getPassword();
        Role role = user.getRole();
        CustomUserDetails userDetails = new CustomUserDetails(
                id,
                usernameOrEmail,
                password,
                List.of(new SimpleGrantedAuthority(role.name()))
        );
        return jwtService.generateAccessToken(usernameOrEmail, userDetails);
    }

    private Authentication getAuthentication(LoginDto loginDto){
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsernameOrEmail(),
                        loginDto.getPassword()
                )
        );
    }

    private CustomUserDetails getUserDetails(Authentication authentication){
        return (CustomUserDetails)authentication.getPrincipal();
    }

    public void logout(String refreshToken){
        try {
            Claims claims = jwtService.validateTokenAndGetClaim(refreshToken, jwtConfig.getRefreshTokenSecretKey());
            String jti = claims.get("jti", String.class);
            refreshTokenService.updateRefreshTokenStatus(UUID.fromString(jti));
        }catch(Exception e){}
    }
}
