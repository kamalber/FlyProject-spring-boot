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
	public List<RegularUser> users = new ArrayList<RegularUser>();

	

	
	// End of user code

	/**
	 * The constructor.
	 */
	public RegularUser() {
		// Start of user code constructor for RegularUser)
		super();
		// End of user code
	}

	// Start of user code (user defined methods for RegularUser)

	// End of user code
	/**
	 * Returns following.
	 * @return following 
	 */
	

	
	/**
	 * Returns publications.
	 * @return publications 
	 */
	public List<Publication> getPublications() {
		return this.publications;
	}

	/**
	 * Returns notifications.
	 * @return notifications 
	 */
	public List<Notification> getNotifications() {
		return this.notifications;
	}

	/**
	 * Returns comments.
	 * @return comments 
	 */
	public List<Comment> getComments() {
		return this.comments;
	}

//	public List<RegularUser> getFollowers() {
//		return followers;
//	}
//
//	public void setFollowers(List<RegularUser> followers) {
//		this.followers = followers;
//	}
//
//	public List<RegularUser> getFollowings() {
//		return followings;
//	}
//
//	public void setFollowings(List<RegularUser> followings) {
//		this.followings = followings;
//	}

	public void setPublications(List<Publication> publications) {
		this.publications = publications;
	}

	public void setNotifications(List<Notification> notifications) {
		this.notifications = notifications;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	
	

	

	/**
	 * Returns user.
	 * @return user 
	 */


}
