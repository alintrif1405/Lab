package org.example.controller;

import org.example.exception.BusinessException;
import org.example.exception.BusinessExceptionCode;
import org.example.model.User;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> saveUser(@RequestBody User user) throws BusinessException {
        User savedUser = this.userService.saveUser(user);

        if(savedUser == null){
            throw new BusinessException(BusinessExceptionCode.INVALID_USER);
        } else{
            return new ResponseEntity<>(savedUser, HttpStatus.OK);
        }
    }
}
