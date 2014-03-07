package creationGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.LineBorder;

import net.miginfocom.swing.MigLayout;
import applicationTools.ButtonManager;
import applicationTools.CustomButton;
import applicationTools.IconManager;
import ca.openquiz.comms.enums.CategoryType;
import ca.openquiz.comms.enums.Degree;
import ca.openquiz.comms.enums.QuestionTarget;
import ca.openquiz.comms.enums.QuestionType;

public class TemplateCreationZonePanel extends JPanel {
	private static final long serialVersionUID = 5450832190315697930L;
	private JLabel lblQuestionSeriesName;
	private JLabel lblQuestionType;
	private JComboBox comboBoxQuestionType;
	private JLabel lblQuestionCategory;
	private JComboBox comboBoxQuestionCategory;
	private JLabel lblQuantity;
	private JSpinner spinnerQuestionQuantity;
	private JLabel lblQuestionValue;
	private JSpinner spinnerQuestionValue;
	private CustomButton btnAdd;
	private CustomButton btnRemove;
	private JLabel lblDifficulty;
	private JComboBox comboBoxDifficulty;
	
	private Dimension comboBoxDim = new Dimension(250,25);
	private Dimension spinnerDim = new Dimension(65,25);
	private JLabel lblNbQuestionTotal;
	private JLabel lblQuestionTarget;
	private JComboBox comboBoxQuestionTarget;
	private JLabel lblDescription;
	private JTextField textFieldDescription;
	private JLabel lblSubCategory;
	private JComboBox comboBoxSubCategory;

	/**
	 * Create the panel.
	 */
	public TemplateCreationZonePanel() {
		setOpaque(false);
		
		lblQuestionSeriesName = new JLabel("Série 1");
		lblQuestionType = new JLabel("Type");
		comboBoxQuestionType = new JComboBox(QuestionType.values());
		lblQuestionCategory = new JLabel("Catégorie");
		comboBoxQuestionCategory = new JComboBox(CategoryType.valuesName());
		comboBoxQuestionCategory.addItem("");
		comboBoxSubCategory = new JComboBox();
		lblQuantity = new JLabel("Quantité");
		spinnerQuestionQuantity = new JSpinner();
		lblQuestionValue = new JLabel("Pointage");
		spinnerQuestionValue = new JSpinner();
		btnAdd = new CustomButton("", IconManager.getAddIcon(), ButtonManager.getIconButtonDimension());
		btnAdd.setToolTipText("Ajouter une série (Atl+A)");
		btnAdd.setMnemonic(KeyEvent.VK_A);
		
		btnRemove = new CustomButton("", IconManager.getCloseXIcon(), ButtonManager.getIconButtonDimension());
		btnRemove.setToolTipText("Supprimer la série");
		lblDifficulty = new JLabel("Difficulté");
		comboBoxDifficulty = new JComboBox(Degree.valuesName());
		comboBoxQuestionTarget = new JComboBox(QuestionTarget.values());
		lblNbQuestionTotal = new JLabel("/");
		textFieldDescription = new JTextField();
		lblDescription = new JLabel("Description");
		lblSubCategory = new JLabel("Sous-Catégorie");
		lblQuestionTarget = new JLabel("Mode de pointage");
		initGUI();
	}
	
	private void initGUI() {
		setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		setLayout(new MigLayout("", "[grow][grow][grow][grow]", "[][][][][][][][]"));
		
		add(lblQuestionSeriesName, "cell 0 0 1 9,alignx center");
		
		add(lblQuestionType, "cell 1 0,alignx left");
		
		comboBoxQuestionType.setMaximumSize(comboBoxDim);
		comboBoxQuestionType.setMinimumSize(comboBoxDim);
		add(comboBoxQuestionType, "cell 2 0,alignx left");
		
		add(lblQuestionCategory, "cell 1 1,alignx left");
		
		comboBoxQuestionCategory.setMaximumSize(comboBoxDim);
		comboBoxQuestionCategory.setMinimumSize(comboBoxDim);
		add(comboBoxQuestionCategory, "cell 2 1,alignx left");
		
		add(lblSubCategory, "cell 1 2,alignx left");
		
		comboBoxSubCategory.setMaximumSize(comboBoxDim);
		comboBoxSubCategory.setMinimumSize(comboBoxDim);
		add(comboBoxSubCategory, "cell 2 2,alignx left");
		
		add(lblDifficulty, "cell 1 3,alignx left");
		
		comboBoxDifficulty.setMaximumSize(comboBoxDim);
		comboBoxDifficulty.setMinimumSize(comboBoxDim);

		add(comboBoxDifficulty, "cell 2 3,alignx left");
		
		add(lblQuestionTarget, "cell 1 4,alignx left");
		
		comboBoxQuestionTarget.setMaximumSize(comboBoxDim);
		comboBoxQuestionTarget.setMinimumSize(comboBoxDim);
		add(comboBoxQuestionTarget, "cell 2 4,alignx left");
		
		add(lblDescription, "cell 1 5,alignx left");
		
		add(textFieldDescription, "cell 2 5,alignx left");
		textFieldDescription.setMaximumSize(comboBoxDim);
		textFieldDescription.setMinimumSize(comboBoxDim);
		
		add(lblQuantity, "cell 1 6,alignx left");
		
		add(lblQuestionValue, "cell 1 7,alignx left");
		
		spinnerQuestionQuantity.setModel(new SpinnerNumberModel(new Integer(10), new Integer(0), null, new Integer(1)));
		spinnerQuestionQuantity.setMaximumSize(spinnerDim);
		spinnerQuestionQuantity.setMinimumSize(spinnerDim);
		add(spinnerQuestionQuantity, "flowx,cell 2 6");
		
		spinnerQuestionValue.setModel(new SpinnerNumberModel(new Integer(10), new Integer(0), null, new Integer(1)));
		spinnerQuestionValue.setMaximumSize(spinnerDim);
		spinnerQuestionValue.setMinimumSize(spinnerDim);
		add(spinnerQuestionValue, "cell 2 7");
		
		add(btnAdd, "cell 3 6 1 2,alignx left");

		add(btnRemove, "cell 3 0 1 2,alignx left");
		
		add(lblNbQuestionTotal, "cell 2 6");
		
		comboBoxDifficulty.addItem("");
	}

