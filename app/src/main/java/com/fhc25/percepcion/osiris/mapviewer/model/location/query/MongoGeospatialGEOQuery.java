
package com.fhc25.percepcion.osiris.mapviewer.model.location.query;

import com.fhc25.percepcion.osiris.mapviewer.model.location.Geometry;

/**
 * Helper object that represents a complete geospatial query to the backend
 */
public class MongoGeospatialGEOQuery extends MongoGeospatialQuery {

    static private final String TAG = MongoGeospatialGEOQuery.class.getName();

    public enum SearchTypeGEO{GEO_WITHIN, GEO_INTERSECTS};

    private SearchTypeGEO searchType;

    public SearchTypeGEO getSearchType() {
        return searchType;
    }

    /**
     * Construction of a geospatial query object
     *
     * @param searchType - GEO_WITHIN, GEO_INTERSECTS -
     * @param geometry
     */
    public MongoGeospatialGEOQuery(SearchTypeGEO searchType, Geometry geometry) {
        super(geometry);

        this.searchType = searchType;
    }

}
