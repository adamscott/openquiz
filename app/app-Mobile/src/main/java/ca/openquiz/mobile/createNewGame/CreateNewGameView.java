package ca.openquiz.mobile.createNewGame;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import applicationTools.Defines.CreateNewGameAction;
import ca.openquiz.comms.enums.CategoryType;
import ca.openquiz.comms.enums.Degree;
import ca.openquiz.comms.enums.QuestionTarget;
import ca.openquiz.comms.enums.QuestionType;
import ca.openquiz.comms.model.Category;
import ca.openquiz.comms.model.Game;
import ca.openquiz.comms.model.Group;
import ca.openquiz.comms.model.Team;
import ca.openquiz.comms.model.Template;
import ca.openquiz.comms.model.TemplateSection;
import ca.openquiz.comms.model.User;
import ca.openquiz.mobile.Openquiz;
import ca.openquiz.mobile.R;
import ca.openquiz.mobile.util.AlertBuilder;
import ca.openquiz.mobile.util.CreateGameValues;
import ca.openquiz.mobile.util.Defines;
import ca.openquiz.mobile.util.QuestionSetArrayAdapter;
import ca.openquiz.mobile.util.SortList;
import ca.openquiz.mobile.util.TemplateArrayAdapter;
import ca.openquiz.mobile.wizardQuestionSet.QuestionSetSelection;
import ca.openquiz.mobile.wizardTemplate.NewTemplateArrayAdapter;
import ca.openquiz.mobile.wizardTemplate.QuestionCountAsyncTask;
import ca.openquiz.mobile.wizardTemplate.TemplateLayout;
import ca.openquiz.mobile.wizardTemplate.TemplateValidationException;
import ca.openquiz.mobile.wizardTemplate.WizardCreateTemplateAct;
import creationGame.ICreateNewGameView;

public class CreateNewGameView implements ICreateNewGameView{
	
	private Activity teamActivity;
	private Activity questionSetActivity;
	private Activity createTemplateActivity;
	private Activity joinGameActivity;
	private Openquiz openquizActivityManager  = new Openquiz();
	
	public QuestionSetArrayAdapter adaptorQuestionSet;
	public TemplateArrayAdapter adaptorTemplate;
	private NewTemplateArrayAdapter adaptorNewTemplate;

	public ArrayList<ArrayAdapter<String>> team1ArrayAdapter = new ArrayList<ArrayAdapter<String>>();
	public ArrayList<ArrayAdapter<String>> team2ArrayAdapter = new ArrayList<ArrayAdapter<String>>();
	
	private String templateName;
	private String gameName;
	
	private CreateGameValues createGameValues = new CreateGameValues();
	
	@Override
	public void addGroupName(final String groupName) {

		new Handler(teamActivity.getBaseContext().getMainLooper()).post(new Runnable() {
            public void run() {
            	if(groupName != null)
            	{
            		team1ArrayAdapter.get(Defines.SPINNER_TEAM_GROUP_POSITION).add(groupName);
            		team2ArrayAdapter.get(Defines.SPINNER_TEAM_GROUP_POSITION).add(groupName);
            		((View)teamActivity.findViewById(R.id.loading)).setVisibility(View.GONE);
            	}
            }});
	}
	
	@Override
	public String getLeftTeamName()
	{
		return team1ArrayAdapter.get(Defines.SPINNER_TEAM_TEAM_POSITION).getItem(createGameValues.team1);
	}
	public void setLeftTeamName(int position) {
		createGameValues.team1 = position;
	}
	
	

	@Override
	public String getRightTeamName()
	{
		return team2ArrayAdapter.get(Defines.SPINNER_TEAM_TEAM_POSITION).getItem(createGameValues.team2);
	}	
	public void setRightTeamName(int position) {
		createGameValues.team2 = position;
	}

	@Override
	public String getLeftGroupName()
	{		
		return team1ArrayAdapter.get(Defines.SPINNER_TEAM_GROUP_POSITION).getItem(createGameValues.group1);
	}	
	public void setLeftGroupName(int groupPosition) {
		setLeftTeamName(Defines.ARRAY_POSITION_INIT);
		createGameValues.group1 = groupPosition;
	}
	
	@Override
	public String getRightGroupName()
	{
		return team2ArrayAdapter.get(Defines.SPINNER_TEAM_GROUP_POSITION).getItem(createGameValues.group2);
	}
	public void setRightGroupName(int groupPosition) {
		setRightTeamName(Defines.ARRAY_POSITION_INIT);
		createGameValues.group2 = groupPosition;
	}

	@Override
	public String getGameName() {
		return gameName;
	}
	
	public void setGameName(String gameName){
		this.gameName = gameName;
	}

	@Override
	public String getLeftTeamPlayer1() {		
		return team1ArrayAdapter.get(Defines.SPINNER_TEAM_USER_1_POSITION).getItem(createGameValues.player1team1);
	}
	public void setLeftTeamPlayer1(int position) {
		createGameValues.player1team1 = position;
	}

	@Override
	public String getLeftTeamPlayer2() {		
		return team1ArrayAdapter.get(Defines.SPINNER_TEAM_USER_2_POSITION).getItem(createGameValues.player2team1);
	}
	public void setLeftTeamPlayer2(int position) {
		createGameValues.player2team1 = position;
	}

	@Override
	public String getLeftTeamPlayer3() {		
		return team1ArrayAdapter.get(Defines.SPINNER_TEAM_USER_3_POSITION).getItem(createGameValues.player3team1);
	}
	public void setLeftTeamPlayer3(int position) {
		createGameValues.player3team1 = position;
	}

	@Override
	public String getLeftTeamPlayer4() {		
		return team1ArrayAdapter.get(Defines.SPINNER_TEAM_USER_4_POSITION).getItem(createGameValues.player4team1);
	}
	public void setLeftTeamPlayer4(int position) {
		createGameValues.player4team1 = position;
	}

	@Override
	public String getRightTeamPlayer1() {		
		return team2ArrayAdapter.get(Defines.SPINNER_TEAM_USER_1_POSITION).getItem(createGameValues.player1team2);
	}
	public void setRightTeamPlayer1(int position) {
		createGameValues.player1team2 = position;
	}

	@Override
	public String getRightTeamPlayer2() {		
		return team2ArrayAdapter.get(Defines.SPINNER_TEAM_USER_2_POSITION).getItem(createGameValues.player2team2);
	}
	public void setRightTeamPlayer2(int position) {
		createGameValues.player2team2 = position;
	}

	@Override
	public String getRightTeamPlayer3() {		
		return team2ArrayAdapter.get(Defines.SPINNER_TEAM_USER_3_POSITION).getItem(createGameValues.player3team2);
	}
	public void setRightTeamPlayer3(int position) {
		createGameValues.player3team2 = position;
	}

