package com.weberfly.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.weberfly.entities.Location;
import com.weberfly.entities.User_test;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

	public Location findOneByIp(String ip);
}
