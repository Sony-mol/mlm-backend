package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.wallet.Wallet;
import com.example.demo.wallet_transactions.WalletTransaction;

public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, Long> {
	 boolean existsByWalletAndRemark(Wallet wallet, String remark);

}

