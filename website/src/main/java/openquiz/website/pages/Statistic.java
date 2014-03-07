package openquiz.website.pages;

import openquiz.website.Website_UI;
import openquiz.website.pages.statistics.GameStatTab;
import openquiz.website.pages.statistics.IndividualStatTab;
import openquiz.website.pages.statistics.StatsPeriodSelector;
import openquiz.website.pages.statistics.TeamStatTab;
import openquiz.website.util.Messages;
import openquiz.website.util.SessionConstants;
import openquiz.website.util.WebServiceCache;

import org.vaadin.navigator.Navigator;

import com.vaadin.Application;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

public class Statistic extends PageBase implements Navigator.View {
	private static final long serialVersionUID = 2104597407186039607L;
	private VerticalLayout mainWindow;
	private WebServiceCache cache = new WebServiceCache();
	private String userKey;
	private StatsPeriodSelector statsPeriodSelector;
	private String authorization;
	
	public Statistic() {
		super(Messages.getString("All.statistics"));
	}

	@Override
	public void init(Navigator navigator, Application application) {

	}

	@Override
	public void navigateTo(String requestedDataId) {
		this.removeAllComponents();
		
		if(userKey == null){
			userKey = (String) ((Website_UI)this.getApplication()).getSession().getAttribute(SessionConstants.SESSION_USER_KEY);
			
			if(userKey == null){
				this.addComponent((new Label(Messages.getString("All.pleaseLogin")))); //$NON-NLS-1$
				return;
			}
			authorization = (String) ((Website_UI)this.getApplication()).getSession().getAttribute(SessionConstants.SESSION_AUTHORIZATION_STRING);
		}
		
		mainWindow = new VerticalLayout();
		
		statsPeriodSelector = new StatsPeriodSelector();
		mainWindow.addComponent(statsPeriodSelector);
		
		// Add a Stacked columnChart
		mainWindow.setSizeFull();

		this.addComponent(mainWindow);
		this.setSizeFull();
		
		TabSheet tabsheet = new TabSheet();
		mainWindow.addComponent(tabsheet);

		tabsheet.addTab(new IndividualStatTab(cache, tabsheet, statsPeriodSelector, authorization), "Individuel");
		tabsheet.addTab(new TeamStatTab(cache, tabsheet, statsPeriodSelector), "Équipe");
		tabsheet.addTab(new GameStatTab(cache, tabsheet, statsPeriodSelector), "Partie");
	}
	
	@Override
	public void attach() {
		super.attach();
	}

	@Override
	public String getWarningForNavigatingFrom() {
		return null;
	}
	
}
