package ca.openquiz.webservice.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import ca.openquiz.webservice.converter.DateConverter;
import ca.openquiz.webservice.converter.KeyConverter;
import ca.openquiz.webservice.converter.CategoryConverter;
import ca.openquiz.webservice.enums.Degree;
import ca.openquiz.webservice.enums.Language;
import ca.openquiz.webservice.enums.QuestionType;
import ca.openquiz.webservice.enums.ReportReason;
import ca.openquiz.webservice.manager.DBManager;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

@PersistenceCapable(detachable = "true")
@Inheritance(strategy = InheritanceStrategy.SUBCLASS_TABLE)
public abstract class Question extends BaseModel {

	@Persistent
	@XmlElement
	@XmlJavaTypeAdapter(KeyConverter.class)
	private Key author;

	@Persistent
	@XmlElement
	private Boolean isReported = false;

	@Persistent
	@XmlElement
	protected int correctAnswer = 0;

	@Persistent
	@XmlElement
	protected int attemptedAnswer = 0;

	@Persistent
	@XmlElement
	protected Degree degree;

	@Persistent
	@XmlElement
	@XmlJavaTypeAdapter(DateConverter.class)
	protected Date availableDate = new Date();
	
	@Persistent
	@XmlTransient
	public Date lastGeneratedDate = new Date(0);

	@Persistent
	@XmlElement
	protected Language language;
	
	@Persistent
	@XmlElement
	@XmlJavaTypeAdapter(CategoryConverter.class)
	protected Key category;
	
	@Persistent
	@XmlElement
	protected String reportComment;
	
	@Persistent
	@XmlElement
	protected ReportReason reportReason = ReportReason.None;

	@XmlTransient
	public String getReportComment() {
		return reportComment;
	}

	public void setReportComment(String reportComment) {
		this.reportComment = reportComment;
	}

	@XmlTransient
	public ReportReason getReportReason() {
		return reportReason;
	}

	public void setReportReason(ReportReason reportReason) {
		this.reportReason = reportReason;
	}

	@Persistent
	@XmlJavaTypeAdapter(KeyConverter.class)
	protected List<Key> groups = new ArrayList<Key>();

	public Question() {

	}
	
	@XmlTransient
	public Key getAuthor() {
		return author;
	}

	public void setAuthor(Key author) {
		this.author = author;
	}

	@XmlTransient
	public int getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(int correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	@XmlTransient
	public int getAttemptedAnswer() {
		return attemptedAnswer;
	}

	public void setAttemptedAnswer(int attemptedAnswer) {
		this.attemptedAnswer = attemptedAnswer;
	}

	@XmlTransient
	public Boolean getIsReported() {
		return isReported;
	}

	public void setIsReported(Boolean isReported) {
		this.isReported = isReported;
	}

	@XmlTransient
	public Degree getDegree() {
		return degree;
	}
	
	public void setDegree(Degree degree) {
		this.degree = degree;
	}

	@XmlTransient
	public Date getAvailableDate() {
		return this.availableDate;
	}

	public void setAvailableDate(Date availableDate) {
		this.availableDate = availableDate;
	}

	@XmlTransient
	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	@XmlTransient
	public Key getCategory() {
		return this.category;
	}
	
	public void setCategory(Key category) {
		this.category = category;
	}
	
	@XmlTransient
	public List<Key> getGroups() {
		return groups;
	}

	public void addGroup(Group group) {
		if (group.getKey() == null) {
			DBManager.save(group);
		}

		if (this.key == null) {
			DBManager.save(this);
		}

		this.groups.add(group.getKey());
		group.getQuestions().add(this.getKey());

		DBManager.save(group);
		DBManager.save(this);
	}

	public void removeGroup(Group group) {
		if (this.groups.contains(group.getKey())) {
			this.groups.remove(group.getKey());
			group.getQuestions().remove(this.getKey());

			DBManager.save(group);
			DBManager.save(this);
		}
	}
	
	@XmlTransient
	public Date getLastGeneratedDate(){
		return this.lastGeneratedDate;
	}
	
	public void setLastGeneratedDate(Date lastQuestionSetGeneratedDate){
		this.lastGeneratedDate = lastQuestionSetGeneratedDate;
	}
	
	public abstract QuestionType getQuestionType();

	@Override
	public void delete() {
		// A question cannot be deleted.
		// It can only be set to reported
	}
	
	@Override
	public boolean isValid(){
		
		return this.availableDate != null && this.category != null 
				&& this.degree != null && this.language != null
				&& this.getAuthor() != null;
	}

	@SuppressWarnings("unchecked")
	public void setPropertiesFromEntity(Entity e) {
		this.author = (Key) e.getProperty("author");
		this.attemptedAnswer = ((Long) (e.getProperty("attemptedAnswer"))).intValue();
		this.availableDate = (Date) e.getProperty("availableDate");
		this.category = (Key) e.getProperty("category");
		this.correctAnswer = ((Long) (e.getProperty("correctAnswer"))).intValue();
		this.degree = Degree.valueOf((String) e.getProperty("degree"));
		this.groups = (List<Key>) e.getProperty("groups");
		this.isReported = (Boolean) e.getProperty("isReported");
		this.key = (Key) e.getProperty("key");
		this.language = Language.valueOf((String) e.getProperty("language"));
		this.lastGeneratedDate = (Date) e.getProperty("lastGeneratedDate");
		this.reportComment = (String) e.getProperty("reportComment");
		this.reportReason = ReportReason.valueOf((String) e.getProperty("reportReason"));
	}	
	
}