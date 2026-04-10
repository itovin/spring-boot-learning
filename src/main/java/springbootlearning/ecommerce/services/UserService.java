package springbootlearning.ecommerce.services;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import springbootlearning.ecommerce.config.SecurityConfig;
import springbootlearning.ecommerce.dtos.LoginDto;
import springbootlearning.ecommerce.dtos.RegisterUserDto;
import springbootlearning.ecommerce.dtos.UserDto;
import springbootlearning.ecommerce.entities.Address;
import springbootlearning.ecommerce.entities.User;
import springbootlearning.ecommerce.exceptions.LoginFailedException;
import springbootlearning.ecommerce.mappers.UserMapper;
import springbootlearning.ecommerce.repositories.UserRepository;
import springbootlearning.ecommerce.exceptions.EmailAddressAlreadyRegisteredException;
import springbootlearning.ecommerce.exceptions.UsernameAlreadyRegisteredException;

import java.util.Optional;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder  passwordEncoder;


    public Optional<User> getUser(String usernameOrEmail) throws LoginFailedException {
        return userRepository.findByUserNameOrEmail(usernameOrEmail);
    }

    public User getUserById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found."));
    }
    public User createUser(String firstName, String lastName, String email, String username, String password) throws EmailAddressAlreadyRegisteredException, UsernameAlreadyRegisteredException {
        if(isEmailRegistered(email))
            throw new EmailAddressAlreadyRegisteredException("Email address is already registered");
        if(isUsernameRegistered(username))
            throw new UsernameAlreadyRegisteredException("Username is already registered");
        return new User(firstName, lastName, email, username, password);
    }

    public Address createAddress(String address1, String address2, String city, String province, String zipCode){
        return new Address(address1, address2, city, province, zipCode);
    }

    public void addAddressToUser(User user, Address address){
        user.getAddressSet().add(address);
        address.setUser(user);
    }

    public void addUserToAddress(User user, Address address){
        address.setUser(user);
    }
    public void save(User user){
        userRepository.save(user);
        System.out.println("Save successful!");
    }
    public boolean isEmailRegistered(String email){
        return userRepository.existsByEmail(email);
    }

    public boolean isUsernameRegistered(String username){
        return userRepository.existsByUsername(username);
    }

    public UserDto registerUser(RegisterUserDto newUserDto){

        String newUserEmail = newUserDto.getEmail();
        String newUserUsername = newUserDto.getUsername();
        if(isEmailRegistered(newUserEmail))
            throw new EmailAddressAlreadyRegisteredException("Email already registered");
        if(isUsernameRegistered(newUserUsername))
            throw new UsernameAlreadyRegisteredException("Username already registered");
        User user = userMapper.newUserDtoToUser(newUserDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        save(user);
        return userMapper.userToUserDto(user);
    }

//    public String login(LoginDto loginDto){
//        User user = getUser(loginDto.getUsernameOrEmail());
//        String rawPassword = loginDto.getPassword();
//        String encodedPassword = user.getPassword();
//        if(!passwordEncoder.matches(rawPassword, encodedPassword))
//            throw new LoginFailedException("Login failed. Invalid username or password");
//        return "Login Successful!";
//    }
}
