package org.example.test.controller;

import org.example.controller.UserController;
import org.example.model.ERole;
import org.example.model.User;
import org.example.service.AccountHistoryService;
import org.example.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private AccountHistoryService accountHistoryService;

    @InjectMocks
    private UserController userController;

    @Test
    void saveUser_ValidUser_ReturnsSavedUser() {
        // Mocking
        User user = new User(); // Create a valid user
        when(userService.saveUser(user)).thenReturn(user);

        // Test
        ResponseEntity<User> response = userController.saveUser(user);

        // Assertion
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
        verify(userService, times(1)).saveUser(user);
    }

    @Test
    void getUser_ValidEmail_ReturnsUser() {
        // Mocking
        String email = "test@example.com"; // Valid email
        User user = new User(); // Create a user
        when(userService.getUserByEmail(email)).thenReturn(user);

        // Test
        ResponseEntity<User> response = userController.getUser(email);

        // Assertion
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
        verify(userService, times(1)).getUserByEmail(email);
    }

    @Test
    void updateUser_ValidUser_ReturnsOK() {
        // Mocking
        User user = new User();
        user.setLastname("Doe");
        user.setFirstname("John");
        user.setEmail("john@example.com");
        user.setPassword("password");
        user.setRole(ERole.STD); // Set a role for the user

        when(userService.updateUser(any(User.class))).thenReturn(user);

        // Test
        ResponseEntity<User> response = userController.updateUser(user.getEmail(), user.getPassword());

        // Assertion
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
        verify(userService, times(1)).updateUser(any(User.class));
    }
}
