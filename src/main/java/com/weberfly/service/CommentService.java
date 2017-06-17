package com.weberfly.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

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
	@Autowired
	private TweetAnalyseService tweetAnalyseService;
	@Autowired
	HttpSession session;

	public void save(Comment comment) {
		String content = comment.getText();
		// GateSentiment
		String gatesentiment = null;
		try {
			gatesentiment = tweetAnalyseService.getAnalyseByGateApi(content);
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		Post.sentiment sentGate = Post.sentiment.valueOf(gatesentiment);
		comment.setGateSentment(sentGate);

		// DumaxSentiement
		String dumaxsentiment = null;
		try {
			dumaxsentiment = tweetAnalyseService.getAnalyseByDumax(content);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// System.out.println("----------------------"+dumaxsentiment);
		Post.sentiment sentDumax = Post.sentiment.valueOf(dumaxsentiment);
		comment.setDumaxSentment(sentDumax);

		// NLTK Sentiment

		String nltkSentiment = null;
		try {
			nltkSentiment = tweetAnalyseService.getAnalyseByNLTK(content);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Post.sentiment sentNltk = Post.sentiment.valueOf(nltkSentiment);
		comment.setNltkSentment(sentNltk);

		// General sentiment
		String generalSentiment = tweetAnalyseService.getMaxPolarityByTools(gatesentiment, dumaxsentiment,
				nltkSentiment);
		Post.sentiment sentGeneral = Post.sentiment.valueOf(generalSentiment);
		comment.setGeneralSentiment(sentGeneral);

		User u = (User) session.getAttribute("connected");
		comment.setUser(u);
		commentRepository.save(comment);
	}

	public List<Comment> findAll() {
		return commentRepository.findAll();
	}

	public Comment find(Long id) {
		return commentRepository.findOne(id);
	}

	public void delete(Long id) {
		commentRepository.delete(id);
	}

	public Map<String, Long> getTotalSentimentByPost(Post post) {
		Map<String, Long> stats = new HashMap<String, Long>();

		Long positive = commentRepository.countSentimentByPost(post, Post.sentiment.positive);
		Long neutral = commentRepository.countSentimentByPost(post, Post.sentiment.neutral);
		Long negative = commentRepository.countSentimentByPost(post, Post.sentiment.negative);

		if (positive == 0 && negative == 0 && neutral == 0) {
			return null;
		}
		stats.put("positive", positive);
		stats.put("neutral", neutral);
		stats.put("negative", negative);
		return stats;

	}
}