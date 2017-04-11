package com.weberfly.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import javax.persistence.Table;

@Entity
@Table(name="categoryItem")
public class CategoryItem {
	
@Id
@GeneratedValue(strategy= GenerationType.IDENTITY)
private Long id;
private String name;
@ManyToOne(cascade = CascadeType.ALL)
private Category category = new Category();
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
public Category getCategory() {
	return category;
}
public void setCategory(Category category) {
	this.category = category;
}
public CategoryItem(Long id, String name, Category category) {
	super();
	this.id = id;
	this.name = name;
	this.category = category;
}

}
