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

package com.fhc25.percepcion.osiris.mapviewer.ui.overlays.mapsforge;

import com.fhc25.percepcion.osiris.mapviewer.model.indoor.Stairway;
import com.fhc25.percepcion.osiris.mapviewer.ui.overlays.themes.IndoorElementTheme;
import com.fhc25.percepcion.osiris.mapviewer.ui.overlays.themes.VisualTheme;

import org.mapsforge.map.layer.Layer;
import org.mapsforge.map.layer.overlay.Polygon;

/**
 * Representation of a Mapsforge stairway VisualElement
 */
public class MapsforgeStairway extends MapsforgeVisualElement {

	private Polygon polygon;
	private Stairway stairway;
	
	/**
	 * Main constructor
	 * 
	 * @param visualTheme
	 * @param stairway
	 */
	public MapsforgeStairway(VisualTheme visualTheme, Stairway stairway) {
		
		this.stairway = stairway;
		IndoorElementTheme elementTheme = visualTheme.getIndoorTheme().getStairwayTheme();
		polygon = getPolygon(this.stairway.getGeometry(), elementTheme);
	}
	
	@Override
	public Layer getLayer() {
		return polygon;
	}

	@Override
	public int getZDepth() {
		return 3;
	}

    @Override
    public void destroy() {
        polygon.onDestroy();
    }
}
