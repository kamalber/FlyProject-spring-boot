package com.weberfly.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weberfly.dao.PostRepository;
import com.weberfly.entities.Category;
import com.weberfly.entities.CategoryItem;
import com.weberfly.entities.Post;

@Service
public class PostService {
	@Autowired
	private PostRepository postRepository;
	
	public void savePost(Post post) {
//		for (CategoryItem item : ) {
//			
//		}
		postRepository.save(post);
	}
	public List<Post> getAll() {
		return postRepository.findAll();
	}  

}
