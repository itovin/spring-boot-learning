package springbootlearning.ecommerce.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import springbootlearning.ecommerce.entities.Address;

@Getter
public class RegisterUserDto {
    @NotBlank(message = "First Name must not be blanked")
    private String firstName;

    @NotBlank(message = "Last Name must not be blanked")
    private String lastName;

    @NotBlank(message = "Email must not be blanked")
    @Email(message = "Email must be valid")
    private String email;

    @NotBlank(message = "Username must not be blanked")
    private String username;

    @NotBlank(message = "Password must not be blanked")
    @Size(min = 8, max = 25, message = "Password must be 8 to 25 characters long")
    private String password;
    private Address address = null;
}
