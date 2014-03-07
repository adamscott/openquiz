package openquiz.website.pages;


import org.vaadin.navigator.Navigator;

import openquiz.website.util.Messages;

import com.vaadin.Application;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class AboutUs extends com.vaadin.ui.Panel implements Navigator.View {
	private static final long serialVersionUID = 3101684716185493478L;
	
	//Language strings
	private String PAGE_ABOUTUS = Messages.getString("All.aboutUs");
	private String LABEL_STORY = Messages.getString("AboutUs.labelStory");
	private String LABEL_PC_APPLICATION = Messages.getString("AboutUs.labelPcApplication");
	private String LABEL_GAME_CONSOLE = Messages.getString("AboutUs.labelGameConsole");
	private String LABEL_MOBILE_APPLICATION = Messages.getString("AboutUs.labelMobileApplication");
	
	public AboutUs() {
		super.setSizeFull();
		super.setCaption(PAGE_ABOUTUS);
	}

	@Override
	public void init(Navigator navigator, Application application) {
		
		//Main layout that is set as content on the panel
		VerticalLayout MainLayout = new VerticalLayout();
		MainLayout.setSizeFull();
		
		VerticalLayout VLayout = new VerticalLayout();
		VLayout.setMargin(true);
		VLayout.setSpacing(true);
		VLayout.setSizeFull();
	    
	    Label lbOurStory = new Label(LABEL_STORY, Label.CONTENT_XHTML);
	    
	    // Presentation text. 
 		HorizontalLayout HLayout = new HorizontalLayout();
 		HLayout.setSpacing(true);

 	    Label lbCol1 = new Label(LABEL_PC_APPLICATION, Label.CONTENT_XHTML);
 	    Label lbCol2 = new Label(LABEL_GAME_CONSOLE, Label.CONTENT_XHTML);
 	    Label lbCol3 = new Label(LABEL_MOBILE_APPLICATION, Label.CONTENT_XHTML);
 	    
 	    HLayout.addComponent(lbCol2);
 	    HLayout.addComponent(lbCol1);
 	    HLayout.addComponent(lbCol3);
 	    HLayout.setSizeFull();
 	    HLayout.setComponentAlignment(lbCol1, Alignment.TOP_LEFT);
 	    HLayout.setComponentAlignment(lbCol2, Alignment.TOP_LEFT);
 	    HLayout.setComponentAlignment(lbCol3, Alignment.TOP_LEFT);
	 	      
	 	 //Images
	    HorizontalLayout HLayoutImage = new HorizontalLayout();
	    
	    ThemeResource appPc = new ThemeResource("images/app-pc.png"); //$NON-NLS-1$
		Embedded image = new Embedded(null, appPc);
		image.setSizeFull();
		
		Label lbSpacer = new Label("");
		Label lbSpacer1 = new Label("");
		HLayoutImage.addComponent(lbSpacer);
		HLayoutImage.addComponent(image);
		HLayoutImage.addComponent(lbSpacer1);
		HLayoutImage.setExpandRatio(lbSpacer, 1);
		HLayoutImage.setExpandRatio(image, 1);
		HLayoutImage.setExpandRatio(lbSpacer1, 1);
		HLayoutImage.setSizeFull();
			
	    //Populating the VLayout
	    VLayout.addComponent(lbOurStory);	
	    VLayout.addComponent(HLayout);
	    VLayout.setExpandRatio(HLayout, 1);
	    VLayout.addComponent(HLayoutImage);
		VLayout.setExpandRatio(HLayoutImage, 1);

		MainLayout.addComponent(VLayout);
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