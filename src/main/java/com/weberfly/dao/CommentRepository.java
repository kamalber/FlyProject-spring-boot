package com.weberfly.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.weberfly.entities.Comment;
import com.weberfly.entities.Post.sentiment;
import com.weberfly.entities.Post;

import com.weberfly.entities.User;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
	
	  @Query("SELECT COUNT(c) FROM Comment c WHERE c.post=:post AND c.generalSentiment=:senti")
	  public Long countSentimentByPost(@Param("post") Post post, @Param("senti") sentiment senti);
	 
	  
      public List<Comment> findByUser(User user);
      
//      @Query("SELECT COUNT(c) FROM Comment c WHERE c.post.user=: user AND c.generalSentiment=:senti")
//	  public Long countSentimentByForPostUser(@Param("user") User user, @Param("senti") sentiment senti);
}
