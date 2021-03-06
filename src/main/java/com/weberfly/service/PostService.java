package com.weberfly.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.IOException;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.weberfly.dao.CategoryItemRepository;
import com.weberfly.dao.PostRepository;
import com.weberfly.dao.PublicationRepository;
import com.weberfly.entities.CategoryItem;
import com.weberfly.entities.Location;
import com.weberfly.entities.Post;
import com.weberfly.entities.Publication;
import com.weberfly.entities.User;
import com.weberfly.util.CustomStatsParams;
import com.weberfly.util.Polarity;
import com.weberfly.util.PostSentimentStats;
import com.weberfly.util.SentimentStats;

@Service
public class PostService {

	@Autowired
	private PostRepository postRepository;
	@Autowired
	private PublicationRepository publicationRepository;
	@Autowired
	TweetAnalyseService tweetAnalyseService;
	@Autowired
	LocationService locatonService;
	@Autowired
	HttpSession session;

	public static final String USER_AGENT = "Mozilla/5.0";

	public static String[] readLines(URL url) throws IOException {

		Reader fileReader = new InputStreamReader(url.openStream(), Charset.forName("UTF-8"));
		List<String> lines;
		try (BufferedReader bufferedReader = new BufferedReader(fileReader)) {
			lines = new ArrayList<>();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				lines.add(line);
			}
		}

