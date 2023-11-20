package org.example.controller;

import org.example.exception.BusinessException;
import org.example.exception.BusinessExceptionCode;
import org.example.model.ERole;
import org.example.model.User;
import org.example.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private MockMvc mockMvc;

    @Test
    void saveUserValid() throws Exception {
        User validUserToSave = new User(1, "Radu", "Valentin", "user2@example.com", "Yth67#890afa", ERole.STD);

        doReturn(validUserToSave).when(userService).saveUser(any(User.class));

        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"lastname\":\"Radu\",\"firstname\":\"Valentin\"," +
                                "\"email\":\"user2@example.com\",\"password\":\"Yth67#890afa\",\"role\":\"STD\"}"))
                .andExpect(status().isOk());

        verify(userService, times(1)).saveUser(any(User.class));
    }

    @Test
    void saveUserInvalid() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        try {
            mockMvc.perform(post("/users/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"lastname\":\"Radu\",\"firstname\":\"Valentin\"," +
                            "\"email\":\"user1@example.com\",\"password\":\"12345\",\"role\":\"STD\"}"));

            fail("Expected BusinessException, but no exception was thrown");
        } catch (Exception e) {
            assert true;
        }

    }

}
