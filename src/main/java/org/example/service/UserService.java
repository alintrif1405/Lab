package org.example.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user){
        if(validatePassword(user.getPassword()) && validateEmail(user.getEmail())
                && validateNames(user.getFirstname()) && validateNames(user.getLastname())){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return this.userRepository.save(user);
        }

        return null;
    }

    public User updateUser(User user){
        if(validatePassword(user.getPassword()) && validateEmailOnUpdate(user.getEmail())
                && validateNames(user.getFirstname()) && validateNames(user.getLastname())){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("CatalogPersistence");
            EntityManager entityManager = entityManagerFactory.createEntityManager();

            User temp = this.getUserByEmail(user.getEmail());
            if(temp != null){
                // Begin a transaction
                EntityTransaction transaction = entityManager.getTransaction();
                transaction.begin();
                User updatedUser = entityManager.find(User.class, temp.getId());
                updatedUser.setPassword(user.getPassword());
                updatedUser.setFirstname(user.getFirstname());
                updatedUser.setLastname(user.getLastname());
                updatedUser.setRole(user.getRole());
                updatedUser.setEmail(user.getEmail());
                transaction.commit();
                return updatedUser;
            }
        }

        return null;
    }

    public User getUserByEmail(String email){
        return this.userRepository.findByEmail(email);
    }

    public boolean validateEmail(String mail) {
        if (mail == null)
            return false;

        List<User> users = this.userRepository.findAll();
        for(User user: users){
            if(Objects.equals(user.getEmail(), mail))
                return false;
        }

        Pattern pattern = Pattern.compile("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
        Matcher matcher = pattern.matcher(mail);
        return matcher.find();
    }

    public boolean validateEmailOnUpdate(String mail) {
        if (mail == null)
            return false;

        Pattern pattern = Pattern.compile("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
        Matcher matcher = pattern.matcher(mail);
        return matcher.find();
    }

    public boolean validatePassword(String password) {
        if (password == null)
            return false;
        Pattern pattern = Pattern.compile("^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!])(.{8,})$");
        Matcher matcher = pattern.matcher(password);
        return matcher.find();
    }

    public boolean validateNames(String name) {
        if (name == null)
            return false;
        Pattern pattern = Pattern.compile("^[A-Za-z]+(?:[ -][A-Za-z]+)*$");
        Matcher matcher = pattern.matcher(name);
        return matcher.find();
    }


}