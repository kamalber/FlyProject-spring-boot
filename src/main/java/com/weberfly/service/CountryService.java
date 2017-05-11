package com.weberfly.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weberfly.dao.CountryRepository;
import com.weberfly.entities.Country;
@Service
public class CountryService {
	@Autowired
	private CountryRepository countryRepository;
	
	public void save(Country comment) {
		countryRepository.save(comment);
	}
	public List<Country> findAll() {
		return countryRepository.findAll();
	}
	public Country find(String id) {
		return countryRepository.findOne(id);
	}
	
	public void delete(String id){
		countryRepository.delete(id);
	}
}
