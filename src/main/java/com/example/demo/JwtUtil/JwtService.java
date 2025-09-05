package com.example.demo.JwtUtil;



import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.demo.users.Users;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

      private final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

     private static final long EXPIRATION_TIME = 1000 * 60 * 60;

   
    public String generateToken(Users user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("name", user.getName());
        claims.put("email", user.getEmail());
        claims.put("phoneNumber", user.getPhoneNumber());
        claims.put("status", user.getStatus());
         claims.put("walletBalance", user.getWallet() != null ? user.getWallet().getWalletBalance() : 0);
        claims.put("referralCount", user.getReferralCount());
        claims.put("depositAmount", user.getDeposit());
        claims.put("refer", user.getReferenceCode());

        Date now = new Date();
        Date expiryDate = new Date(System.currentTimeMillis() + EXPIRATION_TIME);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail())  // subject = email
                .setIssuedAt(now)
                .setExpiration(expiryDate)   // ✅ 1 hour expiry
                .signWith(SECRET_KEY)
                .compact();
    }

    // ✅ Extract all claims
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // ✅ Validate token
    public boolean validateToken(String token, String email) {
        final String username = extractAllClaims(token).getSubject();
        return (username.equals(email) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    public Map<String, Object> decodeToken(String token) {
        Claims claims = extractAllClaims(token);
        Map<String, Object> response = new HashMap<>();
        response.put("id", claims.get("id"));
        response.put("name", claims.get("name"));
        response.put("email", claims.getSubject()); // subject = email
        response.put("phoneNumber", claims.get("phoneNumber"));
      
        response.put("status", claims.get("status"));
        response.put("walletBalance", claims.get("walletBalance"));
        response.put("referralCount", claims.get("referralCount"));
        response.put("depositAmount", claims.get("depositAmount"));
        response.put("refer", claims.get("refer"));
        return response;
    }
    
}


