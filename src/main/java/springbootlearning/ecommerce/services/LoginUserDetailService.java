package springbootlearning.ecommerce.services;

import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import springbootlearning.ecommerce.entities.Role;
import springbootlearning.ecommerce.entities.User;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@Service
public class LoginUserDetailService implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
       User user = userService.getUser(usernameOrEmail).orElseThrow(() -> new UsernameNotFoundException("Login failed. Invalid credentials"));
       String password = user.getPassword();
       Role role = user.getRole();
        return new org.springframework.security.core.userdetails.User(
                usernameOrEmail,
                password,
                List.of(new SimpleGrantedAuthority(role.name()))
        );
    }
}
