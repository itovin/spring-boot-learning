package springbootlearning.ecommerce.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springbootlearning.ecommerce.dtos.LoginDto;
import springbootlearning.ecommerce.dtos.RegisterUserDto;
import springbootlearning.ecommerce.dtos.UserDto;
import springbootlearning.ecommerce.entities.User;
import springbootlearning.ecommerce.exceptions.EmailAddressAlreadyRegisteredException;
import springbootlearning.ecommerce.exceptions.LoginFailedException;
import springbootlearning.ecommerce.exceptions.UsernameAlreadyRegisteredException;
import springbootlearning.ecommerce.mappers.UserMapper;
import springbootlearning.ecommerce.services.UserService;

@AllArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody RegisterUserDto newUserDto){
        return ResponseEntity.ok(userService.registerUser(newUserDto));
    }

//    @GetMapping("/login")
//    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto){
//        User user = userService.getUser(loginDto.getUsernameOrEmail());
//        if(!loginDto.getPassword().equals(user.getPassword()))
//            throw new LoginFailedException("Login failed. Invalid username or password");
//        return ResponseEntity.ok("Login successful");
//    }

//    @GetMapping("/{userId}")
//    public ResponseEntity<?> userHome(@PathVariable(name = "userId") Long userId){
//        User user = userService.getUserById(userId);
//        if(user == null)
//            return ResponseEntity.notFound().build();
//        UserDto userDto = userMapper.userToUserDto(user);
//        return ResponseEntity.ok(userDto);
//    }
}
