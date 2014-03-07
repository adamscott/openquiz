package ca.openquiz.comms.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class Group extends BaseModel implements Serializable{
	private static final long serialVersionUID = -3734247841523391596L;

	private String name;

	private List<String> questionKeys = new ArrayList<String>();

	private List<String> teamKeys = new ArrayList<String>();

	private List<String> tournamentKeys = new ArrayList<String>();

	public Group() {
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("questions")
	public List<String> getQuestionKeys() {
		return questionKeys;
	}

	@JsonProperty("teams")
	public List<String> getTeamKeys() {
		return teamKeys;
	}

	@JsonProperty("tournaments")
	public List<String> getTournamentKeys() {
		return tournamentKeys;
	}
	
	@Override
	public String toString() {
		return name;
	}
}