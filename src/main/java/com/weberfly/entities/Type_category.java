package com.weberfly.entities;
/*******************************************************************************
 * 2017, All rights reserved.
 *******************************************************************************/

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.Cascade;

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
	@OneToMany( mappedBy="type",cascade=CascadeType.ALL)
	private List<Category> categorys ;

	
	public Long getId() {
		return Id;
	}


	public void setId(Long id) {
		Id = id;
	}


	public String getLabelType() {
		return LabelType;
	}


	public void setLabelType(String labelType) {
		LabelType = labelType;
	}





	


	


	


	



 




}
