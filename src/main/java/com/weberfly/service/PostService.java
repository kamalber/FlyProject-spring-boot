package com.weberfly.service;

import static org.assertj.core.api.Assertions.shouldHaveThrown;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weberfly.dao.CategoryItemRepository;
import com.weberfly.dao.PostRepository;
import com.weberfly.entities.Category;
import com.weberfly.entities.CategoryItem;
import com.weberfly.entities.Post;

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
	
	public List<Post> getAnalysedPosts(String querySearche,String startDate, String endDate){
		DateFormat formatter = new SimpleDateFormat("yy-dd-MM");
		CategoryItem item =categoryItemRepository.findByNameIgnoreCase(querySearche);
		List<CategoryItem> items=null;
		if(item!=null){
			items=new ArrayList<>();
			items.add(item);
			 Date dateStart,dateEnd;
			try {
				System.out.println(" date is parsed succesfully");
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

}