	@Override
	public String getRightTeamPlayer4() {		
		return team2ArrayAdapter.get(Defines.SPINNER_TEAM_USER_4_POSITION).getItem(createGameValues.player4team2);
	}
	public void setRightTeamPlayer4(int position) {
		createGameValues.player4team2 = position;
	}

	@Override
	public Boolean checkForEmptyFields() 
	{	
		int emptyFieldCount = 0;
		for(int groupCount=1; groupCount<=2; groupCount++)
		{
			String teamName = "wizard_team_" + groupCount;
			LinearLayout groupLayout = (LinearLayout)teamActivity.findViewById(
					teamActivity.getResources().getIdentifier(teamName, "id", teamActivity.getPackageName()));
		
			for(int n=Defines.USER_POSITION_1; n<=Defines.USER_POSITION_4; n++)
			{	
				String userName = "wizardteam_user_spinner_" + n;
				Spinner userSpinner = (Spinner)groupLayout.findViewById(
						teamActivity.getResources().getIdentifier(userName, "id", teamActivity.getPackageName()));
				
				if(userSpinner.getSelectedItemPosition() != 0)
					emptyFieldCount++;
			}
			if(emptyFieldCount < 1)
				return true;
			
			emptyFieldCount = 0;
		}
		return false;
	}
	
