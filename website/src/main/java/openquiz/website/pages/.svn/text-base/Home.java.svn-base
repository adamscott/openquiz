package openquiz.website.pages;

import openquiz.website.util.Messages;

import org.vaadin.navigator.Navigator;

import com.vaadin.Application;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Runo;


public class Home extends com.vaadin.ui.Panel implements Navigator.View{
	private static final long serialVersionUID = -8117220960945895900L;
	
	//Language strings
	private String PAGE_HOME = Messages.getString("All.home");
	private String LINK_FORUM = Messages.getString("Home.linkForum");
	private String LINK_WIKI = Messages.getString("Home.linkWiki");
	private String BUTTON_FORUM = Messages.getString("Home.forum");
	private String BUTTON_DESC_FORUM = Messages.getString("Home.forumDesc");
	private String BUTTON_WIKI = Messages.getString("Home.wiki");
	private String BUTTON_DESC_WIKI = Messages.getString("Home.wikiDesc");
	private String LABEL_WELCOME = Messages.getString("Home.labelWelcome");
	private String LABEL_FOOTER = Messages.getString("Home.labelFooter");
	
	public Home(){
		super.setSizeFull();
		super.setCaption(PAGE_HOME);
	}

	@Override
	public void init(Navigator navigator, Application application) {
		
		//Main layout that is set as content on the panel
		VerticalLayout MainLayout = new VerticalLayout();
		MainLayout.setSizeFull();
		
		//Title and welcome text
		VerticalLayout vLayout = new VerticalLayout();
		vLayout.setMargin(true);
		Label lbWelcome = new Label(LABEL_WELCOME,Label.CONTENT_XHTML);
		
		vLayout.addComponent(lbWelcome);
		
	    //Redirection buttons
	    HorizontalLayout HLayoutRedirect = new HorizontalLayout();
	    HLayoutRedirect.setMargin(true);
	    HLayoutRedirect.setSpacing(true);
	    HLayoutRedirect.setWidth("100%");
	    
	    LinkButton linkForum = new LinkButton(LINK_FORUM, BUTTON_FORUM);
		linkForum.setDescription(BUTTON_DESC_FORUM);
	    linkForum.setWidth("100%");
	    linkForum.addStyleName(Runo.BUTTON_DEFAULT);
	    linkForum.addStyleName(Runo.BUTTON_BIG);
	    
	    LinkButton linkWiki = new LinkButton(LINK_WIKI, BUTTON_WIKI);
		linkWiki.setDescription(BUTTON_DESC_WIKI);
		linkWiki.setWidth("100%");
		linkWiki.addStyleName(Runo.BUTTON_DEFAULT);
		linkWiki.addStyleName(Runo.BUTTON_BIG);
	    
	    //Footer
	    Label lbFooter = new Label(LABEL_FOOTER, Label.CONTENT_XHTML);
	    
	    HLayoutRedirect.addComponent(lbFooter);
	    HLayoutRedirect.addComponent(linkForum);
	    HLayoutRedirect.addComponent(linkWiki);
	    HLayoutRedirect.setComponentAlignment(linkForum, Alignment.BOTTOM_CENTER);
	    HLayoutRedirect.setComponentAlignment(linkWiki, Alignment.BOTTOM_CENTER);
	    HLayoutRedirect.setComponentAlignment(lbFooter, Alignment.TOP_LEFT);
	    HLayoutRedirect.setExpandRatio(lbFooter, 1);
	    HLayoutRedirect.setExpandRatio(linkForum, 1);
	    HLayoutRedirect.setExpandRatio(linkWiki, 1);
	    
	    
	    
	    HLayoutRedirect.addStyleName(Runo.LAYOUT_DARKER);
	    HLayoutRedirect.setSizeFull();
	    
	    // Design of the second Layout
	 	Label lbLineSeparator = new Label("<hr/>", Label.CONTENT_XHTML);
	 	
	 	MainLayout.addComponent(vLayout);
	 	MainLayout.setExpandRatio(vLayout, 3);
	 	
	    MainLayout.addComponent(lbLineSeparator);
	    MainLayout.setComponentAlignment(lbLineSeparator, Alignment.BOTTOM_CENTER);
	    MainLayout.setExpandRatio(lbLineSeparator, 0);
	    MainLayout.addComponent(HLayoutRedirect);
	    MainLayout.setComponentAlignment(HLayoutRedirect, Alignment.BOTTOM_LEFT);
	    MainLayout.setExpandRatio(HLayoutRedirect, 1);
	    
	    
	    this.setContent(MainLayout); 
	}
	
	public class LinkButton extends Button {
		private static final long serialVersionUID = 1L;
		private String url;
    	public LinkButton(String url, String caption) {
    		super(caption);
    		this.url = url;
    		setImmediate(true);
    		
    		addListener(new Button.ClickListener() {
				private static final long serialVersionUID = 1L;

				@Override
				public void buttonClick(ClickEvent event) {
					LinkButton.this.getApplication().getMainWindow().open(new ExternalResource(LinkButton.this.url));
				}
    			});
    		}
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