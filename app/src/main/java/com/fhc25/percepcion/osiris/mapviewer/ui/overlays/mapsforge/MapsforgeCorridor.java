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

import com.fhc25.percepcion.osiris.mapviewer.model.indoor.Corridor;
import com.fhc25.percepcion.osiris.mapviewer.ui.overlays.themes.IndoorElementTheme;
import com.fhc25.percepcion.osiris.mapviewer.ui.overlays.themes.VisualTheme;

import org.mapsforge.map.layer.Layer;
import org.mapsforge.map.layer.overlay.Polygon;

/**
 * Representation of a Mapsforge corridor VisualElement
 */
public class MapsforgeCorridor extends MapsforgeVisualElement {

	private Polygon polygon = null;
	private Corridor corridor;
	private VisualTheme visualTheme;

	/**
	 * Main constructor
	 * 
	 * @param visualTheme
	 * @param corridor
	 */
	public MapsforgeCorridor(VisualTheme visualTheme, Corridor corridor) {

		this.corridor = corridor;
		this.visualTheme = visualTheme;
	}

	@Override
	public Layer getLayer() {

		if (polygon == null) {
			IndoorElementTheme elementTheme = visualTheme.getIndoorTheme()
					.getCorridorTheme();
			polygon = getPolygon(corridor.getGeometry(), elementTheme);
		}

		return polygon;
	}

	@Override
	public int getZDepth() {
		return 1;
	}

    @Override
    public void destroy() {
        if (polygon != null) {
            polygon.onDestroy();
        }
        polygon = null;
    }

}
