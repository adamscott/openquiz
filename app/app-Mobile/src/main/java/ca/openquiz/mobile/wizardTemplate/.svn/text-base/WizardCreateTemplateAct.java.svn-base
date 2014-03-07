package ca.openquiz.mobile.wizardTemplate;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import ca.openquiz.comms.model.Template;
import ca.openquiz.comms.model.TemplateSection;
import ca.openquiz.mobile.R;
import ca.openquiz.mobile.ThreadManager;
import ca.openquiz.mobile.bluetooth.BluetoothActivity;
import ca.openquiz.mobile.createNewGame.CreateNewGameController;
import ca.openquiz.mobile.createNewGame.CreateNewGameTask;
import ca.openquiz.mobile.createNewGame.CreateNewGameView;
import ca.openquiz.mobile.util.AlertBuilder;
import ca.openquiz.mobile.util.Defines;
import ca.openquiz.mobile.util.SwipeDismissListViewTouchListener;
import ca.openquiz.mobile.wizardQuestionSet.PlayGameAsyncTask;

public class WizardCreateTemplateAct extends Activity {
	
	private List<TemplateLayout> templateView;
	private CreateNewGameTask createNewTemplateTask = new CreateNewGameTask();
	ListView templateListView;
	NewTemplateArrayAdapter arrayAdapter;
	private List<TemplateSection> templates;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.anim_upperright_corner_in, R.anim.anim_null);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.wizard_template_screen);

		templateView = new ArrayList<TemplateLayout>();
		
		arrayAdapter = new NewTemplateArrayAdapter(this, templateView);
		
		templateView.add(new TemplateLayout(getBaseContext(), arrayAdapter.getCount()));
		templateListView = (ListView) findViewById(R.id.template_list);
		templateListView.setAdapter(arrayAdapter);
		
		ThreadManager.getInstance().getCreateNewGameThreadPool().execute(createNewTemplateTask.initTemplateAct(WizardCreateTemplateAct.this));
		
		registerListeners();
	}
	
	@Override
    protected void onResume() {
        super.onResume();
        
		ThreadManager.getInstance().getCreateNewGameThreadPool().execute(createNewTemplateTask.setCurrentActivity(WizardCreateTemplateAct.this));
    }
	
	@Override
    protected void onPause() {
		
		ThreadManager.getInstance().getCreateNewGameThreadPool().execute(createNewTemplateTask.clearCurrentActivity(WizardCreateTemplateAct.this));
        super.onPause();
    }
	
	@Override
    protected void onDestroy() {        
		
		ThreadManager.getInstance().getCreateNewGameThreadPool().execute(createNewTemplateTask.clearCurrentActivity(WizardCreateTemplateAct.this));
		super.onDestroy();
    }

	private void registerListeners()
	{
		findViewById(R.id.btnAdd).setOnClickListener(addListener);
		findViewById(R.id.button_save).setOnClickListener(saveListener);
		findViewById(R.id.button_play).setOnClickListener(playListener);
		findViewById(R.id.button_generate).setOnClickListener(generateListener);

		ListView listView = (ListView)findViewById(R.id.template_list);
		 
		 SwipeDismissListViewTouchListener touchListener =
				         new SwipeDismissListViewTouchListener(
				        		 listView,
				                 new SwipeDismissListViewTouchListener.DismissCallbacks() {
				                     public void onDismiss(ListView listView, int[] reverseSortedPositions) {
				                    	 
				                         for (int position : reverseSortedPositions) {
				                        	 	 
				                        	 arrayAdapter.remove(arrayAdapter.getItem(position));
				                         }
				                         arrayAdapter.notifyDataSetChanged();
				                         
				                         //Rename series here
				                         TemplateLayout templateLayout;
				                         for(int n=0; n<listView.getAdapter().getCount(); n++)
				                         {
				                        	 templateLayout = (TemplateLayout)listView.getAdapter().getItem(n);
				                        	 TextView text = (TextView)templateLayout.findViewById(R.id.wizardtemplate_title);
				                        	 text.setText(getResources().getString(R.string.wizardtemplate_series) + " " + (n+1));
				                         }
				                     }

									@Override
									public boolean canDismiss(int position) {
										
										return true;
									}
				                 });

		 listView.setOnTouchListener(touchListener);
		 listView.setOnScrollListener(touchListener.makeScrollListener());
	}
	
	@Override
	public void onBackPressed() {
	    super.onBackPressed();
	    overridePendingTransition(R.anim.anim_null, R.anim.anim_upperright_corner_out);
	}
	
	public NewTemplateArrayAdapter getArrayAdapter() {
		return arrayAdapter;
	}
	
