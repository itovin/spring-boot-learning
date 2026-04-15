package springbootlearning.ecommerce.results;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AuthResult {
    private final String accessToken;
    private final String refreshToken;
}
