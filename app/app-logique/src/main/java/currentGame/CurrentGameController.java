package currentGame;

import reportQuestion.ReportQuestionController;
import serialization.GameDataImportExport;
import serialization.SerializationManager;
import structures.PlayerInfo;
import structures.QuestionTest;
import Console.bluetooth.event.ConsoleBaseEvent;
import Console.bluetooth.event.ConsoleEventListener;
import Console.bluetooth.event.ConsoleEventPressed;
import Console.bluetooth.event.ConsoleEventSerializedMessage;
import applicationTools.Defines;
import applicationTools.EventGameChanges;
import applicationTools.FileManager;
import applicationTools.GetInstanceBluetoothManager;
import applicationTools.QuestionManager;
import applicationTools.ScoreManager;
import applicationTools.TeamManager;
import ca.openquiz.comms.RequestsWebService;
import ca.openquiz.comms.enums.QuestionTarget;
import ca.openquiz.comms.enums.TeamPosition;
import ca.openquiz.comms.model.Game;
import ca.openquiz.comms.model.GameLog;
import ca.openquiz.comms.model.Question;
import ca.openquiz.comms.model.QuestionAnagram;
import ca.openquiz.comms.model.QuestionAssociation;
import ca.openquiz.comms.model.QuestionGeneral;
import ca.openquiz.comms.model.QuestionIdentification;
import ca.openquiz.comms.model.QuestionIntru;
import ca.openquiz.comms.model.QuestionMedia;
import ca.openquiz.comms.response.BaseResponse;
import enumPack.GameAction;

public class CurrentGameController {
	protected CurrentGameModel model;
	protected ICurrentGameView view;
	protected CurrentGameViewWrapper views;
	private boolean isProcessEvent = false;
	
	private ReportQuestionController reportQuestionController;
	
	public CurrentGameController() {
		model = new CurrentGameModel();
		
		if(GetInstanceBluetoothManager.getInstance().isConnectionToBluetoothEnabled()){
			GetInstanceBluetoothManager.getInstance().registerActionListener(consoleEventListenerCaptureConsoleInput);
		}
	}
	
	public void registerMainView(ICurrentGameView view){
		this.view = view;
	}
	
	public void registerViews(CurrentGameViewWrapper views){
		this.views = views;
	}
	
	private ConsoleEventListener consoleEventListenerCaptureConsoleInput = new ConsoleEventListener(){
		@Override
		public void handleConsoleEvent(ConsoleBaseEvent e) {
			try{
				if (isProcessEvent){
					if(e.Type == ConsoleBaseEvent.EVENT_PRESSED ){
						ConsoleEventPressed event = (ConsoleEventPressed)e;
						processEventPressedEvent(event);
					}
					else if(e.Type == ConsoleBaseEvent.EVENT_SERIALIZED_MESSAGE) {
	
						ConsoleEventSerializedMessage event = (ConsoleEventSerializedMessage)e;
						EventGameChanges eventGame = deserializeEvent(event.getMessage());
						if (event.getObjectType() == 1){processEventGameEvent(eventGame);}
					}
					view.resetEventProcessedCounter();
				}
			} catch(Exception ex){
				//TODO JS Check this and handle it better maybe?
				System.out.println("Cannot parse event received");
				ex.printStackTrace();
			}
			
		}
	};
	
	private void processEventPressedEvent(ConsoleEventPressed event){
		if(model.getIsPlayerSelectionEnabled()){
			PlayerInfo player = view.getPlayerInfoFromPosition(event.Team, event.Player);
			highlightSelectedPlayerPanel(player.playerName, player.playerTeamSide);
			
			model.setCurrentlySelectedPlayer(player.playerName);
			model.setTeamSelected(player.playerTeamSide);
			views.pauseVideo();
			views.pauseSound();
			view.hideCurrentQuestion();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			views.playBuzzerSound();
		}
	}
	
