package com.weberfly.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="TwitterKeyWord")
public class TwitterKeyWord {

	/**
	 * Description of the property id.
	 */
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id ;
	
	private String word;
	
	/**
	 * Description of the property comments.
	 */
	@OneToMany(cascade = CascadeType.ALL,mappedBy="keyWord")
	private List<Twitte> twittes;

	@ManyToMany(mappedBy="categoryItems")
	private List<Publication> twitterKeyWords ;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public void setTwittes(List<Twitte> twittes) {
		this.twittes = twittes;
	}

	public void setTwitterKeyWords(List<Publication> twitterKeyWords) {
		this.twitterKeyWords = twitterKeyWords;
	}
	
}
