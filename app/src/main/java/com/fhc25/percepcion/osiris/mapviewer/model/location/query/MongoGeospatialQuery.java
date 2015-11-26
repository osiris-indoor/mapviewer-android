
package com.fhc25.percepcion.osiris.mapviewer.model.location.query;

import com.fhc25.percepcion.osiris.mapviewer.model.location.Geometry;

/**
 * Base class for Mongo geospatial queries to the backend
 */
public abstract class MongoGeospatialQuery {
	
	private Geometry geometry;

    public MongoGeospatialQuery(Geometry geometry) {
        this.geometry = geometry;
    }

	public Geometry getGeometry() {
        return geometry;
    }

}
