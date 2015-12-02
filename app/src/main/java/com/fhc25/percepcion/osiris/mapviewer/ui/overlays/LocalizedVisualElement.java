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
