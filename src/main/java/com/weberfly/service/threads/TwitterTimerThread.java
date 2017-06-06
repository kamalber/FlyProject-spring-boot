package com.weberfly.service.threads;

import java.util.Date;
import java.util.Timer;

import com.weberfly.entities.TwitterKeyWord;
import com.weberfly.entities.TwitterKeyWord.Period;

public class TwitterTimerThread extends Timer {

	private TwitterKeyWord.Period period;
	private Date StartDate;
	private String keyWord;

	private TwitterTimerTask timerTask;

	public TwitterTimerThread(String keyWord, Date startDate, TwitterKeyWord.Period period) {
		super(keyWord);
		this.StartDate = startDate;
		this.period = period;
		timerTask = new TwitterTimerTask(keyWord);
	}

	public void createTAsk() {
		System.out.println("dd" + this.StartDate + " period" + this.period);
		long period = 0;
		if (this.period == Period.hourly) {
			this.schedule(timerTask, this.StartDate, 3600000);
		} else if (this.period == Period.daily) {
			period = 86400000 * 7;
			this.schedule(timerTask, this.StartDate, 86400000);
		} else {
			this.schedule(timerTask, this.StartDate, 86400000*7);
		}

	}

	// public static void main(String args[]){
	// TwitterKeyWord k=new TwitterKeyWord();
	// k.setPeriod(new Long(500));
	// k.setWord("java");
	// k.setStartDate(new Date());
	//
	// TwitterTimerThread t=new TwitterTimerThread(k);
	// t.createTAsk();
	//
	// }

}
