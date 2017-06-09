package com.weberfly.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Twitte")
public class Twitte extends Publication implements Serializable {

	/**
	 * Description of the property id.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	
	private String text;

	@ManyToOne(cascade = CascadeType.MERGE ,fetch= FetchType.EAGER)
	private TwitterKeyWord keyWord;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public TwitterKeyWord getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(TwitterKeyWord keyWord) {
		this.keyWord = keyWord;
	}
	
}
