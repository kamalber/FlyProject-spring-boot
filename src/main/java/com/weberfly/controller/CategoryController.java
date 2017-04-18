package com.weberfly.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.weberfly.entities.Category;

import com.weberfly.service.CategoryService;

@RestController
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping(value="/categories",method=RequestMethod.GET)
	 public List<Category> getAllCategories(){
		 return categoryService.getAll();
	 }
}
