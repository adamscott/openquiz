package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the tournament database table.
 * 
 */
@Entity
public class Tournament implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="tournament_id")
	private int tournamentId;

	@Temporal(TemporalType.DATE)
	@Column(name="end_date")
	private Date endDate;

	private String location;

	private String name;

	@Temporal(TemporalType.DATE)
	@Column(name="start_date")
	private Date startDate;

	//bi-directional many-to-one association to Game
	@OneToMany(mappedBy="tournament")
	private List<Game> games;

	//bi-directional many-to-one association to Group
	@ManyToOne
	@JoinColumn(name="hosting_group_id")
	private Group group;

	//bi-directional many-to-many association to Team
	@ManyToMany
	@JoinTable(
		name="tournament_participant"
		, joinColumns={
			@JoinColumn(name="tournament_id")
			}
		, inverseJoinColumns={
			@JoinColumn(name="team_id")
			}
		)
	private List<Team> teams;

	public Tournament() {
	}

	public int getTournamentId() {
		return this.tournamentId;
	}

	public void setTournamentId(int tournamentId) {
		this.tournamentId = tournamentId;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public List<Game> getGames() {
		return this.games;
	}

	public void setGames(List<Game> games) {
		this.games = games;
	}

	public Group getGroup() {
		return this.group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public List<Team> getTeams() {
		return this.teams;
	}

	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}

}