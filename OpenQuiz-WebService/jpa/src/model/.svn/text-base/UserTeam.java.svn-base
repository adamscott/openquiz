package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the user_team database table.
 * 
 */
@Entity
@Table(name="user_team")
public class UserTeam implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private UserTeamPK id;

	//bi-directional many-to-one association to Team
	@ManyToOne
	@JoinColumn(name="team_id")
	private Team team;

	public UserTeam() {
	}

	public UserTeamPK getId() {
		return this.id;
	}

	public void setId(UserTeamPK id) {
		this.id = id;
	}

	public Team getTeam() {
		return this.team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

}