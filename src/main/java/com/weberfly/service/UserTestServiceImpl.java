package com.weberfly.service;

import java.util.List;

import com.weberfly.dao.UserTestRepository;
import com.weberfly.entities.User_test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service("userService")
@Transactional
public class UserTestServiceImpl implements UserTestService{

	@Autowired
	private UserTestRepository userRepository;

	public User_test findById(Long id) {
		return userRepository.findOne(id);
	}

	public User_test findByName(String name) {
		return userRepository.findByName(name);
	}

	public void saveUser(User_test user) {
		userRepository.save(user);
	}

	public void updateUser(User_test user){
		saveUser(user);
	}

	public void deleteUserById(Long id){
		userRepository.delete(id);
	}

	public void deleteAllUsers(){
		userRepository.deleteAll();
	}

	public List<User_test> findAllUsers(){
		return userRepository.findAll();
	}

	public boolean isUserExist(User_test user) {
		return findByName(user.getName()) != null;
	}

}
