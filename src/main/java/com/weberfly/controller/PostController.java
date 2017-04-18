package com.weberfly.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.weberfly.entities.Post;
import com.weberfly.service.PostService;
@RestController
public class PostController {
	@Autowired
	private PostService postservice;
	
	@RequestMapping(value="/post",method=RequestMethod.POST)
	 public void savePost(@RequestBody Post post) {
		postservice.savePost(post);
		}
	@RequestMapping(value="/posts",method=RequestMethod.GET)
	 public List<Post> getAllPosts(){
	   
		return postservice.getAll();
		}
}
