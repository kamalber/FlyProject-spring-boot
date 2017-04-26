package com.weberfly.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weberfly.dao.CategoryItemRepository;

import com.weberfly.entities.CategoryItem;


@Service
public class CategoryItemService {
	@Autowired
	private CategoryItemRepository categoryItemRepository;
	
	public void save(CategoryItem category) {
		categoryItemRepository.save(category);
	}
	public List<CategoryItem> findAll() {
		return categoryItemRepository.findAll();
	}
	public CategoryItem find(Long id) {
		return categoryItemRepository.findOne(id);
	}
	public Boolean isExist(CategoryItem Category){
		return categoryItemRepository.findByName(Category.getName()) != null;
	}
	public void delete(Long id){
		categoryItemRepository.delete(id);
	}
	public CategoryItem findByNameIgnoreCase(String name){
		return categoryItemRepository.findByNameIgnoreCase(name);
	}
}
