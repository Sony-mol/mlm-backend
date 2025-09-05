package com.example.demo.referralTier;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "referral_tiers")
public class ReferralTier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "referrer_phone_number", nullable = false, length = 20)
    private String referrerPhoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TierType tierName;

    @Column(nullable = false)
    private int requiredReferrals;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal bonusAmount;

       @Lob
    @Column(name = "items", nullable = false)
    private String items;

    
    public void setItemsList(List<String> itemsList) {
        this.items = String.join(",", itemsList);
    }


    public List<String> getItemsList() {
        return Arrays.stream(this.items.split(","))
                     .map(String::trim)
                     .collect(Collectors.toList());
    }
}
