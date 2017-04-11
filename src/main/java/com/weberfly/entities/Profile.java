package com.weberfly.entities;
/*******************************************************************************
 * 2017, All rights reserved.
 *******************************************************************************/

import java.io.Serializable;

import javax.persistence.*;

// Start of user code (user defined imports)

// End of user code

/**
 * Description of Profile.
 * 
 * @author kamal
 */
@Entity
@Table(name="Profile")
public class Profile implements Serializable{
	/**
	 * Description of the property id.
	 */
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id ;
	/**
	 * Description of the property Employment.
	 */
	private String Employment ;

	/**
	 * Description of the property citys.
	 */
	@ManyToOne(cascade = CascadeType.ALL )
	public City city = new City();

	/**
	 * Description of the property adresse.
	 */
	private String adresse ;

	/**
	 * Description of the property profileDescription.
	 */
	private String profileDescription ;

	/**
	 * Description of the property Education.
	 */
	private String Education ;

	/**
	 * Description of the property user.
	 */
	@OneToOne(cascade = CascadeType.ALL,mappedBy="profile"  )
	public User user ;

	// Start of user code (user defined attributes for Profile)

	// End of user code

	/**
	 * The constructor.
	 */
	public Profile() {
		// Start of user code constructor for Profile)
		super();
		// End of user code
	}

	// Start of user code (user defined methods for Profile)

	// End of user code
	/**
	 * Returns Employment.
	 * @return Employment 
	 */
	public String getEmployment() {
		return this.Employment;
	}

	/**
	 * Sets a value to attribute Employment. 
	 * @param newEmployment 
	 */
	public void setEmployment(String newEmployment) {
		this.Employment = newEmployment;
	}

	/**
	 * Returns citys.
	 * @return citys 
	 */
	public City getCity() {
		return this.city;
	}

	/**
	 * Sets a value to attribute citys. 
	 * @param newCitys 
	 */
	public void setCitys(City newCitys) {
		this.city = newCitys;
	}

	/**
	 * Returns adresse.
	 * @return adresse 
	 */
	public String getAdresse() {
		return this.adresse;
	}

	/**
	 * Sets a value to attribute adresse. 
	 * @param newAdresse 
	 */
	public void setAdresse(String newAdresse) {
		this.adresse = newAdresse;
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
	 * Returns profileDescription.
	 * @return profileDescription 
	 */
	public String getProfileDescription() {
		return this.profileDescription;
	}

	/**
	 * Sets a value to attribute profileDescription. 
	 * @param newProfileDescription 
	 */
	public void setProfileDescription(String newProfileDescription) {
		this.profileDescription = newProfileDescription;
	}

	/**
	 * Returns Education.
	 * @return Education 
	 */
	public String getEducation() {
		return this.Education;
	}

	/**
	 * Sets a value to attribute Education. 
	 * @param newEducation 
	 */
	public void setEducation(String newEducation) {
		this.Education = newEducation;
	}

	/**
	 * Returns user.
	 * @return user 
	 */
	public User getUser() {
		return this.user;
	}

	


}
