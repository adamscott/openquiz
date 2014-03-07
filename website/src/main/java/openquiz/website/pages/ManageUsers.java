package openquiz.website.pages;

import java.util.List;

import openquiz.website.Website_UI;
import openquiz.website.util.Messages;
import openquiz.website.util.SessionConstants;
import openquiz.website.windows.EditAccountWindow;

import org.vaadin.dialogs.ConfirmDialog;
import org.vaadin.navigator.Navigator;

import ca.openquiz.comms.RequestsWebService;
import ca.openquiz.comms.model.Group;
import ca.openquiz.comms.model.User;
import ca.openquiz.comms.response.UserListResponse;
import ca.openquiz.comms.response.UserResponse;

import com.vaadin.Application;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Button.ClickEvent;

public class ManageUsers extends Panel implements Navigator.View{
	private static final long serialVersionUID = 1034689058276380554L;

	private String authString;
	private int USERS_PER_PAGE = 25;
	private int currentPage = 1;
	
	private ComboBox cbGroupSearch;
	private TextField txtEmailSearch;

	private Table usersTable = null;
	
	private Label pageNumberLabel = new Label(Integer.toString(currentPage));
	
	public ManageUsers() {
		super(Messages.getString("All.manageUsers"));
		super.setSizeFull();
	}

	@Override
	public void init(Navigator navigator, Application application) {
		if(application != null)
		{
			authString = (String)((Website_UI)application).getSession().getAttribute(SessionConstants.SESSION_AUTHORIZATION_STRING);
		}
	}