		return lines.toArray(new String[lines.size()]);
	}

	public List<Post> findByUser(User u) {
		return postRepository.findByUser(u);
	}

	public void savePost(Post post) throws Exception {
		String content = post.getContent();

		// System.out.println(getTestAnalyseByCombining(tests));
		// System.out.println("Dumax"+getTestAnalyseByDumax(tests));
		// System.out.println("this is the rate analysis dumax " +
		// getAnalyseRateForDumax());
		// System.out.println("this is the rate analysis nltk " +
		// getAnalyseRateForNLTK());
		// System.out.println("this is the rate analysis gate " +
		// getAnalyseRateForGate());
		// System.out.println(getTwittersByKeyWord("messi"));
		// System.out.println("Dddd"+getAnalysisTweetsByDumax("zafzafi"));
		// System.out.println("nnnn"+getAnalysisTweetsByNLTK("zafzafi"));
		// System.out.println("Gggg"+getAnalysisTweetsByGate("ronaldo"));
		// System.out.println("Gggg"+getTestAnalyseByGate(tests));
		// System.out.println("CCCC"+getAnalysisTweetsBycombining("ronaldo"));

		// GateSentiment
		System.out.println(content);
		String gatesentiment = tweetAnalyseService.getAnalyseByGateApi(content);
		// System.out.println(gatesentiment);
		Post.sentiment sentGate = Post.sentiment.valueOf(gatesentiment);
		post.setGateSentment(sentGate);

		// DumaxSentiement
		String dumaxsentiment = tweetAnalyseService.getAnalyseByDumax(content);
		// System.out.println("----------------------"+dumaxsentiment);
		Post.sentiment sentDumax = Post.sentiment.valueOf(dumaxsentiment);
		post.setDumaxSentment(sentDumax);

		// NLTK Sentiment
		
		String nltkSentiment = tweetAnalyseService.getAnalyseByNLTK(content);
		Post.sentiment sentNltk = Post.sentiment.valueOf(nltkSentiment);
		post.setNltkSentment(sentNltk);
		System.out.println("nltk" +nltkSentiment+" dumax "+dumaxsentiment+"gate "+gatesentiment);
		// General sentiment
		String generalSentiment = tweetAnalyseService.getMaxPolarityByTools(gatesentiment, dumaxsentiment,
				nltkSentiment);
		Post.sentiment sentGeneral = Post.sentiment.valueOf(generalSentiment);
		post.setGeneralSentiment(sentGeneral);

		// List<CategoryItem> categoryItems = categoryItemRepository.findAll();
		// List<CategoryItem> pubcategoryItems = new ArrayList<>();
		// for (CategoryItem item : categoryItems) {
		// if (content.toLowerCase().contains(item.getName().toLowerCase())) {
		// pubcategoryItems.add(item);
		// }
		// }
		// post.setCategoryItems(pubcategoryItems);

	
		User u = (User) session.getAttribute("connected");
		Location location=locatonService.find(u.getSession().getIpUser());
		post.setLocation(location);
		if (u != null) {
			 post.setUser(u);
		}
		postRepository.save(post);
	}

	public List<Post> getAll() {
		return postRepository.findAll();

	}

	public Post findPost(Long id) {
		return postRepository.findOne(id);
	}

	public Publication find(Long id) {
		return publicationRepository.findOne(id);
	}

	// get analysed post between two dates

	// get analysed post between two dates
	public List<Post> getAnalysedPosts(String querySearche, String startDate, String endDate) {

		DateFormat formatter = new SimpleDateFormat("yy-dd-MM");
//		CategoryItem item = categoryItemRepository.findByNameIgnoreCase(querySearche);
//		List<CategoryItem> items = null;
	
		
			Date dateStart, dateEnd;
			try {
				dateStart = formatter.parse(startDate);
				dateEnd = formatter.parse(endDate);
				return postRepository.findByTitleAndDateBetween(querySearche, dateStart, dateEnd);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		return null;
	}

	public PostSentimentStats getStats(CustomStatsParams params) {

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

	private PostSentimentStats getSentimentStatsByMonthOfYear(String title, CustomStatsParams params) {
		System.out.println("sentiments by month");
		PostSentimentStats stats = this.getAnalysedItemstatsTemplate(params, title);
		if (stats == null) {
			return null;
		}

		String[] months = new DateFormatSymbols(Locale.ENGLISH).getShortMonths();
		int monthNumber = 1;
		// the real statistics calcul is in this chappter
		while (monthNumber <= 12 && monthNumber > 0) {
			System.out.println("month " + monthNumber);
			Polarity postPolarity = this.getPolarityPerMonth(monthNumber, stats);
			stats.getLabelSeries().add(String.valueOf(monthNumber));
			stats.getStatistics().put(String.valueOf(monthNumber), postPolarity);
			stats.getPositiveDataCount().add(postPolarity.getPositiveCount());
			stats.getNegativeDataCount().add(postPolarity.getNegativeCount());
			stats.getNeutralDataCount().add(postPolarity.getNeutralCount());
			stats.getAverageDataCount().add(postPolarity.getAverage());

			monthNumber++;
		}
		return stats;
	}

	private PostSentimentStats getSentimentStatsByYear(String title, CustomStatsParams params) {
		System.out.println("sentiments by month");
		PostSentimentStats stats = this.getAnalysedItemstatsTemplate(params, title);
		if (stats == null) {
			return null;
		}

		int yearNumber = params.getEndYear() - params.getStartYear();
		// the real statistics calcul is in this chappter
		while (yearNumber >= 0) {

			int year = params.getEndYear() - yearNumber;
			stats.getLabelSeries().add(String.valueOf(year));
			Polarity postPolarity = this.getPolarityPerYear(year, stats);
			stats.getStatistics().put(String.valueOf(year), postPolarity);
			stats.getPositiveDataCount().add(postPolarity.getPositiveCount());
			stats.getNegativeDataCount().add(postPolarity.getNegativeCount());
			stats.getNeutralDataCount().add(postPolarity.getNeutralCount());
			stats.getAverageDataCount().add(postPolarity.getAverage());
			yearNumber--;
		}
		System.out.println("statisticsss  " + stats.getPositiveDataCount());
		return stats;

	}

	private PostSentimentStats getAnalysedItemstatsTemplate(CustomStatsParams params, String title) {
		PostSentimentStats stats = new PostSentimentStats();

		Date dateStart = getStartDateFromYear(params.getStartYear());
		Date dateEnd = params.getEndYear() != 0 ? getEndDateFromYear(params.getEndYear())
				: getEndDateFromYear(params.getStartYear());

		List<Post> Items = postRepository.findByTitleAndDateBetween(params.getQuery(), dateStart, dateEnd);
		if (Items.isEmpty()) {
			return null;
		}

		String sentimentMethode = "";
		if (params.getSentimentMethode() == 1) {
			sentimentMethode = "getNltkSentment";
		} else if (params.getSentimentMethode() == 2) {
			sentimentMethode = "getGateSentment";
		} else if(params.getSentimentMethode() == 3) {
			sentimentMethode = "getDumaxSentment";
		}else{
			sentimentMethode = "getGeneralSentiment";
		}
		for (Post p : Items) {
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
	private Polarity getPolarityPerMonth(int monthNumber, SentimentStats<Post> stats) {

		Polarity polarity = new Polarity();
		for (Post p : stats.getPositiveItems()) {

			if ((p.getDate().getMonth() + 1) == monthNumber) {
				System.out.println(" post positive id  " + p.getId());
				polarity.setPositiveCount(polarity.getPositiveCount() + 1);
			}
		}
		for (Post p : stats.getNegativeItems()) {
			if ((p.getDate().getMonth() + 1) == monthNumber) {
				System.out.println(" post negative id  " + p.getId());
				polarity.setNegativeCount(polarity.getNegativeCount() + 1);
			}
		}
		for (Post p : stats.getNeutralItems()) {
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
	private Polarity getPolarityPerYear(int year, SentimentStats<Post> stats) {
		Polarity polarity = new Polarity();
		for (Post p : stats.getPositiveItems()) {
			if (p.getDate().getYear() + 1900 == year) {
				polarity.setPositiveCount(polarity.getPositiveCount() + 1);
			}
		}
		for (Post p : stats.getNegativeItems()) {
			if (p.getDate().getYear() + 1900 == year) {
				polarity.setNegativeCount(polarity.getNegativeCount() + 1);
			}
		}
		for (Post p : stats.getNeutralItems()) {
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
