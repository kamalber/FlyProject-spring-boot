package com.weberfly.service;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.text.DateFormatSymbols;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.swing.text.html.HTMLDocument.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.weberfly.dao.TwitterRepository;

import com.weberfly.entities.Post;
import com.weberfly.entities.Twitte;
import com.weberfly.entities.TwitterKeyWord;
import com.weberfly.util.CustomStatsParams;
import com.weberfly.util.Polarity;
import com.weberfly.util.TwitteSentimentStas;

@Service
public class TwitterService {
	@Autowired
	private TwitterRepository twitterRepository;

	public void save(Twitte twitte) {
		twitterRepository.save(twitte);
	}

	public List<Twitte> findAll() {
		return twitterRepository.findAll();
	}

	public Twitte find(Long id) {
		return twitterRepository.findOne(id);
	}

	public Boolean isExist(Twitte twitte) {
		return twitterRepository.findOneByText(twitte.getText()) != null;
	}

	public void delete(Long id) {
		twitterRepository.delete(id);
	}

	public Map<String, Long> getToatalStats(TwitterKeyWord keyWord) {
		Map<String, Long> stats = new HashMap<String, Long>();
		Long positive = twitterRepository.countSentimentByKeyWordAnd(keyWord, Post.sentiment.positive);
		Long neutral = twitterRepository.countSentimentByKeyWordAnd(keyWord, Post.sentiment.neutral);
		Long negative = twitterRepository.countSentimentByKeyWordAnd(keyWord, Post.sentiment.negative);
		if(positive==0 && negative==0 && neutral==0){
			return null;
			}
		stats.put("positive", positive);
		stats.put("neutral", neutral);
		stats.put("negative", negative);
		return stats;
	}

	public TwitteSentimentStas getStats(CustomStatsParams params) {

		if (params.getStartYear() != 0) {
			if (params.getEndYear() != 0) {
				// search between many years
				return this.getSentimentStatsByYear(params.getQuery(), params);
			} else if (params.getEndYear() == 0) {
				if (params.getMonth() == 0) {
					// search by months of year
					return this.getSentimentStatsByMonthOfYear(params.getQuery(), params);
				} else {
					// search by days of month
				}
			}
		}

		return null;// nothing to search the start year is undefined

	}

	private TwitteSentimentStas getSentimentStatsByMonthOfYear(String title, CustomStatsParams params) {
		System.out.println("sentiments by month");
		TwitteSentimentStas stats = this.getAnalysedItemstatsTemplate(params, title);
		if (stats == null) {
			return null;
		}

		String[] months = new DateFormatSymbols(Locale.ENGLISH).getShortMonths();
		int monthNumber = 1;
		// the real statistics calcul is in this chappter
		while (monthNumber <= 12 && monthNumber > 0) {
			System.out.println("month " + monthNumber);
			Polarity polarity = this.getPolarityPerMonth(monthNumber, stats);
			stats.getLabelSeries().add(String.valueOf(monthNumber));
			stats.getStatistics().put(String.valueOf(monthNumber), polarity);
			stats.getPositiveDataCount().add(polarity.getPositiveCount());
			stats.getNegativeDataCount().add(polarity.getNegativeCount());
			stats.getNeutralDataCount().add(polarity.getNeutralCount());
			stats.getAverageDataCount().add(polarity.getAverage());

			monthNumber++;
		}
		return stats;
	}

	private TwitteSentimentStas getSentimentStatsByYear(String title, CustomStatsParams params) {
		System.out.println("sentiments by month");
		TwitteSentimentStas stats = this.getAnalysedItemstatsTemplate(params, title);
		if (stats == null) {
			return null;
		}

		int yearNumber = params.getEndYear() - params.getStartYear();
		// the real statistics calcul is in this chappter
		while (yearNumber >= 0) {

			int year = params.getEndYear() - yearNumber;
			stats.getLabelSeries().add(String.valueOf(year));
			Polarity polarity = this.getPolarityPerYear(year, stats);
			stats.getStatistics().put(String.valueOf(year), polarity);
			stats.getPositiveDataCount().add(polarity.getPositiveCount());
			stats.getNegativeDataCount().add(polarity.getNegativeCount());
			stats.getNeutralDataCount().add(polarity.getNeutralCount());
			stats.getAverageDataCount().add(polarity.getAverage());
			yearNumber--;
		}
		System.out.println("statisticsss  " + stats.getPositiveDataCount());
		return stats;

	}

