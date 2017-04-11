package com.weberfly.entities;
/*******************************************************************************
 * 2017, All rights reserved.
 *******************************************************************************/

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

// Start of user code (user defined imports)

// End of user code

/**
 * Description of Article.
 * 
 * @author kamal
 */
@Entity
@Table(name="Article")
@PrimaryKeyJoinColumn(name = "id")
public class Article extends Publication {
	/**
	 * Description of the property title.
	 */
	private String title ;

	/**
	 * Description of the property content.
	 */
	private String content ;

	// Start of user code (user defined attributes for Article)

	// End of user code

	/**
	 * The constructor.
	 */
	public Article() {
		// Start of user code constructor for Article)
		super();
		// End of user code
	}

	// Start of user code (user defined methods for Article)

	// End of user code
	/**
	 * Returns title.
	 * @return title 
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * Sets a value to attribute title. 
	 * @param newTitle 
	 */
	public void setTitle(String newTitle) {
		this.title = newTitle;
	}

	/**
	 * Returns content.
	 * @return content 
	 */
	public String getContent() {
		return this.content;
	}

	/**
	 * Sets a value to attribute content. 
	 * @param newContent 
	 */
	public void setContent(String newContent) {
		this.content = newContent;
	}

}
