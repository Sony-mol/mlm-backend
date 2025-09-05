package com.example.demo.admin;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "admins")
@Data
@NoArgsConstructor
public class Admin {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(nullable = false, length = 100)
	    private String name;

	    @Column(nullable = false, unique = true, length = 150)
	    private String email;

	    @Column(name = "wallet_balance", nullable = false, precision = 12, scale = 2)
	    private BigDecimal walletBalance = BigDecimal.ZERO;

	    @CreationTimestamp
	    private Timestamp createdAt;

	    @UpdateTimestamp
	    private Timestamp updatedAt;
	    }
