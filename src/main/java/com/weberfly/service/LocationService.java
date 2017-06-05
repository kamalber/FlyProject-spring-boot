package com.weberfly.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.weberfly.dao.LocationRepository;

import com.weberfly.entities.Location;



@Service
public class LocationService {
	@Autowired
	private LocationRepository locationRepository;
	
	public void save(Location location) {
		locationRepository.save(location);
	}
	
	public Location find(String ip){
	return locationRepository.findOneByIp(ip);
	}
	public Location findLast(){
		return locationRepository.findTop1ByOrderByIdDesc();
	}
}
