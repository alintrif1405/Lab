package org.example.controller;

import org.example.model.ERole;
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

//    @PostMapping(value = "/register")
//    public ResponseEntity<User> saveUser(@RequestBody User user) throws BusinessException {
//        User savedUser = this.userService.saveUser(user);
//
//        if(savedUser == null){
//            throw new BusinessException(BusinessExceptionCode.INVALID_USER);
//        } else{
//            return new ResponseEntity<>(savedUser, HttpStatus.OK);
//        }
//    }

    @GetMapping(value = "/getInfo")
    public ResponseEntity<User> getUser(@RequestParam String email){
        User updatedUser = this.userService.getUserByEmail(email);

        if(updatedUser == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else{
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        }
    }

    @PostMapping(value = "/updateInfo", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<User> updateUser(@RequestParam String lastname,
                                           @RequestParam String firstname,
                                           @RequestParam String email,
                                           @RequestParam String password,
                                           @RequestParam String role){
        User user = new User();
        user.setLastname(lastname);
        user.setFirstname(firstname);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(ERole.valueOf(role));
        User oldUser = this.userService.getUserByEmail(user.getEmail());
        User updatedUser = this.userService.updateUser(user);
        if(updatedUser == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else{
            this.accountHistoryService.makeNewEntry(oldUser, updatedUser);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        }
    }
}
