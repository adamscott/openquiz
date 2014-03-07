package openquiz.website.windows;

import openquiz.website.component.LoginComponent;
import openquiz.website.util.Messages;
import openquiz.website.util.Theme;
import ca.openquiz.comms.RequestsWebService;

import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.themes.Runo;

public class LoginWindow extends CustomComponent implements Window.CloseListener {
	private static final long serialVersionUID = -8486123889532847860L;
	
	private Window windowMain;
	private Window windowLogin;
	private Window windowForgetPassword;
	private LoginComponent compLogin;
	private Button btnLogin;
	private Button btnValidate;
	private TextField txtEmail;
	private PasswordField fieldPassword;
	private Label lbTitle;
	private Button btnForgetPass;
	
	//String language
	private String BUTTON_LOGIN = Messages.getString("All.login");
	private String BUTTON_CONNECT = Messages.getString("All.connect");
	private String BUTTON_SEND = Messages.getString("All.send");
	private String BUTTON_CANCEL = Messages.getString("All.cancel");
	private String BUTTON_FORGETPASS = Messages.getString("All.forgetPassword");
	private String WINDOW_LOGIN = Messages.getString("MainWebsiteWindow.windowLogin");
	private String WINDOW_FORGETPASSWORD = Messages.getString("MainWebsiteWindow.windowforgetpassword");
	private String LABEL_TITLE = Messages.getString("MainWebsiteWindow.labelTitle");
	private String TEXT_EMAIL = Messages.getString("All.emailAddress");
	private String TEXT_PASSWORD = Messages.getString("All.password");
	private String NOTIFICATION_INVALID = Messages.getString("MainWebsiteWindow.notificationInvalid");
	private String NOTIFICATION_ENTER_EMAIL = Messages.getString("All.notification_enter_email");
	private String NOTIFICATION_EMAIL_SENT = Messages.getString("All.notification_email_sent");
	private String NOTIFICATION_CANCEL = Messages.getString("All.notification_cancel");

	public LoginWindow(String label, Window main, LoginComponent loginComponent) {
		windowMain = main;
		compLogin = loginComponent;
		// The component contains a button that opens the window.
		final FormLayout layout = new FormLayout();
		btnLogin = new Button(BUTTON_LOGIN, this, "RegisterButtonClick");
		btnLogin.setStyleName(Runo.BUTTON_LINK);
		layout.addComponent(btnLogin);
		
		setCompositionRoot(layout);
	}

	public void RegisterButtonClick(Button.ClickEvent event) {
		windowLogin = new Window(WINDOW_LOGIN);
		windowLogin.setModal(true);
		windowLogin.setTheme("openquiz");
		windowLogin.setHeight("265");
		windowLogin.setWidth("265");
		windowMain.addWindow(windowLogin);

		windowLogin.addListener(this);

		VerticalLayout layout = new VerticalLayout();
		layout.setSpacing(true);
		layout.setSizeFull();
		lbTitle = new Label(LABEL_TITLE);															lbTitle.setWidth("100%");
		txtEmail = new TextField(TEXT_EMAIL);														txtEmail.setWidth("100%");		
		fieldPassword = new PasswordField(TEXT_PASSWORD);											fieldPassword.setWidth("100%");		
		btnValidate = new Button(BUTTON_CONNECT, this, "loginButtonClick");							btnValidate.setWidth("100%");
		btnForgetPass = new Button(BUTTON_FORGETPASS, this, "ForgetPasswordButtonClick");			btnForgetPass.setStyleName(Theme.BUTTON_LINK);
		
		txtEmail.addShortcutListener(new ShortcutListener(null, ShortcutAction.KeyCode.ENTER, null) {
			private static final long serialVersionUID = 7704089377614435943L;

			@Override
		    public void handleAction(Object sender, Object target) {
		        loginButtonClick(null);
		    }
		});
		
		txtEmail.addShortcutListener(new ShortcutListener(null, ShortcutAction.KeyCode.ESCAPE, null) {
			private static final long serialVersionUID = 7704089377614435943L;

			@Override
		    public void handleAction(Object sender, Object target) {
				Component componentTarget = (Component) target;
				Window windowParent = null;
				while(windowParent == null && target != null && componentTarget != null)
				{
					if(componentTarget instanceof Window)
						windowParent = (Window) componentTarget;
					else
						componentTarget = componentTarget.getParent();
				}
				
				if(windowParent != null)
					getParent().getApplication().getMainWindow().removeWindow(windowParent);
		    }
		});
		
		// TODO HARDCODED FOR TESTS
		{
			// Dev
			//txtEmail.setValue("testgroups122@gmail.com"); fieldPassword.setValue("test");
			//txtEmail.setValue("testgroups131@gmail.com"); fieldPassword.setValue("test");
			//txtEmail.setValue("testgroups212@gmail.com"); fieldPassword.setValue("test");
			//txtEmail.setValue("testgroups321@gmail.com"); fieldPassword.setValue("test");
			
			// Prod
			//txtEmail.setValue("desrochf@csvdc.qc.ca"); fieldPassword.setValue("openquiz123");
			//txtEmail.setValue("alexandre.cl07@gmail.com"); fieldPassword.setValue("openquiz");
		}
		
		txtEmail.focus();
		layout.addComponent(lbTitle);
		layout.addComponent(txtEmail);
		layout.addComponent(fieldPassword);
		layout.addComponent(btnValidate);
		layout.addComponent(btnForgetPass);
		
		layout.setComponentAlignment(btnValidate, Alignment.BOTTOM_CENTER);
		layout.setComponentAlignment(btnForgetPass, Alignment.BOTTOM_CENTER);
		
		
		windowLogin.addComponent(layout);

		/* Allow opening only one window at a time. */
		btnLogin.setEnabled(false);
	}

