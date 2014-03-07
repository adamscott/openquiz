package openquiz.website.pages;

import java.util.List;

import openquiz.website.Website_UI;
import openquiz.website.util.Messages;
import openquiz.website.util.SessionConstants;
import openquiz.website.windows.TemplateWindow;

import org.vaadin.dialogs.ConfirmDialog;
import org.vaadin.navigator.Navigator;

import ca.openquiz.comms.RequestsWebService;
import ca.openquiz.comms.model.Template;
import ca.openquiz.comms.model.TemplateSection;

import com.vaadin.Application;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.Button.ClickEvent;

public class ManageTemplates extends Panel implements Navigator.View{
	private static final long serialVersionUID = 2255405552852100120L;
	
	String userKey;
	String authString;
	String sessionKey;

	Application application;
	
	public ManageTemplates() {
		super(Messages.getString("All.manageTemplates")); //$NON-NLS-1$
		super.setSizeFull();
		}

	@Override
	public void init(Navigator navigator, Application application) {
		this.application = application;
		if(application != null)
		{
			userKey = (String)((Website_UI)application).getSession().getAttribute(SessionConstants.SESSION_USER_KEY);
			authString = (String)((Website_UI)application).getSession().getAttribute(SessionConstants.SESSION_AUTHORIZATION_STRING);
			sessionKey = (String)((Website_UI)application).getSession().getAttribute(SessionConstants.SESSION_WS_SESSION_KEY);
		}
		
	}
	
	public static void addTableRow(final Table tableTeams, Template template){
		int questions = 0;
		for(TemplateSection section : template.getSectionList()){
			questions += section.getNbQuestions();
		}
		tableTeams.addItem(new Object[] { template.getName(),template.getSectionList().size(),questions}, template.getKey());
	}


	@Override
	public void navigateTo(String requestedDataId) {
		this.removeAllComponents();
		HorizontalLayout buttonLayout = new HorizontalLayout();
		final Table templateTable = new Table(Messages.getString("All.templates"));  //$NON-NLS-1$
		templateTable.addContainerProperty(Messages.getString("All.name"), String.class, null);  //$NON-NLS-1$
		templateTable.addContainerProperty(Messages.getString("All.sections"), Integer.class, null);  //$NON-NLS-1$
		templateTable.addContainerProperty(Messages.getString("ManageTemplates.questionsNumber"), Integer.class, null);  //$NON-NLS-1$
		templateTable.setSelectable(true);
		templateTable.setWidth("100%"); //$NON-NLS-1$
		this.addComponent(templateTable);
		
		//TODO Maybe use a cache?
		List<Template> templates = RequestsWebService.getTemplates();
		if(templates != null){
			for(Template template : templates){
				addTableRow(templateTable,template);
			}
		}
		
		Button editTemplate = new Button(Messages.getString("All.modify")); //$NON-NLS-1$
		editTemplate.addListener(new ClickListener() {
			private static final long serialVersionUID = -5287973494246130796L;

			@Override
			public void buttonClick(ClickEvent event) {
				String templateKey = (String) templateTable.getValue();
				if(templateKey != null){
					//TODO Maybe use a cache?
					Template template = RequestsWebService.getTemplate(templateKey);
					TemplateWindow window = new TemplateWindow(template, authString, templateTable);
					ManageTemplates.this.getWindow().addWindow(window);
				}else{
					ManageTemplates.this.getWindow().showNotification(Messages.getString("ManageTemplates.pleaseSelectATemplate")); //$NON-NLS-1$
				}
			}
		});
		buttonLayout.addComponent(editTemplate);
		
		Button newTemplate = new Button(Messages.getString("ManageTemplates.newTemplate")); //$NON-NLS-1$
		newTemplate.addListener(new ClickListener() {
			private static final long serialVersionUID = 144123442335435L;
			@Override
			public void buttonClick(ClickEvent event) {
				ManageTemplates.this.getWindow().addWindow(new TemplateWindow(authString, templateTable));
			}
		});
		buttonLayout.addComponent(newTemplate);
		
		Button deleteTemplate = new Button(Messages.getString("All.delete")); //$NON-NLS-1$
		deleteTemplate.addListener(new ClickListener() {
			private static final long serialVersionUID = 144123442335435L;
			@Override
			public void buttonClick(ClickEvent event) {
				ConfirmDialog.show(ManageTemplates.this.getWindow(), Messages.getString("All.confirm"), Messages.getString("ManageTemplates.deleteTemplateConfirmation"), //$NON-NLS-1$ //$NON-NLS-2$
				        Messages.getString("All.yes"), Messages.getString("All.no"), new ConfirmDialog.Listener() { //$NON-NLS-1$ //$NON-NLS-2$
							private static final long serialVersionUID = 8412992901025864613L;
							public void onClose(ConfirmDialog dialog) {
				                if (dialog.isConfirmed()) {
				                	String templateKey = (String)templateTable.getValue();
				    				if(templateKey!= null){
				    					
				    					boolean deleted = RequestsWebService.deleteTemplate(templateKey,authString);
				    					if(deleted){
				    						templateTable.removeItem(templateKey);
				    					}else{
				    						ManageTemplates.this.getWindow().showNotification(Messages.getString("ManageTemplates.errorDeletingModel")); //$NON-NLS-1$
				    					}
				    				}
				                } 
				            }
				        });
			}
		});
		buttonLayout.addComponent(deleteTemplate);
		
		this.addComponent(buttonLayout);
	}
	
	@Override
	public String getWarningForNavigatingFrom() {
		return null;
	}

}
