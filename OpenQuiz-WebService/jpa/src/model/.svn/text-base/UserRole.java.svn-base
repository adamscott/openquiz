package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the user_role database table.
 * 
 */
@Entity
@Table(name="user_role")
public class UserRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private UserRolePK id;

	//bi-directional many-to-one association to CatRole
	@ManyToOne
	@JoinColumn(name="cat_role_id")
	private CatRole catRole;

	public UserRole() {
	}

	public UserRolePK getId() {
		return this.id;
	}

	public void setId(UserRolePK id) {
		this.id = id;
	}

	public CatRole getCatRole() {
		return this.catRole;
	}

	public void setCatRole(CatRole catRole) {
		this.catRole = catRole;
	}

}