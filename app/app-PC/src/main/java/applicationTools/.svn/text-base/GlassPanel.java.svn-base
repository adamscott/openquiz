package applicationTools;

import java.awt.Graphics;

import javax.swing.JPanel;

public class GlassPanel extends JPanel {
	private static final long serialVersionUID = 6281740045302726097L;
	private JPanel panelContent;
	
	protected void paintComponent(Graphics g) {
		if (panelContent != null) {
			g.setColor(ColorManager.getMouseOverColor());
			g.fillRect(panelContent.getX(), panelContent.getY(), panelContent.getWidth(), panelContent.getHeight());
		}
	}
	
	public GlassPanel(JPanel _panelContent){
		panelContent = _panelContent;
		setOpaque(false);
	}
}