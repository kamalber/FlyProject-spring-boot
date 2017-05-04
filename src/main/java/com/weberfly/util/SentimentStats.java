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

private ArrayList<Integer> positiveDataCount=new ArrayList<>();
private ArrayList<Integer> negativeDataCount=new ArrayList<>();
private ArrayList<Integer> neutralDataCount=new ArrayList<>();
private ArrayList<Integer> splineDataCount=new ArrayList<>();



public Map<String, ArrayList<Integer>> getData() {
	return data;
}

public void setData(Map<String, ArrayList<Integer>> data) {
	this.data = data;
}

public ArrayList<Integer> getPositiveDataCount() {
	return positiveDataCount;
}

public void setPositiveDataCount(ArrayList<Integer> positiveDataCount) {
	this.positiveDataCount = positiveDataCount;
}

public ArrayList<Integer> getNegativeDataCount() {
	return negativeDataCount;
}

public void setNegativeDataCount(ArrayList<Integer> negativeDataCount) {
	this.negativeDataCount = negativeDataCount;
}

public ArrayList<Integer> getNeutralDataCount() {
	return neutralDataCount;
}

public void setNeutralDataCount(ArrayList<Integer> neutralDataCount) {
	this.neutralDataCount = neutralDataCount;
}

public ArrayList<Integer> getSplineDataCount() {
	return splineDataCount;
}

public void setSplineDataCount(ArrayList<Integer> splineDataCount) {
	this.splineDataCount = splineDataCount;
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
