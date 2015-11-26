
package com.fhc25.percepcion.osiris.mapviewer.model.location.query;

import com.fhc25.percepcion.osiris.mapviewer.model.location.Point;

/**
 * Helper object that represents a complete geospatial "near" query to the
 * backend
 */
public class MongoGeospatialNEARQuery extends MongoGeospatialQuery {

    static private final String TAG = MongoGeospatialNEARQuery.class.getName();

	public enum SearchTypeNEAR{NEAR, NEAR_SPHERE};

	private SearchTypeNEAR searchType;
	private Double maxDistance;

	public SearchTypeNEAR getSearchType() {
		return searchType;
	}

	public Double getMaxDistance() {
		return maxDistance;
	}

	/**
	 * Construction of a geospatial query object
	 * 
	 * @param searchType
	 *            - NEAR, NEAR_SPHERE -
	 * @param geometry
	 * @param maxDistance
	 */
	public MongoGeospatialNEARQuery(SearchTypeNEAR searchType, Point geometry,
			Double maxDistance) {
		super(geometry);

		this.searchType = searchType;
		this.maxDistance = maxDistance;
	}

}
