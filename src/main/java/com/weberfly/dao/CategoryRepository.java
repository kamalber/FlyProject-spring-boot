package com.weberfly.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.weberfly.entities.Category;
import com.weberfly.entities.TypeCategory;
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

	 Category findByType(String type);
		public Category findByName(String Name);
}
