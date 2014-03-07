package ca.openquiz.mobile.login;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import applicationTools.GetInstanceBluetoothManager;
import ca.openquiz.mobile.R;
import ca.openquiz.mobile.ThreadManager;
import ca.openquiz.mobile.bluetooth.AndroidBluetoothClientSocket;
import ca.openquiz.mobile.util.SystemUiHider;
import ca.openquiz.mobile.wizardTeam.WizardTeamAct;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class LoginAct extends Activity {
	/**
	 * If set, will toggle the system UI visibility upon interaction. Otherwise,
	 * will show the system UI visibility upon interaction.
	 */
	private static final boolean TOGGLE_ON_CLICK = false;

	/**
	 * The flags to pass to {@link SystemUiHider#getInstance}.
	 */
	private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

	/**
	 * The instance of the {@link SystemUiHider} for this activity.
	 */
	private SystemUiHider mSystemUiHider;
	
	private LoginTask loginTask = new LoginTask();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

	    requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);

		setContentView(R.layout.login_screen);

		final View controlsView = findViewById(R.id.fullscreen_content_controls);
		final View contentView = findViewById(R.id.fullscreen_content);
		
		// Set up an instance of SystemUiHider to control the system UI for
		// this activity.
		mSystemUiHider = SystemUiHider.getInstance(this, contentView,
				HIDER_FLAGS);
		mSystemUiHider.setup();
		mSystemUiHider
				.setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
					// Cached values.
					int mControlsHeight;
					int mShortAnimTime;

					@Override
					@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
					public void onVisibilityChange(boolean visible) {
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
							// If the ViewPropertyAnimator API is available
							// (Honeycomb MR2 and later), use it to animate the
							// in-layout UI controls at the bottom of the
							// screen.
							if (mControlsHeight == 0) {
								mControlsHeight = controlsView.getHeight();
							}
							if (mShortAnimTime == 0) {
								mShortAnimTime = getResources().getInteger(
										android.R.integer.config_shortAnimTime);
							}
							controlsView
									.animate()
									.translationY(visible ? 0 : mControlsHeight)
									.setDuration(mShortAnimTime);
						} else {
							// If the ViewPropertyAnimator APIs aren't
							// available, simply show or hide the in-layout UI
							// controls.findViewById(R.id.fullscreen_content_controls)
							controlsView.setVisibility(visible ? View.VISIBLE
									: View.GONE);
						}
					}
				});

		// Set up the user interaction to manually show or hide the system UI.
		contentView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (TOGGLE_ON_CLICK) {
					mSystemUiHider.toggle();
				} else {
					mSystemUiHider.show();
				}
			}
		});

		registerListeners();
		GetInstanceBluetoothManager.setInstance(AndroidBluetoothClientSocket.getInstance());
	}
	
	@Override
	public void onStart()
	{
		super.onStart();
		findViewById(R.id.loading).setVisibility(View.INVISIBLE);
		
		eraseTextFields();
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		// Trigger the initial hide() shortly after the activity has been
		// created, to briefly hint to the user that UI controls
		// are available.
		delayedHide(100);
	}

	/**
	 * Touch listener to use for in-layout UI controls to delay hiding the
	 * system UI. This is to prevent the jarring behavior of controls going away
	 * while interacting with activity UI.
	 */


	Handler mHideHandler = new Handler();
	Runnable mHideRunnable = new Runnable() {
		@Override
		public void run() {
			mSystemUiHider.show();
		}
	};

	/**
	 * Schedules a call to hide() in [delay] milliseconds, canceling any
	 * previously scheduled calls.
	 */
	private void delayedHide(int delayMillis) {
		mHideHandler.removeCallbacks(mHideRunnable);
		mHideHandler.postDelayed(mHideRunnable, delayMillis);
	}
	
	private void registerListeners()
	{
		findViewById(R.id.btnAuth).setOnClickListener(authentificationListener);	    
		findViewById(R.id.btnNoAuth).setOnClickListener(guestListener);	 
		((EditText)findViewById(R.id.txtPassword)).setOnEditorActionListener(onEnterPressedEvent);	    
	}
	
	private void eraseTextFields()
	{
		EditText userText = (EditText)findViewById(R.id.txtUser);
		userText.setText("");
		
		EditText passText = (EditText)findViewById(R.id.txtPassword);
		passText.setText("");
	}
	
//	/******************************************************************************************************************
//	 ********************************** Listeners implementation ******************************************************
//	 ******************************************************************************************************************/
	View.OnClickListener authentificationListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Authentificate();
		}
	};
	
	View.OnClickListener guestListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			InputMethodManager inputManager = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE); 

			inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                       InputMethodManager.HIDE_NOT_ALWAYS);
			
			Intent intent = new Intent(getBaseContext(), WizardTeamAct.class);
			LoginAct.this.startActivity(intent);
		}
	};
	
	EditText.OnEditorActionListener onEnterPressedEvent = new EditText.OnEditorActionListener() {
	    @Override
	    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
	        if (actionId == EditorInfo.IME_ACTION_GO) {
	        	Authentificate();
	            return true;
	        }
	        return false;
	    }
	};
	
	@Override
	public void onBackPressed() {
	    super.onBackPressed();
	    overridePendingTransition(R.anim.anim_toright_in, R.anim.anim_toright_out);
	}
	
	private void Authentificate(){
		InputMethodManager inputManager = (InputMethodManager)
				getSystemService(Context.INPUT_METHOD_SERVICE); 
		
		inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);
		
		findViewById(R.id.loading).setVisibility(View.VISIBLE);
		ThreadManager.getInstance().getCreateNewGameThreadPool().execute(loginTask.initLoginAct(LoginAct.this));
	}
}
