package springbootlearning.ecommerce.entities.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springbootlearning.ecommerce.entities.User;
import springbootlearning.ecommerce.entities.repositories.UserRepository;
import springbootlearning.ecommerce.exceptions.EmailAddressAlreadyRegisteredException;
import springbootlearning.ecommerce.exceptions.UsernameAlreadyRegisteredException;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public void registerUser(User user) throws EmailAddressAlreadyRegisteredException, UsernameAlreadyRegisteredException {
        if(isEmailRegistered(user.getEmail()))
            throw new EmailAddressAlreadyRegisteredException("Email address is already registered");
        if(isUsernameRegistered(user.getUsername()))
            throw new UsernameAlreadyRegisteredException("Username is already registered");
        userRepository.save(user);
;    }

    public boolean isEmailRegistered(String email){
        return userRepository.findByEmail(email).isPresent();
    }

    public boolean isUsernameRegistered(String username){
        return userRepository.findByUsername(username).isPresent();
    }
}
