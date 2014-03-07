package openquiz.website.windows;

import java.io.Serializable;
import java.util.Date;

import openquiz.website.util.Messages;
import ca.openquiz.comms.RequestsWebService;
import ca.openquiz.comms.enums.Language;
import ca.openquiz.comms.parameter.CreateUserParam;
import ca.openquiz.comms.response.KeyResponse;

import com.vaadin.data.validator.EmailValidator;
import com.vaadin.terminal.UserError;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.themes.Runo;

public class RegisterWindow extends CustomComponent implements Window.CloseListener, Serializable {
	private static final long serialVersionUID = -5484757602516325757L;

	
	//String language
	private String BUTTON_SIGNIN = Messages.getString("All.signIn");
	private String WINDOW_SIGNIN = Messages.getString("MainWebsiteWindow.windowSignin");
	private String LABEL_TITLE = Messages.getString("MainWebsiteWindow.labelTitle");
	private String TEXT_FIRSTNAME = Messages.getString("All.firstName");
	private String TEXT_LASTNAME = Messages.getString("All.lastName");
	private String TEXT_EMAIL = Messages.getString("All.emailAddress");
	private String TEXT_PASSWORD = Messages.getString("All.password");
	private String TEXT_PASSWORD_VERIFICATION = Messages.getString("All.passwordVerification");
	private String FIELD_BIRTHDAY = Messages.getString("All.birthday");
	private String NOTIFICATION_SUCCEED = Messages.getString("MainWebsiteWindow.notificationSucceed");
	private String NOTIFICATION_FAILED = Messages.getString("MainWebsiteWindow.notificationFailed");
	private String ERROR_EMPTY = Messages.getString("All.errorEmpty");
	private String ERROR_PASSWORD_VERIFICATION = Messages.getString("All.errorPasswordVerification");
	private String ERROR_INVALID_ADDRESS = Messages.getString("All.errorInvalidAddress");
	private String SELECT_LANGUAGE = Messages.getString("All.selectLangugage");
	
	private String itemWidth = "315";
	private String windowHeight = "480";
	private String windowWidth = "355";
	private Window windowMain;  
	private Window windowRegister;
	private Button btnRegister;  
    private Button validatebutton;
    private Label lbTitle;
    private TextField txtFirstname;
    private TextField txtLastname;
    private TextField txtEmail;
    private PasswordField fieldPassword;
    private PasswordField fieldPasswordVerification;
	private DateField fieldDate;
	private EmailValidator fieldValidator;
	private NativeSelect nsLanguage;

	
	public RegisterWindow(String label, Window main){
		windowMain = main;
		fieldValidator = new EmailValidator(ERROR_INVALID_ADDRESS);

        final FormLayout layout = new FormLayout();
        btnRegister = new Button(BUTTON_SIGNIN, this,"RegisterButtonClick");
        btnRegister.setStyleName(Runo.BUTTON_LINK);
        layout.addComponent(btnRegister);	
        
        setCompositionRoot(layout);

    }
    
