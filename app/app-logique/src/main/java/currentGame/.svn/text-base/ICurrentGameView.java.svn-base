package currentGame;

import structures.PlayerInfo;
import ca.openquiz.comms.model.QuestionAnagram;
import ca.openquiz.comms.model.QuestionAssociation;
import ca.openquiz.comms.model.QuestionGeneral;
import ca.openquiz.comms.model.QuestionIdentification;
import ca.openquiz.comms.model.QuestionIntru;
import ca.openquiz.comms.model.QuestionMedia;

public interface ICurrentGameView {
	
	public void setModel(CurrentGameModel model);
	//public Object getPlayerPanelFromPosition(int TeamNumber, int PlayerNumber);
	public void triggerEndOfGame();
	public void unselectEveryPlayer();
	public void loadTeamInfo();
	public void swapPlayers();
	public void repaintPanelContent();
	public void updateLeftTeamScoreLabel();
	public void updateRightTeamScoreLabel();
	public void setSelectedPlayer(String playerName, String playerTeamSide);
	
	public void showIntruQuestion(QuestionIntru question);
	public void showAssociationQuestion(QuestionAssociation question);
	public void showAnagramQuestion(QuestionAnagram question);
	public void showMultipleClueQuestion(QuestionIdentification question);
	public void showGeneralQuestion(QuestionGeneral question);
	public void showMediaQuestion(QuestionMedia question);
	
	public void disableSkipButton();
	public void enableSkipButton();
	public boolean isSkipButtonEnabled();
	public void setRightAnswerColorToLeftPlayerNamePanel(String playerName);
	public void setRightAnswerColorToRightPlayerNamePanel(String playerName);
	
	public Object getPanelContent();
	
	public void showMediaContentInProjectorMode();
	public void nextAudioTrack();
	public void nextVideoTrack();
	public void previousAudioTrack();
	public void previousVideoTrack();
	public void playSound();
	public void playVideo();
	public void pauseSound();
	public void pauseVideo();
	public void stopSound();
	public void stopVideo();
	public void highlightSelectedPlayerPanel(String playerName, String playerTeam);
	public PlayerInfo getPlayerInfoFromPosition(int team, int player);
	public void nextImage();
	public void previousImage();
	public void enablePlayerIndividualPointEdition(String playerName, String playerTeam);
	public void disableAllPlayerIndividualPointEdition();
	public void resetUI();
	public void enablePrevButton();
	public void disablePrevButton();
	public void enableScoresButton();
	public void disableScoresButton();
	public void setPlayerIndividualScore(String playerSide, String playerName, int value);
	public void adjustSelectedPlayerScoreLabel(String playerSide, String playerName, int value);
	public void updateViewOnPageChanged();
	public void playBuzzerSound();
	public void resetEventProcessedCounter();
	public boolean isProjectorModeEnabled();
	public void initPanelAudioPlayer(int maxValue);
	public void updatePanelAudioPlayer(int newValue, int currentSoundTime);
	public void resetPanelAudioPlayer(int totalTrackTimeInMilliseconds);
	public void showImage();
	public void hideCurrentQuestion();
}
