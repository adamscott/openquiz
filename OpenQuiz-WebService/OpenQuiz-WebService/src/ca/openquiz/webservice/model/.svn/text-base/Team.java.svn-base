package ca.openquiz.webservice.model;

import java.util.ArrayList;
import java.util.List;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import ca.openquiz.webservice.converter.KeyConverter;
import ca.openquiz.webservice.manager.DBManager;
import com.google.appengine.api.datastore.Key;

/**
 * The persistent class for the team database table.
 * 
 */

@XmlRootElement
@PersistenceCapable(detachable="true")
public class Team extends BaseModel {

	@Persistent
	private String name;

	@Persistent
	@XmlJavaTypeAdapter(KeyConverter.class)
	private List<Key> users;

	@Persistent
	@XmlJavaTypeAdapter(KeyConverter.class)
	private List<Key> tournaments;

	@Persistent
	@XmlJavaTypeAdapter(KeyConverter.class)
	private List<Key> games;

	@Persistent
	@XmlJavaTypeAdapter(KeyConverter.class)
	private Key group;

	@Persistent
	@XmlElement
	private java.lang.Boolean active = true;

	public Team() {
		users 		= new ArrayList<Key>();
		tournaments = new ArrayList<Key>();
		games 		= new ArrayList<Key>();
		active		= true;
	}


	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlTransient
	public List<Key> getUsers() {
		return users;
	}

	@XmlTransient
	public List<Key> getTournaments() {
		return tournaments;
	}

	@XmlTransient
	public List<Key> getGames() {
		return games;
	}

	@XmlTransient
	public Key getGroup()
	{
		return group;
	}

	public java.lang.Boolean isActive() {
		return active;
	}


	public void setActive(boolean active) {
		this.active = active;
	}


	public void setGroup(Key groupKey)
	{
		if(groupKey != null && groupKey.isComplete()){
			try{
				Group g = DBManager.getGroup(groupKey);
				this.setGroup(g);
			}
			catch(Exception e){

			}
		}
	}

	public void setGroup(Group group)
	{
		if (group.getKey() == null)
			DBManager.save(group);

		if(this.group != null && !this.group.equals(group.getKey())){
			DBManager.getGroup(this.group).removeTeam(this);
		}
		
		this.group = group.getKey();
		DBManager.save(this);
		
		group.addTeam(this);
	}

	public void addUser(User user) {
		if (user.getKey() == null) {
			DBManager.save(user);
		}

		if (this.key == null) {
			DBManager.save(this);
		}

		this.users.add(user.getKey());
		user.getTeams().add(this.getKey());

		DBManager.save(user);
		DBManager.save(this);
	}

	public void removeUser(User user)
	{
		if (this.users.contains(user.getKey()))
		{
			this.users.remove(user.getKey());
			user.getTeams().remove(this.getKey());

			DBManager.save(user);
			DBManager.save(this);
		}
	}

	public void addTournament(Tournament tournament) {
		if (tournament.getKey() == null) {
			DBManager.save(tournament);
		}

		if (this.key == null) {
			DBManager.save(this);
		}

		this.tournaments.add(tournament.getKey());
		tournament.getTeams().add(this.getKey());

		DBManager.save(tournament);
		DBManager.save(this);
	}

	public void removeTournament(Tournament tournament) {
		if (this.tournaments.contains(tournament.getKey())) 
		{
			this.tournaments.remove(tournament.getKey());
			tournament.getTeams().remove(this.getKey());

			DBManager.save(tournament);
			DBManager.save(this);
		}
	}
	
	public void addGame(Game game) {
		if (game.getKey() == null) {
			DBManager.save(game);
		}

		if (this.key == null) {
			DBManager.save(this);
		}

		this.games.add(game.getKey());
		DBManager.save(this);
	}
	
	public void removeGame(Game game) {
		if (this.games.contains(game.getKey())) 
		{
			this.games.remove(game.getKey());
			DBManager.save(this);
		}
	}

	@Override
	public void delete() {

		active = false;
		if(this.getGroup() != null){
			Group g = DBManager.getGroup(this.getGroup());
			g.removeTeam(this);
		}
		DBManager.save(this);
	}


	@Override
	public boolean isValid() {
		boolean usersMatchGroup = true;

		for(Key uKey : this.getUsers()){
			User u = DBManager.getUser(uKey);
			if(!u.getGroups().contains(this.getGroup())){
				usersMatchGroup = false;
			}
		}

		return usersMatchGroup && name != null && !name.isEmpty() && group != null && group.isComplete();
	}

}