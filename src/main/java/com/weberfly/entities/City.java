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
 * Description of City.
 * 
 * @author kamal
 */
@Entity
@Table(name="City")
public class City implements Serializable{
	
	/**
	 * Description of the property id.
	 */
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id ;

	/**
	 * Description of the property name.
	 */
	private String name ;

	/**
	 * Description of the property countrys.
	 */
	@ManyToOne(cascade = CascadeType.ALL)
	private Country country  ;

	/**
	 * Description of the property profiles.
	 */
	@OneToMany(cascade = CascadeType.ALL,mappedBy="city")
	private List<Profile> profiles ;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	
	

	

	



	
}
