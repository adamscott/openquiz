package openquiz.website.pages;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.dialogs.ConfirmDialog;
import org.vaadin.navigator.Navigator;

import openquiz.website.Website_UI;
import openquiz.website.util.Messages;
import openquiz.website.util.SessionConstants;
import openquiz.website.util.WebServiceCache;
import openquiz.website.windows.EditTeamWindow;
import openquiz.website.windows.ListMembersWindow;
import openquiz.website.windows.UserSelectWindow;
import ca.openquiz.comms.RequestsWebService;
import ca.openquiz.comms.enums.Role;
import ca.openquiz.comms.model.Group;
import ca.openquiz.comms.model.GroupRole;
import ca.openquiz.comms.model.Team;
import ca.openquiz.comms.model.User;
import ca.openquiz.comms.response.UserResponse;

import com.vaadin.Application;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.Button.ClickEvent;

public class ManageTeams extends com.vaadin.ui.Panel implements Navigator.View{
	
	private static final long serialVersionUID = 3367104706995950893L;
	
	private String userKey;
	private String authorization;
	//private User user;

	public ManageTeams(){
		super(Messages.getString("All.manageTeams")); //$NON-NLS-1$
		super.setSizeFull();
	}

	@Override
	public void init(Navigator navigator, Application application) {
		
	}

	@Override
	public void navigateTo(String requestedDataId) {
		buildInitialUI();
	}
	
	public void buildInitialUI(){
		this.removeAllComponents();
		if(userKey == null){
			userKey = (String) ((Website_UI)this.getApplication()).getSession().getAttribute(SessionConstants.SESSION_USER_KEY);
			
			if(userKey == null){
				ManageTeams.this.addComponent(new Label(Messages.getString("All.pleaseLogin"))); //$NON-NLS-1$
				return;
			}
			authorization = (String) ((Website_UI)this.getApplication()).getSession().getAttribute(SessionConstants.SESSION_AUTHORIZATION_STRING);
		}
		
		Button btnTeamMembers = new Button(Messages.getString("ManageTeams.viewTeamUserIsMember")); //$NON-NLS-1$
		this.addComponent(btnTeamMembers);
		btnTeamMembers.addListener(new ClickListener() {
			
			//Load the UI with read only access
			private static final long serialVersionUID = -6589604128301296893L;

			@Override
			public void buttonClick(ClickEvent event) {
				User user = RequestsWebService.getUser(userKey);
				buildMainUI(user.getTeams(), null);
			}
		});
		
		Button btnTeamAdmin = new Button(Messages.getString("ManageTeams.manageTeamInGroup")); //$NON-NLS-1$
		btnTeamAdmin.addListener(new ClickListener() {
			
			//Load the UI with admin access
			private static final long serialVersionUID = -3424149055763215138L;

			@Override
			public void buttonClick(ClickEvent event) {
				UserResponse response = RequestsWebService.getCurrentUserInfo(authorization);
				List<GroupRole> groupRoles = response.getGroupRoles();
				List<String> groupKeys = new ArrayList<String>();
				for(GroupRole role : groupRoles){
					if(role.getRole() == Role.Manager){
						groupKeys.add(role.getGroupKey());
					}
				}
				
				if(groupKeys.size() > 0){
					buildMainUI(null, groupKeys);
				}else{
					ManageTeams.this.getWindow().showNotification(Messages.getString("ManageTeams.noAdminAccess")); //$NON-NLS-1$
				}
			}
		});
		this.addComponent(btnTeamAdmin);
	}
	
