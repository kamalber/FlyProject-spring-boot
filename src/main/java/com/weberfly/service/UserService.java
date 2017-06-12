package com.weberfly.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weberfly.dao.PostRepository;
import com.weberfly.dao.UserRepository;
import com.weberfly.entities.Post;
import com.weberfly.entities.TwitterKeyWord;
import com.weberfly.entities.User;

@Service
public class UserService {
 
	@Autowired
	UserRepository userRepository;
	@Autowired
	PostRepository postRepository;
	
	public User save(User user){
		System.out.println(user.getTwitterKeyWords().get(0).getWord());
		return userRepository.saveAndFlush(user);
		
	}
	public User update(User user){
		return userRepository.save(user);
	}
	
	public User find(String userName){
		return userRepository.findOneByUsername(userName);
	}
	public User find(Long id){
		return userRepository.findOne(id);
	}
	public List<User> findAll(){
		return userRepository.findAll();
	}
	public void  delete(Long id){
		 userRepository.delete(id);
	}
	public Map<String, Long> getUserTotalSentiment(User user) {
		Map<String, Long> stats = new HashMap<String, Long>();
		Long positive = postRepository.countSentimentByUser(user, Post.sentiment.positive);
		Long neutral = postRepository.countSentimentByUser(user, Post.sentiment.neutral);
		Long negative = postRepository.countSentimentByUser(user, Post.sentiment.negative);
		if(positive==0 && negative==0 && neutral==0){
			return null;
			}
		stats.put("positive", positive);
		stats.put("neutral", neutral);
		stats.put("negative", negative);
		return stats;
	}
}
