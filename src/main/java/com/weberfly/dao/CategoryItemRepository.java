package com.weberfly.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.weberfly.entities.Category;
import com.weberfly.entities.CategoryItem;
@Repository
public interface CategoryItemRepository extends JpaRepository<CategoryItem, Long>{

//	    public CategoryItem findByType(String type);
	 
		public CategoryItem findByName(String Name);
}
