package com.weberfly.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import com.weberfly.util.CommentSentimentStats;
import com.weberfly.util.CustomCommentsParams;

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

	private Date getFormatedDate(String dateString) {
		 SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	        String dateInString = "07/06/2013";
		Date date = new Date();
		try {
			date = formatter.parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	public List<Comment> findByPost(Post p){
		return commentRepository.findByPost(p);
	}

	
	public CommentSentimentStats getSentimentByUserPost(){
		User u = (User) session.getAttribute("connected");
		CommentSentimentStats stats = new CommentSentimentStats();
		
		Long positive = commentRepository.countSentimentByUserPosts(u, Post.sentiment.positive);
		Long neutral = commentRepository.countSentimentByUserPosts(u, Post.sentiment.neutral);
		Long negative = commentRepository.countSentimentByUserPosts(u ,Post.sentiment.negative);
		System.out.println(positive+" "+negative+" "+neutral);
		stats.getLabelSeries().add("all dates");
		stats.getLabelSeries().add("all dates");
		stats.getLabelSeries().add("all dates");
		stats.getPositiveDataCount().add(positive);
		stats.getNeutralDataCount().add(neutral);
		stats.getNegativeDataCount().add(negative);
		stats.getAverageDataCount().add((float) ((positive + negative + neutral) / 3));
		stats.setNegativeCount();
		stats.setNeutralCount();
		stats.setPositiveCount();
		return stats;
	}
	
	public CommentSentimentStats getSentimentByPostAndDate(CustomCommentsParams params) {
		CommentSentimentStats stats = new CommentSentimentStats();

		List<Date> dates = dateInterval(getFormatedDate(params.getStartDate()), getFormatedDate(params.getEndDate()));
		for (Date current : dates) {
			SimpleDateFormat sdfr = new SimpleDateFormat("dd/MMM/yyyy");
			stats.getLabelSeries().add(sdfr.format(current));
			Long positive = commentRepository.countSentimentByPostAndDate(params.getPost(), Post.sentiment.positive,
					current,getEndDateFromDate(current));
			
			Long neutral = commentRepository.countSentimentByPostAndDate(params.getPost(), Post.sentiment.neutral,
					current,getEndDateFromDate(current));
			Long negative = commentRepository.countSentimentByPostAndDate(params.getPost(), Post.sentiment.negative,
					current,getEndDateFromDate(current));
			System.out.println(positive+" "+negative+" "+neutral);
			stats.getPositiveDataCount().add(positive);
			stats.getNeutralDataCount().add(neutral);
			stats.getNegativeDataCount().add(negative);
			stats.getAverageDataCount().add((float) ((positive + negative + neutral) / 3));
			stats.setNegativeCount();
			stats.setNeutralCount();
			stats.setPositiveCount();
		}
		return stats;

	}

	private  List<Date> dateInterval(Date initial, Date end) {
		List<Date> dates = new ArrayList<Date>();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(initial);

		while (calendar.getTime().before(end)) {
			Date result = calendar.getTime();
			dates.add(result);
			calendar.add(Calendar.DATE, 1);
			System.out.println(result+ " end "+getEndDateFromDate(result));
		}

		return dates;
	}
	private Date getEndDateFromDate(Date date) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.set(Calendar.HOUR_OF_DAY, 23);
		now.set(Calendar.MINUTE,59);
		date = now.getTime();
		return date;
	}

	
}