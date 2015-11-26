
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
