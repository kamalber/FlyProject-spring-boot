package com.weberfly.util;

public class SentencePolarity {
	private String sentence;
	private String polarity;
	public SentencePolarity() {
	
	}
	public String getSentence() {
		return sentence;
	}
	public void setSentence(String sentence) {
		this.sentence = sentence;
	}
	public String getPolarity() {
		return polarity;
	}
	
	
	public void setPolarity(String polarity) {
		this.polarity = polarity;
	}
	@Override
	public String toString() {
		return "SentencePolarity [sentence=" + sentence + ", Polarity=" + polarity + "]";
	}

}
