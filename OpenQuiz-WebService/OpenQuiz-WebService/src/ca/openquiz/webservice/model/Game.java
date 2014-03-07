package ca.openquiz.webservice.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import ca.openquiz.webservice.converter.DateConverter;
import ca.openquiz.webservice.converter.KeyConverter;
import ca.openquiz.webservice.manager.DBManager;

import com.google.appengine.api.datastore.Key;

@XmlRootElement
@PersistenceCapable(detachable="true")
public class Game extends BaseModel {

	@Persistent
	private String name;

	@Persistent
	@XmlElement
	@XmlJavaTypeAdapter(DateConverter.class)
	private Date gameDate;

	@Persistent
	@XmlElement
	@XmlJavaTypeAdapter(KeyConverter.class)
	private Key team1;

	@Persistent
	@XmlElement
	@XmlJavaTypeAdapter(KeyConverter.class)
	private Key team2;
	
	@Persistent
	@XmlElement
	private int team1Score = 0;

	@Persistent
	@XmlElement
	private int team2Score = 0;

	@Persistent
	@XmlElement
	@XmlJavaTypeAdapter(KeyConverter.class)
	private List<Key> team1Players;

	@Persistent
	@XmlElement
	@XmlJavaTypeAdapter(KeyConverter.class)
	private List<Key> team2Players;

	@Persistent
	@XmlJavaTypeAdapter(KeyConverter.class)
	private Key tournament;

	@Persistent
	@XmlJavaTypeAdapter(KeyConverter.class)
	private Key questionSet;

	@Persistent
	private boolean active;
	
	@Persistent
	@XmlTransient
	private List<Key> teams = new ArrayList<Key>();
	
	@Persistent
	@XmlTransient
	private List<Key> players = new ArrayList<Key>();

	public Game() {
		active = true;
	}

	@Override
	public void delete() {

		active = false;
		DBManager.save(this);
	}

	public void addTournament(Tournament tournament)
	{
		if (tournament.getKey() == null) {
			DBManager.save(tournament);
		}

		if (this.key == null) {
			DBManager.save(this);
		}

		if(!tournament.getGames().contains(this.getKey()))
		{
			this.tournament = tournament.getKey();
			tournament.getGames().add(this.getKey());

			DBManager.save(tournament);
			DBManager.save(this);
		}
	}

	public void removeTournament()
	{		
		if(this.tournament != null)
		{
			Tournament t = DBManager.getTournament(tournament);
			t.getGames().remove(this.getKey());
			this.tournament = null;

			DBManager.save(t);
			DBManager.save(this);
		}
	}

	public void copy(Game game)
	{
		this.gameDate 		= game.getGameDate();
		this.active 		= game.isActive();
		this.name 			= game.getName();
		this.questionSet 	= game.getQuestionSet();
		this.tournament 	= game.getTournament();
		this.questionSet	= game.getQuestionSet();
		this.team1			= game.getTeam1();
		this.team2			= game.getTeam2();
		this.team1Players	= game.getTeam1Players();
		this.team2Players	= game.getTeam2Players();

	}

	//	Getters / Setters
	@XmlTransient
	public Date getGameDate() {
		return this.gameDate;
	}

	public void setGameDate(Date gameDate) {
		this.gameDate = gameDate;
	}

	@XmlTransient
	public Key getTeam1() {
		return this.team1;
	}

	@XmlTransient
	public Key getTeam2() {
		return this.team2;
	}
	
	@XmlTransient
	public int getTeam1Score() {
		return this.team1Score;
	}

	@XmlTransient
	public int getTeam2Score() {
		return this.team2Score;
	}

	@XmlTransient
	public List<Key> getTeam1Players() {
		return this.team1Players;
	}

	@XmlTransient
	public List<Key> getTeam2Players() {
		return this.team2Players;
	}

	public Key getTournament() {
		return this.tournament;
	}

	@XmlTransient
	public Key getQuestionSet() {
		return questionSet;
	}

	public void setQuestionSet(Key questionSet) {
		this.questionSet = questionSet;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean isActive) {
		this.active = isActive;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTeam1(Key team1) {
		this.team1 = team1;
	}

	public void setTeam2(Key team2) {
		this.team2 = team2;
	}

	public void setTeam1Score(int team1Score) {
		this.team1Score = team1Score;
	}

	public void setTeam2Score(int team2Score) {
		this.team2Score = team2Score;
	}

	public void setTeam1Players(List<Key> team1Players) {
		this.team1Players = team1Players;
	}

	public void setTeam2Players(List<Key> team2Players) {
		this.team2Players = team2Players;
	}

	@Override
	public boolean isValid() {
		teams.clear();
		teams.add(team1);
		teams.add(team2);
		
		players.clear();
		players.addAll(team1Players);
		players.addAll(team2Players);
		
		return name != null && !name.isEmpty() && this.gameDate != null
				&& this.team1 != null && this.team1Players.size() > 0
				&& this.team2 != null && this.team2Players.size() > 0;
	}


}
