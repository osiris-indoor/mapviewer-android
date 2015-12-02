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
