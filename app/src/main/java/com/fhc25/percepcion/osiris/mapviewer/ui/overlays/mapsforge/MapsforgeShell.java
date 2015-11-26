
package com.fhc25.percepcion.osiris.mapviewer.ui.overlays.mapsforge;

import com.fhc25.percepcion.osiris.mapviewer.model.indoor.Shell;
import com.fhc25.percepcion.osiris.mapviewer.ui.overlays.themes.IndoorElementTheme;
import com.fhc25.percepcion.osiris.mapviewer.ui.overlays.themes.VisualTheme;

import org.mapsforge.map.layer.Layer;
import org.mapsforge.map.layer.overlay.Polygon;

/**
 * Representation of a Mapsforge shell VisualElement
 *
 */
public class MapsforgeShell extends MapsforgeVisualElement {

	private Polygon polygon;
	private Shell shell;

	/**
	 * Main constructor
	 * 
	 * @param visualTheme
	 * @param shell
	 */
	public MapsforgeShell(VisualTheme visualTheme, Shell shell) {

		this.shell = shell;
		IndoorElementTheme elementTheme = visualTheme.getIndoorTheme().getShellTheme();
		polygon = getPolygon(this.shell.getGeometry(), elementTheme);
	}

	@Override
	public Layer getLayer() {
		return polygon;
	}

	@Override
	public int getZDepth() {
		return 0;
	}

    @Override
    public void destroy() {
        polygon.onDestroy();
    }
}
