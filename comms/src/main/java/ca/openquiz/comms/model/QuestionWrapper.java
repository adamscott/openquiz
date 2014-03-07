package ca.openquiz.comms.model;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public class QuestionWrapper{

	@JsonProperty("questionAnagram")
	private ArrayList<QuestionAnagram> questionAnagram = new ArrayList<QuestionAnagram>();

	@JsonProperty("questionAssociation")
	private ArrayList<QuestionAssociation> questionAssociation = new ArrayList<QuestionAssociation>();

	@JsonProperty("questionCorrectWord")
	private ArrayList<QuestionCorrectWord> questionCorrectWord = new ArrayList<QuestionCorrectWord>();
	
	@JsonProperty("questionGeneral")
	private ArrayList<QuestionGeneral> questionGeneral = new ArrayList<QuestionGeneral>();
	
	@JsonProperty("questionIdentification")
	private ArrayList<QuestionIdentification> questionIdentification = new ArrayList<QuestionIdentification>();
	
	@JsonProperty("questionIntru")
	private ArrayList<QuestionIntru> questionIntru = new ArrayList<QuestionIntru>();

	@JsonProperty("questionMedia")
	private ArrayList<QuestionMedia> questionMedia = new ArrayList<QuestionMedia>();
	
	public ArrayList<QuestionAnagram> getQuestionAnagram() {
		return questionAnagram;
	}
	public void setQuestionAnagram(ArrayList<QuestionAnagram> questionAnagram) {
		this.questionAnagram = questionAnagram;
	}
	public ArrayList<QuestionAssociation> getQuestionAssociation() {
		return questionAssociation;
	}
	public void setQuestionAssociation(ArrayList<QuestionAssociation> questionAssociation) {
		this.questionAssociation = questionAssociation;
	}
	public ArrayList<QuestionCorrectWord> getQuestionCorrectWord() {
		return questionCorrectWord;
	}
	public void setQuestionCorrectWord(ArrayList<QuestionCorrectWord> questionCorrectWord) {
		this.questionCorrectWord = questionCorrectWord;
	}
	public ArrayList<QuestionGeneral> getQuestionGeneral() {
		return questionGeneral;
	}
	public void setQuestionGeneral(ArrayList<QuestionGeneral> questionGeneral) {
		this.questionGeneral = questionGeneral;
	}
	public ArrayList<QuestionIdentification> getQuestionIdentification() {
		return questionIdentification;
	}
	public void setQuestionIdentification(ArrayList<QuestionIdentification> questionIdentification) {
		this.questionIdentification = questionIdentification;
	}
	public ArrayList<QuestionIntru> getQuestionIntru() {
		return questionIntru;
	}
	public void setQuestionIntru(ArrayList<QuestionIntru> questionIntru) {
		this.questionIntru = questionIntru;
	}
	public ArrayList<QuestionMedia> getQuestionMedia() {
		return questionMedia;
	}
	public void setQuestionMedia(ArrayList<QuestionMedia> questionMedia) {
		this.questionMedia = questionMedia;
	}
	
	public void addQuestion(Question question){
		if (question instanceof QuestionAnagram){
			questionAnagram.add((QuestionAnagram) question);
		}
		else if (question instanceof QuestionAssociation){
			questionAssociation.add((QuestionAssociation) question);
		}
		else if (question instanceof QuestionCorrectWord){
			questionCorrectWord.add((QuestionCorrectWord) question);
		}
		else if (question instanceof QuestionGeneral){
			questionGeneral.add((QuestionGeneral) question);
		}
		else if (question instanceof QuestionIdentification){
			questionIdentification.add((QuestionIdentification) question);
		}
		else if (question instanceof QuestionIntru){
			questionIntru.add((QuestionIntru) question);
		}
		else if (question instanceof QuestionMedia){
			questionMedia.add((QuestionMedia) question);
		}
	}
	
	@JsonIgnore
	public List<Question> getAllQuestions() {
		ArrayList<Question> q = new ArrayList<Question>();
		q.addAll(questionAnagram);
		q.addAll(questionAssociation);
		q.addAll(questionCorrectWord);
		q.addAll(questionGeneral);
		q.addAll(questionIdentification);
		q.addAll(questionIntru);
		q.addAll(questionMedia);
		return q;
	}
	
}
