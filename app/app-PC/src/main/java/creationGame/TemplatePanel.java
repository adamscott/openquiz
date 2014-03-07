package creationGame;

import java.awt.Color;

import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import net.miginfocom.swing.MigLayout;
import applicationTools.ButtonManager;
import applicationTools.CustomButton;
import applicationTools.TableHeaderRenderer;
import javax.swing.JButton;

public class TemplatePanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private CustomButton btnCreateTemplate;
	private CustomButton btnGenerateQuestionSet;
	private DefaultTableModel dm;
	private JScrollPane scrollPaneTemplateList;
	private JTable tableTemplateList;
	private JScrollPane scrollPaneTemplateCreationZones;
	private Box verticalBoxTemplateCreationZones;
	private JTextField txtFieldTemplateName;
	private JTextField txtFieldQuestionSetName;

	public TemplatePanel(){
		
		//Disable cell editing
		dm = new DefaultTableModel() {

			private static final long serialVersionUID = -4565425264857772188L;

			@Override
			   public boolean isCellEditable(int row, int column) {
			       return false;
			   }
			};
	    dm.setDataVector(new Object[][] { { "Nom du modèle", "Nombre de sections" } }, 
	    				 new Object[] { "Nom du modèle", "Nombre de sections" });
	    
	    scrollPaneTemplateList = new JScrollPane();
	    tableTemplateList = new JTable(dm);
	    scrollPaneTemplateCreationZones = new JScrollPane();
	    verticalBoxTemplateCreationZones = Box.createVerticalBox();
	    btnCreateTemplate = new CustomButton("Choisir template", null, ButtonManager.getTextButtonDimension());
	    btnGenerateQuestionSet = new CustomButton("Générer template", null, ButtonManager.getTextButtonDimension());
	    txtFieldTemplateName = new JTextField();
	    initGui();
	}
	
	private void initGui(){
		btnCreateTemplate.setText("Créer un modèle");
		
		tableTemplateList.setTableHeader(null);
		tableTemplateList.setRowHeight(30);
		tableTemplateList.setOpaque(false);
		tableTemplateList.setSelectionBackground(new Color(100,100,100,100));
		tableTemplateList.setGridColor(new Color(255,255,255,100));
		tableTemplateList.setShowGrid(true);
		tableTemplateList.setShowVerticalLines(false);
		tableTemplateList.setBackground(new Color(255,255,255,100));
		tableTemplateList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		tableTemplateList.getColumnModel().getColumn(0).setCellRenderer(new TableHeaderRenderer());
		tableTemplateList.getColumnModel().getColumn(1).setCellRenderer(new TableHeaderRenderer());
		
		scrollPaneTemplateCreationZones.setOpaque(false);
		scrollPaneTemplateCreationZones.getViewport().setOpaque(false);
		scrollPaneTemplateCreationZones.setViewportBorder(null);
		scrollPaneTemplateCreationZones.setViewportView(verticalBoxTemplateCreationZones);
		
		scrollPaneTemplateList.setOpaque(false);
		scrollPaneTemplateList.getViewport().setOpaque(false);
		scrollPaneTemplateList.setViewportBorder(null);
		scrollPaneTemplateList.setViewportView(tableTemplateList);
		
		txtFieldTemplateName.setText("Nom du modèle");
		txtFieldTemplateName.setColumns(30);
		
		this.setOpaque(false);
		
		this.setLayout(new MigLayout("", "[400px:n:400px,grow][][grow]", "[grow][][grow]"));
		add(scrollPaneTemplateCreationZones, "cell 1 0 2 1,grow");
		
		txtFieldQuestionSetName = new JTextField();
		add(txtFieldQuestionSetName, "flowx,cell 0 1,growx,aligny center");
		txtFieldQuestionSetName.setColumns(10);
		add(txtFieldTemplateName, "flowx,cell 2 1,aligny center");
		add(btnCreateTemplate, "cell 2 1,alignx center");
		add(scrollPaneTemplateList, "cell 0 0,grow");
		add(btnGenerateQuestionSet, "cell 0 1,alignx center");
	}
	
	public DefaultTableModel getTemplateTableModel(){
		return this.dm;
	}
	
	public JTable getTemplateTable(){
		return this.tableTemplateList;
	}
	
	public CustomButton getBtnSelectTemplate(){
		return this.btnCreateTemplate;
	}
	
	public CustomButton getBtnGenerateQuestionSet(){
		return this.btnGenerateQuestionSet;
	}
	
	public String getGeneratedQuestionSetName(){
		return this.txtFieldQuestionSetName.getText();
	}
	
	public Box getVerticalBoxTemplateCreationZones() {
		return verticalBoxTemplateCreationZones;
	}
	
	public int getTableSelectedRow(){
		if (this.tableTemplateList.getSelectedRow() != -1){
			return this.tableTemplateList.getSelectedRow()-1;
		}
		else {
			return -1;
		}
	}
	
	public JTextField getTxtFieldTemplateName() {
		return txtFieldTemplateName;
	}
	
	public void clearTemplateList(){
		while (dm.getRowCount() > 1){
			dm.removeRow(1);
		}
		tableTemplateList.setModel(dm);
	}
	
	public void clearTemplateCreationZone(){
		verticalBoxTemplateCreationZones.removeAll();
	}
	
	public boolean validateFields(){
		if (txtFieldQuestionSetName.getText().equals("")){
			txtFieldQuestionSetName.setBackground(Color.red);
			return false;
		}
		else{
			txtFieldQuestionSetName.setBackground(Color.white);
			return true;
		}
	}
	
	public void resetTxtFieldQuestionSetNameBackground(){
		this.txtFieldQuestionSetName.setBackground(Color.white);
	}
}
