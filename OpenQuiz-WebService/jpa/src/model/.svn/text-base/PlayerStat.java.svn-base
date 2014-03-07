package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the player_stats database table.
 * 
 */
@Entity
@Table(name="player_stats")
public class PlayerStat implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="user_id")
	private int userId;

	@Column(name="answer_attempt")
	private int answerAttempt;

	@Column(name="average_delay")
	private double averageDelay;

	@Column(name="correct_answer")
	private int correctAnswer;

	private int penalty;

	private int replica;

	//bi-directional many-to-one association to Team
	@ManyToOne
	@JoinColumn(name="team_id")
	private Team team;

	public PlayerStat() {
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getAnswerAttempt() {
		return this.answerAttempt;
	}

	public void setAnswerAttempt(int answerAttempt) {
		this.answerAttempt = answerAttempt;
	}

	public double getAverageDelay() {
		return this.averageDelay;
	}

	public void setAverageDelay(double averageDelay) {
		this.averageDelay = averageDelay;
	}

	public int getCorrectAnswer() {
		return this.correctAnswer;
	}

	public void setCorrectAnswer(int correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	public int getPenalty() {
		return this.penalty;
	}

	public void setPenalty(int penalty) {
		this.penalty = penalty;
	}

	public int getReplica() {
		return this.replica;
	}

	public void setReplica(int replica) {
		this.replica = replica;
	}

	public Team getTeam() {
		return this.team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

}