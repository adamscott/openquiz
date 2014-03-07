package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the game database table.
 * 
 */
@Entity
public class Game implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="game_id")
	private int gameId;

	@Temporal(TemporalType.DATE)
	@Column(name="game_date")
	private Date gameDate;

	@Column(name="team_one_score")
	private int teamOneScore;

	@Column(name="team_two_score")
	private int teamTwoScore;

	//bi-directional many-to-one association to CurrentGameLog
	@OneToMany(mappedBy="game")
	private List<CurrentGameLog> currentGameLogs;

	//bi-directional many-to-one association to Team
	@ManyToOne
	@JoinColumn(name="team_one_id")
	private Team team1;

	//bi-directional many-to-one association to Team
	@ManyToOne
	@JoinColumn(name="team_two_id")
	private Team team2;

	//bi-directional many-to-one association to Tournament
	@ManyToOne
	@JoinColumn(name="tournament_id")
	private Tournament tournament;

	//bi-directional many-to-one association to GameQuestionSet
	@OneToMany(mappedBy="game")
	private List<GameQuestionSet> gameQuestionSets;

	//bi-directional many-to-one association to MasterGameLog
	@OneToMany(mappedBy="game")
	private List<MasterGameLog> masterGameLogs;

	public Game() {
	}

	public int getGameId() {
		return this.gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	public Date getGameDate() {
		return this.gameDate;
	}

	public void setGameDate(Date gameDate) {
		this.gameDate = gameDate;
	}

	public int getTeamOneScore() {
		return this.teamOneScore;
	}

	public void setTeamOneScore(int teamOneScore) {
		this.teamOneScore = teamOneScore;
	}

	public int getTeamTwoScore() {
		return this.teamTwoScore;
	}

	public void setTeamTwoScore(int teamTwoScore) {
		this.teamTwoScore = teamTwoScore;
	}

	public List<CurrentGameLog> getCurrentGameLogs() {
		return this.currentGameLogs;
	}

	public void setCurrentGameLogs(List<CurrentGameLog> currentGameLogs) {
		this.currentGameLogs = currentGameLogs;
	}

	public Team getTeam1() {
		return this.team1;
	}

	public void setTeam1(Team team1) {
		this.team1 = team1;
	}

	public Team getTeam2() {
		return this.team2;
	}

	public void setTeam2(Team team2) {
		this.team2 = team2;
	}

	public Tournament getTournament() {
		return this.tournament;
	}

	public void setTournament(Tournament tournament) {
		this.tournament = tournament;
	}

	public List<GameQuestionSet> getGameQuestionSets() {
		return this.gameQuestionSets;
	}

	public void setGameQuestionSets(List<GameQuestionSet> gameQuestionSets) {
		this.gameQuestionSets = gameQuestionSets;
	}

	public List<MasterGameLog> getMasterGameLogs() {
		return this.masterGameLogs;
	}

	public void setMasterGameLogs(List<MasterGameLog> masterGameLogs) {
		this.masterGameLogs = masterGameLogs;
	}

}