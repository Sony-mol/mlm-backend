package com.example.demo.items;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_phone_number", nullable = false, length = 15)
    private String userPhoneNumber;

    @Convert(converter = StringListConverter.class)
    @Column(name = "item_names_json", columnDefinition = "TEXT")
    private List<String> itemNamesList = new ArrayList<>();
    
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name = "status", nullable = false, length = 20)
    private String status = "PENDING";  // default value

    @Column(name = "balance", nullable = false)
    private Double balance = 0.0;
    
    @PrePersist
    public void prePersist() {
    	  if (itemNamesList == null) {
              itemNamesList = new ArrayList<>();
          }
		
	}
    
}
