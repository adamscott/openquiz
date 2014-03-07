package model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the user_group_role database table.
 * 
 */
@Embeddable
public class UserGroupRolePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="user_id")
	private int userId;

	@Column(name="group_id")
	private int groupId;

	public UserGroupRolePK() {
	}
	public int getUserId() {
		return this.userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getGroupId() {
		return this.groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof UserGroupRolePK)) {
			return false;
		}
		UserGroupRolePK castOther = (UserGroupRolePK)other;
		return 
			(this.userId == castOther.userId)
			&& (this.groupId == castOther.groupId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.userId;
		hash = hash * prime + this.groupId;
		
		return hash;
	}
}