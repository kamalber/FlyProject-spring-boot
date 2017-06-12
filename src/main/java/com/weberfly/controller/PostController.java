package com.weberfly.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.weberfly.entities.Category;
import com.weberfly.entities.CategoryItem;
import com.weberfly.entities.Post;
import com.weberfly.entities.Publication;
import com.weberfly.entities.User;
import com.weberfly.service.CommentService;
import com.weberfly.service.PostService;
import com.weberfly.util.CustomErrorType;
import com.weberfly.util.CustomStatsParams;
import com.weberfly.util.PostSentimentStats;
import com.weberfly.util.SentimentStats;

@RestController
public class PostController {
	@Autowired
	private PostService postservice;
    @Autowired
    HttpSession session;
	public static final Logger logger = LoggerFactory.getLogger(PostController.class);

	@RequestMapping(value = "/posts", method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody Post item, UriComponentsBuilder ucBuilder) throws Exception {
		logger.info("Creating item : {}", item);

		postservice.savePost(item);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/posts/{id}").buildAndExpand(item.getId()).toUri());
		return new ResponseEntity<Post>(item, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/posts", method = RequestMethod.GET)
	public  ResponseEntity<?> getAllPosts(){
		List<Post> items =postservice.getAll();
		return new ResponseEntity<List<Post>>(items, HttpStatus.CREATED);
	}


	
	@RequestMapping(value = "/posts/statistics/{query}/{startDate}/{endDate}", method = RequestMethod.GET)
	public ResponseEntity<?> getItem(@PathVariable("startDate") String startDate,
			@PathVariable("endDate") String endDate, @PathVariable("query") String querySearche) {
		logger.info("date 1 {} , date 2{} , querySearche", startDate, endDate, querySearche);
		List<Post> items = postservice.getAnalysedPosts(querySearche, startDate, endDate);
		if (items == null || items.isEmpty()) {
			logger.error("list is empty ");
			return new ResponseEntity(new CustomErrorType("empty list for query " + querySearche),
					HttpStatus.NOT_FOUND);
			
		}
		return new ResponseEntity<List<Post>>(items, HttpStatus.OK);}

	// -------------------Retrieve Single item------------------------------------------

		@RequestMapping(value = "/posts/{id}", method = RequestMethod.GET)
		public ResponseEntity<?> getItem(@PathVariable("id")long id) {
			logger.info("Fetching item with id {}", id);
			Post item = postservice.findPost(id);
			if (item == null) {
				logger.error("item with id {} not found.", id);
				return new ResponseEntity(new CustomErrorType("item with id " + id 
						+ " not found"), HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<Post>(item, HttpStatus.OK);
		}

		// -------------------Retrieve Single item by User------------------------------------------

		@RequestMapping(value = "/posts/byUser", method = RequestMethod.GET)
		public ResponseEntity<?> getItems() {
			User u=(User)session.getAttribute("connected");
			List<Post> items = postservice.findByUser(u);
			if (items == null) {
				
				return new ResponseEntity(new CustomErrorType("items not found"), HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<List<Post>>(items, HttpStatus.OK);
		}
	@RequestMapping(value = "/posts/statistics", method = RequestMethod.POST)
	public ResponseEntity<?> getStatisctics(@RequestBody CustomStatsParams params, UriComponentsBuilder ucBuilder) {
		logger.info("Creating item : {}", params);

		PostSentimentStats stats = postservice.getStats(params);
		if (stats == null) {
			logger.error("list is empty ");
			return new ResponseEntity(new CustomErrorType("empty list for query " + params.getQuery()),
					HttpStatus.NOT_FOUND);

		}
		return new ResponseEntity<PostSentimentStats>(stats, HttpStatus.OK);
	}

	@RequestMapping(value = "/posts/params", method = RequestMethod.GET)
	public ResponseEntity<?> getParams(UriComponentsBuilder ucBuilder) {
		CustomStatsParams cs = new CustomStatsParams();
		cs.setEndYear(2019);
		cs.setStartYear(2010);
		cs.setQuery("java");
		cs.setSentimentMethode(1);
		return new ResponseEntity<>(cs, HttpStatus.OK);
	}


}
