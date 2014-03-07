package openquiz.website.pages;

import java.util.ArrayList;
import java.util.List;

import openquiz.website.Website_UI;
import openquiz.website.util.Messages;
import openquiz.website.util.Misc;
import openquiz.website.util.SessionConstants;
import openquiz.website.windows.GenericWindow;
import openquiz.website.windows.GenericWindow.WindowType;

import org.vaadin.navigator.Navigator;

import ca.openquiz.comms.RequestsWebService;
import ca.openquiz.comms.enums.Role;
import ca.openquiz.comms.model.Group;
import ca.openquiz.comms.model.GroupRole;
import ca.openquiz.comms.model.User;
import ca.openquiz.comms.response.KeyResponse;
import ca.openquiz.comms.response.UserResponse;

import com.vaadin.Application;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Select;
import com.vaadin.ui.Table;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.TextField;

public class ManageGroups extends Panel implements Navigator.View{
	private static final long serialVersionUID = 1034689058276380554L;

	private int PLAYERS_PER_PAGE = 50;
	
	String userKey;
	String authString;
	String sessionKey;

	Application application;
	
	GenericWindow addGroupWindow;
	GenericWindow addUserWindow;
	GenericWindow deleteGroupNameWindow;
	GenericWindow editGroupNameWindow;
	GenericWindow editRoleWindow;
	GenericWindow deleteUserFromGroupWindow;
	
	Button btnDel;
	Button btnPrevPage;
	Select selectGroup;
	String groupID;
	Table tableUsers;
	TextField txtFName;
	
	int currentPage = 1;
	
