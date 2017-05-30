package com.weberfly.entities;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="Location")
public class Location {

	/**
	 * Description of the property id.
	 */
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id ;
	
	
	private String ip;
	private double latitude=0.0;
	private double longitude=0.0;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy="location")
	private List<Publication> publications ;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Country country ;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Location [ip=" + ip + ", latitude=" + latitude + ", longitude=" + longitude + ", country=" + country + "]";
	}
	
	
	
}
