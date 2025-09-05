package com.example.demo.UserRegistrationController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.JwtUtil.JwtService;
import com.example.demo.Repository.ItemRepository;
import com.example.demo.UserRegistrationService.ForgotPasswordService;
import com.example.demo.UserRegistrationService.LoginService;
import com.example.demo.UserRegistrationService.UserRegistrationService;
import com.example.demo.dto.ItemRequest;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.items.Item;
import com.example.demo.users.Users;



@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRegistrationService registrationService;

    @Autowired
    private LoginService loginService;
    
    @Autowired
    private JwtService jwtService;
     @Autowired
     private ForgotPasswordService passwordService;
     
     @Autowired
     private ItemRepository itemRepository;
    
    @GetMapping("/test")
    public ResponseEntity<?> testEndpoint() {
        System.out.println("=== TEST ENDPOINT CALLED ===");
        return ResponseEntity.ok(Collections.singletonMap("message", "Test endpoint working!"));
    }

    @GetMapping("/ping")
    public ResponseEntity<?> pingEndpoint() {
        System.out.println("=== PING ENDPOINT CALLED ===");
        return ResponseEntity.ok(Collections.singletonMap("message", "Pong!"));
    }

    @GetMapping("/debug")
    public ResponseEntity<?> debugEndpoint() {
        System.out.println("=== DEBUG ENDPOINT CALLED ===");
        Map<String, Object> debugInfo = new HashMap<>();
        debugInfo.put("message", "Debug endpoint working!");
        debugInfo.put("timestamp", System.currentTimeMillis());
        debugInfo.put("endpoint", "/api/users/debug");
        return ResponseEntity.ok(debugInfo);
    }

    @PostMapping("/test-register")
    public ResponseEntity<?> testRegisterEndpoint(@RequestBody RegisterRequest request) {
        System.out.println("=== TEST REGISTER ENDPOINT CALLED ===");
        System.out.println("Request received: " + request);
        
        try {
            // Just validate the request without sending email
            if (request.getName() == null || request.getName().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Name is required"));
            }
            if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Email is required"));
            }
            if (request.getPhoneNumber() == null || request.getPhoneNumber().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Phone number is required"));
            }
            if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Password is required"));
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Test registration validation successful!");
            response.put("name", request.getName());
            response.put("email", request.getEmail());
            response.put("phoneNumber", request.getPhoneNumber());
            response.put("status", "VALIDATED");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("Error in test register endpoint: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body(Collections.singletonMap("error", "Test registration failed: " + e.getMessage()));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) {
        System.out.println("=== REGISTER ENDPOINT CALLED ===");
        System.out.println("Request received: " + request);
        
        try {
            // Validate request
            if (request == null) {
                return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Request body cannot be null"));
            }
            
            if (request.getName() == null || request.getName().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Name is required"));
            }
            if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Email is required"));
            }
            if (request.getPhoneNumber() == null || request.getPhoneNumber().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Phone number is required"));
            }
            if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Password is required"));
            }
            
            Users user = new Users();
            user.setName(request.getName());
            user.setEmail(request.getEmail());
            user.setPhoneNumber(request.getPhoneNumber());
            user.setPassword(request.getPassword());
            user.setReferredByCode(request.getReferredByCode());

            System.out.println("User object created: " + user);
            
            String message = registrationService.registerOrSendOtp(user);
            System.out.println("Registration service response: " + message);
            
            return ResponseEntity.ok(Collections.singletonMap("message", message));
        } catch (Exception e) {
            System.err.println("Error in register endpoint: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body(Collections.singletonMap("error", "Internal server error: " + e.getMessage()));
        }
    }


    // ✅ Step 2: Verify OTP & register user in DB
    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String otp = request.get("otp");

        if (email == null || otp == null) {
            return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("error", "Email and OTP are required"));
        }

        Users verifiedUser = registrationService.verifyOtpAndRegister(email, otp);

        if (verifiedUser != null) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "OTP verified successfully. User registered.");
//            response.put("userId", verifiedUser.getId());
//            response.put("name", verifiedUser.getName());
//            response.put("email", verifiedUser.getEmail());
//            response.put("phoneNumber", verifiedUser.getPhoneNumber());
//            response.put("status", verifiedUser.getStatus());
//            
//            response.put("walletBalance", verifiedUser.getWallet() != null ? verifiedUser.getWallet().getWalletBalance() : 0);
//            response.put("referralCount", verifiedUser.getReferralCount());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("error", "Invalid OTP or user not found."));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
           
            String token = loginService.login(request.getEmail(), request.getPassword());

            
            Optional<Users> optionalUser = loginService.findByEmail(request.getEmail());
            if (!optionalUser.isPresent()) {
                return ResponseEntity.status(404)
                        .body(Collections.singletonMap("error", "User not found"));
            }

            Users user = optionalUser.get();

            
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("userId", user.getId());
          response.put("name", user.getName());
          response.put("email", user.getEmail());
          response.put("phoneNumber", user.getPhoneNumber());
          response.put("status", user.getStatus());
           
           response.put("refer", user.getReferenceCode());
          response.put("walletBalance", user.getWallet() != null ? user.getWallet().getWalletBalance() : 0);
          response.put("referralCount", user.getReferralCount());
          

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            return ResponseEntity.status(401)
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
    }
    
    @GetMapping("/decode")
    public ResponseEntity<?> decodeToken(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.replace("Bearer ", "");

            // ✅ Use the service method to decode token to Map
            Map<String, Object> response = jwtService.decodeToken(token);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(401)
                    .body(Collections.singletonMap("error", "Invalid or expired token"));
        }
        
        
    }
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        if (email == null || email.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("error", "Email is required"));
        }

        try {
            String message = passwordService.sendForgotPasswordOtp(email);
            return ResponseEntity.ok(Collections.singletonMap("message", message));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404)
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
    }
    
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String otp = request.get("otp");
        String newPassword = request.get("newPassword");

        if (email == null || otp == null || newPassword == null) {
            return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("error", "Email, OTP and newPassword are required"));
        }

        try {
            String message = passwordService.resetPassword(email, otp, newPassword);
            return ResponseEntity.ok(Collections.singletonMap("message", message));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400)
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
  }
    
   
    @PatchMapping("/update")
    public ResponseEntity<?> updateItem(@RequestBody ItemRequest request) {
        try {
            Item item = itemRepository.findByUserPhoneNumber(request.getUserPhoneNumber())
                    .orElseThrow(() -> new RuntimeException(
                        "Item not found for phone number: " + request.getUserPhoneNumber()
                    ));

            // Initialize item names list safely
            if (request.getItemNames() != null && !request.getItemNames().isEmpty()) {
                List<String> existingItems = item.getItemNamesList();
                if (existingItems == null) {
                    existingItems = new ArrayList<>();
                }
                existingItems.addAll(request.getItemNames()); // append new items
                item.setItemNamesList(existingItems);
                System.out.println("Updated itemNamesList: " + existingItems);
            }

            // Throw error if status is already SUCCESS
            if ("SUCCESS".equals(item.getStatus())) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Cannot update item: status is already SUCCESS for phone number " 
                              + request.getUserPhoneNumber());
            }

           
            item.setStatus("SUCCESS");
            item.setBalance(1000.00);
            System.out.println("Status set to SUCCESS for phone number: " + request.getUserPhoneNumber());

            Item savedItem = itemRepository.save(item);
            return ResponseEntity.ok(savedItem);

        } catch (Exception e) {
            System.err.println("Error updating item for phone number " + request.getUserPhoneNumber());
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating item: " + e.getMessage());
        }
    }

}
