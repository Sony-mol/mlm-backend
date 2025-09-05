package com.example.demo.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.items.Item;


public interface ItemRepository extends JpaRepository<Item, Long> {
//	 List<Item> findByUserPhoneNumber(String userPhoneNumber);
	Optional<Item> findByUserPhoneNumber(String userPhoneNumber);

}

