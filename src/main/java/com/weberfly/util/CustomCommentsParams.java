package com.weberfly.util;

import java.util.Date;

import com.weberfly.entities.Post;

public class CustomCommentsParams {
	private String startDate;
	private String endDate;
	private Post post;

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

}
