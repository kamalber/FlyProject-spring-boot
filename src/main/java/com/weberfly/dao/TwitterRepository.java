package com.weberfly.dao;
import java.util.Date;
import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.weberfly.entities.Post;
import com.weberfly.entities.Twitte;
import com.weberfly.entities.TwitterKeyWord;

public interface TwitterRepository extends JpaRepository<Twitte, Long>{

   public  Twitte findOneByText(String text);
	public List<Twitte> findByKeyWordAndDateBetween(TwitterKeyWord key,Date dateStart,Date dateEnd);
	public List<Twitte> findByKeyWord(TwitterKeyWord keyWord);
	
   @Query("SELECT COUNT(t) FROM Twitte t WHERE t.keyWord=:keyWord AND t.generalSentiment=:senti")
   public Long countSentimentByKeyWordAnd(@Param("keyWord") TwitterKeyWord word,@Param("senti") Post.sentiment senti);

}
