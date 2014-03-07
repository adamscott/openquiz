package ca.openquiz.webservice.model;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.google.appengine.api.datastore.Key;

import ca.openquiz.webservice.enums.Degree;
import ca.openquiz.webservice.enums.QuestionTarget;
import ca.openquiz.webservice.enums.QuestionType;
import ca.openquiz.webservice.manager.DBManager;
import ca.openquiz.webservice.converter.KeyConverter;

@XmlRootElement
@PersistenceCapable(detachable="true")
public class TemplateSection extends BaseModel {

	@Persistent
	@XmlJavaTypeAdapter(KeyConverter.class)
	private Key category;
	
	@Persistent
	private String description;
	
	@Persistent
	private int points = 10;
	
	@Persistent
	private int nbQuestions = 10;
	
	@Persistent
	private Degree difficulty = null;
	
	@Persistent
	private QuestionType questionType = null;
	
	@Persistent
	private QuestionTarget questionTarget = QuestionTarget.Individual;

	public TemplateSection() {
		
	}
	
	public TemplateSection(QuestionType questionType) {
		this.questionType = questionType;
	}
	
	public void copy(TemplateSection templateSection)
	{
		this.category 		= templateSection.category;
		this.difficulty 	= templateSection.difficulty;
		this.nbQuestions 	= templateSection.nbQuestions;
		this.points 		= templateSection.points;
		this.questionType 	= templateSection.questionType;
		this.questionTarget	= templateSection.questionTarget;
	}
	
	@XmlElement
	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	@XmlTransient
	public Key getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		if(category.getKey() == null)
		{
			DBManager.save(category);
		}
		if(this.key == null)
		{
			DBManager.save(this);
		}
		this.category = category.getKey();
	}

	@XmlElement
	public int getNbQuestions() {
		return nbQuestions;
	}

	public void setNbQuestions(int nbQuestions) {
		this.nbQuestions = nbQuestions;
	}

	@XmlElement
	public Degree getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(Degree difficulty) {
		this.difficulty = difficulty;
	}

	@XmlElement
	public QuestionType getQuestionType() {
		return questionType;
	}

	public void setQuestionType(QuestionType questionType) {
		this.questionType = questionType;
	}

	@XmlElement
	public QuestionTarget getQuestionTarget() {
		return questionTarget;
	}

	public void setQuestionTarget(QuestionTarget questionTarget) {
		this.questionTarget = questionTarget;
	}
	
	@XmlElement
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public void delete() {
		DBManager.delete(this.getKey());
		key = null;
	}

	@Override
	public boolean isValid() {
		return points > 0 && nbQuestions > 0;
	}

}
