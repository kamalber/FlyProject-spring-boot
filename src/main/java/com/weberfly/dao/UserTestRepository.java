package com.weberfly.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.weberfly.entities.User_test;

@Repository
public interface UserTestRepository extends JpaRepository<User_test, Long> {

    User_test findByName(String name);

}
