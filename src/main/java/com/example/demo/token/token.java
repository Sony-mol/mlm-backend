package com.example.demo.token;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.Repository.UserTokenRepository;
import com.example.demo.dto.UserToken;

public class token {
	@Autowired
	private UserTokenRepository userTokenRepository;

	public void saveUserToken(Long userId, String token, Date expiry) {
	    UserToken userToken = new UserToken();
	    userToken.setUserId(userId);
	    userToken.setToken(token);
	    userToken.setExpiry(expiry);
	    userTokenRepository.save(userToken);
	}

}
