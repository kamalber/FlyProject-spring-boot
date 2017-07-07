package com.weberfly.controller;

import java.util.List;
import java.util.Map;

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

import com.weberfly.entities.Comment;
import com.weberfly.entities.Post;
import com.weberfly.entities.Publication;
import com.weberfly.service.CommentService;
import com.weberfly.service.PostService;
import com.weberfly.util.CommentSentimentStats;
import com.weberfly.util.CustomCommentsParams;
import com.weberfly.util.CustomErrorType;
@RestController
public class CommentConroller {
	public static final Logger logger = LoggerFactory.getLogger(CommentConroller.class);
	@Autowired
	private CommentService commentService;
	private PostService postService ;

	// -------------------Retrieve All items---------------------------------------------

	@RequestMapping(value = "/comments", method = RequestMethod.GET)
	public ResponseEntity<List<Comment>> listAll() {
		List<Comment> items = commentService.findAll();
		if (items.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<Comment>>(items, HttpStatus.OK);
	}

	// -------------------Retrieve Single item------------------------------------------

	@RequestMapping(value = "/comments/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getItem(@PathVariable("id") long id) {
		logger.info("Fetching item with id {}", id);
		Comment item = commentService.find(id);
		if (item == null) {
			logger.error("comment with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("comment with id " + id 
					+ " not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Comment>(item, HttpStatus.OK);
	}

	// -------------------Create a item-------------------------------------------

	@RequestMapping(value = "/comments", method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody Comment item, UriComponentsBuilder ucBuilder) {
		logger.info("Creating item : {}", item);
		commentService.save(item);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/comments").buildAndExpand(item.getId()).toUri());
		return new ResponseEntity<Comment>(item, HttpStatus.CREATED);
	}

//	-------------------- get post comments by post --------------------
	@RequestMapping(value = "/comments/commentsByPost", method = RequestMethod.POST)
	public ResponseEntity<?> getCommentByPost(@RequestBody Post post) {
		 
		List<Comment> stats =commentService.findByPost(post);

		if (stats== null||stats.isEmpty()) {
			logger.error("no comments to collect ");
			return new ResponseEntity(new CustomErrorType("no comments for this post"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(stats, HttpStatus.OK);
	}
	
//	-------------------- get user post  comments  --------------------
	@RequestMapping(value = "/comments/commentTotalPolarity", method = RequestMethod.POST)
	public ResponseEntity<?> getCommentTotalPlarit() {
		
		CommentSentimentStats stats=commentService.getSentimentByUserPost();

		if (stats== null||stats.getAverageDataCount().isEmpty()) {
			logger.error("no stats to collect ");
			return new ResponseEntity(new CustomErrorType("no comments for this post"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(stats, HttpStatus.OK);
	}

//	-------------------- get post comments sentiment --------------------
	@RequestMapping(value = "/comments/commentPolarity", method = RequestMethod.POST)
	public ResponseEntity<?> getCommentSentiment(@RequestBody Post post) {
		
		Map<String,Long> stats=commentService.getTotalSentimentByPost(post);

		if (stats== null||stats.isEmpty()) {
			logger.error("no stats to collect ");
			return new ResponseEntity(new CustomErrorType("no comments for this post"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(stats, HttpStatus.OK);
	}
	
	//------------------ get post comments polarity by date ------------------------------
	
	@RequestMapping(value = "/comments/commentPolarityByDate", method = RequestMethod.POST)
	public ResponseEntity<?> getCommentSentimentByDate(@RequestBody CustomCommentsParams params) {
		CommentSentimentStats stats=commentService.getSentimentByPostAndDate(params);
		if (stats== null||stats.getAverageDataCount().isEmpty()) {
			logger.error("no stats to collect ");
			return new ResponseEntity(new CustomErrorType("no comments for this post"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(stats, HttpStatus.OK);
	}
}
