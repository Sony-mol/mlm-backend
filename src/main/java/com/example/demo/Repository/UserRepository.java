package com.example.demo.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.users.Users;

public interface UserRepository extends JpaRepository<Users, Long> {
    
    Optional<Users> findByEmail(String email);

    Optional<Users> findByReferenceCode(String referenceCode);
    Users getUserByEmail(String email);
	

}


