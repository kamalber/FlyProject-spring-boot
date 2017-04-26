package com.weberfly.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.weberfly.entities.CategoryItem;
import com.weberfly.entities.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

	
	public List<Post> findByCategoryItemsAndDateBetween(List<CategoryItem> items,Date startDate,Date endDate);
}