    public void RegisterButtonClick(Button.ClickEvent event) {

    	windowRegister = new Window(WINDOW_SIGNIN);
    	windowRegister.setModal(true);
    	windowRegister.setResizable(false);
    	windowRegister.setHeight(windowHeight);
    	windowRegister.setWidth(windowWidth);
    	windowMain.addWindow(windowRegister);

        windowRegister.addListener(this);
        
        lbTitle = new Label(LABEL_TITLE);
        lbTitle.setWidth(itemWidth);
        txtFirstname = new TextField(TEXT_FIRSTNAME);
        txtFirstname.setWidth(itemWidth);
        txtLastname = new TextField(TEXT_LASTNAME);
        txtLastname.setWidth(itemWidth);
        txtEmail = new TextField(TEXT_EMAIL);
        txtEmail.setWidth(itemWidth);
        fieldPassword = new PasswordField(TEXT_PASSWORD);
        fieldPassword.setWidth(itemWidth);
        fieldPasswordVerification = new PasswordField(TEXT_PASSWORD_VERIFICATION);
        fieldPasswordVerification.setWidth(itemWidth);
        fieldDate = new DateField(FIELD_BIRTHDAY);
        fieldDate.setResolution(DateField.RESOLUTION_DAY);
        fieldDate.setDateFormat("dd-MM-yyyy");
        fieldDate.setData(new Date());
        fieldDate.setWidth(itemWidth);
        validatebutton = new Button(BUTTON_SIGNIN, this, "closeButtonClick");
        validatebutton.setWidth(itemWidth);
        nsLanguage = new NativeSelect(SELECT_LANGUAGE);
        nsLanguage.addItem(Language.fr);
        nsLanguage.addItem(Language.en);
        nsLanguage.setNullSelectionAllowed(false);
        nsLanguage.setValue(2);
        nsLanguage.setImmediate(true);
        
        VerticalLayout vLayout = new VerticalLayout();
        vLayout.setSizeFull();
        vLayout.setSpacing(true);
        vLayout.addComponent(lbTitle);
        vLayout.addComponent(txtFirstname);
        vLayout.addComponent(txtLastname);
        HorizontalLayout hLayout = new HorizontalLayout();
        hLayout.setSizeFull();
        hLayout.addComponent(nsLanguage);
        hLayout.setComponentAlignment(nsLanguage, Alignment.MIDDLE_RIGHT);
        vLayout.addComponent(hLayout);
        vLayout.addComponent(txtEmail);
        vLayout.addComponent(fieldDate);
        vLayout.addComponent(fieldPassword);
        vLayout.addComponent(fieldPasswordVerification);
        vLayout.addComponent(validatebutton);
        
       
        windowRegister.addComponent(vLayout);

        /* Allow opening only one window at a time. */
        btnRegister.setEnabled(false);

    }

    /** Handle Close button click and close the window. */
    public void closeButtonClick(Button.ClickEvent event) {
    	if(validateFields()){
	    	validatebutton.setEnabled(false);
	    	CreateUserParam userParam = new CreateUserParam();
	    	
	    	userParam.setEmail((String)txtEmail.getValue());
	    	userParam.setFirstName((String)txtFirstname.getValue());
	    	userParam.setLastName((String)txtLastname.getValue());
	    	userParam.setLanguage((Language)nsLanguage.getValue());
	    	userParam.setPassword((String)fieldPassword.getValue());
	    	userParam.setBirthDate((Date)fieldDate.getValue());
	    	
	    	KeyResponse response = RequestsWebService.addUser(userParam);
	    	
	    	userParam.isValid();
	    	
	    	
	    	if( response != null && response.isCompleted()){
	    		/* Windows are managed by the application object. */
	    		windowMain.removeWindow(windowRegister);
	    		windowMain.showNotification(NOTIFICATION_SUCCEED);
	            /* Return to initial state. */
	            btnRegister.setEnabled(true);
	    	}else{
	    		windowMain.showNotification(NOTIFICATION_FAILED);
	    		btnRegister.setEnabled(true);
	    	}
    	}

    }

    private boolean validateFields() {
		boolean isValid = true;
		
		removeAllErrorMessages();
		
		if( ((String)txtEmail.getValue()).isEmpty()){
			txtEmail.setComponentError(new UserError(ERROR_EMPTY));
			isValid = false;
		}else if(!fieldValidator.isValid(((String)txtEmail.getValue()))){
			txtEmail.setComponentError(new UserError(fieldValidator.getErrorMessage()));
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
		if( !((String)fieldPassword.getValue()).equals((String)fieldPasswordVerification.getValue()) ||  ((String)fieldPassword.getValue()).isEmpty()){
			fieldPasswordVerification.setComponentError(new UserError(ERROR_PASSWORD_VERIFICATION));
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
		fieldPasswordVerification.setComponentError(null);
		txtEmail.setComponentError(null);
	}

	/** In case the window is closed otherwise. */
    public void windowClose(CloseEvent e) {
        /* Return to initial state. */
    	btnRegister.setEnabled(true);
    }

}