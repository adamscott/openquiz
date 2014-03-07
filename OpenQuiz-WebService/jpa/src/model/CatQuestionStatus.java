package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the cat_question_status database table.
 * 
 */
@Entity
@Table(name="cat_question_status")
public class CatQuestionStatus implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="cat_question_status_id")
	private int catQuestionStatusId;

	@Column(name="desc_en")
	private String descEn;

	@Column(name="desc_fr")
	private String descFr;

	//bi-directional many-to-one association to Question
	@OneToMany(mappedBy="catQuestionStatus")
	private List<Question> questions;

	public CatQuestionStatus() {
	}

	public int getCatQuestionStatusId() {
		return this.catQuestionStatusId;
	}

	public void setCatQuestionStatusId(int catQuestionStatusId) {
		this.catQuestionStatusId = catQuestionStatusId;
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

}