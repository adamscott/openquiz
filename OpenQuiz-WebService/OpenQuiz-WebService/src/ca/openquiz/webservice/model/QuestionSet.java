package ca.openquiz.webservice.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import ca.openquiz.webservice.converter.DateConverter;
import ca.openquiz.webservice.converter.KeyConverter;
import ca.openquiz.webservice.manager.DBManager;

import com.google.appengine.api.datastore.Key;


@XmlRootElement
@PersistenceCapable()
public class QuestionSet extends BaseModel {
	
	@Persistent
	@XmlElement
	@XmlJavaTypeAdapter(DateConverter.class)
	private Date availableDate;

	@Persistent
	@XmlElement
	private String name;
	
	@Persistent
	@XmlElement
	private boolean deletedAfterGame;
	
	@Persistent
	@XmlElement
	@XmlJavaTypeAdapter(KeyConverter.class)
	private Key author;
	
	@Persistent(defaultFetchGroup="true")
	@XmlTransient
	private List<QuestionSetSection> sectionList = new ArrayList<QuestionSetSection>();
	
	@Persistent
	@XmlJavaTypeAdapter(KeyConverter.class)
	private List<Key> groups = new ArrayList<Key>();
	
	public QuestionSet() {
		
	}
	
	public List<QuestionSetSection> getSections() {
		return sectionList;
	}
	
	public void setSections(List<QuestionSetSection> sections){
		this.sectionList = sections;
	}

	@XmlTransient
	public List<Key> getGroups() {
		return groups;
	}
	
	public void setGroups(List<Key> groups) {
		this.groups = groups;
	}

	@XmlTransient
	public Date getAvailableDate() {
		return availableDate;
	}

	public void setAvailableDate(Date availableDate) {
		this.availableDate = availableDate;
	}

	@XmlTransient
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@XmlTransient
	public Key getAuthor() {
		return this.author;
	}

	public void setAuthor(Key author) {
		this.author = author;
	}

	@XmlTransient
	public boolean isDeletedAfterGame() {
		return deletedAfterGame;
	}

	public void setDeletedAfterGame(boolean deletedAfterGame) {
		this.deletedAfterGame = deletedAfterGame;
	}
	
	public int getPossiblePoints() {
		int returnValue = 0;
		
		for(QuestionSetSection qs : this.sectionList){
			returnValue += qs.getQuestionList().size() * qs.getPoints();
		}
		
		return returnValue;
	}
	
	public int getPossibleAttempts() {
		int returnValue = 0;
		
		for(QuestionSetSection qs : this.sectionList){
			returnValue += qs.getQuestionList().size();
		}
		
		return returnValue;
	}

	@Override
	public void delete() {
		for(QuestionSetSection q : this.getSections()) {
			q.delete();
		}
		DBManager.delete(this.getKey());
		this.sectionList.clear();
		this.key = null;
	}

	@Override
	public boolean isValid() {
		return availableDate != null && name != null && !name.isEmpty() 
				&& sectionList != null && !sectionList.isEmpty();
	}
}
