package ca.openquiz.webservice.parameter;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.google.appengine.api.datastore.Key;
import ca.openquiz.webservice.converter.KeyConverter;

@XmlRootElement(name="createSection")
public class CreateQuestionSetSectionParam {

	private String description;

	@XmlElementWrapper(name="questions")
	@XmlElement(name="key")
	@XmlJavaTypeAdapter(KeyConverter.class)
	private List<Key> questionList = new ArrayList<Key>();
	
	private int points = 10;
	
	public CreateQuestionSetSectionParam() {
		
	}
	
	@XmlTransient
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@XmlTransient
	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	@XmlTransient
	public List<Key> getQuestionList() {
		return questionList;
	}

	public void setQuestionList(List<Key> questionList) {
		this.questionList = questionList;
	}

}
