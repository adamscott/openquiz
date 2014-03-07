package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the cat_role database table.
 * 
 */
@Entity
@Table(name="cat_role")
public class CatRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="cat_role_id")
	private int catRoleId;

	@Column(name="desc_en")
	private String descEn;

	@Column(name="desc_fr")
	private String descFr;

	//bi-directional many-to-one association to UserRole
	@OneToMany(mappedBy="catRole")
	private List<UserRole> userRoles;

	public CatRole() {
	}

	public int getCatRoleId() {
		return this.catRoleId;
	}

	public void setCatRoleId(int catRoleId) {
		this.catRoleId = catRoleId;
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

	public List<UserRole> getUserRoles() {
		return this.userRoles;
	}

	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

}