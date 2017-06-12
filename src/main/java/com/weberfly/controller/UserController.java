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

import com.weberfly.entities.TwitterKeyWord;
import com.weberfly.entities.User;
import com.weberfly.service.UserService;
import com.weberfly.util.CustomErrorType;

@RestController
@RequestMapping("/user")
public class UserController {
	
	public static final Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private UserService UserService;

	// -------------------Retrieve All items---------------------------------------------

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<List<User>> listAll() {
		List<User> items = UserService.findAll();
		if (items.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<User>>(items, HttpStatus.OK);
	}

	// -------------------Retrieve Single item------------------------------------------

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getItem(@PathVariable("id") long id) {
		logger.info("Fetching item with id {}", id);
		User item = UserService.find(id);
		if (item == null) {
			logger.error("item with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("item with id " + id 
					+ " not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(item, HttpStatus.OK);
	}

	// -------------------Create a item-------------------------------------------



	// ------------------- Update a item ------------------------------------------------

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> update(@PathVariable("id") long id, @RequestBody User item) {
		logger.info("Updating item with id {}", id);

		User currentitem = UserService.find(id);

		if (currentitem == null) {
			logger.error("Unable to update. item with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to upate. item with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}

		currentitem.setFullName(item.getFullName());
		

		UserService.save(currentitem);
		return new ResponseEntity<User>(currentitem, HttpStatus.OK);
	}

	// ------------------- Delete a item-----------------------------------------

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable("id") long id) {
		logger.info("Fetching & Deleting item with id {}", id);

		User item = UserService.find(id);
		if (item == null) {
			logger.error("Unable to delete. item with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to delete. item with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		UserService.delete(item.getId());
		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}
// ------------------------------- get total user sentiment ------------------------------
	@RequestMapping(value = "/userSentiment", method = RequestMethod.POST)
	public ResponseEntity<?> getUsereSentiment(@RequestBody User user) {
		
		Map<String,Long> stats=UserService.getUserTotalSentiment(user);

		if (stats== null||stats.isEmpty()) {
			logger.error("no stats to collect ");
			return new ResponseEntity(new CustomErrorType("user have not post anything"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(stats, HttpStatus.OK);
	}
	// ------------------- Delete All items-----------------------------

//	@RequestMapping(value = "/item/", method = RequestMethod.DELETE)
//	public ResponseEntity<TypeUser> deleteAllitems() {
//		logger.info("Deleting All items");
//
//		typeCatService.deleteAllitems();
//		return new ResponseEntity<TypeUser>(HttpStatus.NO_CONTENT);
//	}
}
