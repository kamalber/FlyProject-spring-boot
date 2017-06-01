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
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.weberfly.dao.CategoryItemRepository;
import com.weberfly.dao.PostRepository;
import com.weberfly.dao.PublicationRepository;

import com.weberfly.entities.CategoryItem;
import com.weberfly.entities.Post;

import com.weberfly.entities.Publication;

import com.weberfly.util.CustomStatsParams;
import com.weberfly.util.Polarity;
import com.weberfly.util.SentencePolarity;
import com.weberfly.util.SentimentStats;
import com.weberfly.util.opensource.classifiers.NaiveBayes;
import com.weberfly.util.opensource.dataobjects.NaiveBayesKnowledgeBase;


import javassist.expr.Cast;

import net.minidev.json.parser.JSONParser;
import scala.collection.parallel.ParIterableLike.Foreach;



@Service
public class PostService {
	private static final String TWITTER_CONSUMER_KEY = "ECm1eGhFxR2a0IRHuaX5hxytI";
	private static final String TWITTER_SECRET_KEY = "jQ1u1TcO6NN4OlGL5SBzIIssqVhe5TfeDkNXq2HBYwRDPgfwOb";
	private static final String TWITTER_ACCESS_TOKEN = "814239581084286976-RC3zktBH1pJNECZ1LGNlrTDgf398iY1";
	private static final String TWITTER_ACCESS_TOKEN_SECRET = "GZTzYDVwpxSEejufsj3gWoabA5XPy0q4EhV3wqptyrC2H";

	@Autowired
	private PostRepository postRepository;
	@Autowired
	private PublicationRepository publicationRepository;
	@Autowired
	private CategoryItemRepository categoryItemRepository;
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

	public static List<String> readLines2(URL url) throws IOException {

		Reader fileReader = new InputStreamReader(url.openStream(), Charset.forName("UTF-8"));
		List<String> lines;
		try (BufferedReader bufferedReader = new BufferedReader(fileReader)) {
			lines = new ArrayList<>();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				lines.add(line);
			}
		}

		return lines;
	}
	public static String readLines3(BufferedReader bufferedReader) throws IOException {
		    String lines="";		
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				lines+=line;
			}		
		return lines;
	}

	public static String getAnalyseByDumax(String content) throws IOException {
		// map of dataset files
		Map<String, URL> trainingFiles = new HashMap<>();
		trainingFiles.put("Positive", PostService.class.getResource("/datasets/DabbabiData/positive1.csv"));
		trainingFiles.put("Negative", PostService.class.getResource("/datasets/DabbabiData/negative1.csv"));
		trainingFiles.put("Neutral", PostService.class.getResource("/datasets/DabbabiData/neutral1.csv"));
		Map<String, String[]> trainingExamples = new HashMap<>();
		for (Map.Entry<String, URL> entry : trainingFiles.entrySet()) {
			trainingExamples.put(entry.getKey(), readLines(entry.getValue()));
		}

		// train classifier
		NaiveBayes nb = new NaiveBayes();
		nb.setChisquareCriticalValue(6.63); // 0.01 pvalue
		nb.train(trainingExamples);

		// get trained classifier knowledgeBase
		NaiveBayesKnowledgeBase knowledgeBase = nb.getKnowledgeBase();
		nb = null;
		trainingExamples = null;

		// Use classifier
		nb = new NaiveBayes(knowledgeBase);
		// String exampleEn = "i don't like this book";
		String outputEn = nb.predict(content);
		return outputEn;
		// System.out.format("The sentense \"%s\" was classified as \"%s\".%n",
		// exampleEn, outputEn);
	}

	private String getAnalyseByNLTK(String content) throws Exception {

		String url = "http://localhost:8000/" + content;
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		// add request header
		con.setRequestProperty("User-Agent", USER_AGENT);
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// print result
		System.out.println(response.toString());
		return response.toString();
	}

	public String getAnalyseByGateApi(String content) throws ClientProtocolException, IOException, Exception {

		JSONObject json = new JSONObject();
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost postRequest = new HttpPost(
				"https://cloud-api.gate.ac.uk/process-document/generic-opinion-mining-english?annotations=Sentiment:SentenceSet");
		json.put("text", content);
		StringEntity input = new StringEntity(json.toString());
		input.setContentType("application/json");
		postRequest.setEntity(input);
		HttpResponse response = httpClient.execute(postRequest);
		if (response.getStatusLine().getStatusCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
		}
		BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

		String output;
		System.out.println("Output from Server .... \n");
		output = br.readLine();
		System.out.println(output);
		JSONObject fulljson = new JSONObject(output);
		System.out.println(fulljson);
		JSONObject obj1 = fulljson.getJSONObject("entities");
		JSONArray dataJsonArray = obj1.getJSONArray("SentenceSet");
		JSONObject objsent = dataJsonArray.getJSONObject(0);
		String sentiment = objsent.getString("polarity");
		System.out.println("******" + objsent);

		httpClient.getConnectionManager().shutdown();

		return sentiment;
	}
	public static List<SentencePolarity> getTestAnalyseByNLTK(List<String> sentences) throws Exception{
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost postRequest = new HttpPost(
				"http://localhost:5000/sentiments");
		JSONObject jsonIn = new JSONObject();
	
		jsonIn.put("sentences", sentences);
		System.out.println("size---"+sentences.size());
//		System.out.println("sentences++++"+sentences);
		StringEntity input = new StringEntity(jsonIn.toString());
		input.setContentType("application/json");
		postRequest.setEntity(input);
		HttpResponse response = httpClient.execute(postRequest);
		if (response.getStatusLine().getStatusCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
		}
		BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent()),"UTF-8"));

		String output;
		System.out.println("Output from Server .... \n");
		output = readLines3(br);
