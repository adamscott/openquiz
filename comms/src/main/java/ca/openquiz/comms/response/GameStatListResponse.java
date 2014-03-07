package ca.openquiz.comms.response;

import java.util.List;

import ca.openquiz.comms.model.GameStat;

public class GameStatListResponse {
	private List<GameStat> gameStat;

	public List<GameStat> getGameStat() {
		return gameStat;
	}

	public void setGameStat(List<GameStat> gameStat) {
		this.gameStat = gameStat;
	}
}
