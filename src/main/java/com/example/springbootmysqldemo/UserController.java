package com.example.springbootmysqldemo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserRepository repository;

    UserController(UserRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/login")                                                            //not in use
    public ResponseEntity<Map<String, String>> login(@RequestBody User loginUser) {
        Optional<User> optionalUser = repository.findByEmail(loginUser.getEmail());
        Map<String, String> response = new HashMap<>();
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (loginUser.getPassword().equals(user.getPassword())) {
                // Login erfolgreich
                response.put("status", "success");
                response.put("message", "Login erfolgreich");
                return ResponseEntity.ok(response);
            }
        }
        // Login fehlgeschlagen
        response.put("status", "error");
        response.put("message", "Nutzername oder Passwort ist nicht korrekt");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getUserData")
    public ResponseEntity<Map<String, String>> getUserData(@RequestParam String email) {
        Optional<User> optionalUser = repository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Map<String, String> userData = new HashMap<>();
            userData.put("password", user.getPassword());
            userData.put("userId", String.valueOf(user.getUserid()));
            userData.put("name", user.getName());
            return ResponseEntity.ok(userData);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", "Benutzer nicht gefunden"));
        }
    }

    @GetMapping("/getHashedPassword")                                                   //for login - password only
    public ResponseEntity<String> getHashedPassword(@RequestParam String email) {
        Optional<User> optionalUser = repository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return ResponseEntity.ok(user.getPassword());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Benutzer nicht gefunden");
        }
    }

    @GetMapping("/users")                                                                          //not in use
    public ResponseEntity<Object> getUserNamesByEmails(@RequestParam List<String> emails) {
        Map<String, Object> response = new HashMap<>();
        try {
            Map<String, String> userNames = new HashMap<>();
            for (String email : emails) {
                Optional<User> optionalUser = repository.findByEmail(email);
                if (optionalUser.isPresent()) {
                    User user = optionalUser.get();
                    userNames.put(email, user.getName());
                }
            }
            response.put("userNames", userNames);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }



}