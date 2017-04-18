package com.weberfly.entities;

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

@Entity
@Table(name="categoryItem")
public class CategoryItem {
	
@Id
@GeneratedValue(strategy= GenerationType.IDENTITY)
private Long id;
private String name;

@ManyToOne(cascade = CascadeType.ALL)
private Category category = new Category();

@ManyToMany(mappedBy="categoryItems")
private List<Publication> publications = new ArrayList<Publication>();

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


}
