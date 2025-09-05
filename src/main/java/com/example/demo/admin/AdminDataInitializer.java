package com.example.demo.admin;


import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.demo.Repository.AdminRepository;


@Component
public class AdminDataInitializer implements CommandLineRunner {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public void run(String... args) throws Exception {

        if (adminRepository.count() == 0) {
            Admin admin = new Admin();
            admin.setName("RahulKumar");
            admin.setEmail("Rahulmekala22@@example.com");
            admin.setWalletBalance(BigDecimal.ZERO);
            
            adminRepository.save(admin);
            System.out.println("Admin data inserted at runtime: " + admin.getCreatedAt());
        }
    }
}