	@Override
	public void navigateTo(String requestedDataId) {
		this.removeAllComponents();
		HorizontalLayout searchHeader = new HorizontalLayout(); 
		cbGroupSearch = new ComboBox("Recherche par groupe");
		cbGroupSearch.setImmediate(true);
		List<Group> groups = RequestsWebService.getGroups();
		for(Group group : groups){
			cbGroupSearch.addItem(group);
		}
		cbGroupSearch.addListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 5767946477449168968L;
			@Override
			public void valueChange(ValueChangeEvent event) {
				currentPage = 1;
				usersTable.removeAllItems();
				usersTable.setEnabled(false);
				updatePageLabel();
				updateTable(1);
			}
		});
		searchHeader.addComponent(cbGroupSearch);
		
		txtEmailSearch = new TextField("Recherche par courriel");
		txtEmailSearch.addListener(new TextChangeListener() {
			private static final long serialVersionUID = 7151706665329449137L;
			@Override
			public void textChange(TextChangeEvent event) {
				if(event.getText().isEmpty()){
					currentPage = 1;
					usersTable.removeAllItems();
					updatePageLabel();
					updateTable(1);
				}
			}
		});
		searchHeader.addComponent(txtEmailSearch);
		
		Button btnSearchByEmail = new Button("Rechercher");
		btnSearchByEmail.setHeight("100%");
		btnSearchByEmail.addListener(new ClickListener() {
			private static final long serialVersionUID = 2332507789156721323L;
			@Override
			public void buttonClick(ClickEvent event) {
				String email = (String)txtEmailSearch.getValue();
				if(email != null && !email.isEmpty()){
					User user = RequestsWebService.getUserByEmail(email);
					if(user != null){
						usersTable.removeAllItems();
						addTableRow(usersTable, user);
					} else {
						ManageUsers.this.getWindow().showNotification("Aucun usager trouvé avec ce courriel");
					}
				}
			}
		});
		searchHeader.addComponent(btnSearchByEmail);
		
		
		this.addComponent(searchHeader);
		
		usersTable = new Table("Utilisateurs"); 
		usersTable.addContainerProperty("Prénom", String.class, null); 
		usersTable.addContainerProperty("Nom", String.class, null); 
		usersTable.addContainerProperty("Dernière connexion", String.class, null); 
		usersTable.setSelectable(true);
		usersTable.setWidth("100%");
		this.addComponent(usersTable);
		
		// Next and previous page
		Button btnPrevPage = new Button(Messages.getString("All.prevPage"));
		btnPrevPage.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = -3767562099750326514L;
			public void buttonClick(ClickEvent event) {
				if(currentPage != 1 && ManageUsers.this.updateTable(currentPage - 1)){
					--currentPage;
					updatePageLabel();
				}
		    }
		});
		
		Button btnNextPage = new Button(Messages.getString("All.nextPage"));
		btnNextPage.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = -3767562099750326514L;
			public void buttonClick(ClickEvent event) {
				if(ManageUsers.this.updateTable(currentPage + 1)){
					++currentPage;
					updatePageLabel();
				}
		    }
		});
		
		HorizontalLayout footer = new HorizontalLayout();
		HorizontalLayout pageControlLayout = new HorizontalLayout();
		pageControlLayout.addComponent(btnPrevPage);
		pageControlLayout.addComponent(pageNumberLabel);
		pageControlLayout.addComponent(btnNextPage);
		
		HorizontalLayout actionButtons = new HorizontalLayout();
		
		Button btnDeleteUser = new Button("Effacer l'usager");
		btnDeleteUser.addListener(new ClickListener() {
			private static final long serialVersionUID = 8777091823739988948L;
			@Override
			public void buttonClick(ClickEvent event) {
				final String key = (String)usersTable.getValue();
            	if(key == null){
            		ManageUsers.this.getWindow().showNotification("Veuillez selection un usager dans la liste");
            	}else{
            		ConfirmDialog.show(ManageUsers.this.getWindow(), "Confirmation", "Êtes-vous sûr de vouloir effacer cet usager? Cette action est irréversible",
				        "Oui", "Non", new ConfirmDialog.Listener() {
							private static final long serialVersionUID = 1685604086141192760L;
							public void onClose(ConfirmDialog dialog) {
				                if (dialog.isConfirmed()) {
				                	if(RequestsWebService.deleteUser(key, authString)){
				                		usersTable.removeItem(key);
				                		ManageUsers.this.getWindow().showNotification("Usager effacé");
				                		
				                		//Deleselect the item because its selected even if its removed from table
				                		usersTable.setValue(null);
				                	}else{
				                		ManageUsers.this.getWindow().showNotification("Impossible d'effacer l'usager");
				                	}
				                } 
				            }
				        });
            	}
			}
		});
		
		actionButtons.addComponent(btnDeleteUser);
		
		Button btnUserDetails = new Button("Détails de l'usager");
		btnUserDetails.addListener(new ClickListener() {
			private static final long serialVersionUID = -4293597369622898158L;
			@Override
			public void buttonClick(ClickEvent event) {
				final String key = (String)usersTable.getValue();
				boolean flag = true;
            	if(key == null){
            		ManageUsers.this.getWindow().showNotification("Veuillez selection un usager dans la liste");
            	}else{
            		UserResponse user = RequestsWebService.getUserInfo(key, authString);
            		ManageUsers.this.getApplication().getMainWindow().addWindow(new EditAccountWindow(user, flag));
            	}
			}
		});
		actionButtons.addComponent(btnUserDetails);
		
		footer.addComponent(actionButtons);
		footer.addComponent(pageControlLayout);
		
		footer.setWidth("100%");
		footer.setComponentAlignment(actionButtons, Alignment.MIDDLE_LEFT);
		footer.setComponentAlignment(pageControlLayout, Alignment.MIDDLE_RIGHT);
		this.addComponent(footer);
		
		updateTable(currentPage);
	}
	
	private void updatePageLabel(){
		pageNumberLabel.setValue(Integer.toString(currentPage));
	}
	
	protected boolean updateTable(int newPage) {
		String groupKey = null;
		if(((Group)cbGroupSearch.getValue()) != null){
			groupKey = ((Group)cbGroupSearch.getValue()).getKey();
		}
		UserListResponse usersResponse = RequestsWebService.getUsers(groupKey, newPage,USERS_PER_PAGE);
		if(usersResponse == null || usersResponse.getUsers().size() == 0){
			usersTable.setEnabled(true);
			return false;
		}
		
		usersTable.removeAllItems();
		for(User user : usersResponse.getUsers()){
			addTableRow(usersTable, user);
		}
		usersTable.setEnabled(true);
		return true;
	}

	public static void addTableRow(final Table tableTeams, User user){
		tableTeams.addItem(new Object[] { user.getFirstName(), user.getLastName(), user.getLastLogin()}, user.getKey());
	}
	
	@Override
	public String getWarningForNavigatingFrom() {
		return null;
	}
}
