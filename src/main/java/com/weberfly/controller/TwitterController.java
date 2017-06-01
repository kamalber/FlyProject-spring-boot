package com.weberfly.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.weberfly.entities.TwitterKeyWord;
import com.weberfly.service.TwitterKeyWordService;
import com.weberfly.util.CustomErrorType;

@RestController
@RequestMapping("/twitter")
public class TwitterController {

	public static final Logger logger = LoggerFactory.getLogger(TwitterController.class);
	@Autowired
	TwitterKeyWordService twitterKeyWordService;
	@RequestMapping(value = "/keyWords", method = RequestMethod.GET)
	public ResponseEntity<List<TwitterKeyWord>> prepareCreateUser() {

		List<TwitterKeyWord> list=twitterKeyWordService.findAll();
		if (list == null) {
			logger.error("key word list is empty ");
			return new ResponseEntity(new CustomErrorType("list is empty"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(list, HttpStatus.CREATED);
	}
	
	
}
