package creationGame;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import currentGame.CurrentGameView;

import net.miginfocom.swing.MigLayout;
import applicationTools.BackgroundManager;
import applicationTools.BackgroundPanel;
import applicationTools.ButtonManager;
import applicationTools.CustomButton;
import applicationTools.DownloadManager;
import applicationTools.LoginManager;
import applicationTools.QuestionManager;
import applicationTools.Defines.CreateNewGameAction;
import ca.openquiz.comms.RequestsWebService;
import ca.openquiz.comms.enums.CategoryType;
import ca.openquiz.comms.enums.Degree;
import ca.openquiz.comms.enums.QuestionTarget;
import ca.openquiz.comms.enums.QuestionType;
import ca.openquiz.comms.model.Category;
import ca.openquiz.comms.model.Game;
import ca.openquiz.comms.model.Group;
import ca.openquiz.comms.model.QuestionSet;
import ca.openquiz.comms.model.Team;
import ca.openquiz.comms.model.Template;
import ca.openquiz.comms.model.TemplateSection;
import ca.openquiz.comms.model.User;
import ca.openquiz.comms.response.IntegerResponse;

public class CreateNewGameView implements ICreateNewGameView {
	private DefaultTableModel dm;
	private Box horizontalBox;
	private JLabel lblGameName;
	private Component horizontalStrut;
	private JComboBox comboBoxLeftTeamGroup;
	private JComboBox<String> comboBoxRightTeamGroup;
	private JComboBox<String> comboBoxLeftTeamTeamList;
	private JComboBox<String> comboBoxRightTeamTeamList;
	private JSeparator separator;
	private JComboBox<String> comboBoxLeftTeamPlayer1;
	private JComboBox<String> comboBoxRightTeamPlayer1;
	private JComboBox<String> comboBoxLeftTeamPlayer2;
	private JComboBox<String> comboBoxRightTeamPlayer2;
	private JComboBox<String> comboBoxLeftTeamPlayer3;
	private JComboBox<String> comboBoxRightTeamPlayer3;
	private JComboBox<String> comboBoxLeftTeamPlayer4;
	private JComboBox<String> comboBoxRightTeamPlayer4;
	private CustomButton btnPlayGame;
	private JTextField txtfldGameName;
	private Dimension btnDimension = new Dimension(150,60);
	private JPanel panelContent;
	private JTabbedPane tabbedPane;
	private CreateNewGameModel model = null;
	private CreateNewGameController controller = null;
	private TemplatePanel templatePanel;
	private QuestionnairePanel questionnairePanel;
	private GameListPanel gamePanel;
	private TemplateCreationPopUpView templateCreationPopUp;
	private GameCreationCheckPopUp gameCreationCheckPopUp;
	private GameCreationFromTemplatePopUp gameCreationFromTemplatePopUp;
	private DownloadManager downloadManager;
	private Thread threadDownloadManager;
	
	public CreateNewGameView() 
	{
		panelContent = new JPanel();
		
	    btnPlayGame = new CustomButton("Jouer la partie", null, ButtonManager.getTextButtonDimension());
	    btnPlayGame.setToolTipText("Alt+J");
	    btnPlayGame.setMnemonic(KeyEvent.VK_J);
		
		horizontalBox = Box.createHorizontalBox();
		lblGameName = new JLabel("Nom de la partie");
		horizontalStrut = Box.createHorizontalStrut(20);
		txtfldGameName = new JTextField();
		
		comboBoxLeftTeamGroup = new JComboBox<String>();
		comboBoxRightTeamGroup = new JComboBox<String>();
		comboBoxLeftTeamTeamList = new JComboBox<String>();
		comboBoxRightTeamTeamList = new JComboBox<String>();

		comboBoxLeftTeamPlayer1 = new JComboBox<String>();
		comboBoxRightTeamPlayer1 = new JComboBox<String>();
		comboBoxLeftTeamPlayer2 = new JComboBox<String>();
		comboBoxRightTeamPlayer2 = new JComboBox<String>();
		comboBoxLeftTeamPlayer3 = new JComboBox<String>();
		comboBoxRightTeamPlayer3 = new JComboBox<String>();
		comboBoxLeftTeamPlayer4 = new JComboBox<String>();
		comboBoxRightTeamPlayer4 = new JComboBox<String>();
		
		comboBoxLeftTeamPlayer1.addItem("");
		comboBoxLeftTeamPlayer2.addItem("");
		comboBoxLeftTeamPlayer3.addItem("");
		comboBoxLeftTeamPlayer4.addItem("");
		comboBoxRightTeamPlayer1.addItem("");
		comboBoxRightTeamPlayer2.addItem("");
		comboBoxRightTeamPlayer3.addItem("");
		comboBoxRightTeamPlayer4.addItem("");
		
		separator = new JSeparator();
		
		templatePanel = new TemplatePanel();
		questionnairePanel = new QuestionnairePanel();
		gamePanel = new GameListPanel();
		
		templateCreationPopUp = new TemplateCreationPopUpView();
		gameCreationCheckPopUp = new GameCreationCheckPopUp();
		gameCreationFromTemplatePopUp = new GameCreationFromTemplatePopUp();
		
		downloadManager = new DownloadManager();
		threadDownloadManager = new Thread(downloadManager);
		threadDownloadManager.start();
		
		initGUI();
	}
	
