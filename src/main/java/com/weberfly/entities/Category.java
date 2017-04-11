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

	
	public Type_category getType() {
		return type;
	}

	public void setType(Type_category type) {
		this.type = type;
	}

	public List<CategoryItem> getItems() {
		return items;
	}

	public void setItems(List<CategoryItem> items) {
		this.items = items;
	}

	public List<RegularUser> getUsers() {
		return users;
	}

	public void setUsers(List<RegularUser> users) {
		this.users = users;
	}

	/**
	 * Description of the property name.
	 */
	private String name ;

	/**
	 * Description of the property publications.
	 */
	  @ManyToMany(mappedBy="categorys")
	public List<Publication> publications = new ArrayList<Publication>();
	  
	  @ManyToOne(cascade = CascadeType.ALL)
		public Type_category type = new Type_category();
	  
	  @OneToMany(cascade = CascadeType.ALL)
		public List<CategoryItem> items = new ArrayList<CategoryItem>();
	  
	  @ManyToMany(mappedBy="categories")
		public List<RegularUser> users = new ArrayList<RegularUser>();
	// Start of user code (user defined attributes for Category)

	// End of user code

	/**
	 * The constructor.
	 */
	public Category() {
		// Start of user code constructor for Category)
		super();
		// End of user code
	}

	// Start of user code (user defined methods for Category)

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
	 * Returns publications.
	 * @return publications 
	 */
	public List<Publication> getPublications() {
		return this.publications;
	}

	public Type_category getType_category() {
		return type;
	}

	public void setType_category(Type_category type_category) {
		this.type = type_category;
	}

	public void setPublications(List<Publication> publications) {
		this.publications = publications;
	}

}
