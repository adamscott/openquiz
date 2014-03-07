package ca.openquiz.mobile.createNewGame;

import java.util.List;

import android.app.Activity;
import applicationTools.LoginManager;
import ca.openquiz.comms.RequestsWebService;
import ca.openquiz.comms.model.Template;
import ca.openquiz.comms.model.TemplateSection;
import ca.openquiz.mobile.util.Defines;
import creationGame.CreateNewGameModel;

public class CreateNewGameTask {

	private CreateNewGameController createNewGameController = CreateNewGameController.getInstance();  

	public Runnable initTeamAct(final Activity act)
	{
		Runnable init = new Runnable() {

			public void run() {
				createNewGameController.setView(new CreateNewGameView());   
				((CreateNewGameView)createNewGameController.getView()).setTeamActivity(act);

				createNewGameController.loadGroups();
			}
		};

		return init;
	}

	public Runnable initQuestionSetAct(final Activity act)
	{
		Runnable init = new Runnable() {

			public void run() {

				createNewGameController = CreateNewGameController.getInstance();

				((CreateNewGameView)createNewGameController.getView()).setQuestionSetActivity(act);
				createNewGameController.loadQuestionSets();
				createNewGameController.loadTemplates();
			}
		};

		return init;
	}
	
	public Runnable refreshQuestionSetList()
	{
		Runnable init = new Runnable() {

			public void run() {
				CreateNewGameController.getInstance().loadQuestionSets();
			}
		};

		return init;
	}
	
	public Runnable initJoinGameAct(final Activity act)
	{
		Runnable init = new Runnable() {

			public void run() {

				createNewGameController = CreateNewGameController.getInstance();

				((CreateNewGameView)createNewGameController.getView()).setJoinGameActivity(act);
			}
		};

		return init;
	}
	
	public Runnable initTemplateAct(final Activity act)
	{
		Runnable init = new Runnable() {

			public void run() {
				if(createNewGameController == null)
					createNewGameController = CreateNewGameController.getInstance();
				
				((CreateNewGameView)createNewGameController.getView()).setCreateTemplateActivity(act);
			}
		};

		return init;
	}

	public Runnable updateLeftGroup(final int pos)
	{
		Runnable run = new Runnable() {

			public void run() {
				((CreateNewGameView)createNewGameController.getView()).updateLeftGroupTeam(pos);
			}
		};

		return run;
	}

	public Runnable updateRightGroup(final int pos)
	{
		Runnable run = new Runnable() {

			public void run() {
				((CreateNewGameView)createNewGameController.getView()).updateRightGroupTeam(pos);
			}
		};

		return run;
	}

	public Runnable checkForDuplicateNameInSameGroup()
	{
		Runnable run = new Runnable() {

			public void run() {
				((CreateNewGameView)createNewGameController.getView()).resetNameColor();
				((CreateNewGameView)createNewGameController.getView()).checkForDuplicateNameInSameGroup();
			}
		};

		return run;
	}
	
	public Runnable updateLeftTeam(final int pos)
	{
		Runnable run = new Runnable() {

			public void run() {
				((CreateNewGameView)createNewGameController.getView()).updateLeftGroupPlayers(pos);
			}
		};

		return run;
	}

	public Runnable updateRightTeam(final int pos)
	{
		Runnable run = new Runnable() {

			public void run() {
				((CreateNewGameView)createNewGameController.getView()).updateRightGroupPlayers(pos);
			}
		};

		return run;
	}
	
	public Runnable clearLeftTeamComboBox()
	{
		Runnable run = new Runnable() {

			public void run() {
				((CreateNewGameView)createNewGameController.getView()).clearLeftTeamComboBox();
			}
		};

		return run;
	}
	
	public Runnable clearRightTeamComboBox()
	{
		Runnable run = new Runnable() {

			public void run() {
				((CreateNewGameView)createNewGameController.getView()).clearRightTeamComboBox();
			}
		};

		return run;
	}
	
	public Runnable clearRightTeamPlayerNames()
	{
		Runnable run = new Runnable() {

			public void run() {
				((CreateNewGameView)createNewGameController.getView()).clearRightTeamPlayersNameComboBox();
			}
		};

		return run;
	}

	public Runnable getLeftGroupName()
	{
		Runnable run = new Runnable() {

			public void run() {
				((CreateNewGameView)createNewGameController.getView()).getLeftGroupName();
			}
		};

		return run;
	}
	
