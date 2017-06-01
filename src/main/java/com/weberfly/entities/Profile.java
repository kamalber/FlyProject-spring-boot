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
	private String education ;

	/**
	 * Description of the property user.
	 */
	@OneToOne(cascade = CascadeType.ALL,mappedBy="profile"  )
	private User user ;
	/**
	 * Description of the property citys.
	 */
	@ManyToOne(cascade = CascadeType.ALL )
	private City city ;
	// Start of user code (user defined attributes for Profile)
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmployment() {
		return Employment;
	}
	public void setEmployment(String employment) {
		Employment = employment;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public String getProfileDescription() {
		return profileDescription;
	}
	public void setProfileDescription(String profileDescription) {
		this.profileDescription = profileDescription;
	}
	public String getEducation() {
		return this.education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public City getCity() {
		return city;
	}
	public void setCity(City city) {
		this.city = city;
	}
	
	

	




}
