package com.weberfly.util;

import com.weberfly.entities.TwitterKeyWord;

public class CustomStatsParams {

	private int startYear;
	private int endYear;
	private int month;
	private String query;
	private int sentimentMethode;
	private TwitterKeyWord keyWord;

	public int getSentimentMethode() {
		return sentimentMethode;
	}

	public void setSentimentMethode(int sentimentMethode) {
		this.sentimentMethode = sentimentMethode;
	}

	public int getStartYear() {
		return startYear;
	}

	public void setStartYear(int startYear) {
		this.startYear = startYear;
	}

	public int getEndYear() {
		return endYear;
	}

	public void setEndYear(int endYear) {
		this.endYear = endYear;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String querySearche) {
		this.query = querySearche;
	}

	public TwitterKeyWord getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(TwitterKeyWord keyWord) {
		this.keyWord = keyWord;
	}

	@Override
	public String toString() {
		return "CustomStatsParams [startYear=" + startYear + ", endYear=" + endYear + ", month=" + month + ", query="
				+ query + ", sentimentMethode=" + sentimentMethode + "]";
	}

}
