package springbootlearning.ecommerce.securities;

import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import springbootlearning.ecommerce.entities.Role;
import springbootlearning.ecommerce.entities.User;
import springbootlearning.ecommerce.services.UserService;

import java.util.List;

@AllArgsConstructor
@Service
public class LoginUserDetailService implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
       User user = userService.getUser(usernameOrEmail).orElseThrow(() -> new UsernameNotFoundException("Login failed. Invalid credentials"));
       Long id = user.getId();
       String password = user.getPassword();
       Role role = user.getRole();
        return new CustomUserDetails(
                id,
                usernameOrEmail,
                password,
                List.of(new SimpleGrantedAuthority(role.name()))
        );
    }


}
