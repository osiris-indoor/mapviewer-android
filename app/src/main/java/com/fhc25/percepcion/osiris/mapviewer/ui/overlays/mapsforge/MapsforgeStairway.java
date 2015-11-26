
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
