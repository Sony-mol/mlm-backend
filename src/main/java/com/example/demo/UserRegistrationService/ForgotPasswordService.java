package com.example.demo.UserRegistrationService;

import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.Repository.UserRepository;
import com.example.demo.users.Users;

@Service
public class ForgotPasswordService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // In-memory OTP store
    private Map<String, String> otpStore = new ConcurrentHashMap<>();

    // ✅ Step 1: Send OTP for forgot password
    public String sendForgotPasswordOtp(String email) {
        Optional<Users> optionalUser = userRepository.findByEmail(email);
        if (!optionalUser.isPresent()) {
            throw new RuntimeException("User not found with email: " + email);
        }

        // Generate OTP
        String otp = String.format("%06d", new Random().nextInt(999999));
        otpStore.put(email, otp); 

        // Send OTP via email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("OTP for Password Reset");
        message.setText("Dear user,\n\nYour OTP for password reset is: " + otp +
                        "\n\nIt will expire in 5 minutes.");
        mailSender.send(message);

        return "OTP sent to your email.";
    }

    // ✅ Step 2: Reset password using OTP
    public String resetPassword(String email, String otp, String newPassword) {
        String storedOtp = otpStore.get(email);

        if (storedOtp == null || !storedOtp.equals(otp)) {
            throw new RuntimeException("Invalid OTP");
        }

        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Encode new password and save
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        // Remove OTP after successful reset
        otpStore.remove(email);

        return "Password reset successfully.";
    }
}
