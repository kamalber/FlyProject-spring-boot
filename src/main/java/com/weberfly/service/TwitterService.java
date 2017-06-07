package com.weberfly.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.weberfly.dao.TwitterRepository;
import com.weberfly.entities.Twitte;


@Service
public class TwitterService {
	@Autowired
	private TwitterRepository twitterRepository;
	
	public void save(Twitte twitte) {
		twitterRepository.save(twitte);
	}
	public List<Twitte> findAll() {
		return twitterRepository.findAll();
	}
	public Twitte find(Long id) {
		return twitterRepository.findOne(id);
	}
	public Boolean isExist(Twitte twitte){
		return twitterRepository.findOneByText(twitte.getText()) != null;
	}
	public void delete(Long id){
		twitterRepository.delete(id);
	}
	

}
