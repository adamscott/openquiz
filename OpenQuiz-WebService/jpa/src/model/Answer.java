package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the answer database table.
 * 
 */
@Entity
public class Answer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="answer_id")
	private int answerId;

	@Column(name="answer_content")
	private String answerContent;

	//bi-directional many-to-one association to Statement
	@ManyToOne
	@JoinColumn(name="statement_id")
	private Statement statement;

	//bi-directional many-to-one association to Choice
	@OneToMany(mappedBy="answer")
	private List<Choice> choices;

	public Answer() {
	}

	public int getAnswerId() {
		return this.answerId;
	}

	public void setAnswerId(int answerId) {
		this.answerId = answerId;
	}

	public String getAnswerContent() {
		return this.answerContent;
	}

	public void setAnswerContent(String answerContent) {
		this.answerContent = answerContent;
	}

	public Statement getStatement() {
		return this.statement;
	}

	public void setStatement(Statement statement) {
		this.statement = statement;
	}

	public List<Choice> getChoices() {
		return this.choices;
	}

	public void setChoices(List<Choice> choices) {
		this.choices = choices;
	}

}