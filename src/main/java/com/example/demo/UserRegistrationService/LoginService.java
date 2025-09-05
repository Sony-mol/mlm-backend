package com.example.demo.UserRegistrationService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.JwtUtil.JwtService;
import com.example.demo.Repository.UserRepository;
import com.example.demo.users.Users;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public String login(String email, String rawPassword) {
        Optional<Users> optionalUser = userRepository.findByEmail(email);

        if (!optionalUser.isPresent()) {
            throw new RuntimeException("User not found with email: " + email);
        }

        Users user = optionalUser.get();

        System.out.println("Raw password entered: " + rawPassword);
        System.out.println("Password stored in DB (hashed): " + user.getPassword());


        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        // ✅ Generate JWT token if login success
        return jwtService.generateToken(user);
    }
    public Optional<Users> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
