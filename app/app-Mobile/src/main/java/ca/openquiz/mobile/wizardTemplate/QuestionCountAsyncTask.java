package ca.openquiz.mobile.wizardTemplate;

import android.os.AsyncTask;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import ca.openquiz.comms.RequestsWebService;
import ca.openquiz.comms.model.TemplateSection;
import ca.openquiz.comms.response.IntegerResponse;
import ca.openquiz.mobile.R;

public class QuestionCountAsyncTask extends AsyncTask<Void, Void, Integer>{
	
	private TemplateSection ts;
	private LinearLayout templateLayout;
	
	public QuestionCountAsyncTask(View view)
	{
		templateLayout = (LinearLayout)view.getParent().getParent().getParent();
		ts = new TemplateSection();
	}
	
	@Override
    protected void onPreExecute() {
		
		TemplateSpinnerUtils spinnerUtils = new TemplateSpinnerUtils(templateLayout);
		
		if(spinnerUtils.getQuestionDegree() != null)
			ts.setDifficulty(spinnerUtils.getQuestionDegree());
		
		if(spinnerUtils.getQuestionType() != null)
			ts.setQuestionType(spinnerUtils.getQuestionType());
		
		if(spinnerUtils.getQuestionCategory() != null)
			ts.setCategory(spinnerUtils.getQuestionCategory());
    }
	
	@Override
	protected Integer doInBackground(Void... params) {

		Integer count = 0;
		
		IntegerResponse nbQuestionResponse = RequestsWebService.getQuestionNbInTemplateSection(ts);
		if (nbQuestionResponse != null)
			count = nbQuestionResponse.getValue();
		
		return count;
	}

	@Override
    protected void onPostExecute(Integer count) {
		
		TextView textView = (TextView)templateLayout.findViewById(R.id.wizardtemplate_quantity_max);
		textView.setText("/" + count);
    }
}
