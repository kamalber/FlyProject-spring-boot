package com.weberfly.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;

import com.weberfly.dao.TwitterKeyWordRepository;
import com.weberfly.service.TwitterKeyWordService;

@Entity
@Table(name = "TwitterKeyWord")
public class TwitterKeyWord {

	public static enum threadStat {
		running, suspended, stoped,
	}
	public static enum Period{
		daily,hourly,weekly
	}

	/**
	 * Description of the property id.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String word;
	
	private Date dateCreation;

	@Enumerated(EnumType.STRING)
	private threadStat stat;

	private Date startDate;

	@Enumerated(EnumType.STRING)
	private Period period;

	private String threadPlaningDescription;


	@OneToMany(cascade = CascadeType.ALL, mappedBy = "keyWord")
	private List<Twitte> twittes;

	@ManyToOne(cascade = CascadeType.MERGE ,fetch= FetchType.EAGER)
	private Category category;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public void setTwittes(List<Twitte> twittes) {
		this.twittes = twittes;
	}

	
	public threadStat getStat() {
		return stat;
	}

	public void setStat(threadStat stat) {
		this.stat = stat;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Period getPeriod() {
		return period;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}

	public String getThreadPlaningDescription() {
		return threadPlaningDescription;
	}

	public void setThreadPlaningDescription(String threadPlaningDescription) {
		this.threadPlaningDescription = threadPlaningDescription;
	}
	


	public void setCategory(Category category) {
		this.category = category;
	}

	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}


	public Category getCategory() {
		return this.category;
	}

	@Override
	public String toString() {
		return "TwitterKeyWord [id=" + id + ", word=" + word + ", stat=" + stat + ", startDate=" + startDate
				+ ", period=" + period + ", threadPlaningDescription=" + threadPlaningDescription + "]";
	}

}
