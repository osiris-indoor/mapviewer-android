
package com.fhc25.percepcion.osiris.mapviewer.common.errors;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class ErrorDialog {

	/**
	 * Shows an error dialog with a certain title and message
	 * 
	 * @param activity
	 * @param title
	 * @param message
	 */
	static public void showErrorDialog(final Activity activity, final String title,
                                       final String message, final AlertDialog.OnClickListener
            listener){

		if(activity != null && !activity.isFinishing()){
			activity.runOnUiThread(new Runnable(){
	
				@Override
				public void run() {
                    if (!activity.isFinishing()) {
                        AlertDialog alertDialog = new AlertDialog.Builder(activity,
                                AlertDialog.THEME_HOLO_LIGHT)
                                .setTitle(title)
                                .setMessage(message)
                                .setPositiveButton("OK", listener)
                                .setCancelable(false)
                                .create();

                        alertDialog.setCanceledOnTouchOutside(false);

                        alertDialog.show();
                    }
				}
			});
		}
	}

    /**
     * Shows an error dialog with a certain title and message
     *
     * @param activity
     * @param title
     * @param message
     */
    static public void showErrorDialog(final Activity activity, final String title,
                                       final String message){

        showErrorDialog(activity, title, message, new AlertDialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // No implementation here
            }
        });
    }

    /**
     * Shows an error dialog with the information contained in a PError instance
     *
     * @param activity
     * @param error
     */
    static public void showErrorDialog(final Activity activity, final Failure error) {

        showErrorDialog(activity, error, new AlertDialog.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // No implementation here
            }
        });
    }

    /**
     * Shows an error dialog with the information contained in a PError instance
     *
     * @param activity
     * @param error
     */
    static public void showErrorDialog(final Activity activity, final Failure error,
                                       AlertDialog.OnClickListener listener) {

        showErrorDialog(activity, error.getMessage(), error.getMessage(), listener);
    }

}
