package com.weberfly.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Twitte")
public class Twitte implements Serializable {

	/**
	 * Description of the property id.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String text;
	private Post.sentiment sentiment;

	@OneToOne(cascade = CascadeType.ALL)
	private TwitterKeyWord keyWord=new TwitterKeyWord();

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

	public Post.sentiment getSentiment() {
		return sentiment;
	}

	public void setSentiment(Post.sentiment sentiment) {
		this.sentiment = sentiment;
	}

	public TwitterKeyWord getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(TwitterKeyWord keyWord) {
		this.keyWord = keyWord;
	}
	
}
