package com.weberfly.service;

import static org.assertj.core.api.Assertions.shouldHaveThrown;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weberfly.dao.CategoryItemRepository;
import com.weberfly.dao.PostRepository;
import com.weberfly.entities.Category;
import com.weberfly.entities.CategoryItem;
import com.weberfly.entities.Post;
import com.weberfly.util.CustomStatsParams;
import com.weberfly.util.Polarity;
import com.weberfly.util.SentimentStats;

import scala.collection.parallel.ParIterableLike.Foreach;

@Service
public class PostService {
	@Autowired
	private PostRepository postRepository;
	@Autowired
	private CategoryItemRepository categoryItemRepository;
	
	public void savePost(Post post) {
		String pos =post.getContent();
		List<CategoryItem> categoryItems =categoryItemRepository.findAll();
		List<CategoryItem> pubcategoryItems =new ArrayList<>();
		for (CategoryItem item : categoryItems) {		
			if (pos.toLowerCase().contains(item.getName().toLowerCase())){		
				pubcategoryItems.add(item);	
		}
		}
		post.setCategoryItems(pubcategoryItems);
		
		postRepository.save(post);
	}
	public List<Post> getAll() {
		return postRepository.findAll();
	}  
	
	
	        // get analysed post between two dates 
	public List<Post> getAnalysedPosts(String querySearche,String startDate, String endDate){
		DateFormat formatter = new SimpleDateFormat("yy-dd-MM");
		CategoryItem item =categoryItemRepository.findByNameIgnoreCase(querySearche);
		List<CategoryItem> items=null;
		if(item!=null){
			items=new ArrayList<>();
			items.add(item);
			 Date dateStart,dateEnd;
			try {
				 dateStart = formatter.parse(startDate);
				 dateEnd = formatter.parse(endDate);
				 return postRepository.findByCategoryItemsAndDateBetween(items,dateStart,dateEnd);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public SentimentStats getStats(CustomStatsParams params){ 
		SentimentStats stats=null;
		CategoryItem item =categoryItemRepository.findByNameIgnoreCase(params.getQuery());
		if(item==null){
			// the word that we are looking for doesn't exist
			return null;
		}
		
		if(params.getStartYear()!=0){
			if(params.getEndYear()!=0){
				// search between many years 
				return this.getSentimentStatsByYear(item, params);
			}else if(params.getEndYear()==0){
				if(params.getMonth()==0){
					// search by  months of year
               return this.getSentimentStatsByMonthOfYear(item,params);
				}else{
              //	search by days of month
				}
			} 
		}
		
			return null;// nothing to search the start year is undefined
		
	}

	private SentimentStats getSentimentStatsByMonthOfYear(CategoryItem item, CustomStatsParams params) {
		System.out.println("sentiments by month");
		SentimentStats stats=this.getAnalysedPostStatsTemplate(params, item);
		if(stats==null){
			return null;
		}
		
	    String[] months = new DateFormatSymbols(Locale.ENGLISH).getShortMonths();
	    int monthNumber=12;
		// the real statistics calcul is in this chappter
		while(monthNumber <= 12 && monthNumber > 0){
			System.out.println("month "+monthNumber);
			Polarity postPolarity=this.getPolarityPerMonth(monthNumber, stats);
			stats.getStatistics().put(String.valueOf(monthNumber),postPolarity);
			monthNumber--;
		}
		return stats;
	}
	
	private SentimentStats getSentimentStatsByYear(CategoryItem item,CustomStatsParams params){
		System.out.println("sentiments by month");
		SentimentStats stats=this.getAnalysedPostStatsTemplate(params, item);
		if(stats==null){
			return null;
		}
		int yearNumber=params.getEndYear()-params.getStartYear();
		// the real statistics calcul is in this chappter
		while(yearNumber >=0){
			int year=params.getEndYear()-yearNumber;
			Polarity postPolarity=this.getPolarityPerYear(year, stats);
			stats.getStatistics().put(String.valueOf(year),postPolarity);
			yearNumber--;
		}
		return stats;
		
	}
	private SentimentStats getAnalysedPostStatsTemplate(CustomStatsParams params,CategoryItem item){
		SentimentStats stats=new SentimentStats();
		List<CategoryItem> items=new ArrayList<>();
		items.add(item);
		Date dateStart=getStartDateFromYear(params.getStartYear());
	    Date dateEnd=params.getEndYear()!=0 ?getEndDateFromYear(params.getEndYear()) :getEndDateFromYear(params.getStartYear()) ;
	   
	    List<Post> posts= postRepository.findByCategoryItemsAndDateBetween(items,dateStart,dateEnd);
	    if(posts.isEmpty()){
	    	return null;
	    	}
	    
		String sentimentMethode=""; 
		if(params.getSentimentMethode()==0){
			sentimentMethode="getDumaxSentment";
		}else if(params.getSentimentMethode()==1){
			sentimentMethode="getNltkSentment";
		}else{
			sentimentMethode="getOtherSentment";
		}
		for(Post p:posts){
			Post.sentiment senti = null;
			java.lang.reflect.Method getSentimentMethode;
			try {
				getSentimentMethode = p.getClass().getMethod(sentimentMethode);
				senti=(Post.sentiment) getSentimentMethode.invoke(p);	
			}catch(Exception e){
				e.printStackTrace();
			}
			if(senti==Post.sentiment.positive){
				stats.getPositivePosts().add(p);
			}else if(senti==Post.sentiment.neutratl){
				stats.getNeutralPost().add(p);
			}else if(senti==Post.sentiment.negative){
		    	stats.getNegativePosts().add(p);
		     }

	     }
		return stats;
	}
	
	        /*********************** helper functions     *************************/
	
	// this method return the plolarity for the onths of a year  included in the statistics calcul 
	private Polarity getPolarityPerMonth(int monthNumber, SentimentStats stats) {

		Polarity polarity=new Polarity();
		for(Post p:stats.getPositivePosts()){
			
			if((p.getDate().getMonth()+1)==monthNumber){
				System.out.println(" post positive id  "+p.getId());
				polarity.setPositiveCount(polarity.getPositiveCount()+1);
			}
		}
		for(Post p:stats.getNegativePosts()){
			if((p.getDate().getMonth()+1)==monthNumber){
				System.out.println(" post negative id  "+p.getId());
				polarity.setNegativeCount(polarity.getNegativeCount()+1);
			}
		}
		for(Post p:stats.getNeutralPost()){
			if((p.getDate().getMonth()+1)==monthNumber){
				polarity.setNeutralCount(polarity.getNeutralCount()+1);
				System.out.println(" post neutral id  "+p.getId());
			}
		}
		return polarity;
	}
	
	
	// this method return the plolarity for every year included in the statistics calcul 
	private Polarity getPolarityPerYear(int year,SentimentStats stats){
		Polarity polarity=new Polarity();
		for(Post p:stats.getPositivePosts()){
			if(p.getDate().getYear()+1900==year){
				polarity.setPositiveCount(polarity.getPositiveCount()+1);
			}
		}
		for(Post p:stats.getNegativePosts()){
			if(p.getDate().getYear()+1900==year){
				polarity.setNegativeCount(polarity.getNegativeCount()+1);
			}
		}
		for(Post p:stats.getNeutralPost()){
			if(p.getDate().getYear()+1900==year){
				polarity.setNeutralCount(polarity.getNeutralCount()+1);
			}
		}
		return polarity;
		
	}
	private Date getStartDateFromYear(int year) {
		    Calendar cal = Calendar.getInstance();
		    cal.set(Calendar.YEAR, year);
		    cal.set(Calendar.DAY_OF_YEAR, 1);
		    cal.set(Calendar.HOUR_OF_DAY,1);
		    Date dateStart = cal.getTime();
		    return dateStart;
	}
	private Date getEndDateFromYear(int year){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
	    cal.set(Calendar.MONTH, 11); // 12 = december
	    cal.set(Calendar.DAY_OF_MONTH, 31);
	    cal.set(Calendar.HOUR_OF_DAY,12);
	    Date dateEnd = cal.getTime();
        return dateEnd;
	}
}
