package openquiz.website.windows;

import java.util.Locale;

import openquiz.website.Website_UI;
import openquiz.website.component.MenuBarComponent;
import openquiz.website.util.Messages;
import openquiz.website.util.Navigation;
import openquiz.website.util.Theme;

import org.vaadin.navigator.Navigator;

import com.vaadin.terminal.Sizeable;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.Runo;

public class MainWebSiteWindow extends Window {
	private static final long serialVersionUID = 1L;
	Navigation navigation = new Navigation();
	Navigator navigate = navigation.GetNavigator();

	public MainWebSiteWindow(){
		// Variables declarations
		VerticalLayout mainWindowLayout = new VerticalLayout();
		HorizontalSplitPanel split = new HorizontalSplitPanel();
		HorizontalSplitPanel header = new HorizontalSplitPanel();
		MenuBarComponent sidebar;
		VerticalLayout leftSideLayout = new VerticalLayout();
		VerticalLayout rightSideLayout = new VerticalLayout();
		Label labSlogan;
		Component compLinks;
		Component compLogo;
		
		labSlogan = new Label(Messages.getString("All.slogan")); //$NON-NLS-1$
		 
		setTheme("openquiz"); //$NON-NLS-1$
		mainWindowLayout.setMargin(true);
		mainWindowLayout.setSizeFull();
		
		// Add the links on the header of the application
		compLinks = AddLinks();
		mainWindowLayout.addComponent(compLinks);
		
		// Add the logo in the center of the application		
		compLogo = AddLogo();
		header.addComponent(compLogo);
		
		labSlogan.addStyleName(Runo.LABEL_H1);
		labSlogan.addStyleName("title");
		labSlogan.setSizeUndefined();
		header.addComponent(labSlogan);

		header.setSizeFull();
		header.setStyleName(Runo.SPLITPANEL_REDUCED);
		header.setSplitPosition(250, Sizeable.UNITS_PIXELS);
		header.setLocked(true);
		header.setHeight(compLogo.getHeight() + 15, compLogo.getHeightUnits());
		mainWindowLayout.addComponent(header);
		
		// Split the vertical Layout in two
		split.setSizeFull();
		split.setStyleName(Runo.SPLITPANEL_REDUCED);
		split.setSplitPosition(250, Sizeable.UNITS_PIXELS);
		split.setLocked(false);
		mainWindowLayout.addComponent(split);
		mainWindowLayout.setExpandRatio(split, 1);
		
		// Add the sidebar menu on the left
		sidebar = new MenuBarComponent(this,this);
		sidebar.setHeight("100%");
        leftSideLayout.addComponent(sidebar);
        leftSideLayout.setExpandRatio(sidebar, 1);
        leftSideLayout.setSizeFull();
		split.setFirstComponent(leftSideLayout);
	
        rightSideLayout.addComponent(navigate);
        rightSideLayout.setExpandRatio(navigate, 1);
        rightSideLayout.setSizeFull();
		split.setSecondComponent(rightSideLayout);
		split.setLocked(true);
		this.setContent(mainWindowLayout);
		
		mainWindowLayout.addStyleName("mainWindow");
	}
	private Component AddLogo() {
		ThemeResource logo = new ThemeResource("images/OpenQuizLogoWithName.png"); //$NON-NLS-1$
		Embedded image = new Embedded(null, logo);
		image.setWidth("200"); //$NON-NLS-1$
		image.setHeight("100"); //$NON-NLS-1$
		return image;
	}

	private Component AddLinks() {
		Button btnFr = new Button("FR"); //$NON-NLS-1$
		Button btnEn = new Button("EN"); //$NON-NLS-1$

		Label labSlash = new Label("/");
		GridLayout linksLayout = new GridLayout(200,2);
		
		btnFr.setStyleName(Theme.BUTTON_LINK);
		btnEn.setStyleName(Theme.BUTTON_LINK);

		linksLayout.addComponent(btnFr,197,1);
		linksLayout.addComponent(labSlash,198,1);
		linksLayout.addComponent(btnEn,199,1);
		linksLayout.setWidth("100%"); //$NON-NLS-1$
		
		btnFr.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = -968659092658868702L;

			public void buttonClick(ClickEvent event) {
				((Website_UI)getApplication()).setCurrentLocale(Locale.FRENCH);
				((Website_UI)getApplication()).close();
			}
		});

		btnEn.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = 6102977823423765623L;

			public void buttonClick(ClickEvent event) {
				((Website_UI)getApplication()).setCurrentLocale(Locale.ENGLISH);
				((Website_UI)getApplication()).close();
			}
		});
		
		return linksLayout;
	}
	
	public Navigation getNavigationInstance(){
		return navigation;
	}
}
