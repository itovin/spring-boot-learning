package springbootlearning.ecommerce.mappers;

import lombok.Getter;
import org.springframework.stereotype.Component;
import springbootlearning.ecommerce.dtos.RegisterUserDto;
import springbootlearning.ecommerce.dtos.UserDto;
import springbootlearning.ecommerce.entities.User;

@Getter
@Component
public class UserMapper {

    public UserDto userToUserDto(User user){
        if(user == null)
            return null;
        String fullName = user.getFirstName() + " " + user.getLastName();
        return new UserDto(user.getId(), user.getUsername(), fullName, user.getEmail());
    }

    public User newUserDtoToUser(RegisterUserDto newUser){
        if(newUser == null)
            return null;
        User user =  new User(newUser.getFirstName(), newUser.getLastName(), newUser.getEmail(), newUser.getUsername(), newUser.getPassword());
        if(newUser.getAddress() != null)
            user.addAddress(newUser.getAddress());
        return user;
    }
}
