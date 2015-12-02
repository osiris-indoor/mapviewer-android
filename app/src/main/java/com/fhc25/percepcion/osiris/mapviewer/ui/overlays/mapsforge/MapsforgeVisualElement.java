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

import com.fhc25.percepcion.osiris.mapviewer.model.location.LineString;
import com.fhc25.percepcion.osiris.mapviewer.model.location.Point;
import com.fhc25.percepcion.osiris.mapviewer.ui.overlays.VisualElement;
import com.fhc25.percepcion.osiris.mapviewer.ui.overlays.themes.IndoorElementTheme;

import org.mapsforge.core.graphics.Cap;
import org.mapsforge.core.graphics.Join;
import org.mapsforge.core.graphics.Paint;
import org.mapsforge.core.graphics.Style;
import org.mapsforge.core.model.LatLong;
import org.mapsforge.map.android.graphics.AndroidGraphicFactory;
import org.mapsforge.map.layer.overlay.Circle;
import org.mapsforge.map.layer.overlay.Polygon;

import java.util.List;

/**
 * Mapsforge specialization of the {@link VisualElement} class
 */
abstract public class MapsforgeVisualElement extends VisualElement implements
		IMapsforgeVisualElement {

	/**
	 * Gets the specific PaintStroke for a IndoorElementTheme
	 * 
	 * @param elementTheme
	 * @return Paint
	 */
	protected Paint getPaintStroke(IndoorElementTheme elementTheme) {

		Paint paintStroke = AndroidGraphicFactory.INSTANCE.createPaint();
		paintStroke.setStyle(Style.STROKE);
		paintStroke.setColor(elementTheme.getStrokeColor());
		paintStroke.setStrokeWidth(elementTheme.getStrokeWidth());

		return paintStroke;
	}

	/**
	 * Gets the specific PaintFill for a IndoorElementTheme
	 * 
	 * @param elementTheme
	 * @return Paint
	 */
	protected Paint getPaintFill(IndoorElementTheme elementTheme) {

		Paint paintFill = AndroidGraphicFactory.INSTANCE.createPaint();
		paintFill.setStyle(Style.FILL);
		paintFill.setColor(elementTheme.getFillColor());
		paintFill.setStrokeCap(Cap.ROUND);
		paintFill.setStrokeJoin(Join.ROUND);

		return paintFill;
	}

	/**
	 * Gets the Polygon mapsforge native visual representation given a
	 * LineString and a theme
	 * 
	 * @param line
	 * @param theme
	 * @return polygon
	 */
	protected Polygon getPolygon(LineString line, IndoorElementTheme theme) {

		Polygon polygon = new Polygon(getPaintFill(theme),
				getPaintStroke(theme), AndroidGraphicFactory.INSTANCE);

		List<LatLong> latLongs = polygon.getLatLongs();

		for (Point point : line.getCollectionPoint()) {
			LatLong p = new LatLong(point.getLatitude(), point.getLongitude());
			latLongs.add(p);
		}

		return polygon;
	}

	/**
	 * Gets a Circle mapsforge native visual representation given a Point and a
	 * theme
	 * 
	 * @param point
	 * @param theme
	 * @return circle
	 */
	public Circle getCircle(Point point, IndoorElementTheme theme) {
		LatLong geoPoint = new LatLong(point.getLatitude(),
				point.getLongitude());
		return new Circle(geoPoint, theme.getRadius(), getPaintFill(theme),
				getPaintStroke(theme));
	}
}
