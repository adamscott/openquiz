package openquiz.website.component;

import javax.servlet.http.HttpSession;

import openquiz.website.Website_UI;
import openquiz.website.util.Messages;
import openquiz.website.util.SessionConstants;
import openquiz.website.windows.EditAccountWindow;
import openquiz.website.windows.LoginWindow;
import openquiz.website.windows.RegisterWindow;
import ca.openquiz.comms.RequestsWebService;
import ca.openquiz.comms.model.Session;
import ca.openquiz.comms.model.User;
import ca.openquiz.comms.response.UserResponse;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;

public class LoginComponent extends CustomComponent {

	private static final long serialVersionUID = -7080867483481448659L;
	private VerticalLayout layout = new VerticalLayout();
	MenuBarComponent menuBar = null;
	//private transient HttpSession session;
	private Window mainWindow;
	
	//Strings language
	private String LABEL_WELCOME = Messages.getString("All.welcome");
	private String labelWelcomeGuest = Messages.getString("MainWebsiteWindow.labelWelcomeGuest");
	private String BUTTON_LOGOUT = Messages.getString("All.logout");
	private String BUTTON_ACCOUNTSETTINGS = Messages.getString("All.accountsettings");
	
	public LoginComponent( Window main, MenuBarComponent menuBarComponent){
		this.menuBar = menuBarComponent;
		mainWindow = main;
	}
	
	@Override
	public void attach() {
		super.attach();
		
		//Init the ui after attach because HttpSession cannot be find in constructor
		initUI();
	};
	
	private void initUI() {
		//Login component
		layout = new VerticalLayout();
		String userKey = null;
		
		HttpSession session = getSession();
		
		if(session != null) 
			userKey = (String)session.getAttribute(SessionConstants.SESSION_USER_KEY);
		boolean logged = userKey != null;
		
		if(!logged){
			layout.addComponent(new Label (labelWelcomeGuest));
			HorizontalLayout horizontalLayout = new HorizontalLayout();
			
			horizontalLayout.addComponent(new RegisterWindow("Window Opener", mainWindow));
			horizontalLayout.addComponent(new LoginWindow("Window Opener",mainWindow, this));
			layout.addComponent(horizontalLayout);
			
		}else{
			//Should we save the user somewhere?
			User user = RequestsWebService.getUser(userKey);

			if(user != null){
				showLogged(user.getFirstName() + " " + user.getLastName());
				menuBar.displayMenu(user.isAdmin());
			}
		}
		
		setCompositionRoot(layout);
	}

	public boolean authenticate (String email, String password ){
		boolean isValid = false;
		String authString = RequestsWebService.getAuthorization(email, password);
		Session webServiceSession = RequestsWebService.login(authString);

		if(webServiceSession != null && !webServiceSession.getKey().isEmpty()){
			User user = RequestsWebService.getUser(webServiceSession.getUserId());
			
			if (user != null) {
				isValid = true;
				getSession().setAttribute(SessionConstants.SESSION_USER_KEY, user.getKey());
				getSession().setAttribute(SessionConstants.SESSION_AUTHORIZATION_STRING, authString);
				getSession().setAttribute(SessionConstants.SESSION_WS_SESSION_KEY, webServiceSession.getSessionKey());
				RequestsWebService.setCurrentSessionId(webServiceSession.getSessionKey());
				showLogged(user.getFirstName() + " " + user.getLastName());
				menuBar.displayMenu(user.isAdmin());
			}
		}
		return isValid;
	}

	private void showLogged(String login) {
		layout = new VerticalLayout();
		layout.addComponent(new Label (LABEL_WELCOME + " " + login));
		Button btnDeconnection = new Button(BUTTON_LOGOUT);
		btnDeconnection.addListener(new ClickListener() {
			private static final long serialVersionUID = 6073857552878977395L;

			public void buttonClick(ClickEvent event) {
				getSession().removeAttribute(SessionConstants.SESSION_USER_KEY);
				RequestsWebService.setCurrentSessionId(null);
				((Website_UI)getApplication()).close();
				initUI();
			}
		});
		btnDeconnection.addStyleName("leftMenuButton");
		
		Button btnAccountSettings = new Button(BUTTON_ACCOUNTSETTINGS);
		btnAccountSettings.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {
				String authorization = (String) LoginComponent.this.getSession().getAttribute(SessionConstants.SESSION_AUTHORIZATION_STRING);
					UserResponse loggedUser = RequestsWebService.getCurrentUserInfo(authorization);
				LoginComponent.this.getApplication().getMainWindow().addWindow(new EditAccountWindow(loggedUser));
		    }
		});
		btnAccountSettings.addStyleName("leftMenuButton");
		
		layout.addComponent(btnAccountSettings);
		layout.addComponent(btnDeconnection);
		btnDeconnection.setSizeFull();
		btnAccountSettings.setSizeFull();
		setCompositionRoot(layout);
	}
	
	private HttpSession getSession(){
		return ((Website_UI)this.getApplication()).getSession();
	}
	
}
