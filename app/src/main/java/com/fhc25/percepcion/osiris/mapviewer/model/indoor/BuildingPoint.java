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

package com.fhc25.percepcion.osiris.mapviewer.model.indoor;

import com.fhc25.percepcion.osiris.mapviewer.model.location.Point;

import java.io.Serializable;

/**
 * Model class that represents a BuldingElement which has a Point geometry
 * associated with it. It is used to model building parts that are related to
 * just one geographic position such as Doors
 */
public class BuildingPoint extends BuildingElement implements Serializable {

	private Point geometry;

	/**
	 * Default constructor
	 * 
	 * @param id
	 * @param name
	 * @param geometry
	 * @param level
	 */
	public BuildingPoint(Long id, String name, Point geometry, String level) {
		super(id, name, level);
		this.geometry = geometry;
	}

	/**
	 * Gets the geographic Point associated with the BuildingPoint
	 * 
	 * @return point
	 */
	public Point getGeometry() {
		return geometry;
	}

}
