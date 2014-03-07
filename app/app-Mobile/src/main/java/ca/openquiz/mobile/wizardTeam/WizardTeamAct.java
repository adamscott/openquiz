package ca.openquiz.mobile.wizardTeam;

import java.lang.reflect.Field;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import applicationTools.LoginManager;
import ca.openquiz.mobile.R;
import ca.openquiz.mobile.ThreadManager;
import ca.openquiz.mobile.createNewGame.CreateNewGameController;
import ca.openquiz.mobile.createNewGame.CreateNewGameTask;
import ca.openquiz.mobile.createNewGame.CreateNewGameView;
import ca.openquiz.mobile.login.LoginAct;
import ca.openquiz.mobile.util.AlertBuilder;
import ca.openquiz.mobile.util.Defines;

public class WizardTeamAct extends Activity {
	
    private CreateNewGameTask createNewGameTask = new CreateNewGameTask();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		if(Defines.PRINT_DEBUG_INFO) System.out.println("[DEBUG] onCreate");	
		getOverflowMenu();
		
		overridePendingTransition(R.anim.anim_toright_in,R.anim.anim_toright_out);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		ThreadManager.getInstance().getCreateNewGameThreadPool().execute(createNewGameTask.initTeamAct(WizardTeamAct.this));

		setContentView(R.layout.wizard_team_screen);
		
		setLogoutIconVisibility();
		
