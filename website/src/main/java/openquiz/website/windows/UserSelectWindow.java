package openquiz.website.windows;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;

public class UserSelectWindow extends Window {
	private static final long serialVersionUID = -7355623530627932637L;

	private TextField emailField = new TextField("Courriel : ");
	private Button button = new Button("Ajouter");
	
	public UserSelectWindow( String caption){
		super(caption);
		this.setHeight("200px");
		this.setWidth("450px");
		this.center();
		initUI();
	}
	
	public void initUI(){
		emailField.setWidth("300px");
		this.addComponent(emailField);
		this.addComponent(button);
	}
	
	public void addListener(ClickListener listener){
		button.addListener(listener);
	}

	public String getEmail() {
		return (String)emailField.getValue();
	}
	
}
