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
