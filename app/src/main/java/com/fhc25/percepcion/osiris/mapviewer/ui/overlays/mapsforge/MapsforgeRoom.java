
package com.fhc25.percepcion.osiris.mapviewer.ui.overlays.mapsforge;

import com.fhc25.percepcion.osiris.mapviewer.model.indoor.Room;
import com.fhc25.percepcion.osiris.mapviewer.ui.overlays.themes.IndoorElementTheme;
import com.fhc25.percepcion.osiris.mapviewer.ui.overlays.themes.VisualTheme;

import org.mapsforge.map.layer.Layer;
import org.mapsforge.map.layer.overlay.Polygon;

/**
 * Representation of a Mapsforge room VisualElement
 */
public class MapsforgeRoom extends MapsforgeVisualElement {

	private Polygon polygon;
	private Room room;
	
	/**
	 * Main constructor
	 * 
	 * @param visualTheme
	 * @param room
	 */
	public MapsforgeRoom(VisualTheme visualTheme, Room room) {
		
		this.room = room;
		IndoorElementTheme elementTheme = visualTheme.getIndoorTheme().getRoomTheme();
		polygon = getPolygon(this.room.getGeometry(), elementTheme);
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
