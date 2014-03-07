package openquiz.website.pages;


import com.vaadin.ui.Panel;

public class PageBase extends com.vaadin.ui.Panel{
	private static final long serialVersionUID = 2330959264777067605L;
	private Panel panelBase;

	public PageBase(String title){
		panelBase = new Panel(title);
		panelBase.setSizeFull();
		this.setSizeFull();
		
	}
}