package com.weberfly.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.weberfly.entities.Post;
import com.weberfly.entities.Twitte;
import com.weberfly.entities.TwitterKeyWord;
import com.weberfly.util.CombainingDataSource;
import com.weberfly.util.SentencePolarity;
import com.weberfly.util.opensource.classifiers.NaiveBayes;
import com.weberfly.util.opensource.dataobjects.NaiveBayesKnowledgeBase;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

@Service
public class TweetAnalyseService {

	@Autowired
	private TwitterService TwitteService;
	@Autowired
	HttpSession session;

	private static final String TWITTER_CONSUMER_KEY = "ECm1eGhFxR2a0IRHuaX5hxytI";
	private static final String TWITTER_SECRET_KEY = "jQ1u1TcO6NN4OlGL5SBzIIssqVhe5TfeDkNXq2HBYwRDPgfwOb";
	private static final String TWITTER_ACCESS_TOKEN = "814239581084286976-RC3zktBH1pJNECZ1LGNlrTDgf398iY1";
	private static final String TWITTER_ACCESS_TOKEN_SECRET = "GZTzYDVwpxSEejufsj3gWoabA5XPy0q4EhV3wqptyrC2H";
	public static final String USER_AGENT = "Mozilla/5.0";

	public static String readLines3(BufferedReader bufferedReader) throws IOException {
		String lines = "";
		String line;
		while ((line = bufferedReader.readLine()) != null) {
			lines += line;
		}
		return lines;
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

	public List<SentencePolarity> getTestAnalyseByNLTK(List<String> sentences) throws Exception {
		List<String> filtredsentences = unicodeSentences(sentences);
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost postRequest = new HttpPost("http://localhost:5000/sentiments");
		JSONObject jsonIn = new JSONObject();

		jsonIn.put("sentences", filtredsentences);
		// System.out.println("size---"+sentences.size());
		// System.out.println("sentencesNLTK++++"+sentences);
		StringEntity input = new StringEntity(jsonIn.toString());
		input.setContentType("application/json");
		postRequest.setEntity(input);
		HttpResponse response = httpClient.execute(postRequest);
		if (response.getStatusLine().getStatusCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
		}
		BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent()), "UTF-8"));

		String output;
		System.out.println("Output from Server NLTK-S .... \n");
		output = readLines3(br);
		// System.out.println("ici---"+output);
		JSONObject jsonOut = new JSONObject(output);
		JSONArray dataJsonArray = jsonOut.getJSONArray("tasks");
		List<SentencePolarity> sentencesPolarity = new ArrayList<>();

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

		trainingFiles.put("positive", PostService.class.getResource("/datasets/DabbabiData/positive1.csv"));
		trainingFiles.put("negative", PostService.class.getResource("/datasets/DabbabiData/negative1.csv"));
		trainingFiles.put("neutral", PostService.class.getResource("/datasets/DabbabiData/neutral1.csv"));

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

	public String getAnalyseByNLTK(String content) throws Exception {
		content = content.replace(" ", "%20");
		content = content.replace(":", "%20");
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost postRequest = new HttpPost("http://localhost:8000/sentiment");
		JSONObject jsonIn = new JSONObject();

		jsonIn.put("sentence", this.unicodeSentence(content));
		// System.out.println("size---"+sentences.size());
		// System.out.println("sentencesNLTK++++"+sentences);
		StringEntity input = new StringEntity(jsonIn.toString());
		input.setContentType("application/json");
		postRequest.setEntity(input);
		HttpResponse response = httpClient.execute(postRequest);

		if (response.getStatusLine().getStatusCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
		}
		BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent()), "UTF-8"));

		String output;
		System.out.println("Output from Server NLTK-S .... \n");
		output = readLines3(br);
		// System.out.println("ici---"+output);

		httpClient.getConnectionManager().shutdown();
		return output;
	}

	public String getAnalyseByDumax(String content) throws IOException {
		// map of dataset files
		Map<String, URL> trainingFiles = new HashMap<>();
		trainingFiles.put("positive", PostService.class.getResource("/datasets/DabbabiData/positive1.csv"));
		trainingFiles.put("negative", PostService.class.getResource("/datasets/DabbabiData/negative1.csv"));
		trainingFiles.put("neutral", PostService.class.getResource("/datasets/DabbabiData/neutral1.csv"));
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

	public List<SentencePolarity> getTestAnalyseByGate(List<String> sentences) throws IOException, Exception {
		List<String> filtredsentences = unicodeSentences(sentences);
		JSONObject json = new JSONObject();
		List<SentencePolarity> sentencesPolarity = new ArrayList<SentencePolarity>();
		for (String sentence : filtredsentences) {

			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost postRequest = new HttpPost(
					"https://cloud-api.gate.ac.uk/process-document/generic-opinion-mining-english?annotations=Sentiment:SentenceSet");

			SentencePolarity sentencePolarity = new SentencePolarity();
			json.put("text", sentence);
			StringEntity input = new StringEntity(json.toString());
			input.setContentType("application/json");
	    	postRequest.setHeader("authorization", "Basic Z2NkcGhhdnc2eDAwOmd2Mm96OWdjbWc3dTZrNGh4Y2ky");
			//
			postRequest.setEntity(input);
			HttpResponse response = httpClient.execute(postRequest);
			if (response.getStatusLine().getStatusCode() != 200) {
				postRequest.setHeader("authorization", "Basic Z2MxNDU3Ymt6NTdwOjZpczB3enE4ODkxbXJkejQ3cGdp");
			}
			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

			String output;
			// System.out.println("Output from Server Gate .... \n");
			output = br.readLine();
			// System.out.println(output);
			JSONObject fulljson = new JSONObject(output);
			// System.out.println(fulljson);
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

	public List<SentencePolarity> getTestAnalyseByCombining(List<String> sentences, String polarity)
			throws IOException, Exception {
		List<SentencePolarity> sentencesPolarity = new ArrayList<SentencePolarity>();
		List<SentencePolarity> analyseByDumax = CombainingDataSource.dataSource.get("dumax" + polarity);
		List<SentencePolarity> analyseByGate = CombainingDataSource.dataSource.get("gate" + polarity);
		List<SentencePolarity> analyseByNltk = CombainingDataSource.dataSource.get("nltk" + polarity);

		for (int i = 0; i < sentences.size(); i++) {
			SentencePolarity sentencePolarity = new SentencePolarity();
			sentencePolarity.setSentence(sentences.get(i));
			sentencePolarity.setPolarity(getMaxPolarityByTools(analyseByGate.get(i).getPolarity(),
					analyseByDumax.get(i).getPolarity(), analyseByNltk.get(i).getPolarity()));
			sentencesPolarity.add(sentencePolarity);
		}
		return sentencesPolarity;
	}

	public String getMaxPolarityByTools(String gate, String dumax, String nltk) {
		
System.out.println("ntlk "+nltk + " dumax "+dumax+" gate "+gate);

		List<String> sentiments = new ArrayList<String>();
		sentiments.add(nltk);
		sentiments.add(dumax);
		sentiments.add(gate);
//		if(!gate.equals(nltk) && !gate.equals(dumax) && !dumax.equals(nltk)){
//			System.out.println(" gate dkhl "+gate);
//			return gate;
//		}
//		
		System.out.println(" khrj ");
		Map<String, Long> counts = sentiments.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));
		return Collections.max(counts.entrySet(), Map.Entry.comparingByValue()).getKey();
	}

	public List<String> getTwittersByKeyWord(String key) throws Exception {
		
		ConfigurationBuilder cb = new ConfigurationBuilder();
		
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
		return getTestAnalyseByNLTK(getTwittersByKeyWord(KeyWord));
	}

	// public List<SentencePolarity> getAnalysisTweetsBycombining(String
	// KeyWord) throws IOException, Exception {
	// return getTestAnalyseByCombining(getTwittersByKeyWord(KeyWord));
	// }

	private String unicodeSentence(String twitte) {
		String utf8tweet = "";
		try {
			byte[] utf8Bytes = twitte.getBytes("UTF-8");

			utf8tweet = new String(utf8Bytes, "UTF-8");

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Pattern unicodeOutliers = Pattern.compile("[^\\x00-\\x7F]",
				Pattern.UNICODE_CASE | Pattern.CANON_EQ | Pattern.CASE_INSENSITIVE);
		Matcher unicodeOutlierMatcher = unicodeOutliers.matcher(utf8tweet);
		utf8tweet = unicodeOutlierMatcher.replaceAll("");
		return utf8tweet;
	}

	public List<String> unicodeSentences(List<String> originalSentences) {
		List<String> unicodeSentences = new ArrayList<>();
		for (String tweets : originalSentences) {

			String utf8tweet = "";
			try {
				byte[] utf8Bytes = tweets.getBytes("UTF-8");

				utf8tweet = new String(utf8Bytes, "UTF-8");

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			Pattern unicodeOutliers = Pattern.compile("[^\\x00-\\x7F]",
					Pattern.UNICODE_CASE | Pattern.CANON_EQ | Pattern.CASE_INSENSITIVE);
			Matcher unicodeOutlierMatcher = unicodeOutliers.matcher(utf8tweet);
			utf8tweet = unicodeOutlierMatcher.replaceAll("");
			unicodeSentences.add(utf8tweet);
		}
		return unicodeSentences;
	}

	public Map<String, Double> getAnalyseRateForEachTools(String method) throws Exception {
		Date d1 = new Date();
		System.out.println(d1);
		// List<String> positive_sentences = readLines2(
		// PostService.class.getResource("/datasets/DabbabiData/positive_sentences.txt"));
		// List<String> negative_sentences = readLines2(
		// PostService.class.getResource("/datasets/DabbabiData/negative_sentences.txt"));
		// List<String> neutral_sentences = readLines2(
		// PostService.class.getResource("/datasets/DabbabiData/neutral_sentences.txt"));

		List<String> positive_sentences = readLines2((URL) session.getAttribute("positiveData"));
		List<String> negative_sentences = readLines2((URL) session.getAttribute("negativeData"));
		List<String> neutral_sentences = readLines2((URL) session.getAttribute("neutralData"));

		Map<String, Double> analyserates = new HashMap<>();
		List<SentencePolarity> testByPositives = null;
		List<SentencePolarity> testByNegatives = null;
		List<SentencePolarity> testByNeutrals = null;
		if (method == "nltk") {
			testByNegatives = getTestAnalyseByNLTK(negative_sentences);
			testByPositives = getTestAnalyseByNLTK(positive_sentences);
			testByNeutrals = getTestAnalyseByNLTK(neutral_sentences);
			CombainingDataSource.dataSource.put("nltkNeutral", testByNeutrals);
			CombainingDataSource.dataSource.put("nltkPositive", testByPositives);
			CombainingDataSource.dataSource.put("nltkNegative", testByNegatives);
		}
		if (method == "gate") {
			testByNegatives = getTestAnalyseByGate(negative_sentences);
			testByPositives = getTestAnalyseByGate(positive_sentences);
			testByNeutrals = getTestAnalyseByGate(neutral_sentences);
			CombainingDataSource.dataSource.put("gateNeutral", testByNeutrals);
			CombainingDataSource.dataSource.put("gatePositive", testByPositives);
			CombainingDataSource.dataSource.put("gateNegative", testByNegatives);
		}
		if (method == "dumax") {
			testByPositives = getTestAnalyseByDumax(positive_sentences);
			testByNegatives = getTestAnalyseByDumax(negative_sentences);
			testByNeutrals = getTestAnalyseByDumax(neutral_sentences);
			CombainingDataSource.dataSource.put("dumaxNeutral", testByNeutrals);
			CombainingDataSource.dataSource.put("dumaxPositive", testByPositives);
			CombainingDataSource.dataSource.put("dumaxNegative", testByNegatives);
		}
		if (method == "general") {

			testByPositives = getTestAnalyseByCombining(positive_sentences, "Positive");
			testByNegatives = getTestAnalyseByCombining(negative_sentences, "Negative");
			testByNeutrals = getTestAnalyseByCombining(neutral_sentences, "Neutral");

		}
		List<String> polarityPos = new ArrayList<String>();
		List<String> polarityNeg = new ArrayList<String>();
		List<String> polarityNeu = new ArrayList<String>();

		for (SentencePolarity sentencePolarity : testByPositives) {
			polarityPos.add(sentencePolarity.getPolarity());
			// System.out.println(polarity);
		}
		for (SentencePolarity sentencePolarity : testByNegatives) {
			polarityNeg.add(sentencePolarity.getPolarity());
			// System.out.println(polarity);
		}
		for (SentencePolarity sentencePolarity : testByNeutrals) {
			polarityNeu.add(sentencePolarity.getPolarity());
			// System.out.println(polarity);
		}
		analyserates.put("Positive",
				(double) Collections.frequency(polarityPos, "positive") / (polarityPos.size()) * 100);
		analyserates.put("Negative",
				(double) Collections.frequency(polarityNeg, "negative") / (polarityNeg.size()) * 100);
		analyserates.put("Neutral",
				(double) Collections.frequency(polarityNeu, "neutral") / (polarityNeu.size()) * 100);

		Date d2 = new Date();

		System.out.println(d2);
		return analyserates;

	}

	public Map<String, Double> getAnalyseRateForDumax() throws Exception {
		return getAnalyseRateForEachTools("dumax");
	}

	public Map<String, Double> getAnalyseRateForNLTK() throws Exception {
		return getAnalyseRateForEachTools("nltk");
	}

	public Map<String, Double> getAnalyseRateForGate() throws Exception {
		return getAnalyseRateForEachTools("gate");
	}

	public Map<String, Double> getAnalyseRateGeneral() throws Exception {
		return getAnalyseRateForEachTools("general");
	}

	public void collectAndanalyseTwitte() {

	}

	public String getAnalyseByGateApi(String content) throws ClientProtocolException, IOException, Exception {
		JSONObject json = new JSONObject();
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost postRequest = new HttpPost(
				"https://cloud-api.gate.ac.uk/process-document/generic-opinion-mining-english?annotations=Sentiment:SentenceSet");
		json.put("text", content);

		StringEntity input = new StringEntity(json.toString());
		input.setContentType("application/json");
	postRequest.setHeader("authorization", "Basic Z2Ntb3Q4NTlsd3AxOmo5dXFwZmJlbXpqcmU2cGt6emUy");
	//	postRequest.setHeader("authorization", "Basic Z2M1YzQ5azNvOXJhOjZ6ODQycTloOWZjeGZocmV0bm9u");
		
		postRequest.setEntity(input);
		HttpResponse response = httpClient.execute(postRequest);
		if (response.getStatusLine().getStatusCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
		}
		BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
		String output;
		output = br.readLine();
		JSONObject fulljson = new JSONObject(output);
		JSONObject obj1 = fulljson.getJSONObject("entities");
		JSONArray dataJsonArray = obj1.getJSONArray("SentenceSet");
		JSONObject objsent = dataJsonArray.getJSONObject(0);
		String sentiment = objsent.getString("polarity");
		httpClient.getConnectionManager().shutdown();
		return sentiment;
	}

	// @Async
	public void collectTwittesAndAnalyseThem(TwitterKeyWord keyWord)
			throws ClientProtocolException, IOException, Exception {
		ConfigurationBuilder cb = new ConfigurationBuilder();

		cb.setDebugEnabled(true).setOAuthConsumerKey(TWITTER_CONSUMER_KEY).setOAuthConsumerSecret(TWITTER_SECRET_KEY)
				.setOAuthAccessToken(TWITTER_ACCESS_TOKEN).setOAuthAccessTokenSecret(TWITTER_ACCESS_TOKEN_SECRET);
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();

		Query query = new Query(keyWord.getWord()).lang("en");
		query.setCount(10);
		QueryResult result = null;
		do {
			try {
				result = twitter.search(query);
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (result == null) {
				return;
			}
			String dumaxSenti, ntlkSenti, gateSenti, combainingSenti;
			List<twitter4j.Status> tweets = result.getTweets();
			Twitte twitte = null;
			for (twitter4j.Status tweet : tweets) {
				twitte = new Twitte();
				twitte.setKeyWord(keyWord);
				twitte.setText(tweet.getText());
				twitte.setDate(tweet.getCreatedAt());
				gateSenti = this.getAnalyseTwitteByGateApi(twitte.getText());
				twitte.setGateSentment(Post.sentiment.valueOf(gateSenti));
				ntlkSenti = this.getAnalyseByNLTK(twitte.getText());
				twitte.setNltkSentment(Post.sentiment.valueOf(ntlkSenti));
				dumaxSenti = this.getAnalyseByDumax(twitte.getText());
				twitte.setDumaxSentment(Post.sentiment.valueOf(dumaxSenti));
				combainingSenti = this.getMaxPolarityByTools(gateSenti, dumaxSenti, "positive");
				twitte.setGeneralSentiment(Post.sentiment.valueOf(combainingSenti));

				TwitteService.save(twitte);
			}
		} while ((query = result.nextQuery()) != null);
		// System.exit(0);
	}

	private String getAnalyseTwitteByGateApi(String content) throws JSONException, ClientProtocolException, IOException {
		JSONObject json = new JSONObject();
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost postRequest = new HttpPost(
				"https://cloud-api.gate.ac.uk/process-document/generic-opinion-mining-english?annotations=Sentiment:SentenceSet");
		json.put("text", content);

		StringEntity input = new StringEntity(json.toString());
		input.setContentType("application/json");
	postRequest.setHeader("authorization", "Basic Z2MxNDU3Ymt6NTdwOjZpczB3enE4ODkxbXJkejQ3cGdp");
		
		
		postRequest.setEntity(input);
		HttpResponse response = httpClient.execute(postRequest);
		if (response.getStatusLine().getStatusCode() != 200) {
			postRequest.setHeader("authorization", "Basic Z2M1YzQ5azNvOXJhOjZ6ODQycTloOWZjeGZocmV0bm9u");
		}
		BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
		String output;
		output = br.readLine();
		JSONObject fulljson = new JSONObject(output);
		JSONObject obj1 = fulljson.getJSONObject("entities");
		JSONArray dataJsonArray = obj1.getJSONArray("SentenceSet");
		JSONObject objsent = dataJsonArray.getJSONObject(0);
		String sentiment = objsent.getString("polarity");
		httpClient.getConnectionManager().shutdown();
		return sentiment;
	}

}
