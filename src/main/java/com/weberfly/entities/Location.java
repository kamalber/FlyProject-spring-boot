package com.weberfly.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="Location")
public class Location {

	
	@Id
	private String ip;
	private double latitude=0.0;
	private double longitude=0.0;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy="location")
	private List<Publication> publications = new ArrayList<Publication>();
	
	@OneToOne(cascade = CascadeType.ALL)
	private Country country =new Country();

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double langitude) {
		this.longitude = langitude;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}
	
	
	
}
