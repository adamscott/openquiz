package ca.openquiz.mobile.util;

import java.util.Collection;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import ca.openquiz.mobile.R;

public class QuestionSetArrayAdapter extends ArrayAdapter<Object[]> {
	private final Context context;
	private final List<Object[]> values;

	public QuestionSetArrayAdapter(Context context, List<Object[]> values) {
		super(context, R.layout.questionset_list_item, values);
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.questionset_list_item, parent, false);
		TextView textViewName = (TextView) rowView.findViewById(R.id.qsName);
		TextView textViewDate = (TextView) rowView.findViewById(R.id.qsDate);
		TextView textViewCount = (TextView) rowView.findViewById(R.id.qsCount);
		textViewName.setText(values.get(position)[0].toString());
		textViewDate.setText(values.get(position)[1].toString());
		textViewCount.setText(values.get(position)[2].toString());

		return rowView;
	}
	
	@Override
	public void addAll(Collection<? extends Object[]> collection)
	{
		super.addAll(collection);
	}

} 
