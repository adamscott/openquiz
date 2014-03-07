package currentGame;

import java.util.ArrayList;
import java.util.List;

import applicationTools.MediaMode;

public class CurrentGameModel{

	private String currentlySelectedPlayer;
	private String teamSelected;
	
	private Boolean isPlayerSelectionEnabled = true;
	private Boolean hasPlayerPanelBeenClicked = false;
	private Boolean isEndOfGameReached = false;
	private Boolean areStatsPosted = false;
	
	private Boolean isProjectorModeEnabled = false;
	
	private MediaMode mediaMode;

	public CurrentGameModel(){
		currentlySelectedPlayer = null;
		teamSelected = null;
		mediaMode = MediaMode.NONE;
	}
	
	public void setMediaMode(MediaMode mediaMode){
		this.mediaMode = mediaMode;
	}
	
	public MediaMode getMediaMode(){
		return this.mediaMode;
	}

	public String getCurrentlySelectedPlayer() {
		return currentlySelectedPlayer;
	}

	public void setCurrentlySelectedPlayer(String currentlySelectedPlayer) {
		this.currentlySelectedPlayer = currentlySelectedPlayer;
	}

	public String getTeamSelected() {
		return teamSelected;
	}

	public void setTeamSelected(String teamSelected) {
		this.teamSelected = teamSelected;
	}

	public Boolean getIsPlayerSelectionEnabled() {
		return isPlayerSelectionEnabled;
	}

	public void setIsPlayerSelectionEnabled(Boolean isPlayerSelectionEnabled) {
		this.isPlayerSelectionEnabled = isPlayerSelectionEnabled;
	}

	public Boolean getHasPlayerPanelBeenClicked() {
		return hasPlayerPanelBeenClicked;
	}

	public void setHasPlayerPanelBeenClicked(Boolean hasPlayerPanelBeenClicked) {
		this.hasPlayerPanelBeenClicked = hasPlayerPanelBeenClicked;
	}

	public Boolean getIsEndOfGameReached() {
		return isEndOfGameReached;
	}

	public void setIsEndOfGameReached(Boolean isEndOfGameReached) {
		this.isEndOfGameReached = isEndOfGameReached;
	}
	
	public void setAreStatsPosted(Boolean areStatsPosted){
		this.areStatsPosted = areStatsPosted;
	}
	
	public Boolean getAreStatsPosted(){
		return this.areStatsPosted;
	}

}
