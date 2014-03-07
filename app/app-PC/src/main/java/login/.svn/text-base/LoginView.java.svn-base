package login;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import applicationTools.ButtonManager;
import applicationTools.CustomButton;
import applicationTools.LoginManager;
import applicationTools.WindowManager;
import customWindow.CustomDialog;

public class LoginView extends CustomDialog implements ILoginView {
	private JTextField txtUsername;
	private JLabel lblPassword;
	private JLabel lblUsername;
	//private CustomButton btnGuest;
	private CustomButton btnConnect;
	private JPasswordField txtPassword;
	private JHyperlinkLabel lblForgotPassword;
	private JHyperlinkLabel lblCreateNewUser;
	//private Box horizontalBox;
	//private Component horizontalStrut;
	private JLabel lblInfo;
	private JCheckBox chckbxRememberMe;
	private LoginController controller;

	/**
	 * Create the panel.
	 */
	public LoginView() 
	{
		lblUsername = new JLabel("Utilisateur :");
		txtUsername = new JTextField();
		lblPassword = new JLabel("Mot de passe :");
		txtPassword = new JPasswordField();
		lblForgotPassword = new JHyperlinkLabel("Mot de passe oubli�");
		lblCreateNewUser = new JHyperlinkLabel("Nouvel utilisateur");
		//horizontalBox = Box.createHorizontalBox();
		//btnGuest = new CustomButton("<html><center>Continuer sans <br/> authentification</center></html>", null, ButtonManager.getTextButtonDimension());
		//horizontalStrut = Box.createHorizontalStrut(20);
		btnConnect = new CustomButton("S'authentifier", null, ButtonManager.getTextButtonDimension());
		lblInfo = new JLabel("");
		chckbxRememberMe = new JCheckBox("Se souvenir de mes informations d'authentification.");
		controller = new LoginController();
		
		initGUI();
	}
	
	private void initGUI()
	{
		panelContent.setLayout(new MigLayout("", "[grow][][200px:200px][grow]", "[35.00]0[][25.00][25.00][27.00][40.00]"));
		
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panelContent.add(lblUsername, "cell 1 0,growx,aligny center");
		
		panelContent.add(txtUsername, "cell 2 0,growx,aligny center");
		txtUsername.setColumns(10);
		
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panelContent.add(lblPassword, "cell 1 2,growx,aligny center");
		
		panelContent.add(txtPassword, "cell 2 2,growx,aligny center");

		lblInfo.setForeground(Color.red);
		panelContent.add(lblInfo, "cell 1 4 2 1");
		
		panelContent.add(btnConnect, "cell 1 5 2 1,growx,alignx center");
		
		btnConnect.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		btnConnect.addActionListener(actionBtnLogUser);
		
		disableQuitBtn();
	}
	
	/*private ActionListener actionBtnLogAsGuest = new ActionListener() {
		public void actionPerformed(ActionEvent e) {		
			controller.actionLogAsGuest();
			WindowManager.getInstance().getMainWindow().setLoggedUsername(LoginManager.getInstance().getLoggedUsernameOrGuest());
		}
	};*/
	
	private ActionListener actionBtnLogUser = new ActionListener() {
		public void actionPerformed(ActionEvent e) {		
			controller.actionConnectUser();
			WindowManager.getInstance().getMainWindow().setLoggedUsername(LoginManager.getInstance().getLoggedUsernameOrGuest());
		}
	};
	
	public void setController(LoginController controller){
		this.controller = controller;
	}
	
	/*public CustomButton getBtnGuest(){
		return btnGuest;
	}*/
	
	public CustomButton getBtnConnect(){
		return btnConnect;
	}
	
	public String getUsername(){
		return txtUsername.getText();
	}
	
	public String getPassword(){
		return String.valueOf(txtPassword.getPassword());
	}
	
	public void changeUsernameFieldColor(Color color){
		txtUsername.setBackground(color);
	}
	
	public void changePasswordFieldColor(Color color){
		txtPassword.setBackground(color);
	}
	
	public void setInfoLabelText(String text){
		lblInfo.setText(text);
	}
	
	public Boolean isRememberUserChecked(){
		return chckbxRememberMe.isSelected();
	}
	
	public void setUsername(String username){
		txtUsername.setText(username);
	}
	
	public void setChckbxRememberMeChecked(Boolean isSelected){
		chckbxRememberMe.setSelected(isSelected);
	}
	
	public JTextField getTxtUsername() {
		return txtUsername;
	}

	public void setTxtUsername(JTextField txtUsername) {
		this.txtUsername = txtUsername;
	}

	public JPasswordField getTxtPassword() {
		return txtPassword;
	}

	public void setTxtPassword(JPasswordField txtPassword) {
		this.txtPassword = txtPassword;
	}

	public void showView() {
		// TODO Auto-generated method stub
		
	}

	public void checkForRememberedUser() {
		// TODO Auto-generated method stub
		
	}

	public void closeView() {
		getDialogWindow().dispose();
	}

	public Boolean validateFields() {
		boolean isValidationOk = true;
		txtUsername.setBackground(Color.white);
		txtPassword.setBackground(Color.white);
		//System.out.println(txtUsername.getText());
		if (txtUsername.getText().equals("")){
			txtUsername.setBackground(Color.red);
			isValidationOk = false;
		}

		if (txtPassword.getPassword().length == 0){
			txtPassword.setBackground(Color.red);
			isValidationOk = false;
		}
		return isValidationOk;
	}

	public void showWarning(String warning) {
		setInfoLabelText("Nom d'utilisateur et mot de passe invalide.");
	}

	public void changeUsernameFieldColor(String R, String G, String B) {
		// TODO Auto-generated method stub
		
	}

	public void changePasswordFieldColor(String R, String G, String B) {
		// TODO Auto-generated method stub
		
	}

	public void registerActionOnBtnLogAsGuest() {
		// TODO Auto-generated method stub
		
	}

	public void registerActionOnBtnConnect() {
		// TODO Auto-generated method stub
		
	}

	public void registerActionOnTxtFieldUsername() {
		// TODO Auto-generated method stub
		
	}
	
	public void showWindow(){
		getDialogWindow().setLocationRelativeTo(null);
		getDialogWindow().revalidate();
		getDialogWindow().setVisible(true);
	}

	public void showAuthErrorWarning() {
		// TODO Auto-generated method stub
		
	}

	public void showEmptyFieldsWarning() {
		// TODO Auto-generated method stub
		
	}
}
