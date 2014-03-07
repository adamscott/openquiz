package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the cat_language database table.
 * 
 */
@Entity
@Table(name="cat_language")
public class CatLanguage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="cat_language_id")
	private int catLanguageId;

	@Column(name="desc_en")
	private String descEn;

	@Column(name="desc_fr")
	private String descFr;

	//bi-directional many-to-one association to Question
	@OneToMany(mappedBy="catLanguage")
	private List<Question> questions;

	public CatLanguage() {
	}

	public int getCatLanguageId() {
		return this.catLanguageId;
	}

	public void setCatLanguageId(int catLanguageId) {
		this.catLanguageId = catLanguageId;
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