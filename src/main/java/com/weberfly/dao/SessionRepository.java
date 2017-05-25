package com.weberfly.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.weberfly.entities.Session;
@Repository
public interface SessionRepository extends JpaRepository<Session, Long>{

//	    public CategoryItem findByType(String type);
	 
		public Session findByIpUser(String ipUser);

}