	private void processEventGameEvent(EventGameChanges event){
		switch(event.getGameAction()){
		case GOOD:
			goodAnswerEvent(event);
			break;
		case WRONG:
			wrongAnswerEvent(event);
			break;
		case MINUS:
			substractPointsEvent(event);
			break;
		case NEXT:
			skipQuestionEvent(event);
			break;
		case PREV:
			previousQuestionEvent(event);
			break;
		case ADJUST_PLAYER_SCORE:
			adjustPlayerScoreForIndividualQuestionEvent(event);
			break;
		case ADJUST_TEAM_SCORE:
			adjustTeamScoreEvent(event);
			break;
		case SELECT_PLAYER:
			highlightSelectedPlayerPanelEvent(event);
			break;
		case PLAY_MEDIA:
			playMediaEvent(event);
			break;
		case PAUSE_MEDIA:
			pauseMediaEvent(event);
			break;
		case STOP_MEDIA:
			stopMediaEvent(event);
			break;
		default:
			break;
		}
	}
	
	public void adjustPlayerScoreForIndividualQuestionEvent(EventGameChanges event){
		String selectedPlayer = event.getPlayerName();
		String selectedTeam = event.getPlayerSide();
		Integer score = event.getScore();
		System.out.println("[DEBUG-PC] - " + selectedPlayer + " " + selectedTeam + " " + score);
		if(selectedPlayer != null && selectedTeam != null && score != null){
			adjustPlayerScoreForIndividualQuestion(selectedPlayer,selectedTeam,score);
		}
		else{
			System.out.println("[DEBUG] - Cannot parse event");
		}
	}
	
	public void adjustPlayerScoreForIndividualQuestionApp(int multiplicator){
		Integer questionValue = QuestionManager.getInstance().getCurrentQuestionValue();
		//Integer choiceNumber = QuestionManager.getInstance().getCurrentQuestionNbOfChoices();
		Integer individualValue = questionValue;
		/*if (choiceNumber != 0){
			individualValue = questionValue / choiceNumber;
		}*/
		individualValue *= multiplicator;
		
		String selectedPlayer = model.getCurrentlySelectedPlayer();
		String selectedTeam = model.getTeamSelected();
		System.out.println("[DEBUG-PC] - " + selectedPlayer + " " + selectedTeam);
		if (selectedPlayer != null && selectedTeam != null){
			adjustPlayerScoreForIndividualQuestion(selectedPlayer, selectedTeam, individualValue);
			
			//Send the event
			EventGameChanges event = new EventGameChanges();
			event.setGameAction(GameAction.ADJUST_PLAYER_SCORE);
			event.setPlayerName(selectedPlayer);
			event.setPlayerSide(selectedTeam);
			event.setScore(individualValue);
			
			serialiazeEventAndSend(event);
		}
		else{
			System.out.println("[DEBUG] - Cannot generate event");
		}
	}

	private void adjustPlayerScoreForIndividualQuestion(String selectedPlayer, String selectedSide, int individualScore) {
		ScoreManager.getInstance().adjustPlayerScoreForIndividualQuestion(QuestionManager.getInstance().getCurrentQuestion(), selectedPlayer, selectedSide, individualScore);
		view.updateLeftTeamScoreLabel();
		view.updateRightTeamScoreLabel();
		view.adjustSelectedPlayerScoreLabel(selectedSide, selectedPlayer, individualScore);
	}
	
	public void adjustTeamScoreEvent(EventGameChanges event){
		String selectedTeam = event.getPlayerSide();
		Integer score = event.getScore();
		if(selectedTeam != null && score != null){
			adjustTeamScore(selectedTeam,score);
		}
		else{
			System.out.println("[DEBUG] - Cannot parse event");
		}
	}
	
	public void adjustTeamScoreApp(String side, int multiplicator){
		Integer questionValue = QuestionManager.getInstance().getCurrentQuestionValue();
		//Integer choiceNumber = QuestionManager.getInstance().getCurrentQuestionNbOfChoices();
		Integer individualValue = questionValue;
		/*if (choiceNumber != 0){
			individualValue = questionValue / choiceNumber;
		}*/
		individualValue *= multiplicator;
		
		adjustTeamScore(side, individualValue);
		
		//Send the event
		EventGameChanges event = new EventGameChanges();
		event.setGameAction(GameAction.ADJUST_TEAM_SCORE);
		event.setPlayerSide(side);
		event.setScore(individualValue);
		
		serialiazeEventAndSend(event);
		
		Boolean answer = true;
		if(individualValue < 0){
			answer = false;
		}
		
		if (side.equals("Left")){
			sendGameLog(QuestionManager.getInstance().getCurrentGameKey(), null, 
						QuestionManager.getInstance().getCurrentQuestion().getKey(), 10.0, 
						answer, individualValue, TeamManager.getInstance().getLeftTeam().getKey(), 
						TeamPosition.Team1);
		}
		else if (side.equals("Right")){
			sendGameLog(QuestionManager.getInstance().getCurrentGameKey(), null, 
						QuestionManager.getInstance().getCurrentQuestion().getKey(), 10.0, 
						answer, individualValue, TeamManager.getInstance().getRightTeam().getKey(), 
						TeamPosition.Team2);
		}
	}
	
