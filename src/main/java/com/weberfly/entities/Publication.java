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
import com.weberfly.entities.Post.sentiment;


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

	@Enumerated(EnumType.ORDINAL)
    private sentiment nltkSentment;
	@Enumerated(EnumType.ORDINAL)
    private sentiment dumaxSentment;
	@Enumerated(EnumType.ORDINAL)
    private sentiment gateSentment;
	@Enumerated(EnumType.ORDINAL)
	private sentiment generalSentiment;

	@ManyToOne(cascade= CascadeType.ALL)
	private Location location;
	/**
	 * Description of the property categorys.
	 */
	@ManyToMany(cascade = CascadeType.ALL)
	 @JoinTable(name = "Pub_CategoryItem", joinColumns = @JoinColumn(name = "pub_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "catItem_id", referencedColumnName = "id"))
	private List<CategoryItem> categoryItems = new ArrayList<CategoryItem>();

	

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

	public List<CategoryItem> getCategoryItems() {
		return categoryItems;
	}

	public void setCategoryItems(List<CategoryItem> categoryItems) {
		this.categoryItems = categoryItems;
	}


	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public sentiment getNltkSentment() {
		return nltkSentment;
	}

	public void setNltkSentment(sentiment nltkSentment) {
		this.nltkSentment = nltkSentment;
	}

	public sentiment getDumaxSentment() {
		return dumaxSentment;
	}

	public void setDumaxSentment(sentiment dumaxSentment) {
		this.dumaxSentment = dumaxSentment;
	}

	public sentiment getGateSentment() {
		return gateSentment;
	}

	public void setGateSentment(sentiment gateSentment) {
		this.gateSentment = gateSentment;
	}

	public sentiment getGeneralSentiment() {
		return generalSentiment;
	}

	public void setGeneralSentiment(sentiment generalSentiment) {
		this.generalSentiment = generalSentiment;
	}

}
