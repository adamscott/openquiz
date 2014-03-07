package ca.openquiz.mobile.wizardTemplate;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import ca.openquiz.mobile.R;

public class NewTemplateArrayAdapter extends ArrayAdapter<TemplateLayout>
{
	private final List<TemplateLayout> values;

	public NewTemplateArrayAdapter(Context context, List<TemplateLayout> values)
	{
		super(context, R.layout.wizard_template, values);
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{

		View rowView = values.get(position);
		
		return rowView;
	}
} 
 
