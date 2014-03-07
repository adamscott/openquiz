package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the cat_degree database table.
 * 
 */
@Entity
@Table(name="cat_degree")
public class CatDegree implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="cat_degree_id")
	private int catDegreeId;

	@Column(name="desc_en")
	private String descEn;

	@Column(name="desc_fr")
	private String descFr;

	//bi-directional many-to-many association to Question
	@ManyToMany(mappedBy="catDegrees")
	private List<Question> questions;

	//bi-directional many-to-one association to Team
	@OneToMany(mappedBy="catDegree")
	private List<Team> teams;

	public CatDegree() {
	}

	public int getCatDegreeId() {
		return this.catDegreeId;
	}

	public void setCatDegreeId(int catDegreeId) {
		this.catDegreeId = catDegreeId;
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

	public List<Question> getQuestions() {
		return this.questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public List<Team> getTeams() {
		return this.teams;
	}

	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}

}