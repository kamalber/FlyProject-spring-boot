package com.weberfly.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




public class SentimentStats<T>{

private List<T> positiveItems=new ArrayList<>();

private List<T> negativeItems=new ArrayList<>();

private List<T> neutralItems=new ArrayList<>();

private List<String> labelSeries=new ArrayList<>();

private Map<String ,Polarity> statistics =  new HashMap<String ,Polarity>();

private Map<String ,ArrayList<Integer>> data =  new HashMap<String ,ArrayList<Integer>>();

private List<Integer> positiveDataCount=new ArrayList<>();
private List<Integer> negativeDataCount=new ArrayList<>();
private List<Integer> neutralDataCount=new ArrayList<>();
private List<Float> averageDataCount=new ArrayList<>();



public Map<String, ArrayList<Integer>> getData() {
	return data;
}

public void setData(Map<String, ArrayList<Integer>> data) {
	this.data = data;
}



public List<Integer> getPositiveDataCount() {
	return positiveDataCount;
}

public void setPositiveDataCount(List<Integer> positiveDataCount) {
	this.positiveDataCount = positiveDataCount;
}

public List<Integer> getNegativeDataCount() {
	return negativeDataCount;
}

public void setNegativeDataCount(List<Integer> negativeDataCount) {
	this.negativeDataCount = negativeDataCount;
}

public List<Integer> getNeutralDataCount() {
	return neutralDataCount;
}

public void setNeutralDataCount(List<Integer> neutralDataCount) {
	this.neutralDataCount = neutralDataCount;
}

public List<Float> getAverageDataCount() {
	return averageDataCount;
}

public void setAverageDataCount(List<Float> splineDataCount) {
	this.averageDataCount = splineDataCount;
}

public List<T> getPositiveItems() {
	return positiveItems;
}

public void setPositiveItems(List<T> positivePosts) {
	this.positiveItems = positivePosts;
}

public List<T> getNegativeItems() {
	return negativeItems;
}

public void setNegativeItems(List<T> negativePosts) {
	this.negativeItems = negativePosts;
}

public List<T> getNeutralItems() {
	return neutralItems;
}

public void setNeutralItems(List<T> neutralPost) {
	this.neutralItems = neutralPost;
}

public List<String> getLabelSeries() {
	return labelSeries;
}

public void setLabelSeries(List<String> labelSeries) {
	this.labelSeries = labelSeries;
}

public Map<String, Polarity> getStatistics() {
	return statistics;
}

public void setStatistics(Map<String, Polarity> statistics) {
	this.statistics = statistics;
}



	
}