	private void initGUI()
	{
		panelContent.setLayout(new MigLayout("", "[50px:n:50px,grow][grow][20px:n:20px][200px:200px,grow][100px:n:100px][200px:200px,grow][grow][40px:n:40px][42px:n:42px]", "[][10px:n:10px][grow][10px:n:10px][][][][][][][][][]"));
	    panelContent.setOpaque(false);
	    panelContent.add(horizontalBox, "flowx,cell 0 0 9 1");
	    
	    horizontalBox.add(lblGameName);
	    horizontalBox.add(horizontalStrut);
		horizontalBox.add(txtfldGameName);
		
		txtfldGameName.setText("");
		txtfldGameName.setColumns(25);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		panelContent.add(tabbedPane, "cell 0 2 9 1,grow");
		
		tabbedPane.addTab("Questionnaires", null, questionnairePanel, null);
		tabbedPane.addTab("Modèles", null, templatePanel, null);
		tabbedPane.addTab("Parties", null, gamePanel, null);
		
		tabbedPane.addChangeListener(changeListenerTabbedChanged);
		
		panelContent.add(comboBoxLeftTeamGroup, "cell 3 5,growx");
		panelContent.add(comboBoxRightTeamGroup, "cell 5 5,growx");

		panelContent.add(comboBoxLeftTeamTeamList, "cell 3 6,growx");
		panelContent.add(comboBoxRightTeamTeamList, "cell 5 6,growx");
		
		separator.setBackground(Color.BLACK);
		separator.setForeground(Color.BLACK);
		separator.setOrientation(SwingConstants.VERTICAL);
		panelContent.add(separator, "flowx,cell 4 6 1 4,alignx center,growy");
		
		panelContent.add(comboBoxLeftTeamPlayer1, "cell 3 7,growx");
		panelContent.add(comboBoxRightTeamPlayer1, "cell 5 7,growx");
		panelContent.add(comboBoxLeftTeamPlayer2, "cell 3 8,growx");
		panelContent.add(comboBoxRightTeamPlayer2, "cell 5 8,growx");
		panelContent.add(comboBoxLeftTeamPlayer3, "cell 3 9,growx");
		panelContent.add(comboBoxRightTeamPlayer3, "cell 5 9,growx");
		panelContent.add(comboBoxLeftTeamPlayer4, "cell 3 10,growx");
		panelContent.add(comboBoxRightTeamPlayer4, "cell 5 10,growx");
		
		btnPlayGame.setMaximumSize(btnDimension);
		btnPlayGame.setMinimumSize(btnDimension);
		panelContent.add(btnPlayGame, "cell 6 9 3 2,alignx right");
		
		btnPlayGame.addActionListener(actionListenerPlayGame);
		templatePanel.getTemplateTable().addMouseListener(mouseListenerCellSelection);
		templatePanel.getBtnGenerateQuestionSet().addActionListener(actionListenerGenerateQuestionSet);
		templatePanel.getBtnSelectTemplate().addActionListener(actionListenerCreateNewTemplate);
		gamePanel.getGameTable().addMouseListener(mouseListenerGameTableCellSelection);
		
		comboBoxLeftTeamGroup.addItemListener(itemListenerChangedGroupLeft);
		comboBoxRightTeamGroup.addItemListener(itemListenerChangedGroupRight);
		comboBoxLeftTeamTeamList.addItemListener(itemListenerChangedTeamLeft);
		comboBoxRightTeamTeamList.addItemListener(itemListenerChangedTeamRight);
		
		templateCreationPopUp.getBtnAcceptTemplate().addActionListener(actionListenerAcceptTemplatePopUp);
		gameCreationFromTemplatePopUp.getBtnAccept().addActionListener(actionListenerAcceptCreationTemplatePopUp);
	}
	
	private ChangeListener changeListenerTabbedChanged = new ChangeListener(){
		public void stateChanged(ChangeEvent arg0) {
			if (tabbedPane.getSelectedIndex() == 2){
				controller.disableGameDataSelection();
			}
			else{
				controller.enableGameDataSelection();
			}
			if (tabbedPane.getSelectedIndex() == 1 && LoginManager.getInstance().isUserGuest()){
				controller.disablePlayButton();
			}
			else{
				controller.enablePlayButton();
			}
		}
	};
	
	public void disablePlayButton(){
		btnPlayGame.setEnabled(false);
	}
	
	public void enablePlayButton(){
		btnPlayGame.setEnabled(true);
	}
	
	public void disableGameDataSelection(){
		comboBoxLeftTeamGroup.setEnabled(false);
		comboBoxLeftTeamTeamList.setEnabled(false);
		comboBoxRightTeamGroup.setEnabled(false);
		comboBoxRightTeamTeamList.setEnabled(false);
		comboBoxRightTeamPlayer1.setEnabled(false);
		comboBoxRightTeamPlayer2.setEnabled(false);
		comboBoxRightTeamPlayer3.setEnabled(false);
		comboBoxRightTeamPlayer4.setEnabled(false);
		comboBoxLeftTeamPlayer1.setEnabled(false);
		comboBoxLeftTeamPlayer2.setEnabled(false);
		comboBoxLeftTeamPlayer3.setEnabled(false);
		comboBoxLeftTeamPlayer4.setEnabled(false);
		txtfldGameName.setEnabled(false);
	}
	
	public void enableGameDataSelection(){
		comboBoxLeftTeamGroup.setEnabled(true);
		comboBoxLeftTeamTeamList.setEnabled(true);
		comboBoxRightTeamGroup.setEnabled(true);
		comboBoxRightTeamTeamList.setEnabled(true);
		comboBoxRightTeamPlayer1.setEnabled(true);
		comboBoxRightTeamPlayer2.setEnabled(true);
		comboBoxRightTeamPlayer3.setEnabled(true);
		comboBoxRightTeamPlayer4.setEnabled(true);
		comboBoxLeftTeamPlayer1.setEnabled(true);
		comboBoxLeftTeamPlayer2.setEnabled(true);
		comboBoxLeftTeamPlayer3.setEnabled(true);
		comboBoxLeftTeamPlayer4.setEnabled(true);
		txtfldGameName.setEnabled(true);
	}
	
	private Template getTemplateFromCreationZone(){
		List<TemplateSection> templateSections = getTemplateSections();
		Template template = new Template();
		template.setName(getTextOfTxtFieldTemplateName());
		template.setSectionList(templateSections);
		
		return template;
	}
	
	public Template getSelectedTemplate(){
		Template template = null;
		if (templatePanel.getTableSelectedRow() != -1){
			template = model.getTemplate(templatePanel.getTableSelectedRow());
		}
		return template;
	}
	
