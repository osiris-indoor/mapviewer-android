
package com.fhc25.percepcion.osiris.mapviewer.ui.overlays.mapsforge;

import com.fhc25.percepcion.osiris.mapviewer.model.indoor.Office;
import com.fhc25.percepcion.osiris.mapviewer.ui.overlays.themes.IndoorElementTheme;
import com.fhc25.percepcion.osiris.mapviewer.ui.overlays.themes.VisualTheme;

import org.mapsforge.map.layer.Layer;
import org.mapsforge.map.layer.overlay.Polygon;

/**
 * Representation of a Mapsforge office VisualElement
 */
public class MapsforgeOffice extends MapsforgeVisualElement {

	private Polygon polygon;
	private Office office;

	/**
	 * Main constructor
	 * 
	 * @param visualTheme
	 * @param office
	 */
	public MapsforgeOffice(VisualTheme visualTheme, Office office) {
		
		this.office = office;
		IndoorElementTheme elementTheme = visualTheme.getIndoorTheme().getOfficeTheme();
		polygon = getPolygon(this.office.getGeometry(), elementTheme);
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
