
package com.fhc25.percepcion.osiris.mapviewer.ui.overlays;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import com.fhc25.percepcion.osiris.mapviewer.model.location.Location;


/**
 * Icon representation for drawing purposes
 */
public class DrawableMarker {

	private Location location;
	private Drawable drawable;
	
	/**
	 * Main constructor
	 * 
	 * @param location
	 * @param drawable
	 */
	public DrawableMarker(Location location, Drawable drawable) {
		this.location = location;
		this.drawable = drawable;
	}

	/**
	 * Gets the location of the icon
	 * 
	 * @return location
	 */
	public Location getLocation() {
		return location;
	}
	
	/**
	 * Gets the drawable associated with the icon
	 * 
	 * @return drawable
	 */
	public Drawable getDrawable() {
		return drawable;
	}
	
	/**
	 * Sets the bounds of the icon
	 * 
	 * @param bounds
	 */
	public void setBounds(Rect bounds) {
		drawable.setBounds(bounds);
	}
	
	/**
	 * Gets the bounds of the icon
	 * 
	 * @return bounds
	 */
	public Rect getBounds() {
		return drawable.getBounds();
	}

}
