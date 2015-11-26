
package com.fhc25.percepcion.osiris.mapviewer.ui.overlays;

import com.fhc25.percepcion.osiris.mapviewer.model.location.Location;

/**
 * Describes a {@link VisualElement} that includes location information
 */
public abstract class LocalizedVisualElement extends VisualElement {
	
	/**
	 * Sets the location
	 * 
	 * @param location
	 */
	abstract public void setLocation(Location location);

	/**
	 * Gets the location
	 * 
	 * @return location
	 */
	abstract public Location getLocation();
	
}
