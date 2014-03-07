package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the cat_user_type database table.
 * 
 */
@Entity
@Table(name="cat_user_type")
public class CatUserType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="cat_user_type_id")
	private int catUserTypeId;

	@Column(name="desc_en")
	private String descEn;

	@Column(name="desc_fr")
	private String descFr;

	//bi-directional many-to-one association to User
	@OneToMany(mappedBy="catUserType")
	private List<User> users;

	public CatUserType() {
	}

	public int getCatUserTypeId() {
		return this.catUserTypeId;
	}

	public void setCatUserTypeId(int catUserTypeId) {
		this.catUserTypeId = catUserTypeId;
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

	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

}