////	/******************************************************************************************************************
////	 ********************************** Listeners implementation ******************************************************
////	 ******************************************************************************************************************/
	
	View.OnClickListener addListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			
			arrayAdapter.add(new TemplateLayout(getBaseContext(), arrayAdapter.getCount()));
			
			ThreadManager.getInstance().getCreateNewGameThreadPool().execute(createNewTemplateTask.fillLastNewTemplateAdaptors());
		}
	};
	
	View.OnClickListener saveListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			
			CreateNewGameView newGameView = (CreateNewGameView)CreateNewGameController.getInstance().getView();
			
			try {
				templates = newGameView.validateTemplateFields();
				
				if(templates.size() > 0)
					createSaveInputPopUp();
				
			} catch (TemplateValidationException e) {
				AlertBuilder.showPopUp(WizardCreateTemplateAct.this, 
						getResources().getString(R.string.message_template_title_popup_error), 
						e.getMessage());
			}
		}
	};
	
	View.OnClickListener playListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			
			CreateNewGameView newGameView = (CreateNewGameView)CreateNewGameController.getInstance().getView();
			
			try {
				templates = newGameView.validateTemplateFields();
				
				if(templates.size() > 0)
					createPlayInputPopUp();
				
			} catch (TemplateValidationException e) {
				AlertBuilder.showPopUp(WizardCreateTemplateAct.this, 
										getResources().getString(R.string.message_template_title_popup_error), 
										e.getMessage());
			}
		}
	};
	
	View.OnClickListener generateListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			
			CreateNewGameView newGameView = (CreateNewGameView)CreateNewGameController.getInstance().getView();
			
			try {
				templates = newGameView.validateTemplateFields();
				
				if(templates.size() > 0)
					createGenerateInputPopUp();
				
			} catch (TemplateValidationException e) {
				AlertBuilder.showPopUp(WizardCreateTemplateAct.this, 
										getResources().getString(R.string.message_template_title_popup_error), 
										e.getMessage());
			}
		}
	};
	
	private void createSaveInputPopUp()
	{
		AlertDialog.Builder alert = new AlertDialog.Builder(WizardCreateTemplateAct.this);
		
		alert.setTitle(getResources().getString(R.string.message_template_title_popup_input));
		alert.setMessage(getResources().getString(R.string.message_template_info_popup_input));
		
		final EditText input = new EditText(WizardCreateTemplateAct.this);
		alert.setView(input);
		
		alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) { 
				
				String questionTitle = input.getText().toString().trim();
				
				if(!questionTitle.isEmpty())
					ThreadManager.getInstance().getCreateNewGameThreadPool().execute(createNewTemplateTask.saveTemplates(questionTitle, templates));
			}
		});
		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) { 
				// do nothing
			}
		});
		
		// Dialog
		AlertDialog dialog = alert.create();
		dialog.setOnShowListener(new OnShowListener() {
			
			@Override
			public void onShow(DialogInterface dialog) {
				InputMethodManager imm = (InputMethodManager) WizardCreateTemplateAct.this.getBaseContext().getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.showSoftInput(input, InputMethodManager.SHOW_IMPLICIT);
			}
		});
		dialog.show();
	}
	
	private void createPlayInputPopUp()
	{
		AlertDialog.Builder alert = new AlertDialog.Builder(WizardCreateTemplateAct.this);
		
		alert.setTitle(getResources().getString(R.string.message_template_title_popup_input));
	    alert.setMessage(getResources().getString(R.string.message_template_info_popup_input));
	    
	    final EditText input = new EditText(WizardCreateTemplateAct.this);
	    alert.setView(input);
        
	    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 

	        	String questionTitle = input.getText().toString().trim();
	        	
	        	if(!questionTitle.isEmpty())
	        	{
	        		((CreateNewGameView)CreateNewGameController.getInstance().getView()).setGameName(questionTitle);
					Template template = new Template();
					template.setSectionList(templates);
					
					CreateNewGameController.getInstance().getModel().setActiveTemplate(template);
					
					if(Defines.ENABLE_BLUETOOTH)
					{
						Intent intent = new Intent(getBaseContext(), BluetoothActivity.class);
						startActivity(intent);
					}
					else
						new PlayGameAsyncTask(WizardCreateTemplateAct.this).execute();					
	        	}
	        }
	     });
	    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            // do nothing
	        }
	     });
	    
	 // Dialog
	    AlertDialog dialog = alert.create();
	    dialog.setOnShowListener(new OnShowListener() {

	        @Override
	        public void onShow(DialogInterface dialog) {
	            InputMethodManager imm = (InputMethodManager) WizardCreateTemplateAct.this.getBaseContext().getSystemService(Context.INPUT_METHOD_SERVICE);
	            imm.showSoftInput(input, InputMethodManager.SHOW_IMPLICIT);
	        }
	    });
	    dialog.show();
	}
	
	private void createGenerateInputPopUp()
	{
		AlertDialog.Builder alert = new AlertDialog.Builder(WizardCreateTemplateAct.this);
		
		alert.setTitle(getResources().getString(R.string.message_template_title_popup_input));
	    alert.setMessage(getResources().getString(R.string.message_template_info_popup_input));
	    
	    final EditText input = new EditText(WizardCreateTemplateAct.this);
	    alert.setView(input);
        
	    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 

	        	String questionTitle = input.getText().toString().trim();
	        	
	        	if(!questionTitle.isEmpty())
	        	{
					Template template = new Template();
					template.setSectionList(templates);
					
					template.setName(questionTitle);
					ThreadManager.getInstance().getCreateNewGameThreadPool().execute(createNewTemplateTask.generateQuestionSet(template));

	        	}
	        }
	     });
	    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            // do nothing
	        }
	     });
	    
	 // Dialog
	    AlertDialog dialog = alert.create();
	    dialog.setOnShowListener(new OnShowListener() {

	        @Override
	        public void onShow(DialogInterface dialog) {
	            InputMethodManager imm = (InputMethodManager) WizardCreateTemplateAct.this.getBaseContext().getSystemService(Context.INPUT_METHOD_SERVICE);
	            imm.showSoftInput(input, InputMethodManager.SHOW_IMPLICIT);
	        }
	    });
	    dialog.show();
	}
	
}
