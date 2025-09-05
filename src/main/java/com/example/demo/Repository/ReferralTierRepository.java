package com.example.demo.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.referralTier.ReferralTier;
import com.example.demo.referralTier.TierType;

public interface ReferralTierRepository extends JpaRepository<ReferralTier, Long> {
    
	Optional<ReferralTier> findByTierName(TierType tierName);
	Optional<ReferralTier> findByReferrerPhoneNumber(String phoneNumber);
}
