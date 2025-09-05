package com.example.demo.Repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.admin.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
   
    Admin findByEmail(String email);
}

