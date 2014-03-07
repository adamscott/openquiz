package openquiz.website.windows;

import java.io.Serializable;
import java.util.Date;

import openquiz.website.Website_UI;
import openquiz.website.util.Messages;
import openquiz.website.util.SessionConstants;
import ca.openquiz.comms.RequestsWebService;
import ca.openquiz.comms.enums.Language;
import ca.openquiz.comms.model.Group;
import ca.openquiz.comms.model.GroupRole;
import ca.openquiz.comms.model.Team;
import ca.openquiz.comms.parameter.CreateUserParam;
import ca.openquiz.comms.response.BaseResponse;
import ca.openquiz.comms.response.UserResponse;

import com.vaadin.terminal.UserError;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class EditAccountWindow extends Window implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//String language
	private static String WINDOW_SETTINGS = Messages.getString("MainWebsiteWindow.windowAccountSettings");
	private String LABEL_TITLE = Messages.getString("MainWebsiteWindow.labelTitleSettings");
	private String TEXT_FIRSTNAME = Messages.getString("All.firstName");
	private String TEXT_LASTNAME = Messages.getString("All.lastName");
	private String TEXT_EMAIL = Messages.getString("All.emailAddress");
	private String OLD_TEXT_PASSWORD = Messages.getString("All.oldPassword");
	private String TEXT_PASSWORD = Messages.getString("All.password");
	private String TEXT_PASSWORD_VERIFICATION = Messages.getString("All.passwordVerification");
	private String WINDOW_CHANGEPASSWORD = Messages.getString("All.windowChangePassword");
	private String FIELD_BIRTHDAY = Messages.getString("All.birthday");
	private String BUTTON_VALIDATE = Messages.getString("All.validatebutton");
	private String BUTTON_CHANGEPASS = Messages.getString("All.changepassbuttton");
	private String SELECT_LANGUAGE = Messages.getString("All.selectLangugage");
	private String ERROR_EMPTY = Messages.getString("All.errorEmpty");
	private String ERROR_PASSWORD_VERIFICATION = Messages.getString("All.errorPasswordVerification");
	private String ERROR_SAME_PASSWORD = Messages.getString("All.errorSamePassword");
	private String NOTIFICATION_SUCCEED = Messages.getString("MainWebsiteWindow.notificationSucceed");
	private String NOTIFICATION_FAILED = Messages.getString("MainWebsiteWindow.notificationFailed");
	private String REQUIRED_MESSAGE = Messages.getString("All.requiredmessage");
	private String ERROR_PASSWORD_LENGTH = Messages.getString("All.error_password_lenth");

	private String userKey;
	private String authorization;
	private String itemWidth = "250";
	private String windowHeight = "430";
	private String windowWidth = "295";
	private String windowWidthDetailed = "640";
    private Label lbTitle;
    private TextField txtFirstname;
    private TextField txtLastname;
    private TextField txtEmail;
    private Button btnValidation;
    private Button btnChangePass;
    private NativeSelect nsLanguage;
    
    private PasswordField OldfieldPassword;
    private PasswordField fieldPassword;
    private PasswordField fieldPasswordVerification;
	private DateField fieldDate;
	private UserResponse user;
	private boolean detailedUser = false;
	
	private Table teams;
	private Table groups;
	
	public EditAccountWindow(){
		super(WINDOW_SETTINGS);
        windowInfo();
	}
	
	public EditAccountWindow(UserResponse user){
		super(WINDOW_SETTINGS);
		this.user = user;
		windowInfo();
	}
	public EditAccountWindow(UserResponse user, boolean flag){
		super(WINDOW_SETTINGS);
		this.user = user;
		detailedUser = flag;
		windowInfo();
		
	}
	
	@Override
	public void attach() {
		super.attach();
		
		userKey = (String) ((Website_UI)this.getApplication()).getSession().getAttribute(SessionConstants.SESSION_USER_KEY);
		authorization = (String) ((Website_UI)this.getApplication()).getSession().getAttribute(SessionConstants.SESSION_AUTHORIZATION_STRING);
		if(this.user == null){
			UserResponse loggedUser = RequestsWebService.getCurrentUserInfo(authorization);
			populateUI(loggedUser);
		}else{
			populateUI(this.user);
		}
		
		//If this window was opened by ManageUser page, we set read only 
		if(detailedUser){
			txtFirstname.setReadOnly(true);
			txtLastname.setReadOnly(true);
			txtEmail.setReadOnly(true);
			fieldDate.setReadOnly(true);
			nsLanguage.setReadOnly(true);
		}
	}
	
	private void populateUI(UserResponse user){
		txtFirstname.setValue(user.getFirstName());
		txtLastname.setValue(user.getLastName());
		txtEmail.setValue(user.getEmail());
		fieldDate.setValue(user.getBirthDate());
		nsLanguage.setValue(user.getLanguage());
		
			for(Team team : user.getTeams()){
	    		teams.addItem(new Object[] { team.getName()}, team.getKey());
	    	}
	    	
	    	for(GroupRole role : user.getGroupRoles()){
	    		Group group = RequestsWebService.getGroup(role.getGroupKey());
	    		if(group != null) groups.addItem(new Object[] {group, role.getRole()}, role.getGroupKey() + role.getRole());
	    	}
  
	}

	private void windowInfo() {
		
		this.setModal(true);
		this.setResizable(false);
		this.setHeight(windowHeight);
		this.setWidth(windowWidth);

	    lbTitle = new Label(LABEL_TITLE);
        lbTitle.setWidth(itemWidth);
        txtFirstname = new TextField(TEXT_FIRSTNAME);
        txtFirstname.setWidth(itemWidth);
        txtLastname = new TextField(TEXT_LASTNAME);
        txtLastname.setWidth(itemWidth);
        txtEmail = new TextField(TEXT_EMAIL);
        txtEmail.setWidth(itemWidth);
        fieldDate = new DateField(FIELD_BIRTHDAY);
        fieldDate.setResolution(DateField.RESOLUTION_DAY);
        fieldDate.setDateFormat("dd-MM-yyyy");
        fieldDate.setData(new Date());
        fieldDate.setWidth(itemWidth);
        nsLanguage = new NativeSelect(SELECT_LANGUAGE);
        nsLanguage.addItem(Language.fr);
        nsLanguage.addItem(Language.en);
        nsLanguage.setNullSelectionAllowed(false);
        nsLanguage.setValue(2);
        nsLanguage.setImmediate(true);
        
      //Validate button window
        btnValidation = new Button(BUTTON_VALIDATE);
        btnValidation.setWidth(itemWidth);
        btnValidation.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {
				btnValidation.setEnabled(false);
		    	CreateUserParam user = new CreateUserParam();
		    	
		    	if(validateFields()){
		    		
			    	user.setEmail((String)txtEmail.getValue());
			    	user.setFirstName((String)txtFirstname.getValue());
			    	user.setLastName((String)txtLastname.getValue());
			    	user.setLanguage((Language) nsLanguage.getValue());
			    	user.setBirthDate((Date)fieldDate.getValue());
			    	
			    	BaseResponse response = RequestsWebService.editUser(user, userKey, authorization);

			    	if( response != null && response.isCompleted()){
			    		EditAccountWindow.this.showNotification(NOTIFICATION_SUCCEED);
			    		btnValidation.setEnabled(true);
			    		EditAccountWindow.this.close();
			    	}else{
			    		EditAccountWindow.this.showNotification(NOTIFICATION_FAILED);
			    		btnValidation.setEnabled(true);
			    	}
		    	}
            }
        });
        
        //Change password window
        btnChangePass = new Button(BUTTON_CHANGEPASS);
        btnChangePass.setWidth(itemWidth);
        btnChangePass.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {
				EditAccountWindow.this.getApplication().getMainWindow().addWindow(new ChangePasswordWindow());
            }
        });
	
        HorizontalLayout columns = new HorizontalLayout();
        columns.setSizeFull();
	    VerticalLayout vLayout = new VerticalLayout();
	    vLayout.setSizeFull();
	    vLayout.setSpacing(true);
	    vLayout.addComponent(lbTitle);
	    vLayout.addComponent(txtFirstname);
	    vLayout.addComponent(txtLastname);
	    vLayout.addComponent(nsLanguage);
	    vLayout.setComponentAlignment(nsLanguage, Alignment.MIDDLE_LEFT);
	    vLayout.addComponent(txtEmail);
	    vLayout.addComponent(fieldDate);
	    vLayout.addComponent(btnChangePass);
	    vLayout.addComponent(btnValidation);
	    columns.addComponent(vLayout);
	    
	    if(user != null){
	    	this.setWidth(windowWidthDetailed);
	    	VerticalLayout column2 = new VerticalLayout();
	    	teams = new Table("Membre des équipes :");
	    	teams.setWidth("300px");
	    	teams.setHeight("160px");
	    	teams.addContainerProperty("Équipe", String.class, null);
	    	column2.addComponent(teams);

	    	groups = new Table("Membre des groupes :");
	    	groups.setWidth("300px");
	    	groups.setHeight("130px");
	    	groups.addContainerProperty("Groupe", String.class, null);
	    	groups.addContainerProperty("Role", String.class, null);
	    	
	    	column2.addComponent(groups);
	    	columns.addComponent(column2);
	    }
        
	    if(detailedUser == true){
	    	btnValidation.setVisible(false);
	    	btnChangePass.setVisible(false);
	    }
	    
	    this.addComponent(columns);
	}

    private boolean validateFields() {
		boolean isValid = true;
		
		removeAllErrorMessages();
		
		if( ((String)txtEmail.getValue()).isEmpty()){
			txtEmail.setComponentError(new UserError(ERROR_EMPTY));
			isValid = false;
		}
		if( ((String)txtFirstname.getValue()).isEmpty()){
			txtFirstname.setComponentError(new UserError(ERROR_EMPTY));
			isValid = false;
		}
		if( ((String)txtLastname.getValue()).isEmpty()){
			txtLastname.setComponentError(new UserError(ERROR_EMPTY));
			isValid = false;
		}
		if( ((Date)fieldDate.getValue()) == null){
			fieldDate.setComponentError(new UserError(ERROR_EMPTY));
			isValid = false;
		}
		if( ((String)txtEmail.getValue()).isEmpty()){
			txtEmail.setComponentError(new UserError(ERROR_EMPTY));
			isValid = false;
		}
		
		return isValid;
	}
    
	private void removeAllErrorMessages() {
		txtEmail.setComponentError(null);
		txtFirstname.setComponentError(null);
		txtLastname.setComponentError(null);
		fieldDate.setComponentError(null);
		txtEmail.setComponentError(null);
	}


	private class ChangePasswordWindow extends Window {
		private static final long serialVersionUID = 1L;

		public ChangePasswordWindow(){
			super.setCaption(WINDOW_CHANGEPASSWORD);
			this.setModal(true);
			this.setResizable(false);
			this.setWidth(windowWidth);
			this.setHeight("300px");
			
			OldfieldPassword = new PasswordField(OLD_TEXT_PASSWORD);
			OldfieldPassword.setWidth(itemWidth);
	        fieldPassword = new PasswordField(TEXT_PASSWORD);
	        fieldPassword.setWidth(itemWidth);
	        fieldPasswordVerification = new PasswordField(TEXT_PASSWORD_VERIFICATION);
	        fieldPasswordVerification.setWidth(itemWidth);
	        btnValidation = new Button(BUTTON_VALIDATE);
	        btnValidation.setWidth(itemWidth);
	        btnValidation.addListener(new Button.ClickListener() {
				private static final long serialVersionUID = 1L;

				public void buttonClick(ClickEvent event) {
					btnValidation.setEnabled(false);
					CreateUserParam user = new CreateUserParam();
					UserResponse loggedUser = RequestsWebService.getCurrentUserInfo(authorization);
					if (validation())
				    {
						
						user.setEmail((String)loggedUser.getEmail());
				    	user.setFirstName((String)loggedUser.getFirstName());
				    	user.setLastName((String)loggedUser.getLastName());
				    	user.setLanguage((Language)loggedUser.getLanguage());
				    	user.setBirthDate((Date)loggedUser.getBirthDate());
				    	user.setPassword((String)fieldPassword.getValue());
				    	
				    	BaseResponse response = RequestsWebService.editUser(user, userKey, authorization);

				    	if( response != null && response.isCompleted()){
				    		ChangePasswordWindow.this.close();
				    		EditAccountWindow.this.showNotification(NOTIFICATION_SUCCEED);
				    		btnValidation.setEnabled(true);
				    	}else{
				    		EditAccountWindow.this.showNotification(NOTIFICATION_FAILED);
				    		btnValidation.setEnabled(true);
				    	}
				    }
					else{
						EditAccountWindow.this.showNotification(NOTIFICATION_FAILED);
						btnValidation.setEnabled(true);
					}
	            }
	        });
	        
	        VerticalLayout vLayout = new VerticalLayout();
		    vLayout.setSizeFull();
		    vLayout.setMargin(true);
		    vLayout.setSpacing(true);
		    vLayout.addComponent(OldfieldPassword);
		    vLayout.addComponent(fieldPassword);
		    vLayout.addComponent(fieldPasswordVerification);
		    vLayout.addComponent(btnValidation);
		   		    
		    this.setContent(vLayout);
 
		}
		
		private boolean validation(){
			boolean isValid = true;
			
			fieldPasswordVerification.setComponentError(null);
			OldfieldPassword.setComponentError(null);
			
			if(((String)OldfieldPassword.getValue()).isEmpty() || ((String)fieldPassword.getValue()).isEmpty() || ((String)fieldPasswordVerification.getValue()).isEmpty()){
				OldfieldPassword.setRequired(true);
				OldfieldPassword.setRequiredError(REQUIRED_MESSAGE);
				fieldPassword.setRequired(true);
				fieldPassword.setRequiredError(REQUIRED_MESSAGE);
				fieldPasswordVerification.setRequired(true);
				fieldPasswordVerification.setRequiredError(REQUIRED_MESSAGE);
				isValid = false;
			}
			else{
				UserResponse loggedUser = RequestsWebService.getCurrentUserInfo(authorization);
				String authString = RequestsWebService.getAuthorization(loggedUser.getEmail(),(String)OldfieldPassword.getValue());
				
				if(((String)fieldPassword.getValue()).equals((String)OldfieldPassword.getValue())){
					OldfieldPassword.setComponentError(new UserError(ERROR_SAME_PASSWORD));
					isValid = false;
				}
				else if(((String)fieldPassword.getValue()).length() < 6){
					fieldPassword.setComponentError(new UserError(ERROR_PASSWORD_LENGTH));
					isValid = false;
				}	
				else if(!((authString).equals(authorization))){
					OldfieldPassword.setComponentError(new UserError(ERROR_PASSWORD_VERIFICATION));
					isValid = false;
				}			
				else if( !((String)fieldPassword.getValue()).equals((String)fieldPasswordVerification.getValue())){
					fieldPasswordVerification.setComponentError(new UserError(ERROR_PASSWORD_VERIFICATION));
					isValid = false;
				}
			}
			return isValid;
			
		}
	}
	
	

}
