package com.weberfly.controller;

import java.util.List;

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

import com.weberfly.entities.CategoryItem;
import com.weberfly.service.CategoryItemService;
import com.weberfly.util.CustomErrorType;

@RestController
@RequestMapping("/categoryItems")
public class CategoryItemController {
	
	public static final Logger logger = LoggerFactory.getLogger(CategoryItemController.class);
	@Autowired
	private CategoryItemService CategoryItemService;

	// -------------------Retrieve All items---------------------------------------------

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<List<CategoryItem>> listAll() {
		List<CategoryItem> items = CategoryItemService.findAll();
		if (items.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<CategoryItem>>(items, HttpStatus.OK);
	}

	// -------------------Retrieve Single item------------------------------------------

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getItem(@PathVariable("id") long id) {
		logger.info("Fetching item with id {}", id);
		CategoryItem item = CategoryItemService.find(id);
		if (item == null) {
			logger.error("item with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("item with id " + id 
					+ " not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<CategoryItem>(item, HttpStatus.OK);
	}

	// -------------------Create a item-------------------------------------------

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody CategoryItem item, UriComponentsBuilder ucBuilder) {
		logger.info("Creating item : {}", item);
logger.info("{}",CategoryItemService.isExist(item));
		if (CategoryItemService.isExist(item)) {
			logger.error("Unable to create. A item with name {} already exist", item.getName());
			return new ResponseEntity(new CustomErrorType("Unable to create. A item with name " + 
			item.getName()+ " already exist."),HttpStatus.CONFLICT);
		}
		CategoryItemService.save(item);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/typeCategoryItems/{id}").buildAndExpand(item.getId()).toUri());
		return new ResponseEntity<CategoryItem>(item, HttpStatus.CREATED);
	}

	// ------------------- Update a item ------------------------------------------------

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> update(@PathVariable("id") long id, @RequestBody CategoryItem item) {
		logger.info("Updating item with id {}", id);

		CategoryItem currentitem = CategoryItemService.find(id);

		if (currentitem == null) {
			logger.error("Unable to update. item with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to upate. item with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}

		currentitem.setName(item.getName());
		

		CategoryItemService.save(currentitem);
		return new ResponseEntity<CategoryItem>(currentitem, HttpStatus.OK);
	}

	// ------------------- Delete a item-----------------------------------------

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable("id") long id) {
		logger.info("Fetching & Deleting item with id {}", id);

		CategoryItem item = CategoryItemService.find(id);
		if (item == null) {
			logger.error("Unable to delete. item with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to delete. item with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		CategoryItemService.delete(item.getId());
		return new ResponseEntity<CategoryItem>(HttpStatus.NO_CONTENT);
	}

	// ------------------- Delete All items-----------------------------

//	@RequestMapping(value = "/item/", method = RequestMethod.DELETE)
//	public ResponseEntity<TypeCategoryItem> deleteAllitems() {
//		logger.info("Deleting All items");
//
//		typeCatService.deleteAllitems();
//		return new ResponseEntity<TypeCategoryItem>(HttpStatus.NO_CONTENT);
//	}
}
