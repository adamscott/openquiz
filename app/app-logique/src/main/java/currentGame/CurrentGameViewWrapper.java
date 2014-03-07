package currentGame;

import java.util.ArrayList;
import java.util.List;

import ca.openquiz.comms.model.QuestionAnagram;
import ca.openquiz.comms.model.QuestionAssociation;
import ca.openquiz.comms.model.QuestionGeneral;
import ca.openquiz.comms.model.QuestionIdentification;
import ca.openquiz.comms.model.QuestionIntru;
import ca.openquiz.comms.model.QuestionMedia;

public class CurrentGameViewWrapper {
	private List<ICurrentGameView> views;
	
	public CurrentGameViewWrapper(){
		views = new ArrayList<ICurrentGameView>();
	}
	
	public void registerView(ICurrentGameView view){
		this.views.add(view);
	}
	
	public List<ICurrentGameView> getViews(){
		return this.views;
	}

	public void repaintPanelContent() {
		for (ICurrentGameView view : views){
			view.repaintPanelContent();
		}
	}

	public void unselectEveryPlayer() {
		for (ICurrentGameView view : views){
			view.unselectEveryPlayer();
		}
	}

	public void loadTeamInfo() {
		for (ICurrentGameView view : views){
			view.loadTeamInfo();
		}
	}

	public void highlightSelectedPlayerPanel(String playerName,String playerTeam) {
		for (ICurrentGameView view : views){
			view.highlightSelectedPlayerPanel(playerName, playerTeam);
		}
	}

	public void triggerEndOfGame() {
		for (ICurrentGameView view : views){
			view.triggerEndOfGame();
		}
	}

	public void disableSkipButton() {
		for (ICurrentGameView view : views){
			view.disableSkipButton();
		}
	}

	public void showGeneralQuestion(QuestionGeneral question) {
		for (ICurrentGameView view : views){
			view.showGeneralQuestion(question);
		}
	}

	public void showIntruQuestion(QuestionIntru question) {
		for (ICurrentGameView view : views){
			view.showIntruQuestion(question);
		}
	}

	public void showAssociationQuestion(QuestionAssociation question) {
		for (ICurrentGameView view : views){
			view.showAssociationQuestion(question);
		}
	}

	public void showAnagramQuestion(QuestionAnagram question) {
		for (ICurrentGameView view : views){
			view.showAnagramQuestion(question);
		}
	}

	public void showMultipleClueQuestion(QuestionIdentification question) {
		for (ICurrentGameView view : views){
			view.showMultipleClueQuestion(question);
		}
	}
	
	public void showMediaQuestion(QuestionMedia question) {
		for (ICurrentGameView view : views){
			view.showMediaQuestion(question);
		}
	}

	public void setSelectedPlayer(String playerName, String playerTeamSide) {
		for (ICurrentGameView view : views){
			view.setSelectedPlayer(playerName, playerTeamSide);
		}
	}

	public void nextImage() {
		if(views.size() == 2){
			views.get(1).nextImage();
			views.get(0).nextImage();
		}
		else{
			for (ICurrentGameView view : views){
				view.nextImage();
			}
		}
	}

	public void nextVideoTrack() {
		if(views.size() == 2){
			views.get(1).nextVideoTrack();
			views.get(0).nextVideoTrack();
		}
		else{
			for (ICurrentGameView view : views){
				view.nextVideoTrack();
			}
		}
	}

	public void previousImage() {
		if(views.size() == 2){
			views.get(1).previousImage();
			views.get(0).previousImage();
		}
		else{
			for (ICurrentGameView view : views){
				view.previousImage();
			}
		}
	}

	public void previousVideoTrack() {
		if(views.size() == 2){
			views.get(1).previousVideoTrack();
			views.get(0).previousVideoTrack();
		}
		else{
			for (ICurrentGameView view : views){
				view.previousVideoTrack();
			}
		}
	}

	public void previousAudioTrack() {
		for (ICurrentGameView view : views){
			view.previousAudioTrack();
		}
	}

	public void stopSound() {
		for (ICurrentGameView view : views){
			if (view.isProjectorModeEnabled()){
				view.stopSound();
			}
		}
	}

	public void stopVideo() {
		for (ICurrentGameView view : views){
			view.stopVideo();
		}
	}

	public void pauseVideo() {
		for (ICurrentGameView view : views){
			view.pauseVideo();
		}
	}

	public void pauseSound() {
		for (ICurrentGameView view : views){
			if (view.isProjectorModeEnabled()){
				view.pauseSound();
			}
		}
	}

	public void playVideo() {
		for (ICurrentGameView view : views){
			view.playVideo();
		}
	}

	public void playSound() {
		for (ICurrentGameView view : views){
			if (view.isProjectorModeEnabled()){
				view.playSound();
			}
		}
	}
	
	public void updateViewOnPageChanged(){
		for (ICurrentGameView view : views){
			view.updateViewOnPageChanged();
		}
	}

	public void playBuzzerSound() {
		for (ICurrentGameView view : views){
			if (!view.isProjectorModeEnabled()){
				view.playBuzzerSound();
			}
		}
	}

	public void changeQuestionHookFunction() {
		for (ICurrentGameView view : views){
			view.stopSound();
			view.stopVideo();
		}
	}

	public void initPanelAudioPlayer(int maxValue) {
		for (ICurrentGameView view : views){
			view.initPanelAudioPlayer(maxValue);
		}
	}

	public void updatePanelAudioPlayer(int newValue, int currentSoundTime) {
		for (ICurrentGameView view : views){
			view.updatePanelAudioPlayer(newValue, currentSoundTime);
		}
	}

	public void resetPanelAudioPlayer(int totalTrackTimeInMilliseconds) {
		for (ICurrentGameView view : views){
			view.resetPanelAudioPlayer(totalTrackTimeInMilliseconds);
		}
	}

	public void showImage() {
		for (ICurrentGameView view : views){
			view.showImage();
		}
	}

}
