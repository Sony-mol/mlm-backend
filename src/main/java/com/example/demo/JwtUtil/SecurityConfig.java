package com.example.demo.JwtUtil;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

@Configuration
public class SecurityConfig {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Bean
     BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        logger.info("Configuring Spring Security...");
        
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/users/debug").permitAll()
                .requestMatchers("/api/users/ping").permitAll()
                .requestMatchers("/api/users/test").permitAll()
                .requestMatchers("/api/users/test-register").permitAll()
               .requestMatchers("/api/users/register").permitAll()
                 .requestMatchers("/api/users/verify-otp").permitAll()
                .requestMatchers("/api/users/login").permitAll()
                .requestMatchers("/api/users/decode").permitAll()
                .requestMatchers("/api/users/forgot-password").permitAll()
                .requestMatchers("/api/users/reset-password").permitAll()
                .requestMatchers("/api/users/update").permitAll()
                .requestMatchers("/health").permitAll()
                .requestMatchers("/").permitAll()
                .requestMatchers("/v3/api-docs/**").permitAll()
                .requestMatchers("/swagger-ui/**").permitAll()
                .requestMatchers("/swagger-ui.html").permitAll()
                .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults()); 
        
        logger.info("Spring Security configuration completed");
        return http.build();
   }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        logger.info("Configuring CORS...");
        
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        
        logger.info("CORS configuration completed");
        return source;
    }
}
