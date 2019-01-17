package com.telran.controller;

import com.telran.dto.RegistrationRequest;
import com.telran.entity.User;
import com.telran.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public void register(@RequestBody RegistrationRequest registrationRequest) {
        if (userRepository.existsByUsername(registrationRequest.getUsername())) {
            throw new RuntimeException("Such user already exists");
        }

        if (!registrationRequest.getPassword().equals(registrationRequest.getConfirmPassword())) {
            throw new RuntimeException("Passwords do not match");
        }

        User user = User.builder()
                .username(registrationRequest.getUsername())
                .password(registrationRequest.getPassword())
                .build();

        userRepository.save(user);
    }
}
