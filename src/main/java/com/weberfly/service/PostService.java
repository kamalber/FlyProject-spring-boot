package com.weberfly.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weberfly.dao.PostRepository;
import com.weberfly.entities.Post;

@Service
public class PostService {
	@Autowired
	private PostRepository postRepository;
	
	public void savePost(Post post) {
		postRepository.save(post);
	}
}
