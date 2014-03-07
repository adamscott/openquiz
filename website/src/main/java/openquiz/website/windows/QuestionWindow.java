package openquiz.website.windows;

import java.util.ArrayList;
import java.util.List;

import openquiz.website.pages.ManageQuestions;
import openquiz.website.util.Messages;
import openquiz.website.util.Misc;
import openquiz.website.windows.GenericWindow.WindowType;
import ca.openquiz.comms.RequestsWebService;
import ca.openquiz.comms.enums.CategoryType;
import ca.openquiz.comms.enums.Degree;
import ca.openquiz.comms.enums.Language;
import ca.openquiz.comms.enums.QuestionType;
import ca.openquiz.comms.model.Category;
import ca.openquiz.comms.model.Choice;
import ca.openquiz.comms.model.Question;
import ca.openquiz.comms.model.QuestionAnagram;
import ca.openquiz.comms.model.QuestionAssociation;
import ca.openquiz.comms.model.QuestionGeneral;
import ca.openquiz.comms.model.QuestionIdentification;
import ca.openquiz.comms.model.QuestionIntru;
import ca.openquiz.comms.model.QuestionMedia;
import ca.openquiz.comms.response.KeyResponse;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Select;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class QuestionWindow extends Window implements Window.CloseListener {
	private static final long serialVersionUID = -8486123889532847860L;

	public enum QuestionWindowType {
	    ADD, EDIT
	}
	
	private Button btnApply;
	private Button btnDelCategory;
	private Button btnAddCategory;
	private Select selectCategory;
	private Select selectDegree;
	private Select selectLanguage;
	private CheckBox chkBIsReported;
	
	private String authString;
	private String groupID;
	private boolean isAdmin;
	private QuestionWindowType questionWindowType;
	private GenericWindow deleteGroupNameWindow;
	private GenericWindow addCategoryWindow;
	private List<Category> categories;
	
	ManageQuestions parent;
	
	public QuestionWindow(String questionKey, QuestionType questionType, String authString, QuestionWindowType questionWindowType, Component parent, boolean isAdmin) {
		super(Messages.getString("All.manageQuestions"));
		init(questionKey, questionType, authString, questionWindowType, parent, isAdmin, null);
	}

	public QuestionWindow(String questionKey, QuestionType questionType, String authString, QuestionWindowType questionWindowType, Component parent, boolean isAdmin, String groupID) {
		super(Messages.getString("All.manageQuestions"));
		init(questionKey, questionType, authString, questionWindowType, parent, isAdmin, groupID);
	}
	
	private void init(String questionKey, QuestionType questionType, final String authString, QuestionWindowType questionWindowType, Component parent, boolean isAdmin, String groupID)
	{
		this.parent = (ManageQuestions) parent;
		this.authString = authString;
		this.groupID = groupID;
		this.isAdmin = isAdmin;
		this.questionWindowType = questionWindowType;

		this.setModal(true);
		this.setWidth("425");
		this.setResizable(false);
		this.setDraggable(true);
		this.setTheme("openquiz");

		btnApply = new Button(Messages.getString("All.apply"));

		categories = RequestsWebService.getCategories(authString);
		if(categories == null) categories = new ArrayList<Category>();
		
		selectCategory = new Select (Messages.getString("All.category"));
		for(Category currCategory : categories)
		{
			selectCategory.addItem(currCategory.getName() + " - " + currCategory.getType().toString());
		}
		selectCategory.setInvalidAllowed(false);
		selectCategory.setImmediate(true);
		selectCategory.addListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 982572697208652834L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				btnDelCategory.setEnabled(selectCategory.getValue() != null);
			}
		});
		
		btnDelCategory = new Button("-");
		btnDelCategory.setEnabled(false);
		btnDelCategory.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = -3067189261814194178L;

			public void buttonClick(ClickEvent event) {
				List<Component> components = new ArrayList<Component>();
				Label categoryName = new Label(Messages.getString("ManageQuestions.deleteCategoryConfirmation") + selectCategory.getValue().toString());

				components.add(categoryName);

		    	ClickListener clickListener = new ClickListener() {
					private static final long serialVersionUID = -2040476767675464540L;

					@Override
					public void buttonClick(ClickEvent event) {
						String catKey = ""; 
						for(Category category : categories)
						{
							if(selectCategory.getValue().toString().equals(category.getName() + " - " + category.getType()))
								catKey = category.getKey();
						}
						
			    		boolean success = false;
			    		if(!catKey.isEmpty())
			    			success = RequestsWebService.deleteCategory(catKey, authString);
			    		if(success)
			    		{
			    			QuestionWindow.this.getApplication().getMainWindow().showNotification(Messages.getString("All.successProcessingRequest"));
			    			deleteGroupNameWindow.windowClose(null);
			    			selectCategory.removeItem(selectCategory.getValue());
			    		}
			    		else
			    			QuestionWindow.this.getApplication().getMainWindow().showNotification(Messages.getString("All.errorProcessingRequest"));
			    	}
				};
				
				deleteGroupNameWindow = new GenericWindow(Messages.getString("All.deleteGroup"), components, clickListener, WindowType.CONFIRM);
				QuestionWindow.this.getApplication().getMainWindow().addWindow(deleteGroupNameWindow);
		    }
		});
		
		btnAddCategory = new Button("+");
		btnAddCategory.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = -8363001470214255922L;

			public void buttonClick(ClickEvent event) {
				List<Component> components = new ArrayList<Component>();

				final TextField categoryName = new TextField(Messages.getString("All.name"));
				final Select categoryType = new Select(Messages.getString("All.type")); 

				for(CategoryType type : CategoryType.values())
					categoryType.addItem(type);
				
				components.add(categoryName);
				components.add(categoryType);
				
		    	ClickListener clickListener = new ClickListener() {
					private static final long serialVersionUID = 5832425766457600812L;

					@Override
					public void buttonClick(ClickEvent event) {
						
						if(categoryName.getValue().toString().isEmpty() || categoryType.getValue() == null)
						{
							QuestionWindow.this.getApplication().getMainWindow().showNotification(Messages.getString("All.invalidFields"));
							return;	
						}
						
						Category category = new Category();
						category.setName(categoryName.getValue().toString());
						category.setType(CategoryType.valueOf(categoryType.getValue().toString()));
			    		
			    		KeyResponse key = RequestsWebService.addCategory(category, authString);
			    		if(key != null && (key.getError() == null || key.getError().isEmpty()))
			    		{
			    			QuestionWindow.this.getApplication().getMainWindow().showNotification(Messages.getString("All.successProcessingRequest"));
			    			addCategoryWindow.windowClose(null);

		    				selectCategory.addItem(category.getName() + " - " + category.getType());
			    		}
			    		else
			    			QuestionWindow.this.getApplication().getMainWindow().showNotification(Messages.getString("All.errorProcessingRequest"));
					}
				};
				
				addCategoryWindow = new GenericWindow(Messages.getString("ManageQuestions.deleteQuestion"), components, clickListener, WindowType.CONFIRM);
				QuestionWindow.this.getApplication().getMainWindow().addWindow(addCategoryWindow);
		    }
		});
		
		selectDegree = new Select (Messages.getString("All.degree"));
		for(Degree currDegree : Degree.values())
		{
			selectDegree.addItem(currDegree);
		}
		selectDegree.setInvalidAllowed(false);
		
		selectLanguage = new Select (Messages.getString("All.language"));
		for(Language language : Language.values())
		{
			selectLanguage.addItem(language);
		}
		selectLanguage.setInvalidAllowed(false);
		
		chkBIsReported = new CheckBox(Messages.getString("All.reported"));
		
		if(questionType == null)
		{
			final Select selectType = new Select(Messages.getString("All.type"));
			for(QuestionType type : QuestionType.values())
			{
				selectType.addItem(type);
			}
			selectType.setInvalidAllowed(false);
			selectType.setImmediate(true);
			selectType.addListener(new Property.ValueChangeListener() {
				private static final long serialVersionUID = 117214771304371732L;
				
				@Override
				public void valueChange(ValueChangeEvent event) {
					if(selectType.getValue() != null)
					{
						QuestionWindow.this.removeAllComponents();
						QuestionWindow.this.addComponent(selectType);
						for(Object listener : Misc.nullSafe(btnApply.getListeners(Button.ClickEvent.class)))
							btnApply.removeListener((Button.ClickListener) listener);
						initDisplay(null, QuestionType.valueOf(selectType.getValue().toString()));
					}
				}
			});
			this.addComponent(selectType);
		}
		else
			initDisplay(questionKey, questionType);
	}

	private void initDisplay(String questionKey, QuestionType questionType)
	{
		switch(questionType)
		{
		case Anagram:
			displayAnagramQuestionnaire(questionKey);
			break;
		case Association:
			displayAssociationQuestionnaire(questionKey);
			break;
		case General:
			displayGeneralQuestionnaire(questionKey);
			break;
		case Identification:
			displayIdentificationQuestionnaire(questionKey);
			break;
		case Intru:
			displayIntruQuestionnaire(questionKey);
			break;
		case Media:
			displayMediaQuestionnaire(questionKey);
			break;
		default:
			break;
		}
		
		HorizontalLayout horLayout = new HorizontalLayout();
		horLayout.addComponent(btnApply);
		
		this.addComponent(horLayout);
	}
	
	@Override
	public void windowClose(CloseEvent e) {
		this.close();
	}
	
	private void displayAnagramQuestionnaire(String key)
	{
		final QuestionAnagram question;
		if(questionWindowType == QuestionWindowType.EDIT)
			question = RequestsWebService.getQuestionAnagram(key, authString);
		else
			question = new QuestionAnagram();
		
		VerticalLayout verLayout = new VerticalLayout();

		if(key != null) selectCategory.setValue(question.getCategory().getName() + " - " + question.getCategory().getType());
		HorizontalLayout horLayout = new HorizontalLayout();
		horLayout.addComponent(selectCategory);
		
		if(isAdmin)
		{
			horLayout.addComponent(btnDelCategory);
			horLayout.addComponent(btnAddCategory);
		}
		
		horLayout.setComponentAlignment(btnDelCategory, Alignment.BOTTOM_LEFT);
		horLayout.setComponentAlignment(btnAddCategory, Alignment.BOTTOM_LEFT);
		horLayout.setSpacing(true);
		verLayout.addComponent(horLayout);
		
		final TextField txtFAnagram = new TextField(Messages.getString("All.anagram"));
		if(key != null) txtFAnagram.setValue(question.getAnagram());
		verLayout.addComponent(txtFAnagram);
		
		final TextField txtFAnswer = new TextField(Messages.getString("All.answer"));
		if(key != null) txtFAnswer.setValue(question.getAnswer());
		verLayout.addComponent(txtFAnswer);
		
		if(key != null) selectDegree.setValue(question.getDegree());
		verLayout.addComponent(selectDegree);
		
		final TextField txtFHint = new TextField(Messages.getString("All.hint"));
		if(key != null) txtFHint.setValue(question.getHint());
		verLayout.addComponent(txtFHint);

		if(questionWindowType == QuestionWindowType.EDIT)
		{
			chkBIsReported.setValue(question.getIsReported());
			if(key != null) verLayout.addComponent(chkBIsReported);
		}
		
		if(key != null) selectLanguage.setValue(question.getLanguage());
		verLayout.addComponent(selectLanguage);
		
		this.addComponent(verLayout);

		btnApply.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = 587682720306792997L;

			public void buttonClick(ClickEvent event) {
				if(question != null)
				{
					setGeneralFields(question);
					question.setAnagram(txtFAnagram.getValue().toString());
					question.setAnswer(txtFAnswer.getValue().toString());
					question.setHint(txtFHint.getValue().toString());
				}
				addOrEditQuestion(question);
		    }
		});
	}
	
	private void displayAssociationQuestionnaire(String key)
	{		
		final QuestionAssociation question;
		if(questionWindowType == QuestionWindowType.EDIT)
			question = RequestsWebService.getQuestionAssociation(key, authString);
		else
			question = new QuestionAssociation();
		
		VerticalLayout verLayout = new VerticalLayout();

		if(key != null) selectCategory.setValue(question.getCategory().getName() + " - " + question.getCategory().getType());
		HorizontalLayout horLayout = new HorizontalLayout();
		horLayout.addComponent(selectCategory);
		
		if(isAdmin)
		{
			horLayout.addComponent(btnDelCategory);
			horLayout.addComponent(btnAddCategory);
		}
		
		horLayout.setComponentAlignment(btnDelCategory, Alignment.BOTTOM_LEFT);
		horLayout.setComponentAlignment(btnAddCategory, Alignment.BOTTOM_LEFT);
		verLayout.addComponent(horLayout);
		
		final TextField txtFQuestion = new TextField(Messages.getString("All.question"));
		if(key != null) txtFQuestion.setValue(question.getQuestion());
		verLayout.addComponent(txtFQuestion);
		
		Label lblChoices = new Label(Messages.getString("QuestionWindow.choices"));
		final VerticalLayout verTxtBoxes = new VerticalLayout();
		List<Choice> choices = question.getChoices();
		for(int i=0; choices != null && i<choices.size(); ++i)
		{
			TextField TxtFChoice = new TextField(Messages.getString("All.choice") + i);
			TextField TxtFAnswer = new TextField(Messages.getString("All.answer") + i);
			TxtFChoice.setValue(choices.get(i).getChoice());
			TxtFAnswer.setValue(choices.get(i).getAnswer());
			verTxtBoxes.addComponent(TxtFChoice);
			verTxtBoxes.addComponent(TxtFAnswer);
		}
		verLayout.addComponent(lblChoices);
		verLayout.addComponent(verTxtBoxes);
		
		Button btnAddChoice = new Button(Messages.getString("All.addChoice"));
		btnAddChoice.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = 6677123196045026464L;

			public void buttonClick(ClickEvent event) {
				int idx = verTxtBoxes.getComponentCount() / 2;

				TextField TxtFChoice = new TextField(Messages.getString("All.choice") + idx);
				TextField TxtFAnswer = new TextField(Messages.getString("All.answer") + idx);
				verTxtBoxes.addComponent(TxtFChoice);
				verTxtBoxes.addComponent(TxtFAnswer);
			}
		});
		verLayout.addComponent(btnAddChoice);
			
		if(key != null) selectDegree.setValue(question.getDegree());
		verLayout.addComponent(selectDegree);

		if(questionWindowType == QuestionWindowType.EDIT)
		{
			chkBIsReported.setValue(question.getIsReported());
			if(key != null) verLayout.addComponent(chkBIsReported);
		}
		
		if(key != null) selectLanguage.setValue(question.getLanguage());
		verLayout.addComponent(selectLanguage);
		
		this.addComponent(verLayout);

		btnApply.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = 587682720306792997L;

			public void buttonClick(ClickEvent event) {	
				if(question != null)
				{			
					question.setQuestion(txtFQuestion.getValue().toString());
					
					List<Choice> choicesList = new ArrayList<Choice>();
					for(int i=0; i<verTxtBoxes.getComponentCount(); ++i)
					{
						String tmpChoice = verTxtBoxes.getComponent(i).toString();
						++i;
						String tmpAnswer = verTxtBoxes.getComponent(i).toString();
						
						if(!tmpChoice.isEmpty() && !tmpAnswer.isEmpty())
							choicesList.add(new Choice(tmpChoice, tmpAnswer));
					}
				
					setGeneralFields(question);
					question.setChoices(choicesList);
				}
				addOrEditQuestion(question);
		    }
		});
	}
	
	private void displayGeneralQuestionnaire(String key)
	{
		final QuestionGeneral question;
		if(questionWindowType == QuestionWindowType.EDIT)
			question = RequestsWebService.getQuestionGeneral(key, authString);
		else
			question = new QuestionGeneral();
		
		VerticalLayout verLayout = new VerticalLayout();

		if(key != null) selectCategory.setValue(question.getCategory().getName() + " - " + question.getCategory().getType());
		HorizontalLayout horLayout = new HorizontalLayout();
		horLayout.addComponent(selectCategory);

		if(isAdmin)
		{
			horLayout.addComponent(btnDelCategory);
			horLayout.addComponent(btnAddCategory);
		}
		
		horLayout.setComponentAlignment(btnDelCategory, Alignment.BOTTOM_LEFT);
		horLayout.setComponentAlignment(btnAddCategory, Alignment.BOTTOM_LEFT);
		verLayout.addComponent(horLayout);
		
		final TextField txtFQuestion = new TextField(Messages.getString("All.question"));
		if(key != null) txtFQuestion.setValue(question.getQuestion());
		verLayout.addComponent(txtFQuestion);
		
		final TextField txtFAnswer = new TextField(Messages.getString("All.answer"));
		if(key != null) txtFAnswer.setValue(question.getAnswer());
		verLayout.addComponent(txtFAnswer);
		
		if(key != null) selectDegree.setValue(question.getDegree());
		verLayout.addComponent(selectDegree);
				
		if(questionWindowType == QuestionWindowType.EDIT)
		{
			chkBIsReported.setValue(question.getIsReported());
			if(key != null) verLayout.addComponent(chkBIsReported);
		}
		
		if(key != null) selectLanguage.setValue(question.getLanguage());
		verLayout.addComponent(selectLanguage);
		
		this.addComponent(verLayout);

		btnApply.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = 587682720306792997L;

			public void buttonClick(ClickEvent event) {
				if(question != null)
				{
					setGeneralFields(question);
					question.setQuestion(txtFQuestion.getValue().toString());
					question.setAnswer(txtFAnswer.getValue().toString());
				}
				addOrEditQuestion(question);
		    }
		});
	}
	
	private void displayIdentificationQuestionnaire(String key)
	{		
		final QuestionIdentification question;
		if(questionWindowType == QuestionWindowType.EDIT)
			question = RequestsWebService.getQuestionIdentification(key, authString);
		else
			question = new QuestionIdentification();
		
		VerticalLayout verLayout = new VerticalLayout();

		if(key != null) selectCategory.setValue(question.getCategory().getName() + " - " + question.getCategory().getType());
		HorizontalLayout horLayout = new HorizontalLayout();
		horLayout.addComponent(selectCategory);

		if(isAdmin)
		{
			horLayout.addComponent(btnDelCategory);
			horLayout.addComponent(btnAddCategory);
		}
		
		horLayout.setComponentAlignment(btnDelCategory, Alignment.BOTTOM_LEFT);
		horLayout.setComponentAlignment(btnAddCategory, Alignment.BOTTOM_LEFT);
		verLayout.addComponent(horLayout);
		
		Label lblStatements = new Label(Messages.getString("QuestionWindow.statements"));
		final VerticalLayout verTxtBoxes = new VerticalLayout();
		for(String statement : Misc.nullSafe(question.getStatements()))
		{
			TextField temp = new TextField();
			temp.setValue(statement);
			verTxtBoxes.addComponent(temp);
		}
		verLayout.addComponent(lblStatements);
		verLayout.addComponent(verTxtBoxes);
		
		Button btnAddStatement = new Button(Messages.getString("All.addStatement"));
		btnAddStatement.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = 7468455855459449274L;

			public void buttonClick(ClickEvent event) {
				verTxtBoxes.addComponent(new TextField());
			}
		});
		verLayout.addComponent(btnAddStatement);
		
		final TextField txtFAnswer = new TextField(Messages.getString("All.answer"));
		if(key != null) txtFAnswer.setValue(question.getAnswer());
		verLayout.addComponent(txtFAnswer);

		if(key != null) selectDegree.setValue(question.getDegree());
		verLayout.addComponent(selectDegree);

		if(questionWindowType == QuestionWindowType.EDIT)
		{
			chkBIsReported.setValue(question.getIsReported());
			if(key != null) verLayout.addComponent(chkBIsReported);
		}
		
		if(key != null) selectLanguage.setValue(question.getLanguage());
		verLayout.addComponent(selectLanguage);
		
		this.addComponent(verLayout);

		btnApply.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = 587682720306792997L;

			public void buttonClick(ClickEvent event) {
				if(question != null)
				{
					List<String> statementsList = new ArrayList<String>();
					for(int i=0; i<verTxtBoxes.getComponentCount(); ++i)
					{
						String tmpStatement = verTxtBoxes.getComponent(i).toString();
						if(!tmpStatement.isEmpty())
							statementsList.add(tmpStatement);
					}
					
					setGeneralFields(question);
					question.setStatements(statementsList);
					question.setAnswer(txtFAnswer.getValue().toString());
				}
				addOrEditQuestion(question);
		    }
		});
	}
	
	private void displayIntruQuestionnaire(String key)
	{
		final QuestionIntru question;
		if(questionWindowType == QuestionWindowType.EDIT)
			question = RequestsWebService.getQuestionIntru(key, authString);
		else
			question = new QuestionIntru();
		
		VerticalLayout verLayout = new VerticalLayout();

		if(key != null) selectCategory.setValue(question.getCategory().getName() + " - " + question.getCategory().getType());
		HorizontalLayout horLayout = new HorizontalLayout();
		horLayout.addComponent(selectCategory);

		if(isAdmin)
		{
			horLayout.addComponent(btnDelCategory);
			horLayout.addComponent(btnAddCategory);
		}
		
		horLayout.setComponentAlignment(btnDelCategory, Alignment.BOTTOM_LEFT);
		horLayout.setComponentAlignment(btnAddCategory, Alignment.BOTTOM_LEFT);
		verLayout.addComponent(horLayout);
		
		final TextField txtFQuestion = new TextField(Messages.getString("All.question"));
		if(key != null) txtFQuestion.setValue(question.getQuestion());
		verLayout.addComponent(txtFQuestion);
		
		Label lblChoices = new Label(Messages.getString("All.choices"));
		final VerticalLayout verTxtBoxes = new VerticalLayout();
		for(String choice : Misc.nullSafe(question.getChoices()))
		{
			TextField temp = new TextField();
			temp.setValue(choice);
			verTxtBoxes.addComponent(temp);
		}
		verLayout.addComponent(lblChoices);
		verLayout.addComponent(verTxtBoxes);
		
		Button btnAddStatement = new Button(Messages.getString("All.addStatement"));
		btnAddStatement.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = 7468455855459449274L;

			public void buttonClick(ClickEvent event) {
				verTxtBoxes.addComponent(new TextField());
			}
		});
		verLayout.addComponent(btnAddStatement);
		
		final TextField txtFAnswer = new TextField(Messages.getString("All.answer"));
		if(key != null) txtFAnswer.setValue(question.getAnswer());
		verLayout.addComponent(txtFAnswer);
		
		selectDegree.setValue(question.getDegree());
		verLayout.addComponent(selectDegree);
		
		if(questionWindowType == QuestionWindowType.EDIT)
		{
			chkBIsReported.setValue(question.getIsReported());
			verLayout.addComponent(chkBIsReported);
		}
		
		selectLanguage.setValue(question.getLanguage());
		verLayout.addComponent(selectLanguage);
		
		this.addComponent(verLayout);

		btnApply.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = 587682720306792997L;

			public void buttonClick(ClickEvent event) {
				if(question != null)
				{
					List<String> choicesList = new ArrayList<String>();
					for(int i=0; i<verTxtBoxes.getComponentCount(); ++i)
					{
						String tmpStatement = verTxtBoxes.getComponent(i).toString();
						if(!tmpStatement.isEmpty())
							choicesList.add(tmpStatement);
					}
	
					setGeneralFields(question);
					question.setQuestion(txtFQuestion.getValue().toString());
					question.setChoices(choicesList);
					question.setAnswer(txtFAnswer.getValue().toString());
				}
				addOrEditQuestion(question);
		    }
		});
	}
	
	private void displayMediaQuestionnaire(String key)
	{
		final QuestionMedia question;
		if(questionWindowType == QuestionWindowType.EDIT) 
			question = RequestsWebService.getQuestionMedia(key, authString);		
		else 
			question = new QuestionMedia();
		
		VerticalLayout verLayout = new VerticalLayout();

		if(key != null) selectCategory.setValue(question.getCategory().getName() + " - " + question.getCategory().getType());
		HorizontalLayout horLayout = new HorizontalLayout();
		horLayout.addComponent(selectCategory);

		if(isAdmin)
		{
			horLayout.addComponent(btnDelCategory);
			horLayout.addComponent(btnAddCategory);
		}
		
		horLayout.setComponentAlignment(btnDelCategory, Alignment.BOTTOM_LEFT);
		horLayout.setComponentAlignment(btnAddCategory, Alignment.BOTTOM_LEFT);
		verLayout.addComponent(horLayout);
		
		final TextField txtFQuestion = new TextField(Messages.getString("All.question"));
		if(key != null) txtFQuestion.setValue(question.getStatement());
		verLayout.addComponent(txtFQuestion);
		
		final TextField txtFAnswer = new TextField(Messages.getString("All.answer"));
		if(key != null) txtFAnswer.setValue(question.getAnswer());
		verLayout.addComponent(txtFAnswer);
		
		selectDegree.setValue(question.getDegree());
		verLayout.addComponent(selectDegree);
		
		if(questionWindowType == QuestionWindowType.EDIT)
		{
			chkBIsReported.setValue(question.getIsReported());
			verLayout.addComponent(chkBIsReported);
		}
		
		selectLanguage.setValue(question.getLanguage());
		verLayout.addComponent(selectLanguage);
		
		this.addComponent(verLayout);

		btnApply.setCaption(Messages.getString("All.continue"));
		btnApply.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = 587682720306792997L;

			public void buttonClick(ClickEvent event) {
				setGeneralFields(question);
				question.setStatement(txtFQuestion.getValue().toString());
				question.setAnswer(txtFAnswer.getValue().toString());
				if(!question.isValid())
				{
					QuestionWindow.this.showNotification(Messages.getString("All.invalidFields"));
					return;
				}

				QuestionWindow.this.removeAllComponents();
				String groupField;
				if(groupID.isEmpty())
					groupField = "";
				else
					groupField = "<input type=\"hidden\" name=\"group\" id=\"group\" value=\"" + groupID + "\" />";
				
				VerticalLayout verLayout = new VerticalLayout();
				Label mediaFileSize= new Label(Messages.getString("ManageQuestions.mediaMaxSize"));
				Label warning = new Label(Messages.getString("ManageQuestions.mediaWarning"));
				Label htmlForm = new Label("<!DOCTYPE html><html>    <head>        <title>REST with Forms</title>        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">    </head>    <body>        <br>        <form method=\"post\" action=\"https://openquizproject-dev.appspot.com/rest/questions/mediaHTML/\" enctype=\"multipart/form-data\" >			<input type=\"hidden\" name=\"Authorization\" id=\"Authorization\" value=\"Basic " + authString + "\">" + groupField + "            <input type=\"file\" name=\"file\" id=\"file\"><br>            <input type=\"hidden\" name=\"statement\" id=\"statement\" value=\"" + question.getStatement() + "\" ><br>           <input type=\"hidden\" name=\"answer\" id=\"answer\" value=\"" + question.getAnswer() + "\" ><br>            <input type=\"hidden\" name=\"category\" id=\"category\" value=\"" + question.getCategory().getKey() + "\" ><br>			<input type=\"hidden\" name=\"availableDate\" id=\"availableDate\" value=\"0\" ><br>			<input type=\"hidden\" name=\"degree\" id=\"degree\" value=\"" + question.getDegree().toString() + "\" ><br>			<input type=\"hidden\" name=\"language\" id=\"language\" value=\"" + question.getLanguage().toString() + "\" ><br>            <input type=\"submit\" value=\"Submit\">        </form>    </body></html>", Label.CONTENT_XHTML);

				verLayout.setSpacing(true);
				verLayout.addComponent(mediaFileSize);
				verLayout.addComponent(warning);
				QuestionWindow.this.addComponent(verLayout);
				QuestionWindow.this.addComponent(htmlForm);
				
				btnApply.setCaption(Messages.getString("All.apply"));
		    }
		});		
	}
	
	private boolean setGeneralFields(Question question)
	{
		if(selectCategory.getValue() == null || selectDegree.getValue() == null  || selectLanguage.getValue() == null)
			return false;
				
		question.setCategory(categories.get((selectCategory.getTabIndex())));		
		question.setDegree(Degree.valueOf(selectDegree.getValue().toString()));		
		question.setLanguage(Language.valueOf(selectLanguage.getValue().toString()));
		question.setIsReported(chkBIsReported.booleanValue());
		
		return true;
	}
	
	private void addOrEditQuestion(Question question)
	{
		if(question == null || !question.isValid())
		{
			QuestionWindow.this.showNotification(Messages.getString("All.invalidFields"));
			return;
		}
		
		boolean success = false;
		question.getCategory().setName("Language");
		if(questionWindowType == QuestionWindowType.ADD)
		{
			if(question.getGroupKeys() != null)
			{
				question.getGroupKeys().clear();
				question.getGroupKeys().add(groupID);
			}
			
			KeyResponse keyResponse = RequestsWebService.addQuestion(question, authString);
			
			if(keyResponse != null && keyResponse.getKey() != null && keyResponse.getKey().isEmpty() == false)
				success = true;
		}
		else
			success = RequestsWebService.editQuestion(question, authString);

		if(success)
		{
			QuestionWindow.this.close();
			if(parent != null)
				parent.updateTable();
		}
		else
			QuestionWindow.this.showNotification(Messages.getString("All.errorProcessingRequest"));
	}
}
