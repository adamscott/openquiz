package openquiz.website.pages;

import openquiz.website.util.Messages;

import org.vaadin.navigator.Navigator;

import com.vaadin.Application;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class ContactUs extends com.vaadin.ui.Panel implements Navigator.View {
	private static final long serialVersionUID = 6290272272696956879L;
	
	//Language strings
	private String PAGE_CONTACTUS = Messages.getString("All.contactUs");
	private String LABEL_REACHUS = Messages.getString("Contactus.lbReachUs");
	private String LABEL_GENERAL = Messages.getString("Contactus.lbGeneral");
	
	public ContactUs() 
	{
		super.setSizeFull();
		super.setCaption(PAGE_CONTACTUS);
	}

	@Override
	public void init(Navigator navigator, Application application) {
		
		//Main layout that is set as content on the panel
		VerticalLayout MainLayout = new VerticalLayout();
		MainLayout.setSizeFull();
		
		//Building the layout
		
		VerticalLayout vLayout = new VerticalLayout();
		vLayout.setMargin(true);
		vLayout.setSpacing(true);
		
		
		//Left side of the Contact Us page
		Label lbReachus = new Label(LABEL_REACHUS, Label.CONTENT_XHTML);
		vLayout.addComponent(lbReachus);
		
		Label lbAddress = new Label("<b>projet.openquiz@gmail.com</b>", Label.CONTENT_XHTML);
		vLayout.addComponent(lbAddress);
		
		/*
		FormLayout fLayout = new FormLayout();

		TextField txtName = new TextField("Name");
		txtName.setRequired(true);
		txtName.setRequiredError("The Field may not be empty.");
		txtName.setWidth("300");
		fLayout.addComponent(txtName);
		
		TextField txtEmail = new TextField("Email");
		txtEmail.setRequired(true);
		txtEmail.setRequiredError("The Field may not be empty.");
		txtEmail.setWidth("300");
		fLayout.addComponent(txtEmail);
		
		TextField txtMessage = new TextField("Message");
		txtMessage.setRequired(true);
		txtMessage.setRequiredError("The Field may not be empty.");
		txtMessage.setWidth("300");
		txtMessage.setHeight("200");
		fLayout.addComponent(txtMessage);
		
		vLayout.addComponent(fLayout);
*/
		
		Label lbGeneral = new Label(LABEL_GENERAL,Label.CONTENT_XHTML);
		vLayout.addComponent(lbGeneral);
		
		MainLayout.addComponent(vLayout);

		//Link telephone = new Link(Messages.getString("MainWebsiteWindow.telephone_link"), null); //$NON-NLS-1$

		this.setContent(MainLayout);

	}

	@Override
	public void navigateTo(String requestedDataId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getWarningForNavigatingFrom() {
		// TODO Auto-generated method stub
		return null;
	}
}