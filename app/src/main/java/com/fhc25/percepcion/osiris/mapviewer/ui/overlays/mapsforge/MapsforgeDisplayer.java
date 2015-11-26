
package com.fhc25.percepcion.osiris.mapviewer.ui.overlays.mapsforge;

import android.util.Log;

import com.fhc25.percepcion.osiris.mapviewer.common.log.Lgr;
import com.fhc25.percepcion.osiris.mapviewer.ui.overlays.VisualElementDisplayer;

import org.mapsforge.map.android.view.MapView;
import org.mapsforge.map.layer.Layer;
import org.mapsforge.map.layer.renderer.TileRendererLayer;

import java.util.ArrayList;

/**
 * Specific Mapsforge displayer implementation
 */
public class MapsforgeDisplayer extends
		VisualElementDisplayer<IMapsforgeVisualElement> {

	static private final String TAG = MapsforgeDisplayer.class.getName();
	
	private MapView mapView;

	public MapsforgeDisplayer(MapView mapView) {
		super(IMapsforgeVisualElement.class);
		this.mapView = mapView;
	}

	@Override
	public void update() {
		Lgr.w(TAG, "update");
		
		mapView.getLayerManager().redrawLayers();
	}

	@Override
	public void clear() {
		
		Log.i(TAG, "clear");
		
		java.util.Collection<Layer> removeElementsList = new ArrayList<Layer>();
		
		for (Layer layer : mapView.getLayerManager().getLayers()) {
			
			if (!(layer instanceof TileRendererLayer)) {
                removeElementsList.add(layer);
            }
		}
		
		for (Layer layer : removeElementsList) {
			mapView.getLayerManager().getLayers().remove(layer);
		}
	}

	@Override
	protected void displayElement(IMapsforgeVisualElement visualElement) {

		if (!mapView.getLayerManager().getLayers().contains(visualElement.getLayer())) {
            mapView.getLayerManager().getLayers().add(visualElement.getLayer());
        }
	}

	@Override
	protected void removeElement(IMapsforgeVisualElement visualElement) {
		mapView.getLayerManager().getLayers().remove(visualElement.getLayer());
	}

}
