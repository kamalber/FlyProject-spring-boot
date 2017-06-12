package com.weberfly.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.weberfly.entities.Post;
import com.weberfly.entities.TwitterKeyWord;
import com.weberfly.entities.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

	
	public List<Post> findByTitleAndDateBetween(String title,Date startDate,Date endDate);
	
	   @Query("SELECT COUNT(t) FROM Post t WHERE t.user=:user AND t.generalSentiment=:senti AND "
	   		+ "date between :start and :end")
	   public Long countSentimentByKeyWordAnd(@Param("user") User user,
			   @Param("senti") Post.sentiment senti,@Param("start") Date startDate,@Param("end") Date endDate);
	   
	   @Query("SELECT COUNT(t) FROM Post t WHERE t.user=:user AND t.generalSentiment=:senti")
		   public Long countSentimentByUser(@Param("user") User user, @Param("senti") Post.sentiment senti);
	   
	     public List<Post> findByUser(User user);
}