	private void adjustTeamScore(String side, int score) {
		if(side.equals("Left")){
			ScoreManager.getInstance().adjustLeftTeamScore(score);
		}
		else if(side.equals("Right")){
			ScoreManager.getInstance().adjustRightTeamScore(score);
		}
		view.updateLeftTeamScoreLabel();
		view.updateRightTeamScoreLabel();
	}
	
	public void goodAnswerEvent(EventGameChanges event){
		model.setIsPlayerSelectionEnabled(true);
		String selectedPlayer = event.getPlayerName();
		String selectedTeam = event.getPlayerSide();
		if(selectedPlayer != null && selectedTeam != null){
			goodAnswer(selectedPlayer,selectedTeam);
		}
		else{
			System.out.println("[DEBUG] - Cannot parse event");
		}
	}
	
	public void goodAnswerApp(){
		model.setIsPlayerSelectionEnabled(true);
		String selectedPlayer = model.getCurrentlySelectedPlayer();
		String selectedTeam = model.getTeamSelected();
		if(selectedPlayer != null && selectedTeam != null){
			goodAnswer(selectedPlayer,selectedTeam);
			
			//Send the event
			EventGameChanges event = new EventGameChanges();
			event.setGameAction(GameAction.GOOD);
			event.setPlayerName(selectedPlayer);
			event.setPlayerSide(selectedTeam);
			
			serialiazeEventAndSend(event);
		}
		else{
			System.out.println("[DEBUG] - Cannot generate event");
		}
	}
	
	private void goodAnswer(String selectedPlayer, String selectedTeam){
		if (QuestionManager.getInstance().getCurrentQuestionTarget() == QuestionTarget.Collectif){
			Question currentQuestion = QuestionManager.getInstance().getCurrentQuestion();
			ScoreManager.getInstance().addQuestionToPlayerAnswerList(
					currentQuestion, selectedPlayer, selectedTeam, 
					QuestionManager.getInstance().getQuestionValue(currentQuestion));
			//Update scores on each side
			view.disableScoresButton();
			view.updateLeftTeamScoreLabel();
			view.updateRightTeamScoreLabel();
		}
		goToNextQuestion();
	}
	
	public void wrongAnswerEvent(EventGameChanges event){
		String selectedPlayer = event.getPlayerName();
		String selectedTeam = event.getPlayerSide();
		if(selectedPlayer != null && selectedTeam != null){
			wrongAnswer(selectedPlayer,selectedTeam);
		}
		else{
			System.out.println("[DEBUG] - Cannot parse event");
		}
	}
	
	public void wrongAnswerApp(){
		String selectedPlayer = model.getCurrentlySelectedPlayer();
		String selectedTeam = model.getTeamSelected();
		if(selectedPlayer != null && selectedTeam != null){
			wrongAnswer(selectedPlayer,selectedTeam);
			
			//Send the event
			EventGameChanges event = new EventGameChanges();
			event.setGameAction(GameAction.WRONG);
			event.setPlayerName(selectedPlayer);
			event.setPlayerSide(selectedTeam);
			
			serialiazeEventAndSend(event);
		}
		else{
			System.out.println("[DEBUG] - Cannot generate event");
		}
	}
	
	private void wrongAnswer(String selectedPlayer, String selectedTeam){
		Question currentQuestion = QuestionManager.getInstance().getCurrentQuestion();
		ScoreManager.getInstance().addWrongAnswerQuestionToPlayerList(currentQuestion, selectedPlayer, selectedTeam, 0);

		view.disableScoresButton();
		view.updateLeftTeamScoreLabel();
		view.updateRightTeamScoreLabel();
		views.unselectEveryPlayer();
		views.repaintPanelContent();
	}
	
