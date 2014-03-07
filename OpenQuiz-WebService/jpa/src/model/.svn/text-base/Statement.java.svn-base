package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the statement database table.
 * 
 */
@Entity
public class Statement implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="statement_id")
	private int statementId;

	private int points;

	@Column(name="statement_content")
	private String statementContent;

	//bi-directional many-to-one association to Answer
	@OneToMany(mappedBy="statement")
	private List<Answer> answers;

	//bi-directional many-to-one association to CatStatementType
	@ManyToOne
	@JoinColumn(name="cat_statement_type_id")
	private CatStatementType catStatementType;

	//bi-directional many-to-many association to Choice
	@ManyToMany
	@JoinTable(
		name="statement_choice"
		, joinColumns={
			@JoinColumn(name="statement_id")
			}
		, inverseJoinColumns={
			@JoinColumn(name="choice_id")
			}
		)
	private List<Choice> choices;

	//bi-directional many-to-one association to Question
	@ManyToOne
	@JoinColumn(name="question_id")
	private Question question;

	public Statement() {
	}

	public int getStatementId() {
		return this.statementId;
	}

	public void setStatementId(int statementId) {
		this.statementId = statementId;
	}

	public int getPoints() {
		return this.points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public String getStatementContent() {
		return this.statementContent;
	}

	public void setStatementContent(String statementContent) {
		this.statementContent = statementContent;
	}

	public List<Answer> getAnswers() {
		return this.answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	public CatStatementType getCatStatementType() {
		return this.catStatementType;
	}

	public void setCatStatementType(CatStatementType catStatementType) {
		this.catStatementType = catStatementType;
	}

	public List<Choice> getChoices() {
		return this.choices;
	}

	public void setChoices(List<Choice> choices) {
		this.choices = choices;
	}

	public Question getQuestion() {
		return this.question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

}