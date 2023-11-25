package org.example.controller;

import org.example.exception.BusinessException;
import org.example.exception.BusinessExceptionCode;
import org.example.model.ERole;
import org.example.model.User;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<String> saveUser(@RequestParam String firstname, @RequestParam String lastname,
                                         @RequestParam String email, @RequestParam String password,
                                         @RequestParam ERole role) throws BusinessException {
        User userToSave = new User(firstname, lastname, email, password, role);
        User savedUser = this.userService.saveUser(userToSave);

        if(savedUser == null){
            throw new BusinessException(BusinessExceptionCode.INVALID_USER);
        } else{
            return new ResponseEntity<>("User successfully created", HttpStatus.OK);
        }
    }
}
