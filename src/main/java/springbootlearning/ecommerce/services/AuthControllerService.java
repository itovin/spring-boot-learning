package springbootlearning.ecommerce.services;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import springbootlearning.ecommerce.config.JwtConfig;
import springbootlearning.ecommerce.dtos.JwtResponse;
import springbootlearning.ecommerce.dtos.LoginDto;
import springbootlearning.ecommerce.entities.Role;
import springbootlearning.ecommerce.entities.User;
import springbootlearning.ecommerce.exceptions.InvalidTokenException;
import springbootlearning.ecommerce.exceptions.UserNotFoundException;
import springbootlearning.ecommerce.results.AuthResult;

import java.util.List;

@AllArgsConstructor
@Service
public class AuthControllerService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final JwtConfig jwtConfig;
    private final UserService userService;

    public AuthResult login(LoginDto loginDto){
        Authentication authentication = getAuthentication(loginDto);
        UserDetails userDetails = getUserDetails(authentication);
        String usernameOrEmail = loginDto.getUsernameOrEmail();
        String accessToken = jwtService.generateAccessToken(usernameOrEmail, userDetails);
        String refreshToken = jwtService.generateRefreshToken(usernameOrEmail, userDetails);
        return new AuthResult(accessToken, refreshToken);
    }

    public String refresh(String refreshToken){

        if(!jwtService.validateToken(refreshToken, jwtConfig.getRefreshTokenSecretKey())){
            throw new InvalidTokenException("Invalid token");
        }
        String usernameOrEmail = jwtService.getUsernameOrEmailFromToken(refreshToken, jwtConfig.getRefreshTokenSecretKey());
        User user = userService.getUser(usernameOrEmail).orElseThrow(() -> new UserNotFoundException("User not found"));
        String password = user.getPassword();
        Role role = user.getRole();
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
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

    private UserDetails getUserDetails(Authentication authentication){
        return (UserDetails)authentication.getPrincipal();
    }

}
