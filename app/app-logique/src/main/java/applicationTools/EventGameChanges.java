package applicationTools;

import java.util.HashMap;

import enumPack.GameAction;

public class EventGameChanges {
	private GameAction gameAction;
	private String playerName;
	private String playerSide;
	private Integer score;
	
	public GameAction getGameAction() {
		return gameAction;
	}
	public void setGameAction(GameAction gameAction) {
		this.gameAction = gameAction;
	}
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	public String getPlayerSide() {
		return playerSide;
	}
	public void setPlayerSide(String playerSide) {
		this.playerSide = playerSide;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
}
