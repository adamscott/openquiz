package openquiz.website.pages.statistics;

import java.util.List;

import openquiz.website.Website_UI;
import openquiz.website.util.Messages;
import openquiz.website.util.SessionConstants;
import openquiz.website.util.WebServiceCache;

import org.vaadin.vaadinvisualizations.LineChart;
import org.vaadin.vaadinvisualizations.PieChart;

import ca.openquiz.comms.RequestsWebService;
import ca.openquiz.comms.enums.CategoryType;
import ca.openquiz.comms.enums.QuestionType;
import ca.openquiz.comms.model.BaseModel;
import ca.openquiz.comms.model.GameStat;
import ca.openquiz.comms.model.Team;
import ca.openquiz.comms.response.UserResponse;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.VerticalLayout;

public class TeamStatTab extends CustomComponent {
	private static final long serialVersionUID = 3255856571456897404L;
	private WebServiceCache cache;
	private ComboBox questionType;
	private ComboBox categoryType;
	private ComboBox teamsComboBox;
	private String userKey;
	private String authorization;
	private List<GameStat> overallGameStats;
	
	private PieChart teamParticipationPieChart;
	private LineChart teamParticipationLineChart;
	private PieChart userParticipationPieChart;
	private LineChart userParticipationLineChart;
	
	private PieChart teamNumberOfGamesPlayedPieChart;
	private LineChart teamNumberOfGamesPlayedLineChart;
	private PieChart userNumberOfGamesPlayedPieChart;
	private LineChart userNumberOfGamesPlayedLineChart;
	
	private PieChart teamAnswersPieChart;
	private LineChart teamAnswersLineChart;
	private PieChart userAnswersPieChart;
	private LineChart userAnswersLineChart;
	
	private boolean tabLoaded = false;
	private boolean isSelected = false;
	private UserResponse user;
	private int numberOfGamesTotal;
	
