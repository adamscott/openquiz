package openquiz.website.pages;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import ca.openquiz.comms.RequestsWebService;
import ca.openquiz.comms.enums.QuestionType;
import ca.openquiz.comms.enums.Role;
import ca.openquiz.comms.model.Group;
import ca.openquiz.comms.model.GroupRole;
import ca.openquiz.comms.model.Question;
import ca.openquiz.comms.model.QuestionAnagram;
import ca.openquiz.comms.model.QuestionAssociation;
import ca.openquiz.comms.model.QuestionCorrectWord;
import ca.openquiz.comms.model.QuestionGeneral;
import ca.openquiz.comms.model.QuestionIdentification;
import ca.openquiz.comms.model.QuestionIntru;
import ca.openquiz.comms.model.QuestionMedia;
import ca.openquiz.comms.response.UserResponse;
import openquiz.website.util.Messages;
import openquiz.website.util.Misc;
import openquiz.website.util.SessionConstants;
import openquiz.website.windows.GenericWindow;
import openquiz.website.windows.QuestionWindow;
import openquiz.website.windows.GenericWindow.WindowType;
import openquiz.website.windows.QuestionWindow.QuestionWindowType;
import openquiz.website.Website_UI;

import com.vaadin.Application;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Select;
import com.vaadin.ui.Table;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import org.vaadin.navigator.Navigator;

public class ManageQuestions extends Panel implements Navigator.View {
	private static final long serialVersionUID = 7811455616247898086L;

	private int QUESTIONS_PER_PAGE = 25;
	
	private String QUESTION_ANAGRAM = Messages.getString("All.anagram");
	private String QUESTION_ASSOCIATION = Messages.getString("All.association");
	private String QUESTION_CORRECTWORD = Messages.getString("All.correctWord");
	private String QUESTION_GENERAL = Messages.getString("All.general");
	private String QUESTION_IDENTIFICATION = Messages.getString("All.identification");
	private String QUESTION_INTRU = Messages.getString("All.intru");
	private String QUESTION_MEDIA = Messages.getString("All.media");

	private static final Logger log = Logger.getLogger(ManageQuestions.class.getName());
	private Application application;
	
	private Button btnAdd;
	private Button btnEditQuestion;
	private Button btnPrevPage;
	private Button btnRemoveQuestion;
	private CheckBox chkBoxShowReported;
	private GenericWindow deleteQuestionWindow;
	private int currentPage = 1;
	private Select selectQuestionType;
	private String groupID;
	private boolean isAdmin = false;
	private Table tableQuestions;
	
	String userKey;
	String authString;
	String sessionKey;

	public ManageQuestions() {
		super(Messages.getString("All.manageQuestions"));
		super.setSizeFull();
	}

	@Override
	public void init(Navigator navigator, Application application) 
	{
		this.application = application;
		if(application != null)
		{
			userKey = (String)((Website_UI)application).getSession().getAttribute(SessionConstants.SESSION_USER_KEY);
			authString = (String)((Website_UI)application).getSession().getAttribute(SessionConstants.SESSION_AUTHORIZATION_STRING);
			sessionKey = (String)((Website_UI)application).getSession().getAttribute(SessionConstants.SESSION_WS_SESSION_KEY);

			tableQuestions = new Table(Messages.getString("All.questions"));
			tableQuestions.addContainerProperty(Messages.getString("All.type"), String.class, null);
			tableQuestions.addContainerProperty(Messages.getString("All.category"), String.class, null);
			tableQuestions.addContainerProperty(Messages.getString("All.question"), String.class, null);
			tableQuestions.addContainerProperty(Messages.getString("All.answer"), String.class, null);
			tableQuestions.addContainerProperty(Messages.getString("All.reportedReason"), String.class, null);
			
			tableQuestions.setColumnCollapsingAllowed(true);
			tableQuestions.setColumnCollapsed(Messages.getString("All.reportedReason"), true);
			tableQuestions.setImmediate(true);
			tableQuestions.setSelectable(true);
			tableQuestions.setWidth("100%");

			tableQuestions.addListener(new Property.ValueChangeListener() {
				private static final long serialVersionUID = -8383803927240418195L;

				@Override
				public void valueChange(ValueChangeEvent event) {
					if(btnEditQuestion != null)
						btnEditQuestion.setEnabled(tableQuestions.getValue() != null);
					if(btnRemoveQuestion != null)
						btnRemoveQuestion.setEnabled(tableQuestions.getValue() != null);
				}
			});
		}
	}
	
