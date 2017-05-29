package com.weberfly.controller;



import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.weberfly.dao.UserRepository;
import com.weberfly.entities.User;


@RestController
@RequestMapping("acount")
public class AcountController {
	
	@Autowired
	UserRepository userRepository;
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<User> createUser(@RequestBody User appUser) {
		if (userRepository.findOneByUsername(appUser.getUsername()) != null) {
			throw new RuntimeException("Username already exist");
		}
		appUser.setRole("ADMIN");
		return new ResponseEntity<User>(userRepository.save(appUser), HttpStatus.CREATED);
	}
	
	@RequestMapping(value= "/kamal" , method = RequestMethod.GET)
	public ResponseEntity<User> user() {
		User u=userRepository.findOneByUsername("user");
		System.out.println(u);
		return new ResponseEntity<User>(u, HttpStatus.OK);
	}
	
	@RequestMapping("/login")
	public Principal user(Principal principal) {	
		System.out.println(principal);
		return principal;
	}

}
