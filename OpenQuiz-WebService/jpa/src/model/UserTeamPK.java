package model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the user_team database table.
 * 
 */
@Embeddable
public class UserTeamPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="user_id")
	private int userId;

	@Column(name="team_id")
	private int teamId;

	public UserTeamPK() {
	}
	public int getUserId() {
		return this.userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getTeamId() {
		return this.teamId;
	}
	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof UserTeamPK)) {
			return false;
		}
		UserTeamPK castOther = (UserTeamPK)other;
		return 
			(this.userId == castOther.userId)
			&& (this.teamId == castOther.teamId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.userId;
		hash = hash * prime + this.teamId;
		
		return hash;
	}
}