	public void loginButtonClick(Button.ClickEvent event) {
		if (compLogin.authenticate((String)txtEmail.getValue(),	
			(String)fieldPassword.getValue())) {
			windowMain.removeWindow(windowLogin);

			/* Return to initial state. */
			btnLogin.setEnabled(true);
		}else{
			windowMain.showNotification(NOTIFICATION_INVALID);
		}
	}
	
	public void ForgetPasswordButtonClick(Button.ClickEvent event) {
		windowForgetPassword = new Window(WINDOW_FORGETPASSWORD);
		windowForgetPassword.setModal(true);
		windowForgetPassword.setResizable(false);
		windowForgetPassword.setTheme("openquiz");
		windowForgetPassword.setHeight("200");
		windowForgetPassword.setWidth("300");
		windowMain.addWindow(windowForgetPassword);
		
		VerticalLayout vLayout = new VerticalLayout();
		vLayout.setSizeFull();
		vLayout.setSpacing(true);
		vLayout.setMargin(true);
		
		final TextField txtEmail = new TextField(TEXT_EMAIL);
		txtEmail.setWidth("100%");
		Button btnSend = new Button(BUTTON_SEND);
		btnSend.setWidth("100%");
		btnSend.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;
			public void buttonClick(ClickEvent event) {
				String email = (String)txtEmail.getValue();
				if (!(email.isEmpty())){
					RequestsWebService.ResetPassword(email);
					windowMain.showNotification(NOTIFICATION_EMAIL_SENT);
					windowMain.removeWindow(windowForgetPassword);
				}
				else{
					windowMain.showNotification(NOTIFICATION_ENTER_EMAIL);
					txtEmail.setRequired(true);
				}
				
			}
		});
		Button btnCancel = new Button(BUTTON_CANCEL);
		btnCancel.setWidth("100%");
		btnCancel.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;
			public void buttonClick(ClickEvent event) {
				windowMain.removeWindow(windowForgetPassword);
				windowMain.showNotification(NOTIFICATION_CANCEL);
			}
		});
		HorizontalLayout hLayout = new  HorizontalLayout();
		hLayout.setSizeFull();
		hLayout.addComponent(btnSend);
		hLayout.addComponent(btnCancel);
		vLayout.addComponent(txtEmail);
		vLayout.addComponent(hLayout);
		
		windowForgetPassword.setContent(vLayout);

	}

	/** In case the window is closed otherwise. */
	public void windowClose(CloseEvent e) {
		/* Return to initial state. */
		btnLogin.setEnabled(true);
	}
}
