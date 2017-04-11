package com.weberfly.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.weberfly.entities.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	 

 
}
