package openquiz.website.pages;

import openquiz.website.Website_UI;
import openquiz.website.util.Messages;
import openquiz.website.util.SessionConstants;

import org.vaadin.navigator.Navigator;

import ca.openquiz.comms.RequestsWebService;

import com.vaadin.Application;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.Runo;

public class Download extends com.vaadin.ui.Panel implements Navigator.View{
	private static final long serialVersionUID = 4148094647279246204L;
	
	//Language strings
	private String PAGE_DOWNLOAD = Messages.getString("All.download");
	private String LABLE_TITLE = Messages.getString("Download.lbTitle");
	private String LABLE_INTRO = Messages.getString("Download.lbIntro");
	private String BUTTON_DOWNLOAD = Messages.getString("All.buttonDownload");
	private String LABEL_CONSOLE = Messages.getString("Download.lbConsole");
	private String LABEL_SCRIPT = Messages.getString("Download.lbScript");
	
	
	public Download(){
		super.setSizeFull();
		super.setCaption(PAGE_DOWNLOAD);
	}

	@Override
	public void init(Navigator navigator, Application application) {
		VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.setSizeFull();
		
		VerticalLayout vLayout = new VerticalLayout();
		vLayout.setSizeFull();
		
        Label lbTitre = new Label(LABLE_TITLE, Label.CONTENT_XHTML);
        vLayout.addComponent(lbTitre);
        vLayout.setComponentAlignment(lbTitre, Alignment.MIDDLE_CENTER);

        Label lbIntro = new Label(LABLE_INTRO, Label.CONTENT_XHTML);
        vLayout.addComponent(lbIntro);
        vLayout.setComponentAlignment(lbIntro, Alignment.MIDDLE_CENTER);
        
        HorizontalLayout HLayout = new HorizontalLayout();
        HLayout.setSpacing(true);
        HLayout.setWidth("100%");
        HLayout.setMargin(true);
		
		Button btnDownloadPc = new Button(BUTTON_DOWNLOAD);
		btnDownloadPc.setWidth("200px");
		btnDownloadPc.addListener(new ClickListener() {
			private static final long serialVersionUID = 6073857552878977395L;

			public void buttonClick(ClickEvent event) {
				getApplication().getMainWindow().open(new ExternalResource("http://commondatastorage.googleapis.com/general_openquizproject-prod%2FopenQuizLogoWithName.png"));
			}
		});
		
		ThemeResource pcLogo = new ThemeResource("images/Windows_logo.png"); //$NON-NLS-1$
		Embedded imagePc = new Embedded(null, pcLogo);
		imagePc.setWidth("20"); //$NON-NLS-1$
		imagePc.setHeight("20"); //$NON-NLS-1$
		
		Button btnDownloadMobile = new Button(BUTTON_DOWNLOAD);
		btnDownloadMobile.setWidth("200px");
		btnDownloadMobile.addListener(new ClickListener() {
			private static final long serialVersionUID = 6073857552878977395L;

			public void buttonClick(ClickEvent event) {
				getApplication().getMainWindow().open(new ExternalResource("http://commondatastorage.googleapis.com/general_openquizproject-prod%2FopenQuizLogoWithName.png"));
			}
		});
		
		ThemeResource mobileLogo = new ThemeResource("images/androidlogo.png"); //$NON-NLS-1$
		Embedded imageMobile = new Embedded(null, mobileLogo);
		imageMobile.setWidth("20"); //$NON-NLS-1$
		imageMobile.setHeight("20"); //$NON-NLS-1$
		
		HLayout.addComponent(btnDownloadPc);
		HLayout.addComponent(imagePc);
		HLayout.addComponent(btnDownloadMobile);
		HLayout.addComponent(imageMobile);
		
		HLayout.setExpandRatio(btnDownloadPc,2);
		HLayout.setComponentAlignment(btnDownloadPc, Alignment.MIDDLE_RIGHT);
		HLayout.setExpandRatio(imagePc,1);
		HLayout.setComponentAlignment(imagePc, Alignment.MIDDLE_LEFT);
		HLayout.setExpandRatio(btnDownloadMobile,1);
		HLayout.setComponentAlignment(btnDownloadMobile, Alignment.MIDDLE_RIGHT);
		HLayout.setExpandRatio(imageMobile,2);
		HLayout.setComponentAlignment(imageMobile, Alignment.MIDDLE_LEFT);
		vLayout.addComponent(HLayout);
        
        
        //This layout is for all the other downloads 
		VerticalLayout vLayout3 = new VerticalLayout();
		vLayout3.addStyleName(Runo.LAYOUT_DARKER);
		vLayout3.setSizeFull();		
		
		// Design of the second Layout
		Label lbLineSeparator = new Label("<hr/>", Label.CONTENT_XHTML);
        
        
        VerticalLayout vLayout2 = new VerticalLayout();
        vLayout2.setSizeFull();
        vLayout2.setMargin(true);
        vLayout2.setSpacing(true);
		
        Label lbConsole = new Label(LABEL_CONSOLE, Label.CONTENT_XHTML);
        vLayout2.addComponent(lbConsole);
        
        Label lbScript = new Label(LABEL_SCRIPT, Label.CONTENT_XHTML);
        vLayout2.addComponent(lbScript);
        
        
        vLayout3.addComponent(lbLineSeparator);
        vLayout3.addComponent(vLayout2);
        vLayout3.setComponentAlignment(lbLineSeparator, Alignment.TOP_CENTER);
        vLayout3.setExpandRatio(lbLineSeparator, 0);
        vLayout3.setExpandRatio(vLayout2, 1);
        
        mainLayout.addComponent(vLayout);
        mainLayout.addComponent(vLayout3);
        
        

		this.setContent(mainLayout);
	
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