	public CustomButton getBtnAdd() {
		return btnAdd;
	}
	
	public CustomButton getBtnRemove() {
		return btnRemove;
	}
	
	public JComboBox getComboBoxQuestionType(){
		return this.comboBoxQuestionType;
	}
	
	public JComboBox getComboBoxDifficulty(){
		return this.comboBoxDifficulty;
	}
	
	public JComboBox getComboBoxQuestionCategory(){
		return this.comboBoxQuestionCategory;
	}
	
	public JComboBox getComboBoxQuestionSubCategory(){
		return this.comboBoxSubCategory;
	}
	
	public void setQuestionSeriesName(String seriesName) {
		this.lblQuestionSeriesName.setText(seriesName);
	}
	
	public CategoryType getQuestionCategory(){
		if (comboBoxQuestionCategory.getSelectedItem() != null){
			if (comboBoxQuestionCategory.getSelectedItem().equals("")){
				return null;
			}
			return CategoryType.findByStringName((String)comboBoxQuestionCategory.getSelectedItem());
		}
		return null;
	}
	
	public void setQuestionCategory(CategoryType categoryType){
		if (categoryType == null){
			comboBoxQuestionCategory.setSelectedItem("");
		}
		else{
			comboBoxQuestionCategory.setSelectedItem(CategoryType.toString(categoryType));
		}
	}
	
	public void addQuestionCategory(CategoryType categoryType){
		if (categoryType != null && !isComboBoxContainsCategory(categoryType)){
			comboBoxQuestionCategory.addItem(categoryType);
		}
	}
	
	private boolean isComboBoxContainsCategory(CategoryType categoryType) {
		for (int i = 0; i < comboBoxQuestionCategory.getItemCount(); i++){
			if (comboBoxQuestionCategory.getItemAt(i).equals(categoryType)){
				return true;
			}
		}
		return false;
	}

	public Degree getQuestionDifficulty(){
		if (comboBoxDifficulty.getSelectedItem() != null){
			if (comboBoxDifficulty.getSelectedItem().equals("")){
				return null;
			}
			return Degree.findByStringName((String)comboBoxDifficulty.getSelectedItem());
		}
		return null;
	}
	
	public void setQuestionDifficulty(Degree degree){
		comboBoxDifficulty.setSelectedItem(degree);
	}
	
	public QuestionType getQuestionType(){
		return (QuestionType)comboBoxQuestionType.getSelectedItem();
	}
	
	public void setQuestionType(QuestionType questionType){
		comboBoxQuestionType.setSelectedItem(questionType);
	}
	
	public int getQuestionValue(){
		return Integer.parseInt(spinnerQuestionValue.getValue().toString());
	}
	
	public void setQuestinValue(int value){
		spinnerQuestionValue.setValue(value);
	}
	
	public int getQuestionQuantity(){
		return Integer.parseInt(spinnerQuestionQuantity.getValue().toString());
	}
	
	public void setQuestionQuantity(int quantity){
		spinnerQuestionQuantity.setValue(quantity);
	}
	
	public void setQuestionNbMax(int nbQuestions){
		lblNbQuestionTotal.setText("/"+nbQuestions);
		if (Integer.parseInt(spinnerQuestionQuantity.getValue().toString()) > nbQuestions){
			spinnerQuestionQuantity.setBackground(Color.red);
		}
		else {
			spinnerQuestionQuantity.setBackground(Color.white);
		}
	}
	
	public Integer getQuestionNbMax(){
		return Integer.parseInt(lblNbQuestionTotal.getText().replace("/", ""));
	}

	public void setQuestionTarget(QuestionTarget questionTarget) {
		comboBoxQuestionTarget.setSelectedItem(questionTarget);
	}

	public String getDescription() {
		return textFieldDescription.getText();
	}
	
	public void setDescription(String description){
		textFieldDescription.setText(description);
	}

	public QuestionTarget getQuestionTarget() {
		return (QuestionTarget)comboBoxQuestionTarget.getSelectedItem();
	}

	public String getQuestionSubCategory() {
		return (String)comboBoxSubCategory.getSelectedItem();
	}
	
	public void addQuestioSubCategory(String subCategory){
		comboBoxSubCategory.addItem(subCategory);
	}
	
	public void setQuestionSubCategory(String subCategory){
		if (subCategory.equals("")){
			comboBoxSubCategory.addItem("");
		}
		comboBoxSubCategory.setSelectedItem(subCategory);
	}
	
	public void setQuestionSubCategory(int idx){
		if (idx < comboBoxSubCategory.getItemCount()){
			comboBoxSubCategory.setSelectedIndex(idx);
		}
	}
	
	public void resetSubCategory(){
		comboBoxSubCategory.removeAllItems();
	}
}