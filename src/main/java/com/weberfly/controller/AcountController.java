package com.weberfly.controller;

import java.security.Principal;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.weberfly.entities.User;
import com.weberfly.service.SessionService;

import com.weberfly.service.UserService;
import com.weberfly.service.threads.PostLocationDetection;
import com.weberfly.util.CustomErrorType;

@RestController
@RequestMapping("acount")
public class AcountController {
	public static final Logger logger = LoggerFactory.getLogger(AcountController.class);
	@Autowired
	UserService userService;
	@Autowired
	SessionService sessionService;
	
	@Autowired
	private ApplicationContext applicationContext;

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@RequestBody User appUser) {
		if (userService.find(appUser.getUsername()) != null) {
			logger.error("username Already exist "+appUser.getUsename());
			return new ResponseEntity(new CustomErrorType("user with username "
		+appUser.getUsename()+"already exist "), HttpStatus.CONFLICT);
		}
		appUser.setRole("ADMIN");
		return new ResponseEntity<User>(userService.save(appUser), HttpStatus.CREATED);
	}

	

	@RequestMapping("/login")
	public Principal user(Principal principal) {
		PostLocationDetection postDetection = new PostLocationDetection();
		applicationContext.getAutowireCapableBeanFactory().autowireBean(postDetection);
		postDetection.run();
		return principal;
	}
	@RequestMapping(value = "/detection/{ip}", method = RequestMethod.GET)
	public ResponseEntity<?> getLocation(@PathVariable("ip") String ip) {
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
