package ca.openquiz.mobile.util;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import ca.openquiz.mobile.R;

public class TemplateArrayAdapter extends ArrayAdapter<Object[]> {
	private final Context context;
	private final List<Object[]> values;

	public TemplateArrayAdapter(Context context, List<Object[]> values) {
		super(context, R.layout.template_list_item, values);
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.template_list_item, parent, false);
		TextView textViewName = (TextView) rowView.findViewById(R.id.templateName);
		TextView textViewSectionCount = (TextView) rowView.findViewById(R.id.templateSectionCount);
		textViewName.setText(values.get(position)[0].toString());
		textViewSectionCount.setText(values.get(position)[1].toString());

		return rowView;
	}
	
	@Override
	public void add(Object[] object)
	{
		super.add(object);
	}
	
	

} 
