package com.weberfly.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.weberfly.entities.Post.sentiment;

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

	@Enumerated(EnumType.ORDINAL)
    private Post.sentiment nltkSentment;
	@Enumerated(EnumType.ORDINAL)
    private Post.sentiment dumaxSentment;
	@Enumerated(EnumType.ORDINAL)
    private Post.sentiment gateSentment;
	@Enumerated(EnumType.ORDINAL)
	private Post.sentiment generalSentiment;
	
	private String datePublication;

	@ManyToOne(cascade = CascadeType.MERGE ,fetch= FetchType.EAGER)
	private TwitterKeyWord keyWord;
	
	public String getDatePublication() {
		return datePublication;
	}

	public void setDatePublication(String datePublication) {
		this.datePublication = datePublication;
	}

	

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


	public Post.sentiment getNltkSentment() {
		return nltkSentment;
	}

	public void setNltkSentment(Post.sentiment nltkSentment) {
		this.nltkSentment = nltkSentment;
	}

	public Post.sentiment getDumaxSentment() {
		return dumaxSentment;
	}

	public void setDumaxSentment(Post.sentiment dumaxSentment) {
		this.dumaxSentment = dumaxSentment;
	}

	public Post.sentiment getGateSentment() {
		return gateSentment;
	}

	public void setGateSentment(Post.sentiment gateSentment) {
		this.gateSentment = gateSentment;
	}

	public Post.sentiment getGeneralSentiment() {
		return generalSentiment;
	}

	public void setGeneralSentiment(Post.sentiment generalSentiment) {
		this.generalSentiment = generalSentiment;
	}

	public TwitterKeyWord getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(TwitterKeyWord keyWord) {
		this.keyWord = keyWord;
	}
	
}
