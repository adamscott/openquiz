package ca.openquiz.mobile.login;

import login.ILoginView;
import applicationTools.LoginManager;

public class LoginController extends login.LoginController{

	private static LoginController instance = null;
	
	private LoginController()
	{
		super();
	}
	
	public static LoginController getInstance()
	{
		if(instance == null)
			instance = new LoginController();

		return instance;
	}
	
	@Override
	public void actionConnectUser()
	{
		if(view.getUsername().equals("") || view.getPassword().equals(""))
		{
			view.showEmptyFieldsWarning();
			return;
		}
		
		Boolean authResult = LoginManager.getInstance().authenticateUser(view.getUsername(), view.getPassword());

		if(authResult)
			((LoginView)view).continueLogin();
		else
			view.showAuthErrorWarning();
	}
	
	
	public void setView(ILoginView view){
		this.view = view;
	}

	public ILoginView getView() {
		return view;
	}
}
