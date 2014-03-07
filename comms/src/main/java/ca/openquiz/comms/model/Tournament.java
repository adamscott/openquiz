package ca.openquiz.comms.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class Tournament extends BaseModel {
	private static final long serialVersionUID = 4935195257231907159L;

	private String name;
	
	private Date startDate;
	
	private Date endDate;

	private String address;

	private String city;

	private String postalCode;

	private String country;
	
	private String groupKey;
	
	private List<String> gameKeys = new ArrayList<String>();

	private List<String> teamKeys = new ArrayList<String>();
		
	public Tournament() {
	}
	
//	Getters/Setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

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

	public String getGroupKey() {
		return groupKey;
	}

	@JsonProperty("group")
	public void setGroupKey(String group) {
		this.groupKey = group;
	}

	@JsonProperty("games")
	public List<String> getGameKeys() {
		return gameKeys;
	}

	public void setGameKeys(List<String> games) {
		this.gameKeys = games;
	}

	@JsonProperty("teams")
	public List<String> getTeamKeys() {
		return teamKeys;
	}

	public void setTeamKeys(List<String> teams) {
		this.teamKeys = teams;
	}
	
}
