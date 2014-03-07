package applicationTools;

import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.miginfocom.swing.MigLayout;

public class MultilineLabel {
	public static ArrayList<JComponent> multiLabelText(String text, float maxWidth, Font font){			
		if(font == null){
			font = FontManager.getQuestionZoneSubItem();
		}
		
		maxWidth = (int)(0.85*maxWidth);
		
		ArrayList<JComponent> lblList = new ArrayList<JComponent>();
		
		if(text != null){
			if(text != ""){
				String[] textSplit = text.split(" |-");
			    int lastWord = 0;
			    
			    JLabel firstLbl = new JLabel();
			    firstLbl.setFont(font);
			    lblList.add(firstLbl);
			    firstLbl.setOpaque(false);
			    
			    while (lastWord < textSplit.length) {
			    	JLabel lastLabel = (JLabel)lblList.get(lblList.size()-1);
			    	String lastText = lastLabel.getText();
			    	lastLabel.setText(lastText + " " + textSplit[lastWord]);
			    	
			    	if(lastLabel.getPreferredSize().width > maxWidth){
			    		lastLabel.setText(lastText);
			    	    JLabel tmp = new JLabel(textSplit[lastWord]);
			    	    tmp.setFont(font);
			    	    lblList.add(tmp);
			    	    tmp.setOpaque(false);
			    	}
			    	
			        lastWord++;
			    }
			    
			    for(JComponent lbl : lblList){
			    	((JLabel)lbl).setText(((JLabel)lbl).getText().replace(";", ""));
			    }
			}
		}
	    return lblList;
	}
	
	public static JPanel getComponentListInPanel(ArrayList<JComponent> listComponents, int width, int height, boolean isScrollable){
		String pos = "";
		JPanel panel = new JPanel();
		MigLayout panelLayout = new MigLayout();
		panel.setLayout(panelLayout);
		panel.setOpaque(false);
		panelLayout.setLayoutConstraints("gapy 0px");
		
		JPanel internalPanel = new JPanel();
		BoxLayout vLayout = new BoxLayout(internalPanel, BoxLayout.Y_AXIS);
		internalPanel.setLayout(vLayout);
		internalPanel.setOpaque(isScrollable);  // Si le composant n'est pas opaque et scrollable, le refresh lors du scroll ne s'effectue pas comme il faut et affiche en translucide d'autres parties non désirées de l'écran
		
		for(JComponent c : listComponents){
			c.setOpaque(isScrollable);
			internalPanel.add(c, pos);
		}
		
		if(width > 0 && height > 0){
			Dimension panelDimension = new Dimension(width, height);
			panel.setMaximumSize(panelDimension);
			panel.setMinimumSize(panelDimension);
			panel.setPreferredSize(panelDimension);
			panel.setSize(panelDimension);
			
			Dimension internalPanelDimension = new Dimension((int)(0.9*width), internalPanel.getPreferredSize().height);
			internalPanel.setMaximumSize(internalPanelDimension);
			internalPanel.setMinimumSize(internalPanelDimension);
			internalPanel.setPreferredSize(internalPanelDimension);
			internalPanel.setSize(internalPanelDimension);
		}
		
		if(isScrollable){
			JScrollPane scroll = new JScrollPane(internalPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			if(width > 0 && height > 0){
				Dimension panelDimension = new Dimension((int)(0.95*width), (int)(0.75*height));
				scroll.setMaximumSize(panelDimension);
				scroll.setMinimumSize(panelDimension);
				scroll.setPreferredSize(panelDimension);
				scroll.setSize(panelDimension);
			}
			panel.add(scroll);
		}
		else{
			panel.add(internalPanel);
		}
		
		return panel;
	}
}
