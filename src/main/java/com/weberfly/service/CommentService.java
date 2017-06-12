package com.weberfly.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weberfly.dao.CommentRepository;

import com.weberfly.entities.Comment;
import com.weberfly.entities.Post;
import com.weberfly.entities.Publication;
import com.weberfly.entities.User;


@Service
public class CommentService {
	@Autowired
	private CommentRepository commentRepository;
	
	public void save(Comment comment) {
		commentRepository.save(comment);
	}
	public List<Comment> findAll() {
		return commentRepository.findAll();
	}
	public Comment find(Long id) {
		return commentRepository.findOne(id);
	}
	
	public void delete(Long id){
		commentRepository.delete(id);
	}
	
	public Map<String, Long> getTotalSentimentByPost(Post post) {
		Map<String, Long> stats = new HashMap<String, Long>();

		Long positive = commentRepository.countSentimentByPost(post, Post.sentiment.positive);
		Long neutral = commentRepository.countSentimentByPost(post, Post.sentiment.neutral);
		Long negative =  commentRepository.countSentimentByPost(post, Post.sentiment.negative);
		if(positive==0 && negative==0 && neutral==0){
			
			return null;
			}
		stats.put("positive", positive);
		stats.put("neutral", neutral);
		stats.put("negative", negative);
		return stats;

}
}