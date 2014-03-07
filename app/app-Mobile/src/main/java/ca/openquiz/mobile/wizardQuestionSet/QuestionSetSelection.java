package ca.openquiz.mobile.wizardQuestionSet;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import applicationTools.LoginManager;
import ca.openquiz.mobile.R;
import ca.openquiz.mobile.ThreadManager;
import ca.openquiz.mobile.bluetooth.BluetoothActivity;
import ca.openquiz.mobile.createNewGame.CreateNewGameController;
import ca.openquiz.mobile.createNewGame.CreateNewGameTask;
import ca.openquiz.mobile.createNewGame.CreateNewGameView;
import ca.openquiz.mobile.login.LoginAct;
import ca.openquiz.mobile.util.AlertBuilder;
import ca.openquiz.mobile.util.Defines;
import ca.openquiz.mobile.wizardTemplate.WizardCreateTemplateAct;

public class QuestionSetSelection extends FragmentActivity
{
	private CreateNewGameTask createNewGameTask = new CreateNewGameTask();

	private static final String [] TITLES = {
		"Questions prédéfinies",
		"Questions aléatoires"
	};

	private SwipeyTabs mTabs;
	private ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		overridePendingTransition(R.anim.anim_toright_in,R.anim.anim_toright_out);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		ThreadManager.getInstance().getCreateNewGameThreadPool().execute(createNewGameTask.initQuestionSetAct(QuestionSetSelection.this));
		
		setContentView(R.layout.questionset_screen);
		
		setLogoutIconVisibility();
		
		// SWIPEYYYYYYYYYYYYYYYYYY!
		mViewPager = (ViewPager) findViewById(R.id.viewpager);
		mTabs = (SwipeyTabs) findViewById(R.id.swipeytabs);
		
		SwipeyTabsPagerAdapter adapter = new SwipeyTabsPagerAdapter(this, getSupportFragmentManager());
		mViewPager.setAdapter(adapter);
		mTabs.setAdapter(adapter);
		mViewPager.setOnPageChangeListener(pageChangeListener);
		mViewPager.setCurrentItem(0);