	public TeamStatTab(WebServiceCache cache, TabSheet tabsheet, final StatsPeriodSelector statsPeriodSelector) {
		this.cache = cache;
		
		statsPeriodSelector.addChangeListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 3122992412229374120L;
			@Override
			public void valueChange(ValueChangeEvent event) {
				if(isSelected)
					TeamStatTab.this.updateTab((int)event.getProperty().getValue());
			}
		});
			
		//This is to make the tab load only when selected
		tabsheet.addListener( new TabSheet.SelectedTabChangeListener() {
			private static final long serialVersionUID = 3220047043655101347L;
			@Override
			public void selectedTabChange(SelectedTabChangeEvent event) {
				if( user == null) user = RequestsWebService.getCurrentUserInfo(authorization);
				numberOfGamesTotal = statsPeriodSelector.getSelectedValue();
				if(event.getTabSheet().getSelectedTab() == TeamStatTab.this){
					isSelected = true;
					if(!tabLoaded){
						buildUI();
						addListeners();
						populateUI();
						tabLoaded = true;
					}
					populateCharts();
				}else{
					isSelected = false;
				}
			}
		});
	}

	@Override
	public void attach() {
		super.attach();
		if(userKey == null){
			userKey = (String) ((Website_UI)this.getApplication()).getSession().getAttribute(SessionConstants.SESSION_USER_KEY);
			
			if(userKey == null){
				this.setCompositionRoot((new Label(Messages.getString("All.pleaseLogin")))); //$NON-NLS-1$
				return;
			}
			authorization = (String) ((Website_UI)this.getApplication()).getSession().getAttribute(SessionConstants.SESSION_AUTHORIZATION_STRING);
		}
	}

	private void addListeners() {
		questionType.addListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void valueChange(ValueChangeEvent event) {
				if(event.getProperty().getValue() == null){
					//TODO Remove filter
					return;
				}
				//TODO Apply type filter
				populateCharts();
			}
		});
		
		categoryType.addListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void valueChange(ValueChangeEvent event) {
				if(event.getProperty().getValue() == null){
					//TODO Remove filter
					return;
				}
				//TODO Apply type filter
				populateCharts();
			}
		});
		
		teamsComboBox.addListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 1885949777344093414L;
			@Override
			public void valueChange(ValueChangeEvent event) {
				Team team = (Team) event.getProperty().getValue();
				if(team == null){
					//TODO Remove filter
				}
				populateCharts();
			}
		});
	}

	private void populateUI() {
		overallGameStats = RequestsWebService.getGameStats(userKey, null, 50, 1);
				
		for(CategoryType type : CategoryType.values()){
			categoryType.addItem(type);
		}
		
		for(QuestionType type : QuestionType.values()){
			questionType.addItem(type);
		}
		
		UserResponse user = RequestsWebService.getCurrentUserInfo(authorization);
		if(user != null){		
			if(user.getTeams() != null){				
				for(Team team : user.getTeams()){
					teamsComboBox.addItem(team);
					teamsComboBox.select(team);
				}
			}
		}
	}

	private void populateCharts(){
		overallGameStats = RequestsWebService.getGameStats(userKey, null, numberOfGamesTotal, 1);
		
		if(overallGameStats != null){
			BaseModel team = (BaseModel) teamsComboBox.getValue();
			CategoryType catType = (CategoryType) categoryType.getValue();
			QuestionType questType = (QuestionType) questionType.getValue();
						
			if(team != null){
				List<GameStat> teamGameStats = RequestsWebService.getGameStats(team.getKey(), null, numberOfGamesTotal, 1);
				StatChartHelper.updateNumberOfGamesCharts(teamGameStats, teamNumberOfGamesPlayedLineChart, teamNumberOfGamesPlayedPieChart);
				StatChartHelper.updateResponseChart(teamGameStats, questType, catType, teamAnswersLineChart, teamAnswersPieChart);
				StatChartHelper.updateParticipationChart(teamGameStats, teamParticipationLineChart, teamParticipationPieChart);
			}
			
			List<GameStat> userGameStats = RequestsWebService.getGameStats(user.getKey(), null, numberOfGamesTotal, 1);
			StatChartHelper.updateNumberOfGamesCharts(userGameStats, userNumberOfGamesPlayedLineChart, userNumberOfGamesPlayedPieChart);
			StatChartHelper.updateResponseChart(userGameStats, questType, catType, userAnswersLineChart, userAnswersPieChart);
			StatChartHelper.updateParticipationChart(userGameStats, userParticipationLineChart, userParticipationPieChart);
		}
	}
	
	private void buildUI() {
		VerticalLayout vl = new VerticalLayout();
		
		teamsComboBox = new ComboBox("Équipe");
		
		vl.addComponent(teamsComboBox);
		vl.addComponent(createNumberGamesPlayedLayout());
		vl.addComponent(new Label("<br/><hr/><br/>",Label.CONTENT_XHTML));
		
		vl.addComponent(createAnswerLayout());
		vl.addComponent(new Label("<br/><hr/><br/>",Label.CONTENT_XHTML));
		
		vl.addComponent(createParticipationLayout());
		
		this.setCompositionRoot(vl);
	}
	
	private Component createNumberGamesPlayedLayout(){
		VerticalLayout vl = new VerticalLayout();
		
		Label title = new Label("<h2>Nombre de parties jouées</h2>", Label.CONTENT_XHTML);
		
		HorizontalLayout hlTeam = new HorizontalLayout();
		HorizontalLayout hlUser = new HorizontalLayout();
		
		hlTeam.addComponent(new Label("<h3>Équipe</h3>", Label.CONTENT_XHTML));
		teamNumberOfGamesPlayedPieChart = createPieChart("Répartition des parties jouées");
		hlTeam.addComponent(teamNumberOfGamesPlayedPieChart);
		teamNumberOfGamesPlayedLineChart = createPointChart("Progression des parties jouées", "Partie", "Nombre de parties");
		hlTeam.addComponent(teamNumberOfGamesPlayedLineChart);
		
		hlUser.addComponent(new Label("<h3>Joueur</h3>", Label.CONTENT_XHTML));
		userNumberOfGamesPlayedPieChart = createPieChart("Répartition des parties jouées");
		hlUser.addComponent(userNumberOfGamesPlayedPieChart);
		userNumberOfGamesPlayedLineChart = createPointChart("Progression des parties jouées", "Partie", "Nombre de parties");
		hlUser.addComponent(userNumberOfGamesPlayedLineChart);
		
		vl.addComponent(title);
		vl.addComponent(hlTeam);
		vl.addComponent(new Label("<br/><hr/><br/>",Label.CONTENT_XHTML));
		vl.addComponent(hlUser);
		
		return vl;
	}
	
	private Component createAnswerLayout(){
		VerticalLayout vl = new VerticalLayout();
		
		Label title = new Label("<h2>Réponse</h2>", Label.CONTENT_XHTML);
		categoryType = new ComboBox("Type de catégorie");
		categoryType.setImmediate(true);
		
		questionType = new ComboBox("Type de question");
		questionType.setImmediate(true);

		HorizontalLayout hlTeam = new HorizontalLayout();
		HorizontalLayout hlUser = new HorizontalLayout();
		
		hlTeam.addComponent(new Label("<h3>Équipe</h3>", Label.CONTENT_XHTML));
		teamAnswersPieChart = createPieChart("Répartition des réponses");
		hlTeam.addComponent(teamAnswersPieChart);
		teamAnswersLineChart = createPointChart("Progression des réponses", "Partie", "Nombre de réponses");
		hlTeam.addComponent(teamAnswersLineChart);
		
		hlUser.addComponent(new Label("<h3>Joueur</h3>", Label.CONTENT_XHTML));
		userAnswersPieChart = createPieChart("Répartition des réponses");
		hlUser.addComponent(userAnswersPieChart);
		userAnswersLineChart = createPointChart("Progression des réponses", "Partie", "Nombre de réponses");
		hlUser.addComponent(userAnswersLineChart);
		
		vl.addComponent(title);
		vl.addComponent(categoryType);
		vl.addComponent(questionType);
		vl.addComponent(hlTeam);
		vl.addComponent(new Label("<br/><hr/><br/>",Label.CONTENT_XHTML));
		vl.addComponent(hlUser);
		
		return vl;
	}
	
	private Component createParticipationLayout(){
		VerticalLayout vl = new VerticalLayout();
		
		Label title = new Label("<h2>Participation</h2>", Label.CONTENT_XHTML);
		
		HorizontalLayout hlTeam = new HorizontalLayout();
		HorizontalLayout hlUser = new HorizontalLayout();
		
		hlTeam.addComponent(new Label("<h3>Équipe</h3>", Label.CONTENT_XHTML));
		teamParticipationPieChart = createPieChart("Répartition de la participation");
		hlTeam.addComponent(teamParticipationPieChart);
		teamParticipationLineChart = createPointChart("Progression des la participation", "Partie", "Nombre de tentatives de réponse");
		hlTeam.addComponent(teamParticipationLineChart);
				
		hlUser.addComponent(new Label("<h3>Joueur</h3>", Label.CONTENT_XHTML));
		userParticipationPieChart = createPieChart("Répartition de la participation");
		hlUser.addComponent(userParticipationPieChart);
		userParticipationLineChart = createPointChart("Progression des la participation", "Partie", "Nombre de tentatives de réponse");
		hlUser.addComponent(userParticipationLineChart);
		
		vl.addComponent(title);
		vl.addComponent(hlTeam);
		vl.addComponent(new Label("<br/><hr/><br/>",Label.CONTENT_XHTML));
		vl.addComponent(hlUser);
		
		return vl;
	}
	
	private LineChart createPointChart(String title, String xAxisTitle, String yAxisTitle) {
		LineChart lc = new LineChart();	
		lc.addXAxisLabel(xAxisTitle);			
		lc.setOption("hAxis.title", xAxisTitle);
		lc.setOption("vAxis.title", yAxisTitle);
		lc.setOption("title", title);
		lc.setOption("width", 620);	
		lc.setOption("height", 420);
		lc.setWidth("600px");
		lc.setHeight("400px");
		return lc;
	}

	private PieChart createPieChart(String title){
		PieChart pc = new PieChart();	
		pc.setOption("is3D", true);
		pc.setOption("width", 500);	
		pc.setOption("height", 500);
		pc.setOption("legend.position", "left");
		pc.setOption("legend.alignment", "start");
		pc.setOption("title", title);
		pc.setOption("chartArea", "{left:0,top:0,width:'100%',height:'100%'}");
		pc.setSizeFull();
		pc.setColors("#FF0000","#CC0000","#AA0000","#880000","#550000");
		pc.setWidth("400px");
		pc.setHeight("400px");
		return pc;
	}
	
	public void updateTab(int number) {
		this.numberOfGamesTotal = number;
		populateCharts();
	}
}

