package com.stripe.Users;

import io.swagger.models.Response;
import lombok.extern.slf4j.Slf4j;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import com.google.common.base.Throwables;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordManager passwordManager;

    @PostMapping("")
    public ResponseEntity<User> createUser(@RequestBody User userBody) {
        String salt;
        try {
            salt = passwordManager.getNewSalt();
            userBody.setPassword(passwordManager.getEncryptedPassword(userBody.getPassword(), salt));
            userBody.setSalt(salt);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            String errorMessage = String.format("Encountered exception encrypting user's password {}", ex.toString());
            log.error(errorMessage);
            throw new ResponseStatusException(500, errorMessage, ex);
        }
        return saveUser(userBody);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable long userId) {
        try {
            return ResponseEntity.ok(userRepository.findById(userId).get());
        } catch (NoSuchElementException ex) {
            String errorMessage = String.format("Couldn't find user for userId %s", userId);
            log.error(errorMessage);
            return new ResponseEntity(errorMessage, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable long userId) {
        userRepository.deleteById(userId);
        return ResponseEntity.status(204).body("Successfully deleted user");
    }

    public ResponseEntity<User> saveUser(User userBody) {
        try {
            User savedUser = userRepository.save(userBody);
            return ResponseEntity.ok(savedUser);
        } catch (Exception ex) {
            Throwable baseException = Throwables.getRootCause(ex);
            if (baseException instanceof PSQLException && (((SQLException) baseException)).getSQLState().equals("23505")) {
                log.error("Creating user failed due to username already existing");
                return new ResponseEntity("Username already exists", HttpStatus.CONFLICT);
            } else {
                String errorMessage = String.format("Encountered exception saving user {}", ex.toString());
                log.error(errorMessage);
                return ResponseEntity.internalServerError().build();
            }
        }
    }





}
