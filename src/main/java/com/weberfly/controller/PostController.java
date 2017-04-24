package com.weberfly.controller;



import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.weberfly.entities.Category;
import com.weberfly.entities.Post;
import com.weberfly.service.PostService;
import com.weberfly.util.CustomErrorType;
@RestController
public class PostController {
	@Autowired
	private PostService postservice;
	public static final Logger logger = LoggerFactory.getLogger(PostController.class);
	
	
	@RequestMapping(value = "/posts", method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody Post item, UriComponentsBuilder ucBuilder) {
		logger.info("Creating item : {}", item);
       
		postservice.savePost(item);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/posts/{id}").buildAndExpand(item.getId()).toUri());
		return new ResponseEntity<Post>(item, HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/posts",method=RequestMethod.GET)
	 public List<Post> getAllPosts(){
		return postservice.getAll();
		}
}
