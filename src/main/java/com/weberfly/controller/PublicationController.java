package com.weberfly.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.weberfly.entities.Publication;
import com.weberfly.service.PublicationService;


@RestController
public class PublicationController {
	@Autowired
	private PublicationService publicationService ;
	public PublicationController() {
		// TODO Auto-generated constructor stub
	}
	@RequestMapping(value="/pubs",method=RequestMethod.GET)
	 public List<Publication> getAllPubs(){
		return publicationService.getAllPubs();
		}
}
