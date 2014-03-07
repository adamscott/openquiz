package ca.openquiz.mobile.login;

import android.app.Activity;

public class LoginTask {

	private LoginController loginController = LoginController.getInstance(); 
	
    public Runnable initLoginAct(final Activity act)
    {
    	Runnable init = new Runnable() {

            public void run() {
        		loginController.setView(new LoginView());   
        		((LoginView)loginController.getView()).setLoginActivity(act);
        		
        		loginController.actionConnectUser();
            }
        };
        
        return init;
    }
	
}
