package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the question_set database table.
 * 
 */
@Entity
@Table(name="question_set")
public class QuestionSet implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="question_set_id")
	private int questionSetId;

	@Temporal(TemporalType.DATE)
	@Column(name="available_date")
	private Date availableDate;

	private String name;

	//bi-directional many-to-one association to GameQuestionSet
	@OneToMany(mappedBy="questionSet")
	private List<GameQuestionSet> gameQuestionSets;

	//bi-directional many-to-many association to Question
	@ManyToMany(mappedBy="questionSets")
	private List<Question> questions;

	public QuestionSet() {
	}

	public int getQuestionSetId() {
		return this.questionSetId;
	}

	public void setQuestionSetId(int questionSetId) {
		this.questionSetId = questionSetId;
	}

	public Date getAvailableDate() {
		return this.availableDate;
	}

	public void setAvailableDate(Date availableDate) {
		this.availableDate = availableDate;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<GameQuestionSet> getGameQuestionSets() {
		return this.gameQuestionSets;
	}

	public void setGameQuestionSets(List<GameQuestionSet> gameQuestionSets) {
		this.gameQuestionSets = gameQuestionSets;
	}

	public List<Question> getQuestions() {
		return this.questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

}