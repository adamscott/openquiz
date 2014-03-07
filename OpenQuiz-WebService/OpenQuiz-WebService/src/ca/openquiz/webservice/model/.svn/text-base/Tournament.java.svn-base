package ca.openquiz.webservice.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import ca.openquiz.webservice.converter.DateConverter;
import ca.openquiz.webservice.converter.KeyConverter;
import ca.openquiz.webservice.manager.DBManager;
import ca.openquiz.webservice.parameter.SearchGameParam;
import ca.openquiz.webservice.parameter.SearchGroupParam;
import ca.openquiz.webservice.parameter.SearchTeamParam;

import com.google.appengine.api.datastore.Key;

@XmlRootElement
@PersistenceCapable(detachable="true")
public class Tournament extends BaseModel {

	@Persistent
	private String name;

	@Persistent
	private Date startDate;

	@Persistent
	private Date endDate;

	@Persistent
	private String address;

	@Persistent
	private String city;

	@Persistent
	private String postalCode;

	@Persistent
	private String country;

	@Persistent
	@XmlJavaTypeAdapter(KeyConverter.class)
	private Key group;

	@Persistent
	@XmlJavaTypeAdapter(KeyConverter.class)
	private List<Key> games = new ArrayList<Key>();

	@Persistent
	@XmlJavaTypeAdapter(KeyConverter.class)
	private List<Key> teams = new ArrayList<Key>();

	public Tournament() {
	}

	@Override
	public void delete()
	{
		SearchTeamParam teamParam = new SearchTeamParam();
		teamParam.setTournament(this.getKey());
		List<Team> searchedTeams = DBManager.searchTeams(teamParam);

		for(Team t : searchedTeams)
		{
			t.removeTournament(this);
		}

		SearchGameParam gameParam = new SearchGameParam();
		gameParam.setTournament(key);
		List<Game> searchedGames = DBManager.searchGames(gameParam);

		for(Game g : searchedGames)
		{
			g.removeTournament();
		}

		SearchGroupParam groupParam = new SearchGroupParam();
		groupParam.setTournament(this.getKey());
		List<Group> searchedGroups = DBManager.searchGroups(groupParam);

		for(Group g : searchedGroups)
		{
			g.removeTournament(this);
		}

		//		DBManager.getGroup(group).getTournaments().remove(this.key);
		//		DBManager.save(group);

		DBManager.delete(this.getKey());
		key = null;
	}

	public void addTeam(Team team)
	{
		if (team.getKey() == null) {
			DBManager.save(team);
		}

		if (this.key == null) {
			DBManager.save(this);
		}

		this.teams.add(team.getKey());
		team.getTournaments().add(this.getKey());

		DBManager.save(team);
		DBManager.save(this);
	}

	public void removeTeam(Team team)
	{
		if (this.teams.contains(team.getKey())) 
		{
			this.teams.remove(team.getKey());
			team.getUsers().remove(this.getKey());

			DBManager.save(team);
			DBManager.save(this);
		}
	}

	public void addGame(Game game)
	{
		if (game.getKey() == null) {
			DBManager.save(game);
		}

		if (this.key == null) {
			DBManager.save(this);
		}

		this.games.add(game.getKey());
		game.addTournament(this);

		DBManager.save(game);
		DBManager.save(this);
	}

	public void removeGame(Game game)
	{
		if (this.games.contains(game.getKey())) 
		{
			this.games.remove(game.getKey());
			game.removeTournament();

			DBManager.save(game);
			DBManager.save(this);
		}
	}

	public void addGroup(Group group)
	{
		if (group.getKey() == null) {
			DBManager.save(group);
		}

		if (this.key == null) {
			DBManager.save(this);
		}

		this.group = group.getKey();
		group.addTournament(this);

		DBManager.save(group);
		DBManager.save(this);
	}

	public void removeGroup()
	{
		Group group = DBManager.getGroup(this.group);
		group.removeTournament(this);
		this.group = null;

		DBManager.save(group);
		DBManager.save(this);
	}

	//	Getters/Setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlJavaTypeAdapter(DateConverter.class)
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@XmlJavaTypeAdapter(DateConverter.class)
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@XmlTransient
	public Key getGroup() {
		return group;
	}

	public void setGroup(Key group) {
		this.group = group;
	}

	@XmlTransient
	public List<Key> getGames() {
		return games;
	}

	public void setGames(List<Key> games) {
		this.games = games;
	}

	@XmlTransient
	public List<Key> getTeams() {
		return teams;
	}

	public void setTeams(List<Key> teams) {
		this.teams = teams;
	}

	@Override
	public boolean isValid() {
		Date now = new Date();
		return name != null && !name.isEmpty() && startDate.after(now)
				&& address != null && address.isEmpty()
				&& city != null && city.isEmpty()
				&& postalCode != null && postalCode.isEmpty()
				&& country != null && country.isEmpty();
	}

}