	public Boolean checkForEmptyFieldsUser() 
	{	
		for(int groupCount=1; groupCount<=2; groupCount++)
		{
			String teamName = "wizard_team_" + groupCount;
			LinearLayout groupLayout = (LinearLayout)teamActivity.findViewById(
					teamActivity.getResources().getIdentifier(teamName, "id", teamActivity.getPackageName()));
		
			for(int n=Defines.USER_POSITION_1; n<=Defines.USER_POSITION_4; n++)
			{	
				String userName = "wizardteam_user_spinner_" + n;
				Spinner userSpinner = (Spinner)groupLayout.findViewById(
						teamActivity.getResources().getIdentifier(userName, "id", teamActivity.getPackageName()));
				
				if(userSpinner.getSelectedItemPosition() == 0)
				{
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public Boolean checkForDuplicateNameInSameGroup() {

		for(int groupCount=1; groupCount<=2; groupCount++)
		{
			if(checkForDuplicateNameInSameGroup(groupCount))
				return true;
		}
		return false;
	}
	
	public boolean checkForDuplicateNameInSameGroup(int groupId) {

		String teamName = "wizard_team_" + groupId;
		boolean duplicateFlag = false;
		LinearLayout groupLayout = (LinearLayout)teamActivity.findViewById(
				teamActivity.getResources().getIdentifier(teamName, "id", teamActivity.getPackageName()));
		
		for(int n=Defines.USER_POSITION_1; n<Defines.USER_POSITION_4; n++)
		{	
			String userName = "wizardteam_user_spinner_" + n;
			Spinner userSpinnerRef = (Spinner)groupLayout.findViewById(
					teamActivity.getResources().getIdentifier(userName, "id", teamActivity.getPackageName()));
			
			for(int nc=n+1; nc<=Defines.USER_POSITION_4; nc++)
			{
				String userNameCompare = "wizardteam_user_spinner_" + nc;

				Spinner userSpinnerCompare = (Spinner)groupLayout.findViewById(
						teamActivity.getResources().getIdentifier(userNameCompare, "id", teamActivity.getPackageName()));
				
				if(userSpinnerRef.getSelectedItemPosition() == userSpinnerCompare.getSelectedItemPosition() && userSpinnerRef.getSelectedItemPosition() != 0)
				{
					hightlightDuplicateName(groupId, n);
					hightlightDuplicateName(groupId, nc);
					duplicateFlag = true;
				}
			}
		}
		
		return duplicateFlag;
	}
	
	public boolean checkForDuplicateNameBetweenGroups()
	{
		boolean duplicateFlag = false;
		int[] playerPositions = new int[8];
		String teamName = "wizard_team_" + Defines.GROUP_POSITION_1;
		LinearLayout groupLayout = (LinearLayout)teamActivity.findViewById(
				teamActivity.getResources().getIdentifier(teamName, "id", teamActivity.getPackageName()));
		
		for(int n=Defines.USER_POSITION_1; n<=Defines.USER_POSITION_4; n++)
		{	
			String userName = "wizardteam_user_spinner_" + n;
			Spinner userSpinner = (Spinner)groupLayout.findViewById(
					teamActivity.getResources().getIdentifier(userName, "id", teamActivity.getPackageName()));
			playerPositions[n-1] = userSpinner.getSelectedItemPosition();
		}
		
		teamName = "wizard_team_" + Defines.GROUP_POSITION_2;
		groupLayout = (LinearLayout)teamActivity.findViewById(
				teamActivity.getResources().getIdentifier(teamName, "id", teamActivity.getPackageName()));
		
		for(int n=Defines.USER_POSITION_1; n<=Defines.USER_POSITION_4; n++)
		{	
			String userName = "wizardteam_user_spinner_" + n;
			Spinner userSpinner = (Spinner)groupLayout.findViewById(
					teamActivity.getResources().getIdentifier(userName, "id", teamActivity.getPackageName()));
			playerPositions[n+3] = userSpinner.getSelectedItemPosition();
		}
		
		for(int i=0; i<playerPositions.length-1; i++)
		{
			for(int j=i+1; j<playerPositions.length; j++)
			{
				if(playerPositions[i] == playerPositions[j] && playerPositions[i]!=0)
				{
					highlightDuplicateNamesBetweenGroups(i,j);
					duplicateFlag = true;
				}
			}
		}
			
		return duplicateFlag;
	}
	
	@SuppressLint("CutPasteId")
	public boolean checkIfSameGroupSelected()
	{
		LinearLayout layoutTeam = (LinearLayout)teamActivity.findViewById(R.id.wizard_team_1);
		Spinner groupSpinner1 = (Spinner)layoutTeam.findViewById(R.id.wizardteam_group_spinner);
		
		layoutTeam = (LinearLayout)teamActivity.findViewById(R.id.wizard_team_2);
		Spinner groupSpinner2 = (Spinner)layoutTeam.findViewById(R.id.wizardteam_group_spinner);
		
		if(groupSpinner1.getSelectedItemPosition() == groupSpinner2.getSelectedItemPosition() && groupSpinner1.getSelectedItemPosition() != 0)
		{
			return true;
		}
		
		return false;
	}
	
	@SuppressLint("CutPasteId")
	public boolean checkIfSameTeamSelected()
	{
		LinearLayout layoutTeam = (LinearLayout)teamActivity.findViewById(R.id.wizard_team_1);
		Spinner teamSpinner1 = (Spinner)layoutTeam.findViewById(R.id.wizardteam_team_spinner);
		
		layoutTeam = (LinearLayout)teamActivity.findViewById(R.id.wizard_team_2);
		Spinner teamSpinner2 = (Spinner)layoutTeam.findViewById(R.id.wizardteam_team_spinner);
		
		if(teamSpinner1.getSelectedItemPosition() == teamSpinner2.getSelectedItemPosition() && teamSpinner1.getSelectedItemPosition() != 0)
		{
			return true;
		}
		
		return false;
	}
	
	public void highlightDuplicateNamesBetweenGroups(int i, int j)
	{
		if(i<4)
		{
			hightlightDuplicateName(Defines.GROUP_POSITION_1,i+1);
		}
		else
		{
			hightlightDuplicateName(Defines.GROUP_POSITION_2,i-3);
		}
		if(j<4)
		{
			hightlightDuplicateName(Defines.GROUP_POSITION_1,j+1);
		}
		else
		{
			hightlightDuplicateName(Defines.GROUP_POSITION_2,j-3);
		}
	}
	
	public void hightlightDuplicateName(int groupId, int userId)
	{
		String teamName = "wizard_team_" + groupId;
		LinearLayout groupLayout = (LinearLayout)teamActivity.findViewById(
				teamActivity.getResources().getIdentifier(teamName, "id", teamActivity.getPackageName()));
		
		String userName = "wizardteam_user_spinner_" + userId;
		Spinner userSpinnerRef = (Spinner)groupLayout.findViewById(
				teamActivity.getResources().getIdentifier(userName, "id", teamActivity.getPackageName()));
		
	    final TextView tv = (TextView) userSpinnerRef.getSelectedView();
	    
		
		new Handler(teamActivity.getBaseContext().getMainLooper()).post(new Runnable() {
            public void run() {
            	if(tv!=null)
            		tv.setTextColor(Color.RED);
            }});
	}
	
	public void resetNameColor()
	{
		for(int groupCount=1; groupCount<=2; groupCount++)
		{
			for(int n=Defines.USER_POSITION_1; n<=Defines.USER_POSITION_4; n++)
			{
				String teamName = "wizard_team_" + groupCount;
				LinearLayout groupLayout = (LinearLayout)teamActivity.findViewById(
						teamActivity.getResources().getIdentifier(teamName, "id", teamActivity.getPackageName()));
				
				String userName = "wizardteam_user_spinner_" + n;
				Spinner userSpinnerRef = (Spinner)groupLayout.findViewById(
						teamActivity.getResources().getIdentifier(userName, "id", teamActivity.getPackageName()));
				
			    final TextView tv = (TextView) userSpinnerRef.getSelectedView();
			    
				
				new Handler(teamActivity.getBaseContext().getMainLooper()).post(new Runnable() {
		            public void run() {
		            	if(tv!=null)
		            		tv.setTextColor(Color.BLACK);
		            }});
			}
		}
	}
	
	public void validateNewGame()
	{
		new Handler(teamActivity.getBaseContext().getMainLooper()).post(new Runnable() {
			public void run() {
				if(checkForDuplicateNameInSameGroup() || (checkIfSameGroupSelected() && checkIfSameTeamSelected() && checkForDuplicateNameBetweenGroups()))
				{
					String title = teamActivity.getResources().getString(R.string.message_title_duplicate_user);
					String message = teamActivity.getResources().getString(R.string.message_info_duplicate_user);
					AlertBuilder.showPopUp(teamActivity, title, message);
				}
				else if(checkForEmptyFields())
				{
					String title = teamActivity.getResources().getString(R.string.message_title_empty_fields);
					String message = teamActivity.getResources().getString(R.string.message_info_empty_fields);
					AlertBuilder.showPopUp(teamActivity, title, message);
				}
				else
				{
					Intent intent = new Intent(teamActivity.getBaseContext(), QuestionSetSelection.class);
					teamActivity.startActivity(intent);
				}
			}});
	}
	
	public void clearLeftTeamComboBox(){
		new Handler(teamActivity.getBaseContext().getMainLooper()).post(new Runnable() {
        public void run() {
    		team1ArrayAdapter.get(Defines.SPINNER_TEAM_TEAM_POSITION).clear();
    		team1ArrayAdapter.get(Defines.SPINNER_TEAM_TEAM_POSITION).add(teamActivity.getResources().getString(R.string.wizardteam_user_default));
        }});
		createGameValues.team1 = 0;
	}
	
	public void clearRightTeamComboBox(){
		new Handler(teamActivity.getBaseContext().getMainLooper()).post(new Runnable() {
        public void run() {
    		team2ArrayAdapter.get(Defines.SPINNER_TEAM_TEAM_POSITION).clear();
    		team2ArrayAdapter.get(Defines.SPINNER_TEAM_TEAM_POSITION).add(teamActivity.getResources().getString(R.string.wizardteam_user_default));
        }});
		createGameValues.team2 = 0;
	}

	@Override
	public void clearLeftTeamPlayersNameComboBox() {

		createGameValues.resetLeftTeamPlayers();
		LinearLayout layoutTeam = (LinearLayout)teamActivity.findViewById(R.id.wizard_team_1);
		final Spinner UserSpinner1 = (Spinner)layoutTeam.findViewById(R.id.wizardteam_user_spinner_1);
		final Spinner UserSpinner2 = (Spinner)layoutTeam.findViewById(R.id.wizardteam_user_spinner_2);
		final Spinner UserSpinner3 = (Spinner)layoutTeam.findViewById(R.id.wizardteam_user_spinner_3);
		final Spinner UserSpinner4 = (Spinner)layoutTeam.findViewById(R.id.wizardteam_user_spinner_4);
		
		
		new Handler(teamActivity.getBaseContext().getMainLooper()).post(new Runnable() {
            public void run() {
            	team1ArrayAdapter.get(Defines.SPINNER_TEAM_USER_1_POSITION).clear();
            	team1ArrayAdapter.get(Defines.SPINNER_TEAM_USER_2_POSITION).clear();
            	team1ArrayAdapter.get(Defines.SPINNER_TEAM_USER_3_POSITION).clear();
            	team1ArrayAdapter.get(Defines.SPINNER_TEAM_USER_4_POSITION).clear();
            	team1ArrayAdapter.get(Defines.SPINNER_TEAM_USER_1_POSITION).add(teamActivity.getResources().getString(R.string.wizardteam_user_default));
        		team1ArrayAdapter.get(Defines.SPINNER_TEAM_USER_2_POSITION).add(teamActivity.getResources().getString(R.string.wizardteam_user_default));
        		team1ArrayAdapter.get(Defines.SPINNER_TEAM_USER_3_POSITION).add(teamActivity.getResources().getString(R.string.wizardteam_user_default));
        		team1ArrayAdapter.get(Defines.SPINNER_TEAM_USER_4_POSITION).add(teamActivity.getResources().getString(R.string.wizardteam_user_default));
        		
        		UserSpinner1.setSelection(0);
        		UserSpinner2.setSelection(0);
        		UserSpinner3.setSelection(0);
        		UserSpinner4.setSelection(0);
            }});
	}

	@Override
	public void clearRightTeamPlayersNameComboBox() {
		
		createGameValues.resetRightTeamPlayers();
		LinearLayout layoutTeam = (LinearLayout)teamActivity.findViewById(R.id.wizard_team_2);
		final Spinner UserSpinner1 = (Spinner)layoutTeam.findViewById(R.id.wizardteam_user_spinner_1);
		final Spinner UserSpinner2 = (Spinner)layoutTeam.findViewById(R.id.wizardteam_user_spinner_2);
		final Spinner UserSpinner3 = (Spinner)layoutTeam.findViewById(R.id.wizardteam_user_spinner_3);
		final Spinner UserSpinner4 = (Spinner)layoutTeam.findViewById(R.id.wizardteam_user_spinner_4);
		
		new Handler(teamActivity.getBaseContext().getMainLooper()).post(new Runnable() {
            public void run() {
            	team2ArrayAdapter.get(Defines.SPINNER_TEAM_USER_1_POSITION).clear();
            	team2ArrayAdapter.get(Defines.SPINNER_TEAM_USER_2_POSITION).clear();
            	team2ArrayAdapter.get(Defines.SPINNER_TEAM_USER_3_POSITION).clear();
        		team2ArrayAdapter.get(Defines.SPINNER_TEAM_USER_4_POSITION).clear();
            	team2ArrayAdapter.get(Defines.SPINNER_TEAM_USER_1_POSITION).add(teamActivity.getResources().getString(R.string.wizardteam_user_default));
        		team2ArrayAdapter.get(Defines.SPINNER_TEAM_USER_2_POSITION).add(teamActivity.getResources().getString(R.string.wizardteam_user_default));
        		team2ArrayAdapter.get(Defines.SPINNER_TEAM_USER_3_POSITION).add(teamActivity.getResources().getString(R.string.wizardteam_user_default));
        		team2ArrayAdapter.get(Defines.SPINNER_TEAM_USER_4_POSITION).add(teamActivity.getResources().getString(R.string.wizardteam_user_default));
        		
        		UserSpinner1.setSelection(0);
        		UserSpinner2.setSelection(0);
        		UserSpinner3.setSelection(0);
        		UserSpinner4.setSelection(0);
            }});		
	}

	@Override
	public void addPlayerNameToAllLeftTeamComboBox(final String playerName) {

		new Handler(teamActivity.getBaseContext().getMainLooper()).post(new Runnable() {
            public void run() {
            	team1ArrayAdapter.get(Defines.SPINNER_TEAM_USER_1_POSITION).add(playerName);
            	team1ArrayAdapter.get(Defines.SPINNER_TEAM_USER_2_POSITION).add(playerName);
            	team1ArrayAdapter.get(Defines.SPINNER_TEAM_USER_3_POSITION).add(playerName);
            	team1ArrayAdapter.get(Defines.SPINNER_TEAM_USER_4_POSITION).add(playerName);
            }});
	}

	@Override
	public void addPlayerNameToAllRightTeamComboBox(final String playerName) {

		new Handler(teamActivity.getBaseContext().getMainLooper()).post(new Runnable() {
            public void run() {
            	team2ArrayAdapter.get(Defines.SPINNER_TEAM_USER_1_POSITION).add(playerName);
            	team2ArrayAdapter.get(Defines.SPINNER_TEAM_USER_2_POSITION).add(playerName);
            	team2ArrayAdapter.get(Defines.SPINNER_TEAM_USER_3_POSITION).add(playerName);
            	team2ArrayAdapter.get(Defines.SPINNER_TEAM_USER_4_POSITION).add(playerName);

            }});
		
	}

	@Override
	public void changeAllComboBoxBackgroundToValidColor() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Integer getQuestionSetTableSelectedRow() {		
		return createGameValues.questionSetSelectedRow;
	}
	public void setQuestionSetTableSelectedRow(int index){
		createGameValues.questionSetSelectedRow = index;
	}

	@Override
	public void RemoveQuestionnaire(Integer RowToRemove) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void shufflePlayerComboSelection(final String teamSide)
	{
		new Handler(teamActivity.getBaseContext().getMainLooper()).post(new Runnable() {
            public void run() {
        		if(teamSide == "Left")
        		{
        			try
        			{
                		LinearLayout layoutTeam = (LinearLayout)teamActivity.findViewById(R.id.wizard_team_1);
                		final Spinner UserSpinner2 = (Spinner)layoutTeam.findViewById(R.id.wizardteam_user_spinner_2);
                		final Spinner UserSpinner3 = (Spinner)layoutTeam.findViewById(R.id.wizardteam_user_spinner_3);
                		final Spinner UserSpinner4 = (Spinner)layoutTeam.findViewById(R.id.wizardteam_user_spinner_4);
                		
                		UserSpinner2.setSelection(1);
                		UserSpinner3.setSelection(2);
                		UserSpinner4.setSelection(3);
        			}
        			catch(Exception e)
        			{
        				e.printStackTrace();
        			}
        		}
        		else if(teamSide == "Right")
        		{
        			try
        			{
                		LinearLayout layoutTeam = (LinearLayout)teamActivity.findViewById(R.id.wizard_team_2);
                		final Spinner UserSpinner2 = (Spinner)layoutTeam.findViewById(R.id.wizardteam_user_spinner_2);
                		final Spinner UserSpinner3 = (Spinner)layoutTeam.findViewById(R.id.wizardteam_user_spinner_3);
                		final Spinner UserSpinner4 = (Spinner)layoutTeam.findViewById(R.id.wizardteam_user_spinner_4);
                		
                		UserSpinner2.setSelection(1);
                		UserSpinner3.setSelection(2);
                		UserSpinner4.setSelection(3);
        			}
        			catch(Exception e)
        			{
        				e.printStackTrace();
        			}
        		}
            }});
	}

	// LeftGroupTeam is also TopGroupTeam on Android.
	@Override
	public void updateLeftGroupTeam()
	{
		updateLeftGroupTeam(createGameValues.group1);
	}
	public void updateLeftGroupTeam(int position) {
		setLeftGroupName(position);
		CreateNewGameController gameController = CreateNewGameController.getInstance();
		final ArrayList<Team> teams = gameController.getModel().getTeamsInGroup(getLeftGroupName());
		
		if(teams != null && teams.isEmpty()) { 
			Group currentGroup = gameController.getModel().getGroupByName(getLeftGroupName()); 
			gameController.loadTeams(currentGroup); 
		}
		
		clearLeftTeamComboBox();
		clearLeftTeamPlayersNameComboBox();
		
		final List<String> sortedTeamNames = SortList.sortTeamList(teams);
	
		if(teams != null)
		{
			new Handler(teamActivity.getBaseContext().getMainLooper()).post(new Runnable() {
				public void run() {

					for (String teamName : sortedTeamNames)
						team1ArrayAdapter.get(Defines.SPINNER_TEAM_TEAM_POSITION).add(teamName);

					LinearLayout layoutTeam = (LinearLayout)teamActivity.findViewById(R.id.wizard_team_1);
					final Spinner TeamSpinner = (Spinner)layoutTeam.findViewById(R.id.wizardteam_team_spinner);

					if(teams.size() > 0)
						TeamSpinner.setSelection(Defines.ARRAY_POSITION_INIT);

				}});
		}
	}

//	LeftGroupTeam is also BottomGroupTeam on Android.
	@Override
	public void updateRightGroupTeam()
	{
		updateRightGroupTeam(createGameValues.group2);
	}	
	public void updateRightGroupTeam(int position) {
		setRightGroupName(position);
		
		CreateNewGameController gameController = CreateNewGameController.getInstance();
		final ArrayList<Team> teams = gameController.getModel().getTeamsInGroup(getRightGroupName());
		
		if(teams!=null && teams.isEmpty()) { 
			Group currentGroup = gameController.getModel().getGroupByName(getRightGroupName()); 
			gameController.loadTeams(currentGroup); 
		}

		clearRightTeamComboBox();
		clearRightTeamPlayersNameComboBox();
		
		final List<String> sortedTeamNames = SortList.sortTeamList(teams);
		
		if(teams != null)
		{
			new Handler(teamActivity.getBaseContext().getMainLooper()).post(new Runnable() {
				public void run() {

					for (String teamName : sortedTeamNames)
						team2ArrayAdapter.get(Defines.SPINNER_TEAM_TEAM_POSITION).add(teamName);

					LinearLayout layoutTeam = (LinearLayout)teamActivity.findViewById(R.id.wizard_team_2);
					final Spinner TeamSpinner = (Spinner)layoutTeam.findViewById(R.id.wizardteam_team_spinner);

					if(teams.size() > 0)
						TeamSpinner.setSelection(Defines.ARRAY_POSITION_INIT);

				}});
		}
	}
	
	@Override
	public void updateLeftGroupPlayers()
	{
		updateLeftGroupPlayers(createGameValues.team1);
	}
	public void updateLeftGroupPlayers(int position) {

		setLeftTeamName(position);
		
		if(position != 0)
		{			
			CreateNewGameController gameController = CreateNewGameController.getInstance();
			final ArrayList<User> users = gameController.getModel().getUsersInTeam(getLeftTeamName());
			
			if(users != null && users.isEmpty()) { 
				Team currentTeam = gameController.getModel().getTeamObjectInGroup(getLeftGroupName(), getLeftTeamName()); 
				gameController.loadUsers(currentTeam); 
			}
			
			clearLeftTeamPlayersNameComboBox();
			
			if(users != null)
			{
				final List<String> sortedUserNames = SortList.sortUserList(users);
				Iterator<String> it = sortedUserNames.iterator();
				
				while(it.hasNext())
				{
					String user = it.next();
					addPlayerNameToAllLeftTeamComboBox(user);
				}
			}	
		}
	}

	@Override
	public void updateRightGroupPlayers()
	{
		updateRightGroupPlayers(createGameValues.team2);
	}	
	public void updateRightGroupPlayers(int position) {
		
		setRightTeamName(position);
			
		if(position != 0)
		{
			
			CreateNewGameController gameController = CreateNewGameController.getInstance();
			final ArrayList<User> users = gameController.getModel().getUsersInTeam(getRightTeamName());
			if(users != null && users.isEmpty()) { 
				Team currentTeam = gameController.getModel().getTeamObjectInGroup(getRightGroupName(), getRightTeamName()); 
				gameController.loadUsers(currentTeam); 
			}
			
			clearRightTeamPlayersNameComboBox();
			
			if(users != null)
			{
				final List<String> sortedUserNames = SortList.sortUserList(users);
				Iterator<String> it = sortedUserNames.iterator();
				
				while(it.hasNext())
				{
					String user = it.next();
					addPlayerNameToAllRightTeamComboBox(user);
				}
			}	
		}
	}

	@Override
	public Object getPanelContent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addRowToTemplateTableModel(final Object[] data) {
		new Handler(questionSetActivity.getBaseContext().getMainLooper()).post(new Runnable() {
            public void run() {
            	adaptorTemplate.add(data);
            }});		
	}

	@Override
	public void setTextOfTxtFieldTemplateName(String t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getTextOfTxtFieldTemplateName() {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		return null;
	}

	public void setTeamActivity(Activity teamActivity) {
		this.teamActivity = teamActivity;
		
		openquizActivityManager.setCurrentActivity(teamActivity);
		createAdapters();
		addTeamSpinnerAdapter(teamActivity);
	}
	
	public Activity getTeamActivity() {
		return teamActivity;
	}

	public void setQuestionSetActivity(Activity questionSetActivity) {
		this.questionSetActivity = questionSetActivity;
		
		openquizActivityManager.setCurrentActivity(questionSetActivity);
		
		createQuestionSetAdaptors();		
	}
	
	public void setJoinGameActivity(Activity joinGameActivity) {
		this.joinGameActivity = joinGameActivity;
		
		openquizActivityManager.setCurrentActivity(joinGameActivity);
	}
	
	public void setCreateTemplateActivity(Activity createTemplateActivity) {
		this.createTemplateActivity = createTemplateActivity;
		
		openquizActivityManager.setCurrentActivity(createTemplateActivity);
		
		adaptorNewTemplate = ((WizardCreateTemplateAct) createTemplateActivity).getArrayAdapter();
		fillLastNewTemplateAdaptors();
	}

	private void createAdapters()
	{
		ArrayList<String> arrayList = null;
		ArrayAdapter<String> arrayAdapter = null;
		
		for(int n=0; n<Defines.SPINNER_TEAM_COUNT; n++)
		{
			arrayList = new ArrayList<String>();
			arrayList.add("Sélectionnez...");
			arrayAdapter = new ArrayAdapter<String>(teamActivity.getBaseContext(), android.R.layout.simple_spinner_item, arrayList);
			arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			team1ArrayAdapter.add(arrayAdapter);
			
			arrayList = new ArrayList<String>();
			arrayList.add("Sélectionnez...");
			arrayAdapter = new ArrayAdapter<String>(teamActivity.getBaseContext(), android.R.layout.simple_spinner_item, arrayList);
			arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			team2ArrayAdapter.add(arrayAdapter);
		}
	}
	
	@Override
	public void setQuestionSetTableModel(final ArrayList<Object[]> questionSetsData) {
		new Handler(questionSetActivity.getBaseContext().getMainLooper()).post(new Runnable() {
            public void run() {
            	adaptorQuestionSet.clear();
            	adaptorQuestionSet.addAll(questionSetsData);
            	questionSetActivity.findViewById(R.id.loading).setVisibility(View.INVISIBLE);
            }});
	}
	
	private void createQuestionSetAdaptors()
	{
		List<Object[]> values = new ArrayList<Object[]>();
		List<Object[]> values2 = new ArrayList<Object[]>();
		
		adaptorQuestionSet = new QuestionSetArrayAdapter(questionSetActivity.getBaseContext(), values);
		adaptorTemplate = new TemplateArrayAdapter(questionSetActivity.getBaseContext(), values2);
	}
	
	public void fillLastNewTemplateAdaptors()
	{
		ListView templateList = (ListView)createTemplateActivity.findViewById(R.id.template_list);
		int adaptorsIndex = templateList.getAdapter().getCount() - 1;
		
		TemplateLayout layout = (TemplateLayout)templateList.getAdapter().getItem(adaptorsIndex);
		
		loadCategories(layout);
		loadDifficulties(layout);
		loadType(layout);
		loadTarget(layout);
		registerSpinnerListeners(layout);
	}
	
	public List<TemplateSection> validateTemplateFields() throws TemplateValidationException
	{
		CreateNewGameController gameController = CreateNewGameController.getInstance();
		List<TemplateSection> templates = new ArrayList<TemplateSection>();
		
		TemplateLayout templateLayout;
		for(int n=0; n<adaptorNewTemplate.getCount(); n++)
		{
			TemplateSection section = new TemplateSection();
			templateLayout = adaptorNewTemplate.getItem(n);

			final Spinner spinnerCat = (Spinner)templateLayout.findViewById(R.id.wizardtemplate_category_spinner);
			final Spinner spinnerSubCat = (Spinner)templateLayout.findViewById(R.id.wizardtemplate_subcategory_spinner);
			String key = null;
			
			if(spinnerCat.getSelectedItemPosition() != 0)
			{
				String category = (String)spinnerCat.getAdapter().getItem(spinnerCat.getSelectedItemPosition());
				String subCategory = (String)spinnerSubCat.getAdapter().getItem(spinnerSubCat.getSelectedItemPosition());
				key = gameController.getModel().getCategoryIDFromTypeAndName(CategoryType.findByStringName(category), subCategory);
			}
			
			final Spinner spinnerType = (Spinner)templateLayout.findViewById(R.id.wizardtemplate_type_spinner);
			String type = (String)spinnerType.getAdapter().getItem(spinnerType.getSelectedItemPosition());
			
			final Spinner spinnerDif = (Spinner)templateLayout.findViewById(R.id.wizardtemplate_difficulty_spinner);
			String dif = (String)spinnerDif.getAdapter().getItem(spinnerDif.getSelectedItemPosition());
			
			final Spinner spinnerTarget = (Spinner)templateLayout.findViewById(R.id.wizardtemplate_target_spinner);
			String target = (String)spinnerTarget.getAdapter().getItem(spinnerTarget.getSelectedItemPosition());
			
			EditText textQuantity = (EditText)templateLayout.findViewById(R.id.txtQuantity);
			EditText textPoints = (EditText)templateLayout.findViewById(R.id.txtScore);
			EditText textDescription = (EditText)templateLayout.findViewById(R.id.txtDesc);
			TextView maxQuestions = (TextView)templateLayout.findViewById(R.id.wizardtemplate_quantity_max);
			
			Integer askQuestionsQuantity = 0;
			if(!textQuantity.getText().toString().isEmpty())
				askQuestionsQuantity = Integer.parseInt(textQuantity.getText().toString());
			
			Integer availableQuestionsQuantity = 0;
			if(!maxQuestions.getText().toString().isEmpty())
				availableQuestionsQuantity = Integer.parseInt(maxQuestions.getText().toString().substring(1));
			
			if(key == null && QuestionType.findByName(type) == null && Degree.findByName(dif) == null && 
					textQuantity.getText().toString().isEmpty() && textPoints.getText().toString().isEmpty() &&
					textDescription.getText().toString().isEmpty());
			else if(textQuantity.getText().toString().isEmpty() || textPoints.getText().toString().isEmpty() ||
					QuestionType.findByName(type) == null)
			{
				final int serie = n+1;
				throw new TemplateValidationException(createTemplateActivity.getResources().getString(R.string.message_template_info_popup_error) + " " + serie);
			}
			else if(askQuestionsQuantity > availableQuestionsQuantity)
			{
				final int serie = n+1;
				throw new TemplateValidationException(createTemplateActivity.getResources().getString(R.string.message_template_info_popup_questions_max) + " " + serie);
			}
			else
			{
				section.setCategory(key);
				section.setQuestionType(QuestionType.findByName(type));
				section.setDifficulty(Degree.findByName(dif));
				section.setNbQuestions(Integer.parseInt(textQuantity.getText().toString()));
				section.setPoints(Integer.parseInt(textPoints.getText().toString()));
				section.setDescription(textDescription.getText().toString());
				section.setQuestionTarget(QuestionTarget.findByName(target));
				
				templates.add(section);
			}
		}
		return templates;
	}
	
	public void saveTemplates(String templateName, List<TemplateSection> templates)
	{
		if(!templateName.isEmpty())
			this.templateName = templateName;
		
		CreateNewGameController gameController = CreateNewGameController.getInstance();
		
		boolean retCode = false;
		if(templates.size() != 0)
		{
			Template template = new Template();
			template.setName(this.templateName);
			template.setSectionList(templates);
			retCode = gameController.createTemplate(template);
		}
		
		if(retCode)
		{
			new Handler(createTemplateActivity.getBaseContext().getMainLooper()).post(new Runnable() {
				public void run() {
					createTemplateActivity.finish();
				}});
		}
	}
	
	private void loadCategories(TemplateLayout layout)
	{
		final Spinner spinnerCategory = (Spinner)layout.findViewById(R.id.wizardtemplate_category_spinner);
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add("Sélectionnez...");
		final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(createTemplateActivity.getBaseContext(), android.R.layout.simple_spinner_item, arrayList);
		arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		CreateNewGameController gameController = CreateNewGameController.getInstance();
		final List<Category> categories = gameController.getModel().getCategories();
		
		if(categories!=null)
		{
			if(categories.isEmpty())	
				gameController.loadCategories();
			
			// Mini hack puisque loadCategories() fait un model.setCategories()
			final List<Category> updatedCategories = gameController.getModel().getCategories();
			
			new Handler(createTemplateActivity.getBaseContext().getMainLooper()).post(new Runnable() {
				public void run() {

					spinnerCategory.setAdapter(arrayAdapter);
					
					for (Category cat : updatedCategories)
						arrayAdapter.add(CategoryType.toString(cat.getType()));
				}});
			
			loadSubCategories(layout);
		}
	}
	
	private void loadSubCategories(TemplateLayout layout)
	{
		final Spinner spinnerSubCategory = (Spinner)layout.findViewById(R.id.wizardtemplate_subcategory_spinner);
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add("Sélectionnez...");
		final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(createTemplateActivity.getBaseContext(), android.R.layout.simple_spinner_item, arrayList);
		arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			
		new Handler(createTemplateActivity.getBaseContext().getMainLooper()).post(new Runnable() {
			public void run() {
				spinnerSubCategory.setAdapter(arrayAdapter);
			}});
	}
	
	private void loadDifficulties(TemplateLayout layout)
	{
		final Spinner spinner = (Spinner)layout.findViewById(R.id.wizardtemplate_difficulty_spinner);
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add("Sélectionnez...");
		final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(createTemplateActivity.getBaseContext(), android.R.layout.simple_spinner_item, arrayList);
		arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		new Handler(createTemplateActivity.getBaseContext().getMainLooper()).post(new Runnable() {
			public void run() {

				spinner.setAdapter(arrayAdapter);
				
				for (Degree difficulty : Degree.values())
					arrayAdapter.add(Degree.toString(difficulty));
				
			}});
	}
	
	private void loadType(TemplateLayout layout)
	{
		final Spinner spinner = (Spinner)layout.findViewById(R.id.wizardtemplate_type_spinner);
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add("Sélectionnez...");
		final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(createTemplateActivity.getBaseContext(), android.R.layout.simple_spinner_item, arrayList);
		arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		
		new Handler(createTemplateActivity.getBaseContext().getMainLooper()).post(new Runnable() {
			public void run() {
				spinner.setAdapter(arrayAdapter);
				for (QuestionType type : QuestionType.values())
					arrayAdapter.add(type.name());
			}});
	}
	
	private void loadTarget(TemplateLayout layout)
	{
		final Spinner spinner = (Spinner)layout.findViewById(R.id.wizardtemplate_target_spinner);
		final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(createTemplateActivity.getBaseContext(), android.R.layout.simple_spinner_item);
		arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		
		new Handler(createTemplateActivity.getBaseContext().getMainLooper()).post(new Runnable() {
			public void run() {
				spinner.setAdapter(arrayAdapter);
				for (QuestionTarget target : QuestionTarget.values())
					arrayAdapter.add(target.name());
			}});
	}
	
	private void registerSpinnerListeners(final TemplateLayout layout)
	{
		final Spinner typeSpinner = (Spinner)layout.findViewById(R.id.wizardtemplate_type_spinner);
		final Spinner difficultySpinner = (Spinner)layout.findViewById(R.id.wizardtemplate_difficulty_spinner);
		final Spinner categorySpinner = (Spinner)layout.findViewById(R.id.wizardtemplate_category_spinner);
		final Spinner subCategorySpinner = (Spinner)layout.findViewById(R.id.wizardtemplate_subcategory_spinner);
		
		typeSpinner.setOnItemSelectedListener(spinnerSelected);
		difficultySpinner.setOnItemSelectedListener(spinnerSelected);
		categorySpinner.setOnItemSelectedListener(spinnerSelected);
		subCategorySpinner.setOnItemSelectedListener(spinnerSelected);
		
		categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
		{
			public void onItemSelected(AdapterView<?> parent, final View view, final int pos, long id)
			{
				if(view != null)
				{
					final Spinner spinnerCat = (Spinner)layout.findViewById(R.id.wizardtemplate_category_spinner);
					String category = (String)spinnerCat.getAdapter().getItem(spinnerCat.getSelectedItemPosition());
					
					List<String> subcat = CreateNewGameController.getInstance().getModel().getSubCategoriesFromCategoryType(CategoryType.findByStringName(category));
					
					final Spinner spinnerSubCategory = (Spinner)layout.findViewById(R.id.wizardtemplate_subcategory_spinner);
					
					ArrayList<String> arrayList = new ArrayList<String>();
					for (String subCategory : subcat) {
						arrayList.add(subCategory);
					}
					
					final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(teamActivity.getBaseContext(), android.R.layout.simple_spinner_item, arrayList);
					arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					
					spinnerSubCategory.setAdapter(arrayAdapter);
				}
			}
			public void onNothingSelected(AdapterView<?> parent)
			{
			}
		});
	}
	
	private AdapterView.OnItemSelectedListener spinnerSelected = new AdapterView.OnItemSelectedListener()
	{
		public void onItemSelected(AdapterView<?> parent, final View view, final int pos, long id)
		{
			if(view != null)
			{
				new QuestionCountAsyncTask(view).execute();
			}
		}
		public void onNothingSelected(AdapterView<?> parent)
		{
		}
	};
	
	public void addTeamSpinnerAdapter(final Activity teamActivity)
	{
		new Handler(teamActivity.getBaseContext().getMainLooper()).post(new Runnable() {
	        public void run() {
	    		// Team1
	    		LinearLayout layoutTeam = (LinearLayout)teamActivity.findViewById(R.id.wizard_team_1);
	    		
	    		Spinner groupSpinner = (Spinner)layoutTeam.findViewById(R.id.wizardteam_group_spinner);
	    		Spinner TeamSpinner = (Spinner)layoutTeam.findViewById(R.id.wizardteam_team_spinner);
	    		Spinner MemberSpinner1 = (Spinner)layoutTeam.findViewById(R.id.wizardteam_user_spinner_1);
	    		Spinner MemberSpinner2 = (Spinner)layoutTeam.findViewById(R.id.wizardteam_user_spinner_2);
	    		Spinner MemberSpinner3 = (Spinner)layoutTeam.findViewById(R.id.wizardteam_user_spinner_3);
	    		Spinner MemberSpinner4 = (Spinner)layoutTeam.findViewById(R.id.wizardteam_user_spinner_4);
	    		
	        	groupSpinner.setAdapter(team1ArrayAdapter.get(Defines.SPINNER_TEAM_GROUP_POSITION));
	        	TeamSpinner.setAdapter(team1ArrayAdapter.get(Defines.SPINNER_TEAM_TEAM_POSITION));
	        	MemberSpinner1.setAdapter(team1ArrayAdapter.get(Defines.SPINNER_TEAM_USER_1_POSITION));
	        	MemberSpinner2.setAdapter(team1ArrayAdapter.get(Defines.SPINNER_TEAM_USER_2_POSITION));
	        	MemberSpinner3.setAdapter(team1ArrayAdapter.get(Defines.SPINNER_TEAM_USER_3_POSITION));
	        	MemberSpinner4.setAdapter(team1ArrayAdapter.get(Defines.SPINNER_TEAM_USER_4_POSITION));
	        	
	        	// Team2
	        	layoutTeam = (LinearLayout)teamActivity.findViewById(R.id.wizard_team_2);
	    		groupSpinner = (Spinner)layoutTeam.findViewById(R.id.wizardteam_group_spinner);
	    		TeamSpinner = (Spinner)layoutTeam.findViewById(R.id.wizardteam_team_spinner);
	    		MemberSpinner1 = (Spinner)layoutTeam.findViewById(R.id.wizardteam_user_spinner_1);
	    		MemberSpinner2 = (Spinner)layoutTeam.findViewById(R.id.wizardteam_user_spinner_2);
	    		MemberSpinner3 = (Spinner)layoutTeam.findViewById(R.id.wizardteam_user_spinner_3);
	    		MemberSpinner4 = (Spinner)layoutTeam.findViewById(R.id.wizardteam_user_spinner_4);
	    		
	        	groupSpinner.setAdapter(team2ArrayAdapter.get(Defines.SPINNER_TEAM_GROUP_POSITION));
	        	TeamSpinner.setAdapter(team2ArrayAdapter.get(Defines.SPINNER_TEAM_TEAM_POSITION));
	        	MemberSpinner1.setAdapter(team2ArrayAdapter.get(Defines.SPINNER_TEAM_USER_1_POSITION));
	        	MemberSpinner2.setAdapter(team2ArrayAdapter.get(Defines.SPINNER_TEAM_USER_2_POSITION));
	        	MemberSpinner3.setAdapter(team2ArrayAdapter.get(Defines.SPINNER_TEAM_USER_3_POSITION));
	        	MemberSpinner4.setAdapter(team2ArrayAdapter.get(Defines.SPINNER_TEAM_USER_4_POSITION));
	        }});
	}

	@Override
	public void showWebServicePopup(final CreateNewGameAction action) {
		
		final Activity currentActivity = openquizActivityManager.getCurrentActivity();
		
		new Handler(currentActivity.getBaseContext().getMainLooper()).post(new Runnable() {
			public void run() {
				String title = currentActivity.getResources().getString(R.string.message_webservice_exception_title);
				String message = currentActivity.getResources().getString(R.string.message_webservice_exception_content);
				String positiveButtonMessage = currentActivity.getResources().getString(R.string.message_webservice_exception_retry_button);
				String negativeButtonMessage = currentActivity.getResources().getString(R.string.message_webservice_exception_close_button);
				
				
				AlertDialog.Builder alert = new AlertDialog.Builder(currentActivity);
				
				alert.setTitle(title);
			    alert.setMessage(message);
			    alert.setPositiveButton(positiveButtonMessage, new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 

			            new Thread(new Runnable() {
			                @Override
			                public void run() {
			                	
								if(CreateNewGameController.getInstance().handleRetry(currentActivity, action))
								{
									((CreateNewGameView)CreateNewGameController.getInstance().getView()).showWebServicePopup(action);
								}
								
			                }
			            }).start();
			        }
			     });
			    
			    alert.setNegativeButton(negativeButtonMessage, new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
			        	currentActivity.finish();
			        	return;
			        }
			     });
			    alert.show();   
			}});
	}
	
