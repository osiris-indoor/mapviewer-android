
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
