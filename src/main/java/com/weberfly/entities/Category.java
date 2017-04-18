package com.weberfly.entities;
/*******************************************************************************
 * 2017, All rights reserved.
 *******************************************************************************/

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

// Start of user code (user defined imports)

// End of user code

/**
 * Description of Category.
 * 
 * @author kamal
 */
@Entity
@Table(name="Category")
public class Category implements Serializable{
	
	/**
	 * Description of the property id.
	 */
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id ;
	private String name ;
	
	  @ManyToMany(mappedBy="categorys")
	private List<Publication> publications = new ArrayList<Publication>();
	  
	  @ManyToOne(cascade = CascadeType.ALL)
	 private Type_category type  ;
	  
	  @OneToMany(cascade = CascadeType.ALL)
		private List<CategoryItem> items = new ArrayList<CategoryItem>();
	  
	  @ManyToMany(mappedBy="categories")
		private List<RegularUser> users = new ArrayList<RegularUser>();

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

	public Type_category getType() {
		return type;
	}

	public void setType(Type_category type) {
		this.type = type;
	}

	


}
