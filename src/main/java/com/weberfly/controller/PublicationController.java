package com.weberfly.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.weberfly.entities.Publication;
import com.weberfly.service.PublicationService;
import com.weberfly.util.CustomErrorType;


@RestController
public class PublicationController {
	@Autowired
	private PublicationService publicationService ;

	@RequestMapping(value="/pubs",method=RequestMethod.GET)
	 public List<Publication> getAllPubs(){
		return publicationService.getAllPubs();
		}
	
	
	 @RequestMapping(value="/pubs/{id}",method=RequestMethod.GET)
	public ResponseEntity<?> getItem(@PathVariable("id") long id) {
	
		Publication item = publicationService.find(id);
		if (item == null) {
		
			return new ResponseEntity(new CustomErrorType("item with id " + id 
					+ " not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Publication>(item, HttpStatus.OK);
	}
}

