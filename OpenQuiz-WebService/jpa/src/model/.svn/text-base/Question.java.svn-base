package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the question database table.
 * 
 */
@Entity
public class Question implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="question_id")
	private int questionId;

	@Column(name="answer_attempt")
	private int answerAttempt;

	@Column(name="correct_answer")
	private int correctAnswer;

	@Column(name="user_id")
	private int userId;

	//bi-directional many-to-one association to CurrentGameLog
	@OneToMany(mappedBy="question")
	private List<CurrentGameLog> currentGameLogs;

	//bi-directional many-to-one association to MasterGameLog
	@OneToMany(mappedBy="question")
	private List<MasterGameLog> masterGameLogs;

	//bi-directional many-to-many association to CatDegree
	@ManyToMany
	@JoinTable(
		name="question_degree"
		, joinColumns={
			@JoinColumn(name="question_id")
			}
		, inverseJoinColumns={
			@JoinColumn(name="cat_degree_id")
			}
		)
	private List<CatDegree> catDegrees;

	//bi-directional many-to-one association to CatLanguage
	@ManyToOne
	@JoinColumn(name="cat_language_id")
	private CatLanguage catLanguage;

	//bi-directional many-to-one association to CatQuestionStatus
	@ManyToOne
	@JoinColumn(name="cat_question_status_id")
	private CatQuestionStatus catQuestionStatus;

	//bi-directional many-to-one association to CatQuestionType
	@ManyToOne
	@JoinColumn(name="cat_question_type_id")
	private CatQuestionType catQuestionType;

	//bi-directional many-to-one association to Group
	@ManyToOne
	@JoinColumn(name="group_id")
	private Group group;

	//bi-directional many-to-many association to Group
	@ManyToMany
	@JoinTable(
		name="question_group"
		, joinColumns={
			@JoinColumn(name="question_id")
			}
		, inverseJoinColumns={
			@JoinColumn(name="group_id")
			}
		)
	private List<Group> groups;

	//bi-directional many-to-many association to QuestionSet
	@ManyToMany
	@JoinTable(
		name="question_question_set"
		, joinColumns={
			@JoinColumn(name="question_id")
			}
		, inverseJoinColumns={
			@JoinColumn(name="question_set_id")
			}
		)
	private List<QuestionSet> questionSets;

	//bi-directional many-to-one association to Statement
	@OneToMany(mappedBy="question")
	private List<Statement> statements;

	public Question() {
	}

	public int getQuestionId() {
		return this.questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public int getAnswerAttempt() {
		return this.answerAttempt;
	}

	public void setAnswerAttempt(int answerAttempt) {
		this.answerAttempt = answerAttempt;
	}

	public int getCorrectAnswer() {
		return this.correctAnswer;
	}

	public void setCorrectAnswer(int correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public List<CurrentGameLog> getCurrentGameLogs() {
		return this.currentGameLogs;
	}

	public void setCurrentGameLogs(List<CurrentGameLog> currentGameLogs) {
		this.currentGameLogs = currentGameLogs;
	}

	public List<MasterGameLog> getMasterGameLogs() {
		return this.masterGameLogs;
	}

	public void setMasterGameLogs(List<MasterGameLog> masterGameLogs) {
		this.masterGameLogs = masterGameLogs;
	}

	public List<CatDegree> getCatDegrees() {
		return this.catDegrees;
	}

	public void setCatDegrees(List<CatDegree> catDegrees) {
		this.catDegrees = catDegrees;
	}

	public CatLanguage getCatLanguage() {
		return this.catLanguage;
	}

	public void setCatLanguage(CatLanguage catLanguage) {
		this.catLanguage = catLanguage;
	}

	public CatQuestionStatus getCatQuestionStatus() {
		return this.catQuestionStatus;
	}

	public void setCatQuestionStatus(CatQuestionStatus catQuestionStatus) {
		this.catQuestionStatus = catQuestionStatus;
	}

	public CatQuestionType getCatQuestionType() {
		return this.catQuestionType;
	}

	public void setCatQuestionType(CatQuestionType catQuestionType) {
		this.catQuestionType = catQuestionType;
	}

	public Group getGroup() {
		return this.group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public List<Group> getGroups() {
		return this.groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public List<QuestionSet> getQuestionSets() {
		return this.questionSets;
	}

	public void setQuestionSets(List<QuestionSet> questionSets) {
		this.questionSets = questionSets;
	}

	public List<Statement> getStatements() {
		return this.statements;
	}

	public void setStatements(List<Statement> statements) {
		this.statements = statements;
	}

}