		registerListeners();
		setTeamNumber();
	}
	
	private void setLogoutIconVisibility() 
	{
		if(LoginManager.getInstance().getLoggedUsername().equals(""))
		{
			findViewById(R.id.userlogoutButton).setVisibility(View.INVISIBLE);
		}
		else
		{
			findViewById(R.id.userlogoutButton).setVisibility(View.VISIBLE);
		}
	}
	
	private void getOverflowMenu() {

	    try {
	       ViewConfiguration config = ViewConfiguration.get(this);
	       Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
	       if(menuKeyField != null) {
	           menuKeyField.setAccessible(true);
	           menuKeyField.setBoolean(config, false);
	       }
	   } catch (Exception e) {
	       e.printStackTrace();
	   }
	 }
	
	@Override
    protected void onResume() {
        super.onResume();
        
		ThreadManager.getInstance().getCreateNewGameThreadPool().execute(createNewGameTask.setCurrentActivity(WizardTeamAct.this));
    }
	
	@Override
    protected void onPause() {

		ThreadManager.getInstance().getCreateNewGameThreadPool().execute(createNewGameTask.clearCurrentActivity(WizardTeamAct.this));
        super.onPause();
    }
	
	@Override
    protected void onDestroy() {        

		if(Defines.PRINT_DEBUG_INFO) System.out.println("[DEBUG] onDestroy");
		ThreadManager.getInstance().getCreateNewGameThreadPool().execute(createNewGameTask.clearCurrentActivity(WizardTeamAct.this));
		super.onDestroy();
    }

	private void registerListeners()
	{
		findViewById(R.id.button_next).setOnClickListener(nextListener);
		findViewById(R.id.btnJoinGame).setOnClickListener(joinGameListener);
		findViewById(R.id.userlogoutButton).setOnClickListener(logoutListener);
		
		LinearLayout layoutTeam = (LinearLayout)this.findViewById(R.id.wizard_team_1);
		Spinner groupSpinner = (Spinner)layoutTeam.findViewById(R.id.wizardteam_group_spinner);
		Spinner teamSpinner = (Spinner)layoutTeam.findViewById(R.id.wizardteam_team_spinner);
		Spinner userSpinner1 = (Spinner)layoutTeam.findViewById(R.id.wizardteam_user_spinner_1);
		Spinner userSpinner2 = (Spinner)layoutTeam.findViewById(R.id.wizardteam_user_spinner_2);
		Spinner userSpinner3 = (Spinner)layoutTeam.findViewById(R.id.wizardteam_user_spinner_3);
		Spinner userSpinner4 = (Spinner)layoutTeam.findViewById(R.id.wizardteam_user_spinner_4);
		
		
		
		groupSpinner.setOnItemSelectedListener(groupLeftSelected);
		
		teamSpinner.setOnItemSelectedListener(teamLeftSelected);
		teamSpinner.setOnTouchListener(leftTeamSpinnerClicked);
		
		userSpinner1.setOnItemSelectedListener(user1LeftSelected);
		userSpinner2.setOnItemSelectedListener(user2LeftSelected);
		userSpinner3.setOnItemSelectedListener(user3LeftSelected);
		userSpinner4.setOnItemSelectedListener(user4LeftSelected);
		
		userSpinner1.setOnTouchListener(leftUserSpinnerClicked);
		userSpinner2.setOnTouchListener(leftUserSpinnerClicked);
		userSpinner3.setOnTouchListener(leftUserSpinnerClicked);
		userSpinner4.setOnTouchListener(leftUserSpinnerClicked);
		
		layoutTeam = (LinearLayout)this.findViewById(R.id.wizard_team_2);
		groupSpinner = (Spinner)layoutTeam.findViewById(R.id.wizardteam_group_spinner);
		teamSpinner = (Spinner)layoutTeam.findViewById(R.id.wizardteam_team_spinner);
		userSpinner1 = (Spinner)layoutTeam.findViewById(R.id.wizardteam_user_spinner_1);
		userSpinner2 = (Spinner)layoutTeam.findViewById(R.id.wizardteam_user_spinner_2);
		userSpinner3 = (Spinner)layoutTeam.findViewById(R.id.wizardteam_user_spinner_3);
		userSpinner4 = (Spinner)layoutTeam.findViewById(R.id.wizardteam_user_spinner_4);

		groupSpinner.setOnItemSelectedListener(groupRightSelected);
		
		teamSpinner.setOnItemSelectedListener(teamRightSelected);
		teamSpinner.setOnTouchListener(rightTeamSpinnerClicked);
		
		userSpinner1.setOnItemSelectedListener(user1RightSelected);
		userSpinner2.setOnItemSelectedListener(user2RightSelected);
		userSpinner3.setOnItemSelectedListener(user3RightSelected);
		userSpinner4.setOnItemSelectedListener(user4RightSelected);
		
		userSpinner1.setOnTouchListener(rightUserSpinnerClicked);
		userSpinner2.setOnTouchListener(rightUserSpinnerClicked);
		userSpinner3.setOnTouchListener(rightUserSpinnerClicked);
		userSpinner4.setOnTouchListener(rightUserSpinnerClicked);
	}
	
	private void setTeamNumber()
	{
		LinearLayout layoutTeam = (LinearLayout)findViewById(R.id.wizard_team_1);
		TextView textViewTeam = (TextView)layoutTeam.findViewById(R.id.wizardteam_title);
		textViewTeam.setText(textViewTeam.getText() + " 1");
		
		layoutTeam = (LinearLayout)findViewById(R.id.wizard_team_2);
		textViewTeam = (TextView)layoutTeam.findViewById(R.id.wizardteam_title);
		textViewTeam.setText(textViewTeam.getText() + " 2");
	}
	
	@Override
	public void onBackPressed() {
	    super.onBackPressed();
	    overridePendingTransition(R.anim.anim_toleft_in, R.anim.anim_toleft_out);
	    
	    if(LoginManager.getInstance().isAnyUserLogged())
	    	ThreadManager.getInstance().getCreateNewGameThreadPool().execute(createNewGameTask.logout());
	    
	}
	
