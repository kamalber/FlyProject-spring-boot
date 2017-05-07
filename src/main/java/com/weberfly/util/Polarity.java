package com.weberfly.util;

public class Polarity {

	private int positiveCount;
	private int negativeCount;
	private int  neutralCount;
	private float average;
	
	
	public float getAverage() {
		return average;
	}

	public void setAverage(float spline) {
		this.average = spline;
	}

	public Polarity() {
		super();
	}
	
	public Polarity(int positiveCount, int negativeCount, int neutralCount) {
		super();
		this.positiveCount = positiveCount;
		this.negativeCount = negativeCount;
		this.neutralCount = neutralCount;
	}
	public int getPositiveCount() {
		return positiveCount;
	}
	public void setPositiveCount(int positiveCount) {
		this.positiveCount = positiveCount;
	}
	public int getNegativeCount() {
		return negativeCount;
	}
	public void setNegativeCount(int negativeCount) {
		this.negativeCount = negativeCount;
	}
	public int getNeutralCount() {
		return neutralCount;
	}
	public void setNeutralCount(int neutralCount) {
		this.neutralCount = neutralCount;
	};
	
	
	
}
