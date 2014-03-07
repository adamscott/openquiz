package openquiz.website.pages;

import java.util.ArrayList;
import java.util.List;

import openquiz.website.Website_UI;
import openquiz.website.util.Messages;
import openquiz.website.util.Misc;
import openquiz.website.util.SessionConstants;

import org.vaadin.navigator.Navigator;

import ca.openquiz.comms.RequestsWebService;
import ca.openquiz.comms.enums.Role;
import ca.openquiz.comms.model.Group;
import ca.openquiz.comms.model.GroupRole;
import ca.openquiz.comms.model.QuestionSet;
import ca.openquiz.comms.response.UserResponse;

import com.vaadin.Application;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Select;
import com.vaadin.ui.Table;
import com.vaadin.ui.Button.ClickEvent;

public class ManageQuestionSets extends Panel implements Navigator.View{
	private static final long serialVersionUID = 2255405552852100120L;
	
	String userKey;
	String authString;
	String sessionKey;

	Application application;
	Button btnEdit;
	Table tableQuestionSets; 
	
	public ManageQuestionSets() {
		super(Messages.getString("All.manageQuestionSets"));
		super.setSizeFull();
		}

	@Override
	public void init(Navigator navigator, Application application) {
		this.application = application;
		if(application != null)
		{
			userKey = (String)((Website_UI)application).getSession().getAttribute(SessionConstants.SESSION_USER_KEY);
			authString = (String)((Website_UI)application).getSession().getAttribute(SessionConstants.SESSION_AUTHORIZATION_STRING);
			sessionKey = (String)((Website_UI)application).getSession().getAttribute(SessionConstants.SESSION_WS_SESSION_KEY);
		}
	}

	@Override
	public void navigateTo(String requestedDataId) {
		this.removeAllComponents();

		// Show the users list
		final List<Group> groupList = new ArrayList<Group> ();
		final Select selectGroup = new Select(Messages.getString("ManageGroups.selectAGroup"));
		selectGroup.setInvalidAllowed(false);
		selectGroup.setImmediate(true);
		
		selectGroup.addListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = -7466690869127955818L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				if(btnEdit != null)
					btnEdit.setEnabled(selectGroup.getValue() != null);
			}
		});
		
		UserResponse userResponse = RequestsWebService.getCurrentUserInfo(authString);
		
		// If the user is an admin, he can modify any group he wants, even the Public group
		if(userResponse != null && userResponse.isAdmin())
			selectGroup.addItem(Messages.getString("All.public"));
		
		// Add any group where the user is manager
		Group tmpGroup;
		for(GroupRole groupRole : Misc.nullSafe(userResponse.getGroupRoles()))
		{
			if(groupRole.getRole() == Role.QuestionManager || groupRole.getRole() == Role.Manager)
			{
				tmpGroup = RequestsWebService.getGroup(groupRole.getGroupKey());
				if(tmpGroup != null)
					groupList.add(tmpGroup);
			}
		}

		for (Group group : Misc.nullSafe(groupList)) 
			selectGroup.addItem(group.toString());
		
		this.addComponent(selectGroup);
		
		// To return to the group selection
		final Button btnReturn = new Button(Messages.getString("All.return"));
	
		btnReturn.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = 654365517938145209L;

			public void buttonClick(ClickEvent event) {
		    	navigateTo(null);
		    }
		});
		
		// Go to the group edit page
		btnEdit = new Button(Messages.getString("All.edit"));
		btnEdit.setEnabled(false);
		btnEdit.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = 7273331592371053666L;

			public void buttonClick(ClickEvent event) {				
		    	Object selectedGroup = selectGroup.getValue();
		    	if(selectedGroup == null)
		    		return;
		    	
		    	ManageQuestionSets.this.removeAllComponents();
		    	ManageQuestionSets.this.addComponent(btnReturn);
		    	
		    	String groupID = null;
		    	
		    	if(selectedGroup.toString().equals(Messages.getString("All.public")))
		    		groupID = "";
		    	else
		    	{
		    		for(Group group : Misc.nullSafe(groupList))
		    		{
		    			if(group.toString().equals(selectedGroup.toString()))
    					{
		    				groupID = group.getKey();
		    				break;
    					}
		    		}
		    	}
		    	
		    	displayModifyQuestionSet(groupID);
		    }
		});

		this.addComponent(btnEdit);		
	}
	
	private void displayModifyQuestionSet(final String groupID)
	{		
		tableQuestionSets = new Table(Messages.getString("All.questionSet"));
		tableQuestionSets.addContainerProperty(Messages.getString("All.name"), String.class, null);
		tableQuestionSets.addContainerProperty(Messages.getString("All.availableDate"), String.class, null);
		tableQuestionSets.addContainerProperty(Messages.getString("ManageQuestionSets.questionSetSectionCount"), Integer.class, null);		

		Button btnAdd= new Button(Messages.getString("MainWebsiteWindow.adQuestionSet"));
		btnAdd.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = -3903562855838595247L;

			public void buttonClick(ClickEvent event) {
				// TODO 44 THIS DOES NOTHING
		    }
		});
		
		tableQuestionSets.setSelectable(true);
		this.addComponent(tableQuestionSets);
		tableQuestionSets.setWidth("100%");

		Button btnEdit = new Button(Messages.getString("ManageQuestionSets.editQuestionSet"));
		btnEdit.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = -926681634162076743L;

			public void buttonClick(ClickEvent event) {
				// TODO 44 THIS DOES NOTHING
		    }
		});
		
		Button btnDel = new Button(Messages.getString("All.delete"));
		btnDel.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = 3626590735116747570L;

			public void buttonClick(ClickEvent event) {
				// TODO 44 THIS DOES NOTHING
		    }
		});
		
		HorizontalLayout horLayout = new HorizontalLayout();
		horLayout.addComponent(btnEdit);
		horLayout.addComponent(btnDel);
		
		this.addComponent(horLayout);
	}
	
	private void updateTable()
	{
		// TODO 44 get les QuestionSets du groupe seulement?
		List<QuestionSet> questionSetList = RequestsWebService.getQuestionSets();

		if(questionSetList == null)
		{
			application.getMainWindow().showNotification(Messages.getString("All.errorProcessingRequest"));
			return;
		}
		
		for(QuestionSet questionSet : Misc.nullSafe(questionSetList))
			tableQuestionSets.addItem(new Object[] {questionSet.getName(), questionSet.getAvailableDate(), questionSet.getSectionList().size()}, questionSet.getKey());
	}

	@Override
	public String getWarningForNavigatingFrom() {
		// TODO Auto-generated method stub
		return null;
	}

}
