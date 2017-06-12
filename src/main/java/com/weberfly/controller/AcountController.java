package com.weberfly.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.weberfly.entities.User;
import com.weberfly.service.SessionService;

import com.weberfly.service.UserService;
import com.weberfly.service.threads.PostLocationDetection;
import com.weberfly.util.CustomErrorType;

@RestController
@RequestMapping("acount")
public class AcountController {
	public static final Logger logger = LoggerFactory.getLogger(AcountController.class);
	//Save the uploaded file to this folder
    private static String UPLOADED_FOLDER = "C://Users//kamal//workspace//fly-knowledge//src//main//resources//static//images//";
	@Autowired
	UserService userService;
	@Autowired
	SessionService sessionService;
	
	@Autowired
	private ApplicationContext applicationContext;
	@Autowired
	HttpSession session;
	
	
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
	//	PostLocationDetection postDetection = new PostLocationDetection();
//		applicationContext.getAutowireCapableBeanFactory().autowireBean(postDetection);
//		postDetection.run();
		User u=(User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		session.setAttribute("connected", u);
	    System.out.println(u);
		return principal;
	}
	
	@RequestMapping(value = "/session", method = RequestMethod.GET)
	public ResponseEntity<?> getConnected() {
	
		User user=(User)session.getAttribute("connected");
		if (user == null) {
			logger.error("user not found ");
			return new ResponseEntity(new CustomErrorType("user not found "
		), HttpStatus.CONFLICT);
		}
		
		return new ResponseEntity<User>(user, HttpStatus.CREATED);
	}
	@RequestMapping(value = "/detection/{ip}", method = RequestMethod.GET)
	public ResponseEntity<?> getLocation(@PathVariable("ip") String ip) {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	@RequestMapping(value = "/uploadImg", method = RequestMethod.POST) 
    public ResponseEntity<?> singleFileUpload(@RequestParam("file") MultipartFile file){
		User user = (User)session.getAttribute("connected");
		if (user == null) {
			return new ResponseEntity(new CustomErrorType("user session expired "), HttpStatus.NOT_FOUND);
		
		}
        if (file.isEmpty()) {
        	return new ResponseEntity(new CustomErrorType(" error file is empty"), HttpStatus.CONFLICT);
        }

        try {

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);
            user.getProfile().setImage(file.getOriginalFilename());
            userService.update(user);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ResponseEntity(HttpStatus.CREATED);
    }
}
