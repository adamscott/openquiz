package openquiz.website.pages.statistics;

import java.util.List;

import openquiz.website.Website_UI;
import openquiz.website.util.Messages;
import openquiz.website.util.SessionConstants;
import openquiz.website.util.WebServiceCache;

import org.vaadin.vaadinvisualizations.PieChart;

import ca.openquiz.comms.RequestsWebService;
import ca.openquiz.comms.model.Game;
import ca.openquiz.comms.model.GameLog;
import ca.openquiz.comms.model.Team;
import ca.openquiz.comms.model.User;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;

public class GameStatTab extends CustomComponent{

	private static final long serialVersionUID = 7647026533264689718L;
	private WebServiceCache cache = null;
	
	private static String statsForTeamString = Messages.getString("GameStatTab.statsPlayerInTeamLabel"); //$NON-NLS-1$
	private static String questionsRepartition = Messages.getString("GameStatTab.answersRepartitionLabel"); //$NON-NLS-1$
	
	private ComboBox playersTeam1 = new ComboBox(statsForTeamString);
	private ComboBox playersTeam2 = new ComboBox(statsForTeamString);
	private ComboBox gameComboBox = new ComboBox(Messages.getString("GameStatTab.game")); //$NON-NLS-1$
	private Label lblDate = new Label(Messages.getString("GameStatTab.selectGameNotification")); //$NON-NLS-1$
	private Label lblScore = new Label();
	private PieChart pieChartTeam1 = null;
	private PieChart pieChartTeam2 = null;
	private List<GameLog> gameLogs = null;
	private Table gameActionsLog = null;
	
	private String userKey;
	private boolean tabLoaded = false;
	private boolean isSelected = false;
	
