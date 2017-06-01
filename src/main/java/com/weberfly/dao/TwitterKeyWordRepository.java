package com.weberfly.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.weberfly.entities.Category;
import com.weberfly.entities.CategoryItem;
import com.weberfly.entities.TwitterKeyWord;
@Repository
public interface TwitterKeyWordRepository extends JpaRepository<TwitterKeyWord, Long>{

public TwitterKeyWord findByWord(String word);
	 
		
}
