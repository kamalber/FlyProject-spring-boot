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
	private List<City> citys ;

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



	

	
}
