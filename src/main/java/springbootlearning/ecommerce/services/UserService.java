package springbootlearning.ecommerce.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import springbootlearning.ecommerce.entities.Address;
import springbootlearning.ecommerce.entities.User;
import springbootlearning.ecommerce.repositories.UserRepository;
import springbootlearning.ecommerce.exceptions.EmailAddressAlreadyRegisteredException;
import springbootlearning.ecommerce.exceptions.UserDoesNotExistException;
import springbootlearning.ecommerce.exceptions.UsernameAlreadyRegisteredException;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;


    public User getUser(String usernameOrEmail) throws UserDoesNotExistException {
        if(isEmailRegistered(usernameOrEmail))
            return userRepository.findByEmail(usernameOrEmail).get();
        if(isUsernameRegistered(usernameOrEmail))
            return userRepository.findByUsername(usernameOrEmail).get();
        throw new UserDoesNotExistException("User does not exist");
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
        return userRepository.findByEmail(email).isPresent();
    }

    public boolean isUsernameRegistered(String username){
        return userRepository.findByUsername(username).isPresent();
    }
}