	public void substractPointsEvent(EventGameChanges event){
		String selectedPlayer = event.getPlayerName();
		String selectedTeam = event.getPlayerSide();
		if(selectedPlayer != null && selectedTeam != null){
			substractPoints(selectedPlayer,selectedTeam);
		}
		else{
			System.out.println("[DEBUG] - Cannot parse event");
		}
	}
	
	public void substractPointsApp(){
		String selectedPlayer = model.getCurrentlySelectedPlayer();
		String selectedTeam = model.getTeamSelected();
		if(selectedPlayer != null && selectedTeam != null){
			substractPoints(selectedPlayer,selectedTeam);
			
			//Send the event
			EventGameChanges event = new EventGameChanges();
			event.setGameAction(GameAction.MINUS);
			event.setPlayerName(selectedPlayer);
			event.setPlayerSide(selectedTeam);
			
			serialiazeEventAndSend(event);
		}
		else{
			System.out.println("[DEBUG] - Cannot generate event");
		}
	}
	
	private void substractPoints(String selectedPlayer, String selectedTeam){
		Question currentQuestion = QuestionManager.getInstance().getCurrentQuestion();
		ScoreManager.getInstance().addWrongAnswerQuestionToPlayerList(currentQuestion, selectedPlayer, selectedTeam, -10);

		view.disableScoresButton();
		view.updateLeftTeamScoreLabel();
		view.updateRightTeamScoreLabel();
		
		views.unselectEveryPlayer();
	}
	
	public void previousQuestionEvent(EventGameChanges event){
		previousQuestion();
	}
	
	public void previousQuestionApp(){
		previousQuestion();
		
		//Send the event
		EventGameChanges event = new EventGameChanges();
		event.setGameAction(GameAction.PREV);
		
		serialiazeEventAndSend(event);
	}
	
	private void previousQuestion(){
		goToPreviousQuestion();
		if(!view.isSkipButtonEnabled()){
			view.enableSkipButton();
		}
		if(QuestionManager.getInstance().getCurrentQuestionTarget() == QuestionTarget.Collectif)
			view.enableScoresButton();
		model.setIsPlayerSelectionEnabled(true);
	}
	
	public void skipQuestionEvent(EventGameChanges event){
		skipQuestion();
	}
	
	public void skipQuestionApp(){
		skipQuestion();
		
		//Send the event
		EventGameChanges event = new EventGameChanges();
		event.setGameAction(GameAction.NEXT);
		
		serialiazeEventAndSend(event);
	}
	
	private void skipQuestion(){
		goToNextQuestion();
		view.disableScoresButton();
		view.enablePrevButton();
	}
	
	public void disableEventProcess(){
		this.isProcessEvent = false;
	}
	
	public void highlightSelectedPlayerPanelEvent(EventGameChanges event){
		String selectedPlayer = event.getPlayerName();
		String selectedTeam = event.getPlayerSide();

//		System.out.println(selectedPlayer + " " + selectedTeam);
		if(selectedPlayer != null && selectedTeam != null){
			model.setCurrentlySelectedPlayer(selectedPlayer);
			model.setTeamSelected(selectedTeam);
			highlightSelectedPlayerPanel(selectedPlayer,selectedTeam);
		}
		else{
			System.out.println("[DEBUG] - Cannot parse event");
		}
	}
	
	public void highlightSelectedPlayerPanelApp(String playerName, String playerTeam){

		if(playerName != null && playerTeam != null){
			model.setCurrentlySelectedPlayer(playerName);
			model.setTeamSelected(playerTeam);
			highlightSelectedPlayerPanel(playerName,playerTeam);
			
			//Send the event
			EventGameChanges event = new EventGameChanges();
			event.setGameAction(GameAction.SELECT_PLAYER);
			event.setPlayerName(playerName);
			event.setPlayerSide(playerTeam);
			
			serialiazeEventAndSend(event);
		}
		else{
			System.out.println("[DEBUG] - Cannot generate event");
		}
		
	}
	
	private void highlightSelectedPlayerPanel(String playerName, String playerTeam){
		views.highlightSelectedPlayerPanel(playerName, playerTeam);
		if(QuestionManager.getInstance().getCurrentQuestionTarget() == QuestionTarget.Collectif)
			view.enableScoresButton();
		view.disableAllPlayerIndividualPointEdition();
		view.enablePlayerIndividualPointEdition(playerName, playerTeam);
	}
	
