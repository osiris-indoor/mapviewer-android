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
