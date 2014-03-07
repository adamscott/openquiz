package ca.openquiz.mobile.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class AlertBuilder {
	
	public static void showPopUp(Activity act, String title, String message)
	{
		AlertDialog.Builder alert = new AlertDialog.Builder(act);
		
		alert.setTitle(title);
	    alert.setMessage(message);
	    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            // handle OK button
	        }
	     });
	    alert.show();
	}
	
	public static void showChoiceDialogBox(Activity act, String title, String message)
	{
		AlertDialog.Builder alert = new AlertDialog.Builder(act);
		
		alert.setTitle(title);
	    alert.setMessage(message);
	    alert.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            // handle OK button
	        }
	     });
	    alert.setNegativeButton("Non", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            // do nothing
	        }
	     });
	    alert.show();
	}
}
