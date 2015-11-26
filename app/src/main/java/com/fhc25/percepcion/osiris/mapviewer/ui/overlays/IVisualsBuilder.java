
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