	private TwitteSentimentStas getAnalysedItemstatsTemplate(CustomStatsParams params, String title) {
		TwitteSentimentStas stats = new TwitteSentimentStas();

		Date dateStart = getStartDateFromYear(params.getStartYear());
		Date dateEnd = params.getEndYear() != 0 ? getEndDateFromYear(params.getEndYear())
				: getEndDateFromYear(params.getStartYear());

		List<Twitte> Items = twitterRepository.findByKeyWordAndDateBetween(params.getKeyWord(), dateStart, dateEnd);
		if (Items.isEmpty()) {
			return null;
		}

		String sentimentMethode = "";
		if (params.getSentimentMethode() == 0) {
			sentimentMethode = "getDumaxSentment";
		} else if (params.getSentimentMethode() == 1) {
			sentimentMethode = "getNltkSentment";
		} else {
			sentimentMethode = "getOtherSentment";
		}
		for (Twitte p : Items) {
			Post.sentiment senti = null;
			java.lang.reflect.Method getSentimentMethode;
			try {
				getSentimentMethode = p.getClass().getMethod(sentimentMethode);
				senti = (Post.sentiment) getSentimentMethode.invoke(p);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (senti == Post.sentiment.positive) {
				stats.getPositiveItems().add(p);
			} else if (senti == Post.sentiment.neutral) {
				stats.getNeutralItems().add(p);
			} else if (senti == Post.sentiment.negative) {
				stats.getNegativeItems().add(p);
			}

		}
		return stats;
	}

	/*********************** helper functions *************************/

	// this method return the plolarity for the onths of a year included in the
	// statistics calcul
	private Polarity getPolarityPerMonth(int monthNumber, TwitteSentimentStas stats) {

		Polarity polarity = new Polarity();
		for (Twitte p : stats.getPositiveItems()) {

			if ((p.getDate().getMonth() + 1) == monthNumber) {
				System.out.println(" post positive id  " + p.getId());
				polarity.setPositiveCount(polarity.getPositiveCount() + 1);
			}
		}
		for (Twitte p : stats.getNegativeItems()) {
			if ((p.getDate().getMonth() + 1) == monthNumber) {
				System.out.println(" post negative id  " + p.getId());
				polarity.setNegativeCount(polarity.getNegativeCount() + 1);
			}
		}
		for (Twitte p : stats.getNeutralItems()) {
			if ((p.getDate().getMonth() + 1) == monthNumber) {
				polarity.setNeutralCount(polarity.getNeutralCount() + 1);
				System.out.println(" post neutral id  " + p.getId());
			}
		}
		// spline est la valeur moyenne
		polarity.setAverage(
				(float) (polarity.getNeutralCount() + polarity.getNegativeCount() + polarity.getPositiveCount()) / 3);

		return polarity;
	}

	// this method return the plolarity for every year included in the
	// statistics calcul
	private Polarity getPolarityPerYear(int year, TwitteSentimentStas stats) {
		Polarity polarity = new Polarity();
		for (Twitte p : stats.getPositiveItems()) {
			if (p.getDate().getYear() + 1900 == year) {
				polarity.setPositiveCount(polarity.getPositiveCount() + 1);
			}
		}
		for (Twitte p : stats.getNegativeItems()) {
			if (p.getDate().getYear() + 1900 == year) {
				polarity.setNegativeCount(polarity.getNegativeCount() + 1);
			}
		}
		for (Twitte p : stats.getNeutralItems()) {
			if (p.getDate().getYear() + 1900 == year) {
				polarity.setNeutralCount(polarity.getNeutralCount() + 1);
			}
		}
		// spline est la valeur moyenne
		polarity.setAverage(
				(float) (polarity.getNeutralCount() + polarity.getNegativeCount() + polarity.getPositiveCount()) / 3);
		return polarity;

	}

	private Date getStartDateFromYear(int year) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);

		cal.set(Calendar.DAY_OF_YEAR, 1);
		cal.set(Calendar.HOUR_OF_DAY, 1);
		Date dateStart = cal.getTime();
		return dateStart;
	}

	private Date getEndDateFromYear(int year) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, 11); // 12 = december
		cal.set(Calendar.DAY_OF_MONTH, 31);
		cal.set(Calendar.HOUR_OF_DAY, 12);
		Date dateEnd = cal.getTime();
		return dateEnd;

	}
}
