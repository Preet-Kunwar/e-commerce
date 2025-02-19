package com.learn.electronic.store;

import com.learn.electronic.store.entities.User;
import com.learn.electronic.store.repositories.UserRepository;
import com.learn.electronic.store.security.JwtHelper;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ElectronicStoreApplicationTests {

	@Autowired
	private UserRepository userRepository;



	@Autowired
	private JwtHelper jwtHelper;


	@Test
	void contextLoads() {
	}

	@Test
	void testToken(){
		User user = userRepository.findByEmail("preet@gmail.com").get();
	String token =jwtHelper.generateToken(user);
		System.out.println(token);
		System.out.println(jwtHelper.getUsernameFromToken(token));
		System.out.println("Token Expired : ");
		System.out.println(jwtHelper.isTokenExpired(token));
	}

}
