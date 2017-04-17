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
 * Description of Post.
 * 
 * @author kamal
 */
@Entity
@Table(name="Post")
@PrimaryKeyJoinColumn(name = "id")
public class Post extends Publication {
	/**
	 * Description of the property title.
	 */

	/**
	 * Description of the property content.
	 */
	private String content ;
	private String title;

	
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
	
}
