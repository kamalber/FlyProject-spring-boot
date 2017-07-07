package com.weberfly.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.weberfly.entities.Category;
import com.weberfly.entities.CategoryItem;
import com.weberfly.entities.TwitterKeyWord;
import com.weberfly.entities.User;
@Repository
public interface TwitterKeyWordRepository extends JpaRepository<TwitterKeyWord, Long>{

public TwitterKeyWord findByWord(String word);

@Query("SELECT k.category.title FROM TwitterKeyWord k WHERE k.id=:key")
public String findByKeyWord(@Param("key") Long key);

public List<TwitterKeyWord> findByCategory(Category c);
		
}