//	/******************************************************************************************************************
//	 ********************************** Listeners implementation ******************************************************
//	 ******************************************************************************************************************/
	
	View.OnClickListener nextListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {

			((CreateNewGameView)CreateNewGameController.getInstance().getView()).setIsLoadGeneratedGame(false);
			ThreadManager.getInstance().getCreateNewGameThreadPool().execute(createNewGameTask.verifyDuplicateName());
		}
	};
	
	View.OnClickListener joinGameListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(getBaseContext(), JoinGameActivity.class);
			WizardTeamAct.this.startActivity(intent);
		}
	};
	
	View.OnClickListener logoutListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			ImageView imv = (ImageView) findViewById(R.id.userlogoutButton);
			imv.setImageResource(R.drawable.userlogouticonclicked);
			
			String title = WizardTeamAct.this.getResources().getString(R.string.message_logout_title);
			String message = WizardTeamAct.this.getResources().getString(R.string.message_logout_message);
			
			AlertDialog.Builder alert = new AlertDialog.Builder(WizardTeamAct.this);
			
			alert.setTitle(title);
		    alert.setMessage(message);
		    alert.setPositiveButton(R.string.positive_dialog_button, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		        	ThreadManager.getInstance().getCreateNewGameThreadPool().execute(createNewGameTask.logout());
		        	Intent intent = new Intent(getBaseContext(), LoginAct.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					WizardTeamAct.this.startActivity(intent);
		        	return;
		        }
		     });
		    alert.setNegativeButton(R.string.negative_dialog_button, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		        	ImageView imv = (ImageView) findViewById(R.id.userlogoutButton);
		        	imv.setImageResource(R.drawable.userlogouticon);
		        }
		     });
		    alert.show();
		}
	};
	
	AdapterView.OnItemSelectedListener groupLeftSelected = new AdapterView.OnItemSelectedListener()
	{
		public void onItemSelected(AdapterView<?> parent, View view, final int pos, long id)
		{
			ThreadManager.getInstance().getCreateNewGameThreadPool().execute(createNewGameTask.checkForDuplicateNameInSameGroup());
			
			if(pos == 0)
			{
				ThreadManager.getInstance().getCreateNewGameThreadPool().execute(createNewGameTask.clearLeftTeamPlayerNames());
				ThreadManager.getInstance().getCreateNewGameThreadPool().execute(createNewGameTask.clearLeftTeamComboBox());
			}
			else if(pos != 0)
			{
				ThreadManager.getInstance().getCreateNewGameThreadPool().execute(createNewGameTask.updateLeftGroup(pos));
			}
		}
		public void onNothingSelected(AdapterView<?> parent)
		{
		}
	};
	
	AdapterView.OnItemSelectedListener groupRightSelected = new AdapterView.OnItemSelectedListener()
	{
	    public void onItemSelected(AdapterView<?> parent, View view, final int pos, long id)
	    {
	    	ThreadManager.getInstance().getCreateNewGameThreadPool().execute(createNewGameTask.checkForDuplicateNameInSameGroup());
	    	
			if(pos == 0)
			{
				ThreadManager.getInstance().getCreateNewGameThreadPool().execute(createNewGameTask.clearRightTeamPlayerNames());
				ThreadManager.getInstance().getCreateNewGameThreadPool().execute(createNewGameTask.clearRightTeamComboBox());
			}
			else if(pos != 0)
				ThreadManager.getInstance().getCreateNewGameThreadPool().execute(createNewGameTask.updateRightGroup(pos));
	    }
	    public void onNothingSelected(AdapterView<?> parent)
	    {
	    }
	};
	
	AdapterView.OnItemSelectedListener teamLeftSelected = new AdapterView.OnItemSelectedListener()
	{
		public void onItemSelected(AdapterView<?> parent, View view, final int pos, long id)
		{	
			if(pos == 0)
			{
				ThreadManager.getInstance().getCreateNewGameThreadPool().execute(createNewGameTask.clearLeftTeamPlayerNames());
			}

			ThreadManager.getInstance().getCreateNewGameThreadPool().execute(createNewGameTask.checkForDuplicateNameInSameGroup());
			ThreadManager.getInstance().getCreateNewGameThreadPool().execute(createNewGameTask.updateLeftTeam(pos));
		}
		public void onNothingSelected(AdapterView<?> parent)
		{
		}
	};	
	
	AdapterView.OnItemSelectedListener teamRightSelected = new AdapterView.OnItemSelectedListener()
	{
		public void onItemSelected(AdapterView<?> parent, View view, final int pos, long id)
		{
			if(pos == 0)
			{
				ThreadManager.getInstance().getCreateNewGameThreadPool().execute(createNewGameTask.clearRightTeamPlayerNames());
			}
			
			ThreadManager.getInstance().getCreateNewGameThreadPool().execute(createNewGameTask.checkForDuplicateNameInSameGroup());	
			ThreadManager.getInstance().getCreateNewGameThreadPool().execute(createNewGameTask.updateRightTeam(pos));
		}
		public void onNothingSelected(AdapterView<?> parent)
		{
		}
	};	
	
	AdapterView.OnTouchListener leftTeamSpinnerClicked = new AdapterView.OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_UP) {
				LinearLayout layoutTeam = (LinearLayout)WizardTeamAct.this.findViewById(R.id.wizard_team_1);
				Spinner groupSpinner = (Spinner)layoutTeam.findViewById(R.id.wizardteam_group_spinner);
				
				if(groupSpinner.getSelectedItemPosition()==0)
				{
					String title = WizardTeamAct.this.getResources().getString(R.string.message_group_not_selected_error_title);
					String message = WizardTeamAct.this.getResources().getString(R.string.message_group_not_selected_error_message);
					AlertBuilder.showPopUp(WizardTeamAct.this, title, message);
					return true;
				}
			}
			return false;
		}
	};
	
	AdapterView.OnTouchListener rightTeamSpinnerClicked = new AdapterView.OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_UP) {
				LinearLayout layoutTeam = (LinearLayout)WizardTeamAct.this.findViewById(R.id.wizard_team_2);
				Spinner groupSpinner = (Spinner)layoutTeam.findViewById(R.id.wizardteam_group_spinner);
				
				if(groupSpinner.getSelectedItemPosition()==0)
				{
					String title = WizardTeamAct.this.getResources().getString(R.string.message_group_not_selected_error_title);
					String message = WizardTeamAct.this.getResources().getString(R.string.message_group_not_selected_error_message);
					AlertBuilder.showPopUp(WizardTeamAct.this, title, message);
					return true;
				}
			}
			return false;
		}
	};
	
	AdapterView.OnTouchListener leftUserSpinnerClicked = new AdapterView.OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_UP) {
				LinearLayout layoutTeam = (LinearLayout)WizardTeamAct.this.findViewById(R.id.wizard_team_1);
				Spinner teamSpinner = (Spinner)layoutTeam.findViewById(R.id.wizardteam_team_spinner);
				
				if(teamSpinner.getSelectedItemPosition()==0)
				{
					String title = WizardTeamAct.this.getResources().getString(R.string.message_team_not_selected_error_title);
					String message = WizardTeamAct.this.getResources().getString(R.string.message_team_not_selected_error_message);
					AlertBuilder.showPopUp(WizardTeamAct.this, title, message);
					return true;
				}
			}
			return false;
		}
	};
	
	AdapterView.OnTouchListener rightUserSpinnerClicked = new AdapterView.OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_UP) {
				LinearLayout layoutTeam = (LinearLayout)WizardTeamAct.this.findViewById(R.id.wizard_team_2);
				Spinner teamSpinner = (Spinner)layoutTeam.findViewById(R.id.wizardteam_team_spinner);
				
				if(teamSpinner.getSelectedItemPosition()==0)
				{
					String title = WizardTeamAct.this.getResources().getString(R.string.message_team_not_selected_error_title);
					String message = WizardTeamAct.this.getResources().getString(R.string.message_team_not_selected_error_message);
					AlertBuilder.showPopUp(WizardTeamAct.this, title, message);
					return true;
				}
			}
			return false;
		}
	};
	
	AdapterView.OnItemSelectedListener user1LeftSelected = new AdapterView.OnItemSelectedListener()
	{
		public void onItemSelected(AdapterView<?> parent, View view, final int pos, long id)
		{
			ThreadManager.getInstance().getCreateNewGameThreadPool().execute(
				createNewGameTask.updateUser(Defines.GROUP_POSITION_1, Defines.USER_POSITION_1, pos));
		}
		public void onNothingSelected(AdapterView<?> parent)
		{
		}
	};	
	
	AdapterView.OnItemSelectedListener user2LeftSelected = new AdapterView.OnItemSelectedListener()
	{
		public void onItemSelected(AdapterView<?> parent, View view, final int pos, long id)
		{
			ThreadManager.getInstance().getCreateNewGameThreadPool().execute(
				createNewGameTask.updateUser(Defines.GROUP_POSITION_1, Defines.USER_POSITION_2, pos));
		}
		public void onNothingSelected(AdapterView<?> parent)
		{
		}
	};	
	
	AdapterView.OnItemSelectedListener user3LeftSelected = new AdapterView.OnItemSelectedListener()
	{
		public void onItemSelected(AdapterView<?> parent, View view, final int pos, long id)
		{
			ThreadManager.getInstance().getCreateNewGameThreadPool().execute(
				createNewGameTask.updateUser(Defines.GROUP_POSITION_1, Defines.USER_POSITION_3, pos));
		}
		public void onNothingSelected(AdapterView<?> parent)
		{
		}
	};	
	
	AdapterView.OnItemSelectedListener user4LeftSelected = new AdapterView.OnItemSelectedListener()
	{
	    public void onItemSelected(AdapterView<?> parent, View view, final int pos, long id)
	    {
			ThreadManager.getInstance().getCreateNewGameThreadPool().execute(
				createNewGameTask.updateUser(Defines.GROUP_POSITION_1, Defines.USER_POSITION_4, pos));
	    }
	    public void onNothingSelected(AdapterView<?> parent)
	    {
	    }
	};	
	AdapterView.OnItemSelectedListener user1RightSelected = new AdapterView.OnItemSelectedListener()
	{
		public void onItemSelected(AdapterView<?> parent, View view, final int pos, long id)
		{
			ThreadManager.getInstance().getCreateNewGameThreadPool().execute(
				createNewGameTask.updateUser(Defines.GROUP_POSITION_2, Defines.USER_POSITION_1, pos));
		}
		public void onNothingSelected(AdapterView<?> parent)
		{
		}
	};	
	
	AdapterView.OnItemSelectedListener user2RightSelected = new AdapterView.OnItemSelectedListener()
	{
		public void onItemSelected(AdapterView<?> parent, View view, final int pos, long id)
		{
			ThreadManager.getInstance().getCreateNewGameThreadPool().execute(
				createNewGameTask.updateUser(Defines.GROUP_POSITION_2, Defines.USER_POSITION_2, pos));
		}
		public void onNothingSelected(AdapterView<?> parent)
		{
		}
	};	
	
	AdapterView.OnItemSelectedListener user3RightSelected = new AdapterView.OnItemSelectedListener()
	{
		public void onItemSelected(AdapterView<?> parent, View view, final int pos, long id)
		{
			ThreadManager.getInstance().getCreateNewGameThreadPool().execute(
				createNewGameTask.updateUser(Defines.GROUP_POSITION_2, Defines.USER_POSITION_3, pos));
		}
		public void onNothingSelected(AdapterView<?> parent)
		{
		}
	};	
	
	AdapterView.OnItemSelectedListener user4RightSelected = new AdapterView.OnItemSelectedListener()
	{
		public void onItemSelected(AdapterView<?> parent, View view, final int pos, long id)
		{
			ThreadManager.getInstance().getCreateNewGameThreadPool().execute(
				createNewGameTask.updateUser(Defines.GROUP_POSITION_2, Defines.USER_POSITION_4, pos));
		}
		public void onNothingSelected(AdapterView<?> parent)
		{
		}
	};	
}

