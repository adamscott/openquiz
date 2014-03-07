package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the team_stats database table.
 * 
 */
@Entity
@Table(name="team_stats")
public class TeamStat implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="team_id")
	private int teamId;

	@Column(name="average_points_against")
	private int averagePointsAgainst;

	@Column(name="average_points_for")
	private int averagePointsFor;

	@Column(name="average_team_delay")
	private double averageTeamDelay;

	@Column(name="game_played")
	private int gamePlayed;

	@Column(name="game_won")
	private int gameWon;

	@Column(name="total_team_attempt")
	private int totalTeamAttempt;

	@Column(name="total_team_correct_answer")
	private int totalTeamCorrectAnswer;

	@Column(name="total_team_penalty")
	private int totalTeamPenalty;

	@Column(name="total_team_replica")
	private int totalTeamReplica;

	//bi-directional one-to-one association to Team
	@OneToOne
	@JoinColumn(name="team_id")
	private Team team;

	public TeamStat() {
	}

	public int getTeamId() {
		return this.teamId;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}

	public int getAveragePointsAgainst() {
		return this.averagePointsAgainst;
	}

	public void setAveragePointsAgainst(int averagePointsAgainst) {
		this.averagePointsAgainst = averagePointsAgainst;
	}

	public int getAveragePointsFor() {
		return this.averagePointsFor;
	}

	public void setAveragePointsFor(int averagePointsFor) {
		this.averagePointsFor = averagePointsFor;
	}

	public double getAverageTeamDelay() {
		return this.averageTeamDelay;
	}

	public void setAverageTeamDelay(double averageTeamDelay) {
		this.averageTeamDelay = averageTeamDelay;
	}

	public int getGamePlayed() {
		return this.gamePlayed;
	}

	public void setGamePlayed(int gamePlayed) {
		this.gamePlayed = gamePlayed;
	}

	public int getGameWon() {
		return this.gameWon;
	}

	public void setGameWon(int gameWon) {
		this.gameWon = gameWon;
	}

	public int getTotalTeamAttempt() {
		return this.totalTeamAttempt;
	}

	public void setTotalTeamAttempt(int totalTeamAttempt) {
		this.totalTeamAttempt = totalTeamAttempt;
	}

	public int getTotalTeamCorrectAnswer() {
		return this.totalTeamCorrectAnswer;
	}

	public void setTotalTeamCorrectAnswer(int totalTeamCorrectAnswer) {
		this.totalTeamCorrectAnswer = totalTeamCorrectAnswer;
	}

	public int getTotalTeamPenalty() {
		return this.totalTeamPenalty;
	}

	public void setTotalTeamPenalty(int totalTeamPenalty) {
		this.totalTeamPenalty = totalTeamPenalty;
	}

	public int getTotalTeamReplica() {
		return this.totalTeamReplica;
	}

	public void setTotalTeamReplica(int totalTeamReplica) {
		this.totalTeamReplica = totalTeamReplica;
	}

	public Team getTeam() {
		return this.team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

}