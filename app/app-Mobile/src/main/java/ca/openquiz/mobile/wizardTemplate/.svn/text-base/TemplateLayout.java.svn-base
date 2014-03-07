package ca.openquiz.mobile.wizardTemplate;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import ca.openquiz.mobile.R;

public class TemplateLayout extends LinearLayout
{
	
	public TemplateLayout(Context context, int position)
	{
		super(context);

        View.inflate(context, R.layout.wizard_template, this);
        setSeriesCount(position);
	}
	
	private void setSeriesCount(int position)
	{
		TextView textViewSeries = (TextView)findViewById(R.id.wizardtemplate_title);
		textViewSeries.setText(textViewSeries.getText() + " " + (position+1));
	}

}
