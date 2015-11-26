
package com.fhc25.percepcion.osiris.mapviewer.ui.overlays.mapsforge;

import com.fhc25.percepcion.osiris.mapviewer.model.indoor.Elevator;
import com.fhc25.percepcion.osiris.mapviewer.ui.overlays.themes.IndoorElementTheme;
import com.fhc25.percepcion.osiris.mapviewer.ui.overlays.themes.VisualTheme;

import org.mapsforge.map.layer.Layer;
import org.mapsforge.map.layer.overlay.Polygon;

/**
 * Representation of a Mapsforge elevator VisualElement
 */
public class MapsforgeElevator extends MapsforgeVisualElement {

	private Polygon polygon;
	private Elevator elevator;
	
	/**
	 * Main constructor
	 * 
	 * @param visualTheme
	 * @param elevator
	 */
	public MapsforgeElevator(VisualTheme visualTheme, Elevator elevator) {
		
		this.elevator = elevator;
		IndoorElementTheme elementTheme = visualTheme.getIndoorTheme().getElevatorTheme();
		polygon = getPolygon(this.elevator.getGeometry(), elementTheme);
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
