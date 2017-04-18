package com.weberfly.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weberfly.dao.CategoryRepository;
import com.weberfly.entities.Category;
import com.weberfly.entities.Type_category;

@Service
public class CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;
	
	public void save(Category category) {
		categoryRepository.save(category);
	}
	public List<Category> getAll() {
		return categoryRepository.findAll();
	}
	public Category findCategory(Long id) {
		return categoryRepository.findOne(id);
	}
}
