package com.weberfly.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.weberfly.entities.Post;


public class SentimentStats{

private List<Post> positivePosts=new ArrayList<>();

private List<Post> negativePosts=new ArrayList<>();

private List<Post> neutralPost=new ArrayList<>();

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

public List<Post> getPositivePosts() {
	return positivePosts;
}

public void setPositivePosts(List<Post> positivePosts) {
	this.positivePosts = positivePosts;
}

public List<Post> getNegativePosts() {
	return negativePosts;
}

public void setNegativePosts(List<Post> negativePosts) {
	this.negativePosts = negativePosts;
}

public List<Post> getNeutralPost() {
	return neutralPost;
}

public void setNeutralPost(List<Post> neutralPost) {
	this.neutralPost = neutralPost;
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
