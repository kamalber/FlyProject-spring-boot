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
	private RegularUser user = new RegularUser();

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
	private List<Comment> comments = new ArrayList<Comment>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCommentNumber() {
		return commentNumber;
	}

	public void setCommentNumber(Long commentNumber) {
		this.commentNumber = commentNumber;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public RegularUser getUser() {
		return user;
	}

	public void setUser(RegularUser user) {
		this.user = user;
	}

	public List<Category> getCategorys() {
		return categorys;
	}

	public void setCategorys(List<Category> categorys) {
		this.categorys = categorys;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	

	// Start of user code (user defined attributes for Publication)

	// End of user code


}
