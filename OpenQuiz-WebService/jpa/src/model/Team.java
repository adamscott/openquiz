package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the team database table.
 * 
 */
@Entity
public class Team implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="team_id")
	private int teamId;

	private String name;

	//bi-directional many-to-one association to CurrentGameLog
	@OneToMany(mappedBy="team")
	private List<CurrentGameLog> currentGameLogs;

	//bi-directional many-to-one association to Game
	@OneToMany(mappedBy="team1")
	private List<Game> games1;

	//bi-directional many-to-one association to Game
	@OneToMany(mappedBy="team2")
	private List<Game> games2;

	//bi-directional many-to-one association to MasterGameLog
	@OneToMany(mappedBy="team")
	private List<MasterGameLog> masterGameLogs;

	//bi-directional many-to-one association to PlayerStat
	@OneToMany(mappedBy="team")
	private List<PlayerStat> playerStats;

	//bi-directional many-to-one association to CatDegree
	@ManyToOne
	@JoinColumn(name="cat_degree_id")
	private CatDegree catDegree;

	//bi-directional many-to-one association to Group
	@ManyToOne
	@JoinColumn(name="group_id")
	private Group group;

	//bi-directional one-to-one association to TeamStat
	@OneToOne(mappedBy="team")
	private TeamStat teamStat;

	//bi-directional many-to-many association to Tournament
	@ManyToMany(mappedBy="teams")
	private List<Tournament> tournaments;

	//bi-directional many-to-one association to UserTeam
	@OneToMany(mappedBy="team")
	private List<UserTeam> userTeams;

	public Team() {
	}

	public int getTeamId() {
		return this.teamId;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<CurrentGameLog> getCurrentGameLogs() {
		return this.currentGameLogs;
	}

	public void setCurrentGameLogs(List<CurrentGameLog> currentGameLogs) {
		this.currentGameLogs = currentGameLogs;
	}

	public List<Game> getGames1() {
		return this.games1;
	}

	public void setGames1(List<Game> games1) {
		this.games1 = games1;
	}

	public List<Game> getGames2() {
		return this.games2;
	}

	public void setGames2(List<Game> games2) {
		this.games2 = games2;
	}

	public List<MasterGameLog> getMasterGameLogs() {
		return this.masterGameLogs;
	}

	public void setMasterGameLogs(List<MasterGameLog> masterGameLogs) {
		this.masterGameLogs = masterGameLogs;
	}

	public List<PlayerStat> getPlayerStats() {
		return this.playerStats;
	}

	public void setPlayerStats(List<PlayerStat> playerStats) {
		this.playerStats = playerStats;
	}

	public CatDegree getCatDegree() {
		return this.catDegree;
	}

	public void setCatDegree(CatDegree catDegree) {
		this.catDegree = catDegree;
	}

	public Group getGroup() {
		return this.group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public TeamStat getTeamStat() {
		return this.teamStat;
	}

	public void setTeamStat(TeamStat teamStat) {
		this.teamStat = teamStat;
	}

	public List<Tournament> getTournaments() {
		return this.tournaments;
	}

	public void setTournaments(List<Tournament> tournaments) {
		this.tournaments = tournaments;
	}

	public List<UserTeam> getUserTeams() {
		return this.userTeams;
	}

	public void setUserTeams(List<UserTeam> userTeams) {
		this.userTeams = userTeams;
	}

}