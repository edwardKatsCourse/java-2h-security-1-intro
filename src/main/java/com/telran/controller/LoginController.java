package com.telran.controller;

import com.telran.dto.LoginRequest;
import com.telran.dto.LoginResponse;
import com.telran.dto.UsernameResponse;
import com.telran.entity.User;
import com.telran.entity.UserSession;
import com.telran.repository.UserRepository;
import com.telran.repository.UserSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@RestController
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserSessionRepository userSessionRepository;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        User user = userRepository.findByUsernameAndPassword(
                request.getUsername(),
                request.getPassword()
        );

        if (user == null) {
            throw new RuntimeException("Incorrect username or password");
        }

        //username and password are correct!!
        UserSession userSession = UserSession.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .isValid(true)
                .build();

        userSessionRepository.save(userSession);

        return new LoginResponse(userSession.getToken());
    }

    @GetMapping("/test")
    public UsernameResponse testMethod(@RequestHeader("TelRan") String token) {
        UserSession userSession = userSessionRepository.findByTokenAndIsValidTrue(token);


        System.out.println("LoginController -> test method");

        return new UsernameResponse(userSession.getUser().getUsername());
    }


    @PostMapping("/logout")
    public void logout(HttpServletRequest request,
                       @RequestHeader("TelRan") String token) {
//        request.getHeader("TelRan");

        UserSession userSession = userSessionRepository.findByTokenAndIsValidTrue(token);
        userSession.setIsValid(false);
        userSessionRepository.save(userSession);
    }






    public static void main(String[] args) {
        Set<String> tokens = new HashSet<>();
        for (int i = 0; i < 10000; i++) {
            String token = UUID.randomUUID().toString();
            System.out.println(token);
            tokens.add(token);
        }

        System.out.println(tokens.size());
    }
}
