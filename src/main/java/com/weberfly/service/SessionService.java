package com.weberfly.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weberfly.dao.CategoryItemRepository;
import com.weberfly.dao.SessionRepository;
import com.weberfly.entities.CategoryItem;
import com.weberfly.entities.Session;


@Service
public class SessionService {
	@Autowired
	private SessionRepository sessionRepository;
	
	public void save(Session session) {
		sessionRepository.save(session);
	}
	
	public Session findByIpUser(String ipUser){
	return sessionRepository.findByIpUser( ipUser);
	}
}