//		System.out.println("ici---"+output);
		JSONObject jsonOut = new JSONObject(output);
		JSONArray dataJsonArray = jsonOut.getJSONArray("tasks");
		List<SentencePolarity>sentencesPolarity = new ArrayList<>();
		
		for (int i = 0; i < dataJsonArray.length(); i++) {
		
		JSONObject objsent = dataJsonArray.getJSONObject(i);
		SentencePolarity sentencePolarity = new SentencePolarity();
		sentencePolarity.setPolarity(objsent.getString("polarity"));
		sentencePolarity.setSentence(objsent.getString("sentence"));
		sentencesPolarity.add(sentencePolarity);
		} 
		httpClient.getConnectionManager().shutdown();
		
return sentencesPolarity;
	}

	public static List<SentencePolarity> getTestAnalyseByDumax(List<String> sentences) throws IOException {
		// map of dataset files
		Map<String, URL> trainingFiles = new HashMap<>();

		trainingFiles.put("Positive", PostService.class.getResource("/datasets/DabbabiData/positive1.csv"));
		trainingFiles.put("Negative", PostService.class.getResource("/datasets/DabbabiData/negative1.csv"));
		trainingFiles.put("Neutral", PostService.class.getResource("/datasets/DabbabiData/neutral1.csv"));

		Map<String, String[]> trainingExamples = new HashMap<>();
		for (Map.Entry<String, URL> entry : trainingFiles.entrySet()) {
			trainingExamples.put(entry.getKey(), readLines(entry.getValue()));
		}

		// train classifier
		NaiveBayes nb = new NaiveBayes();
		nb.setChisquareCriticalValue(6.63); // 0.01 pvalue
		nb.train(trainingExamples);

		// get trained classifier knowledgeBase
		NaiveBayesKnowledgeBase knowledgeBase = nb.getKnowledgeBase();

		nb = null;
		trainingExamples = null;

		// Use classifier
		nb = new NaiveBayes(knowledgeBase);
		// String exampleEn = "i don't like this book";
		String outputEn;
		List<SentencePolarity> sentencesPolarity = new ArrayList<SentencePolarity>();
		for (String sentence : sentences) {
			SentencePolarity sentencePolarity = new SentencePolarity();
			outputEn = nb.predict(sentence);
			sentencePolarity.setPolarity(outputEn);
			sentencePolarity.setSentence(sentence);
			// System.out.println("-----------"+sentencePolarity);

			sentencesPolarity.add(sentencePolarity);
		}

		return sentencesPolarity;
		// System.out.format("The sentense \"%s\" was classified as \"%s\".%n",
		// exampleEn, outputEn);

	}

	public  List<SentencePolarity> getTestAnalyseByGate(List<String> sentences) throws IOException, Exception {
		JSONObject json = new JSONObject();		
		List<SentencePolarity>sentencesPolarity = new ArrayList<SentencePolarity>();	
		for (String sentence : sentences) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost postRequest = new HttpPost(
				"https://cloud-api.gate.ac.uk/process-document/generic-opinion-mining-english?annotations=Sentiment:SentenceSet");
		
		SentencePolarity sentencePolarity = new SentencePolarity();
		json.put("text", sentence);
		StringEntity input = new StringEntity(json.toString());
		input.setContentType("application/json");
		postRequest.setEntity(input);
		HttpResponse response = httpClient.execute(postRequest);
		if (response.getStatusLine().getStatusCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().toString());
		}
		BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

		String output;
		System.out.println("Output from Server .... \n");
		output = br.readLine();
		System.out.println(output);
		JSONObject fulljson = new JSONObject(output);
		System.out.println(fulljson);
		JSONObject obj1 = fulljson.getJSONObject("entities");
		JSONArray dataJsonArray = obj1.getJSONArray("SentenceSet");
		JSONObject objsent = dataJsonArray.getJSONObject(0);
		String sentiment = objsent.getString("polarity");
		sentencePolarity.setPolarity(sentiment);
		sentencePolarity.setSentence(sentence);
		sentencesPolarity.add(sentencePolarity);
		httpClient.getConnectionManager().shutdown();
		}
		

		return sentencesPolarity;
	}

	public List<String> getTwittersByKeyWord(String key) throws Exception {
		System.out.format("begin");
		ConfigurationBuilder cb = new ConfigurationBuilder();
		System.out.format("end");
		List<String> tweetsCollected = new ArrayList<String>();
		cb.setDebugEnabled(true).setOAuthConsumerKey(TWITTER_CONSUMER_KEY).setOAuthConsumerSecret(TWITTER_SECRET_KEY)
				.setOAuthAccessToken(TWITTER_ACCESS_TOKEN).setOAuthAccessTokenSecret(TWITTER_ACCESS_TOKEN_SECRET);
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();

		Query query = new Query(key).lang("en");
		query.setCount(10);
		QueryResult result;
		do {
			result = twitter.search(query);

			List<twitter4j.Status> tweets = result.getTweets();
			for (twitter4j.Status tweet : tweets) {
				tweetsCollected.add(tweet.getText());
				// System.out.println(tweet.getText()+"00000000");
				//
			}
		} while ((query = result.nextQuery()) != null);
		// System.exit(0);

		return tweetsCollected;
	}

	public List<SentencePolarity> getAnalysisTweetsByDumax(String KeyWord) throws IOException, Exception {
		return getTestAnalyseByDumax(getTwittersByKeyWord(KeyWord));
	}
	public List<SentencePolarity> getAnalysisTweetsByGate(String KeyWord) throws IOException, Exception {
		return getTestAnalyseByGate(getTwittersByKeyWord(KeyWord));
	}
	public List<SentencePolarity> getAnalysisTweetsByNLTK(String KeyWord) throws IOException, Exception {
		List<String>originalTweets = getTwittersByKeyWord(KeyWord);
		List<String>filtredTweets = new ArrayList<>();
		for (String tweets : originalTweets) {
		
		 String utf8tweet = "";
	        try {
	            byte[] utf8Bytes = tweets.getBytes("UTF-8");

	            utf8tweet = new String(utf8Bytes, "UTF-8");

	        } catch (UnsupportedEncodingException e) {
	            e.printStackTrace();
	        }
	        Pattern unicodeOutliers = Pattern.compile("[^\\x00-\\x7F]",
	                Pattern.UNICODE_CASE | Pattern.CANON_EQ
	                        | Pattern.CASE_INSENSITIVE);
	        Matcher unicodeOutlierMatcher = unicodeOutliers.matcher(utf8tweet);
	        utf8tweet = unicodeOutlierMatcher.replaceAll("");
	        filtredTweets.add(utf8tweet);
	        }
		return getTestAnalyseByNLTK(filtredTweets);
	}

	public Map<String, Double> getAnalyseRateForEachTools(String method) throws Exception {
		List<String> positive_sentences = readLines2(
				PostService.class.getResource("/datasets/DabbabiData/positive_sentences.txt"));
		List<String> negative_sentences = readLines2(
				PostService.class.getResource("/datasets/DabbabiData/negative_sentences.txt"));
		Map<String, Double> analyserates = new HashMap<>();
		List<SentencePolarity> testByPositives=null ;
		List<SentencePolarity> testByNegatives=null;
		if(method=="nltk"){
			testByNegatives = getTestAnalyseByNLTK(negative_sentences);	
			testByPositives = getTestAnalyseByNLTK(positive_sentences);		
	    
		 }
		if(method=="gate"){
			testByNegatives = getTestAnalyseByGate(negative_sentences);	
			testByPositives = getTestAnalyseByGate(positive_sentences);		
	    
		 }
		if(method=="dumax"){    
		testByPositives = getTestAnalyseByDumax(positive_sentences);
		testByNegatives = getTestAnalyseByDumax(negative_sentences);}

		List<String> polarityPos = new ArrayList<String>();
		List<String> polarityNeg = new ArrayList<String>();

		for (SentencePolarity sentencePolarity : testByPositives) {
			polarityPos.add(sentencePolarity.getPolarity());
			// System.out.println(polarity);
		}
		for (SentencePolarity sentencePolarity : testByNegatives) {
			polarityNeg.add(sentencePolarity.getPolarity());
			// System.out.println(polarity);
		}
		analyserates.put("Positive",
				(double) Collections.frequency(polarityPos, "Positive")/polarityPos.size()*100);
		analyserates.put("Negative",
				(double) Collections.frequency(polarityNeg, "Negative")/polarityNeg.size()*100);

		return analyserates;

	}
	public Map<String, Double> getAnalyseRateForDumax() throws Exception{
		return getAnalyseRateForEachTools("dumax");
	}
    public Map<String, Double> getAnalyseRateForNLTK() throws Exception{
    	return getAnalyseRateForEachTools("nltk");
    }
    public Map<String, Double> getAnalyseRateForGate() throws Exception{
    	return getAnalyseRateForEachTools("gate");
    }
	public void savePost(Post post) throws Exception {
		String content = post.getContent();
		// testGateApi();
//		List<String>tests = new ArrayList<>();
//		tests.add("I love Brokeback Mountain.");
//		tests.add("I hate Harry Potter..");
//
//		tests.add("I liked the Da Vinci Code but it ultimatly didn't seem to hold it's own.");
//		tests.add("that's not even an exaggeration ) and at midnight we went to Wal-Mart to buy the Da Vinci Code which is amazing of course.");

//		System.out.println(getTestAnalyseByNLTK(tests));
//		System.out.println("Dumax"+getTestAnalyseByDumax(tests));
//		System.out.println("this is the rate analysis dumax " + getAnalyseRateForDumax());
//		System.out.println("this is the rate analysis nltk " + getAnalyseRateForNLTK());
		
//		System.out.println(getTwittersByKeyWord("messi"));
//		System.out.println(getAnalysisTweetsByNLTK("zafzafi"));
		System.out.println(getAnalysisTweetsByGate("messi"));

		// GateSentiment
		String gatesentiment = getAnalyseByGateApi(content);
		// System.out.println(gatesentiment);
		if (gatesentiment.equalsIgnoreCase("positive"))
			post.setGateSentment(Post.sentiment.positive);
		if (gatesentiment.equalsIgnoreCase("negative"))
			post.setGateSentment(Post.sentiment.negative);
		if (gatesentiment.equalsIgnoreCase("neutral"))
			post.setGateSentment(Post.sentiment.neutratl);

		// NLTK Sentiment
		content = content.replace(" ", "%20");
		content = content.replace(":", "%20");
		String nltkSentiment = getAnalyseByNLTK(content);
		if (nltkSentiment.equalsIgnoreCase("positive"))
			post.setNltkSentment(Post.sentiment.positive);
		if (nltkSentiment.equalsIgnoreCase("negative"))
			post.setNltkSentment(Post.sentiment.negative);
		if (nltkSentiment.equalsIgnoreCase("neutral"))
			post.setNltkSentment(Post.sentiment.neutratl);

		// DumaxSentiement
		String dumaxsentiment = getAnalyseByDumax(post.getContent());
		// System.out.println("----------------------"+dumaxsentiment);
		if (dumaxsentiment.equalsIgnoreCase("Positive"))
			post.setDumaxSentment(Post.sentiment.positive);
		if (dumaxsentiment.equalsIgnoreCase("Negative"))
			post.setDumaxSentment(Post.sentiment.negative);
		if (dumaxsentiment.equalsIgnoreCase("Neutral"))
			post.setDumaxSentment(Post.sentiment.neutratl);

		// General sentiment
		List<String> sentiments = new ArrayList<String>();
		sentiments.add(gatesentiment.toLowerCase());
		sentiments.add(dumaxsentiment.toLowerCase());
		sentiments.add(nltkSentiment.toLowerCase());
		Map<String, Long> counts = sentiments.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));
		String key = Collections.max(counts.entrySet(), Map.Entry.comparingByValue()).getKey();

		if (key.equalsIgnoreCase("Positive"))
			post.setGeneralSentiment(Post.sentiment.positive);
		if (key.equalsIgnoreCase("Negative"))
			post.setGeneralSentiment(Post.sentiment.negative);
		if (key.equalsIgnoreCase("Neutral"))
			post.setGeneralSentiment(Post.sentiment.neutratl);

		List<CategoryItem> categoryItems = categoryItemRepository.findAll();
		List<CategoryItem> pubcategoryItems = new ArrayList<>();
		for (CategoryItem item : categoryItems) {
			if (content.toLowerCase().contains(item.getName().toLowerCase())) {
				pubcategoryItems.add(item);
			}
		}
		post.setCategoryItems(pubcategoryItems);

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
	public List<Post> getAnalysedPosts(String querySearche,String startDate, String endDate){

		DateFormat formatter = new SimpleDateFormat("yy-dd-MM");
		CategoryItem item = categoryItemRepository.findByNameIgnoreCase(querySearche);
		List<CategoryItem> items = null;
		if (item != null) {
			items = new ArrayList<>();
			items.add(item);
			Date dateStart, dateEnd;
			try {
				dateStart = formatter.parse(startDate);
				dateEnd = formatter.parse(endDate);
				return postRepository.findByTitleAndDateBetween(querySearche, dateStart, dateEnd);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public SentimentStats getStats(CustomStatsParams params) {
		

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

	private SentimentStats getSentimentStatsByMonthOfYear(String title, CustomStatsParams params) {
		System.out.println("sentiments by month");
		SentimentStats stats = this.getAnalysedPostStatsTemplate(params, title);
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

	private SentimentStats getSentimentStatsByYear(String title, CustomStatsParams params) {
		System.out.println("sentiments by month");
		SentimentStats stats = this.getAnalysedPostStatsTemplate(params, title);
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

	private SentimentStats getAnalysedPostStatsTemplate(CustomStatsParams params, String title) {
		SentimentStats stats = new SentimentStats();
	
		Date dateStart = getStartDateFromYear(params.getStartYear());
		Date dateEnd = params.getEndYear() != 0 ? getEndDateFromYear(params.getEndYear())
				: getEndDateFromYear(params.getStartYear());

		List<Post> posts = postRepository.findByTitleAndDateBetween(params.getQuery(), dateStart, dateEnd);
		if (posts.isEmpty()) {
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
		for (Post p : posts) {
			Post.sentiment senti = null;
			java.lang.reflect.Method getSentimentMethode;
			try {
				getSentimentMethode = p.getClass().getMethod(sentimentMethode);
				senti = (Post.sentiment) getSentimentMethode.invoke(p);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (senti == Post.sentiment.positive) {
				stats.getPositivePosts().add(p);
			} else if (senti == Post.sentiment.neutratl) {
				stats.getNeutralPost().add(p);
			} else if (senti == Post.sentiment.negative) {
				stats.getNegativePosts().add(p);
			}

		}
		return stats;
	}

	/*********************** helper functions *************************/

	// this method return the plolarity for the onths of a year included in the
	// statistics calcul
	private Polarity getPolarityPerMonth(int monthNumber, SentimentStats stats) {

		Polarity polarity = new Polarity();
		for (Post p : stats.getPositivePosts()) {

			if ((p.getDate().getMonth() + 1) == monthNumber) {
				System.out.println(" post positive id  " + p.getId());
				polarity.setPositiveCount(polarity.getPositiveCount() + 1);
			}
		}
		for (Post p : stats.getNegativePosts()) {
			if ((p.getDate().getMonth() + 1) == monthNumber) {
				System.out.println(" post negative id  " + p.getId());
				polarity.setNegativeCount(polarity.getNegativeCount() + 1);
			}
		}
		for (Post p : stats.getNeutralPost()) {
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
	private Polarity getPolarityPerYear(int year, SentimentStats stats) {
		Polarity polarity = new Polarity();
		for (Post p : stats.getPositivePosts()) {
			if (p.getDate().getYear() + 1900 == year) {
				polarity.setPositiveCount(polarity.getPositiveCount() + 1);
			}
		}
		for (Post p : stats.getNegativePosts()) {
			if (p.getDate().getYear() + 1900 == year) {
				polarity.setNegativeCount(polarity.getNegativeCount() + 1);
			}
		}
		for (Post p : stats.getNeutralPost()) {
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


