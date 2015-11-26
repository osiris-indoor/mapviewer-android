
package com.fhc25.percepcion.osiris.mapviewer.ui.overlays.layers;

import com.fhc25.percepcion.osiris.mapviewer.ui.overlays.DrawableMarker;
import com.fhc25.percepcion.osiris.mapviewer.ui.overlays.IVisualsBuilder;
import com.fhc25.percepcion.osiris.mapviewer.ui.overlays.VisualElement;
import com.fhc25.percepcion.osiris.mapviewer.ui.overlays.layeroverlay.SparseArrayLevelOverlay;

import java.util.LinkedList;

/**
 * Indoor marker layer
 */
public class LevelMarkerLayer extends SparseArrayLevelOverlay {

	private IVisualsBuilder builder;

	/**
	 * Main constructor
	 * 
	 * @param identificationName
	 * @param builder
	 */
	public LevelMarkerLayer(String identificationName, IVisualsBuilder builder) {
		super(identificationName);
		this.builder = builder;
	}

	/**
	 * Adds a marker to a certain level
	 * 
	 * @param marker
	 * @param level
	 */
	public void addMarker(DrawableMarker marker, Integer level) {
		java.util.Collection<VisualElement> list = new LinkedList<VisualElement>();
		list.add(builder.build(marker));
		addAll(level, list);
	}

	@Override
	public int getZDepth() {
		return 20;
	}

}
