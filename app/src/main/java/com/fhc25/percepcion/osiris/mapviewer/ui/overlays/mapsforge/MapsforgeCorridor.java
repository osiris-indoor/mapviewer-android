
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