	public ManageGroups() {
		super(Messages.getString("All.manageGroups"));
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
		
		// Create a group
		Button btnAdd = new Button(Messages.getString("ManageGroups.createGroup"));
		btnAdd.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = -2490895796258508660L;

			public void buttonClick(ClickEvent event) 
			{
				List<Component> components = new ArrayList<Component>();
				final TextField groupName = new TextField(Messages.getString("All.name"));
				components.add(groupName);
				
				ClickListener clickListener = new Button.ClickListener() {
					private static final long serialVersionUID = 4761895567719696510L;

					@Override
					public void buttonClick(ClickEvent event) {
						if(groupName.getValue() == null || groupName.getValue().toString().isEmpty())
							return;
						
						Group group = new Group();
						group.setName(groupName.getValue().toString());
						KeyResponse success = RequestsWebService.addGroup(group, authString);
						
						if(success != null && success.isCompleted())
						{
							addGroupWindow.showNotification(Messages.getString("All.successProcessingRequest"));
							addGroupWindow.windowClose(null);
							ManageGroups.this.navigateTo(null);
						}	
						else
							addGroupWindow.showNotification(Messages.getString("All.errorProcessingRequest"));
						}
				};
				
				addGroupWindow = new GenericWindow(Messages.getString("All.editRole"), components, clickListener, WindowType.APPLY);
				application.getMainWindow().addWindow(addGroupWindow);
				
			}
		});
		
		this.addComponent(btnAdd);
		
		// Show the users list
		final List<Group> groupList = new ArrayList<Group>();
		selectGroup = new Select(Messages.getString("ManageGroups.selectAGroup"));
		selectGroup.setImmediate(true);
		selectGroup.setInvalidAllowed(false);
		
		selectGroup.addListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 2073292261763397158L;

			@Override
			public void valueChange(ValueChangeEvent event) {
		    	Object selectedGroup = selectGroup.getValue();
		    	btnDel.setEnabled(selectedGroup != null);
		    	
		    	groupID = null;
		    	
		    	if(selectedGroup != null)
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
		    	
		    	updateTable();
			}
		});
		
		UserResponse userResponse = RequestsWebService.getCurrentUserInfo(authString);
		
		// Add any group where the user is manager
		Group tmpGroup;
		for(GroupRole groupRole : Misc.nullSafe(userResponse.getGroupRoles()))
		{
			if(groupRole.getRole() == Role.Manager)
			{
				tmpGroup = RequestsWebService.getGroup(groupRole.getGroupKey());
				if(tmpGroup != null)
					groupList.add(tmpGroup);
			}
		}

		for (Group group : Misc.nullSafe(groupList))
			selectGroup.addItem(group.toString());
				
		// Delete the selected group
		btnDel = new Button(Messages.getString("All.delete"));
		btnDel.setEnabled(false);
		btnDel.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = 5970410949083745846L;

			public void buttonClick(ClickEvent event) {
		    	final Object selectedGroup = selectGroup.getValue();
		    	if(selectedGroup == null)
		    		return;

				List<Component> components = new ArrayList<Component>();
				Label groupName = new Label(Messages.getString("ManageGroups.deleteGroupConfirmation") + selectedGroup.toString());
				components.add(groupName);

		    	ClickListener clickListener = new ClickListener() {
					private static final long serialVersionUID = 5832425766457600812L;

					@Override
					public void buttonClick(ClickEvent event) {
						String groupID = null;
			    	
			    		for(Group group : Misc.nullSafe(groupList))
			    		{
			    			if(group.toString().equals(selectedGroup))
	    					{
			    				groupID = group.getKey();
			    				break;
	    					}
			    		}
			    		
			    		boolean success = RequestsWebService.deleteGroup(groupID, authString);
			    		if(success)
			    		{
			    			application.getMainWindow().showNotification(Messages.getString("All.successProcessingRequest"));
			    			deleteGroupNameWindow.windowClose(null);
			    			ManageGroups.this.navigateTo(null);
			    		}
			    		else
			    			application.getMainWindow().showNotification(Messages.getString("All.errorProcessingRequest"));
			    	}
				};
				
				deleteGroupNameWindow = new GenericWindow(Messages.getString("All.deleteGroup"), components, clickListener, WindowType.CONFIRM);
				application.getMainWindow().addWindow(deleteGroupNameWindow);
			}
		});
		
		HorizontalLayout horLayout = new HorizontalLayout();
		horLayout.addComponent(selectGroup);
		horLayout.addComponent(btnDel);
		horLayout.setComponentAlignment(btnDel, Alignment.BOTTOM_LEFT);

		this.addComponent(horLayout);
		
		createTable();
	}


	private void createTable()
	{		
		// Add the components to edit the group's name
		HorizontalLayout horLayoutEditGroupName = new HorizontalLayout();
		txtFName = new TextField(Messages.getString("All.name"));
		
		Button btnEditName = new Button(Messages.getString("All.edit"));
		btnEditName.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = 8410247644396884606L;

			public void buttonClick(ClickEvent event) {
				List<Component> components = new ArrayList<Component>();
				Label groupName = new Label(Messages.getString("ManageGroups.editGroupConfirmation") + txtFName.getValue().toString());
				components.add(groupName);
				
				ClickListener clickListener = new Button.ClickListener() {
					private static final long serialVersionUID = -4325356853094354296L;

					@Override
					public void buttonClick(ClickEvent event) {
						Group group = RequestsWebService.getGroup(groupID);
						boolean success = false;
						
						if(group != null)
						{
							group.setName(txtFName.getValue().toString());
							success = RequestsWebService.editGroup(group, authString);
						}
						
						if(success)
						{
							application.getMainWindow().showNotification(Messages.getString("All.successProcessingRequest"));
							editGroupNameWindow.windowClose(null);
						}
						else
							application.getMainWindow().showNotification(Messages.getString("All.errorProcessingRequest"));
					}
				};
				editGroupNameWindow = new GenericWindow(Messages.getString("All.addGroup"), components, clickListener, WindowType.CONFIRM);
				application.getMainWindow().addWindow(editGroupNameWindow);
		    }
		});
		
		horLayoutEditGroupName.addComponent(txtFName);
		horLayoutEditGroupName.addComponent(btnEditName);
		horLayoutEditGroupName.setComponentAlignment(btnEditName, Alignment.BOTTOM_LEFT);
		this.addComponent(horLayoutEditGroupName);
		
		tableUsers = new Table(Messages.getString("All.group"));
		tableUsers.addContainerProperty(Messages.getString("All.firstName"), String.class, null);
		tableUsers.addContainerProperty(Messages.getString("All.lastName"), String.class, null);
		tableUsers.addContainerProperty(Messages.getString("All.emailAddress"), String.class, null);
		tableUsers.addContainerProperty(Messages.getString("All.role"), String.class, null);

		tableUsers.setSelectable(true);
		this.addComponent(tableUsers);
		tableUsers.setWidth("100%");
		
		Button btnAdd= new Button(Messages.getString("All.addUser"));
		btnAdd.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = -38570592560872196L;

			public void buttonClick(ClickEvent event) {

				List<Component> components = new ArrayList<Component>();
				final TextField userEmail = new TextField(Messages.getString("All.emailAddress"));
				final Select selectRole = new Select(Messages.getString("All.role"));
				for(Role role : Role.values())
				{
					selectRole.addItem(role);
				}
				components.add(userEmail);
				components.add(selectRole);
				
				ClickListener clickListener = new Button.ClickListener() {
					private static final long serialVersionUID = -1240433300056781435L;

					@Override
					public void buttonClick(ClickEvent event) {
						if(userEmail.getValue() == null || selectRole.getValue() == null)
							return;
						
						User user = RequestsWebService.getUserByEmail(userEmail.getValue().toString());
						if(user == null)
						{
							addUserWindow.showNotification(Messages.getString("All.invalidFields"));
							return;
						}
						
						GroupRole groupRole = new GroupRole();
						groupRole.setUserKey(user.getKey());
						groupRole.setGroupKey(groupID);
						groupRole.setRole(Role.valueOf(selectRole.getValue().toString()));

						KeyResponse resp = RequestsWebService.addGroupRole(groupRole, authString);
						
						if(resp.getError() == null || resp.getError().isEmpty())
						{
							addUserWindow.showNotification(Messages.getString("All.successProcessingRequest"));
							addUserWindow.windowClose(null);
							ManageGroups.this.updateTable();
						}	
						else
							addUserWindow.showNotification(Messages.getString("All.errorProcessingRequest"));
					}
				};
				
				addUserWindow = new GenericWindow(Messages.getString("All.addUser"), components, clickListener, WindowType.APPLY);
				application.getMainWindow().addWindow(addUserWindow);
		    }
		});

		Button btnEdit = new Button(Messages.getString("All.editRole"));
		btnEdit.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = 7280717616368368626L;

			public void buttonClick(ClickEvent event) {

				Object objectSelected = tableUsers.getValue();
				
				if(objectSelected == null)
					return;
				
				List<Component> components = new ArrayList<Component>();
				final Select selectRole = new Select(Messages.getString("All.role"));
				for(Role role : Role.values())
				{
					selectRole.addItem(role);
				}
				Item itm = tableUsers.getItem(objectSelected);
				Object obj = itm.getItemProperty(Messages.getString("All.role"));
				if(obj != null)
					selectRole.setValue(Role.valueOf(obj.toString()));
				components.add(selectRole);
				
				ClickListener clickListener = new Button.ClickListener() {
					private static final long serialVersionUID = 4761895567719696510L;

					@Override
					public void buttonClick(ClickEvent event) {

						if(tableUsers.getValue() == null)
						{
							application.getMainWindow().showNotification(Messages.getString("All.invalidFields"));
							return;	
						}
						
						String userID = tableUsers.getValue().toString();
						
						GroupRole groupRole = new GroupRole();
						groupRole.setUserKey(userID);
						groupRole.setGroupKey(groupID);
						groupRole.setRole(Role.valueOf(selectRole.getValue().toString()));
						
						boolean success = RequestsWebService.editGroupRole(groupRole, authString);
						
						if(success)
						{
							addGroupWindow.showNotification(Messages.getString("All.successProcessingRequest"));
							addGroupWindow.windowClose(null);
							ManageGroups.this.updateTable();
						}	
						else
							addGroupWindow.showNotification(Messages.getString("All.errorProcessingRequest"));
						}
				};
				
				addGroupWindow = new GenericWindow(Messages.getString("ManageGroups.createGroup"), components, clickListener, WindowType.APPLY);
				application.getMainWindow().addWindow(addGroupWindow);				
		    }
		});
		
		Button btnDel = new Button(Messages.getString("All.delete"));
		btnDel.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = -6184345771697586928L;

			public void buttonClick(ClickEvent event) {	    		
				Object objectSelected = tableUsers.getValue();
				Item itemSelected = tableUsers.getItem(objectSelected);
				
				if(itemSelected == null)
					return;

				List<Component> components = new ArrayList<Component>();
				Label groupName = new Label(Messages.getString("ManageGroups.deleteUserFromGroupConfirmation") + itemSelected.getItemProperty(Messages.getString("All.email")));
				components.add(groupName);

		    	ClickListener clickListener = new ClickListener() {
					private static final long serialVersionUID = 5832425766457600812L;

					@Override
					public void buttonClick(ClickEvent event) {
						
						if(tableUsers.getValue() == null)
						{
							application.getMainWindow().showNotification(Messages.getString("All.invalidFields"));
							return;	
						}
						
						String userID = tableUsers.getValue().toString();
			    		
			    		boolean success = RequestsWebService.deleteUserFromGroup(userID, groupID, authString);
			    		if(success)
			    		{
			    			application.getMainWindow().showNotification(Messages.getString("All.successProcessingRequest"));
			    			deleteUserFromGroupWindow.windowClose(null);
			    			updateTable();
			    		}
			    		else
			    			application.getMainWindow().showNotification(Messages.getString("All.errorProcessingRequest"));
					}
				};
				
				deleteUserFromGroupWindow = new GenericWindow(Messages.getString("ManageGroups.deleteUserFromGroup"), components, clickListener, WindowType.CONFIRM);
				application.getMainWindow().addWindow(deleteUserFromGroupWindow);
		    }
		});
		
		// Next and previous page
		btnPrevPage = new Button(Messages.getString("All.prevPage"));
		btnPrevPage.setEnabled(false);
		btnPrevPage.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = -3767562099750326514L;

			public void buttonClick(ClickEvent event) {
				--currentPage;
				ManageGroups.this.updateTable();
		    }
		});
		
		Button btnNextPage = new Button(Messages.getString("All.nextPage"));
		btnNextPage.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = -3767562099750326514L;

			public void buttonClick(ClickEvent event) {
				++currentPage;
				ManageGroups.this.updateTable();
		    }
		});

		HorizontalLayout innerHorLayout1 = new HorizontalLayout();
		innerHorLayout1.addComponent(btnAdd);
		innerHorLayout1.addComponent(btnEdit);
		innerHorLayout1.addComponent(btnDel);
		
		HorizontalLayout innerHorLayout2 = new HorizontalLayout();
		innerHorLayout2.addComponent(btnPrevPage);
		innerHorLayout2.addComponent(btnNextPage);
		
		HorizontalLayout horLayout = new HorizontalLayout();
		horLayout.addComponent(innerHorLayout1);
		horLayout.addComponent(innerHorLayout2);
		horLayout.setWidth("100%");
		horLayout.setComponentAlignment(innerHorLayout1, Alignment.MIDDLE_LEFT);
		horLayout.setComponentAlignment(innerHorLayout2, Alignment.MIDDLE_RIGHT);
		this.addComponent(horLayout);
		
		this.addComponent(horLayout);
	}
	
	private void updateTable()
	{
		if(groupID == null)
		{
			txtFName.setValue("");
			tableUsers.removeAllItems();
			return;
		}
		
		txtFName.setValue(selectGroup.getValue().toString());

		List<UserResponse> userResponses = RequestsWebService.getDetailedUserList(groupID, currentPage, PLAYERS_PER_PAGE, authString);

		if((userResponses == null || userResponses.isEmpty()) && currentPage > 1)	
		{
			--currentPage;
			application.getMainWindow().showNotification(Messages.getString("All.notAvailable"));
			return;
		}

		tableUsers.removeAllItems();
		
		Role role = Role.Player;
		for(UserResponse user : Misc.nullSafe(userResponses))
		{
			for(GroupRole groupRole : Misc.nullSafe(user.getGroupRoles()))
			{
				if(groupRole.getGroupKey().equals(groupID))
				{
					role = groupRole.getRole();
					break;
				}
			}
			
			tableUsers.addItem(new Object[] {user.getFirstName(), user.getLastName(), user.getEmail(), role.toString()}, user.getKey());
		}
		
		btnPrevPage.setEnabled(currentPage > 1);
	}
	
	@Override
	public String getWarningForNavigatingFrom() 
	{
		return null;
	}
}
