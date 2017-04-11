package com.weberfly.entities;
/*******************************************************************************
 * 2017, All rights reserved.
 *******************************************************************************/

import java.io.Serializable;
import java.util.Date;
// Start of user code (user defined imports)

import javax.persistence.*;

// End of user code

/**
 * Description of Message.
 * 
 * @author kamal
 */
@Entity
@Table(name="Message")
public class Message implements Serializable{
	
	/**
	 * Description of the property id.
	 */
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id ;
	/**
	 * Description of the property sender.
	 */
	@ManyToOne(cascade = CascadeType.ALL )
	private User sender = new RegularUser();

	/**
	 * Description of the property date.
	 */
	private Date date = new Date();

	/**
	 * Description of the property receiver.
	 */
	@ManyToOne(cascade = CascadeType.ALL )
	private User receiver = new RegularUser();
    /**
	 * Description of the property content.
	 */
	private String content ;

	// Start of user code (user defined attributes for Message)

	// End of user code

	/**
	 * The constructor.
	 */
	public Message() {
		// Start of user code constructor for Message)
		super();
		// End of user code
	}

	// Start of user code (user defined methods for Message)

	// End of user code
	/**
	 * Returns sender.
	 * @return sender 
	 */
	public User getSender() {
		return this.sender;
	}

	/**
	 * Sets a value to attribute sender. 
	 * @param newSender 
	 */
	public void setSender(User newSender) {
		this.sender = newSender;
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
	 * Returns receiver.
	 * @return receiver 
	 */
	public User getReceiver() {
		return this.receiver;
	}

	/**
	 * Sets a value to attribute receiver. 
	 * @param newReceiver 
	 */
	public void setReceiver(User newReceiver) {
		this.receiver = newReceiver;
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
