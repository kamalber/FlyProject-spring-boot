package com.weberfly.entities;
/*******************************************************************************
 * 2017, All rights reserved.
 *******************************************************************************/

import java.util.Date;

import javax.persistence.*;

// Start of user code (user defined imports)

// End of user code

/**
 * Description of Event.
 * 
 * @author kamal
 */
@Entity
@Table(name="Event")
@PrimaryKeyJoinColumn(name = "id")
public class Event extends Publication {
	/**
	 * Description of the property title.
	 */
	private String title ;

	/**
	 * Description of the property description.
	 */
	private String description ;

	/**
	 * Description of the property event_date.
	 */
	private Date event_date ;

	/**
	 * Description of the property content.
	 */
	private String content ;

	// Start of user code (user defined attributes for Event)

	// End of user code

	/**
	 * The constructor.
	 */

	// Start of user code (user defined methods for Event)

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
	 * Returns description.
	 * @return description 
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Sets a value to attribute description. 
	 * @param newDescription 
	 */
	public void setDescription(String newDescription) {
		this.description = newDescription;
	}

	/**
	 * Returns event_date.
	 * @return event_date 
	 */
	public Date getEvent_date() {
		return this.event_date;
	}

	/**
	 * Sets a value to attribute event_date. 
	 * @param newEvent_date 
	 */
	public void setEvent_date(Date newEvent_date) {
		this.event_date = newEvent_date;
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
