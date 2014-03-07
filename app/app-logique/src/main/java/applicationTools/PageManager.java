package applicationTools;

import java.awt.List;
import java.util.Observable;

public class PageManager extends Observable {
	
	public final static String HOMEPAGE = "Home page";
	public final static String CURRENTGAME = "Current game";
	public final static String CREATENEWGAME = "Create new game";
	public final static String CREATENEWQUESTIONNAIRE = "Create new questionnaire";
	public final static String REPORTQUESTION = "Report question";
	public final static String OPTIONS = "Options";
	public final static String LOGIN = "Login";
	
	private String pageDisplayed;
	private static List pageHistory = new List();

	private PageManager()
	{
		pageDisplayed = HOMEPAGE;
	}
	
	private static PageManager INSTANCE = null;
	
	public static PageManager getInstance()
	{	
		if (INSTANCE == null)
		{ 
			INSTANCE = new PageManager();
		}
			return INSTANCE;
	}
	
	public String getPageDisplayed() {
		return pageDisplayed;
	}
	
	public void setPageDisplayed(String pageDisplayed) {
		pageHistory.add(this.pageDisplayed);
		this.pageDisplayed = pageDisplayed;
		setChanged();
		notifyObservers(this.pageDisplayed);
	}
	
	/**
	 * Same as setPageDisplayed except that it does not add the page were on in the page history.
	 * @param pageDisplayed = Page to be displayed
	 */
	public void setPageDisplayedNoHistory(String pageDisplayed) {
		this.pageDisplayed = pageDisplayed;
		setChanged();
		notifyObservers(this.pageDisplayed);
	}
	
	/**
	 * Return the string corresponding to the last page visited in the page history
	 * @return
	 */
	public String getLastPageVisited() {
		String lastPage = "";
		if(pageHistory.getItemCount() >= 1) {
			lastPage = pageHistory.getItem(pageHistory.getItemCount()-1);
			pageHistory.remove(pageHistory.getItemCount()-1);
		}
		
		return lastPage;
	}

}
