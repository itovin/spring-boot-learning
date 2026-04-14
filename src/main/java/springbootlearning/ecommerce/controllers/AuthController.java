package springbootlearning.ecommerce.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import springbootlearning.ecommerce.config.JwtConfig;
import springbootlearning.ecommerce.dtos.JwtResponse;
import springbootlearning.ecommerce.dtos.LoginDto;
import springbootlearning.ecommerce.entities.Role;
import springbootlearning.ecommerce.entities.User;
import springbootlearning.ecommerce.exceptions.InvalidTokenException;
import springbootlearning.ecommerce.exceptions.UserNotFoundException;
import springbootlearning.ecommerce.services.JwtService;
import springbootlearning.ecommerce.services.UserService;

import java.util.List;

@AllArgsConstructor
@RestController
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final JwtConfig jwtConfig;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginDto loginDto, HttpServletResponse response){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsernameOrEmail(),
                        loginDto.getPassword()
                )
        );
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        String accessToken = jwtService.generateAccessToken(loginDto.getUsernameOrEmail(), userDetails);
        String refreshToken = jwtService.generateRefreshToken(loginDto.getUsernameOrEmail(), userDetails);
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/auth/refresh");
        cookie.setMaxAge(jwtConfig.getRefreshTokenExpiration());
        cookie.setSecure(true);
        response.addCookie(cookie);
        return ResponseEntity.ok(new JwtResponse(accessToken));
    }

    @PostMapping("/auth/refresh")
    public ResponseEntity<JwtResponse> refresh(@CookieValue("refreshToken") String refreshToken){

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
        String newAccessToken = jwtService.generateAccessToken(usernameOrEmail, userDetails);
        return ResponseEntity.ok(new JwtResponse(newAccessToken));
    }
}
