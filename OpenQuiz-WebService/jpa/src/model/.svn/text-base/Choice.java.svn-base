package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the choice database table.
 * 
 */
@Entity
public class Choice implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="choice_id")
	private int choiceId;

	@Column(name="choice_content")
	private String choiceContent;

	//bi-directional many-to-one association to Answer
	@ManyToOne
	@JoinColumn(name="answer_id")
	private Answer answer;

	//bi-directional many-to-many association to Statement
	@ManyToMany(mappedBy="choices")
	private List<Statement> statements;

	public Choice() {
	}

	public int getChoiceId() {
		return this.choiceId;
	}

	public void setChoiceId(int choiceId) {
		this.choiceId = choiceId;
	}

	public String getChoiceContent() {
		return this.choiceContent;
	}

	public void setChoiceContent(String choiceContent) {
		this.choiceContent = choiceContent;
	}

	public Answer getAnswer() {
		return this.answer;
	}

	public void setAnswer(Answer answer) {
		this.answer = answer;
	}

	public List<Statement> getStatements() {
		return this.statements;
	}

	public void setStatements(List<Statement> statements) {
		this.statements = statements;
	}

}