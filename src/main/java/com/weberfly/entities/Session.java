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
 * Description of Session.
 * 
 * @author kamal
 */
@Entity
@Table(name="Session")
public class Session implements Serializable{
	/**
	 * Description of the property id.
	 */
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id ;

	/**
	 * Description of the property ipUser.
	 */
	private String ipUser="127.0.0.1";

	/**
	

	/**
	 * Description of the property time_in.
	 */
	private Date time_in = new Date();

	/**
	 * Description of the property time_out.
	 */
	private Date time_out = new Date();
	
	// Start of user code (user defined attributes for Session)
	
	// End of user code

	private double latitude;
	private double langitude;
	/**
	 * The constructor.
	 */
	public Session() {
		// Start of user code constructor for Session)
		super();
		// End of user code
	}

	// Start of user code (user defined methods for Session)

	// End of user code
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
	 * Returns ipUser.
	 * @return ipUser 
	 */
	public String getIpUser() {
		return this.ipUser;
	}

	/**
	 * Sets a value to attribute ipUser. 
	 * @param newIpUser 
	 */
	public void setIpUser(String newIpUser) {
		this.ipUser = newIpUser;
	}

	

	/**
	 * Returns time_in.
	 * @return time_in 
	 */
	public Date getTime_in() {
		return this.time_in;
	}

	/**
	 * Sets a value to attribute time_in. 
	 * @param newTime_in 
	 */
	public void setTime_in(Date newTime_in) {
		this.time_in = newTime_in;
	}

	/**
	 * Returns time_out.
	 * @return time_out 
	 */
	public Object getTime_out() {
		return this.time_out;
	}

	/**
	 * Sets a value to attribute time_out. 
	 * @param newTime_out 
	 */
	public void setTime_out(Date newTime_out) {
		this.time_out = newTime_out;
	}



	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLangitude() {
		return langitude;
	}

	public void setLangitude(double langitude) {
		this.langitude = langitude;
	}

}
