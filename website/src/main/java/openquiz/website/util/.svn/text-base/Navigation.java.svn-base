package openquiz.website.util;

import java.io.Serializable;

import openquiz.website.pages.AboutUs;
import openquiz.website.pages.ContactUs;
import openquiz.website.pages.Download;
import openquiz.website.pages.Home;
import openquiz.website.pages.ManageGroups;
import openquiz.website.pages.ManageQuestionSets;
import openquiz.website.pages.ManageQuestions;
import openquiz.website.pages.ManageTeams;
import openquiz.website.pages.ManageTemplates;
import openquiz.website.pages.ManageUsers;
import openquiz.website.pages.Statistic;

import org.vaadin.navigator.Navigator;

import com.vaadin.ui.Button;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.VerticalLayout;

public class Navigation implements Serializable{
	private static final long serialVersionUID = -2375484053012271845L;
	
	final public Navigator navigator = new Navigator();
	private Button btnHome;
	private Button btnAboutUs;
	private Button btnContactUs;
	private Button btnDownload;
	private Button btnStatistic;
	private Button btnManageTeams;
	private Button btnManageGroups;
	private Button btnManageQuestions;
	private Button btnManageQuestionSets;
	private Button btnManageTemplates;
	private Button btnManageUsers;
	private Button btnRedirectDownload;
	
	//Strings languages
	private String BUTTON_HOME = Messages.getString("All.home");
	private String BUTTON_ABOUTUS = Messages.getString("All.aboutUs");
	private String BUTTON_CONTACTUS = Messages.getString("All.contactUs");
	private String BUTTON_DOWNLOAD = Messages.getString("All.download");
	private String BUTTON_STATISTIC = Messages.getString("All.statistics");
	private String BUTTON_MANAGETEAMS = Messages.getString("All.manageTeams");
	private String BUTTON_MANAGEGROUPS = Messages.getString("All.manageGroups");
	private String BUTTON_MANAGEQUESTIONS = Messages.getString("All.manageQuestions");
	private String BUTTON_MANAGEQUESTIONSETS = Messages.getString("All.manageQuestionSets");
	private String BUTTON_MANAGETEMPLATES = Messages.getString("All.manageTemplates");
	private String BUTTON_MANAGEUSERS = Messages.getString("All.manageUsers");
	
	public Navigation(){
		// Create the navigator and links classes to it
		navigator.addView("Home", Home.class);
		navigator.addView("AboutUs", AboutUs.class);
		navigator.addView("Download", Download.class);
		navigator.addView("ContactUs", ContactUs.class);
		navigator.addView("Statistic", Statistic.class);
		navigator.addView("ManageGroups", ManageGroups.class);
		navigator.addView("ManageTeams", ManageTeams.class);
		navigator.addView("ManageQuestions", ManageQuestions.class);
		navigator.addView("ManageQuestionSets", ManageQuestionSets.class);
		navigator.addView("ManageTemplates", ManageTemplates.class);
		navigator.addView("ManageUsers", ManageUsers.class);
	}
	
	public Navigator GetNavigator(){
		return navigator;
	}

	public Layout HeaderMenu(){
		
		btnHome = new Button(BUTTON_HOME, new Button.ClickListener() {
			private static final long serialVersionUID = -4661859939114424530L;

			@Override
            public void buttonClick(ClickEvent event) {
				navigator.navigateTo(Home.class);
            }
        });
		btnHome.addStyleName("leftMenuButton");
		
		btnAboutUs = new Button(BUTTON_ABOUTUS, new Button.ClickListener() {
			private static final long serialVersionUID = 17758813484534185L;

			@Override
            public void buttonClick(ClickEvent event) {
				navigator.navigateTo(AboutUs.class);
            }
        });
		btnAboutUs.addStyleName("leftMenuButton");
		
		btnContactUs = new Button(BUTTON_CONTACTUS, new Button.ClickListener() {
			private static final long serialVersionUID = -5996203972726842501L;

			@Override
            public void buttonClick(ClickEvent event) {
				navigator.navigateTo(ContactUs.class);
            }
        });
		btnContactUs.addStyleName("leftMenuButton");
		
		btnDownload = new Button(BUTTON_DOWNLOAD, new Button.ClickListener() {
			private static final long serialVersionUID = 7136893544393006347L;

			@Override
            public void buttonClick(ClickEvent event) {
				navigator.navigateTo(Download.class);
            }
        });
		btnDownload.addStyleName("leftMenuButton");
		
		btnHome.setSizeFull();
		btnAboutUs.setSizeFull();
		btnContactUs.setSizeFull();
		btnDownload.setSizeFull();
		VerticalLayout navigationLayout = new VerticalLayout();
		navigationLayout.addComponent(btnHome);
		navigationLayout.addComponent(btnAboutUs);
		navigationLayout.addComponent(btnContactUs);
		navigationLayout.addComponent(btnDownload);
		
		return navigationLayout;	
	}
	
