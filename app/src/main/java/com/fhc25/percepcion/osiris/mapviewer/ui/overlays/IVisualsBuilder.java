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

import com.fhc25.percepcion.osiris.mapviewer.model.indoor.BuildingElement;
import com.fhc25.percepcion.osiris.mapviewer.model.indoor.Shell;

/**
 * Builder interface for describing the functionality of building different
 * types of VisualElements. This interface should be specialized for each map
 * library.
 */
public interface IVisualsBuilder {

	/**
	 * Builds a visual elements from a building model element
	 * 
	 * @param buildingElement
	 * @return visualElement
	 */
	VisualElement build(BuildingElement buildingElement);

	/**
	 * Builds a localized visual element from an icon (marker)
	 * 
	 * @param marker
	 * @return localized visual element
	 */
	LocalizedVisualElement build(DrawableMarker marker);

	/**
	 * Builds a visual element from a shell model object
	 * 
	 * @param shell
	 * @return visual element
	 */
	VisualElement buildTinyBuilding(Shell shell);


}