		registerListeners();
	}
	
	@Override
	public void onStart()
	{
		super.onStart();
		ThreadManager.getInstance().getCreateNewGameThreadPool().execute(createNewGameTask.refreshQuestionSetList());
		resetIndex();
	}
	
	private void resetIndex()
	{
		CreateNewGameView newGameView = ((CreateNewGameView)CreateNewGameController.getInstance().getView()); 
		newGameView.setSelectedTemplateIndex(-1);
		newGameView.setQuestionSetTableSelectedRow(-1);

		if(mViewPager.getCurrentItem() == 0)
			newGameView.setIsGenerateFromTemplate(false);
	}

	private void setLogoutIconVisibility() 
	{
		if(LoginManager.getInstance().getLoggedUsername().equals(""))
		{
			findViewById(R.id.userlogoutButtonQS).setVisibility(View.INVISIBLE);
			findViewById(R.id.ButtonAddLayout).setVisibility(View.INVISIBLE);
		}
		else
		{
			findViewById(R.id.userlogoutButtonQS).setVisibility(View.VISIBLE);
			findViewById(R.id.ButtonAddLayout).setVisibility(View.VISIBLE);
		}
	}
	
	private void registerListeners()
	{
		findViewById(R.id.btn_play).setOnClickListener(playGameButtonListener);
		findViewById(R.id.userlogoutButtonQS).setOnClickListener(logoutButtonListener);
		findViewById(R.id.btnAdd).setOnClickListener(addButtonListener);
	}
	
	private class SwipeyTabsPagerAdapter extends FragmentPagerAdapter implements
	SwipeyTabsAdapter {

		private final Context mContext;

		public SwipeyTabsPagerAdapter(Context context, FragmentManager fm) {
			super(fm);

			this.mContext = context;
		}

		@Override
		public Fragment getItem(int position) {

			SwipeyTabFragment frag = null;
			if(position == 0)
			{
				frag = (SwipeyTabFragment) SwipeyTabFragment.newInstance();
				frag.setAdapter(((CreateNewGameView)CreateNewGameController.getInstance().getView()).adaptorQuestionSet);
			}
			else if (position == 1)
			{
				frag = (SwipeyTabFragment) SwipeyTabFragment.newInstance();
				frag.setAdapter(((CreateNewGameView)CreateNewGameController.getInstance().getView()).adaptorTemplate);
			}

			return frag;
		}

		@Override
		public int getCount() {
			return TITLES.length;
		}

		public TextView getTab(final int position, SwipeyTabs root) {
			TextView view = (TextView) LayoutInflater.from(mContext).inflate(
					R.layout.swipey_tab_indicator, root, false);
			view.setText(TITLES[position]);
			view.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					mViewPager.setCurrentItem(position);
				}
			});

			return view;
		}

	}
	
	@Override
	public void onBackPressed() {
	    super.onBackPressed();
	    overridePendingTransition(R.anim.anim_toleft_in, R.anim.anim_toleft_out);
	    
		CreateNewGameView newGameView = ((CreateNewGameView)CreateNewGameController.getInstance().getView()); 
		newGameView.setIsGenerateFromTemplate(false);
	}

	//	/******************************************************************************************************************
	//	 ********************************** Listeners implementation ******************************************************
	//	 ******************************************************************************************************************/
	
	View.OnClickListener playGameButtonListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {

			String gameName = ((EditText)QuestionSetSelection.this.findViewById(R.id.txtGameName)).getText().toString();
			CreateNewGameView newGameView = ((CreateNewGameView)CreateNewGameController.getInstance().getView()); 
			newGameView.setGameName(gameName);
			
			boolean generatedTemplate = newGameView.isGenerateGameFromTemplate();
			int selectedIndex;
			
        	if(generatedTemplate)
        		selectedIndex = newGameView.getSelectedTemplateIndex();
        	else
        		selectedIndex = newGameView.getQuestionSetTableSelectedRow();
        	if(generatedTemplate && !LoginManager.getInstance().isAnyUserLogged())
        	{
        		String title = QuestionSetSelection.this.getResources().getString(R.string.message_game_user_logged_error_title);
				String message = QuestionSetSelection.this.getResources().getString(R.string.message_game_user_logged_error_message);
				AlertBuilder.showPopUp(QuestionSetSelection.this, title, message);
        	}
        	else if(selectedIndex == -1)
			{
				String title = QuestionSetSelection.this.getResources().getString(R.string.message_game_selection_error_title);
				String message = QuestionSetSelection.this.getResources().getString(R.string.message_game_selection_error_message);
				AlertBuilder.showPopUp(QuestionSetSelection.this, title, message);
			}
			else if(gameName.equals(""))
			{
				String title = QuestionSetSelection.this.getResources().getString(R.string.message_game_name_error_title);
				String message = QuestionSetSelection.this.getResources().getString(R.string.message_game_name_error_message);
				AlertBuilder.showPopUp(QuestionSetSelection.this, title, message);
			}
			else
			{
				if(Defines.ENABLE_BLUETOOTH)
				{
					Intent intent = new Intent(getBaseContext(), BluetoothActivity.class);
					startActivity(intent);
				}
				else
					new PlayGameAsyncTask(QuestionSetSelection.this).execute();	
			}
		}
	};
	
	View.OnClickListener logoutButtonListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			ImageView imv = (ImageView) findViewById(R.id.userlogoutButtonQS);
			imv.setImageResource(R.drawable.userlogouticonclicked);
			
			String title = QuestionSetSelection.this.getResources().getString(R.string.message_logout_title);
			String message = QuestionSetSelection.this.getResources().getString(R.string.message_logout_message);
			
			AlertDialog.Builder alert = new AlertDialog.Builder(QuestionSetSelection.this);
			
			alert.setTitle(title);
		    alert.setMessage(message);
		    alert.setPositiveButton(R.string.positive_dialog_button, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		        	ThreadManager.getInstance().getCreateNewGameThreadPool().execute(createNewGameTask.logout());
		        	Intent intent = new Intent(getBaseContext(), LoginAct.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					QuestionSetSelection.this.startActivity(intent);
		        }
		     });
		    alert.setNegativeButton(R.string.negative_dialog_button, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		        	ImageView imv = (ImageView) findViewById(R.id.userlogoutButtonQS);
		        	imv.setImageResource(R.drawable.userlogouticon);
		        }
		     });
		    alert.show();
		}
	};
	
	View.OnClickListener addButtonListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			
			Intent intent = new Intent(getBaseContext(), WizardCreateTemplateAct.class);
			QuestionSetSelection.this.startActivity(intent);
		}
	};
	
	ViewPager.OnPageChangeListener pageChangeListener = new OnPageChangeListener() {
		
		@Override
		public void onPageSelected(int position) {
			if(position == 0)
            {
            	findViewById(R.id.btnAdd).setVisibility(View.INVISIBLE);
            }
            else if (position == 1)
            {
            	findViewById(R.id.btnAdd).setVisibility(View.VISIBLE);
            }
			((CreateNewGameView)CreateNewGameController.getInstance().getView()).setIsGenerateFromTemplate(position==0?false:true);
			mTabs.onPageSelected(position);
		}
		
		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			mTabs.onPageScrolled(position, positionOffset, positionOffsetPixels);
		}
		
		@Override
		public void onPageScrollStateChanged(int state) {
			mTabs.onPageScrollStateChanged(state);
		}
	};
}