	public void playMediaEvent(EventGameChanges event){
		play();
	}
	
	public void playMediaApp(){
		play();
		
		//Send the event
		EventGameChanges event = new EventGameChanges();
		event.setGameAction(GameAction.PLAY_MEDIA);
		
		serialiazeEventAndSend(event);
	}
	
	public void pauseMediaEvent(EventGameChanges event){
		pause();
	}
	
	public void pauseMediaApp(){
		pause();
		
		//Send the event
		EventGameChanges event = new EventGameChanges();
		event.setGameAction(GameAction.PAUSE_MEDIA);
		
		serialiazeEventAndSend(event);
	}
	
	public void stopMediaEvent(EventGameChanges event){
		stop();
	}
	
	public void stopMediaApp(){
		stop();
		
		//Send the event
		EventGameChanges event = new EventGameChanges();
		event.setGameAction(GameAction.STOP_MEDIA);
		
		serialiazeEventAndSend(event);
	}
	
	public CurrentGameModel getModel() {
		return model;
	}

	public Object getView() {
		return view;
	}
	
	public void initData(){
		isProcessEvent = true;
		//If a game was previously ended, reset the ui.
		if (model.getIsEndOfGameReached()){
			view.resetUI();
		}
		
		views.loadTeamInfo();
		
		//Stuff to do if we recovered a game
		if(FileManager.getInstance().isGameInProgressLocally(QuestionManager.getInstance().getCurrentGameKey())){
			view.updateLeftTeamScoreLabel();
			view.updateRightTeamScoreLabel();
			selectPlayerWhoAnsweredQuestion(QuestionManager.getInstance().getCurrentQuestion());
		}
		
		if (model.getIsEndOfGameReached() == true){
			triggerEndOfGame();
		}
		else{
			showCurrentQuestion();
		}
		views.updateViewOnPageChanged();
		view.disableScoresButton();
	}
	
	public void showCurrentQuestion() {
		Question question = QuestionManager.getInstance().getCurrentQuestion();
		if(question != null){
			changeQuestion(question);
		}
	}
	
	private void selectPlayerWhoAnsweredQuestion(Question question){
		if (QuestionManager.getInstance().getCurrentQuestionTarget() == QuestionTarget.Collectif){
			PlayerInfo playerInfo = ScoreManager.getInstance().getPlayerWhoAnsweredQuestionCorrectly(question);

			if(playerInfo != null){
				setSelectedPlayer(playerInfo.playerName, playerInfo.playerTeamSide);
			}
		}
		else if (QuestionManager.getInstance().getCurrentQuestionTarget() == QuestionTarget.Individual){
			loadIndividualScores(question);
		}
	}
	
	public void loadIndividualScores(Question question){
		for (String playerName : TeamManager.getInstance().getLeftTeamNames()){
			QuestionTest qt = ScoreManager.getInstance().getPlayerIndividualQuestion(question, playerName, "Left");
			if (qt != null){
				view.setPlayerIndividualScore("Left",  playerName, qt.getValue());
			}else{
				view.setPlayerIndividualScore("Left", playerName, 0);
			}
		}
		
		for (String playerName : TeamManager.getInstance().getRightTeamNames()){
			QuestionTest qt = ScoreManager.getInstance().getPlayerIndividualQuestion(question, playerName, "Right");
			if (qt != null){
				view.setPlayerIndividualScore("Right", playerName, qt.getValue());
			}else{
				view.setPlayerIndividualScore("Right", playerName, 0);
			}
		}
	}
	
	public void goToNextQuestion() {
		Question question = QuestionManager.getInstance().getNextQuestion();
		views.unselectEveryPlayer();
		if(question != null){
			changeQuestion(question);
			selectPlayerWhoAnsweredQuestion(question);
		}
		else{
			triggerEndOfGame();
		}
	}
	
	private void triggerEndOfGame(){
		model.setIsEndOfGameReached(true);
		//Change UI
		views.triggerEndOfGame();

		//Disable next button
		views.disableSkipButton();
		
		isProcessEvent = false;
	}
	
