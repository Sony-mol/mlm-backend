package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.wallet.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
	 Wallet findByUserPhoneNumber(String phoneNumber);

}