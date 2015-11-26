
package com.fhc25.percepcion.osiris.mapviewer.ui.overlays.mapsforge;

import android.content.res.Resources;

import com.fhc25.percepcion.osiris.mapviewer.ui.overlays.IVisualElementDisplayer;
import com.fhc25.percepcion.osiris.mapviewer.ui.overlays.IVisualsBuilder;
import com.fhc25.percepcion.osiris.mapviewer.ui.overlays.OsirisOverlayManager;
import com.fhc25.percepcion.osiris.mapviewer.ui.overlays.themes.VisualTheme;

import org.mapsforge.map.android.view.MapView;

/**
 * Specialization of the {@link OsirisOverlayManager} component for
 * Mapsforge library
 */
public class MapsforgeOsirisOverlayManager extends
		OsirisOverlayManager {

	private MapsforgeDisplayer displayer = null;
	private MapsforgeVisualsBuilder visualsBuilder = null;

	/**
	 * Main constructor
	 * 
	 * @param resources
	 * @param mapView
	 * @param theme
	 */
	public MapsforgeOsirisOverlayManager(Resources resources,
										 MapView mapView, VisualTheme theme) {
		super(resources, mapView, theme);
	}

	@Override
	public IVisualsBuilder getVisualsBuilder() {
		if (visualsBuilder == null) {
            visualsBuilder = new MapsforgeVisualsBuilder(theme);
        }
		return visualsBuilder;
	}

	@Override
	public IVisualElementDisplayer getDisplayer() {
		if (displayer == null) {
            displayer = new MapsforgeDisplayer(mapView);
        }
		return displayer;
	}

}
