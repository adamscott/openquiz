package openquiz.website.windows;

import java.util.List;

import openquiz.website.util.Messages;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Window;

public class GenericWindow extends Window implements Window.CloseListener {
	private static final long serialVersionUID = 3656267654781836884L;
	
	public enum WindowType {
	    APPLY, CONFIRM
	}
	
	public GenericWindow(String title, List<Component> components, ClickListener applyBtnListener, WindowType windowType) {
		super(title);

		this.setModal(true);
		this.setWidth("425");
		this.setResizable(false);
		this.setDraggable(true);
		this.setTheme("openquiz");
		
		for (Component component : components) {
			this.addComponent(component);
		}
		if(windowType == WindowType.APPLY)
		{
			Button btnApply = new Button(Messages.getString("All.apply"));
			btnApply.addListener(applyBtnListener);
			this.addComponent(btnApply);
		}
		else if (windowType == WindowType.CONFIRM)
		{
			HorizontalLayout horLayout = new HorizontalLayout();
			Button btnConfirm = new Button(Messages.getString("All.confirm"));
			btnConfirm.addListener(applyBtnListener);
			
			Button btnCancel = new Button(Messages.getString("All.cancel"));
			btnCancel.addListener(new Button.ClickListener() {
				private static final long serialVersionUID = -2512741609898510172L;

				public void buttonClick(ClickEvent event) {
					GenericWindow.this.close();
			    }
			});
			
			horLayout.addComponent(btnCancel);
			horLayout.addComponent(btnConfirm);
			this.addComponent(horLayout);
		}
	}

	@Override
	public void windowClose(CloseEvent e) {
		this.close();
	}
}
