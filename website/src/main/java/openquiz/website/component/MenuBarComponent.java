package openquiz.website.component;

import openquiz.website.pages.ManageQuestions;
import openquiz.website.windows.MainWebSiteWindow;

import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class MenuBarComponent extends VerticalLayout {
	private static final long serialVersionUID = -5399919279688560817L;
	
	private VerticalLayout headerLayout = new VerticalLayout();
	private VerticalLayout mainLayout = new VerticalLayout();
	private VerticalLayout menuLayout = new VerticalLayout();
	MainWebSiteWindow websiteUI;
	
	
	//Pages
	ManageQuestions manageQuestionsPage = null;
	
	public MenuBarComponent(Window main, MainWebSiteWindow websiteUI){
		
		Panel panel = new Panel("Menu");
		headerLayout.setMargin(true);
		
		this.websiteUI = websiteUI;
		headerLayout.addComponent(new LoginComponent(main,this));
		mainLayout.addComponent(headerLayout);
		mainLayout.addComponent(new Label("<br/><hr/><br/>",Label.CONTENT_XHTML));
		mainLayout.addComponent(websiteUI.getNavigationInstance().HeaderMenu());
		mainLayout.addComponent(menuLayout);
		menuLayout.addComponent(new Label("<br/><hr/><br/>",Label.CONTENT_XHTML));
		menuLayout.addComponent(websiteUI.getNavigationInstance().SideMenu());
		menuLayout.setVisible(false);
		
		panel.setContent(mainLayout);
		panel.setSizeFull();
		this.addComponent(panel);
	}
	
	public void displayMenu(boolean isAdmin){
		websiteUI.getNavigationInstance().updateUser(isAdmin);
		menuLayout.setVisible(true);
	}

	public void emptySideMenu() {
		menuLayout.setVisible(false);
	}	
}
