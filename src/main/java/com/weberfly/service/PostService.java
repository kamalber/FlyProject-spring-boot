package com.weberfly.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weberfly.dao.CategoryItemRepository;
import com.weberfly.dao.PostRepository;
import com.weberfly.dao.PublicationRepository;
import com.weberfly.entities.Category;
import com.weberfly.entities.CategoryItem;
import com.weberfly.entities.Post;
import com.weberfly.entities.Publication;

@Service
public class PostService {
	@Autowired
	private PostRepository postRepository;
	@Autowired
	private PublicationRepository publicationRepository;
	@Autowired
	private CategoryItemRepository categoryItemRepository;
	
	public void savePost(Post post) {
		String pos =post.getContent();
		List<CategoryItem> categoryItems =categoryItemRepository.findAll();
		List<CategoryItem> pubcategoryItems =new ArrayList<>();
		for (CategoryItem item : categoryItems) {		
			if (pos.toLowerCase().contains(item.getName().toLowerCase())){		
				pubcategoryItems.add(item);	
		}
		}
		post.setCategoryItems(pubcategoryItems);
		
		postRepository.save(post);
	}
	public List<Post> getAll() {
		return postRepository.findAll();
	}  
	
	public Post findPost(Long id) {
		return postRepository.findOne(id);
	} 
	
	public Publication find(Long id) {
		return publicationRepository.findOne(id);
	}
}
