package creationGame;

import java.util.ArrayList;
import java.util.List;

import applicationTools.Defines.CreateNewGameAction;
import ca.openquiz.comms.enums.CategoryType;
import ca.openquiz.comms.enums.Degree;
import ca.openquiz.comms.enums.QuestionTarget;
import ca.openquiz.comms.enums.QuestionType;
import ca.openquiz.comms.model.Category;
import ca.openquiz.comms.model.Game;
import ca.openquiz.comms.model.Template;


public interface ICreateNewGameView {
	
	public void addGroupName(String groupName);
	public String getLeftTeamName();
	public String getRightTeamName();
	public String getLeftGroupName();
	public String getRightGroupName();
	public String getGameName();
	
	public String getLeftTeamPlayer1();
	public String getLeftTeamPlayer2();
	public String getLeftTeamPlayer3();
	public String getLeftTeamPlayer4();
	
	public String getRightTeamPlayer1();
	public String getRightTeamPlayer2();
	public String getRightTeamPlayer3();
	public String getRightTeamPlayer4();

	public Boolean checkForEmptyFields();
	public Boolean checkForDuplicateNameInSameGroup();
	public void clearLeftTeamPlayersNameComboBox();
	public void clearRightTeamPlayersNameComboBox();
	public void addPlayerNameToAllLeftTeamComboBox(String playerName);
	public void addPlayerNameToAllRightTeamComboBox(String playerName);
	public void changeAllComboBoxBackgroundToValidColor();
	public Integer getQuestionSetTableSelectedRow();
	public void RemoveQuestionnaire(Integer RowToRemove);
	public void shufflePlayerComboSelection(String teamSide);
	public void setQuestionSetTableModel(ArrayList<Object[]> questionSetsData);
	
	public void updateLeftGroupTeam();
	public void updateRightGroupTeam();
	public void updateLeftGroupPlayers();
	public void updateRightGroupPlayers();
	
	public Object getPanelContent();
	
	public void addRowToTemplateTableModel(Object[] data);
	public void setTextOfTxtFieldTemplateName(String t);
	public String getTextOfTxtFieldTemplateName();
	public void addNewTemplateSection(CategoryType categoryType, String subCategory, Degree difficulty, QuestionType questionType, int value, int quantity, QuestionTarget questionTarget, String description);
	public boolean isGenerateGameFromTemplate();
	public Template getSelectedTemplate();
	public void showWebServicePopup(CreateNewGameAction action);
	public boolean checkForGameName();
	public boolean checkForDuplicateGroupNTeam();
	public void addGameDataToTable(Object[] gameData);
	public boolean isLoadGeneratedGame();
	public Game getSelectedGame();
	public void clearQuestionSetList();
	public void clearTemplateList();
	public void clearOpenGameList();
	public void clearTeamsCompletly();
	public void enableGameDataSelection();
	public void disableGameDataSelection();
	public void disablePlayButton();
	public void enablePlayButton();
	public void downloadMediaFiles(List<String[]> mediaFilesPath);
}
