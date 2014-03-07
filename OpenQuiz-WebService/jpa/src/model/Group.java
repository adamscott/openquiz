package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the group database table.
 * 
 */
@Entity
public class Group implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="group_id")
	private int groupId;

	private String name;

	//bi-directional many-to-one association to Question
	@OneToMany(mappedBy="group")
	private List<Question> questions1;

	//bi-directional many-to-many association to Question
	@ManyToMany(mappedBy="groups")
	private List<Question> questions2;

	//bi-directional many-to-one association to Team
	@OneToMany(mappedBy="group")
	private List<Team> teams;

	//bi-directional many-to-one association to Tournament
	@OneToMany(mappedBy="group")
	private List<Tournament> tournaments;

	//bi-directional many-to-one association to UserGroupRole
	@OneToMany(mappedBy="group")
	private List<UserGroupRole> userGroupRoles;

	public Group() {
	}

	public int getGroupId() {
		return this.groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Question> getQuestions1() {
		return this.questions1;
	}

	public void setQuestions1(List<Question> questions1) {
		this.questions1 = questions1;
	}

	public List<Question> getQuestions2() {
		return this.questions2;
	}

	public void setQuestions2(List<Question> questions2) {
		this.questions2 = questions2;
	}

	public List<Team> getTeams() {
		return this.teams;
	}

	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}

	public List<Tournament> getTournaments() {
		return this.tournaments;
	}

	public void setTournaments(List<Tournament> tournaments) {
		this.tournaments = tournaments;
	}

	public List<UserGroupRole> getUserGroupRoles() {
		return this.userGroupRoles;
	}

	public void setUserGroupRoles(List<UserGroupRole> userGroupRoles) {
		this.userGroupRoles = userGroupRoles;
	}

}