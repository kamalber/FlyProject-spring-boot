package com.weberfly.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.weberfly.entities.Comment;
import com.weberfly.entities.Post;
import com.weberfly.entities.Post.sentiment;
import com.weberfly.entities.Publication;
import com.weberfly.entities.User;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
	  @Query("SELECT COUNT(t) FROM Comment t WHERE t.post=:pub AND t.generalSentiment=:senti")
	   public Long countSentimentByPost(@Param("pub") Post pub, @Param("senti") sentiment senti);
  
    public List<Comment> findByUser(User user);
}
