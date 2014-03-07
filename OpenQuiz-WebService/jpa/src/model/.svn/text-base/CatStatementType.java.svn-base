package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the cat_statement_type database table.
 * 
 */
@Entity
@Table(name="cat_statement_type")
public class CatStatementType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="cat_statement_type_id")
	private int catStatementTypeId;

	@Column(name="desc_en")
	private String descEn;

	@Column(name="desc_fr")
	private String descFr;

	//bi-directional many-to-one association to Statement
	@OneToMany(mappedBy="catStatementType")
	private List<Statement> statements;

	public CatStatementType() {
	}

	public int getCatStatementTypeId() {
		return this.catStatementTypeId;
	}

	public void setCatStatementTypeId(int catStatementTypeId) {
		this.catStatementTypeId = catStatementTypeId;
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

	public List<Statement> getStatements() {
		return this.statements;
	}

	public void setStatements(List<Statement> statements) {
		this.statements = statements;
	}

}