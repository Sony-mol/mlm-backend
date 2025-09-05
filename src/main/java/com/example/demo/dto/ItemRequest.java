package com.example.demo.dto;

import java.util.List;

import lombok.Data;

@Data
public class ItemRequest {
	private String userPhoneNumber;
    private List<String> itemNames;

}
