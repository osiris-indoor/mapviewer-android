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
