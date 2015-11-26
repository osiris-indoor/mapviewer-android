
package com.fhc25.percepcion.osiris.mapviewer.ui.overlays.themes;

import android.content.Context;

/**
 * Complete visual theme of an application
 */
public class VisualTheme {

	protected IndoorVisualTheme indoorVisualTheme = new IndoorVisualTheme();

	protected Context context;

	/**
	 * Main constructor
	 * 
	 * @param context
	 */
	public VisualTheme(Context context) {
		this.context = context;
	}

	/**
	 * Gets the indoor theme
	 * 
	 * @return theme
	 */
	public IndoorVisualTheme getIndoorTheme() {
		return indoorVisualTheme;
	}

	/**
	 * Gets the context
	 * 
	 * @return context
	 */
	public Context getContext() {
		return context;
	}

}
