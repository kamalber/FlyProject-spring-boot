//package com.weberfly.service;
//
//import static org.hamcrest.CoreMatchers.instanceOf;
//
//import java.text.DateFormatSymbols;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//import java.util.Locale;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.weberfly.dao.PostRepository;
//import com.weberfly.entities.Post;
//import com.weberfly.util.CustomStatsParams;
//import com.weberfly.util.Polarity;
//import com.weberfly.util.SentimentStats;
//
//@Service
//public class StatHelperService<T> {
//	T t;
//	@Autowired
//	private PostRepository postRepository;
//	
//public SentimentStats<T> getPostStats(CustomStatsParams params) {
//		
//
//		if (params.getStartYear() != 0) {
//			if (params.getEndYear() != 0) {
//				// search between many years
//				return this.getSentimentStatsByYear(params.getQuery(), params);
//			} else if (params.getEndYear() == 0) {
//				if (params.getMonth() == 0) {
//					// search by months of year
//					return this.getSentimentStatsByMonthOfYear(params.getQuery(), params);
//				} else {
//					// search by days of month
//				}
//			}
//		}
//
//		return null;// nothing to search the start year is undefined
//
//	}
//private SentimentStats getSentimentStatsByMonthOfYear(String title, CustomStatsParams params) {
//	System.out.println("sentiments by month");
//	SentimentStats<T> stats = this.getAnalysedItemstatsTemplate(params, title);
//	if (stats == null) {
//		return null;
//	}
//	String[] months = new DateFormatSymbols(Locale.ENGLISH).getShortMonths();
//	int monthNumber = 1;
//	// the real statistics calcul is in this chappter
//	while (monthNumber <= 12 && monthNumber > 0) {
//		System.out.println("month " + monthNumber);
//		Polarity postPolarity = this.getPolarityPerMonth(monthNumber, stats);
//		stats.getLabelSeries().add(String.valueOf(monthNumber));
//		stats.getStatistics().put(String.valueOf(monthNumber), postPolarity);
//		stats.getPositiveDataCount().add(postPolarity.getPositiveCount());
//		stats.getNegativeDataCount().add(postPolarity.getNegativeCount());
//		stats.getNeutralDataCount().add(postPolarity.getNeutralCount());
//		stats.getAverageDataCount().add(postPolarity.getAverage());
//
//		monthNumber++;
//	}
//	return stats;
//}
//
//private SentimentStats<T> getSentimentStatsByYear(String title, CustomStatsParams params) {
//	System.out.println("sentiments by month");
//	SentimentStats<T> stats = this.getAnalysedItemstatsTemplate(params, title);
//	if (stats == null) {
//		return null;
//	}
//	int yearNumber = params.getEndYear() - params.getStartYear();
//	// the real statistics calcul is in this chappter
//	while (yearNumber >= 0) {
//		int year = params.getEndYear() - yearNumber;
//		stats.getLabelSeries().add(String.valueOf(year));
//		Polarity postPolarity = this.getPolarityPerYear(year, stats);
//		stats.getStatistics().put(String.valueOf(year), postPolarity);
//		stats.getPositiveDataCount().add(postPolarity.getPositiveCount());
//		stats.getNegativeDataCount().add(postPolarity.getNegativeCount());
//		stats.getNeutralDataCount().add(postPolarity.getNeutralCount());
//		stats.getAverageDataCount().add(postPolarity.getAverage());
//		yearNumber--;
//	}
//	System.out.println("statisticsss  " + stats.getPositiveDataCount());
//	return stats;
//
//}
//
//private SentimentStats<T> getAnalysedItemstatsTemplate(CustomStatsParams params, String title) {
//	SentimentStats<T> stats = new SentimentStats<T>();
//
//	Date dateStart = getStartDateFromYear(params.getStartYear());
//	Date dateEnd = params.getEndYear() != 0 ? getEndDateFromYear(params.getEndYear())
//			: getEndDateFromYear(params.getStartYear());
//
//	List<T> Items = postRepository.findByTitleAndDateBetween(params.getQuery(), dateStart, dateEnd);
//	if (Items.isEmpty()) {
//		return null;
//	}
//
//	String sentimentMethode = "";
//	if (params.getSentimentMethode() == 0) {
//		sentimentMethode = "getDumaxSentment";
//	} else if (params.getSentimentMethode() == 1) {
//		sentimentMethode = "getNltkSentment";
//	} else if (params.getSentimentMethode() == 2) {
//		sentimentMethode = "getOtherSentment";
//	}else{
//		sentimentMethode = "getOtherSentment";
//	}
//	for (T p : Items) {
//		Post.sentiment senti = null;
//		java.lang.reflect.Method getSentimentMethode;
//		try {
//			getSentimentMethode = p.getClass().getMethod(sentimentMethode);
//			senti = (Post.sentiment) getSentimentMethode.invoke(p);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		if (senti == Post.sentiment.positive) {
//			stats.getPositiveItems().add(p);
//		} else if (senti == Post.sentiment.neutral) {
//			stats.getNeutralItems().add(p);
//		} else if (senti == Post.sentiment.negative) {
//			stats.getNegativeItems().add(p);
//		}
//
//	}
//	return stats;
//}
//
///*********************** helper functions *************************/
//
//// this method return the plolarity for the onths of a year included in the
//// statistics calcul
//private Polarity getPolarityPerMonth(int monthNumber, SentimentStats<T> stats) {
//
//
//	Polarity polarity = new Polarity();
//	if(this.t instanceof Double)
//		
//	for (Post p : stats.getPositiveItems()) {
//		if ((p.getDate().getMonth() + 1) == monthNumber) {
//			System.out.println(" post positive id  " + p.getId());
//			polarity.setPositiveCount(polarity.getPositiveCount() + 1);
//		}
//	}
//	for (Post p : stats.getNegativeItems()) {
//		if ((p.getDate().getMonth() + 1) == monthNumber) {
//			System.out.println(" post negative id  " + p.getId());
//			polarity.setNegativeCount(polarity.getNegativeCount() + 1);
//		}
//	}
//	for (Post p : stats.getNeutralItems()) {
//		if ((p.getDate().getMonth() + 1) == monthNumber) {
//			polarity.setNeutralCount(polarity.getNeutralCount() + 1);
//			System.out.println(" post neutral id  " + p.getId());
//		}
//	}
//	// spline est la valeur moyenne
//	polarity.setAverage(
//			(float) (polarity.getNeutralCount() + polarity.getNegativeCount() + polarity.getPositiveCount()) / 3);
//
//	return polarity;
//}
//
//// this method return the polarity for every year included in the
//// statistics calcule
//private Polarity getPolarityPerYear(int year, SentimentStats<T> stats) {
//	if(this.t instanceof  Post){
//		
//	}
//	Polarity polarity = new Polarity();
//	for (Post p : stats.getPositiveItems()) {
//		if (p.getDate().getYear() + 1900 == year) {
//			polarity.setPositiveCount(polarity.getPositiveCount() + 1);
//		}
//	}
//	for (Post p : stats.getNegativeItems()) {
//		if (p.getDate().getYear() + 1900 == year) {
//			polarity.setNegativeCount(polarity.getNegativeCount() + 1);
//		}
//	}
//	for (Post p : stats.getNeutralItems()) {
//		if (p.getDate().getYear() + 1900 == year) {
//			polarity.setNeutralCount(polarity.getNeutralCount() + 1);
//		}
//	}
//	// spline est la valeur moyenne
//	polarity.setAverage(
//			(float) (polarity.getNeutralCount() + polarity.getNegativeCount() + polarity.getPositiveCount()) / 3);
//	return polarity;
//
//}
//
//private Date getStartDateFromYear(int year) {
//	Calendar cal = Calendar.getInstance();
//	cal.set(Calendar.YEAR, year);
//
//	cal.set(Calendar.DAY_OF_YEAR, 1);
//	cal.set(Calendar.HOUR_OF_DAY, 1);
//	Date dateStart = cal.getTime();
//	return dateStart;
//}
//
//private Date getEndDateFromYear(int year) {
//	Calendar cal = Calendar.getInstance();
//	cal.set(Calendar.YEAR, year);
//	cal.set(Calendar.MONTH, 11); // 12 = december
//	cal.set(Calendar.DAY_OF_MONTH, 31);
//	cal.set(Calendar.HOUR_OF_DAY, 12);
//	Date dateEnd = cal.getTime();
//	return dateEnd;
//
//}
//
//}
