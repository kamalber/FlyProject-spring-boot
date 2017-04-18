package com.weberfly.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weberfly.dao.PublicationRepository;
import com.weberfly.entities.Publication;
@Service
public class PublicationService {
	@Autowired
private PublicationRepository publicationRepository;
	
	public PublicationService() {
		// TODO Auto-generated constructor stub
	}
	 public List<Publication> getAllPubs(){
			return publicationRepository.findAll();
			}
}
