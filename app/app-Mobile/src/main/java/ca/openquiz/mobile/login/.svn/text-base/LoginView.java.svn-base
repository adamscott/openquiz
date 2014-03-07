package ca.openquiz.mobile.login;

import login.ILoginView;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import ca.openquiz.mobile.R;
import ca.openquiz.mobile.util.AlertBuilder;
import ca.openquiz.mobile.wizardTeam.WizardTeamAct;

public class LoginView implements ILoginView{

	private Activity loginActivity;
	
	public void setLoginActivity(Activity loginActivity) {
		this.loginActivity = loginActivity;
	}
	
	public void continueLogin()
	{
		showLoading(false);
		
		Intent intent = new Intent(loginActivity.getBaseContext(), WizardTeamAct.class);
		loginActivity.startActivity(intent);
	}
	
	public void showLoading(final boolean value)
	{
		new Handler(loginActivity.getBaseContext().getMainLooper()).post(new Runnable() {
			public void run() {
				if(value)
					loginActivity.findViewById(R.id.loading).setVisibility(View.VISIBLE);
				else
					loginActivity.findViewById(R.id.loading).setVisibility(View.INVISIBLE);
			}});
	}
	
	@Override
	public void setUsername(String username) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void checkForRememberedUser() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Boolean validateFields() {
		return null;
	}

	@Override
	public String getUsername() {
		return ((EditText)loginActivity.findViewById(R.id.txtUser)).getText().toString();
	}

	@Override
	public String getPassword() {
		return ((EditText)loginActivity.findViewById(R.id.txtPassword)).getText().toString();
	}

	@Override
	public Boolean isRememberUserChecked() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void showAuthErrorWarning() {
		new Handler(loginActivity.getBaseContext().getMainLooper()).post(new Runnable() {
			public void run() {
				String title = loginActivity.getResources().getString(R.string.message_login_error_title);
				String message = loginActivity.getResources().getString(R.string.message_login_error_message);
				AlertBuilder.showPopUp(loginActivity, title, message);
			}});
		showLoading(false);
	}
	
	@Override
	public void showEmptyFieldsWarning() {
		showLoading(false);
		
		new Handler(loginActivity.getBaseContext().getMainLooper()).post(new Runnable() {
			public void run() {
				String title = loginActivity.getResources().getString(R.string.message_login_error_title);
				String message = loginActivity.getResources().getString(R.string.message_empty_fields_message);
				AlertBuilder.showPopUp(loginActivity, title, message);
			}});
		showLoading(false);
	}

	@Override
	public void changeUsernameFieldColor(String R, String G, String B) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changePasswordFieldColor(String R, String G, String B) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerActionOnBtnLogAsGuest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerActionOnBtnConnect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerActionOnTxtFieldUsername() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showWarning(String warning) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showWindow() {
		// TODO Auto-generated method stub
		
	}
}
