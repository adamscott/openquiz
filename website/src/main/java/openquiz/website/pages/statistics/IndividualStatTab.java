package openquiz.website.pages.statistics;

import java.util.ArrayList;
import java.util.HashMap;
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
import ca.openquiz.comms.model.GroupRole;
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

public class IndividualStatTab extends CustomComponent {
	private static final long serialVersionUID = 3255856571456897404L;
	private WebServiceCache cache;
	private ComboBox categoryOrQuestionType;
	private ComboBox teamOrGroup;
	private ComboBox teamsComboBox;
	private String userKey;
	private String authorization;
	private List<GameStat> overallGameStats;
	
	private PieChart participationPieChart;
	private LineChart participationLineChart;
	private PieChart numberOfGamesPlayedPieChart;
	private LineChart numberOfGamesPlayedLineChart;
	private PieChart answersPieChart;
	private LineChart answersLineChart;
	
	private boolean tabLoaded = false;
	private boolean isSelected = false;
	private UserResponse user;
	private int numberOfGamesTotal;
	
	public IndividualStatTab(WebServiceCache cache, TabSheet tabsheet, final StatsPeriodSelector statsPeriodSelector, final String authorization) {
		this.cache = cache;
		
		statsPeriodSelector.addChangeListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 3122992412229374120L;
			@Override
			public void valueChange(ValueChangeEvent event) {
				if(isSelected)
					IndividualStatTab.this.updateTab((int)event.getProperty().getValue());
			}
		});
		
		//This is to make the tab load only when selected
		tabsheet.addListener( new TabSheet.SelectedTabChangeListener() {
			private static final long serialVersionUID = 3220047043655101347L;
			@Override
			public void selectedTabChange(SelectedTabChangeEvent event) {
				if( user == null) user = RequestsWebService.getCurrentUserInfo(authorization);
				if(user != null){
					userKey = user.getKey();
					numberOfGamesTotal = statsPeriodSelector.getSelectedValue();
					if(event.getTabSheet().getSelectedTab() == IndividualStatTab.this){
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
			}
		});
	}

	@Override
	public void attach() {
		super.attach();
		if(userKey == null){
			userKey = (String) ((Website_UI)this.getApplication()).getSession().getAttribute(SessionConstants.SESSION_USER_KEY);
			
			if(userKey == null){
				this.setCompositionRoot((new Label(""))); //$NON-NLS-1$
				return;
			}
			authorization = (String) ((Website_UI)this.getApplication()).getSession().getAttribute(SessionConstants.SESSION_AUTHORIZATION_STRING);
		}
		if( user == null) user = RequestsWebService.getCurrentUserInfo(authorization);
	}
	
	private void addListeners() {
		categoryOrQuestionType.addListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void valueChange(ValueChangeEvent event) {
				updateResponseChart();
			}
		});
		
		teamOrGroup.addListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void valueChange(ValueChangeEvent event) {
				updateNumberOfGamesCharts();
			}
		});
		
		teamsComboBox.addListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 1885949777344093414L;
			@Override
			public void valueChange(ValueChangeEvent event) {
				updateParticipationChart();
			}
		});
	}

	private void populateUI() {
		for(CategoryType type : CategoryType.values()){
			categoryOrQuestionType.addItem(type);
		}
		
		for(QuestionType type : QuestionType.values()){
			categoryOrQuestionType.addItem(type);
		}
		
		if(user != null){
			if(user.getTeams() != null){
				//teamOrGroup.addItem("--- Équipes ---");
				for(Team team : user.getTeams()){
					teamOrGroup.addItem(team);
				}
			}
	
			/*if(user.getGroupRoles() != null){
				teamOrGroup.addItem(" ");
				teamOrGroup.addItem("--- Groupes ---");
				for(GroupRole role : user.getGroupRoles()){
					teamOrGroup.addItem(cache.getGroup(role.getGroupKey()));
				}
			}*/
			
			if(user.getTeams() != null){
				for(Team team : user.getTeams()){
					teamsComboBox.addItem(team);
				}
			}
		}
	}
	
	private void populateCharts(){
		overallGameStats = RequestsWebService.getGameStats(userKey, null, numberOfGamesTotal, 1);
		if(overallGameStats != null){
			updateNumberOfGamesCharts();
			updateParticipationChart();
			updateResponseChart();
		}
	}
	
	private void updateNumberOfGamesCharts(){
		BaseModel obj = (BaseModel) teamOrGroup.getValue();
		if(obj != null){
			List<GameStat> gameStats = RequestsWebService.getGameStats(userKey, obj.getKey(), numberOfGamesTotal, 1);
			StatChartHelper.updateNumberOfGamesCharts(gameStats, numberOfGamesPlayedLineChart, numberOfGamesPlayedPieChart);
		}else{
			StatChartHelper.updateNumberOfGamesCharts(overallGameStats, numberOfGamesPlayedLineChart, numberOfGamesPlayedPieChart);
		}
	}
	
	private void updateParticipationChart(){
		BaseModel obj = (BaseModel) teamsComboBox.getValue();
		if(obj != null){
			List<GameStat> gameStats = RequestsWebService.getGameStats(userKey, obj.getKey(), numberOfGamesTotal, 1);
			StatChartHelper.updateParticipationChart(gameStats, participationLineChart, participationPieChart);
		}else{
			StatChartHelper.updateParticipationChart(overallGameStats, participationLineChart, participationPieChart);
		}
	}
	
	private void updateResponseChart(){
		Object obj = categoryOrQuestionType.getValue();
		if(obj instanceof CategoryType){
			StatChartHelper.updateResponseChart(overallGameStats, null, (CategoryType) obj, answersLineChart, answersPieChart);
		}else if(obj instanceof QuestionType){
			StatChartHelper.updateResponseChart(overallGameStats, (QuestionType)obj, null, answersLineChart, answersPieChart);
		}else {
			StatChartHelper.updateResponseChart(overallGameStats, null, null, answersLineChart, answersPieChart);
		}
	}

	private void buildUI() {
		VerticalLayout vl = new VerticalLayout();
		vl.setMargin(true);
		
		vl.addComponent(createNumberGamesPlayedLayout());
		vl.addComponent(new Label("<br/><hr/><br/>",Label.CONTENT_XHTML)); //$NON-NLS-1$
		
		vl.addComponent(createAnswerLayout());
		vl.addComponent(new Label("<br/><hr/><br/>",Label.CONTENT_XHTML)); //$NON-NLS-1$
		
		vl.addComponent(createParticipationLayout());
		
		this.setCompositionRoot(vl);
	}
	
	private Component createNumberGamesPlayedLayout(){
		ArrayList<double[]> points = new ArrayList<double[]>();
		for(int i = 0 ; i < 1000 ; i++){
			points.add(new double[] { i, i+100 });
		}
		
		VerticalLayout vl = new VerticalLayout();
		
		Label title = new Label(Messages.getString("IndividualStatTab.numberOfGamePlayedHeader"), Label.CONTENT_XHTML); //$NON-NLS-1$
		teamOrGroup = new ComboBox(Messages.getString("IndividualStatTab.teamOrGroup")); //$NON-NLS-1$
		teamOrGroup.setImmediate(true);
		
		HorizontalLayout hl = new HorizontalLayout();
		numberOfGamesPlayedPieChart = createPieChart("Répartition des parties jouées"); //$NON-NLS-1$
		hl.addComponent(numberOfGamesPlayedPieChart);
		numberOfGamesPlayedLineChart = createPointChart("Progression des parties jouées", Messages.getString("IndividualStatTab.game"), Messages.getString("IndividualStatTab.numberOfGames")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		hl.addComponent(numberOfGamesPlayedLineChart);
		
		vl.addComponent(title);
		vl.addComponent(teamOrGroup);
		vl.addComponent(hl);
		
		return vl;
	}
	
	private Component createAnswerLayout(){//
		ArrayList<double[]> points = new ArrayList<double[]>();
		for(int i = 0 ; i < 1000 ; i++){
			points.add(new double[] { i, i+100 });
		}
		
		VerticalLayout vl = new VerticalLayout();
		
		Label title = new Label(Messages.getString("IndividualStatTab.answersHeader"), Label.CONTENT_XHTML); //$NON-NLS-1$
		categoryOrQuestionType = new ComboBox(Messages.getString("IndividualStatTab.typeCategoryOrTypeQuestion")); //$NON-NLS-1$
		categoryOrQuestionType.setImmediate(true);

		HorizontalLayout hl = new HorizontalLayout();
		answersPieChart = createPieChart(Messages.getString("IndividualStatTab.repartitionOfAnswers")); //$NON-NLS-1$
		hl.addComponent(answersPieChart);
		answersLineChart = createPointChart(Messages.getString("IndividualStatTab.progressionOfAnswers"), Messages.getString("IndividualStatTab.games"), Messages.getString("IndividualStatTab.numberOfAnswer")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		hl.addComponent(answersLineChart);
		
		vl.addComponent(title);
		vl.addComponent(categoryOrQuestionType);
		vl.addComponent(hl);
		
		return vl;
	}
	
	private Component createParticipationLayout(){
		ArrayList<String> linesTitle = new ArrayList<String>();
		linesTitle.add(Messages.getString("IndividualStatTab.attempts")); //$NON-NLS-1$
		linesTitle.add(Messages.getString("IndividualStatTab.noAttempts")); //$NON-NLS-1$
		VerticalLayout vl = new VerticalLayout();
		
		Label title = new Label(Messages.getString("IndividualStatTab.participationHeader"), Label.CONTENT_XHTML); //$NON-NLS-1$
		teamsComboBox = new ComboBox(Messages.getString("IndividualStatTab.team")); //$NON-NLS-1$
		teamsComboBox.setImmediate(true);
		
		HorizontalLayout hl = new HorizontalLayout();
		
		participationPieChart = createPieChart(Messages.getString("IndividualStatTab.repartitionOfParticipation")); //$NON-NLS-1$
		hl.addComponent(participationPieChart);
		participationLineChart = createPointChart(Messages.getString("IndividualStatTab.participationProgression"), Messages.getString("IndividualStatTab.game"), Messages.getString("IndividualStatTab.numberOfAttempts")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		hl.addComponent(participationLineChart);
		
		vl.addComponent(title);
		vl.addComponent(teamsComboBox);
		vl.addComponent(hl);
		
		return vl;
	}
	
	private LineChart createPointChart(String title, String xAxisTitle, String yAxisTitle) {
		LineChart lc = new LineChart();	
		lc.addXAxisLabel(xAxisTitle);	
		lc.setOption("hAxis.title", xAxisTitle); //$NON-NLS-1$
		lc.setOption("vAxis.title", yAxisTitle); //$NON-NLS-1$
		lc.setOption("title", title); //$NON-NLS-1$
		lc.setOption("width", 620);	 //$NON-NLS-1$
		lc.setOption("height", 420); //$NON-NLS-1$
		lc.setWidth("650px"); //$NON-NLS-1$
		lc.setHeight("450px"); //$NON-NLS-1$
		return lc;
	}

	private PieChart createPieChart(String title){
		PieChart pc = new PieChart();	
		pc.setOption("is3D", true); //$NON-NLS-1$
		pc.setOption("width", 500);	 //$NON-NLS-1$
		pc.setOption("height", 500); //$NON-NLS-1$
		pc.setOption("legend.position", "left"); //$NON-NLS-1$ //$NON-NLS-2$
		pc.setOption("legend.alignment", "start"); //$NON-NLS-1$ //$NON-NLS-2$
		pc.setOption("title", title); //$NON-NLS-1$
		pc.setOption("chartArea", "{left:0,top:0,width:'100%',height:'100%'}"); //$NON-NLS-1$ //$NON-NLS-2$
		pc.setSizeFull();
		pc.setWidth("400px"); //$NON-NLS-1$
		pc.setHeight("400px"); //$NON-NLS-1$
		return pc;
	}

	public void updateTab(int number) {
		this.numberOfGamesTotal = number;
		populateCharts();
		//this.categoryOrQuestionType.setValue(null);
		//teamOrGroup.setValue(null);
		//teamsComboBox.setValue(null);
	}
}

