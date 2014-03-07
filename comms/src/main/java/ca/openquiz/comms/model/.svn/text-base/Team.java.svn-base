package ca.openquiz.comms.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * The persistent class for the team database table.
 * 
 */

public class Team extends BaseModel implements Serializable{
	private static final long serialVersionUID = 6513314864721288822L;

	private String name;

	@JsonProperty("users")
	private List<String> userKeys;
	
	@JsonProperty("tournament")
	private List<String> tournamentKeys;
	
	@JsonProperty("games")
	private List<String> gameKeys;

	@JsonProperty("group")
	private String groupKey;
	
	private java.lang.Boolean active = true;

	public Team() {
		userKeys 		= new ArrayList<String>();
		tournamentKeys = new ArrayList<String>();
		gameKeys 		= new ArrayList<String>();
		active		= true;
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public List<String> getUserKeys() {
		return userKeys;
	}

	public List<String> getTournamentKeys() {
		return tournamentKeys;
	}

	public List<String> getGameKeys() {
		return gameKeys;
	}

	public String getGroupKey()
	{
		return groupKey;
	}

	public java.lang.Boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setGroupKey(String group)
	{
		this.groupKey = group;
	}
	@Override
	public String toString() {
		return name;
	}
}