	@Override
	public void navigateTo(String requestedDataId) 
	{
		if(application == null)
			return;
		
		UserResponse userResponse = RequestsWebService.getCurrentUserInfo(authString);
		if(userResponse == null)
		{
			application.getMainWindow().showNotification(Messages.getString("All.invalidFields"));
			return;
		}
		
		isAdmin = userResponse.isAdmin();
		
		this.removeAllComponents();
		
		final List<Group> groupList = new ArrayList<Group> ();
		final Select selectGroup = new Select(Messages.getString("All.group"));
		selectGroup.setInvalidAllowed(false);
		selectGroup.setImmediate(true);

		selectGroup.addListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = -8383803927240418195L;

			@Override
			public void valueChange(ValueChangeEvent event) {
		    	Object selectedGroup = selectGroup.getValue();
		    	if(selectedGroup == null)
		    	{
		    		groupID = null;
			    	updateTable();
		    		return;
		    	}
		    	
		    	if(selectedGroup.toString().equals(Messages.getString("All.public")))
		    		groupID = selectedGroup.toString();
		    	else
		    	{
		    		for(Group group : Misc.nullSafe(groupList))
		    		{
		    			if(group.toString().equals(selectedGroup.toString()))
    					{
		    				groupID = group.getKey();
		    				break;
    					}
		    		}
		    	}

				btnAdd.setEnabled(groupID != null && !groupID.isEmpty());
				
		    	currentPage = 1;
		    	updateTable();
			}
		});
		
		if(isAdmin)
			selectGroup.addItem(Messages.getString("All.public"));

		Group tmpGroup;
		for(GroupRole groupRole : Misc.nullSafe(userResponse.getGroupRoles()))
		{
			if(groupRole.getRole() == Role.QuestionManager || groupRole.getRole() == Role.Manager)
			{
				tmpGroup = RequestsWebService.getGroup(groupRole.getGroupKey());
				if(tmpGroup != null)
				{
					groupList.add(tmpGroup);
					selectGroup.addItem(tmpGroup.toString());
				}
			}
		}
		
		selectQuestionType = new Select(Messages.getString("ManageQuestions.questionType"));
		selectQuestionType.setInvalidAllowed(false);
		selectQuestionType.setImmediate(true);
		
		for(QuestionType type : QuestionType.values())
			selectQuestionType.addItem(type);
		
		selectQuestionType.addListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 5967544199804369565L;

			@Override
			public void valueChange(ValueChangeEvent event) {
		    	currentPage = 1;
		    	updateTable();
			}
		});
		
		chkBoxShowReported = new CheckBox(Messages.getString("ManageQuestions.showReported"));
		chkBoxShowReported.setImmediate(true);
		chkBoxShowReported.addListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 5967544199804369565L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				tableQuestions.setColumnCollapsed(Messages.getString("All.reportedReason"), !chkBoxShowReported.booleanValue());
				
				currentPage = 1;
		    	updateTable();
			}
		});
		
		HorizontalLayout horLayout = new HorizontalLayout();
		horLayout.addComponent(selectGroup);
		horLayout.addComponent(selectQuestionType);
		horLayout.addComponent(chkBoxShowReported);
		horLayout.setComponentAlignment(chkBoxShowReported, Alignment.BOTTOM_LEFT);
		horLayout.setSpacing(true);
		this.addComponent(horLayout);
		this.addComponent(tableQuestions);
		
		CreateTable();
	}

	private void CreateTable()
	{		
		btnAdd = new Button(Messages.getString("All.add"));
		btnAdd.setEnabled(false);
		btnAdd.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = 7749413929208609015L;

			public void buttonClick(ClickEvent event) {
				ManageQuestions parent = null; 
				Component tempParent = ((Button) event.getSource()).getParent();
				while(parent == null && tempParent != null)
				{
					if(tempParent instanceof ManageQuestions)
						parent = (ManageQuestions) tempParent;
					tempParent = tempParent.getParent();
				}
				
				String group = groupID.equals(Messages.getString("All.public")) ? "" : groupID;
				application.getMainWindow().addWindow(new QuestionWindow(null, null, authString, QuestionWindowType.ADD, parent, isAdmin, group));
		    }
		});

		btnEditQuestion = new Button(Messages.getString("All.edit"));
		btnEditQuestion.setEnabled(false);
		btnEditQuestion.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = -4203236765236107840L;
			public void buttonClick(ClickEvent event) {
				
				Object objectSelected = tableQuestions.getValue();
				Item itemSelected = tableQuestions.getItem(objectSelected);
				
				if(itemSelected == null)
					return;
				
				String typeString = itemSelected.getItemProperty(Messages.getString("All.type")).toString();
				
				ManageQuestions parent = null; 
				Component tempParent = ((Button) event.getSource()).getParent();
				while(parent == null && tempParent != null)
				{
					if(tempParent instanceof ManageQuestions)
						parent = (ManageQuestions) tempParent;
					tempParent = tempParent.getParent();
				}
				 
				
				if(typeString.equals(QUESTION_ANAGRAM))
					application.getMainWindow().addWindow(new QuestionWindow(objectSelected.toString(), QuestionType.Anagram, authString, QuestionWindowType.EDIT, parent, isAdmin));
				else if(typeString.equals(QUESTION_ASSOCIATION))
					application.getMainWindow().addWindow(new QuestionWindow(objectSelected.toString(), QuestionType.Association, authString, QuestionWindowType.EDIT, parent, isAdmin));
				else if(typeString.equals(QUESTION_GENERAL))
					application.getMainWindow().addWindow(new QuestionWindow(objectSelected.toString(), QuestionType.General, authString, QuestionWindowType.EDIT, parent, isAdmin));
				else if(typeString.equals(QUESTION_IDENTIFICATION))
					application.getMainWindow().addWindow(new QuestionWindow(objectSelected.toString(), QuestionType.Identification, authString, QuestionWindowType.EDIT, parent, isAdmin));
				else if(typeString.equals(QUESTION_INTRU))
					application.getMainWindow().addWindow(new QuestionWindow(objectSelected.toString(), QuestionType.Intru, authString, QuestionWindowType.EDIT, parent, isAdmin));
				else if(typeString.equals(QUESTION_MEDIA))
					application.getMainWindow().addWindow(new QuestionWindow(objectSelected.toString(), QuestionType.Media, authString, QuestionWindowType.EDIT, parent, isAdmin));
				else
				{
			        log.warning("Encountered unexpected class : " + typeString);
				}
			}
		});

		btnRemoveQuestion = new Button(Messages.getString("All.delete"));
		btnRemoveQuestion.setEnabled(false);
		btnRemoveQuestion.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = 751276946495828548L;

			public void buttonClick(ClickEvent event) {

				Object objectSelected = tableQuestions.getValue();
				Item itemSelected = tableQuestions.getItem(objectSelected);
				
				if(itemSelected == null)
					return;
				
				List<Component> components = new ArrayList<Component>();
				Label groupName = new Label(Messages.getString("ManageQuestions.deleteQuestionConfirmation") + itemSelected.getItemProperty(Messages.getString("All.question")));
				components.add(groupName);

		    	ClickListener clickListener = new ClickListener() {
					private static final long serialVersionUID = 5832425766457600812L;

					@Override
					public void buttonClick(ClickEvent event) {
						
						if(tableQuestions.getValue() == null)
						{
							application.getMainWindow().showNotification(Messages.getString("All.invalidFields"));
							return;	
						}
						
						String questionID = tableQuestions.getValue().toString();
			    		
			    		boolean success = RequestsWebService.deleteQuestion(questionID, authString);
			    		if(success)
			    		{
			    			application.getMainWindow().showNotification(Messages.getString("All.successProcessingRequest"));
			    			deleteQuestionWindow.windowClose(null);
			    			updateTable();
			    		}
			    		else
			    			application.getMainWindow().showNotification(Messages.getString("All.errorProcessingRequest"));
					}
				};
				
				deleteQuestionWindow = new GenericWindow(Messages.getString("ManageQuestions.deleteQuestion"), components, clickListener, WindowType.CONFIRM);
				application.getMainWindow().addWindow(deleteQuestionWindow);
		    }
		});

		// Next and previous page
		btnPrevPage = new Button(Messages.getString("All.prevPage"));
		btnPrevPage.setEnabled(false);
		btnPrevPage.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = -3767562099750326514L;

			public void buttonClick(ClickEvent event) {
				--currentPage;
				ManageQuestions.this.updateTable();
		    }
		});
		
		Button btnNextPage = new Button(Messages.getString("All.nextPage"));
		btnNextPage.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = -3767562099750326514L;

			public void buttonClick(ClickEvent event) {
				++currentPage;
				ManageQuestions.this.updateTable();
		    }
		});

		HorizontalLayout innerHorLayout1 = new HorizontalLayout();
		innerHorLayout1.addComponent(btnAdd);
		innerHorLayout1.addComponent(btnEditQuestion);
		innerHorLayout1.addComponent(btnRemoveQuestion);
		
		HorizontalLayout innerHorLayout2 = new HorizontalLayout();
		innerHorLayout2.addComponent(btnPrevPage);
		innerHorLayout2.addComponent(btnNextPage);
		
		HorizontalLayout horLayout = new HorizontalLayout();
		horLayout.addComponent(innerHorLayout1);
		horLayout.addComponent(innerHorLayout2);
		horLayout.setWidth("100%");
		horLayout.setComponentAlignment(innerHorLayout1, Alignment.MIDDLE_LEFT);
		horLayout.setComponentAlignment(innerHorLayout2, Alignment.MIDDLE_RIGHT);
		this.addComponent(horLayout);
	}
	
	public void updateTable()
	{
		List<Question> questionList = null;
		
		if(groupID == null || selectQuestionType.getValue() == null)
		{
			tableQuestions.removeAllItems();
			return;
		}
		
		QuestionType type = QuestionType.valueOf(selectQuestionType.getValue().toString());
		if(groupID.equals(Messages.getString("All.public"))) // Get the public group
			questionList = RequestsWebService.getQuestions(type, QUESTIONS_PER_PAGE, currentPage, authString, chkBoxShowReported.booleanValue());
		else if (!groupID.isEmpty())
			questionList = RequestsWebService.getQuestions(type, groupID, QUESTIONS_PER_PAGE, currentPage, authString, chkBoxShowReported.booleanValue());
		
		if((questionList == null || questionList.isEmpty()) && currentPage > 1)	
		{
			--currentPage;
			application.getMainWindow().showNotification(Messages.getString("All.notAvailable"));
			return;
		}

		tableQuestions.removeAllItems();

		String questionType = null; 
		String questionCategory = null;
		String questionQuestion = null; 
		String questionAnswer = null;
		String questionReportedReason = null;
		
		for(Question question : Misc.nullSafe(questionList))
		{ 
			questionCategory = Messages.getString("All." + question.getCategory().getType().toString().toLowerCase());

			questionReportedReason = ((Question) question).getReportReason().name();
			
			if(question instanceof QuestionAnagram)
			{
				questionType = QUESTION_ANAGRAM;
				questionQuestion = ((QuestionAnagram) question).getAnagram();
			}
			else if(question instanceof QuestionAssociation)
			{
				questionType = QUESTION_ASSOCIATION;
				questionQuestion = ((QuestionAssociation) question).getQuestion();
				questionAnswer = Messages.getString("All.notAvailable");
			}
			else if(question instanceof QuestionCorrectWord)
			{
				questionType = QUESTION_CORRECTWORD;
				questionQuestion = ((QuestionCorrectWord) question).getQuestion();
				questionAnswer = ((QuestionCorrectWord) question).getAnswer();
			}
			else if(question instanceof QuestionGeneral)
			{
				questionType = QUESTION_GENERAL;
				questionQuestion = ((QuestionGeneral) question).getQuestion();
				questionAnswer = ((QuestionGeneral) question).getAnswer();
			}
			else if(question instanceof QuestionIdentification)
			{
				questionType = QUESTION_IDENTIFICATION;
				questionQuestion = Messages.getString("All.notAvailable");
				questionAnswer = ((QuestionIdentification) question).getAnswer();
			}
			else if(question instanceof QuestionIntru)
			{
				questionType = QUESTION_INTRU;
				questionQuestion = ((QuestionIntru) question).getQuestion();
				questionAnswer = ((QuestionIntru) question).getAnswer();
			}
			else if(question instanceof QuestionMedia)
			{
				questionType = QUESTION_MEDIA;
				questionQuestion = ((QuestionMedia) question).getStatement();
				questionAnswer = ((QuestionMedia) question).getAnswer();
			}
			else
			{
				log.warning("Encountered question of type unknown");
			}
			
			tableQuestions.addItem(new Object[] {questionType, questionCategory, questionQuestion, questionAnswer, questionReportedReason}, question.getKey());
		}
		
		btnPrevPage.setEnabled(currentPage > 1);
	}
	
	@Override
	public String getWarningForNavigatingFrom() {
		return null;
	}
}
