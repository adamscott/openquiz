package applicationTools;

import ca.openquiz.comms.RequestsWebService;
import ca.openquiz.comms.model.Session;

public class LoginManager {
	
	private static LoginManager INSTANCE = null;
	private static String loggedUsername;
	private static String rememberedUsername;
	private static Boolean isUserGuest;
	private static String authorization;
	
	public static LoginManager getInstance()
	{	
		if (INSTANCE == null)
		{ 
			INSTANCE = new LoginManager();
		}
			return INSTANCE;
	}
	
	public LoginManager(){
		loggedUsername = "";
		rememberedUsername = "";
		authorization = null;
		setIsUserGuest(false);
	}
	
	public void logAsGuest(){
		loggedUsername = "";
		setIsUserGuest(true);
	}
	
	public String getLoggedUsername(){
		return loggedUsername;
	}
	
	public String getLoggedUsernameOrGuest(){
		if (isUserGuest){
			return "Guest";
		}
		return loggedUsername;
	}
	
	public void logout()
	{
		if (loggedUsername != ""){
			RequestsWebService.logout(authorization);
			loggedUsername = "";
			authorization = null;
			setIsUserGuest(true);
		}
	}
	
	public String getRememberedUsername(){
		return rememberedUsername;
	}
	
	public void setRememberedUsername(String username){
		LoginManager.rememberedUsername = username;
	}
	
	public void setLoggedUsername(String loggedUsername){
		LoginManager.loggedUsername = loggedUsername;
	}
	
	public Boolean authenticateUser(String username, String password){
		//Call webservice for authentification
		//System.out.println("Login Attempt for " + username + " " + password);
		authorization = RequestsWebService.getAuthorization(username, password);
		Session authResult = RequestsWebService.login(authorization);
		
		if(authResult != null && authResult.getSessionKey() != null){
			loggedUsername = username;
			setIsUserGuest(false);
			return true;
		}
		else{
			loggedUsername = "";
			return false;
		}
	}

	public Boolean isUserGuest() {
		return isUserGuest;
	}

	public static void setIsUserGuest(Boolean isUserGuest) {
		LoginManager.isUserGuest = isUserGuest;
	}
	
	public boolean isAnyUserLogged(){
		if (isUserGuest == true || loggedUsername != ""){
			return true;
		}
		return false;
	}

	public String getAuthorization() {
		return authorization;
	}
	
	
}
