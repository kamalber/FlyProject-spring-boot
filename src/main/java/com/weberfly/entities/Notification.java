package com.weberfly.entities;
/*******************************************************************************
 * 2017, All rights reserved.
 *******************************************************************************/

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

// Start of user code (user defined imports)

// End of user code

/**
 * Description of Notification.
 * 
 * @author kamal
 */
@Entity
@Table(name="Notification")
public class Notification implements Serializable{
	
	/**
	 * Description of the property id.
	 */
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id ;

	
	/**
	 * Description of the property date.
	 */
	private Date date =new Date(); ;

	
	/**
	 * Description of the property Description.
	 */
	private String Description ;

	/**
	 * Description of the property state.
	 */
	private Boolean state = Boolean.FALSE;

	/**
	 * Description of the property titre.
	 */
	private String titre ;

	/**
	 * Description of the property user.
	 */
	@ManyToOne(cascade = CascadeType.ALL )
	public RegularUser user = new RegularUser();

	// Start of user code (user defined attributes for Notification)

	// End of user code

	/**
	 * The constructor.
	 */
	public Notification() {
		// Start of user code constructor for Notification)
		super();
		// End of user code
	}

	// Start of user code (user defined methods for Notification)

	// End of user code
	/**
	 * Returns date.
	 * @return date 
	 */
	public Object getDate() {
		return this.date;
	}

	/**
	 * Sets a value to attribute date. 
	 * @param newDate 
	 */
	public void setDate(Date newDate) {
		this.date = newDate;
	}

	/**
	 * Returns id.
	 * @return id 
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * Sets a value to attribute id. 
	 * @param newId 
	 */
	public void setId(Long newId) {
		this.id = newId;
	}

	/**
	 * Returns Description.
	 * @return Description 
	 */
	public String getDescription() {
		return this.Description;
	}

	/**
	 * Sets a value to attribute Description. 
	 * @param newDescription 
	 */
	public void setDescription(String newDescription) {
		this.Description = newDescription;
	}

	/**
	 * Returns state.
	 * @return state 
	 */
	public Boolean getState() {
		return this.state;
	}

	/**
	 * Sets a value to attribute state. 
	 * @param newState 
	 */
	public void setState(Boolean newState) {
		this.state = newState;
	}

	/**
	 * Returns titre.
	 * @return titre 
	 */
	public String getTitre() {
		return this.titre;
	}

	/**
	 * Sets a value to attribute titre. 
	 * @param newTitre 
	 */
	public void setTitre(String newTitre) {
		this.titre = newTitre;
	}

	/**
	 * Returns user.
	 * @return user 
	 */
	public RegularUser getUser() {
		return this.user;
	}

	/**
	 * Sets a value to attribute user. 
	 * @param newUser 
	 */
	public void setUser(RegularUser newUser) {
		this.user = newUser;
	}

	public Notification( Date date, String description, Boolean state, String titre) {
		super();
		
		this.date = date;
		Description = description;
		this.state = state;
		this.titre = titre;
		
	}



}
