package openquiz.website.pages.statistics;

import com.vaadin.data.Property;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

public class StatsPeriodSelector extends CustomComponent{
	private static final long serialVersionUID = 2036033757201678624L;
	ComboBox comboBoxNumberOfGames;
	
	public StatsPeriodSelector(){
		//Add header to the page
		HorizontalLayout vlayout = new HorizontalLayout();
		vlayout.addComponent(new Label("Afficher les statistiques sur les "));
		comboBoxNumberOfGames = new ComboBox();
		/*comboBoxNumberOfGames.addItem(1);
		comboBoxNumberOfGames.addItem(2);
		comboBoxNumberOfGames.addItem(3);
		comboBoxNumberOfGames.addItem(4);*/
		comboBoxNumberOfGames.addItem(25);
		comboBoxNumberOfGames.addItem(50);
		comboBoxNumberOfGames.addItem(100);
		comboBoxNumberOfGames.addItem(200);
		comboBoxNumberOfGames.setValue(comboBoxNumberOfGames.getItemIds().iterator().next());
		comboBoxNumberOfGames.setNullSelectionAllowed(false);
		comboBoxNumberOfGames.setImmediate(true);
		vlayout.addComponent(comboBoxNumberOfGames);
		vlayout.addComponent(new Label(" dernières parties. "));
		this.setCompositionRoot(vlayout);
	}
	
	public void addChangeListener(Property.ValueChangeListener listener){
		comboBoxNumberOfGames.addListener(listener);
	}
	
	public int getSelectedValue(){
		return (int)comboBoxNumberOfGames.getValue();
	}
}
