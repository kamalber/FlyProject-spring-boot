package com.weberfly.service.threads;
import java.util.Timer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;


import com.weberfly.entities.TwitterKeyWord;
import com.weberfly.entities.TwitterKeyWord.Period;
import com.weberfly.service.TweetAnalyseService;

public class TwitterTimerThread extends Timer {

	@Autowired
	private ApplicationContext applicationContext;


	
	
	private TwitterKeyWord keyWord;

	private TwitterTimerTask timerTask;

	public TwitterTimerThread(TwitterKeyWord keyword,TweetAnalyseService tweetservice) {
		super(keyword.getWord());
		this.keyWord=keyword;
		timerTask = new TwitterTimerTask(keyWord);
		timerTask.setTweetAnalyseService(tweetservice);
		
		
	}

	public void createTAsk() {
		System.out.println("dd" +this.keyWord.getStartDate() + " period" + this.keyWord.getWord());
		long period = 0;
		if (this.keyWord.getPeriod() == Period.hourly) {
			this.schedule(timerTask, this.keyWord.getStartDate(), 86400000/5);
		} else if (this.keyWord.getPeriod() == Period.daily) {
			period = 86400000 * 7;
			this.schedule(timerTask,this.keyWord.getStartDate(), 86400000);
		} else {
			this.schedule(timerTask, this.keyWord.getStartDate(), 86400000*7);
		}

	}


}
