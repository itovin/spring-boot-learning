package springbootlearning.ecommerce.service;

import org.junit.jupiter.api.Test;
import springbootlearning.ecommerce.entities.User;
import springbootlearning.ecommerce.entities.repositories.UserRepository;
import springbootlearning.ecommerce.entities.services.UserService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Test
    void testIsEmailRegisteredShouldReturnTrue(){
        UserRepository userRepository = mock(UserRepository.class);
        UserService userService = new UserService(userRepository);
        User mockUser = new User("Sample", "Test", "sampletest@gmail.com", "sample", "test123");
        when(userRepository.findByEmail("sampletest@gmail.com")).thenReturn(Optional.of(mockUser));

        boolean isExisting = userService.isEmailRegistered("sampletest@gmail.com");
        assertTrue(isExisting);

    }

    @Test
    void testIsEmailRegisteredShouldReturnFalse() {
        UserRepository userRepository = mock(UserRepository.class);
        UserService userService = new UserService(userRepository);
        User mockUser = new User("Sample", "Test", "sampletest@gmail.com", "sample", "test123");
        when(userRepository.findByEmail("sampletest@gmail.com")).thenReturn(Optional.of(mockUser));

        boolean isExisting = userService.isEmailRegistered("11sampletest@gmail.com");
        assertFalse(isExisting);
    }

    @Test
    void testIsUsernameRegisteredShouldReturnTrue(){
        UserRepository userRepository = mock(UserRepository.class);
        UserService userService = new UserService(userRepository);
        User mockUser = new User("Sample", "Test", "sampletest@gmail.com", "sample1", "test123");
        when(userRepository.findByUsername("sample1")).thenReturn(Optional.of(mockUser));

        boolean isExisting = userService.isUsernameRegistered("sample1");
        assertTrue(isExisting);
    }

    @Test
    void testIsUsernameRegisteredShouldReturnFalse(){
        UserRepository userRepository = mock(UserRepository.class);
        UserService userService = new UserService(userRepository);
        User mockUser = new User("Sample", "Test", "sampletest@gmail.com", "sample1", "test123");
        when(userRepository.findByUsername("sample1")).thenReturn(Optional.of(mockUser));

        boolean isExisting = userService.isUsernameRegistered("sample2");
        assertFalse(isExisting);
    }
}
