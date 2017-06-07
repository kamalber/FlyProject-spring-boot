package com.weberfly.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.weberfly.entities.TwitterKeyWord;
import com.weberfly.entities.TwitterKeyWord.Period;
import com.weberfly.entities.TwitterKeyWord;
import com.weberfly.service.TweetAnalyseService;
import com.weberfly.service.TwitterKeyWordService;
import com.weberfly.service.threads.TwitterTimerThread;
import com.weberfly.util.CustomErrorType;

@RestController
@RequestMapping("/twitter")
public class TwitterController {

	public static final Logger logger = LoggerFactory.getLogger(TwitterController.class);
	@Autowired
	TwitterKeyWordService twitterKeyWordService;
	@Autowired
	private ApplicationContext applicationContext;
	@Autowired
	private TweetAnalyseService tweetService;
	
	
	// plan schudeled task
	@RequestMapping(value = "/planTask", method = RequestMethod.POST)
	public ResponseEntity<?> planScheduledTask(@RequestBody TwitterKeyWord item,UriComponentsBuilder ucBuilder) {
		if (item == null) {
			logger.error("key word list is empty ");
			return new ResponseEntity(new CustomErrorType("item not found "), HttpStatus.NOT_FOUND);
		}
		System.out.println(item);
	
		TwitterTimerThread task=new TwitterTimerThread(item,tweetService);
		applicationContext.getAutowireCapableBeanFactory().autowireBean(item);
		task.createTAsk();
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<List<TwitterKeyWord>> prepareCreateUser() {

		List<TwitterKeyWord> list=twitterKeyWordService.findAll();
		if (list == null) {
			logger.error("key word list is empty ");
			return new ResponseEntity(new CustomErrorType("list is empty"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(list, HttpStatus.CREATED);
	}
	
	// -------------------Retrieve Single item------------------------------------------

		@RequestMapping(value = "/{id}", method = RequestMethod.GET)
		public ResponseEntity<?> getItem(@PathVariable("id") long id) {
			logger.info("Fetching item with id {}", id);
			TwitterKeyWord item = twitterKeyWordService.find(id);
			if (item == null) {
				logger.error("item with id {} not found.", id);
				return new ResponseEntity(new CustomErrorType("item with id " + id 
						+ " not found"), HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<TwitterKeyWord>(item, HttpStatus.OK);
		}

		// -------------------Create a item-------------------------------------------

		@RequestMapping(value = "/", method = RequestMethod.POST)
		public ResponseEntity<?> create(@RequestBody TwitterKeyWord item, UriComponentsBuilder ucBuilder) {
			logger.info("Creating item : {}", item);
	logger.info("{}",twitterKeyWordService.isExist(item));
			if (twitterKeyWordService.isExist(item)) {
				logger.error("Unable to create. A item with name {} already exist", item.getWord());
				return new ResponseEntity(new CustomErrorType("Unable to create. A item with name " + 
				item.getWord()+ " already exist."),HttpStatus.CONFLICT);
			}
			twitterKeyWordService.save(item);

			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(ucBuilder.path("/typeTwitterKeyWords/{id}").buildAndExpand(item.getId()).toUri());
			return new ResponseEntity<TwitterKeyWord>(item, HttpStatus.CREATED);
		}

		// ------------------- Update a item ------------------------------------------------

		@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
		public ResponseEntity<?> update(@PathVariable("id") long id, @RequestBody TwitterKeyWord item) {
			logger.info("Updating item with id {}", id);

			TwitterKeyWord currentitem = twitterKeyWordService.find(id);

			if (currentitem == null) {
				logger.error("Unable to update. item with id {} not found.", id);
				return new ResponseEntity(new CustomErrorType("Unable to upate. item with id " + id + " not found."),
						HttpStatus.NOT_FOUND);
			}

			currentitem.setWord(item.getWord());
			

			twitterKeyWordService.save(currentitem);
			return new ResponseEntity<TwitterKeyWord>(currentitem, HttpStatus.OK);
		}

		// ------------------- Delete a item-----------------------------------------

		@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
		public ResponseEntity<?> delete(@PathVariable("id") long id) {
			logger.info("Fetching & Deleting item with id {}", id);

			TwitterKeyWord item = twitterKeyWordService.find(id);
			if (item == null) {
				logger.error("Unable to delete. item with id {} not found.", id);
				return new ResponseEntity(new CustomErrorType("Unable to delete. item with id " + id + " not found."),
						HttpStatus.NOT_FOUND);
			}
			twitterKeyWordService.delete(item.getId());
			return new ResponseEntity<TwitterKeyWord>(HttpStatus.NO_CONTENT);
		}
	
}
