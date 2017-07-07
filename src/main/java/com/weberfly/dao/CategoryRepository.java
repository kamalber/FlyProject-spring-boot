package com.weberfly.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.weberfly.entities.Category;
import com.weberfly.entities.Comment;
import com.weberfly.entities.TwitterKeyWord;
import com.weberfly.entities.User;
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

		public Category findByTitle(String title);
		
		
}
