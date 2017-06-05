package com.weberfly.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weberfly.dao.UserRepository;
import com.weberfly.entities.User;

@Service
public class UserService {
 
	@Autowired
	UserRepository userRepository;
	
	public User save(User user){
		System.out.println(user.getTwitterKeyWords().get(0).getWord());
		return userRepository.saveAndFlush(user);
		
	}
	public User update(User user){
		return userRepository.save(user);
	}
	
	public User find(String userName){
		return userRepository.findOneByUsername(userName);
	}
}
