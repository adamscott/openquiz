package ca.openquiz.comms.response;

import java.util.List;

import ca.openquiz.comms.model.GameLog;

public class GameLogListResponse {
	private List<GameLog> gameLog;

	public List<GameLog> getGameLog() {
		return gameLog;
	}

	public void setGameLog(List<GameLog> gameLog) {
		this.gameLog = gameLog;
	}
	
}
