
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
