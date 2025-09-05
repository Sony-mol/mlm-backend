package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.referrals.Referral;

public interface ReferralRepository extends JpaRepository<Referral, Long> {
	 int countByReferrerPhoneNumber(String phoneNumber);
}


