package com.weberfly.entities;
/*******************************************************************************
 * 2017, All rights reserved.
 *******************************************************************************/

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.weberfly.entities.Post.sentiment;


// Start of user code (user defined imports)

// End of user code

/**
 * Description of User.
 * 
 * @author kamal
 */
@Entity
@Table(name="User")
public  class User implements Serializable{
	/**
	 * Description of the property id.
	 */
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id ;

	public static enum Role{
		ADMIN,
		USER
	}
	/**
	 * Description of the property email.
	 */
	private String username ;
	private String password ;
	@Enumerated(EnumType.STRING)
    private Role role;
	/**
	 * Description of the property sessions.
	 */
	
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy="user")
	private List<Session> sessions = new ArrayList<Session>();
	
	/**
	 * Description of the property recivedMessages.
	 */
	@OneToMany(cascade = CascadeType.ALL,mappedBy="sender")
	private List<Message> recivedMessages = new ArrayList<Message>();

	/**
	 * Description of the property sendedMessages.
	 */
	@OneToMany(cascade = CascadeType.ALL,mappedBy="receiver")
	private List<Message> sendedMessages = new ArrayList<Message>();

	/**
	 * Description of the property profiles.
	 */
	@OneToOne(cascade = CascadeType.ALL)
	private Profile profile = new Profile() ;
	
	/*
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
	private List<User> following=new ArrayList<User>();
	@ManyToMany(cascade = CascadeType.ALL)
	 @JoinTable(name = "User_Categories", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"))
	private List<Category> categories= new ArrayList<Category>();
	@ManyToMany(mappedBy="following")
	private List<User> users = new ArrayList<User>();
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}



	public String getUsename() {
		return username;
	}

	public void setUsename(String usename) {
		this.username = usename;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Session> getSessions() {
		return sessions;
	}

	public void setSessions(List<Session> sessions) {
		this.sessions = sessions;
	}

	public List<Message> getRecivedMessages() {
		return recivedMessages;
	}

	public void setRecivedMessages(List<Message> recivedMessages) {
		this.recivedMessages = recivedMessages;
	}

	public List<Message> getSendedMessages() {
		return sendedMessages;
	}

	public void setSendedMessages(List<Message> sendedMessages) {
		this.sendedMessages = sendedMessages;
	}

	public List<Publication> getPublications() {
		return publications;
	}

	public void setPublications(List<Publication> publications) {
		this.publications = publications;
	}

	public List<Notification> getNotifications() {
		return notifications;
	}

	public void setNotifications(List<Notification> notifications) {
		this.notifications = notifications;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<User> getFollowing() {
		return following;
	}

	public void setFollowing(List<User> following) {
		this.following = following;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}


	

}