	public GameStatTab(WebServiceCache webServiceCache, TabSheet tabsheet, final StatsPeriodSelector statsPeriodSelector){
		this.cache = webServiceCache;
		
		statsPeriodSelector.addChangeListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 3122992412229374120L;
			@Override
			public void valueChange(ValueChangeEvent event) {
				if(isSelected)
					GameStatTab.this.updateTab((int)event.getProperty().getValue());
			}
		});
		
		//This is to make the tab load only when selected
		tabsheet.addListener( new TabSheet.SelectedTabChangeListener() {
			private static final long serialVersionUID = 3220047043655101547L;
			@Override
			public void selectedTabChange(SelectedTabChangeEvent event) {
				if(event.getTabSheet().getSelectedTab() == GameStatTab.this ){
					isSelected = true;
					if(!tabLoaded){
						buildUI();
						addListeners();
						tabLoaded = true;
					}
					populateUI(statsPeriodSelector.getSelectedValue());
				}else{
					isSelected = false;
				}
				
			}
		});
	}
	
	protected void updateTab(int value) {
		populateUI(value);
	}

	@Override
	public void attach() {
		super.attach();
		userKey = (String) ((Website_UI)this.getApplication()).getSession().getAttribute(SessionConstants.SESSION_USER_KEY);
	}
	
	private void addListeners() {
		playersTeam1.addListener(new ComboBox.ValueChangeListener() {
			private static final long serialVersionUID = -3262891914655881350L;
			@Override
			public void valueChange(ValueChangeEvent event) {
				User user = (User)playersTeam1.getValue();
				updateChart(user, pieChartTeam1);
			}
		});
		
		playersTeam2.addListener(new ComboBox.ValueChangeListener() {
			private static final long serialVersionUID = -3262891914655881350L;
			@Override
			public void valueChange(ValueChangeEvent event) {
				User user = (User)playersTeam2.getValue();
				updateChart(user, pieChartTeam2);
			}
		});
		
		gameComboBox.addListener( new ComboBox.ValueChangeListener() {
			private static final long serialVersionUID = -3262891914671341350L;
			@Override
			public void valueChange(ValueChangeEvent event) {
				Game game = (Game)gameComboBox.getValue();
				if(game != null){
					Team team1 = cache.getTeam(game.getTeam1());
					Team team2 = cache.getTeam(game.getTeam2());
					gameLogs = RequestsWebService.getGameLogs(game.getKey(), null);
					
					Team winnerTeam = null;
					int winnerScore = 0;
					Team loserTeam = null;
					int loserScore = 0;
					if(game.getTeam1Score() > game.getTeam2Score()){
						winnerTeam = team1;
						winnerScore = game.getTeam1Score();
						loserTeam = team2;
						loserScore = game.getTeam2Score();
					}else if(game.getTeam2Score() > game.getTeam1Score()){
						winnerTeam = team2;
						winnerScore = game.getTeam2Score();
						loserTeam = team1;
						loserScore = game.getTeam1Score();
					}
					
					if(game.getTeam1Score() != game.getTeam2Score()){
						lblScore.setValue(Messages.getString("GameStatTab.theTeam") + winnerTeam.getName() + Messages.getString("GameStatTab.teamWonGameVersus") + winnerScore + Messages.getString("GameStatTab.pointsAgainst") + loserScore + Messages.getString("GameStatTab.pointsForTeamX") + loserTeam.getName() + "\""); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
					}else{
						lblScore.setValue(Messages.getString("GameStatTab.theGameX") + team1.getName() + Messages.getString("GameStatTab.versus") + team2.getName() + Messages.getString("GameStatTab.gameTiedWithScore") + game.getTeam1Score()); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					}
					
					//Populate dropbox team1
					playersTeam1.removeAllItems();
					playersTeam1.setCaption(statsForTeamString + team1.getName());
					for(String key : game.getTeam1Players()){
						playersTeam1.addItem(cache.getUser(key));
					}
					playersTeam1.setValue(playersTeam1.getItemIds().iterator().next());
	
					//Populate dropbox team2
					playersTeam2.removeAllItems();
					playersTeam2.setCaption(statsForTeamString + team1.getName());
					for(String key :  game.getTeam2Players()){
						playersTeam2.addItem(cache.getUser(key));
					}
					playersTeam2.setValue(playersTeam2.getItemIds().iterator().next());
					
					updateTableContent();
				}
			}
		}); 
	}

	private void populateUI(int nb) {
		List<Game> games = RequestsWebService.getGames(false, nb, 1, userKey);
		gameComboBox.removeAllItems();
		if(games != null){
			for(Game game : games){
				gameComboBox.addItem(game);
			}
		}
	}

	private void buildUI() {
		VerticalLayout vl = new VerticalLayout();
		vl.setMargin(true);
		
		gameComboBox.setWidth("300px"); //$NON-NLS-1$
		gameComboBox.setImmediate(true);
		gameComboBox.setNullSelectionAllowed(false);

		HorizontalLayout horLayoutAnswer = new HorizontalLayout();
		playersTeam1.setImmediate(true);
		playersTeam2.setImmediate(true);
		playersTeam1.setNullSelectionAllowed(false);
		playersTeam2.setNullSelectionAllowed(false);
		
		VerticalLayout verLayoutTeam1 = new VerticalLayout();
		verLayoutTeam1.addComponent(playersTeam1);
		pieChartTeam1 = createPieChart(questionsRepartition);
		verLayoutTeam1.addComponent(pieChartTeam1);

		VerticalLayout verLayoutTeam2 = new VerticalLayout();

		verLayoutTeam2.addComponent(playersTeam2);
		pieChartTeam2 = createPieChart(questionsRepartition);
		verLayoutTeam2.addComponent(pieChartTeam2);

		horLayoutAnswer.addComponent(verLayoutTeam1);
		horLayoutAnswer.addComponent(verLayoutTeam2);

		vl.addComponent(gameComboBox);
		vl.addComponent(lblDate);
		vl.addComponent(lblScore);
		vl.addComponent(new Label("<br/><hr/><br/>",Label.CONTENT_XHTML)); //$NON-NLS-1$

		vl.addComponent(horLayoutAnswer);

		gameActionsLog = new Table(Messages.getString("GameStatTab.gameDetails")); //$NON-NLS-1$
		gameActionsLog.addContainerProperty(Messages.getString("GameStatTab.player"), String.class, null); //$NON-NLS-1$
		gameActionsLog.addContainerProperty(Messages.getString("GameStatTab.team"), String.class, null); //$NON-NLS-1$
		gameActionsLog.addContainerProperty(Messages.getString("GameStatTab.response"), String.class, null); //$NON-NLS-1$
		gameActionsLog.addContainerProperty(Messages.getString("GameStatTab.points"), String.class, null); //$NON-NLS-1$
		gameActionsLog.setWidth("100%"); //$NON-NLS-1$
		vl.addComponent(gameActionsLog);
		this.setCompositionRoot(vl);
	}
	
	private void updateTableContent(){
		gameActionsLog.removeAllItems();
		String goodAnswerStr = Messages.getString("GameStatTab.goodAnswer"); //$NON-NLS-1$
		String badAnswerStr = Messages.getString("GameStatTab.badAnswer"); //$NON-NLS-1$
		String penaltyStr = Messages.getString("GameStatTab.penalty"); //$NON-NLS-1$
		if(gameLogs != null){
			for(GameLog log : gameLogs){
				String logAnswer = null;
				if(log.isAnswer()){
					logAnswer = goodAnswerStr;
				}else if(log.getPoints() < 0){
					logAnswer = penaltyStr;
				}else{
					logAnswer = badAnswerStr;
				}
				gameActionsLog.addItem(new Object[]{cache.getUser(log.getUserKey()), cache.getTeam(log.getTeamKey()), logAnswer, Integer.toString(log.getPoints())}, log.getKey());
			}
		}
	}

	private void updateChart(User user, PieChart pieChartTeam) {
		int penalty = 0;
		int goodAnswer = 0;
		int badAnswer = 0;
		
		String goodAnswerStr = Messages.getString("GameStatTab.goodAnswer"); //$NON-NLS-1$
		pieChartTeam.remove(goodAnswerStr);
		
		String badAnswerStr = Messages.getString("GameStatTab.badAnswer"); //$NON-NLS-1$
		pieChartTeam.remove(badAnswerStr);
		
		String penaltyStr = Messages.getString("GameStatTab.penalty"); //$NON-NLS-1$
		pieChartTeam.remove(penaltyStr);
		
		if(user == null) 
			return;
		
		
		if(gameLogs != null){
			for(GameLog log : gameLogs){
				if(log.getUserKey().equals(user.getKey())){
					if(log.isAnswer()){
						goodAnswer++;
					}else if(log.getPoints() < 0){
						penalty++;
					}else{
						badAnswer++;
					}
				}
			}
		}
		
		if(!(goodAnswer == 0 && badAnswer == 0 && penalty == 0)){
			pieChartTeam.add(goodAnswerStr, goodAnswer);
			pieChartTeam.add(badAnswerStr, badAnswer);
			pieChartTeam.add(penaltyStr, penalty);
		}
	}

	public PieChart createPieChart(String title){
		PieChart pc = new PieChart();	
		pc.setOption("is3D", true); //$NON-NLS-1$
		pc.setOption("width", 500);	 //$NON-NLS-1$
		pc.setOption("height", 500); //$NON-NLS-1$
		pc.setOption("legend.position", "left"); //$NON-NLS-1$ //$NON-NLS-2$
		pc.setOption("legend.alignment", "start"); //$NON-NLS-1$ //$NON-NLS-2$
		pc.setOption("title", title); //$NON-NLS-1$
		pc.setOption("chartArea", "{left:0,top:0,width:'100%',height:'100%'}"); //$NON-NLS-1$ //$NON-NLS-2$
		pc.setSizeFull();
		pc.setColors("#FF0000","#0000FF","#AA00AA","#880000","#550000"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
		
		pc.setWidth("400px"); //$NON-NLS-1$
		pc.setHeight("400px"); //$NON-NLS-1$
		return pc;
	}
	
}
