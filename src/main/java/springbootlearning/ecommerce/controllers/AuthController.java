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
import springbootlearning.ecommerce.results.AuthResult;
import springbootlearning.ecommerce.services.AuthControllerService;
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
    private final AuthControllerService authControllerService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginDto loginDto, HttpServletResponse response){
        AuthResult authResult =  authControllerService.login(loginDto);
        Cookie cookie = buildRefreshTokenCookie(authResult.getRefreshToken());
        response.addCookie(cookie);
        return ResponseEntity.ok(new JwtResponse(authResult.getAccessToken()));
    }

    @PostMapping("/auth/refresh")
    public ResponseEntity<JwtResponse> refresh(@CookieValue("refreshToken") String refreshToken){
        String accessToken = authControllerService.refresh(refreshToken);

        return ResponseEntity.ok(new JwtResponse(accessToken));
    }


    private Cookie buildRefreshTokenCookie(String refreshToken){
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/auth/refresh");
        cookie.setMaxAge(jwtConfig.getRefreshTokenExpiration());
        cookie.setSecure(true);
        return cookie;
    }
}
