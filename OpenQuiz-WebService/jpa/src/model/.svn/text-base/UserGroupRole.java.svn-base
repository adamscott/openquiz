package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the user_group_role database table.
 * 
 */
@Entity
@Table(name="user_group_role")
public class UserGroupRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private UserGroupRolePK id;

	//bi-directional many-to-one association to CatGroupRole
	@ManyToOne
	@JoinColumn(name="cat_group_role_id")
	private CatGroupRole catGroupRole;

	//bi-directional many-to-one association to Group
	@ManyToOne
	@JoinColumn(name="group_id")
	private Group group;

	public UserGroupRole() {
	}

	public UserGroupRolePK getId() {
		return this.id;
	}

	public void setId(UserGroupRolePK id) {
		this.id = id;
	}

	public CatGroupRole getCatGroupRole() {
		return this.catGroupRole;
	}

	public void setCatGroupRole(CatGroupRole catGroupRole) {
		this.catGroupRole = catGroupRole;
	}

	public Group getGroup() {
		return this.group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

}