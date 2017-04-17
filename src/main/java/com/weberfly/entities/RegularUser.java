package com.weberfly.entities;
/*******************************************************************************
 * 2017, All rights reserved.
 *******************************************************************************/

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

// Start of user code (user defined imports)

// End of user code

/**
 * Description of RegularUser.
 * 
 * @author kamal
 */
@Entity
@Table(name="RegularUser")
public class RegularUser extends User {
	/**
	
	 * Description of the property publications.
	 */
	@OneToMany(cascade = CascadeType.ALL,mappedBy="user")
	private List<Publication> publications = new ArrayList<Publication>();

	/**
	 * Description of the property notifications.
	 */
	
	@OneToMany(mappedBy="user")
	private List<Notification> notifications = new ArrayList<Notification>();

	/**
	 * Description of the property comments.
	 */
	@OneToMany(cascade = CascadeType.ALL,mappedBy="user")
	private List<Comment> comments = new ArrayList<Comment>();

	@ManyToMany(cascade = CascadeType.ALL)
	 @JoinTable(name = "User_Folowers", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "folowing_id", referencedColumnName = "id"))
	private List<RegularUser> following=new ArrayList<RegularUser>();
	@ManyToMany(cascade = CascadeType.ALL)
	 @JoinTable(name = "User_Categories", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"))
	private List<Category> categories= new ArrayList<Category>();
	@ManyToMany(mappedBy="following")
	private List<RegularUser> users = new ArrayList<RegularUser>();
	
	public List<RegularUser> getFollowing() {
		return following;
	}
	public void setFollowing(List<RegularUser> following) {
		this.following = following;
	}
	public List<Category> getCategories() {
		return categories;
	}
	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	

	

}
