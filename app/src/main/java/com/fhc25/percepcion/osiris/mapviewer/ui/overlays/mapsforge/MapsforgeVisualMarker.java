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

import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import com.fhc25.percepcion.osiris.mapviewer.model.location.Location;
import com.fhc25.percepcion.osiris.mapviewer.ui.overlays.DrawableMarker;

import org.mapsforge.core.graphics.Bitmap;
import org.mapsforge.core.model.LatLong;
import org.mapsforge.map.android.graphics.AndroidGraphicFactory;
import org.mapsforge.map.layer.Layer;
import org.mapsforge.map.layer.overlay.Marker;


/**
 * Representation of a Mapsforge marker VisualElement
 */
public class MapsforgeVisualMarker extends MapsforgeLocalizedVisualElement {

	private Marker marker;
	private Location location;
    private Bitmap bitmap;

	/**
	 * Main constructor
	 * 
	 * @param marker
	 */
	public MapsforgeVisualMarker(DrawableMarker marker) {

		location = marker.getLocation();
		LatLong point = new LatLong(marker.getLocation().getPosition()
				.getLatitude(), marker.getLocation().getPosition()
				.getLongitude());
		
		Drawable drawable = marker.getDrawable();
        bitmap = AndroidGraphicFactory.convertToBitmap(drawable);

		Rect rect = drawable.getBounds();
		int width = Math.abs(rect.right - rect.left);
		int height = Math.abs(rect.bottom - rect.top);

        bitmap.scaleTo(width, height);
		
		this.marker = new Marker(point, bitmap, rect.left + width/2, rect.bottom - height/2);
	}

	@Override
	public Layer getLayer() {
		return marker;
	}

	@Override
	public int getZDepth() {
		return 10;
	}

	@Override
	public void setLocation(Location location) {
		
		this.location = location;
		LatLong point = new LatLong(location.getPosition()
				.getLatitude(), location.getPosition()
				.getLongitude());
		
		marker.setLatLong(point);
	}

	@Override
	public Location getLocation() {
		return location;
	}

    @Override
    public void destroy() {
    }

}