	public void goToPreviousQuestion(){
		Question question = QuestionManager.getInstance().getPreviousQuestion();
		views.unselectEveryPlayer();
		if(question != null){
			//Get the player who answered the question
			changeQuestion(question);
			selectPlayerWhoAnsweredQuestion(question);
		}
		if (!isProcessEvent){
			isProcessEvent = true;
		}
	}
	
	public void changeQuestion(Question question){
		//Serialize data NOW!!!
		model.setIsEndOfGameReached(false);
		if(!model.getAreStatsPosted()){
			if(Defines.PC_BUILD)
				GameDataImportExport.getInstance().saveCurrentGameDataInFile(FileManager.getInstance().getGameDataFile(QuestionManager.getInstance().getCurrentGameKey()));
		}

		views.changeQuestionHookFunction();
		
		if(question.getClass() == QuestionGeneral.class){
			views.showGeneralQuestion((QuestionGeneral)question);
		}
		else if(question.getClass() == QuestionIntru.class){
			views.showIntruQuestion((QuestionIntru)question);
		}
		else if(question.getClass() == QuestionAssociation.class){
			views.showAssociationQuestion((QuestionAssociation)question);
		}
		else if(question.getClass() == QuestionAnagram.class){
			views.showAnagramQuestion((QuestionAnagram)question);
		}
		else if(question.getClass() == QuestionIdentification.class){
			views.showMultipleClueQuestion((QuestionIdentification)question);
		}
		else if(question.getClass() == QuestionMedia.class){
			views.showMediaQuestion((QuestionMedia)question);
		}

		if (QuestionManager.getInstance().getIsFirstQuestionShown()){
			view.disablePrevButton();
		}
		else{
			view.enablePrevButton();
		}
		
		views.unselectEveryPlayer();
		view.disableAllPlayerIndividualPointEdition();
		
		/*if (QuestionManager.getInstance().getCurrentQuestionTarget() == QuestionTarget.Collectif){
			view.enableScoresButton();
		}
		else{
			view.disableScoresButton();
		}*/
		
		views.repaintPanelContent();
	}
	
	public void setSelectedPlayer(String playerName, String playerTeamSide){
		views.setSelectedPlayer(playerName, playerTeamSide);
		model.setCurrentlySelectedPlayer(playerName);
		model.setTeamSelected(playerTeamSide);
	}

	public Boolean getIsEndOfGameReached() {
		return model.getIsEndOfGameReached();
	}

	public void setIsEndOfGameReached(Boolean isEndOfGameReached) {
		model.setIsEndOfGameReached(isEndOfGameReached);
	}

	public ReportQuestionController getReportQuestionController() {
		return reportQuestionController;
	}

	public void setReportQuestionController(ReportQuestionController reportQuestionController) {
		this.reportQuestionController = reportQuestionController;
	}
	
	public void skipMedia(){
		switch (model.getMediaMode()) {
		case NONE:
			break;
		case PICTURE:
			views.nextImage();
			break;
		case AUDIO:
			views.nextVideoTrack();
			break;
		case VIDEO:
			views.nextVideoTrack();
			break;
		}
	}
	
	public void previousMedia(){
		switch (model.getMediaMode()) {
		case NONE:
			break;
		case PICTURE:
			views.previousImage();
			view.previousImage();
			break;
		case AUDIO:
			views.previousAudioTrack();
			break;
		case VIDEO:
			views.previousVideoTrack();
			view.previousVideoTrack();
			break;
		}
	}
	
	private void play(){
		switch (model.getMediaMode()) {
		case NONE:
			break;
		case PICTURE:
			views.showImage();
			break;
		case AUDIO:
			views.playSound();
			break;
		case VIDEO:
			views.playVideo();
			break;
		}
	}
	
	private void pause(){
		switch (model.getMediaMode()) {
		case NONE:
			break;
		case PICTURE:
			break;
		case AUDIO:
			views.pauseSound();
			break;
		case VIDEO:
			views.pauseVideo();
			break;
		}
	}
	
	private void stop(){
		switch (model.getMediaMode()) {
		case NONE:
			break;
		case PICTURE:
			break;
		case AUDIO:
			views.stopSound();
			break;
		case VIDEO:
			views.stopVideo();
			break;
		}
	}

