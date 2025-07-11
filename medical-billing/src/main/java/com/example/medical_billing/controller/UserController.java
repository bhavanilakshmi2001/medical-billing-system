package com.example.medical_billing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.example.medical_billing.model.User;
import com.example.medical_billing.repository.UserRepository;

@RestController
@RequestMapping("/user")

public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginUser) {
        User user = userRepository.findByEmail(loginUser.getEmail());

        if (user != null) {
            boolean isPasswordMatch = passwordEncoder.matches(loginUser.getPassword(), user.getPassword());
      if (isPasswordMatch) {
            return ResponseEntity.ok("Login Successful...");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials..");
        }}else{
             return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User Not found..");
        }
       
    }
}

