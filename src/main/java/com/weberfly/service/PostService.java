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
import com.weberfly.util.CustomSatatsParams;
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	public SentimentStats getStats(CustomSatatsParams params){
		 
		SentimentStats stats=null;
		
		CategoryItem item =categoryItemRepository.findByNameIgnoreCase(params.getQuerySearche());
		if(item==null){
			// the word that we are looking for doesn't exist
			return null;
		}
		
		if(params.getStartYear()!=0){
			if(params.getEndYear()!=0){
				// search between many years 
            	stats= this.getSentimentStatsByYear(item, params);
            	
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

	private SentimentStats getSentimentStatsByMonthOfYear(CategoryItem item, CustomSatatsParams params) {
		System.out.println("sentiments by month");
		SentimentStats stats=new SentimentStats();
		List<CategoryItem> items=new ArrayList<>();
		items.add(item);
		Date dateStart=getStartDateFromYear(params.getStartYear());
	    Date dateEnd=getEndDateFromYear(params.getStartYear());
	    List<Post> posts= postRepository.findByCategoryItemsAndDateBetween(items,dateStart,dateEnd);
	    if(posts.isEmpty()){
	    	return null;
	    	}
	    	
		for(Post p:posts){
			if(p.getDumaxSentment()==Post.sentiment.positive){
				stats.getPositivePosts().add(p);
			}else if(p.getDumaxSentment()==Post.sentiment.neutratl){
				stats.getNeutralPost().add(p);
			}else if(p.getDumaxSentment()==Post.sentiment.negative){
		    	stats.getNegativePosts().add(p);
		     }

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
	
	private SentimentStats getSentimentStatsByYear(CategoryItem item,CustomSatatsParams params){
		SentimentStats stats=new SentimentStats();
		List<CategoryItem> items=new ArrayList<>();
		items.add(item);
	    Date dateStart=getStartDateFromYear(params.getStartYear());
	    Date dateEnd=getEndDateFromYear(params.getStartYear());
	   List<Post> posts= postRepository.findByCategoryItemsAndDateBetween(items,dateStart,dateEnd);
	  
		for(Post p:posts){
			if(p.getDumaxSentment()==Post.sentiment.positive){
				stats.getPositivePosts().add(p);
			}else if(p.getDumaxSentment()==Post.sentiment.neutratl){
				stats.getNeutralPost().add(p);
			}else if(p.getDumaxSentment()==Post.sentiment.negative){
		    	stats.getNegativePosts().add(p);
		     }

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
