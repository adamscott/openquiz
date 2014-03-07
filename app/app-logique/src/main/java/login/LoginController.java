package login;

import ca.openquiz.comms.RequestsWebService;
import ca.openquiz.comms.response.KeyResponse;
import serialization.PreferencesImportExport;
import applicationTools.LoginManager;

public class LoginController {
	protected ILoginView view;
	
	public LoginController(){
		/*view.getBtnGuest().addActionListener(actionListenerLogAsGuest);
		view.getBtnConnect().addActionListener(actionListenerConnectUser);
		view.getTxtUsername().addActionListener(actionListenerTextFieldChanged);*/
//		TODO Put listener in view and logic somewhere in this class
		/*view.addActionToBtnGuest(actionLogAsGuest);
		view.addActionToBtnConnect(actionConnectUser);
		view.addActionToTxtUsername(actionTextFieldChanged);*/
		
		//view.setUsername(PreferencesImportExport.getInstance().getRememberedUsername());
		
		//view.checkForRememberedUser();
		/*if(view.getUsername().equals("")){
			view.setChckbxRememberMeChecked(false);
		}
		else{
			view.setChckbxRememberMeChecked(true);
		}*/

		//view.showView();
		/*view.getDialogWindow().setLocationRelativeTo(null);
		view.getDialogWindow().revalidate();
		view.getDialogWindow().setVisible(true);*/
	}
	
	public void actionTextFieldChanged(){
		System.out.println("Action");
	}

	public void actionLogAsGuest() {
		//String authorization = RequestsWebService.authorizeUser(view.getUsername(), view.getPassword());
		//KeyResponse result = RequestsWebService.verifyUserCredentials(authorization);
		LoginManager.getInstance().logAsGuest();
		
		view.closeView();
	}
	
	public void actionConnectUser() {
		if(view.validateFields()){
			Boolean authResult = LoginManager.getInstance().authenticateUser(view.getUsername(), view.getPassword());
			
			if(authResult){
				view.closeView();
			}
			else{
				//Show the user that he failed
				view.showWarning("Nom d'utilisateur et mot de passe invalide.");
			}
		}
	}
	
	public Object getView(){
		return view;
	}
	
	public void setView(ILoginView view){
		this.view = view;
	}
	
	//In the view now
	/*private Boolean validateFields(){
		Boolean isValidOk = true;
		view.setInfoLabelText("");
		view.changeUsernameFieldColor(Color.white);
		view.changePasswordFieldColor(Color.white);
		
		if(view.getUsername().equals("")){
			view.changeUsernameFieldColor(Color.red);
			isValidOk = false;
		}
		if(view.getPassword().length == 0){
			view.changePasswordFieldColor(Color.red);
			isValidOk = false;
		}
		return isValidOk;
	}*/

}
