package springbootlearning.ecommerce.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springbootlearning.ecommerce.dtos.JwtResponse;
import springbootlearning.ecommerce.dtos.LoginDto;
import springbootlearning.ecommerce.services.JwtService;

@AllArgsConstructor
@RestController
@RequestMapping("/login")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginDto loginDto){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsernameOrEmail(),
                        loginDto.getPassword()
                )
        );

        String token = jwtService.generateToken(loginDto.getUsernameOrEmail());
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
