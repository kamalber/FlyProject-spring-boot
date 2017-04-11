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
	private String name = "";

	/**
	 * Description of the property countrys.
	 */
	@ManyToOne(cascade = CascadeType.ALL)
	public Country country = new Country();

	/**
	 * Description of the property profiles.
	 */
	@OneToMany(cascade = CascadeType.ALL,mappedBy="city")
	public List<Profile> profiles = new ArrayList<Profile>();

	// Start of user code (user defined attributes for City)

	// End of user code

	/**
	 * The constructor.
	 */
	public City() {
		// Start of user code constructor for City)
		super();
		// End of user code
	}

	// Start of user code (user defined methods for City)

	// End of user code
	/**
	 * Returns name.
	 * @return name 
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets a value to attribute name. 
	 * @param newName 
	 */
	public void setName(String newName) {
		this.name = newName;
	}

	/**
	 * Returns id.
	 * @return id 
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * Sets a value to attribute id. 
	 * @param newId 
	 */
	public void setId(Long newId) {
		this.id = newId;
	}

	/**
	 * Returns countrys.
	 * @return countrys 
	 */
	public Country getCountry() {
		return this.country;
	}

	/**
	 * Sets a value to attribute countrys. 
	 * @param newCountrys 
	 */
	public void setCountry(Country newCountrys) {
		this.country = newCountrys;
	}

	/**
	 * Returns profiles.
	 * @return profiles 
	 */
	public List<Profile> getProfiles() {
		return this.profiles;
	}

}
