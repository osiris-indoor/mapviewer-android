
package com.fhc25.percepcion.osiris.mapviewer.ui.overlays.mapsforge;

import com.fhc25.percepcion.osiris.mapviewer.model.indoor.Shell;
import com.fhc25.percepcion.osiris.mapviewer.ui.overlays.themes.IndoorElementTheme;
import com.fhc25.percepcion.osiris.mapviewer.ui.overlays.themes.VisualTheme;

import org.mapsforge.map.layer.Layer;
import org.mapsforge.map.layer.overlay.Polygon;

/**
 * Representation of a Mapsforge "tiny" building VisualElement
 */
public class MapsforgeTinyBuilding extends MapsforgeVisualElement {

	private Polygon mPolygon;
	private Shell mShell;

	public MapsforgeTinyBuilding(VisualTheme visualTheme, Shell shell) {

		mShell = shell;
		IndoorElementTheme elementTheme = visualTheme.getIndoorTheme().getTinyBuildingTheme();
		mPolygon = getPolygon(mShell.getGeometry(), elementTheme);
	}

	@Override
	public Layer getLayer() {
		return mPolygon;
	}

	@Override
	public int getZDepth() {
		return 0;
	}

    @Override
    public void destroy() {
        mPolygon.onDestroy();
    }

}
