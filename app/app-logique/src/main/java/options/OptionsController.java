package options;

public class OptionsController {
	private IOptionsView view;
	
	public OptionsController() {

	}
	
	public void setView(IOptionsView view){
		this.view = view;
		
		view.showWindow();
	}

	//TODO To implement sometime
	public void actionSaveChanges(){
		
	}
}
