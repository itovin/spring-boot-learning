package springbootlearning.ecommerce.services;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import springbootlearning.ecommerce.entities.User;

import java.util.Collections;

@AllArgsConstructor
@Service
public class LoginUserDetailService implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
       User user = userService.getUser(usernameOrEmail).orElseThrow(() -> new UsernameNotFoundException("Login failed. Invalid credentials"));

       return new org.springframework.security.core.userdetails.User(
               usernameOrEmail,
               user.getPassword(),
               Collections.emptyList()
       );
    }
}
