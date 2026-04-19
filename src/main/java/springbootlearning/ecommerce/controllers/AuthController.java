package springbootlearning.ecommerce.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import springbootlearning.ecommerce.config.JwtConfig;
import springbootlearning.ecommerce.dtos.JwtResponse;
import springbootlearning.ecommerce.dtos.LoginDto;
import springbootlearning.ecommerce.results.AuthResult;
import springbootlearning.ecommerce.securities.AuthControllerService;
import springbootlearning.ecommerce.securities.JwtService;
import springbootlearning.ecommerce.services.UserService;

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
        Cookie cookie = buildCookie(authResult.getRefreshToken(), jwtConfig.getRefreshTokenExpiration());
        response.addCookie(cookie);
        return ResponseEntity.ok(new JwtResponse(authResult.getAccessToken()));
    }

    @PostMapping("/auth/refresh")
    public ResponseEntity<JwtResponse> refresh(@CookieValue(value = "refreshToken", required = false) String refreshToken){
        String accessToken = authControllerService.refresh(refreshToken);
        return ResponseEntity.ok(new JwtResponse(accessToken));
    }

    @PostMapping("/auth/logout")
    public ResponseEntity<String> logout(@CookieValue(value = "refreshToken", required = false) String refreshToken, HttpServletResponse response){
        authControllerService.logout(refreshToken);
        Cookie cookie = buildCookie("", 0);
        response.addCookie(cookie);
        return ResponseEntity.ok("Logged out successfully");
    }


    private Cookie buildCookie(String refreshToken, int expiry){
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/auth");
        cookie.setMaxAge(expiry);
        cookie.setSecure(true);
        return cookie;
    }

}
