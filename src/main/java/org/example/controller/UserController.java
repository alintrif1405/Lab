package org.example.controller;
import org.example.model.ERole;
import org.example.model.User;
import org.example.service.EmailService;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(value = "/register")
    public ResponseEntity<String> saveUser(@RequestParam String firstname, @RequestParam String lastname,
                                           @RequestParam String email, @RequestParam String password,
                                           @RequestParam ERole role) {
        User userToSave = new User(firstname, lastname, email, password, role);
        User savedUser = this.userService.saveUser(userToSave);

        if (savedUser == null) {
            return new ResponseEntity<>("Invalid user", HttpStatus.OK);
        } else {
            emailService.sendEmailFromTemplate(savedUser.getEmail(),
                    "src/main/java/org/example/service/emailTemplateAccountConfirmation.txt",
                    "UBB Account created", password);
            return new ResponseEntity<>("User successfully created", HttpStatus.OK);
        }

    }
}
