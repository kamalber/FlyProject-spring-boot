package com.weberfly.util;

import java.util.ArrayList;
import java.util.List;

public class CommentSentimentStats {
	private List<String> labelSeries=new ArrayList<>();
	private List<Long> positiveDataCount = new ArrayList<>();
	private List<Long> negativeDataCount = new ArrayList<>();
	private List<Long> neutralDataCount = new ArrayList<>();
	private List<Float> averageDataCount = new ArrayList<>();
	private long positiveCount;
	private long neutralCount;
	private long negativeCount;

	public List<String> getLabelSeries() {
		return labelSeries;
	}

	public void setLabelSeries(List<String> labelSerires) {
		this.labelSeries = labelSerires;
	}

	public List<Long> getPositiveDataCount() {
		return positiveDataCount;
	}

	public void setPositiveDataCount(List<Long> positiveDataCount) {
		this.positiveDataCount = positiveDataCount;
	}

	public List<Long> getNegativeDataCount() {
		return negativeDataCount;
	}

	public void setNegativeDataCount(List<Long> negativeDataCount) {
		this.negativeDataCount = negativeDataCount;
	}

	public List<Long> getNeutralDataCount() {
		return neutralDataCount;
	}

	public void setNeutralDataCount(List<Long> neutralDataCount) {
		this.neutralDataCount = neutralDataCount;
	}

	public List<Float> getAverageDataCount() {
		return averageDataCount;
	}

	public void setAverageDataCount(List<Float> averageDataCount) {
		this.averageDataCount = averageDataCount;
	}

	public long setPositiveCount() {
		long result = 0;
		for (Long item : positiveDataCount) {
			result += item;
		}
		this.positiveCount=result;
		return result;
	}
	public long setNegativeCount() {
		long result = 0;
		for (Long item : negativeDataCount) {
			result += item;
		}
		this.negativeCount=result;
		return result;
	}
	public long setNeutralCount() {
		long result = 0;
		for (Long item : neutralDataCount) {
			result += item;
		}
		this.neutralCount=result;
		return result;
	}

	public long getPositiveCount() {
		return positiveCount;
	}

	public long getNeutralCount() {
		return neutralCount;
	}

	public long getNegativeCount() {
		return negativeCount;
	}
	
}
