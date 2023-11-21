package org.example.test.service;

import org.example.exception.BusinessException;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    void saveUser_ValidUser_ReturnsSavedUser() {
        // Mocking
        User user = new User(1, "John", "Doe", "john@example.com", "Pass@123", null);
        List<User> userList = new ArrayList<>(); // Mocked empty list or a list with test data
        when(userRepository.findAll()).thenReturn(userList); // Stubbing the findAll() method

        // Perform saveUser operation - This is where you save the user using the service
        User savedUser = userService.saveUser(user);

        // Assertions and verifications (assuming userRepository.save() is called)
        assertNotNull(savedUser);
        assertEquals(user.getEmail(), savedUser.getEmail());
        verify(userRepository, times(1)).save(user);
        verify(userRepository, times(1)).findAll();
    }


    @Test
    void saveUser_EncryptsPassword() {
        // Mocking
        User user = new User(1, "John", "Doe", "john@example.com", "Pass@123", null);
        String encodedPassword = "EncodedPass@123";
        when(passwordEncoder.encode(user.getPassword())).thenReturn(encodedPassword);
        when(userRepository.save(user)).thenReturn(user);

        // Test
        User savedUser = userService.saveUser(user);

        // Assertion
        assertNotNull(savedUser);
        assertEquals(encodedPassword, passwordEncoder.encode(user.getPassword()));
    }

    @Test
    void saveUser_InvalidUser_ReturnsNull() {
        // Mocking
        User user = new User(); // Invalid user without required fields

        // Test
        User savedUser = userService.saveUser(user);

        // Assertion
        assertNull(savedUser);
        verify(userRepository, never()).save(user);
    }

    @Test
    void updateUser_ValidUser_ReturnsUpdatedUser() {
        // Mocking
        User user = new User(1, "John", "Doe", "john@example.com", "Pass@123", null);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);

        // Test
        User updatedUser = userService.updateUser(user);

        // Assertion
        assertNotNull(updatedUser);
        assertEquals(user.getEmail(), updatedUser.getEmail());
        verify(userRepository, times(1)).findByEmail(user.getEmail());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void updateUser_UserNotFound_ReturnsNull() {
        // Mocking
        User user = new User(1, "John", "Doe", "john@example.com", "Pass@123", null);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(null);

        // Test
        User updatedUser = userService.updateUser(user);

        // Assertion
        assertNull(updatedUser);
        verify(userRepository, times(1)).findByEmail(user.getEmail());
        verify(userRepository, never()).save(user);
    }

    @Test
    void getUserByEmail_ExistingEmail_ReturnsUser() {
        // Mocking
        String email = "john@example.com";
        User user = new User(1, "John", "Doe", email, "Pass@123", null);
        when(userRepository.findByEmail(email)).thenReturn(user);

        // Test
        User foundUser = userService.getUserByEmail(email);

        // Assertion
        assertNotNull(foundUser);
        assertEquals(email, foundUser.getEmail());
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void getUserByEmail_NonExistingEmail_ReturnsNull() {
        // Mocking
        String email = "nonexistent@example.com";
        when(userRepository.findByEmail(email)).thenReturn(null);

        // Test
        User foundUser = userService.getUserByEmail(email);

        // Assertion
        assertNull(foundUser);
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void validateEmail_ValidEmail_ReturnsTrue() {
        // Test
        boolean isValid = userService.validateEmail("john@example.com");

        // Assertion
        assertTrue(isValid);
    }

    @Test
    void validateEmail_InvalidEmail_ReturnsFalse() {
        // Test
        boolean isValid = userService.validateEmail("invalidemail");

        // Assertion
        assertFalse(isValid);
    }

    @Test
    void validatePassword_ValidPassword_ReturnsTrue() {
        // Test
        boolean isValid = userService.validatePassword("Pass@123");

        // Assertion
        assertTrue(isValid);
    }

    @Test
    void validatePassword_InvalidPassword_ReturnsFalse() {
        // Test
        boolean isValid = userService.validatePassword("invalidpassword");

        // Assertion
        assertFalse(isValid);
    }

    @Test
    void validateNames_ValidName_ReturnsTrue() {
        // Test
        boolean isValid = userService.validateNames("John");

        // Assertion
        assertTrue(isValid);
    }

    @Test
    void validateNames_InvalidName_ReturnsFalse() {
        // Test
        boolean isValid = userService.validateNames("123");

        // Assertion
        assertFalse(isValid);
    }

    @Test
    void validateEmailOnUpdate_ValidEmail_ReturnsTrue() {
        // Test
        boolean isValid = userService.validateEmailOnUpdate("john@example.com");

        // Assertion
        assertTrue(isValid);
    }

    @Test
    void validateEmailOnUpdate_InvalidEmail_ReturnsFalse() {
        // Test
        boolean isValid = userService.validateEmailOnUpdate("invalidemail");

        // Assertion
        assertFalse(isValid);
    }
}
