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

import com.fasterxml.jackson.annotation.JsonFormat;


// End of user code

/**
 * Description of Publication.
 * 
 * @author kamal
 */
@Entity
@Table(name="publication")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "pubs", discriminatorType = DiscriminatorType.STRING)


public   class Publication implements Serializable {
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
	private RegularUser user ;

	/**
	 * Description of the property categorys.
	 */
	@ManyToMany(cascade = CascadeType.ALL)
	 @JoinTable(name = "Pub_CategoryItem", joinColumns = @JoinColumn(name = "pub_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "catItem_id", referencedColumnName = "id"))
	private List<CategoryItem> categoryItems = new ArrayList<CategoryItem>();

	/**
	 * Description of the property comments.
	 */
	@OneToMany(cascade = CascadeType.ALL,mappedBy="publication")
	private List<Comment> comments ;

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


	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "EEE, d MMM yyyy 'at' hh:mm aaa",timezone = "GMT+1")
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

	
	public List<CategoryItem> getCategoryItems() {
		return categoryItems;
	}

	public void setCategoryItems(List<CategoryItem> categoryItems) {
		this.categoryItems = categoryItems;
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
