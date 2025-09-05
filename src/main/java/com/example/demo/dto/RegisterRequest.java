package com.example.demo.dto;



import lombok.Data;

@Data

public class RegisterRequest  {

    private String name;
	
    private String email;
	
    private String phoneNumber;
	
    private String password;
    private String referredByCode;
}
