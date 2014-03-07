package ca.openquiz.mobile.createNewGame;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import applicationTools.Defines.CreateNewGameAction;
import ca.openquiz.comms.RequestsWebService;
import ca.openquiz.comms.model.Group;
import ca.openquiz.comms.model.Template;
import ca.openquiz.comms.model.TemplateSection;
import ca.openquiz.mobile.util.SortList;
import ca.openquiz.mobile.wizardTemplate.TemplateValidationException;
import creationGame.ICreateNewGameView;

public class CreateNewGameController extends creationGame.CreateNewGameController
{
	private static CreateNewGameController instance = null;
	private static final int[] sleepMsTimeArray = {500,1000,2000,4000,6000};
	private int sleepArrayIndex = 0;
	
	private CreateNewGameController()
	{
		super();
	}
	
	public static CreateNewGameController getInstance()
	{
		if(instance == null)
			instance = new CreateNewGameController();

		return instance;
	}
	
	public boolean handleRetry(Activity currentActivity, CreateNewGameAction action)
	{
		sleepArrayIndex = 0;

		while(!isInternetConnectionAvailable(currentActivity))
		{
			try {
				Thread.sleep(sleepMsTimeArray[sleepArrayIndex]);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sleepArrayIndex++;

			if(sleepArrayIndex <= sleepMsTimeArray.length)
			{
				return true;
			}
		}

		resumeCreateNewGameController(action);
		return false;
	}

	private boolean isInternetConnectionAvailable(Activity currentActivity)
	{
		ConnectivityManager connectivityManager = (ConnectivityManager) currentActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
	
	@Override
	public void setView(ICreateNewGameView view){
		this.view = view;
	}
	
	private void resumeCreateNewGameController(CreateNewGameAction action)
	{
		switch(action)
		{
		case LOADGROUP:
			this.loadGroups();
			break;
		case LOADTEAMS:
			view.updateLeftGroupTeam();
			view.updateRightGroupTeam();
			break;
		case LOADUSERS:
			view.updateLeftGroupPlayers();
			view.updateRightGroupPlayers();
			break;
		case LOADCATEGORIES:
//			this.loadCategories();
			((CreateNewGameView)view).fillLastNewTemplateAdaptors();
			break;
		case CREATE_TEMPLATES:
			//TODO this must be tested
			try {
				List<TemplateSection> templateSections = ((CreateNewGameView)view).validateTemplateFields();
				((CreateNewGameView)view).saveTemplates("", templateSections);
			} catch (TemplateValidationException e) {
				e.getStackTrace();
			}
			break;
		default:
			break;
			
		}
	}
	
	@Override
	public Boolean validateData() {
		view.changeAllComboBoxBackgroundToValidColor();
		boolean isValidationOk = true;

		if(view.checkForDuplicateNameInSameGroup()){
			isValidationOk = false;
		}
		return isValidationOk;
	}
	
	/*
	 * This was overrided to sort the Group name before adding them to the arrayAdapter.
	 * 
	 * (non-Javadoc)
	 * @see creationGame.CreateNewGameController#loadGroups()
	 */
	@Override
	public void loadGroups() {

		List<Group> groups = RequestsWebService.getGroups();
		if(groups != null)
		{
			for(Group group : groups){
				if(group.getName() != null){
					model.initGroup(group);
				}
			}
			
			List<String> sortedGroupName = SortList.sortGroupList(groups);
			for(String group : sortedGroupName){
				if(group != null){
					view.addGroupName(group);
				}
			}
		}
		else
			view.showWebServicePopup(CreateNewGameAction.LOADGROUP);
	}
	
	public Template getTemplate(int index)
	{
		return super.getModel().getTemplate(index);
	}
}
