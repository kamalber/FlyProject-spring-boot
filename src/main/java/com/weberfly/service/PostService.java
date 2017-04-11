package com.weberfly.service;

import java.util.List;

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
	public List<Post> getAll(Post post) {
		return postRepository.findAll();
	}
}
