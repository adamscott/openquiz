package ca.openquiz.mobile.wizardQuestionSet;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import ca.openquiz.mobile.R;
import ca.openquiz.mobile.createNewGame.CreateNewGameController;
import ca.openquiz.mobile.createNewGame.CreateNewGameView;

public class SwipeyTabFragment extends ListFragment {
	
		private ListAdapter mAdapter;
		private int mCurrentSelectedPosition = -1;
        public static Fragment newInstance() {
                SwipeyTabFragment f = new SwipeyTabFragment();
                return f;
        }
        
        public void setAdapter(ListAdapter adapter) {
        	mAdapter = adapter;
        }
        
        @Override
        public int getSelectedItemPosition() {
        	
			return mCurrentSelectedPosition;
		}

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                        Bundle savedInstanceState) {
                ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_swipeytab, null);
                
                // This correct the black listView bug, thank's to my stack buddy.
                ListView ls = (ListView) root.findViewById(android.R.id.list);
                ls.setCacheColorHint(0);
                
                setListAdapter(mAdapter);
                
                return root;
        }        
        
        @Override
        public void onListItemClick(ListView l, View v, int position, long id)
        {
        	CreateNewGameView view = (CreateNewGameView)CreateNewGameController.getInstance().getView();
        	if(view.isGenerateGameFromTemplate())
        	{
        		System.out.println(String.format("Select %d from template list", position));
        		view.setSelectedTemplateIndex(position);
        	} 
        	else
        	{
        		System.out.println(String.format("Select %d from questionSet list", position)); 
        		view.setQuestionSetTableSelectedRow(position);
        	}
    		mCurrentSelectedPosition = position;	
        }
        
}

