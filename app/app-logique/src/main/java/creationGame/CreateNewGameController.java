package creationGame;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import serialization.GameDataImportExport;
import applicationTools.Defines;
import applicationTools.Defines.CreateNewGameAction;
import applicationTools.FileManager;
import applicationTools.LoginManager;
import applicationTools.PageManager;
import applicationTools.QuestionManager;
import applicationTools.ScoreManager;
import applicationTools.TeamManager;
import ca.openquiz.comms.RequestsWebService;
import ca.openquiz.comms.enums.CategoryType;
import ca.openquiz.comms.enums.Degree;
import ca.openquiz.comms.enums.QuestionTarget;
import ca.openquiz.comms.enums.QuestionType;
import ca.openquiz.comms.model.Category;
import ca.openquiz.comms.model.Game;
import ca.openquiz.comms.model.Group;
import ca.openquiz.comms.model.QuestionSet;
import ca.openquiz.comms.model.QuestionSetSection;
import ca.openquiz.comms.model.Team;
import ca.openquiz.comms.model.Template;
import ca.openquiz.comms.model.User;
import ca.openquiz.comms.response.KeyResponse;
import ca.openquiz.comms.response.UserListResponse;

public class CreateNewGameController {
	protected CreateNewGameModel model;
	protected ICreateNewGameView view;
	private Boolean isPanelDataInit;
	
	public CreateNewGameController() {
		model = new CreateNewGameModel();
		
		isPanelDataInit = false;
	}
	
	public Object getView(){
		return view;
	}
	
	private void resetGameData(){
		TeamManager.getInstance().resetTeams();
		QuestionManager.getInstance().resetGame();
		ScoreManager.getInstance().resetScores();
	}
	
	public boolean playGame(){
		boolean validGameData = false;
		//Reset everything just to be sure
		resetGameData();
		
		/*Test for TeamManager */
		if(validateData())
		{
			if (isGameDataAlreadyStarted()){
				loadGameData();
			}
			else if (view.isLoadGeneratedGame()){
				Game game = view.getSelectedGame();
				setGameData(game);
			}
			else{
				setGameData(null);
			}
			System.out.println("[DEBUG_TEMPLATE] validateData()");
			//Load questions for the game
			
			if(initGameData()){
				System.out.println("[DEBUG_TEMPLATE] initGameData()");
				validGameData = true;
				if(Defines.PC_BUILD)
				{
					//Serialize all this
					if (!isGameDataAlreadyStarted()){
						GameDataImportExport.getInstance().saveCurrentGameDataInFile(FileManager.getInstance().getGameDataFile(QuestionManager.getInstance().getCurrentGameKey()));
					}
					
					//Start downloading the media files
					List<String[]> mediaFilesPath = QuestionManager.getInstance().getAllMediaPathQuestions();
					view.downloadMediaFiles(mediaFilesPath);
					
					//Change page
					PageManager.getInstance().setPageDisplayed(PageManager.CURRENTGAME);
				}
			}
		}
		return validGameData;
	}
	
	public void loadGameData(){
		if (view.isLoadGeneratedGame()){
			Game game = view.getSelectedGame();
			if (game != null){
				loadGameInProgress(game.getKey());
			}
		}
	}
	
	public boolean isGameDataAlreadyStarted(){
		if (view.isLoadGeneratedGame()){
			Game game = view.getSelectedGame();
			if (game != null){
				return FileManager.getInstance().isGameInProgressLocally(game.getKey());
			}
		}
		return false;
	}

	public CreateNewGameModel getModel() {
		return model;
	}
	
	public void loadGameInProgress(String gameKey){
		//Check if the file for this game exist, than load the data.
		if(FileManager.getInstance().isGameInProgressLocally(gameKey)){
			GameDataImportExport.getInstance().loadCurrentGameDataFromFile(FileManager.getInstance().getGameDataFile(gameKey));
		}
		
		//Go to currentGame.
		//PageManager.getInstance().setPageDisplayed(PageManager.CURRENTGAME);
	}
	
	public void initData(){
		if(!isPanelDataInit){
			loadQuestionSets();
			loadGroups();
			loadOpenGames();
			loadCategories();
			loadTemplates();
		}
		isPanelDataInit = true;
	}
	
