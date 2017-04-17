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
 * Description of User.
 * 
 * @author kamal
 */
@Entity
@Table(name="User")
public abstract class User implements Serializable{
	/**
	 * Description of the property id.
	 */
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id ;

	/**
	 * Description of the property email.
	 */
	private String email ;
	private String password ;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	/**
	 * Description of the property password.
	 */
	

	// Start of user code (user defined attributes for User)

	// End of user code

	

}