	private ActionListener actionListenerGenerateQuestionSet = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(templatePanel.validateFields()){
				String questionSetName = templatePanel.getGeneratedQuestionSetName();
				Template template = getSelectedTemplate();
				if (template != null){
					template.setName(questionSetName);
					QuestionSet questionSet = RequestsWebService.generateQuestionSet(template, LoginManager.getInstance().getAuthorization(), true);
					if (questionSet != null){
						questionnairePanel.addQuestionSet(questionSet.getName(), questionSet.getAvailableDate());
						QuestionManager.getInstance().addQuestionSet(questionSet);
						templatePanel.resetTxtFieldQuestionSetNameBackground();
					}
				}
			}
		}
	};
	
	private ActionListener actionListenerPlayGame = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if (isGenerateGameFromTemplate()){
				if (templatePanel.getTableSelectedRow() == -1){
					gameCreationCheckPopUp.setError("Veuillez sélectionner un modèle de questionnaire.");
					gameCreationCheckPopUp.showWindow();
				}
				else{
					Template template = getTemplateFromCreationZone();
					List<Integer> maxNbQuestions = getTemplateMaxNbQuestions();
					List<String> errorList = controller.checkFullTemplateIntegriety(template, maxNbQuestions);
					
					if (errorList != null && errorList.size() > 0){
						gameCreationFromTemplatePopUp.setErrors(errorList);
						gameCreationFromTemplatePopUp.showWindow();
					}
					else{
						controller.playGame();
					}
				}
			}
			else if (isLoadGeneratedGame()){
				if (gamePanel.getTableSelectedRow() == -1){
					gameCreationCheckPopUp.setError("Veuillez sélectionner une partie.");
					gameCreationCheckPopUp.showWindow();
				}
				else{
					controller.playGame();
				}
			}
			else{
				if (questionnairePanel.getTableSelectedRow() == -1){
					gameCreationCheckPopUp.setError("Veuillez sélectionner un questionnaire.");
					gameCreationCheckPopUp.showWindow();
				}
				else{
					controller.playGame();
				}
			}
		}
	};
	
	private ActionListener actionListenerAcceptCreationTemplatePopUp = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			controller.playGame();
			gameCreationFromTemplatePopUp.closeWindow();
		}
	};
	
	private ActionListener actionListenerAcceptQuestionnaireZone = new ActionListener() {
		public void actionPerformed(ActionEvent event) {
			addNewTemplateSection(CategoryType.Unknown, "", Degree.normal, QuestionType.General, 10, 10, QuestionTarget.Collectif, "");
		}
	};
	
	private ActionListener actionListenerRemoveQuestionnaireZone = new ActionListener() {
		public void actionPerformed(ActionEvent event) {
			removeQuestionnaireCreationPanel(event);
		}
	};
	
	private ActionListener actionListenerCreateNewTemplate = new ActionListener() {
		public void actionPerformed(ActionEvent event){			
			Template template = getTemplateFromCreationZone();
			List<Integer> maxNbQuestions = getTemplateMaxNbQuestions();
			
			List<String> errorList = controller.checkTemplateIntegriety(template, maxNbQuestions);
			
			if (errorList != null && errorList.size() != 0){
				templateCreationPopUp.setErrorList(errorList);
				templateCreationPopUp.showWindow();
			}
			else if (template != null){
				controller.createTemplate(template);
			}
		}
	};
	
	private ActionListener actionListenerAcceptTemplatePopUp = new ActionListener(){
		public void actionPerformed(ActionEvent event){
			Template template = getTemplateFromCreationZone();
			if (template != null){
				controller.createTemplate(template);
			}
			templateCreationPopUp.closeWindow();
		}
	};
	
	private List<Integer> getTemplateMaxNbQuestions(){
		List<Integer> maxNbQuestions = new ArrayList<Integer>();
		
		Box verticalBox = templatePanel.getVerticalBoxTemplateCreationZones();
		for (int i = 0; i < verticalBox.getComponentCount(); i++){
			Integer maxQuestion = ((TemplateCreationZonePanel)verticalBox.getComponent(i)).getQuestionNbMax();
			
			maxNbQuestions.add(maxQuestion);
		}
		
		return maxNbQuestions;
	}
	
	private MouseListener mouseListenerCellSelection = new MouseListener() {
		public void mouseClicked(MouseEvent arg0) {
			int index = templatePanel.getTableSelectedRow();
			Template template = model.getTemplate(index);
			//We only load basic info about a template. Load the full data
			if(template != null){
				if (template.getSectionList().size() == 0){
					template = RequestsWebService.getTemplate(template.getKey());
					model.setTemplate(template, index);
				}
				loadTemplateDataInPanel(template);
			}
		}

		public void mouseEntered(MouseEvent arg0) {
			
		}

		public void mouseExited(MouseEvent arg0) {
			
		}

		public void mousePressed(MouseEvent arg0) {
			
		}

		public void mouseReleased(MouseEvent arg0) {
			
		}
	};
	
	private MouseListener mouseListenerGameTableCellSelection = new MouseListener(){

		public void mouseClicked(MouseEvent arg0) {
			int idx = gamePanel.getTableSelectedRow();
			Game game = model.getGameByIdx(idx);
			if (game != null){
				//Set the team assigned to this game
				Team team1 = model.loadTeamById(game.getTeam1());
				Team team2 = model.loadTeamById(game.getTeam2());
				Group group1 = model.loadGroupById(team1.getGroupKey());
				Group group2 = model.loadGroupById(team2.getGroupKey());
				
				//Load players from the team
				List<User> userList1 = model.loadUserListByIds(game.getTeam1Players());
				List<User> userList2 = model.loadUserListByIds(game.getTeam2Players());
				
				//Set the selected group and team
				setSelectedGroup(group1.getName(), group2.getName());
				setSelectedTeam(team1.getName(), team2.getName());
				setSelectedUsers(userList1, userList2);
				
				//Set the game name
				String gameName = "";
				if (game.getName() != null){
					gameName = game.getName();
				}
				txtfldGameName.setText(gameName);
			}
			
		}

		public void mouseEntered(MouseEvent arg0) {
			
			
		}

		public void mouseExited(MouseEvent arg0) {
			
			
		}

		public void mousePressed(MouseEvent arg0) {
			
			
		}

		public void mouseReleased(MouseEvent arg0) {
			
			
		}
		
	};

	private void setSelectedUsers(List<User> userList1, List<User> userList2) {
		if (userList1 != null){
			if (userList1.size() >= 1){
				comboBoxLeftTeamPlayer1.setSelectedItem(userList1.get(0).getFirstName() + " " + userList1.get(0).getLastName());
			}else{
				comboBoxLeftTeamPlayer1.setSelectedItem("");
			}
			if (userList1.size() >= 2){
				comboBoxLeftTeamPlayer2.setSelectedItem(userList1.get(1).getFirstName() + " " + userList1.get(1).getLastName());
			}else{
				comboBoxLeftTeamPlayer2.setSelectedItem("");
			}
			if (userList1.size() >= 3){
				comboBoxLeftTeamPlayer3.setSelectedItem(userList1.get(2).getFirstName() + " " + userList1.get(2).getLastName());
			}else{
				comboBoxLeftTeamPlayer3.setSelectedItem("");
			}
			if (userList1.size() >= 4){
				comboBoxLeftTeamPlayer4.setSelectedItem(userList1.get(3).getFirstName() + " " + userList1.get(3).getLastName());
			}else{
				comboBoxLeftTeamPlayer4.setSelectedItem("");
			}
		}
		if (userList2 != null){
			if (userList2.size() >= 1){
				comboBoxRightTeamPlayer1.setSelectedItem(userList2.get(0).getFirstName() + " " + userList2.get(0).getLastName());
			}else{
				comboBoxRightTeamPlayer1.setSelectedItem("");
			}
			if (userList2.size() >= 2){
				comboBoxRightTeamPlayer2.setSelectedItem(userList2.get(1).getFirstName() + " " + userList2.get(1).getLastName());
			}else{
				comboBoxRightTeamPlayer2.setSelectedItem("");
			}
			if (userList2.size() >= 3){
				comboBoxRightTeamPlayer3.setSelectedItem(userList2.get(2).getFirstName() + " " + userList2.get(2).getLastName());
			}else{
				comboBoxRightTeamPlayer3.setSelectedItem("");
			}
			if (userList2.size() >= 4){
				comboBoxRightTeamPlayer4.setSelectedItem(userList2.get(3).getFirstName() + " " + userList2.get(3).getLastName());
			}else{
				comboBoxRightTeamPlayer4.setSelectedItem("");
			}
		}
	}
	
	public void setSelectedGroup(String group1Name, String group2Name){
		if (group1Name != null){
			comboBoxLeftTeamGroup.setSelectedItem(group1Name);
		}
		if (group2Name != null){
			comboBoxRightTeamGroup.setSelectedItem(group2Name);
		}
	}
	
	public void setSelectedTeam(String team1Name, String team2Name){
		if (team1Name != null){
			comboBoxLeftTeamTeamList.setSelectedItem(team1Name);
		}
		if (team2Name != null){
			comboBoxRightTeamTeamList.setSelectedItem(team2Name);
		}
	}
	
	public void loadTemplateDataInPanel(Template template){
		removeAllQuestionnaireCreationPanel();
		if(template != null){
			//Clear previous QuestionnaireZone;
			setTextOfTxtFieldTemplateName(template.getName());
			for (TemplateSection ts : template.getSectionList()){
				Category cat = model.getCategoryFromID(ts.getCategory());
				if (cat == null){
					addNewTemplateSection(null, "", ts.getDifficulty(), ts.getQuestionType(), ts.getPoints(), ts.getNbQuestions(), ts.getQuestionTarget(), ts.getDescription());
				}
				else{
					addNewTemplateSection(cat.getType(), cat.getName(), ts.getDifficulty(), ts.getQuestionType(), ts.getPoints(), ts.getNbQuestions(), ts.getQuestionTarget(), ts.getDescription());
				}
			}
		}
	}
	
	public void addNewTemplateSection(CategoryType categoryType, String subCategory, Degree difficulty, QuestionType questionType, int value, int quantity, QuestionTarget questionTarget, String description){
		TemplateCreationZonePanel newQuestionnaireZone = addNewTemplateZone();
		
		List<String> subCategories = model.getSubCategoriesFromCategoryType(categoryType);
		newQuestionnaireZone.resetSubCategory();
		for (String subCat : subCategories){
			newQuestionnaireZone.addQuestioSubCategory(subCat);
		}
		
		newQuestionnaireZone.setQuestionCategory(categoryType);
		if (subCategory.equals("")){
			newQuestionnaireZone.setQuestionSubCategory(0);
		}
		else{
			newQuestionnaireZone.setQuestionSubCategory(subCategory);
		}
		newQuestionnaireZone.setQuestionDifficulty(difficulty);
		newQuestionnaireZone.setQuestionType(questionType);
		newQuestionnaireZone.setQuestinValue(value);
		newQuestionnaireZone.setQuestionQuantity(quantity);
		newQuestionnaireZone.setQuestionTarget(questionTarget);
		newQuestionnaireZone.setDescription(description);
		
		setTemplateSectionPanelNbOfQuestions(newQuestionnaireZone);
		
		String tsIdx = Integer.toString(templatePanel.getVerticalBoxTemplateCreationZones().getComponentCount()-1);
		newQuestionnaireZone.getBtnAdd().addActionListener(actionListenerAcceptQuestionnaireZone);
		newQuestionnaireZone.getBtnRemove().addActionListener(actionListenerRemoveQuestionnaireZone);
		newQuestionnaireZone.getBtnRemove().setActionCommand(tsIdx);
		
		newQuestionnaireZone.getComboBoxQuestionType().addActionListener(actionListenerTemplateSectionFieldChanged);
		newQuestionnaireZone.getComboBoxQuestionCategory().addActionListener(actionListenerTemplateSectionCategoryChanged);
		newQuestionnaireZone.getComboBoxQuestionSubCategory().addActionListener(actionListenerTemplateSectionFieldChanged);
		newQuestionnaireZone.getComboBoxDifficulty().addActionListener(actionListenerTemplateSectionFieldChanged);
		newQuestionnaireZone.getComboBoxQuestionType().setActionCommand(tsIdx);
		newQuestionnaireZone.getComboBoxQuestionSubCategory().setActionCommand(tsIdx);
		newQuestionnaireZone.getComboBoxQuestionCategory().setActionCommand(tsIdx);
		newQuestionnaireZone.getComboBoxDifficulty().setActionCommand(tsIdx);
	}
	
	public ActionListener actionListenerTemplateSectionCategoryChanged = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			int idx = Integer.parseInt(arg0.getActionCommand());
			Box verticalBox = templatePanel.getVerticalBoxTemplateCreationZones();
			TemplateCreationZonePanel currentPanel = (TemplateCreationZonePanel)verticalBox.getComponent(idx);
			CategoryType ct = currentPanel.getQuestionCategory();
			List<String> subCategories = model.getSubCategoriesFromCategoryType(ct);
			currentPanel.resetSubCategory();
			for (String subCategory : subCategories){
				currentPanel.addQuestioSubCategory(subCategory);
			}
			setTemplateSectionPanelNbOfQuestions(currentPanel);
		}
	};
	
	public ActionListener actionListenerTemplateSectionFieldChanged = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			int idx = Integer.parseInt(arg0.getActionCommand());
			Box verticalBox = templatePanel.getVerticalBoxTemplateCreationZones();
			TemplateCreationZonePanel currentPanel = (TemplateCreationZonePanel)verticalBox.getComponent(idx);
			setTemplateSectionPanelNbOfQuestions(currentPanel);
		}
	};
	
	public void setTemplateSectionPanelNbOfQuestions(TemplateCreationZonePanel templatePanel){
		CategoryType ct = templatePanel.getQuestionCategory();
		String subCategory = templatePanel.getQuestionSubCategory();
		
		TemplateSection ts = new TemplateSection();
		ts.setDifficulty(templatePanel.getQuestionDifficulty());
		ts.setQuestionType(templatePanel.getQuestionType());
		ts.setCategory(model.getCategoryIDFromTypeAndName(ct, subCategory));
		
		IntegerResponse nbQuestionResponse = RequestsWebService.getQuestionNbInTemplateSection(ts);
		if (nbQuestionResponse != null){
			templatePanel.setQuestionNbMax(nbQuestionResponse.getValue());
		}
		else{
			templatePanel.setQuestionNbMax(0);
		}
	}
	
	public void removeQuestionnaireCreationPanel(ActionEvent event) {
		Box verticalBox = templatePanel.getVerticalBoxTemplateCreationZones();
		if(verticalBox.getComponentCount() > 1) {
			verticalBox.remove(Integer.parseInt(event.getActionCommand()));
			
			//When deleting a question zone, go throught every question zone and change its serie id and action command id
			//which is used to know which question serie to remove
			for(int i=0; i<verticalBox.getComponentCount(); i++) {
				TemplateCreationZonePanel currentPanel = (TemplateCreationZonePanel)verticalBox.getComponent(i);
				currentPanel.setQuestionSeriesName("Series " + (i+1));
				currentPanel.getBtnRemove().setActionCommand(Integer.toString(i));
				
				if(i == verticalBox.getComponentCount()-1) {
					currentPanel.getBtnAdd().setVisible(true);
				}
				
				if(i == 0 && verticalBox.getComponentCount() == 1) {
					currentPanel.getBtnRemove().setVisible(false);
				}
			}
			
			verticalBox.repaint();
			verticalBox.revalidate();
		}
	}
	
	public void setQuestionSetTableModel(ArrayList<Object[]> questionSetsData){
		DefaultTableModel tableModel = new DefaultTableModel();
		tableModel = (DefaultTableModel) questionnairePanel.getTableQuestionnaireList().getModel();
		
		for(Iterator<Object[]> it = questionSetsData.iterator(); it.hasNext();){
			tableModel.addRow(it.next());
		}
		questionnairePanel.getTableQuestionnaireList().setModel(tableModel);
	}
	
	public Boolean checkForEmptyFields() {
		Boolean isFieldEmpty = false;
		
		//Group ComboBox
		if(comboBoxLeftTeamGroup.getSelectedItem() == null) {
			comboBoxLeftTeamGroup.setBackground(Color.RED);
			isFieldEmpty = true;
		}
		if(comboBoxRightTeamGroup.getSelectedItem() == null) {
			comboBoxRightTeamGroup.setBackground(Color.RED);
			isFieldEmpty = true;
		}
		//Team ComboBox
		if(comboBoxLeftTeamTeamList.getSelectedItem() == null) {
			comboBoxLeftTeamTeamList.setBackground(Color.RED);
			isFieldEmpty = true;
		}
		if(comboBoxRightTeamTeamList.getSelectedItem() == null) {
			comboBoxRightTeamTeamList.setBackground(Color.RED);
			isFieldEmpty = true;
		}
		//Left Player ComboBox
		if(comboBoxLeftTeamPlayer1.getSelectedItem() == null) {
			comboBoxLeftTeamPlayer1.setBackground(Color.RED);
			isFieldEmpty = true;
		}
		if(comboBoxLeftTeamPlayer2.getSelectedItem() == null) {
			comboBoxLeftTeamPlayer2.setBackground(Color.RED);
			isFieldEmpty = true;
		}
		if(comboBoxLeftTeamPlayer3.getSelectedItem() == null) {
			comboBoxLeftTeamPlayer3.setBackground(Color.RED);
			isFieldEmpty = true;
		}
		if(comboBoxLeftTeamPlayer4.getSelectedItem() == null) {
			comboBoxLeftTeamPlayer4.setBackground(Color.RED);
			isFieldEmpty = true;
		}
		//Right Player ComboBox
		if(comboBoxRightTeamPlayer1.getSelectedItem() == null) {
			comboBoxRightTeamPlayer1.setBackground(Color.RED);
			isFieldEmpty = true;
		}
		if(comboBoxRightTeamPlayer2.getSelectedItem() == null) {
			comboBoxRightTeamPlayer2.setBackground(Color.RED);
			isFieldEmpty = true;
		}
		if(comboBoxRightTeamPlayer3.getSelectedItem() == null) {
			comboBoxRightTeamPlayer3.setBackground(Color.RED);
			isFieldEmpty = true;
		}
		if(comboBoxRightTeamPlayer4.getSelectedItem() == null) {
			comboBoxRightTeamPlayer4.setBackground(Color.RED);
			isFieldEmpty = true;
		}
		
		return isFieldEmpty;
	}
	
	public Boolean checkForDuplicateNameInSameGroup() {
		Boolean areNamesUnique = true;
		ArrayList<String> uniqueNameList = new ArrayList<String>();

		//Start with left team
		if (comboBoxLeftTeamPlayer1.getSelectedItem() != null && comboBoxLeftTeamPlayer1.getSelectedItem() != ""){
			if(uniqueNameList.contains((String) comboBoxLeftTeamPlayer1.getSelectedItem())){
				comboBoxLeftTeamPlayer1.setBackground(Color.RED);
				areNamesUnique = false;
			} else {
				uniqueNameList.add((String) comboBoxLeftTeamPlayer1.getSelectedItem());
			}
		}
		if (comboBoxLeftTeamPlayer2.getSelectedItem() != null && comboBoxLeftTeamPlayer2.getSelectedItem() != ""){
			if(uniqueNameList.contains((String) comboBoxLeftTeamPlayer2.getSelectedItem())){
				comboBoxLeftTeamPlayer2.setBackground(Color.RED);
				areNamesUnique = false;
			} else {
				uniqueNameList.add((String) comboBoxLeftTeamPlayer2.getSelectedItem());
			}
		}
		if (comboBoxLeftTeamPlayer3.getSelectedItem() != null && comboBoxLeftTeamPlayer3.getSelectedItem() != ""){
			if(uniqueNameList.contains((String) comboBoxLeftTeamPlayer3.getSelectedItem())){
				comboBoxLeftTeamPlayer3.setBackground(Color.RED);
				areNamesUnique = false;
			} else {
				uniqueNameList.add((String) comboBoxLeftTeamPlayer3.getSelectedItem());
			}
		}
		if (comboBoxLeftTeamPlayer4.getSelectedItem() != null && comboBoxLeftTeamPlayer4.getSelectedItem() != ""){
			if(uniqueNameList.contains((String) comboBoxLeftTeamPlayer4.getSelectedItem())){
				comboBoxLeftTeamPlayer4.setBackground(Color.RED);
				areNamesUnique = false;
			} else {
				uniqueNameList.add((String) comboBoxLeftTeamPlayer4.getSelectedItem());
			}
		}
		
		if (!comboBoxLeftTeamGroup.getSelectedItem().equals(comboBoxRightTeamGroup.getSelectedItem()) ||
			!comboBoxLeftTeamTeamList.getSelectedItem().equals(comboBoxRightTeamTeamList.getSelectedItem())){
			uniqueNameList.clear();
		}
		//Continue with right team
		if (comboBoxRightTeamPlayer1.getSelectedItem() != null && comboBoxRightTeamPlayer1.getSelectedItem() != ""){
			if(uniqueNameList.contains((String) comboBoxRightTeamPlayer1.getSelectedItem())){
				comboBoxRightTeamPlayer1.setBackground(Color.RED);
				areNamesUnique = false;
			} else {
				uniqueNameList.add((String) comboBoxRightTeamPlayer1.getSelectedItem());
			}
		}
		if (comboBoxRightTeamPlayer2.getSelectedItem() != null && comboBoxRightTeamPlayer2.getSelectedItem() != ""){
			if(uniqueNameList.contains((String) comboBoxRightTeamPlayer2.getSelectedItem())){
				comboBoxRightTeamPlayer2.setBackground(Color.RED);
				areNamesUnique = false;
			} else {
				uniqueNameList.add((String) comboBoxRightTeamPlayer2.getSelectedItem());
			}
		}
		if (comboBoxRightTeamPlayer3.getSelectedItem() != null && comboBoxRightTeamPlayer3.getSelectedItem() != ""){
			if(uniqueNameList.contains((String) comboBoxRightTeamPlayer3.getSelectedItem())){
				comboBoxRightTeamPlayer3.setBackground(Color.RED);
				areNamesUnique = false;
			} else {
				uniqueNameList.add((String) comboBoxRightTeamPlayer3.getSelectedItem());
			}
		}
		if (comboBoxRightTeamPlayer4.getSelectedItem() != null && comboBoxRightTeamPlayer4.getSelectedItem() != ""){
			if(uniqueNameList.contains((String) comboBoxRightTeamPlayer4.getSelectedItem())){
				comboBoxRightTeamPlayer4.setBackground(Color.RED);
				areNamesUnique = false;
			} else {
				uniqueNameList.add((String) comboBoxRightTeamPlayer4.getSelectedItem());
			}
		}

		return areNamesUnique;
	}
	
	public void clearLeftGroupComboBox(){
		comboBoxLeftTeamGroup.removeAllItems();
	}
	
	public void clearLeftTeamComboBox(){
		comboBoxLeftTeamTeamList.removeAllItems();
	}
	
	public void clearRightGroupComboBox(){
		comboBoxRightTeamGroup.removeAllItems();
	}
	
	public void clearRightTeamComboBox(){
		comboBoxRightTeamTeamList.removeAllItems();
	}
	
	public void clearLeftTeamPlayersNameComboBox(){
		comboBoxLeftTeamPlayer1.removeAllItems();
		comboBoxLeftTeamPlayer2.removeAllItems();
		comboBoxLeftTeamPlayer3.removeAllItems();
		comboBoxLeftTeamPlayer4.removeAllItems();
		
		comboBoxLeftTeamPlayer1.addItem("");
		comboBoxLeftTeamPlayer2.addItem("");
		comboBoxLeftTeamPlayer3.addItem("");
		comboBoxLeftTeamPlayer4.addItem("");
	}
	
	public void clearRightTeamPlayersNameComboBox(){
		comboBoxRightTeamPlayer1.removeAllItems();
		comboBoxRightTeamPlayer2.removeAllItems();
		comboBoxRightTeamPlayer3.removeAllItems();
		comboBoxRightTeamPlayer4.removeAllItems();
		
		comboBoxRightTeamPlayer1.addItem("");
		comboBoxRightTeamPlayer2.addItem("");
		comboBoxRightTeamPlayer3.addItem("");
		comboBoxRightTeamPlayer4.addItem("");
	}
	
	public void addPlayerNameToAllLeftTeamComboBox(String playerName){
		comboBoxLeftTeamPlayer1.addItem(playerName);
		comboBoxLeftTeamPlayer2.addItem(playerName);
		comboBoxLeftTeamPlayer3.addItem(playerName);
		comboBoxLeftTeamPlayer4.addItem(playerName);
	}
	
	public void addPlayerNameToAllRightTeamComboBox(String playerName){
		comboBoxRightTeamPlayer1.addItem(playerName);
		comboBoxRightTeamPlayer2.addItem(playerName);
		comboBoxRightTeamPlayer3.addItem(playerName);
		comboBoxRightTeamPlayer4.addItem(playerName);
	}
	
	public void changeAllComboBoxBackgroundToValidColor(){
		Color white = Color.white;
		
		comboBoxLeftTeamGroup.setBackground(white);
		comboBoxRightTeamGroup.setBackground(white);
		
		comboBoxLeftTeamTeamList.setBackground(white);
		comboBoxRightTeamTeamList.setBackground(white);
		
		comboBoxLeftTeamPlayer1.setBackground(white);
		comboBoxLeftTeamPlayer2.setBackground(white);
		comboBoxLeftTeamPlayer3.setBackground(white);
		comboBoxLeftTeamPlayer4.setBackground(white);

		comboBoxRightTeamPlayer1.setBackground(white);
		comboBoxRightTeamPlayer2.setBackground(white);
		comboBoxRightTeamPlayer3.setBackground(white);
		comboBoxRightTeamPlayer4.setBackground(white);
		
		txtfldGameName.setBackground(white);
		comboBoxLeftTeamGroup.setBackground(white);
		comboBoxRightTeamGroup.setBackground(white);
		comboBoxLeftTeamTeamList.setBackground(white);
		comboBoxRightTeamTeamList.setBackground(white);
	}
	
	public void shufflePlayerComboSelection(String teamSide){
		if(teamSide.equals("Left")){
			try{
				if (comboBoxLeftTeamPlayer1.getItemCount() > 1){
					comboBoxLeftTeamPlayer1.setSelectedIndex(1);
				}
				if (comboBoxLeftTeamPlayer2.getItemCount() > 2){
					comboBoxLeftTeamPlayer2.setSelectedIndex(2);
				}
				if (comboBoxLeftTeamPlayer3.getItemCount() > 3){
					comboBoxLeftTeamPlayer3.setSelectedIndex(3);
				}
				if (comboBoxLeftTeamPlayer4.getItemCount() > 4){
					comboBoxLeftTeamPlayer4.setSelectedIndex(4);
				}
			}catch(Exception e){e.printStackTrace();}
		}
		else if(teamSide.equals("Right")){
			try{
				if (comboBoxRightTeamPlayer1.getItemCount() > 1){
					comboBoxRightTeamPlayer1.setSelectedIndex(1);
				}
				if (comboBoxRightTeamPlayer2.getItemCount() > 2){
					comboBoxRightTeamPlayer2.setSelectedIndex(2);
				}
				if (comboBoxRightTeamPlayer3.getItemCount() > 3){
					comboBoxRightTeamPlayer3.setSelectedIndex(3);
				}
				if (comboBoxRightTeamPlayer4.getItemCount() > 4){
					comboBoxRightTeamPlayer4.setSelectedIndex(4);
				}
			}catch(Exception e){e.printStackTrace();}
		}
	}
	
	public void updateLeftGroupTeam(){
		if(getLeftGroupName() != ""){
			List<Team> teamList = model.getTeamsInGroup(getLeftGroupName());
			
			if(teamList.size() == 0){
				controller.loadTeams(model.getGroupByName(getLeftGroupName()));
				teamList = model.getTeamsInGroup(getLeftGroupName());
			}

			comboBoxLeftTeamTeamList.removeAllItems();
			for(Iterator<Team> it = teamList.iterator(); it.hasNext();){
				comboBoxLeftTeamTeamList.addItem(it.next().getName());
			}
			
		}
	}
	
	public void updateRightGroupTeam(){
		if(getRightGroupName() != ""){
			List<Team> teamList = model.getTeamsInGroup(getRightGroupName());
			
			if(teamList.size() == 0){
				controller.loadTeams(model.getGroupByName(getRightGroupName()));
				teamList = model.getTeamsInGroup(getRightGroupName());
			}

			comboBoxRightTeamTeamList.removeAllItems();
			for(Iterator<Team> it = teamList.iterator(); it.hasNext();){
				comboBoxRightTeamTeamList.addItem(it.next().getName());
			}
		}
	}
	
	public void updateLeftGroupPlayers(){
		if(getLeftGroupName() != "" && getLeftTeamName() != ""){
			List<User> userList = model.getUsersInTeam(getLeftTeamName());
			
			if(userList.size() == 0){
				controller.loadUsers(model.getTeamObjectInGroup(getLeftGroupName(), getLeftTeamName()));
				userList = model.getUsersInTeam(getLeftTeamName());
			}
		
			clearLeftTeamPlayersNameComboBox();
			for(Iterator<User> it = userList.iterator(); it.hasNext();){
				User user = it.next();
				addPlayerNameToAllLeftTeamComboBox(user.getFirstName() + " " + user.getLastName());
			}
			
			shufflePlayerComboSelection("Left");
			
		}
	}
	
	public void updateRightGroupPlayers(){
		if(getRightGroupName() != "" && getRightTeamName() != ""){
			List<User> userList = model.getUsersInTeam(getRightTeamName());
			
			if(userList.size() == 0){
				controller.loadUsers(model.getTeamObjectInGroup(getRightGroupName(), getRightTeamName()));
				userList = model.getUsersInTeam(getRightTeamName());
			}
			clearRightTeamPlayersNameComboBox();
			for(Iterator<User> it = userList.iterator(); it.hasNext();){
				User user = it.next();
				addPlayerNameToAllRightTeamComboBox(user.getFirstName() + " " + user.getLastName());
			}
			shufflePlayerComboSelection("Right");
			
		}
	}
	
	private ItemListener itemListenerChangedGroupLeft = new ItemListener() {
		public void itemStateChanged(ItemEvent e) {			
			updateLeftGroupTeam();
		}
	};
	
	private ItemListener itemListenerChangedGroupRight = new ItemListener() {
		public void itemStateChanged(ItemEvent e) {			
			updateRightGroupTeam();
		}
	};
	
	private ItemListener itemListenerChangedTeamLeft = new ItemListener() {
		public void itemStateChanged(ItemEvent e) {		
			updateLeftGroupPlayers();
		}
	};
	
	private ItemListener itemListenerChangedTeamRight = new ItemListener() {
		public void itemStateChanged(ItemEvent e) {		
			updateRightGroupPlayers();
		}
	};
	
	public Integer getQuestionSetTableSelectedRow() {
		return questionnairePanel.getTableQuestionnaireList().getSelectedRow()-1;
	}
	
	public void RemoveQuestionnaire(Integer RowToRemove) {
		if(RowToRemove > 0 && dm.getRowCount()-1 >= RowToRemove) {
			dm.removeRow(RowToRemove);
		}
	}
	
	public void setModel(CreateNewGameModel model) {
		this.model = model;
	}
	
	public void setController(CreateNewGameController controller) {
		this.controller = controller;
	}
	
	public void addGroupName(String groupName) {
		comboBoxLeftTeamGroup.addItem(groupName);
		comboBoxRightTeamGroup.addItem(groupName);
	}
	
	public String getLeftTeamName() {
		String itemSelected = "";
		
		if(comboBoxLeftTeamTeamList.getItemCount() > 0)
		{
			itemSelected = (String)comboBoxLeftTeamTeamList.getSelectedItem();
		}
		
		return itemSelected;
	}
	
	public String getRightTeamName() {
		String itemSelected = "";
		
		if(comboBoxRightTeamTeamList.getItemCount() > 0)
		{
			itemSelected = (String)comboBoxRightTeamTeamList.getSelectedItem();
		}
		
		return itemSelected;
	}
	
	public String getLeftGroupName() {
		String itemSelected = "";
		
		if(comboBoxLeftTeamGroup.getItemCount() > 0)
		{
			itemSelected = (String)comboBoxLeftTeamGroup.getSelectedItem();
		}
		
		return itemSelected;
	}
	
	public String getRightGroupName() {
		String itemSelected = "";
		
		if(comboBoxRightTeamGroup.getItemCount() > 0)
		{
			itemSelected = (String)comboBoxRightTeamGroup.getSelectedItem();
		}
		
		return itemSelected;
	}
	
	public String getGameName() {
		return txtfldGameName.getText();
	}
	
	public String getLeftTeamPlayer1() {
		String itemSelected = "";
		
		if(comboBoxLeftTeamPlayer1.getItemCount() > 0)
		{
			itemSelected = (String)comboBoxLeftTeamPlayer1.getSelectedItem();
		}
		
		return itemSelected;
	}
	public String getLeftTeamPlayer2() {
		String itemSelected = "";
		
		if(comboBoxLeftTeamPlayer2.getItemCount() > 0)
		{
			itemSelected = (String)comboBoxLeftTeamPlayer2.getSelectedItem();
		}
		
		return itemSelected;
	}
	public String getLeftTeamPlayer3() {
		String itemSelected = "";
		
		if(comboBoxLeftTeamPlayer3.getItemCount() > 0)
		{
			itemSelected = (String)comboBoxLeftTeamPlayer3.getSelectedItem();
		}
		
		return itemSelected;
	}
	public String getLeftTeamPlayer4() {
		String itemSelected = "";
		
		if(comboBoxLeftTeamPlayer4.getItemCount() > 0)
		{
			itemSelected = (String)comboBoxLeftTeamPlayer4.getSelectedItem();
		}
		
		return itemSelected;
	}
	public String getRightTeamPlayer1() {
		String itemSelected = "";
		
		if(comboBoxRightTeamPlayer1.getItemCount() > 0)
		{
			itemSelected = (String)comboBoxRightTeamPlayer1.getSelectedItem();
		}
		
		return itemSelected;
	}
	public String getRightTeamPlayer2() {
		String itemSelected = "";
		
		if(comboBoxRightTeamPlayer2.getItemCount() > 0)
		{
			itemSelected = (String)comboBoxRightTeamPlayer2.getSelectedItem();
		}
		
		return itemSelected;
	}
	public String getRightTeamPlayer3() {
		String itemSelected = "";
		
		if(comboBoxRightTeamPlayer3.getItemCount() > 0)
		{
			itemSelected = (String)comboBoxRightTeamPlayer3.getSelectedItem();
		}
		
		return itemSelected;
	}
	public String getRightTeamPlayer4() {
		String itemSelected = "";
		
		if(comboBoxRightTeamPlayer4.getItemCount() > 0)
		{
			itemSelected = (String)comboBoxRightTeamPlayer4.getSelectedItem();
		}
		
		return itemSelected;
	}
	public Object getPanelContent() {
		return panelContent;
	}
	
	public void addRowToTemplateTableModel(Object[] data){
		DefaultTableModel dm = (DefaultTableModel)templatePanel.getTemplateTable().getModel();
		dm.addRow(data);
		templatePanel.getTemplateTable().setModel(dm);
	}
	
	public TemplateCreationZonePanel addNewTemplateZone() {	
		TemplateCreationZonePanel newQuestionnaireZone = new TemplateCreationZonePanel();
		Box verticalBox = templatePanel.getVerticalBoxTemplateCreationZones();
		verticalBox.add(newQuestionnaireZone);
		newQuestionnaireZone.setQuestionSeriesName("Series " + (verticalBox.getComponentCount()));
		
		if(verticalBox.getComponentCount() > 1) {
			TemplateCreationZonePanel previousQuestionnaireZone = (TemplateCreationZonePanel) verticalBox.getComponent(verticalBox.getComponentCount()-2);
			previousQuestionnaireZone.getBtnAdd().setVisible(false);
			previousQuestionnaireZone.getBtnRemove().setVisible(true);
		}
		else if (verticalBox.getComponentCount() == 1) {
			TemplateCreationZonePanel previousQuestionnaireZone = (TemplateCreationZonePanel) verticalBox.getComponent(verticalBox.getComponentCount()-1);
			previousQuestionnaireZone.getBtnRemove().setVisible(false);
		}
		
		return newQuestionnaireZone;
	}
	
	public void setTextOfTxtFieldTemplateName(String t){
		templatePanel.getTxtFieldTemplateName().setText(t);
	}
	
	public void removeAllQuestionnaireCreationPanel(){
		Box verticalBox = templatePanel.getVerticalBoxTemplateCreationZones();
		int count = verticalBox.getComponentCount();
		for (int i = 0; i<count; i++){
			verticalBox.remove(0);
		}
	}
	
	public List<TemplateSection> getTemplateSections(){
		List<TemplateSection> templateSections = new ArrayList<TemplateSection>();
		TemplateCreationZonePanel currentPanel;
		Box verticalBox = templatePanel.getVerticalBoxTemplateCreationZones();
		for (int i = 0; i < verticalBox.getComponentCount(); i++){
			currentPanel = (TemplateCreationZonePanel)verticalBox.getComponent(i);
			
			Category category = getCategory(currentPanel.getQuestionCategory(), currentPanel.getQuestionSubCategory());
			Degree difficulty = currentPanel.getQuestionDifficulty();
			int nbQuestions = currentPanel.getQuestionQuantity();
			int points = currentPanel.getQuestionValue();
			QuestionType questionType = currentPanel.getQuestionType();
			QuestionTarget questionTarget = currentPanel.getQuestionTarget();
			String description = currentPanel.getDescription();
			
			TemplateSection templateSection;
			if (category != null){
				templateSection = new TemplateSection(category.getKey(), difficulty, nbQuestions, points, questionType, questionTarget, description);
			}
			else{
				templateSection = new TemplateSection(null, difficulty, nbQuestions, points, questionType, questionTarget, description);
			}
			templateSections.add(templateSection);
		}
		return templateSections;
	}

	public Category getCategory(CategoryType categoryType, String subCategory){
		List<Category> categories = model.getCategories();
		if (categories == null){
			categories = RequestsWebService.getCategories(RequestsWebService.getAuthorization());
		}
		for (Category category : categories){
			if (category.getType() == categoryType && category.getName().equals(subCategory)){
				return category;
			}
		}
		return null;
	}
	
	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}
	
	public boolean isGenerateGameFromTemplate(){
		if (tabbedPane.getSelectedIndex() == 1){
			return true;
		}
		return false;
	}
	
	public String getTextOfTxtFieldTemplateName(){
		return templatePanel.getTxtFieldTemplateName().getText();
	}

	public void showWebServicePopup(String methodName) {

	}

	public boolean checkForGameName() {
		if(txtfldGameName.getText().equals("")){
			txtfldGameName.setBackground(Color.RED);
			return false;
		}
		return true;
	}

	public boolean checkForDuplicateGroupNTeam() {
		boolean isUnique = true;
		if(((String)comboBoxLeftTeamTeamList.getSelectedItem()).equals((String)comboBoxRightTeamTeamList.getSelectedItem())){
			comboBoxLeftTeamTeamList.setBackground(Color.red);
			comboBoxRightTeamTeamList.setBackground(Color.red);
			isUnique = false;
		}
		return isUnique;
	}
	
	public void addGameDataToTable(Object[] gameData) {
		DefaultTableModel dm = (DefaultTableModel)gamePanel.getGameTable().getModel();
		dm.addRow(gameData);
		gamePanel.getGameTable().setModel(dm);
	}

	public boolean isLoadGeneratedGame() {
		if (tabbedPane.getSelectedIndex() == 2){
			return true;
		}
		else{
			return false;
		}
	}

	public Game getSelectedGame() {
		if (gamePanel.getTableSelectedRow() >= 0){
			return model.getGameByIdx(gamePanel.getTableSelectedRow());
		}
		return null;
	}
	
	public void clearQuestionSetList() {
		questionnairePanel.clearQuestionSetList();
	}

	public void clearTemplateList() {
		templatePanel.clearTemplateList();
		templatePanel.clearTemplateCreationZone();
		addNewTemplateSection(CategoryType.Unknown, "", Degree.normal, QuestionType.General, 10, 10, QuestionTarget.Collectif, "");
	}

	public void clearOpenGameList() {
		gamePanel.clearGameList();
	}

	public void clearTeamsCompletly() {	
		clearLeftGroupComboBox();
		clearRightGroupComboBox();
		clearLeftTeamComboBox();
		clearRightTeamComboBox();
		clearLeftTeamPlayersNameComboBox();
		clearRightTeamPlayersNameComboBox();
	}

	public void downloadMediaFiles(List<String[]> mediaFilesPath) {
		downloadManager.setFilesToDownload(mediaFilesPath);
	}

	public void showWebServicePopup(CreateNewGameAction action) {
		// TODO Auto-generated method stubs
	}
}
