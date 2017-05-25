package com.weberfly.controller;



import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.weberfly.dao.UserRepository;
import com.weberfly.entities.Session;
import com.weberfly.entities.User;
import com.weberfly.service.SessionService;
import com.weberfly.service.threads.PostLocationDetection;


@RestController
@RequestMapping("acount")
public class AcountController {
	
	@Autowired
	UserRepository userRepository;
	@Autowired
	SessionService sessionService;
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<User> createUser(@RequestBody User appUser) {
		if (userRepository.findOneByUsername(appUser.getUsername()) != null) {
			throw new RuntimeException("Username already exist");
		}
		appUser.setRole("ADMIN");
		return new ResponseEntity<User>(userRepository.save(appUser), HttpStatus.CREATED);
	}
	
	@RequestMapping(value= "/detection/{ip}" , method = RequestMethod.GET)
	public ResponseEntity<?> user(@PathVariable("ip") String ip) {
	PostLocationDetection postDetection =new PostLocationDetection(ip);
	postDetection.run();
	return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping("/login")
	public Principal user(Principal principal) {
		return principal;
	}

}