	public Layout SideMenu(){
		
		btnStatistic = new Button(BUTTON_STATISTIC, new Button.ClickListener() {
			private static final long serialVersionUID = -7297776728549393106L;

			@Override
			public void buttonClick(ClickEvent event) {
				navigator.navigateTo(Statistic.class);
			}
        });
		btnStatistic.addStyleName("leftMenuButton");
			
		btnManageTeams = new Button(BUTTON_MANAGETEAMS, new Button.ClickListener() {
			private static final long serialVersionUID = -7472414926258515857L;

			@Override
			public void buttonClick(ClickEvent event) {
				navigator.navigateTo(ManageTeams.class);
			}
        });
		btnManageTeams.addStyleName("leftMenuButton");
		
		btnManageGroups = new Button(BUTTON_MANAGEGROUPS, new Button.ClickListener() {
			private static final long serialVersionUID = 8473168029856557282L;

			@Override
			public void buttonClick(ClickEvent event) {
				navigator.navigateTo(ManageGroups.class);
			}
        });
		btnManageGroups.addStyleName("leftMenuButton");
		
		btnManageQuestions = new Button(BUTTON_MANAGEQUESTIONS, new Button.ClickListener() {
			private static final long serialVersionUID = -718196641893797485L;

			@Override
			public void buttonClick(ClickEvent event) {
				navigator.navigateTo(ManageQuestions.class);
			}
        });
		btnManageQuestions.addStyleName("leftMenuButton");
		
		btnManageQuestionSets = new Button(BUTTON_MANAGEQUESTIONSETS, new Button.ClickListener() {
			private static final long serialVersionUID = -7023907831451801803L;

			@Override
			public void buttonClick(ClickEvent event) {
				navigator.navigateTo(ManageQuestionSets.class);
			}
        });
		btnManageQuestionSets.addStyleName("leftMenuButton");
		
		btnManageTemplates = new Button(BUTTON_MANAGETEMPLATES, new Button.ClickListener() {
			private static final long serialVersionUID = -7023907831451801803L;

			@Override
			public void buttonClick(ClickEvent event) {
				navigator.navigateTo(ManageTemplates.class);
			}
        });
		btnManageTemplates.addStyleName("leftMenuButton");
		
		btnManageUsers = new Button(BUTTON_MANAGEUSERS, new Button.ClickListener() {
			private static final long serialVersionUID = -7023907831451801803L;

			@Override
			public void buttonClick(ClickEvent event) {
				navigator.navigateTo(ManageUsers.class);
			}
        });
		btnManageUsers.addStyleName("leftMenuButton");
		
		btnStatistic.setSizeFull();
		btnManageTeams.setSizeFull();
		btnManageGroups.setSizeFull();
		btnManageQuestions.setSizeFull();
		btnManageQuestionSets.setSizeFull();
		btnManageTemplates.setSizeFull();
		btnManageUsers.setSizeFull();
		VerticalLayout SideNavigationLayout = new VerticalLayout();
		
		SideNavigationLayout.addComponent(btnStatistic);
		SideNavigationLayout.addComponent(btnManageTeams);
		SideNavigationLayout.addComponent(btnManageGroups);
		SideNavigationLayout.addComponent(btnManageQuestions);
		SideNavigationLayout.addComponent(btnManageQuestionSets);
		SideNavigationLayout.addComponent(btnManageTemplates);
		SideNavigationLayout.addComponent(btnManageUsers);
		
		return SideNavigationLayout;
	}
	public Button RedirectButton(String label){
		btnRedirectDownload = new Button(label, new Button.ClickListener() {
			private static final long serialVersionUID = 7136893544393006347L;

			@Override
            public void buttonClick(ClickEvent event) {
				navigator.navigateTo(Download.class);
            }
        });
		btnRedirectDownload.addStyleName("leftMenuButton");
		
		return btnRedirectDownload;
	}
	
	public void updateUser(boolean isAdmin)
	{
		btnManageTemplates.setVisible(isAdmin);
		btnManageUsers.setVisible(isAdmin);
	}
}
