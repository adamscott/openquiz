package homepage;

public interface IHomePageView {
	public void initWindow();
	public void showLogin();
	public Object getPanelContent();
	//Action
	public void registerActionOnBtnPlay();
	public void registerActionOnBtnJoinOnlineGame();
	public void registerActionOnBtnTutorial();
	public void registerActionOnTimerForLogin();
}
