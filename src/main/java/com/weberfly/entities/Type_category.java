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
 * Description of Type_category.
 * 
 * @author kamal
 */
@Entity
@Table(name="type_category")
public class Type_category implements Serializable{
	/**
	 * Description of the property Id.
	 */
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long Id ;
	/**
	 * Description of the property LabelType.
	 */
	private String LabelType ;


	/**
	 * Description of the property categorys.
	 */
	@OneToMany(cascade = CascadeType.ALL,mappedBy="type")
	private List<Category> categorys= new ArrayList<Category>() ;

	// Start of user code (user defined attributes for Type_category)

	// End of user code

	/**
	 * The constructor.
	 */
	public Type_category() {
		// Start of user code constructor for Type_category)
		super();
		// End of user code
	}

	// Start of user code (user defined methods for Type_category)

	// End of user code
	/**
	 * Returns LabelType.
	 * @return LabelType 
	 */
	public String getLabelType() {
		return this.LabelType;
	}

	/**
	 * Sets a value to attribute LabelType. 
	 * @param newLabelType 
	 */
	public void setLabelType(String newLabelType) {
		this.LabelType = newLabelType;
	}

	/**
	 * Returns Id.
	 * @return Id 
	 */
	public Long getId() {
		return this.Id;
	}

	/**
	 * Sets a value to attribute Id. 
	 * @param newId 
	 */
	public void setId(Long newId) {
		this.Id = newId;
	}

	/**
	 * Returns categorys.
	 * @return categorys 
	 */
	public List<Category> getCategorys() {
		return this.categorys;
	}

	public void setCategorys(List<Category> categorys) {
		this.categorys = categorys;
	}

	public Type_category(String labelType) {
		super();
	
		LabelType = labelType;
		
	}

	@Override
	public String toString() {
		return "Type_category [Id=" + Id + ", LabelType=" + LabelType + ", categorys=" + categorys + ", getLabelType()="
				+ getLabelType() + ", getId()=" + getId() + ", getCategorys()=" + getCategorys() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

}
