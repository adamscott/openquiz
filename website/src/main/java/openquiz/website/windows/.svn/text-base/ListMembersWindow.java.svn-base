package openquiz.website.windows;

import java.util.List;

import org.vaadin.dialogs.ConfirmDialog;

import openquiz.website.pages.ManageTeams;
import openquiz.website.util.WebServiceCache;
import ca.openquiz.comms.RequestsWebService;
import ca.openquiz.comms.model.Group;
import ca.openquiz.comms.model.Team;
import ca.openquiz.comms.model.User;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Table;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;

public class ListMembersWindow extends Window{
	private static final long serialVersionUID = -7798994394623480111L;

	public ListMembersWindow(List<User> users, boolean isAdmin, final WebServiceCache cache, final Team team, final String authorization, final Table parentTable){
		final Table tableMembers = new Table();
		tableMembers.addContainerProperty("Nom", String.class, null);
		tableMembers.addContainerProperty("Prenom", String.class, null);
		tableMembers.addContainerProperty("Dernière connexion", String.class, null);
		tableMembers.setWidth("100%");
		
		populateTable(tableMembers, users);
		
		this.addComponent(tableMembers);
		
		if(isAdmin){
			tableMembers.setSelectable(true);
			
			Button removeMember = new Button("Enlever l'utilisateur");
			this.addComponent(removeMember);
			removeMember.addListener(new ClickListener() {
				private static final long serialVersionUID = -3815566671412506330L;
				@Override
				public void buttonClick(ClickEvent event) {
					
					ConfirmDialog.show(ListMembersWindow.this.getApplication().getMainWindow(), "Confirmation", "Etes vous sur de vouloir enlever ce membre de l'équipe?",
					        "Oui", "Non", new ConfirmDialog.Listener() {
								private static final long serialVersionUID = 8785562931624354654L;

								public void onClose(ConfirmDialog dialog) {
					                if (dialog.isConfirmed()) {
					                	String userKey = (String)tableMembers.getValue();
										team.getUserKeys().remove(userKey);
										if(RequestsWebService.editTeam(team, authorization)){
											Group group = cache.getGroup(team.getGroupKey());
											ManageTeams.updateRow(parentTable, team, group);
											parentTable.refreshRowCache();
											tableMembers.removeItem(userKey);
											cache.uncacheObject(team);
											ListMembersWindow.this.getParent().showNotification("Membre enlevé");
										}else{
											ListMembersWindow.this.showNotification("Impossible d'enlever le membre ");
										}
					                } 
					            }
					        });
				}
			});
		}
		
		this.setWidth("500px");
		this.center();
	}
	
	private void populateTable(Table tableMembers,List<User> users ){
		tableMembers.removeAllItems();
		for(User user : users){
			tableMembers.addItem(new Object[] { user.getLastName(), user.getFirstName(), user.getLastLogin().toString() }, user.getKey());
		}
	}
	
}
