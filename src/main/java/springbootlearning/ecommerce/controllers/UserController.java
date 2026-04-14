package springbootlearning.ecommerce.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import springbootlearning.ecommerce.dtos.RegisterUserDto;
import springbootlearning.ecommerce.dtos.UserDto;
import springbootlearning.ecommerce.entities.User;
import springbootlearning.ecommerce.exceptions.UserNotFoundException;
import springbootlearning.ecommerce.mappers.UserMapper;
import springbootlearning.ecommerce.services.UserService;

@AllArgsConstructor
@RestController
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody RegisterUserDto newUserDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.registerUser(newUserDto));
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> me(){
        String usernameOrEmail = String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        User user = userService.getUser(usernameOrEmail).orElseThrow(() -> new UserNotFoundException("User not found"));
        return ResponseEntity.ok(userMapper.userToUserDto(user));
    }

//    @GetMapping("/login")
//    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto){
//        return ResponseEntity.ok(userService.login(loginDto));
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
