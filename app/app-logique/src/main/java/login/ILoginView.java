package login;

public interface ILoginView {
	
	public void setUsername(String username);
	public void showView();
	public void checkForRememberedUser();
	public void closeView();
	public Boolean validateFields();
	public String getUsername();
	public String getPassword();
	public Boolean isRememberUserChecked();
	public void showWarning(String warning);
	public void showAuthErrorWarning();
	public void showEmptyFieldsWarning();
	public void changeUsernameFieldColor(String R, String G, String B);
	public void changePasswordFieldColor(String R, String G, String B);
	//Action
	public void registerActionOnBtnLogAsGuest();
	public void registerActionOnBtnConnect();
	public void registerActionOnTxtFieldUsername();
	public void showWindow();

}
