package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the game_question_set database table.
 * 
 */
@Entity
@Table(name="game_question_set")
public class GameQuestionSet implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private GameQuestionSetPK id;

	@Column(name="question_order")
	private int questionOrder;

	//bi-directional many-to-one association to Game
	@ManyToOne
	@JoinColumn(name="game_id")
	private Game game;

	//bi-directional many-to-one association to QuestionSet
	@ManyToOne
	@JoinColumn(name="question_set_id")
	private QuestionSet questionSet;

	public GameQuestionSet() {
	}

	public GameQuestionSetPK getId() {
		return this.id;
	}

	public void setId(GameQuestionSetPK id) {
		this.id = id;
	}

	public int getQuestionOrder() {
		return this.questionOrder;
	}

	public void setQuestionOrder(int questionOrder) {
		this.questionOrder = questionOrder;
	}

	public Game getGame() {
		return this.game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public QuestionSet getQuestionSet() {
		return this.questionSet;
	}

	public void setQuestionSet(QuestionSet questionSet) {
		this.questionSet = questionSet;
	}

}