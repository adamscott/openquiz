package ca.openquiz.comms.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class QuestionSet extends BaseModel {
	private static final long serialVersionUID = -1916361266787766238L;

	private Date availableDate;

	private String name;
	
	private String authorKey;
	
	private List<QuestionSetSection> sectionList = new ArrayList<QuestionSetSection>();
	
	private List<String> groupKeys = new ArrayList<String>();
	
	private boolean deletedAfterGame;

	public QuestionSet() {
	}

	@JsonProperty("sections")
	public List<QuestionSetSection> getSectionList() {
		return sectionList;
	}

	@JsonProperty("groups")
	public List<String> getGroupKeys() {
		return groupKeys;
	}
	
	public void setGroupKeys(List<String> groups) {
		this.groupKeys = groups;
	}

	public Date getAvailableDate() {
		return availableDate;
	}

	public void setAvailableDate(Date availableDate) {
		this.availableDate = availableDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("author")
	public String getAuthorKey() {
		return this.authorKey;
	}

	public void setAuthorKey(String author) {
		this.authorKey = author;
	}

	public boolean isDeletedAfterGame() {
		return deletedAfterGame;
	}

	public void setDeletedAfterGame(boolean deletedAfterGame) {
		this.deletedAfterGame = deletedAfterGame;
	}

}
