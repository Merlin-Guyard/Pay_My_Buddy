package com.paymybuddy.pmbv1;

import com.paymybuddy.pmbv1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Pmbv1Application {

	@Autowired
	UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(Pmbv1Application.class, args);
	}
	}


