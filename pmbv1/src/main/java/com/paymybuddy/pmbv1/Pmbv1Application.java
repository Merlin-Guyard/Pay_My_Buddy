package com.paymybuddy.pmbv1;

import com.paymybuddy.pmbv1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.transaction.Transactional;

@SpringBootApplication
public class Pmbv1Application implements CommandLineRunner {

	@Autowired
	UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(Pmbv1Application.class, args);
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {

		userService.getUsers().forEach(
				user -> System.out.println(user.getFirstName()));
	}

}
