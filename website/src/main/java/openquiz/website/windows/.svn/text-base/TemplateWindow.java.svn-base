package openquiz.website.windows;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import openquiz.website.pages.ManageTemplates;
import openquiz.website.util.WebServiceCache;
import ca.openquiz.comms.RequestsWebService;
import ca.openquiz.comms.enums.Degree;
import ca.openquiz.comms.enums.QuestionTarget;
import ca.openquiz.comms.enums.QuestionType;
import ca.openquiz.comms.model.Category;
import ca.openquiz.comms.model.Template;
import ca.openquiz.comms.model.TemplateSection;
import ca.openquiz.comms.response.KeyResponse;

import com.vaadin.terminal.UserError;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout.MarginInfo;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;

public class TemplateWindow extends Window {
	private static final long serialVersionUID = -2427873810972760851L;
	private TextField name = new TextField("Nom");
	private VerticalLayout sectionContainer = new VerticalLayout();
	private List<TemplateSectionLine> sectionLines = new ArrayList<TemplateSectionLine>();
	private WebServiceCache cache = new WebServiceCache();
	private String auth;
	
	private Template templateEditing = null; 
	
	public TemplateWindow(String auth, final Table table){
		this(null,auth, table);
	}
	
	public TemplateWindow(Template template, String auth, final Table table) {
		super("Modification de modèle");
		this.setHeight("600px");
		this.setWidth("600px");
		this.center();
		initUI(template, auth, table);
	}
	
	public void initUI(Template template, final String auth, final Table table){
		this.templateEditing = template;
		this.addComponent(name);
		this.auth = auth;
		
		//Populate the fields if needed
		if(template != null){
			name.setValue(template.getName());
			for(TemplateSection section : template.getSectionList()){
				sectionLines.add(new TemplateSectionLine(section));
			}
		}else{
			sectionLines.add(new TemplateSectionLine());
		}
		
		this.addComponent(new Label("</br><h1>Liste des sections du modèle</h1>",Label.CONTENT_XHTML));
		for(TemplateSectionLine section : sectionLines){
			sectionContainer.addComponent(section);
		}
		this.addComponent(sectionContainer);

		Button addLine = new Button("Nouvelle section");
		this.addComponent(addLine);
		addLine.addListener(new ClickListener() {
			private static final long serialVersionUID = 6618905889733652508L;
			@Override
			public void buttonClick(ClickEvent event) {
				TemplateSectionLine line = new TemplateSectionLine();
				if(templateEditing != null){
					templateEditing.getSectionList().add(line.getTemplateSection());
				}
				sectionLines.add(line);
				sectionContainer.addComponent(line);
			}
		});
		
		Button save = new Button("Sauvegarder");
		this.addComponent(save);
		save.addListener(new ClickListener() {
			private static final long serialVersionUID = 6618955889733652508L;
			@Override
			public void buttonClick(ClickEvent event) {
				
				//Validate everything!
				removeAllErrors();
				boolean isValid = true;
				if(((String)name.getValue()).isEmpty()){
					isValid = false;
					name.setComponentError(new UserError("Entrez un nom"));
				}
				for(TemplateSectionLine line : sectionLines){
					if(!line.validate()){
						isValid = false;
					}
				}
				
				if(!isValid){
					TemplateWindow.this.getWindow().showNotification("Votre modèle est invalide, voir les champs en erreur");
				}else{
					if(templateEditing == null){
						//Create a new template
						Template template = new Template();
						template.setName((String)name.getValue());
						for(TemplateSectionLine line : sectionLines){
							TemplateSection section = new TemplateSection();
							if(line.getCategory() != null)
								section.setCategory(line.getCategory().getKey());
							section.setDescription(line.getDescriptionText());
							section.setDifficulty(line.getDifficuly());
							section.setNbQuestions(line.getNbQuestions());
							section.setPoints(line.getPoints());
							section.setQuestionTarget(line.getQuestionTarget());
							section.setQuestionType(line.getQuestionType());
							template.getSectionList().add(section);
						}
						KeyResponse response = RequestsWebService.addTemplate(template, auth );
						if(response.getKey() != null){
							Template template2 = RequestsWebService.getTemplate(response.getKey());
							ManageTemplates.addTableRow(table, template2);
							TemplateWindow.this.getParent().removeWindow(TemplateWindow.this);
						}else{
							TemplateWindow.this.getWindow().showNotification("Une erreur s'est produite lors de l'ajout du modèle");
						}
					}else{
						templateEditing.setName((String)name.getValue());
						//Edit a template
						for(TemplateSectionLine line : sectionLines){
							TemplateSection section = line.getTemplateSection();
							if(line.getCategory() != null)
								section.setCategory(line.getCategory().getKey());
							section.setDescription(line.getDescriptionText());
							section.setDifficulty(line.getDifficuly());
							section.setNbQuestions(line.getNbQuestions());
							section.setPoints(line.getPoints());
							section.setQuestionTarget(line.getQuestionTarget());
							section.setQuestionType(line.getQuestionType());
						}
						if(RequestsWebService.editTemplate(templateEditing, auth)){
							//Refresh the row?
							ManageTemplates.addTableRow(table, templateEditing);
							TemplateWindow.this.getParent().removeWindow(TemplateWindow.this);
						}else{
							TemplateWindow.this.getWindow().showNotification("Une erreur s'est produite lors de la modification du modèle");
						}
					}
				}
			}

		});
		
	}
	
