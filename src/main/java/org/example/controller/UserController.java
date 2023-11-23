package org.example.controller;

import org.example.exception.BusinessException;
import org.example.exception.BusinessExceptionCode;
import org.example.model.User;
import org.example.service.EmailService;
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
    private final EmailService emailService;

    @Autowired
    public UserController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @PostMapping("register")
    public ResponseEntity<User> saveUser(@RequestBody User user) throws BusinessException {
        String initialPass = user.getPassword();
        User savedUser = this.userService.saveUser(user);
        if (savedUser == null) {
            throw new BusinessException(BusinessExceptionCode.INVALID_USER);
        } else {
            emailService.sendEmailFromTemplate(savedUser.getEmail(),
                    "src/main/java/org/example/service/emailTemplateAccountConfirmation.txt",
                    "UBB Account created", initialPass);
            return new ResponseEntity<>(savedUser, HttpStatus.OK);
        }
    }
}
