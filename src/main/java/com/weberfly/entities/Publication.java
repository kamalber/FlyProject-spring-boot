package com.weberfly.entities;
/*******************************************************************************
 * 2017, All rights reserved.
 *******************************************************************************/

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
// Start of user code (user defined imports)
import java.util.List;

import javax.persistence.*;


// End of user code

/**
 * Description of Publication.
 * 
 * @author kamal
 */
@Entity
@Table(name="publication")
@Inheritance(strategy = InheritanceType.JOINED)
public  class Publication implements Serializable {
	/**
	 * Description of the property id.
	 */
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id ;

	/**
	 * Description of the property commentNumber.
	 */
	private Long commentNumber ;

	/**
	 * Description of the property date.
	 */
	private Date date = new Date();

	/**
	 * Description of the property user.
	 */
	@ManyToOne(cascade = CascadeType.ALL )
	public RegularUser user = new RegularUser();

	/**
	 * Description of the property categorys.
	 */
	@ManyToMany(cascade = CascadeType.ALL)
	 @JoinTable(name = "Bub_Category", joinColumns = @JoinColumn(name = "pub_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "cat_id", referencedColumnName = "id"))
	private List<Category> categorys = new ArrayList<Category>();

	/**
	 * Description of the property comments.
	 */
	@OneToMany(cascade = CascadeType.ALL,mappedBy="publication")
	public List<Comment> comments = new ArrayList<Comment>();

	// Start of user code (user defined attributes for Publication)

	// End of user code

	/**
	 * The constructor.
	 */
	public Publication() {
		// Start of user code constructor for Publication)
		super();
		// End of user code
	}

	// Start of user code (user defined methods for Publication)

	// End of user code
	/**
	 * Returns commentNumber.
	 * @return commentNumber 
	 */
	public Long getCommentNumber() {
		return this.commentNumber;
	}

	/**
	 * Sets a value to attribute commentNumber. 
	 * @param newCommentNumber 
	 */
	public void setCommentNumber(Long newCommentNumber) {
		this.commentNumber = newCommentNumber;
	}

	/**
	 * Returns date.
	 * @return date 
	 */
	public Date getDate() {
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

	/**
	 * Returns categorys.
	 * @return categorys 
	 */
	

	public List<Category> getCategorys() {
		return categorys;
	}

	public void setCategorys(List<Category> categorys) {
		this.categorys = categorys;
	}

	/**
	 * Returns id.
	 * @return id 
	 */
	public Long getId() {
		return this.id;
	}



	/**
	 * Returns comments.
	 * @return comments 
	 */
	public List<Comment> getComments() {
		return this.comments;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

}
