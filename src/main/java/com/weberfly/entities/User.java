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
	public List<Session> sessions = new ArrayList<Session>();
	
	/**
	 * Description of the property recivedMessages.
	 */
	@OneToMany(cascade = CascadeType.ALL,mappedBy="sender")
	public List<Message> recivedMessages = new ArrayList<Message>();

	/**
	 * Description of the property sendedMessages.
	 */
	@OneToMany(cascade = CascadeType.ALL,mappedBy="receiver")
	public List<Message> sendedMessages = new ArrayList<Message>();

	/**
	 * Description of the property profiles.
	 */
	@OneToOne(cascade = CascadeType.ALL)
	public Profile profile = new Profile() ;

	/**
	 * Description of the property password.
	 */
	

	// Start of user code (user defined attributes for User)

	// End of user code

	/**
	 * The constructor.
	 */
	public User() {
		// Start of user code constructor for User)
		super();
		// End of user code
	}

	// Start of user code (user defined methods for User)

	// End of user code
	/**
	 * Returns email.
	 * @return email 
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * Sets a value to attribute email. 
	 * @param newEmail 
	 */
	public void setEmail(String newEmail) {
		this.email = newEmail;
	}

	/**
	 * Returns sessions.
	 * @return sessions 
	 */
	



	/**
	 * Returns recivedMessages.
	 * @return recivedMessages 
	 */
	

	/**
	 * Returns id.
	 * @return id 
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * Returns sendedMessages.
	 * @return sendedMessages 
	 */
	

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

	public void setId(Long id) {
		this.id = id;
	}

//	public void setProfiles(Profile profiles) {
//		this.profile = profiles;
//	}
//
//	/**
//	 * Returns profiles.
//	 * @return profiles 
//	 */
//	public Profile getProfile() {
//		return this.profile;
//	}



	/**
	 * Returns password.
	 * @return password 
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * Sets a value to attribute password. 
	 * @param newPassword 
	 */
	public void setPassword(String newPassword) {
		this.password = newPassword;
	}

	public User(String email, String password, List<Session> sessions, List<Message> recivedMessages,
			List<Message> sendedMessages) {
		super();
		
		this.email = email;
		this.password = password;
		this.sessions = sessions;
		this.recivedMessages = recivedMessages;
		this.sendedMessages = sendedMessages;
		
	}

}
