package com.example.demo.referrals;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "referrals")
public class Referral {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    // Referred user's phone number
//    @Column(name = "referred_phone_number", nullable = false, length = 20)
//    private String referredPhoneNumber;

    // Referrer's phone number
    @Column(name = "referrer_phone_number", nullable = false, length = 20)
    private String referrerPhoneNumber;

    @Column(name = "bonus_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal bonusAmount = BigDecimal.valueOf(250);

    @CreationTimestamp
    private Timestamp createdAt;
}
