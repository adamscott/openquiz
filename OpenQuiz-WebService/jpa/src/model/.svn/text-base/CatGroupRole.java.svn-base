package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the cat_group_role database table.
 * 
 */
@Entity
@Table(name="cat_group_role")
public class CatGroupRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="cat_group_role_id")
	private int catGroupRoleId;

	@Column(name="desc_en")
	private String descEn;

	@Column(name="desc_fr")
	private String descFr;

	//bi-directional many-to-one association to UserGroupRole
	@OneToMany(mappedBy="catGroupRole")
	private List<UserGroupRole> userGroupRoles;

	public CatGroupRole() {
	}

	public int getCatGroupRoleId() {
		return this.catGroupRoleId;
	}

	public void setCatGroupRoleId(int catGroupRoleId) {
		this.catGroupRoleId = catGroupRoleId;
	}

	public String getDescEn() {
		return this.descEn;
	}

	public void setDescEn(String descEn) {
		this.descEn = descEn;
	}

	public String getDescFr() {
		return this.descFr;
	}

	public void setDescFr(String descFr) {
		this.descFr = descFr;
	}

	public List<UserGroupRole> getUserGroupRoles() {
		return this.userGroupRoles;
	}

	public void setUserGroupRoles(List<UserGroupRole> userGroupRoles) {
		this.userGroupRoles = userGroupRoles;
	}

}