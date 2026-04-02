package springbootlearning.ecommerce.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginDto {
    @NotBlank
    private String usernameOrEmail;

    @NotBlank
    private String password;
}
