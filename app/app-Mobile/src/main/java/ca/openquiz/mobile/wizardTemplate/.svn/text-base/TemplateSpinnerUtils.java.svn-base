package ca.openquiz.mobile.wizardTemplate;

import android.widget.LinearLayout;
import android.widget.Spinner;
import ca.openquiz.comms.enums.CategoryType;
import ca.openquiz.comms.enums.Degree;
import ca.openquiz.comms.enums.QuestionType;
import ca.openquiz.comms.enums.QuestionTarget;
import ca.openquiz.mobile.R;
import ca.openquiz.mobile.createNewGame.CreateNewGameController;

public class TemplateSpinnerUtils {
	
	private LinearLayout linearLayout;

	public TemplateSpinnerUtils(LinearLayout linearLayout) {

		this.linearLayout = linearLayout;
	}
	
	public QuestionType getQuestionType()
	{
		final Spinner spinnerType = (Spinner)linearLayout.findViewById(R.id.wizardtemplate_type_spinner);
		String type = (String)spinnerType.getAdapter().getItem(spinnerType.getSelectedItemPosition());
		
		return QuestionType.findByName(type);
	}
	
	public Degree getQuestionDegree()
	{
		final Spinner spinnerDif = (Spinner)linearLayout.findViewById(R.id.wizardtemplate_difficulty_spinner);
		String dif = (String)spinnerDif.getAdapter().getItem(spinnerDif.getSelectedItemPosition());
		
		return Degree.findByStringName(dif);
	}
	
	public String getQuestionCategory()
	{
		String key = null;
		
		final Spinner spinnerCat = (Spinner)linearLayout.findViewById(R.id.wizardtemplate_category_spinner);
		final Spinner subSpinnerCat = (Spinner)linearLayout.findViewById(R.id.wizardtemplate_subcategory_spinner);
		
		if(spinnerCat.getSelectedItemPosition() != 0)
		{
			String category = (String)spinnerCat.getAdapter().getItem(spinnerCat.getSelectedItemPosition());
			String subCategory = (String)subSpinnerCat.getAdapter().getItem(subSpinnerCat.getSelectedItemPosition());
			
			key = CreateNewGameController.getInstance().getModel().getCategoryIDFromTypeAndName(
					CategoryType.findByStringName(category), subCategory);
		}

		return key;
	}
	
	public QuestionTarget getQuestionTarget()
	{
		final Spinner spinnerTarget = (Spinner)linearLayout.findViewById(R.id.wizardtemplate_target_spinner);
		String target = (String)spinnerTarget.getAdapter().getItem(spinnerTarget.getSelectedItemPosition());
		
		return QuestionTarget.findByName(target);
	}
}
