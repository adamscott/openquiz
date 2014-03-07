package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the master_game_log database table.
 * 
 */
@Entity
@Table(name="master_game_log")
public class MasterGameLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="master_game_log_id")
	private int masterGameLogId;

	private double delay;

	private byte replica;

	@Column(name="user_id")
	private int userId;

	//bi-directional many-to-one association to CatResult
	@ManyToOne
	@JoinColumn(name="cat_result_id")
	private CatResult catResult;

	//bi-directional many-to-one association to Game
	@ManyToOne
	@JoinColumn(name="game_id")
	private Game game;

	//bi-directional many-to-one association to Question
	@ManyToOne
	@JoinColumn(name="question_id")
	private Question question;

	//bi-directional many-to-one association to Team
	@ManyToOne
	@JoinColumn(name="team_id")
	private Team team;

	public MasterGameLog() {
	}

	public int getMasterGameLogId() {
		return this.masterGameLogId;
	}

	public void setMasterGameLogId(int masterGameLogId) {
		this.masterGameLogId = masterGameLogId;
	}

	public double getDelay() {
		return this.delay;
	}

	public void setDelay(double delay) {
		this.delay = delay;
	}

	public byte getReplica() {
		return this.replica;
	}

	public void setReplica(byte replica) {
		this.replica = replica;
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public CatResult getCatResult() {
		return this.catResult;
	}

	public void setCatResult(CatResult catResult) {
		this.catResult = catResult;
	}

	public Game getGame() {
		return this.game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public Question getQuestion() {
		return this.question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Team getTeam() {
		return this.team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

}