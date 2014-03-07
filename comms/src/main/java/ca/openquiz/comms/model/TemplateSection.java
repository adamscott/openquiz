package ca.openquiz.comms.model;


import ca.openquiz.comms.enums.Degree;
import ca.openquiz.comms.enums.QuestionTarget;
import ca.openquiz.comms.enums.QuestionType;

public class TemplateSection extends BaseModel {
	private static final long serialVersionUID = -741568945612751595L;

	private String category;
	
	private int points = 10;
	
	private int nbQuestions = 10;
	
	private Degree difficulty = null;
	
	private QuestionType questionType = null;
	
	private QuestionTarget questionTarget = QuestionTarget.Individual;
	
	private String description = "";
	
	public TemplateSection() {
		
	}
	
	public TemplateSection(String category, Degree difficulty, int nbQuestions, int points, QuestionType questionType, QuestionTarget questionTarget, String description) {
		this.category	 	= category;
		this.difficulty 	= difficulty;
		this.nbQuestions 	= nbQuestions;
		this.points 		= points;
		this.questionType 	= questionType;
		this.questionTarget = questionTarget;
		this.description    = description;
	}
	
	public TemplateSection(QuestionType questionType) {
		this.questionType = questionType;
	}
	
	public void copy(TemplateSection templateSection)
	{
		this.category	    = templateSection.category;
		this.difficulty 	= templateSection.difficulty;
		this.nbQuestions 	= templateSection.nbQuestions;
		this.points 		= templateSection.points;
		this.questionType 	= templateSection.questionType;
	}
	
	public void setQuestionTarget(QuestionTarget questionTarget){
		this.questionTarget = questionTarget;
	}
	
	public QuestionTarget getQuestionTarget(){
		return this.questionTarget;
	}
	
	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getNbQuestions() {
		return nbQuestions;
	}

	public void setNbQuestions(int nbQuestions) {
		this.nbQuestions = nbQuestions;
	}

	public Degree getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(Degree difficulty) {
		this.difficulty = difficulty;
	}

	public QuestionType getQuestionType() {
		return questionType;
	}

	public void setQuestionType(QuestionType questionType) {
		this.questionType = questionType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
