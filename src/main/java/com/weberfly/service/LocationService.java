package com.weberfly.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weberfly.dao.CategoryItemRepository;
import com.weberfly.dao.LocationRepository;
import com.weberfly.dao.SessionRepository;
import com.weberfly.entities.CategoryItem;
import com.weberfly.entities.Location;
import com.weberfly.entities.Session;


@Service
public class LocationService {
	@Autowired
	private LocationRepository locationRepository;
	
	public void save(Location location) {
		locationRepository.save(location);
	}
	
	public Boolean isExist(Location location){
		return locationRepository.findOne(location.getIp()) != null;
	}
	public Location find(String ip){
	return locationRepository.findOne(ip);
	}
}
