/**
Copyright 2015 Osiris Project Team

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/   

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