	public void clearCurrentActivity(Activity act)
	{
		Activity currActivity = openquizActivityManager.getCurrentActivity();
		if (currActivity != null && currActivity.equals(act))
			openquizActivityManager.setCurrentActivity(null);
	}
	
    public void setCurrentActivity(Activity act)
    {
    	openquizActivityManager.setCurrentActivity(act);
    }
    
	@Override
	public boolean isGenerateGameFromTemplate() {
		return createGameValues.isGeneraterFromTemplate;
	}
	public void setIsGenerateFromTemplate(boolean value) {
		createGameValues.isGeneraterFromTemplate = value;
	}

	@Override
	public Template getSelectedTemplate() {
		Template template = null;
		if (createGameValues.templateSelectedRow != -1){
			template = CreateNewGameController.getInstance().getTemplate(createGameValues.templateSelectedRow);
		}
		return template;
	}
	public void setSelectedTemplateIndex(int index){
		createGameValues.templateSelectedRow = index;
	}
	public int getSelectedTemplateIndex(){
		return createGameValues.templateSelectedRow;
	}

	@Override
	public boolean checkForGameName() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean checkForDuplicateGroupNTeam() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void addGameDataToTable(final Object[] gameData) {		
		new Handler(joinGameActivity.getBaseContext().getMainLooper()).post(new Runnable() {
			@SuppressWarnings("unchecked")
			public void run() {
				final ListView listView = (ListView) joinGameActivity.findViewById(R.id.games_list);
				
				((ArrayAdapter<String>)listView.getAdapter()).add(gameData[0].toString());
			}});
	}

