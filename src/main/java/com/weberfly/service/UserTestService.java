package com.weberfly.service;


import java.util.List;

import com.weberfly.entities.User_test;

public interface UserTestService {
	
	User_test findById(Long id);

	User_test findByName(String name);

	void saveUser(User_test user);

	void updateUser(User_test user);

	void deleteUserById(Long id);

	void deleteAllUsers();

	List<User_test> findAllUsers();

	boolean isUserExist(User_test user);
}