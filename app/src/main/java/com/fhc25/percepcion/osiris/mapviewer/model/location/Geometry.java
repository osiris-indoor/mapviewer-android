
package com.fhc25.percepcion.osiris.mapviewer.model.location;

import java.io.Serializable;
import java.util.Collection;

/**
 * Base abstract class for geometric objects
 */
abstract public class Geometry implements Serializable {

	abstract public Collection<Point> getFormingPoints();
}
