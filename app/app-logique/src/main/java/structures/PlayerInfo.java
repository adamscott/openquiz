package structures;

//Keep info about an answered question
public class PlayerInfo {
	
	public String playerName;
	public String playerTeamSide;
	public int idx;
	
	public PlayerInfo(String _playerName, String _playerTeamSide){
		this.playerName = _playerName;
		this.playerTeamSide = _playerTeamSide;
	}
	
	public PlayerInfo(String _playerName, String _playerTeamSide, int _i){
		this.playerName = _playerName;
		this.playerTeamSide = _playerTeamSide;
		this.idx = _i;
	}
}