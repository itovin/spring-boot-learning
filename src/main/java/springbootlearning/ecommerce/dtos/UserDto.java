package springbootlearning.ecommerce.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class UserDto {
    private Long id;
    private String username;
    private String fullName;
    private String email;
}
