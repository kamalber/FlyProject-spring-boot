package com.weberfly.service.threads;


import java.util.Date;
import java.util.TimerTask;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.weberfly.entities.TwitterKeyWord;
import com.weberfly.service.TweetAnalyseService;
@Component
@Scope("prototype")
public class TwitterTimerTask extends TimerTask {
    

	@Autowired
	private TweetAnalyseService tweetservice;
	private TwitterKeyWord  keyWord;

	public TwitterTimerTask() {
		
	}
	public TwitterTimerTask(TwitterKeyWord keyWord) {
		this.keyWord = keyWord;	
	}
	public void setTweetAnalyseService(TweetAnalyseService service){
		this.tweetservice=service;
	}
public void setKeyWord(TwitterKeyWord key){
	this.keyWord=key;
}
	@Override
	public void run() {
	
		System.out.println(" keyWord :"+this.keyWord + new Date());
		try {
			tweetservice.collectTwittesAndAnalyseThem(this.keyWord);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		}
	}

}
