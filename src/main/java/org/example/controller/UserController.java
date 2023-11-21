package org.example.controller;

import org.example.exception.BusinessException;
import org.example.exception.BusinessExceptionCode;
import org.example.model.User;
import org.example.service.AccountHistoryService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final AccountHistoryService accountHistoryService;

    @Autowired
    public UserController(UserService userService, AccountHistoryService accountHistoryService) {
        this.userService = userService;
        this.accountHistoryService = accountHistoryService;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<User> saveUser(@RequestBody User user) throws BusinessException {
        User savedUser = this.userService.saveUser(user);

        if(savedUser == null){
            throw new BusinessException(BusinessExceptionCode.INVALID_USER);
        } else{
            return new ResponseEntity<>(savedUser, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/getInfo")
    public ResponseEntity<User> getUser(@RequestParam String email) throws BusinessException {
        User updatedUser = this.userService.getUserByEmail(email);

        if(updatedUser == null){
            throw new BusinessException(BusinessExceptionCode.INVALID_USER);
        } else{
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        }
    }

    @PostMapping(value = "/updateInfo")
    public ResponseEntity<User> updateUser(@RequestBody User user) throws BusinessException {
        User oldUser = this.userService.getUserByEmail(user.getEmail());
        User updatedUser = this.userService.updateUser(user);
        if(updatedUser == null){
            throw new BusinessException(BusinessExceptionCode.INVALID_USER);
        } else{
            this.accountHistoryService.makeNewEntry(oldUser, updatedUser);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        }
    }
}
