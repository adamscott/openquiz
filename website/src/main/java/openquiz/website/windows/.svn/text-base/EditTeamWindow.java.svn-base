package openquiz.website.windows;

import java.util.List;

import openquiz.website.pages.ManageTeams;
import ca.openquiz.comms.RequestsWebService;
import ca.openquiz.comms.model.Group;
import ca.openquiz.comms.model.Team;
import ca.openquiz.comms.response.KeyResponse;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Select;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;

public class EditTeamWindow  extends Window implements Window.CloseListener{

	private static final long serialVersionUID = -8803525464563637194L;

	private Team teamEditing = null;
	
	private TextField teamName = new TextField("Nom de l'équipe");
	private Select groupsSelect = new Select("Membre du groupe ");

	private String authorization;
	
	public EditTeamWindow( List<Group> groups, String authorization, final Table parentTable){
		this(null, groups, authorization, parentTable);
	}
	
	public EditTeamWindow(Team team, List<Group> groups, String authorization, final Table parentTable){
		super(team == null ? "Nouvelle équipe" : "Modification d'équipe");
		this.setHeight("200px");
		this.setWidth("450px");
		this.center();
		this.authorization = authorization;
		if(team != null){
			teamEditing = team;
		}
		initUI(parentTable);
		fillTeamFields(groups);
	}
	
	
	private void initUI( final Table parentTable) {
		this.addComponent(teamName);
		this.addComponent(groupsSelect);
		Button actionButton = new Button(teamEditing == null ? "Créer" : "Modifier");
		actionButton.addListener(new ClickListener() {
			private static final long serialVersionUID = -1889059981417490544L;

			@Override
			public void buttonClick(ClickEvent event) {
				Group group = (Group)groupsSelect.getValue();
				if(teamEditing == null){
					
					Team team = new Team();
					team.setName((String)teamName.getValue());
					
					if(team.getName() == null || team.getName().isEmpty()){
						EditTeamWindow.this.showNotification("Veuillez entrer un nom d'équipe");
						return;
					}
					if(groupsSelect.getValue() == null){
						EditTeamWindow.this.showNotification("Veuillez choisir un groupe");
						return;
					}
					
					team.setGroupKey(group.getKey());
					KeyResponse response = RequestsWebService.addTeam(team, authorization);
					if(response != null && response.getKey() != null && !response.getKey().isEmpty()){
						team = RequestsWebService.getTeam(response.getKey());
						//Success add and close the window
						ManageTeams.addTableRow(parentTable, team, group);
						EditTeamWindow.this.getParent().showNotification("Nouvelle équipe ajoutée");
						EditTeamWindow.this.close();
					}
					else{
						//Something went wrong, display message and keep window open...
						EditTeamWindow.this.showNotification("Impossible d'ajouter la nouvelle équipe");
					}
				}else{
					teamEditing.setName((String)teamName.getValue());
					if(RequestsWebService.editTeam(teamEditing, authorization)){
						//Success add and close the window
						ManageTeams.updateRow(parentTable, teamEditing,group );
						EditTeamWindow.this.getParent().showNotification("Équipe modifié");
						EditTeamWindow.this.close();
						
					}else{
						EditTeamWindow.this.showNotification("Impossible de modifier l'équipe");
					}
					
				}
			}
		});
		
		this.addComponent(actionButton);
		
	}

	private void fillTeamFields(List<Group> groups) {
		
		if (teamEditing != null) {
			this.teamName.setValue(teamEditing.getName());
			Group group = null;
			for (Group item : groups) {
				if (item.getKey().equals(teamEditing.getGroupKey())) {
					group = item;
					break;
				}
			}
			this.groupsSelect.addItem(group);
			this.groupsSelect.select(group);
			this.groupsSelect.setReadOnly(true);
		} else {
			for (Group group : groups) {
				this.groupsSelect.addItem(group);
			}
		}
	}

	@Override
	public void windowClose(CloseEvent e) {
	}

}
