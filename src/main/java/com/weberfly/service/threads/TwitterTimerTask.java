package com.weberfly.service.threads;

import java.util.Date;
import java.util.TimerTask;

import com.weberfly.entities.TwitterKeyWord;

public class TwitterTimerTask extends TimerTask {

	private String  keyWord;

	public TwitterTimerTask(String keyWord) {
		this.keyWord = keyWord;
	}

	

	@Override
	public void run() {
		System.out.println(" keyWord :"+this.keyWord + new Date());
	}

}