	public void refreshData() {
		//Clear everything first
		view.clearQuestionSetList();
		view.clearTemplateList();
		view.clearOpenGameList();
		view.clearTeamsCompletly();
		
		model.clearGameList();
		model.clearGroups();
		model.clearTemplateList();
		
		loadQuestionSets();
		loadGroups();
		loadOpenGames();
		loadTemplates();
	}
	
	public void loadQuestionSets(){
		ArrayList<Object[]> questionSetsData = new ArrayList<Object[]>();
		
		List<QuestionSet> questionSets = RequestsWebService.getQuestionSets();
		
		if (questionSets != null){
			for(Iterator<QuestionSet> it = questionSets.iterator(); it.hasNext();){
				QuestionSet currentQuestionSet = (QuestionSet) it.next();
				
				Locale localeFrCa = new Locale("fr", "ca");
				DateFormat availableDateFormatFrCa =  DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL, localeFrCa);
				
				questionSetsData.add(new Object[]{ currentQuestionSet.getName(), availableDateFormatFrCa.format(currentQuestionSet.getAvailableDate()), 0 });
			}
			QuestionManager.getInstance().setQuestionSetList(questionSets);
			
			view.setQuestionSetTableModel(questionSetsData);
		}
	}
	
	public void loadOpenGames(){
		List<Game> games = RequestsWebService.getGames();
		String team1Key;
		String team2Key;
		if (games != null){
			for(Game game : games){
				team1Key = game.getTeam1();
				team2Key = game.getTeam2();
				
				//Try to load the teams from the already loaded team
				Team team1 = model.loadTeamById(team1Key);
				Team team2 = model.loadTeamById(team2Key);
				
				String team1Name = "";
				String team2Name = "";
				
				if (team1 != null){
					team1Name = team1.getName();
				}
				if (team2 != null){
					team2Name = team2.getName();
				}
				
				Object[] gameData = new Object[]{ game.getName(), game.getGameDate(), game.getTournamentKey(), team1Name, team2Name};
				
				if (gameData != null){
					//Try to load the tournament
					view.addGameDataToTable(gameData);
					model.addGame(game);
				}
			}
		}
	}
	
	private boolean initGameData(){
		boolean isInitOk = true;
		String gameName = view.getGameName();
		String gameKey = "";
		QuestionSet questionSet = null;
		
		//Generate a question set from a template and create the game
		if (view.isGenerateGameFromTemplate()){
			System.out.println("[DEBUG_TEMPLATE] view.isGenerateGameFromTemplate()");
			Template template = view.getSelectedTemplate();
			
			if (template != null){
				System.out.println("[DEBUG_TEMPLATE] view.isGenerateGameFromTemplate() template != null");
				System.out.println("[DEBUG_TEMPLATE] category=" + template.getSectionList().get(0).getCategory());
				System.out.println("[DEBUG_TEMPLATE] nbQuestions=" + template.getSectionList().get(0).getNbQuestions());
				System.out.println("[DEBUG_TEMPLATE] description=" + template.getSectionList().get(0).getDescription());
				System.out.println("[DEBUG_TEMPLATE] points=" + template.getSectionList().get(0).getPoints());
				System.out.println("[DEBUG_TEMPLATE] difficulty=" + template.getSectionList().get(0).getDifficulty());
				System.out.println("[DEBUG_TEMPLATE] type=" + template.getSectionList().get(0).getQuestionType());
				System.out.println("[DEBUG_TEMPLATE] question target=" + template.getSectionList().get(0).getQuestionTarget());
				questionSet = RequestsWebService.generateQuestionSet(template, LoginManager.getInstance().getAuthorization(), false);
				System.out.println("[DEBUG_TEMPLATE] questionSet=" + questionSet);
			}
			else if (getModel().getActiveTemplate() != null)
			{
				// Play game without saving template.
				template = getModel().getActiveTemplate();
				questionSet = RequestsWebService.generateQuestionSet(template, LoginManager.getInstance().getAuthorization(), false);
				getModel().setActiveTemplate(null);
			}
			else {
				//TODO - Show the user no template is selected
				System.out.println("[DEBUG_TEMPLATE] view.isGenerateGameFromTemplate() else");
				isInitOk = false;
			}
		}
		//Start a game from an already set game.
		else if (view.isLoadGeneratedGame()){
			System.out.println("[DEBUG_TEMPLATE] view.isLoadGeneratedGame()");
			Game game = view.getSelectedGame();
			
			//TODO - The game object is not good.
			if(game == null){
				System.out.println("[DEBUG_TEMPLATE] game == null");
				isInitOk = false;
			}
			else{
				gameKey = game.getKey();
				String key = game.getQuestionSetKey();
				questionSet = RequestsWebService.getQuestionSetById(key);
				QuestionManager.getInstance().setCurrentGameKey(gameKey);
			}
		}
		else {
			System.out.println("[DEBUG_TEMPLATE] else");
			if(view.getQuestionSetTableSelectedRow() >= 0){
				int selectedItem = view.getQuestionSetTableSelectedRow();
				
				questionSet = RequestsWebService.getQuestionSetById(QuestionManager.getInstance().getQuestionSetList().get(selectedItem).getKey());
				
				if (questionSet == null){
					System.out.println("questionSet == null");
					isInitOk = false;
				}
			}
			else{
				System.out.println("No questionSet selected");
				isInitOk = false;
			}
		}
		
		//TODO - Show the user the question set is not good.
		System.out.println("[DEBUG_TEMPLATE] before if (questionSet != null){=" + questionSet);
		if (questionSet != null){
			//TODO - Check the questionSet for integriety
			if(questionSet.getSectionList().size() > 0){
				System.out.println("[DEBUG_TEMPLATE] question set OK");
				QuestionManager.getInstance().setQuestionSectionList((ArrayList<QuestionSetSection>)questionSet.getSectionList());
			}
			else{
				System.out.println("questionSectionList empty");
				System.out.println("[DEBUG_TEMPLATE] list empty");
				isInitOk = false;
			}
			
			//Create Game Object
			if(isInitOk){
				Game game = null;
				if(gameKey != null && gameKey != ""){
					game = RequestsWebService.getGame(gameKey);	
					System.out.println("[DEBUG_TEMPLATE] get game");
				}
				
				
				if (game == null){
					System.out.println("[DEBUG_TEMPLATE] game null");
					Date newDate = new Date();
					KeyResponse newGameKey = createAndSendGame(true, new Date(), gameName, questionSet.getKey(), getSelectedTeam1Key(), getSelectedTeam2Key(), getSelectedUserList1Keys(), getSelectedUserList2Keys());
					if (newGameKey.getError() != null || newGameKey.getKey() == null){
						System.out.println("GameKey error");
						System.out.println("[DEBUG_TEMPLATE] gamekey error");
						isInitOk = false;
					}
					else{
						System.out.println("[DEBUG_TEMPLATE] game no error");
						QuestionManager.getInstance().setCurrentGameKey(newGameKey.getKey());
					}
				}

			}
		}
		else{
			isInitOk = false;
		}
		return isInitOk;
	}
	
	private List<String> getSelectedUserList1Keys(){
		List<String> userList = model.getUserListKeys(view.getLeftGroupName(), view.getLeftTeamName(), view.getLeftTeamPlayer1(), 
				view.getLeftTeamPlayer2(), view.getLeftTeamPlayer3(), view.getLeftTeamPlayer4());

		return userList;
	}
	
	private List<String> getSelectedUserList2Keys(){
		List<String> userList = model.getUserListKeys(view.getRightGroupName(), view.getRightTeamName(), view.getRightTeamPlayer1(), 
				view.getRightTeamPlayer2(), view.getRightTeamPlayer3(), view.getRightTeamPlayer4());

		return userList;
	}

	private String getSelectedTeam1Key(){
		String leftTeamKey = model.getTeamObjectInGroup(view.getLeftGroupName(), view.getLeftTeamName()).getKey();

		return leftTeamKey;
	}
	
	private String getSelectedTeam2Key(){
		String rightTeamKey = model.getTeamObjectInGroup(view.getRightGroupName(), view.getRightTeamName()).getKey();
	
		return rightTeamKey;
	}
	
	private KeyResponse createAndSendGame(boolean isActive, Date gameDate, String name, String questionSetKey, 
			String leftTeamKey, String rightTeamKey, List<String> leftUsersListKey, List<String> rightUsersListKey){
		Game game = new Game();
		game.setActive(isActive);
		game.setGameDate(gameDate);
		game.setName(name);
		game.setQuestionSetKey(questionSetKey);
		game.setTeam1(leftTeamKey);
		game.setTeam2(rightTeamKey);
		game.setTeam1Players(leftUsersListKey);
		game.setTeam2Players(rightUsersListKey);
		
		KeyResponse gameKey = RequestsWebService.addGame(game);
		return gameKey;
	}
	
	public void loadGroups() {

		List<Group> groups = RequestsWebService.getGroups();
		if(groups != null)
		{
			for(Group group : groups){
				if(group.getName() != null){
					model.initGroup(group);
					view.addGroupName(group.getName());
				}
			}
		}
		else
			view.showWebServicePopup(CreateNewGameAction.LOADGROUP);
	}
	
	public void loadTeams(Group group) {

		for(String teamKey : group.getTeamKeys()){
			Team team = RequestsWebService.getTeam(teamKey);
			if(team != null)
			{
				model.addTeam(team);
				model.addTeamToGroup(group.getName(), team);
			}
			else
			{
				view.showWebServicePopup(CreateNewGameAction.LOADTEAMS);
				return;
			}
		}
	}
	
	public void loadUsers(Team team) {		
		UserListResponse response = RequestsWebService.getUsersByTeam(team.getKey(), 1, team.getUserKeys().size());
		List<User> userList = response.getUsers();
		
		for(User user : userList){
			if(user != null)
			{
				if (user.getEmail() == null || user.getEmail() == ""){
					user.setEmail("test@test.com");
				}
				if (user.getLastLoginAttempt() == null){
					user.setLastLogin(new Date());
				}
				if (user.getPassword() == null || user.getPassword() == ""){
					user.setPassword("password");
				}
				model.addUser(user);
				model.addUserToTeam(team.getName(), user);
			}
			else
			{
				view.showWebServicePopup(CreateNewGameAction.LOADUSERS);
				return;
			}
		}
	}
	
	public Boolean validateData() {
		view.changeAllComboBoxBackgroundToValidColor();
		boolean isValidationOk = true;
		/*if(view.checkForEmptyFields()){
			isValidationOk = false;
		}*/
		if(!view.checkForGameName()){
			isValidationOk = false;
		}
		if(!view.checkForDuplicateNameInSameGroup()){
			isValidationOk = false;
		}
		
		return isValidationOk;
	}
	
	public Boolean isPanelDataInit() {
		return isPanelDataInit;
	}
	
	public void setIsPanelDataInit(Boolean isPanelDataInit) {
		this.isPanelDataInit = isPanelDataInit;
	}

	public void setModel(CreateNewGameModel model){
		this.model = model;
	}
	
	public void setView(ICreateNewGameView view){
		this.view = view;
		
		//Load templates in TemplateSelectionPanel
		loadCategories();
		//Add an empty section at first.
		view.addNewTemplateSection(CategoryType.Unknown, "", Degree.normal, QuestionType.General, 10, 10, QuestionTarget.Collectif, "");
	}
	
	public void loadTemplates(){
		List<Template> templates = RequestsWebService.getTemplates();
		
		if (templates != null){
			for(Template template : templates){
				if (template != null){
					model.addTemplate(template);
					
					Object[] data = new Object[]{ template.getName(), template.getSectionList().size()};
					view.addRowToTemplateTableModel(data);
				}
			}
		}
	}
	
	public boolean createTemplate(Template template){
		
		boolean ret = false;
		KeyResponse key = RequestsWebService.addTemplate(template, null);
		
		//Add newly created template in the list.
		if(key == null)
			view.showWebServicePopup(CreateNewGameAction.CREATE_TEMPLATES);
		else if (key.getKey() != null){
			addNewTemplate(template);
			ret = true;
		}
		
		return ret;
	}
	
	public void addNewTemplate(Template template){
		model.addTemplate(template);
		
		Object[] data = new Object[]{template.getName(), template.getSectionList().size()};
		view.addRowToTemplateTableModel(data);
	}
	
	public void loadCategories(){

		List<Category> categories = RequestsWebService.getCategories(RequestsWebService.getAuthorization());
		if(categories != null)
			model.setCategories(categories);
		else
			view.showWebServicePopup(CreateNewGameAction.LOADCATEGORIES);
	}

	public List<String> checkTemplateIntegriety(Template template, List<Integer> maxNbQuestions) {
		List<String> errorList = new ArrayList<String>();
		
		for (int i = 0; i < template.getSectionList().size(); i++){
			if (maxNbQuestions.get(i) == 0){
				errorList.add("La section " + Integer.toString(i+1) + " ne contient aucune question.");
			}
			else if (template.getSectionList().get(i).getNbQuestions() > maxNbQuestions.get(i)){
				errorList.add("La section " + Integer.toString(i+1) + " ne contient pas suffisament de questions.");
			}
		}
		
		return errorList;
	}

	public List<String> checkFullTemplateIntegriety(Template template, List<Integer> maxNbQuestions) {
		List<String> errorList = new ArrayList<String>();
		
		for (int i = 0; i < template.getSectionList().size(); i++){
			if (maxNbQuestions.get(i) == 0){
				errorList.add("La section " + Integer.toString(i+1) + " ne contient aucune question. Cette section sera omise de la partie.");
			}
			else if (template.getSectionList().get(i).getNbQuestions() > maxNbQuestions.get(i)){
				errorList.add("La section " + Integer.toString(i+1) + " ne contient pas suffisament de questions.");
			}
		}
		
		return errorList;
	}

	public void enableGameDataSelection() {
		view.enableGameDataSelection();
	}

	public void disableGameDataSelection() {
		view.disableGameDataSelection();
	}

	public void disablePlayButton() {
		view.disablePlayButton();
	}

	public void enablePlayButton() {
		view.enablePlayButton();
	}
	
	private void setGameData(Game game){
		if (game != null){
			Team team1 = model.loadTeamById(game.getTeam1());
			Team team2 = model.loadTeamById(game.getTeam2());
			Group group1 = model.loadGroupById(team1.getGroupKey());
			Group group2 = model.loadGroupById(team2.getGroupKey());
			
			TeamManager.getInstance().setLeftTeam(team1);
			TeamManager.getInstance().setRightTeam(team2);
			
			for (String userKey : game.getTeam1Players()){
				User user = model.loadUserById(userKey);
				TeamManager.getInstance().addPlayerToLeftTeam(user);
			}
			
			for (String userKey : game.getTeam2Players()){
				User user = model.loadUserById(userKey);
				TeamManager.getInstance().addPlayerToRightTeam(user);
			}
		}
		else{
			String leftTeamName = view.getLeftTeamName();
			String rightTeamName = view.getRightTeamName();
			
			TeamManager.getInstance().setLeftTeam(model.getTeamObjectInGroup(view.getLeftGroupName(), view.getLeftTeamName()));
			TeamManager.getInstance().setRightTeam(model.getTeamObjectInGroup(view.getRightGroupName(), view.getRightTeamName()));
			
			if (view.getLeftTeamPlayer1() != ""){
				TeamManager.getInstance().addPlayerToLeftTeam(model.getUserObjectInTeam(leftTeamName, view.getLeftTeamPlayer1()));
			}
			if (view.getLeftTeamPlayer2() != ""){
				TeamManager.getInstance().addPlayerToLeftTeam(model.getUserObjectInTeam(leftTeamName, view.getLeftTeamPlayer2()));
			}
			if (view.getLeftTeamPlayer3() != ""){
				TeamManager.getInstance().addPlayerToLeftTeam(model.getUserObjectInTeam(leftTeamName, view.getLeftTeamPlayer3()));
			}
			if (view.getLeftTeamPlayer4() != ""){
				TeamManager.getInstance().addPlayerToLeftTeam(model.getUserObjectInTeam(leftTeamName, view.getLeftTeamPlayer4()));
			}
			
			if (view.getRightTeamPlayer1() != ""){
				TeamManager.getInstance().addPlayerToRightTeam(model.getUserObjectInTeam(rightTeamName, view.getRightTeamPlayer1()));
			}
			if (view.getRightTeamPlayer2() != ""){
				TeamManager.getInstance().addPlayerToRightTeam(model.getUserObjectInTeam(rightTeamName, view.getRightTeamPlayer2()));
			}
			if (view.getRightTeamPlayer3() != ""){
				TeamManager.getInstance().addPlayerToRightTeam(model.getUserObjectInTeam(rightTeamName, view.getRightTeamPlayer3()));
			}
			if (view.getRightTeamPlayer4() != ""){
				TeamManager.getInstance().addPlayerToRightTeam(model.getUserObjectInTeam(rightTeamName, view.getRightTeamPlayer4()));
			}
		}
	}
}
