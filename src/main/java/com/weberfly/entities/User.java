package com.weberfly.entities;
/*******************************************************************************
 * 2017, All rights reserved.
 *******************************************************************************/


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;



// Start of user code (user defined imports)

// End of user code

/**
 * Description of User.
 * 
 * @author kamal
 */
@Entity
@Table(name="User")
public  class User implements UserDetails{
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
	@Column(unique = true)
	private String username ;

	@JsonProperty(access = Access.WRITE_ONLY)
	private String password ;
	
    private String  role;
    
    private String fullName;
	/**
	 * Description of the property session.
	 */
	
	@OneToOne(cascade = CascadeType.ALL)
	private Session session ;
	
	/**
	 * Description of the property recivedMessages.
	 */
	@OneToMany(cascade = CascadeType.ALL,mappedBy="sender")
	private List<Message> recivedMessages ;
	/**
	 * Description of the property sendedMessages.
	 */
	@OneToMany(cascade = CascadeType.ALL,mappedBy="receiver")
	private List<Message> sendedMessages;

	/**
	 * Description of the property profiles.
	 */
	@OneToOne(cascade = CascadeType.ALL)
	private Profile profile = new Profile() ;
	
	/*
	 * Description of the property publications.
	 */
	@OneToMany(cascade = CascadeType.ALL,mappedBy="user")
	private List<Publication> publications ;

	/**
	 * Description of the property notifications.
	 */
	
	@OneToMany(mappedBy="user")
	private List<Notification> notifications ;

	/**
	 * Description of the property comments.
	 */
	@OneToMany(cascade = CascadeType.ALL,mappedBy="user")
	private List<Comment> comments ;

	@ManyToMany(cascade = CascadeType.ALL)
	 @JoinTable(name = "User_Folowers", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "folowing_id", referencedColumnName = "id"))
	private List<User> following;
	@ManyToMany(cascade = CascadeType.ALL)
	 @JoinTable(name = "User_Categories", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"))
	private List<Category> categories;
	@ManyToMany(mappedBy="following")
	private List<User> users;
	

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

	

	public void setSession(Session session) {
		this.session = session;
	}
public Session getSession(){
	return this.session;
}
	public void setRecivedMessages(List<Message> recivedMessages) {
		this.recivedMessages = recivedMessages;
	}


	public void setSendedMessages(List<Message> sendedMessages) {
		this.sendedMessages = sendedMessages;
	}

	
	public void setPublications(List<Publication> publications) {
		this.publications = publications;
	}

	

	public void setNotifications(List<Notification> notifications) {
		this.notifications = notifications;
	}



	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	

	public void setFollowing(List<User> following) {
		this.following = following;
	}

	

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@JsonIgnore
	@Override
	public boolean isEnabled() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(role));
		return authorities;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", role=" + role +
				 ", profile=" + profile + "]";
	}
	

}
