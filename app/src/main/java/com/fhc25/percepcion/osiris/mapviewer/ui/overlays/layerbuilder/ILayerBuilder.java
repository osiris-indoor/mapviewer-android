
package com.fhc25.percepcion.osiris.mapviewer.ui.overlays.layerbuilder;

import com.fhc25.percepcion.osiris.mapviewer.model.indoor.Building;
import com.fhc25.percepcion.osiris.mapviewer.ui.overlays.layers.IndoorLayer;

/**
 * Describes the functionality of a LayerBuilder. This component should be
 * capable of building all type of layers that the application will need.
 */
public interface ILayerBuilder {

	/**
	 * Builds the indoor layer from the information of a Building model
	 * 
	 * @param building
	 * @return indoor layer
	 */
	IndoorLayer buildIndoor(Building building);
	
}
