package com.weberfly.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weberfly.dao.CategoryRepository;
import com.weberfly.entities.Category;


@Service
public class CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;
	
	public void save(Category category) {
		categoryRepository.save(category);
	}
	public List<Category> findAll() {
		return categoryRepository.findAll();
	}
	public Category find(Long id) {
		return categoryRepository.findOne(id);
	}
	public Boolean isExist(Category Category){
		return categoryRepository.findByName(Category.getName()) != null;
	}
	public void delete(Long id){
		categoryRepository.delete(id);
	}
}
