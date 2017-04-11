package com.weberfly.entities;
/*******************************************************************************
 * 2017, All rights reserved.
 *******************************************************************************/

import javax.persistence.*;

// Start of user code (user defined imports)

// End of user code

/**
 * Description of Question.
 * 
 * @author kamal
 */
@Entity
@Table(name="Question")
@PrimaryKeyJoinColumn(name = "id")
public class Question extends Publication {
	/**
	 * Description of the property question.
	 */
	private String question ;

	// Start of user code (user defined attributes for Question)

	// End of user code

	/**
	 * The constructor.
	 */
	public Question() {
		// Start of user code constructor for Question)
		super();
		// End of user code
	}

	// Start of user code (user defined methods for Question)

	// End of user code
	/**
	 * Returns question.
	 * @return question 
	 */
	public String getQuestion() {
		return this.question;
	}

	/**
	 * Sets a value to attribute question. 
	 * @param newQuestion 
	 */
	public void setQuestion(String newQuestion) {
		this.question = newQuestion;
	}

}
