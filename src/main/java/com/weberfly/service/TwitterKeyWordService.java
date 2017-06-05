package com.weberfly.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weberfly.dao.CategoryItemRepository;
import com.weberfly.dao.TwitterKeyWordRepository;
import com.weberfly.entities.CategoryItem;
import com.weberfly.entities.TwitterKeyWord;


@Service
public class TwitterKeyWordService {
	@Autowired
	private TwitterKeyWordRepository twitsRepository;
	
	public void save(TwitterKeyWord word) {
		twitsRepository.save(word);
	}
	public List<TwitterKeyWord> findAll() {
		return twitsRepository.findAll();
	}
	public TwitterKeyWord find(Long id) {
		return twitsRepository.findOne(id);
	}
	public Boolean isExist(TwitterKeyWord word){
		return twitsRepository.findByWord(word.getWord()) != null;
	}
	public void delete(Long id){
		twitsRepository.delete(id);
	}
	
}
