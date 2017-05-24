package com.weberfly.entities;
/*******************************************************************************
 * 2017, All rights reserved.
 *******************************************************************************/

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

// Start of user code (user defined imports)

// End of user code

/**
 * Description of Post.
 * 
 * @author kamal
 */
@Entity
@Table(name="Post")
@PrimaryKeyJoinColumn(name = "id")
@DiscriminatorValue("post")
public class Post extends Publication {
	public static enum sentiment{
		positive,
		negative,
		neutratl,
	}
	/**
	 * Description of the property title.
	 */

	/**
	 * Description of the property content.
	 */
	private String content ;
	private String title;

	@Enumerated(EnumType.ORDINAL)
    private sentiment nltkSentment;
	@Enumerated(EnumType.ORDINAL)
    private sentiment dumaxSentment;
	@Enumerated(EnumType.ORDINAL)
    private sentiment gateSentment;
	@Enumerated(EnumType.ORDINAL)
	private sentiment generalSentiment;
	// Start of user code (user defined attributes for Post)


	// End of user code

	/**
	 * The constructor.
	 */
	public Post() {
		// Start of user code constructor for Post)
		super();
		// End of user code
	}

	// Start of user code (user defined methods for Post)

	// End of user code
	/**
	 * Returns title.
	 * @return title 
	 */
	
	public String getContent() {
		return this.content;
	}

	public void setContent(String newContent) {
		this.content = newContent;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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