	private void buildAdminFooter(HorizontalLayout layout, final Table tableTeams, final WebServiceCache cache){
		
		Button deleteTeamButton = new Button(Messages.getString("ManageTeams.deleteTeam")); //$NON-NLS-1$
		deleteTeamButton.addListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				
				ConfirmDialog.show(ManageTeams.this.getWindow(), Messages.getString("All.confirm"), Messages.getString("ManageTeams.deleteTeamConfirmation"), //$NON-NLS-1$ //$NON-NLS-2$
				        "Oui", "Non", new ConfirmDialog.Listener() { //$NON-NLS-1$ //$NON-NLS-2$
							private static final long serialVersionUID = 8958206356407387938L;
							public void onClose(ConfirmDialog dialog) {
				                if (dialog.isConfirmed()) {
				                	final String teamKey = (String) tableTeams.getValue();
				    				if(teamKey == null){
				    					ManageTeams.this.getWindow().showNotification(Messages.getString("ManageTeams.pleaseSelectTeam")); //$NON-NLS-1$
				    					return;
				    				}
				    				
				    				//TODO Ajouter une confirmation
				    				if(RequestsWebService.deleteTeam(teamKey, authorization)){
				    					tableTeams.removeItem(teamKey);
				    				}else{
				    					ManageTeams.this.getWindow().showNotification(Messages.getString("ManageTeams.deleteTeamError")); //$NON-NLS-1$
				    				}
				                } 
				            }
				        });
			}
		});
		
		
		Button addMemberButton = new Button(Messages.getString("ManageTeams.addMember")); //$NON-NLS-1$
		addMemberButton.addListener(new ClickListener() {
			private static final long serialVersionUID = -8410768876465053788L;

			@Override
			public void buttonClick(ClickEvent event) {
				
				final String teamKey = (String) tableTeams.getValue();
				if(teamKey == null){
					ManageTeams.this.getWindow().showNotification(Messages.getString("ManageTeams.pleaseSelectTeam")); //$NON-NLS-1$
					return;
				}
				
				final UserSelectWindow window = new UserSelectWindow("Ajouter un membre"); //$NON-NLS-1$
				ManageTeams.this.getApplication().getMainWindow().addWindow(window);
				ClickListener listener = new ClickListener() {
					
					//Add a member to the team window
					private static final long serialVersionUID = -1492461505559502208L;
					@Override
					public void buttonClick(ClickEvent event) {
						User user = RequestsWebService.getUserByEmail((String) window.getEmail());
						if (user == null) {
							ManageTeams.this.getWindow().showNotification(Messages.getString("ManageTeams.userNotFound")); //$NON-NLS-1$
						} else {
							//user.getTeams().add(teamKey);
							Team team = RequestsWebService.getTeam(teamKey);
							team.getUserKeys().add(user.getKey());
							
							if(RequestsWebService.editTeam(team, authorization)){
								cache.uncacheObject(team);
								ManageTeams.updateRow(tableTeams, team, cache.getGroup(team.getGroupKey()));
								ManageTeams.this.getParent().getWindow().showNotification(Messages.getString("ManageTeams.userAdded")); //$NON-NLS-1$
								ManageTeams.this.getParent().getWindow().removeWindow(window);
							} else{
								ManageTeams.this.getParent().getWindow().showNotification(Messages.getString("ManageTeams.userNotInGroup")); //$NON-NLS-1$
							}
						}
					}
				};
				window.addListener(listener);
			}
		});
		
		Button editTeam = new Button(Messages.getString("ManageTeams.editTeam")); //$NON-NLS-1$
		layout.addComponent(editTeam);
		editTeam.addListener(new ClickListener() {
			
			//Edit team window
			private static final long serialVersionUID = -8045203024458851091L;
			@Override
			public void buttonClick(ClickEvent event) {
				String teamKey = (String) tableTeams.getValue();
				if(teamKey == null){
					ManageTeams.this.getWindow().showNotification(Messages.getString("ManageTeams.selectUserInList")); //$NON-NLS-1$
					return;
				}
				Team team = cache.getTeam(teamKey);
				
				List<Group> groups = new ArrayList<Group>();
				groups = RequestsWebService.getGroups();
				if(groups != null){
					ManageTeams.this.getApplication().getMainWindow().addWindow(new EditTeamWindow(team, groups, authorization, tableTeams ));
				}
			}
		});
		layout.addComponent(deleteTeamButton);
		layout.addComponent(addMemberButton);
	}
	
	public static void updateRow(Table tableTeams, Team team, Group group) {
		tableTeams.removeItem(team.getKey());
		ManageTeams.addTableRow(tableTeams, team, group);
	}


	private void buildAdminHeader(final Table tableTeams,final List<String> groupList, final WebServiceCache cache){
		HorizontalLayout layout = new HorizontalLayout();
		layout.addComponent(new Label(Messages.getString("ManageTeams.manageGroup"))); //$NON-NLS-1$
		final ComboBox groupSelect = new ComboBox();
		for(String groupKey : groupList){
			Group group = cache.getGroup(groupKey);
			groupSelect.addItem(group);
		}
		layout.addComponent(groupSelect);
		
		groupSelect.setImmediate(true);
		groupSelect.addListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 5767946477449168968L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				tableTeams.removeAllItems();
				Group group = (Group)event.getProperty().getValue();
				if(group != null){
					populateTable(tableTeams, group.getTeamKeys(), cache);
				}
			}
		});
		
		Button addTeamButton = new Button(Messages.getString("ManageTeams.createTeam")); //$NON-NLS-1$
		this.addComponent(addTeamButton);
		addTeamButton.addListener(new ClickListener() {
			
			//Create team window
			private static final long serialVersionUID = 7391392900553095644L;
 			@Override
			public void buttonClick(ClickEvent event) {
 				List<Group> groups = new ArrayList<Group>();
 				for(String key : groupList){
 					Group group = cache.getGroup(key);
 					if(group != null){
 						groups.add(group);
 					}
 				}
				ManageTeams.this.getApplication().getMainWindow().addWindow(new EditTeamWindow(groups, authorization, tableTeams));  
			}
		});
		this.addComponent(layout);
		
	}
	
	private void buildMainUI(List<String> teamList, List<String> groupList){
		this.removeAllComponents();
		final WebServiceCache cache = new WebServiceCache();
		
		final boolean isGroupAdmin = groupList != null;
		
		final Table tableTeams = new Table(Messages.getString("All.teams")); //$NON-NLS-1$
		tableTeams.addContainerProperty(Messages.getString("All.name"), String.class, null); //$NON-NLS-1$
		tableTeams.addContainerProperty(Messages.getString("ManageTeams.groupMember"), String.class, null); //$NON-NLS-1$
		tableTeams.addContainerProperty(Messages.getString("ManageTeams.gamesPlates"), String.class, null); //$NON-NLS-1$
		tableTeams.addContainerProperty(Messages.getString("ManageTeams.tournamentsPlayed"), String.class, null); //$NON-NLS-1$
		tableTeams.addContainerProperty(Messages.getString("ManageTeams.numberOfMembers"), String.class, null); //$NON-NLS-1$
		tableTeams.setSelectable(true);
		tableTeams.setWidth("100%"); //$NON-NLS-1$
		
		Button backBtn = new Button(Messages.getString("All.back")); //$NON-NLS-1$
		this.addComponent(backBtn);
		backBtn.addListener(new ClickListener() {
			private static final long serialVersionUID = 32753149447082908L;
			@Override
			public void buttonClick(ClickEvent event) {
				buildInitialUI();
			}
		});
		
		// Add some more buttons if user have admin access
		if(isGroupAdmin){
			buildAdminHeader(tableTeams, groupList,cache);
		}
		
		if(teamList != null){
			populateTable(tableTeams, teamList, cache);
		}
			
		this.addComponent(tableTeams);
			
		HorizontalLayout layout = new HorizontalLayout();
		final Button detailsButton = new Button(Messages.getString("ManageTeams.showTeamMember")); //$NON-NLS-1$
		layout.addComponent(detailsButton);
		detailsButton.addListener(new ClickListener() {
			
			//Show the list of members in the team
			private static final long serialVersionUID = 4267488908346605048L;

			@Override
			public void buttonClick(ClickEvent event) {
				String teamKey = (String) tableTeams.getValue();
				if(teamKey == null){
					ManageTeams.this.getWindow().showNotification(Messages.getString("ManageTeams.pleaseSelectTeam")); //$NON-NLS-1$
					return;
				}
				Team team = cache.getTeam(teamKey);
				
				List<User> membersList = new ArrayList<User>();
				for(String memberKey : team.getUserKeys()){
					User member;
					member = cache.getUser(memberKey);
					if(member != null)
						membersList.add(member);
				}
				
				ListMembersWindow window = new ListMembersWindow(membersList, isGroupAdmin, cache, team, authorization,tableTeams);
				ManageTeams.this.getWindow().addWindow(window);
			}
		});
		
		if(isGroupAdmin){
			buildAdminFooter(layout,tableTeams,cache);
		}
		this.addComponent(layout);

		
		return;
	}

	private void populateTable(final Table tableTeams, List<String> teamList, WebServiceCache cache) {
		tableTeams.removeAllItems();
		for(String teamKey : teamList){
			Team team = cache.getTeam(teamKey);
			if(team != null){
				Group group = cache.getGroup(team.getGroupKey());
				if(group != null){
					addTableRow(tableTeams, team, group);
				}
			}
		}
	}
	
	public static void addTableRow(final Table tableTeams, Team team, Group group){
		tableTeams.addItem(new Object[] { team.getName(), group.getName(), Integer.toString(team.getGameKeys().size()), Integer.toString(team.getTournamentKeys().size()), Integer.toString(team.getUserKeys().size())}, team.getKey());
	}

	@Override
	public String getWarningForNavigatingFrom() {
		return null;
	}

}
