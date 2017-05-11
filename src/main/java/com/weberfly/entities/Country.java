package com.weberfly.entities;
/*******************************************************************************
 * 2017, All rights reserved.
 *******************************************************************************/

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

// Start of user code (user defined imports)

// End of user code

/**
 * Description of Country.
 * 
 * @author kamal
 */
@Entity
@Table(name="Country")
public class Country implements Serializable{
	/**
	 * Description of the property id.
	 */
	@Id
	private String hcKey;
	/**
	 * Description of the property name.
	 */
	private String name ;
	
	private double latitude;
	private double longitude;

	/**
	 * Description of the property citys.
	 */
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy="country")
	private List<City> citys ;

	public String getHcKey() {
		return hcKey;
	}

	public void setHcKey(String hcKey) {
		this.hcKey = hcKey;
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

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public List<City> getCitys() {
		return citys;
	}

	public void setCitys(List<City> citys) {
		this.citys = citys;
	}

	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}




	
}