	private void serialiazeEventAndSend(EventGameChanges event){
		if(GetInstanceBluetoothManager.getInstance().isConnectionToBluetoothEnabled()){
			String msg = SerializationManager.getInstance().serializeObject(event);
			if(msg != null){
				ConsoleEventSerializedMessage consoleEvent = new ConsoleEventSerializedMessage("");
				consoleEvent.setObjectType(1);
				consoleEvent.setMessage(msg);
				GetInstanceBluetoothManager.getInstance().sendMessage(consoleEvent.toString());
			} else{
				System.out.println("ERROR: Serialized message is null, see why!!!");
			}
		}
	}
	
	private EventGameChanges deserializeEvent(String eventString){
		EventGameChanges event = null;
		event = (EventGameChanges)SerializationManager.getInstance().deserializeObjectFromString(EventGameChanges.class, eventString);
		return event;
	}

	public void initPanelAudioPlayer(int maxValue) {
		views.initPanelAudioPlayer(maxValue);
	}

	public void updatePanelAudioPlayer(int newValue, int currentSoundTime) {
		views.updatePanelAudioPlayer(newValue, currentSoundTime);
	}

	public void resetPanelAudioPlayer(int totalTrackTimeInMilliseconds) {
		views.resetPanelAudioPlayer(totalTrackTimeInMilliseconds);
	}

	public void setProcessEvent(boolean isProcessEvent) {
		this.isProcessEvent = isProcessEvent;
	}
	
	public void setGameAsEnded(){
		//Get game object
		Game game = RequestsWebService.getGame(QuestionManager.getInstance().getCurrentGameKey());
		String leftTeamKey = TeamManager.getInstance().getLeftTeam().getKey();
		String rightTeamKey = TeamManager.getInstance().getRightTeam().getKey();
		
		game.setTeam1Score(ScoreManager.getInstance().getLeftTeamScore());
		game.setTeam2Score(ScoreManager.getInstance().getRightTeamScore());
		game.setActive(true);
		
		//Put game
		RequestsWebService.editGame(game);
		RequestsWebService.closeGame(game.getKey());
		System.out.println("closing time");
		deleteGameFile(game.getKey());
	}
	
	public void postGameLogs(){
		
		String gameKey = QuestionManager.getInstance().getCurrentGameKey();
		String teamKey = "";
		String userKey = "";
		String questionKey = "";
		boolean answer = true;
		int points = 0;
		
		//Left Side
		teamKey = TeamManager.getInstance().getLeftTeam().getKey();
		for(String player : ScoreManager.getInstance().getLeftPlayerQuestionsAnswered().keySet()){
			userKey = TeamManager.getInstance().getUser(player, "Left").getKey();
			for(QuestionTest qt : ScoreManager.getInstance().getLeftPlayerQuestionsAnswered().get(player)){
				questionKey = qt.getQuestion().getKey();
				answer = qt.answer;
				points = qt.value;
				//TODO - Uncomment after presentation day.
				sendGameLog(gameKey, userKey, questionKey, 10, answer, points, teamKey, TeamPosition.Team1);
			}
		}
		
		//Right Side
		teamKey = TeamManager.getInstance().getRightTeam().getKey();
		for(String player : ScoreManager.getInstance().getRightPlayerQuestionsAnswered().keySet()){
			userKey = TeamManager.getInstance().getUser(player, "Right").getKey();
			for(QuestionTest qt : ScoreManager.getInstance().getRightPlayerQuestionsAnswered().get(player)){
				questionKey = qt.getQuestion().getKey();
				answer = qt.answer;
				points = qt.value;
				//TODO - Uncomment after presentation day.
				sendGameLog(gameKey, userKey, questionKey, 10, answer, points, teamKey, TeamPosition.Team2);
			}
		}
	}
	
	private void sendGameLog(String gameKey, String userKey, String questionKey, double responseTime, boolean answer, int points, String teamKey, TeamPosition teamPosition){
		GameLog gameLog = new GameLog(gameKey, userKey, questionKey, responseTime, answer, points, teamKey, teamPosition);
		RequestsWebService.addGameLog(gameLog);
	}
	
	private void deleteGameFile(String gameKey) {
		if (gameKey != null && gameKey != ""){
			FileManager.getInstance().deleteGameDataFile(gameKey);
		}
	}
}
