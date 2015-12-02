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

import com.fhc25.percepcion.osiris.mapviewer.model.indoor.Door;
import com.fhc25.percepcion.osiris.mapviewer.ui.overlays.themes.IndoorElementTheme;
import com.fhc25.percepcion.osiris.mapviewer.ui.overlays.themes.VisualTheme;

import org.mapsforge.map.layer.Layer;
import org.mapsforge.map.layer.overlay.Circle;

/**
 * Representation of a Mapsforge door VisualElement
 */
public class MapsforgeDoor extends MapsforgeVisualElement {

	private Door door;
	private Circle circle;
	
	/**
	 * Main constructor
	 * 
	 * @param visualTheme
	 * @param door
	 */
	public MapsforgeDoor(VisualTheme visualTheme, Door door) {
		
		this.door = door;
		IndoorElementTheme elementTheme = visualTheme.getIndoorTheme().getDoorTheme();
		circle = getCircle(this.door.getGeometry(), elementTheme);
	}
	
	@Override
	public Layer getLayer() {
		return circle;
	}

	@Override
	public int getZDepth() {
		return 4;
	}

    @Override
    public void destroy() {
        circle.onDestroy();
    }
}