	public Runnable getRightGroupName()
	{
		Runnable run = new Runnable() {

			public void run() {
				((CreateNewGameView)createNewGameController.getView()).getRightGroupName();
			}
		};

		return run;
	}
	
	public Runnable clearLeftTeamPlayerNames()
	{
		Runnable run = new Runnable() {

			public void run() {
				((CreateNewGameView)createNewGameController.getView()).clearLeftTeamPlayersNameComboBox();
			}
		};

		return run;
	}

	public Runnable updateUser(final int groupId, final int userPos, final int selectedUserPos)
	{
		Runnable run = new Runnable() {

			public void run() {
				
				CreateNewGameView view = ((CreateNewGameView)createNewGameController.getView());
				
				if(groupId == Defines.GROUP_POSITION_1) {
					switch (userPos) {
					case 1:
						view.setLeftTeamPlayer1(selectedUserPos);
						break;
					case 2:
						view.setLeftTeamPlayer2(selectedUserPos);	
						break;
					case 3:
						view.setLeftTeamPlayer3(selectedUserPos);	
						break;
					case 4:
						view.setLeftTeamPlayer4(selectedUserPos);
						break;

					default:
						break;
					}
				}
				else if(groupId == Defines.GROUP_POSITION_2) {
					switch (userPos) {
					case 1:
						view.setRightTeamPlayer1(selectedUserPos);
						break;
					case 2:
						view.setRightTeamPlayer2(selectedUserPos);
						break;
					case 3:
						view.setRightTeamPlayer3(selectedUserPos);
						break;
					case 4:
						view.setRightTeamPlayer4(selectedUserPos);
						break;

					default:
						break;
					}
				}

				view.resetNameColor();
				
				if(view.checkIfSameGroupSelected() && view.checkIfSameTeamSelected())
					view.checkForDuplicateNameBetweenGroups();
				else
					view.checkForDuplicateNameInSameGroup();
			}
		};

		return run;
	}
	
	public Runnable verifyDuplicateName()
	{
		Runnable run = new Runnable() {

			public void run() {
				((CreateNewGameView)createNewGameController.getView()).validateNewGame();
			}
		};

		return run;
	}

	public Runnable setCurrentActivity(final Activity act)
	{
		Runnable run = new Runnable() {

			public void run() {
				((CreateNewGameView)createNewGameController.getView()).setCurrentActivity(act);
			}
		};

		return run;
	}

	public Runnable clearCurrentActivity(final Activity act)
	{
		Runnable run = new Runnable() {

			public void run() {
				((CreateNewGameView)createNewGameController.getView()).clearCurrentActivity(act);
			}
		};

		return run;
	}

	public Runnable playGame()
	{
		Runnable run = new Runnable() {
			
			public void run() {
				createNewGameController.playGame();
			}
		};
		
		return run;
	}
	
	public Runnable logout()
	{
		Runnable run = new Runnable() {

			public void run() {
				LoginManager.getInstance().logout();
			}
		};

		return run;
	}
	
	public Runnable refreshGames()
	{
		Runnable run = new Runnable() {

			public void run() {
				((CreateNewGameView)createNewGameController.getView()).clearOpenGameList();
				((CreateNewGameModel)createNewGameController.getModel()).clearGameList();
				createNewGameController.loadOpenGames();
			}
		};

		return run;
	}
	
//	/******************************************************************************************************************
//	 ********************************** New Template implementation ***************************************************
//	 ******************************************************************************************************************/
	public Runnable fillLastNewTemplateAdaptors()
	{
		Runnable run = new Runnable() {

			public void run() {
				((CreateNewGameView)createNewGameController.getView()).fillLastNewTemplateAdaptors();
			}
		};
		
		return run;
	}
	
	public Runnable saveTemplates(final String templateName, final List<TemplateSection> templates)
	{
		Runnable run = new Runnable() {

			public void run() {
				((CreateNewGameView)createNewGameController.getView()).saveTemplates(templateName, templates);
			}
		};

		return run;
	}
	
	public Runnable generateQuestionSet(final Template template)
	{
		Runnable run = new Runnable() {

			public void run() {
				RequestsWebService.generateQuestionSet(template, LoginManager.getInstance().getAuthorization(), true);
			}
		};

		return run;
	}
}
