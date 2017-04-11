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
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id ;
	/**
	 * Description of the property name.
	 */
	private String name ;

	/**
	 * Description of the property citys.
	 */
	@OneToMany(cascade = CascadeType.ALL,mappedBy="country")
	private List<City> citys = new ArrayList<City>();

	// Start of user code (user defined attributes for Country)

	// End of user code

	/**
	 * The constructor.
	 */
	public Country() {
		// Start of user code constructor for Country)
		super();
		// End of user code
	}

	// Start of user code (user defined methods for Country)

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
	 * Returns citys.
	 * @return citys 
	 */
	public List<City> getCitys() {
		return this.citys;
	}

}