	@Override
	public boolean isLoadGeneratedGame() {
		return createGameValues.isLoadGeneratedGame;
	}
	public void setIsLoadGeneratedGame(boolean value){
		createGameValues.isLoadGeneratedGame = value;
	}

	@Override
	public Game getSelectedGame() {
		Game game = null;
		if (createGameValues.joinGameSelectedRow != -1){
			game = CreateNewGameController.getInstance().getModel().getGameByIdx(createGameValues.joinGameSelectedRow);
		}
		return game;
	}
	public void setSelectedGameIndex(int index){
		createGameValues.joinGameSelectedRow = index;
	}

	@Override
	public void clearQuestionSetList() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearTemplateList() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearOpenGameList() {
		new Handler(joinGameActivity.getBaseContext().getMainLooper()).post(new Runnable() {
			@SuppressWarnings("unchecked")
			public void run() {
				final ListView listView = (ListView) joinGameActivity.findViewById(R.id.games_list);
				
				((ArrayAdapter<String>)listView.getAdapter()).clear();
			}});
	}

	@Override
	public void clearTeamsCompletly() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enableGameDataSelection() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disableGameDataSelection() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disablePlayButton() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enablePlayButton() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void downloadMediaFiles(List<String[]> mediaFilesPath) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addNewTemplateSection(CategoryType categoryType,
			String subCategory, Degree difficulty, QuestionType questionType,
			int value, int quantity, QuestionTarget questionTarget,
			String description) {
		// TODO Auto-generated method stub
		
	}
}