	private void removeAllErrors() {
		name.setComponentError(null);
		for(TemplateSectionLine line : sectionLines){
			line.resetErrorMessages();
		}
	}
	
	public class TemplateSectionLine extends Panel{
		private static final long serialVersionUID = -3425700788595505043L;
		private ComboBox category = new ComboBox("Categorie");
		private TextField points = new TextField("Points");
		private TextField nbQuestions = new TextField("Nombre de questions");
		private ComboBox difficulty = new ComboBox("Difficulté");
		private ComboBox questionType = new ComboBox("Type de questions");
		private ComboBox questionTarget  = new ComboBox("Mode de pointage");
		private TextArea description = new TextArea("Description");
		
		private TemplateSection section = null;
		
		public TemplateSectionLine(){
			section = new TemplateSection();
			buildUI();
		}
		
		public TemplateSectionLine(TemplateSection section){
			this();
			this.section = section;
			populateUI(section);
		}
		
		private void populateUI(TemplateSection section) {
			category.setValue(cache.getCategory(section.getCategory()));
			category.setNullSelectionAllowed(false);
			points.setValue(Integer.toString(section.getPoints()));
			nbQuestions.setValue(Integer.toString(section.getNbQuestions()));
			difficulty.setValue(section.getDifficulty());
			difficulty.setNullSelectionAllowed(false);
			questionType.setValue(section.getQuestionType());
			questionType.setNullSelectionAllowed(false);
			questionTarget.setValue(section.getQuestionTarget());
			description.setValue(section.getDescription());
			description.setHeight("120px");
		}

		private void buildUI() {
			HorizontalLayout horizontalLayout = new HorizontalLayout();
			VerticalLayout column1 = new VerticalLayout();
			VerticalLayout column2 = new VerticalLayout();
			column2.setMargin(new MarginInfo(false, true, false, true));
			column1.setMargin(new MarginInfo(false, true, false, true));
			
			column1.addComponent(category);
			Collection<Category> categories = cache.getCategories(auth);
			category.addItem("Toutes les categories");
			for(Category cat : categories){
				category.addItem(cat);
			}
			
			column1.addComponent(points);
			column1.addComponent(nbQuestions);
			
			//TODO Translate all these fields...
			column1.addComponent(difficulty);
			difficulty.addItem("Toutes les difficultés");
			for(Degree d : Degree.values()){
				difficulty.addItem(d);
			}
			
			column2.addComponent(questionType);
			questionType.addItem("Toutes les questions");
			for(QuestionType qt : QuestionType.values()){
				questionType.addItem(qt);
			}
			
			column2.addComponent(questionTarget);
			questionTarget.addItem(QuestionTarget.Collectif);
			questionTarget.addItem(QuestionTarget.Individual);
			
			column2.addComponent(description);
			
			horizontalLayout.addComponent(column1);
			horizontalLayout.addComponent(column2);
			
			this.addComponent(horizontalLayout);
			Button remove = new Button("Enlever cette section");
			remove.addListener(new ClickListener() {
				private static final long serialVersionUID = 8286296774300889104L;
				@Override
				public void buttonClick(ClickEvent event) {
					TemplateWindow.this.templateEditing.getSectionList().remove(section);
					TemplateWindow.this.sectionContainer.removeComponent(TemplateSectionLine.this);
					TemplateWindow.this.sectionLines.remove(this);
				}
			});
			this.addComponent(remove);
		}
		
		public Category getCategory(){
			if(category.getValue() instanceof String)
				return null;
			return (Category)category.getValue();
		}

		public int getPoints(){
			return Integer.parseInt((String)points.getValue().toString());
		}
		
		public int getNbQuestions(){
			return Integer.parseInt((String)nbQuestions.getValue().toString());
		}
	
		public Degree getDifficuly(){
			if(difficulty.getValue() instanceof String)
				return null;
			return (Degree)difficulty.getValue();
		}
		
		public QuestionType getQuestionType(){
			if(questionType.getValue() instanceof String)
				return null;
			return (QuestionType)questionType.getValue();
		}
	
		public QuestionTarget getQuestionTarget(){
			return (QuestionTarget) questionTarget.getValue();
		}
		
		public String getDescriptionText(){
			return (String) description.getValue();
		}
		
		public TemplateSection getTemplateSection(){
			return section;
		}
		
		public boolean validate(){
			boolean isValid = true;
			
			try {
				int n = Integer.parseInt(points.getValue().toString());
				if(n < 0){
					points.setComponentError(new UserError("Veuillez entrer un nombre plus grand que 0"));
					isValid = false;
				}
			} catch (Exception e) {
				points.setComponentError(new UserError("Veuillez entrer un nombre"));
				isValid = false;
			}
			
			try {
				int n = Integer.parseInt(nbQuestions.getValue().toString());
				if(n < 1){
					nbQuestions.setComponentError(new UserError("Veuillez entrer un nombre plus grand que 1"));
					isValid = false;
				}
			} catch (Exception e) {
				nbQuestions.setComponentError(new UserError("Veuillez entrer un nombre"));
				isValid = false;
			}
			
			
			
			if(questionTarget.getValue() == null){
				questionTarget.setComponentError(new UserError("Choisissez un mode de pointage"));
				isValid = false;
			}
			
			return isValid;
		}
		
		public void resetErrorMessages(){
			category.setComponentError(null);
			points.setComponentError(null);
			nbQuestions.setComponentError(null);
			difficulty.setComponentError(null);
			questionType.setComponentError(null);
			questionTarget.setComponentError(null);
			description.setComponentError(null);
		}
	}
}
