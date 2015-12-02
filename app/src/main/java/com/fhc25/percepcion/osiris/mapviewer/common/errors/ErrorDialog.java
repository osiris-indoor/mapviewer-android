/**
Copyright 2015 Osiris Project Team

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/   

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
