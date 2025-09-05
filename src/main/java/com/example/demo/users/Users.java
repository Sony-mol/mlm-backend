package com.example.demo.users;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.example.demo.items.Item;
import com.example.demo.wallet.Wallet;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

@Entity
@Table(name = "users")
public class Users {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "phone_number", nullable = false, unique = true, length = 15)
    private String phoneNumber;
    @Column(nullable = false)
    private String password;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal deposit = BigDecimal.valueOf(1000);

    @Column(name = "reference_code", nullable = false, unique = true, length = 20)
    private String referenceCode;

    @Column(name = "referred_by_code", length = 20)
    private String referredByCode;
    
    @Column(nullable = false, length = 20)
    private String status = "PENDING";

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Wallet wallet;

    @Transient   
    private List<Item> items = new ArrayList<>();
    @Transient
    private int referralCount;
    @Transient
    private String otp;
    @Transient
    private List<String> itemNames;
    
   @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;
}