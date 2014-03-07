package ca.openquiz.webservice.model;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.google.appengine.api.datastore.Entity;

import ca.openquiz.webservice.enums.Bucket;
import ca.openquiz.webservice.enums.QuestionType;
import ca.openquiz.webservice.manager.FileManager;

@PersistenceCapable
@XmlRootElement
public class QuestionMedia extends Question {

	@Persistent
	@XmlTransient
	private String mediaId;

	@Persistent
	@XmlTransient
	private String answer;
	
	@Persistent
	@XmlTransient
	private String statement;

	public QuestionMedia() {

	}

	public QuestionMedia(String statement, String mediaId, String answer) {
		this.statement = statement;
		this.mediaId = mediaId;
		this.answer = answer;
	}
	
	@XmlElement
	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	@XmlElement
	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	@XmlElement
	public String getStatement() {
		return statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}
	
	@XmlElement
	public String getMediaUrl(){
		return FileManager.getFilePublicUrl(mediaId, Bucket.MediaQuestion);
	}
	
	@Override
	public QuestionType getQuestionType() {
		return QuestionType.Media;
	}
	
	@XmlTransient
	@Override
	public boolean isValid(){
		return super.isValid() 
				&& this.mediaId != null && !this.mediaId.isEmpty()
				&& this.statement != null && !this.statement.isEmpty()
				&& this.answer != null && !this.answer.isEmpty();
	}

	@Override
	public void setPropertiesFromEntity(Entity e) {
		super.setPropertiesFromEntity(e);
		this.mediaId = (String) e.getProperty("mediaId");
		this.answer = (String) e.getProperty("answer");
		this.statement = (String) e.getProperty("statement");
	}
}