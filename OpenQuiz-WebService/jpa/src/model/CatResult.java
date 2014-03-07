package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the cat_result database table.
 * 
 */
@Entity
@Table(name="cat_result")
public class CatResult implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="cat_result_id")
	private int catResultId;

	@Column(name="desc_en")
	private String descEn;

	@Column(name="desc_fr")
	private String descFr;

	//bi-directional many-to-one association to CurrentGameLog
	@OneToMany(mappedBy="catResult")
	private List<CurrentGameLog> currentGameLogs;

	//bi-directional many-to-one association to MasterGameLog
	@OneToMany(mappedBy="catResult")
	private List<MasterGameLog> masterGameLogs;

	public CatResult() {
	}

	public int getCatResultId() {
		return this.catResultId;
	}

	public void setCatResultId(int catResultId) {
		this.catResultId = catResultId;
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

	public List<CurrentGameLog> getCurrentGameLogs() {
		return this.currentGameLogs;
	}

	public void setCurrentGameLogs(List<CurrentGameLog> currentGameLogs) {
		this.currentGameLogs = currentGameLogs;
	}

	public List<MasterGameLog> getMasterGameLogs() {
		return this.masterGameLogs;
	}

	public void setMasterGameLogs(List<MasterGameLog> masterGameLogs) {
		this.masterGameLogs = masterGameLogs;
	}

}