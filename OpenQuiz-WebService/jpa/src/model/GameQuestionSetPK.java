package model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the game_question_set database table.
 * 
 */
@Embeddable
public class GameQuestionSetPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="game_id")
	private int gameId;

	@Column(name="question_set_id")
	private int questionSetId;

	public GameQuestionSetPK() {
	}
	public int getGameId() {
		return this.gameId;
	}
	public void setGameId(int gameId) {
		this.gameId = gameId;
	}
	public int getQuestionSetId() {
		return this.questionSetId;
	}
	public void setQuestionSetId(int questionSetId) {
		this.questionSetId = questionSetId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof GameQuestionSetPK)) {
			return false;
		}
		GameQuestionSetPK castOther = (GameQuestionSetPK)other;
		return 
			(this.gameId == castOther.gameId)
			&& (this.questionSetId == castOther.questionSetId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.gameId;
		hash = hash * prime + this.questionSetId;
		
		return hash;
	}
}