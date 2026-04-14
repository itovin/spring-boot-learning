package springbootlearning.ecommerce.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import springbootlearning.ecommerce.entities.Address;
import springbootlearning.ecommerce.entities.Role;

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
    @Pattern(regexp = "^[a-z0-9_]+$", message = "Username must only contain lower case characters, numbers, and/or underscore (_)")
    @Size(min = 4, max = 18, message = "Username must be 4 to 18 characters long")
    private String username;

    @NotBlank(message = "Password must not be blanked")
    @Size(min = 8, max = 25, message = "Password must be 8 to 25 characters long")
    private String password;

    private Role role = Role.USER;

    private Address address = null;
}
